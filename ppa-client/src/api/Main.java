package api;

import ripley.Ripley;

public class Main {

	public Main() {
		
		Ripley ripley = new Ripley( "abc", "abc" );
		
		ripley.getAllIncidents();

	}
	
	public static void main( String[] args ) {
		
		new Main();
		
	}

}