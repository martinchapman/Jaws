package api.jaws;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONArray;

/*
 * Uh-oh. You've decompiled this class file, and in doing so you are in
 * breach of exam regulations, and risk forfeiting your grade
 * for the major coursework assignment.
 * 
 * Only joking. I've commented (some of) the source, so while 
 * you're here hopefully you can learn something :-)
 */
public final class Connection {

	private String privateKey;
	
	private String publicKey;
	
	private boolean useSSL;
	
	protected Connection( String privateKey, String publicKey, boolean useSSL ) {
		
		this.privateKey = privateKey;
		
		this.publicKey = publicKey;
		
		this.useSSL = useSSL;
	
	}
	
	protected JSONArray sendRESTfulQuery( String query ) throws NoSuchAlgorithmException, KeyManagementException {
		
		query = query.replace(" ", "_");
		
		try {
			
			URL url;
			
			if ( useSSL ) {
				
				url = new URL("https://martinchapman.ddns.net/jaws/index.php" + query);
			
			} else {
				
				url = new URL("http://martinchapman.ddns.net/jaws/index.php" + query);
			
			}
			
	        TrustManager[] trustAllCerts = new TrustManager[] { 
	        		
	        	new X509TrustManager() {
	        
		        	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		            
		        		return null;
		            
		        	}
		            
		        	public void checkClientTrusted(X509Certificate[] certs, String authType) { }
		            
		        	public void checkServerTrusted(X509Certificate[] certs, String authType) { }
	        	
	            }
	        };
	 
	        SSLContext sc = SSLContext.getInstance("SSL");
	        
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	 
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	        	
	            public boolean verify(String hostname, SSLSession session) {
	            
	            	return true;
	            
	            }
	        };
	        
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			
	        // Hash the query using private key as authentication
	        String queryHash = Utils.hmacSha1(query, privateKey);
	        
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();           
	        
	        String rawData = "public_key=" + publicKey + "&query_hash=" + queryHash + "&query=" + query;
	        
	        byte[] postData = rawData.getBytes( StandardCharsets.UTF_8 );
	        
	        int postDataLength = postData.length;
	        
	        con.setDoOutput( true );
	        
	        con.setInstanceFollowRedirects( false );
	        
	        con.setRequestMethod( "POST" );
	        
	        con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
	        
	        con.setRequestProperty( "charset", "utf-8");
	        
	        con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
	        
	        con.setUseCaches( false );
	        
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
