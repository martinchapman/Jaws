package ripley;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/*
 * Uh-oh. You've decompiled this class file, and in doing so you are in
 * breach of exam regulations, and risk forfeiting your grade
 * for the major coursework assignment.
 * 
 * Only joking. I've commented (some of) the source, so while 
 * you're here hopefully you can learn something :-)
 * 
 * Unfortunately, none of this code can be included in your solution,
 * as your solution will therefore not be original, and you may be
 * subject to plagiarism sanctions.
 */
/**
 * Contains the main functionality required to communicate with the
 * Jaws remote API. 
 * 
 * @author Martin
 *
 */
public class Ripley {

	private Connection connection;
	
	/**
	 * Links objects of this class to the Jaws remote API. Requires
	 * a pre-validated private and public key. Connections sent over
	 * SSL are recommended, but if performance degrades, this parameter
	 * should be set to false.
	 * 
	 * @param privateKey Pre-validated private key
	 * @param publicKey Pre-validated public key
	 * @param useSSL Whether to issue commands using a secure connection
	 */
	public Ripley( String privateKey, String publicKey, boolean useSSL ) {
		
		connection = new Connection( privateKey, publicKey, useSSL );
		
	}
	
	/**
	 * Links objects of this class to the Jaws remote API. Requires
	 * a pre-validated private and public key. Connections are sent over SSL
	 * by default.
	 * 
	 * @param privateKey
	 * @param publicKey
	 */
	public Ripley( String privateKey, String publicKey ) {
		
		connection = new Connection( privateKey, publicKey, true );
		
	}
	
	/******************
	 * Methods
	 * 
	 * 
	 * 
	 */
	
	/**
	 * Returns the list of shark names currently in the dataset.
	 *  
	 * @return A list of shark names
	 */
	public ArrayList<String> getSharkNames() {
		
		try {
			
			return Utils.keyPairResultToList( connection.sendRESTfulQuery("/names") );
			
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			
			System.out.println("API Error: Line 79");
			
			return null;
			
		}
	
	}
	
	/**
	 * Returns an object containing all pertinent details about a shark.
	 * 
	 * @param name The name of the shark for which you wish to gather data
	 * @return An object containing pertinent details about a shark
	 */
	public Shark getShark( String name ) {
		
		try {
			
			JSONArray sharkInfo = connection.sendRESTfulQuery("/names/" + name);
			
			return new Shark( name, sharkInfo.getJSONObject(0).get("species") + "", sharkInfo.getJSONObject(0).get("stageOfLife") + "", sharkInfo.getJSONObject(0).get("gender") + "", sharkInfo.getJSONObject(0).get("length") + "", sharkInfo.getJSONObject(0).get("weight") + "", sharkInfo.getJSONObject(0).get("tagLocation") + "", sharkInfo.getJSONObject(0).get("description").toString().replaceAll("\\P{Print}", ""));
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			
			System.out.println("API Error: Line 103");
			
			return null;
			
		}
		
	}

	/**
	 * Returns the name and contact time for those
	 * sharks that have been detected in the past 24 hours.
	 * 
	 * @return A list of names and timestamps
	 */
	public ArrayList<Ping> past24Hours() {
		
		try {
			
			return historyNames("day");
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			
			System.out.println("API Error: Line 125");
			
			return null;
			
		}
		
	}
	
	/**
	 * Returns the name and contact time for those
	 * sharks that have been detected in the past week.
	 * 
	 * @return A list of names and timestamps
	 */
	public ArrayList<Ping> pastWeek() {
		
		try {
			
			return historyNames("week");
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			
			System.out.println("API Error: Line 147");
			
			return null;
			
		}
		
	}
	
	/**
	 * Returns the name and contact time for those
	 * sharks that have been detected in the past month.
	 * 
	 * Be warned, this method can return a large amount of data.
	 * 
	 * @return A list of names and timestamps
	 */
	public ArrayList<Ping> pastMonth() {
		
		try {
			
			return historyNames("month");
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			
			System.out.println("API Error: Line 171");
			
			return null;
			
		}
		
	}

