package api;

import ripley.Incident;
import ripley.Ripley;

public class Main {

	/**
	 * Test main.
	 */
	public Main() {
	
		Ripley ripley = new Ripley("90tMKXOTsNiyVD6ql2OMtA==", "lBgh6Jdt9AjVqL46EnH7ew==" );
		
		System.out.println(ripley.getIncidentDetails("132S132079tmoce0ic54ivmnvn7ugghsi2mm"));

	}
	
	public static void main( String[] args ) {
		
		new Main();
		
	}

}