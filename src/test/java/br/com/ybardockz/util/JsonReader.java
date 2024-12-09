package br.com.ybardockz.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonReader {
	
	public static String getContentFromResource(String resourceName) {
    	try {
	        // Obtém o arquivo como InputStream
    		InputStream inputStream = JsonReader.class.getClassLoader().getResourceAsStream(resourceName);
	
	        // Converte o InputStream em String
	        String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
	        
	        return json;
	        
    	} catch (IOException e) {
    		throw new RuntimeException(e);
    	}
    }
}
