package api.jaws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class Utils {

	private Utils() {}
	
	protected static String hmacSha1( String value, String key ) {
	    
		try {
        
            byte[] keyBytes = key.getBytes();           
            
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

            Mac mac = Mac.getInstance("HmacSHA1");
            
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(value.getBytes());

            byte[] hexBytes = new Hex().encode(rawHmac);

            return new String(hexBytes, "UTF-8");
        
		} catch (Exception e) {
        
			throw new RuntimeException(e);
        
		}
    
	}
	
	protected static String convertStreamToString(java.io.InputStream is) {
	    
	    try ( java.util.Scanner s = new java.util.Scanner(is) ) { 
	    	
	    	return s.useDelimiter("\\A").hasNext() ? s.next() : ""; 
	    	
	    }
	
	}
	
	protected static String readAll(Reader rd) throws IOException {
	    
		StringBuilder sb = new StringBuilder();
		
		int cp;
		
		while ((cp = rd.read()) != -1) {
			
			sb.append((char) cp);
		
		}
		
		return sb.toString();
  
	}
	
	protected static JSONArray processJSONInputStream( InputStream is ) throws IOException {
		
		try {
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			
			String jsonText = readAll(rd);
			
			if ( jsonText.contains("Authentication failed") ) {
				
				System.err.println("Authentication Failed.");
				
				return new JSONArray();
			
			}
			
			JSONArray json = new JSONArray(jsonText);
			
			return json;
		
		} finally {
			
			is.close();
		
		}
		
	}
	
	protected static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
		
		InputStream is = new URL(url).openStream();
		
		return processJSONInputStream(is);
		
	}
	
	protected static ArrayList<String> keyPairResultToList( JSONArray queryResult ) {
		
		ArrayList<String> results = new ArrayList<String>();
		
		for ( int i = 0; i < queryResult.length(); i++ ) {
	    	
			JSONObject jObject = queryResult.getJSONObject(i);
			
			Iterator<?> keys = jObject.keys();
			
			while ( keys.hasNext() ) {
				
				Object key = keys.next();
				
				results.add(jObject.get(key + "") + "");
				
			}
		}
		
		return results;
		
	}
	
	protected static ArrayList<JSONObject> arrayToObjectList( JSONArray queryResult ) {
		
		ArrayList<JSONObject> results = new ArrayList<JSONObject>();
		
		for ( int i = 0; i < queryResult.length(); i++ ) {
	    	
			JSONObject jObject = queryResult.getJSONObject(i);
			
			results.add(jObject);
			
		}
		
		return results;
		
	}

}