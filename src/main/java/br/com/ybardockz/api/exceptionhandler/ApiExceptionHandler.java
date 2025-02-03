package br.com.ybardockz.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.com.ybardockz.api.exceptionhandler.enums.ProblemType;
import br.com.ybardockz.core.validation.ValidacaoException;
import br.com.ybardockz.domain.exception.EntidadeEmUsoException;
import br.com.ybardockz.domain.exception.EntidadeNaoEncontradaException;
import br.com.ybardockz.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro inesperado no sistema. Tente novamente e se o problema"
			+ " persistir, entre em contato com o Admininstrador do sistema.";
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<?> handleValidacaoException(ValidacaoException ex, WebRequest request) {
		return handleValidationInternal(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request,
				ex.getBindingResult());
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request, BindingResult bindResult) {
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		
		List<Problema.Object> problemObjects = bindResult.getAllErrors().stream()
				.map(objectError -> {
					String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					
					String name = objectError.getObjectName();
					
					if (objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
							
					return Problema.Object.builder()
						.name(name)
						.userMessage(message)
						.build();
				})
				.collect(Collectors.toList());
			
		
		Problema problema = createProblemaBuilder(HttpStatus.valueOf(status.value()), problemType, detail)
				.userMessage(detail)
				.objects(problemObjects)
				.build();
		
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleInternalError(Exception ex, WebRequest request) {
		
		ex.printStackTrace();
		
		ProblemType problemType = ProblemType.ERRO_INTERNO;
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;
		
		Problema problema = createProblemaBuilder(HttpStatus.INTERNAL_SERVER_ERROR, problemType, detail)
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = "O recurso: " + ex.getResourcePath() + " não existe.";
		
		Problema problema = createProblemaBuilder(HttpStatus.valueOf(status.value()), problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers, status
					, request);
		}
		
		
		return super.handleTypeMismatch(ex, headers, status, request);
	}

	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		String parametroNome = ex.getParameter().getParameterName();
		Object parametroValor = ex.getValue();
		
		String tipoEsperado = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName()
				: "desconhecido";
		String detail = "O parâmetro de URL: '" + parametroNome + "' recebeu o valor '" + parametroValor +
				"' cujo é invalido, Corrija novamente com um valor do tipo '" + tipoEsperado + "'";
		
		Problema problema = createProblemaBuilder(HttpStatus.valueOf(status.value()),
				problemType, detail).build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}
		
		if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}
		
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido, verifique erro de sintaxe.";
		
		Problema problema = createProblemaBuilder(HttpStatus.valueOf(status.value()), problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
			
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "A propriedade '" + path + "' não existe, Corrija e tente"
				+ " uma existente.";
		
		Problema problema = createProblemaBuilder(HttpStatus.valueOf(status.value()), problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}


	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		String detail = "A propriedade " + path + " recebeu o valor " + ex.getValue() +
				" que é de um tipo inválido, Corrija e informe um valor compatível com o tipo "
				+ ex.getTargetType().getSimpleName();
		
		Problema problema = createProblemaBuilder(HttpStatus.valueOf(status.value()), problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}


	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = ex.getMessage();
		
		
		Problema problema = createProblemaBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
			WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		Problema problema = createProblemaBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
		
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex,
			WebRequest request) {
		
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		
		Problema problema = createProblemaBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		
		if (body == null) {
			HttpStatus status = HttpStatus.valueOf(statusCode.value());
			
			body = Problema.builder()
					.status(status.value())
					.title(status.getReasonPhrase())
					.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.build();
			
			
		}
		
		else if (body instanceof String) {
			HttpStatus status = HttpStatus.valueOf(statusCode.value());
			
			body = Problema.builder()
					.status(status.value())
					.title(status.getReasonPhrase())
					.userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
					.build();
			
		}
		
		return super.handleExceptionInternal(ex, body, headers, statusCode, request);
	}
	
	private Problema.ProblemaBuilder createProblemaBuilder(HttpStatus status, ProblemType problemType,
			String detail) {
		
		return Problema.builder()
				.title(problemType.getTitle())
				.type(problemType.getUri())
				.status(status.value())
				.detail(detail)
				.timestamp(LocalDateTime.now());
		
	}
	
}
