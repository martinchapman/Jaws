package api;

import ripley.Incident;
import ripley.Ripley;

public class Main {

	/**
	 * Test main.
	 */
	public Main() {
	
		Ripley ripley = new Ripley("90tMKXOTsNiyVD6ql2OMtA==", "lBgh6Jdt9AjVqL46EnH7ew==" );
		
		for ( Incident incident : ripley.getIncidentsInRange("2017-01-00 00:00", "2017-03-00 00:00") ) {
			
			System.out.println(incident.getState() + " " + incident.getIncidentID());
			ripley.getIncidentDetails(incident.getIncidentID());
			
			
		}

	}
	
	public static void main( String[] args ) {
		
		new Main();
		
	}

}