	private ArrayList<Ping> historyNames( String duration ) throws KeyManagementException, NoSuchAlgorithmException {
		
		try {
			
			ArrayList<Ping> locations = new ArrayList<Ping>();
			
			for ( JSONObject data : Utils.arrayToObjectList( connection.sendRESTfulQuery("/recent/" + duration) ) ) {
				
				locations.add(new Ping(data.get("name") + "", data.get("datetime") + ""));
				
			}
			
			return locations;
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			
			System.out.println("API Error: Line 195");
			
			return null;
			
		}
		
	}
	
	/**
	 * Returns the last known location of a shark.
	 * 
	 * @param name The name of the shark to locate
	 * @return The location of the shark
	 */
	public Location getLastLocation( String name ) {
		
		try {
			
			JSONArray locationInfo = connection.sendRESTfulQuery("/names/" + name + "/location");
			
			return new Location(Double.parseDouble(locationInfo.getJSONObject(0).get("longitude") + ""), Double.parseDouble(locationInfo.getJSONObject(0).get("latitude") + ""));
			
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			
			System.out.println("API Error: Line 219");
			
			return null;
			
		}
		
	}
	
	/**
	 * Returns a link to a video of a given shark, if one exists.
	 * 
	 * @param name Name of the shark to find a video URL for
	 * @return Video URL
	 */
	public String getVideo( String name ) {
		
		try {
			
			JSONArray videoInfo = connection.sendRESTfulQuery("/names/" + name + "/video");
			
			if ( videoInfo.length() > 0 ) {
				
				return videoInfo.getJSONObject(0).get("url") + "";
			
			} else {
				
				return "No footage available for this shark :-(";
				
			}
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
					
			System.out.println("API Error: Line 251");
			
			return null;
			
		}
	
	}
	
	/**
	 * Returns a list of the locations in which the sharks in the dataset were tagged.
	 * 
	 * @return List of tag locations
	 */
	public ArrayList<String> getTagLocations() {
		
		try {
			
			return Utils.keyPairResultToList( connection.sendRESTfulQuery("/tag_locations") );
			
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			
			System.out.println("API Error: Line 272");
			
			return null;
			
		}
		
	}
	
	/**
	 * Returns the last time the content in the Jaws API dataset was updated.
	 * 
	 * @return Update time
	 */
	public String getLastUpdated() {
		
		try {
			
			JSONArray videoInfo = connection.sendRESTfulQuery("/grabbed");
			
			return videoInfo.getJSONObject(0).get("grabbed") + "";
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
					
			System.out.println("API Error: Line 295");
			
			return null;
			
		}
		
	}
	
	/**
	 * Returns the acknowledgement string that must be displayed by all applications
	 * that use the Jaws API.
	 * 
	 * @return Acknowledgement string
	 */
	public String getAcknowledgement() {
		
		return "All the information used in this application is the property of Ocearch (http://www.ocearch.org/) and CAT (http://www.cat.com/). Thanks to EPMF.";
		
	}
	
	/******************
	 * Not used ( and associated API calls deactivated ;-) )
	 * 
	 * 
	 * 
	 */

	private ArrayList<Location> getLocations( String name ) {
		
		try {
			
			ArrayList<Location> locations = new ArrayList<Location>();
			
			for ( JSONObject data : Utils.arrayToObjectList( connection.sendRESTfulQuery("/names/" + name + "/locations") ) ) {
				
				locations.add(new Location(Double.parseDouble(data.get("longitude") + ""), Double.parseDouble(data.get("latitude") + "")));
				
			}
			
			return locations;
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			
			System.out.println("API Error: Line 220");
			
			return null;
			
		}
		
	}
	
	private ArrayList<Shark> history( String duration ) {
		
		try {
		
			ArrayList<Shark> sharks = new ArrayList<Shark>();
			
			for ( String data : Utils.keyPairResultToList( connection.sendRESTfulQuery("/recent/" + duration) ) ) {
				
				sharks.add( getShark(data) );
				
			}
			
			return sharks;
		
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			
			System.out.println("API Error: Line 337");
			
			return null;
			
		}
		
	}
	
}