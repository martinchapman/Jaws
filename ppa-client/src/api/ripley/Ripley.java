package api.ripley;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A local proxy library designed to facilitate communication with a
 * remote API distributing UFO sighting data.
 * 
 * @author Martin
 *
 */
public class Ripley {

	/**
	 * The connection to the remote API.
	 */
	private Connection connection;
	
	/**
	 * This library accesses a closed, remote API, for which
	 * a private and public key are needed.
	 * 
	 * @param privateKey Your group's unique private key
	 * @param publicKey Your group's unique public key
	 */
	public Ripley( String privateKey, String publicKey ) {
		
		System.err.println("You are using a temporary version of the Ripley library, please switch back to the production version (in your repo), prior to submission");
		connection = new Connection( privateKey, publicKey );
		
	}
	
	/**
	 * Provides a list of UFO sighting incidents during a given time period.
	 * 
	 * @param start The start date from which to collect incidents (format: yyyy-MM-dd HH:mm:ss)
	 * @param end The end date to which to collect incidents (format: yyyy-MM-dd HH:mm:ss)
	 * @return A list of the incidents that have occurred during this timeframe @see api.ripley.Incident
	 */
	public ArrayList<Incident> getIncidentsInRange(String start, String end) {
		
		try {
			
			LocalDate date = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		} catch ( DateTimeParseException e ) {
			
			System.err.println("Start time not in expected format. Expected: yyyy-MM-dd HH:mm:ss");
			
			return null;
			
		}
		
		try {
			
			LocalDate date = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		} catch ( DateTimeParseException e ) {
			
			System.err.println("End time not in expected format. Expected: yyyy-MM-dd HH:mm:ss");
			
			return null;
			
		}
		
		try {
			
			ArrayList<Incident> incidents = new ArrayList<Incident>();
			
			for ( JSONObject data : Utils.arrayToObjectList( connection.sendRESTfulQuery("/range/" + start + "/" + end) ) ) {
				
				incidents.add(new Incident(data.getString("primary_key"), data.getString("dateandtime"), data.getString("city"), data.getString("state"), data.getString("shape"), data.getString("duration"), data.getString("summary"), data.getString("posted")));
			
			}
			
			return incidents;
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			
			System.out.println("API Error: Line 86");
			
			return null;
			
		}
		
	}
	
	/**
	 * Provides additional decriptive details of a UFO sighting incident.
	 * 
	 * @param id The unique incident ID.
	 * @return The details of this incident.
	 */
	public String getIncidentDetails(String id) {
		
		try {
		
			JSONArray incidentDetails = connection.sendRESTfulQuery("/individual/" + id);
			
			if ( incidentDetails.length() > 0 ) {
				
				return incidentDetails.getJSONObject(0).get("description").toString();
			
			} else {
				
				return "No details available";
				
			}
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
		
			System.out.println("API Error: Line 118");
		
			return null;
		
		}
		
	}
	
	/**
	 * @return The current API version.
	 */
	public double getVersion() {
		
		return -1.0;
		
	}
	
	/**
	 * @return The first year from which incident records are available.
	 */
	public int getStartYear() {
		
		return 1561;
		
	}
	
	/**
	 * @return The last year to which incident records are available.
	 */
	public int getLatestYear() {
		
		return 2017;
		
	}
	
	/**
	 * @return The last time a set of incident details were received.
	 */
	public String getLastUpdated() {
			
		try {
				
			JSONArray lastUpdated = connection.sendRESTfulQuery("/updated");
			
			if ( lastUpdated.length() > 0 ) {
				
				return lastUpdated.getJSONObject(0).get("grabbed") + "";
				
			} else {
				
				return null;
				
			}
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
					
			System.out.println("API Error: Line 169");
			
			return null;
			
		}
		
	}
	
	/**
	 * @return An acknowledgement string to be shown by all programs using this library, and the 
	 * associated API.
	 */
	public String getAcknowledgementString() {
		
		return "All of the data contained within this program is the property of NUFORC (National UFO Reporting Centre): http://www.nuforc.org/.";
		
	}
	
private String[] treat = { 
"Alabama,Ala.,AL",
"Alaska,Alaska,AK",
"American,Samoa,,,AS",
"Arizona,Ariz.,AZ",
"Arkansas,Ark.,AR",
"California,Calif.,CA",
"Colorado,Colo.,CO",
"Connecticut,Conn.,CT",
"Delaware,Del.,DE",
"Dist.,of,Columbia,D.C.,DC",
"Florida,Fla.,FL",
"Georgia,Ga.,GA",
"Guam,Guam,GU",
"Hawaii,Hawaii,HI",
"Idaho,Idaho,ID",
"Illinois,Ill.,IL",
"Indiana,Ind.,IN",
"Iowa,Iowa,IA",
"Kansas,Kans.,KS",
"Kentucky,Ky.,KY",
"Louisiana,La.,LA",
"Maine,Maine,ME",
"Maryland,Md.,MD",
"Marshall,Islands,,,MH",
"Massachusetts,Mass.,MA",
"Michigan,Mich.,MI",
"Micronesia,,,FM",
"Minnesota,Minn.,MN",
"Mississippi,Miss.,MS",
"Missouri,Mo.,MO",
"Montana,Mont.,MT",
"Nebraska,Nebr.,NE",
"Nevada,Nev.,NV",
"New_Hampshire,N.H.,NH",
"New_Jersey,N.J.,NJ",
"New_Mexico,N.M.,NM",
"New_York,N.Y.,NY",
"North_Carolina,N.C.,NC",
"North_Dakota,N.D.,ND",
"Northern_Marianas,,MP",
"Ohio,Ohio,OH",
"Oklahoma,Okla.,OK",
"Oregon,Ore.,OR",
"Palau,,PW",
"Pennsylvania,Pa.,PA",
"Puerto,Rico,P.R.,PR",
"Rhode,Island,R.I.,RI",
"South_Carolina,S.C.,SC",
"South_Dakota,S.D.,SD",
"Tennessee,Tenn.,TN",
"Texas,Tex.,TX",
"Utah,Utah,UT",
"Vermont,Vt.,VT",
"Virginia,Va.,VA",
"Virgin_Islands,V.I.,VI",
"Washington,Wash.,WA",
"West,Virginia,W.Va.,WV",
"Wisconsin,Wis.,WI",
"Wyoming,Wyo.,WY",
};
	
}