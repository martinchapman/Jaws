package api.ripley;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.json.JSONArray;

/**
 * @author Martin
 *
 */
public final class Connection {
	
	private String a = "Decompiling my JAR file. Terrible.";

	private String b = "I'm actually impressed.";

	private String c = "I've left a treat for you somewhere in this source...";

	private static final String URL = "px205.dcs.kcl.ac.uk";
	
	private String privateKey;
	
	private String publicKey;
	
	protected Connection( String privateKey, String publicKey ) {
		
		this.privateKey = privateKey;
		
		this.publicKey = publicKey;
	
	}
	
	protected JSONArray sendRESTfulQuery( String query ) throws NoSuchAlgorithmException, KeyManagementException {
		
		query = query.replace(" ", "_");
		
		try {
			
	        String queryHash = Utils.hmacSha1(query, privateKey);
	        
	        // Query also passed in post purely for validation.
	        String rawData = "public_key=" + publicKey + "&query_hash=" + queryHash + "&query=" + query;
	        
	        byte[] postData = rawData.getBytes( StandardCharsets.UTF_8 );
	        
	        int postDataLength = postData.length;
	        
	        HttpURLConnection con;
	        
	        if ( URL.contains("localhost") ) {
	        	
				// Appending query helps identify route on server.
				URL url = new URL("http://" + URL + ":8080" + query);
				
				con = (HttpURLConnection)url.openConnection();
				
	        } else {
	        	
				URL url = new URL("http://" + URL + ":8080" + query);
				
			    SSLSocketFactory sslFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
	
			    con = (HttpURLConnection)url.openConnection();
			    
			   // ((HttpURLConnection)con).setSSLSocketFactory(sslFactory);
			    
	        }
	        
	        con.setRequestMethod("POST");
	        
	        con.setDoOutput(true);
		    
	        try ( DataOutputStream wr = new DataOutputStream( con.getOutputStream()) ) {
	        
	        	wr.write( postData );
	        
	        }
		    
	        if ( ((HttpURLConnection) con).getResponseCode() != 200 ) {
	        	
	        	System.err.println("API Response Error Code: " + ((HttpURLConnection) con).getResponseCode());
	        
	        }
	        
		    return Utils.processJSONInputStream( con.getInputStream() );
		    
		    
		} catch (MalformedURLException e) {
		    
			e.printStackTrace();
		
		} catch (IOException e) {
		    
			e.printStackTrace();
		
		}
		
		return null;
	
	}

}
