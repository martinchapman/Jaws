package api.ripley;

/**
 * Supplies data on an alien sighting incident.
 * 
 * @author Martin
 *
 */
public class Incident {
	
	private String incidentID, dateAndTime, city, state, shape, duration, summary, posted;
	
	/**
	 * @return The unique ID of the incident.
	 */
	public String getIncidentID() {
		
		return incidentID;
		
	}
	
	/**
	 * @return The time the incident took place.
	 */
	public String getDateAndTime() {
		
		return dateAndTime;
	
	}

	/**
	 * @return The city in which the incident took place.
	 */
	public String getCity() {
	
		return city;
	
	}

	/**
	 * @return The state in which the incident took place.
	 */
	public String getState() {
	
		return state;
	
	}

	/**
	 * @return The shape of the object observed during the incident.
	 */
	public String getShape() {
	
		return shape;
	
	}

	/**
	 * @return The duration of the incident.
	 */
	public String getDuration() {
	
		return duration;
	
	}

	/**
	 * @return An incomplete summary of the incident.
	 */
	public String getSummary() {
	
		return summary;
	
	}

	/**
	 * @return The date on which the incident was logged.
	 */
	public String getPosted() {
	
		return posted;
	
	}

	protected Incident(String incidentID, String dateAndTime, String city, String state, String shape, String duration, String summary, String posted) {
		
		this.incidentID = incidentID;
		
		this.dateAndTime = dateAndTime;
		
		this.city = city;
		
		this.state = state;
		
		this.shape = shape;
		
		this.duration = duration;
		
		this.summary = summary;
		
		this.posted = posted;
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		
		return incidentID + " " + dateAndTime + " " + city + " " + state + " " + shape + " " + duration + " " + summary + " " + posted;
		
	}

}
