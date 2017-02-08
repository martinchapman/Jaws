package ripley;

public class Incident {
	
	private String incidentID, dateAndTime, city, state, shape, duration, summary, posted;
	
	public String getIncidentID() {
		
		return incidentID;
		
	}
	
	public String getDateAndTime() {
		
		return dateAndTime;
	
	}

	public String getCity() {
	
		return city;
	
	}

	public String getState() {
	
		return state;
	
	}

	public String getShape() {
	
		return shape;
	
	}

	public String getDuration() {
	
		return duration;
	
	}

	public String getSummary() {
	
		return summary;
	
	}

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
	
	public String toString() {
		
		return incidentID + " " + dateAndTime + " " + city + " " + state + " " + shape + " " + duration + " " + summary + " " + posted;
		
	}

}
