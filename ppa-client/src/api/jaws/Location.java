package api.jaws;

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
 * A class to represent a location with a longitude and a latitude value.
 * 
 * @author Martin
 *
 */
public class Location {

	private double longitude;
	
	private double latitude;
	
	/**
	 * Constructs a new location object with given longitude
	 * and latitude values
	 * 
	 * @param longitude
	 * @param latitude
	 */
	public Location( double longitude, double latitude ) {
		
		this.longitude = longitude;
		
		this.latitude = latitude;
		
	}

	/**
	 * @return The logitude value
	 */
	public double getLongitude() {
		
		return longitude;
	
	}

	/**
	 * @return The latitude value
	 */
	public double getLatitude() {
		
		return latitude;
		
	}
	
	public String toString() {
		
		return longitude + " " + latitude;
		
	}

}