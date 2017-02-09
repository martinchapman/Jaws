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
	
	public ArrayList<Incident> getIncidentsInRange(String start, String end) {
		
		try {
			
			ArrayList<Incident> incidents = new ArrayList<Incident>();
			
			for ( JSONObject data : Utils.arrayToObjectList( connection.sendRESTfulQuery("/range/" + start + "/" + end) ) ) {
				
				incidents.add(new Incident(data.getString("primary_key"), data.getString("dateandtime"), data.getString("city"), data.getString("state"), data.getString("shape"), data.getString("duration"), data.getString("summary"), data.getString("posted")));
			
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
			
			if ( incidentDetails.length() > 0 ) {
				
				return incidentDetails.getJSONObject(0).get("description").toString();
			
			} else {
				
				return "No details available";
				
			}
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
		
			System.out.println("API Error: Line 79");
		
			return null;
		
		}
		
	}
	
	// Do map of states to full names as bonus in source.
	
	public int getStartYear() {
		
		return 1400;
		
	}
	
	public int getLatestYear() {
		
		return 2017;
		
	}
	
	public String getLastUpdated() {
			
		try {
				
			JSONArray lastUpdated = connection.sendRESTfulQuery("/updated");
			
			return lastUpdated.getJSONObject(0).get("grabbed") + "";
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
					
			System.out.println("API Error: Line 295");
			
			return null;
			
		}
		
	}
	
	public static String getAcknowledgementString() {
		
		return "All the data contained within this program is the property of NUFORC (National UFO Reporting Centre).";
		
	}
	
}