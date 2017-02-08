package ripley;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ripley {

	private Connection connection;
	
	public Ripley( String privateKey, String publicKey ) {
		
		connection = new Connection( privateKey, publicKey );
		
	}
	
	public ArrayList<Incident> getAllIncidents() {
		
		try {
			
			ArrayList<Incident> incidents = new ArrayList<Incident>();
			
			for ( JSONObject data : Utils.arrayToObjectList( connection.sendRESTfulQuery("/all") ) ) {
				
				incidents.add(new Incident(data.getString("primary_key"), data.getString("DateAndTime"), data.getString("City"), data.getString("State"), data.getString("Shape"), data.getString("Duration"), data.getString("Summary"), data.getString("Posted")));
			
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
	
	public ArrayList<Incident> getIncidentsInRange(String start, String end) {
		
		try {
			
			ArrayList<Incident> incidents = new ArrayList<Incident>();
			
			for ( JSONObject data : Utils.arrayToObjectList( connection.sendRESTfulQuery("/range/" + start + "/" + end) ) ) {
				
				incidents.add(new Incident(data.getString("primary_key"), data.getString("DateAndTime"), data.getString("City"), data.getString("State"), data.getString("Shape"), data.getString("Duration"), data.getString("Summary"), data.getString("Posted")));
			
			}
			
			return incidents;
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			
			System.out.println("API Error: Line 79");
			
			return null;
			
		}
		
	}
	
	public String getIncidentDetails(String id) {
		
		try {
		
			JSONArray incidentDetails = connection.sendRESTfulQuery("/individual/" + id);
		
			return incidentDetails.getJSONObject(0).get("Description").toString();
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
		
			System.out.println("API Error: Line 79");
		
			return null;
		
		}
		
	}
	
	public int getStartYear() {
		
		return 1400;
		
	}
	
	public static String getAcknowledgementString() {
		
		return "All the data contained within this program is the property of NUFORC (National UFO Reporting Centre).";
		
	}
	
}