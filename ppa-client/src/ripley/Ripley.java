package ripley;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ripley {

	private Connection connection;
	
	public Ripley( String privateKey, String publicKey, boolean useSSL ) {
		
		connection = new Connection( privateKey, publicKey, useSSL );
		
	}
	
	public Ripley( String privateKey, String publicKey ) {
		
		connection = new Connection( privateKey, publicKey, true );
		
	}
	
	public ArrayList<Incident> getAllIncidents() {
		
		try {
			
			ArrayList<Incident> incidents = new ArrayList<Incident>();
			
			for ( JSONObject data : Utils.arrayToObjectList( connection.sendRESTfulQuery("/all") ) ) {
				
				incidents.add(new Incident(data.toString()));
			
			}
			
			//return Utils.keyPairResultToList( connection.sendRESTfulQuery("/all") );
			
			/*JSONArray sharkInfo = connection.sendRESTfulQuery("/names/" + name);
			
			return new Shark( name, sharkInfo.getJSONObject(0).get("species") + "", sharkInfo.getJSONObject(0).get("stageOfLife") + "", sharkInfo.getJSONObject(0).get("gender") + "", sharkInfo.getJSONObject(0).get("length") + "", sharkInfo.getJSONObject(0).get("weight") + "", sharkInfo.getJSONObject(0).get("tagLocation") + "", sharkInfo.getJSONObject(0).get("description").toString().replaceAll("\\P{Print}", ""));*/
			
			return incidents;
			
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			
			System.out.println("API Error: Line 79");
			
			return null;
			
		}
	
	}
	
}