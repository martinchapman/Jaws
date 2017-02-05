package api;

import ripley.Ripley;

public class Main {

	public Main() {
	
		Ripley ripley = new Ripley("90tMKXOTsNiyVD6ql2OMtA==", "lBgh6Jdt9AjVqL46EnH7ew==", false );
		
		System.out.println(ripley.getAllIncidents());

	}
	
	public static void main( String[] args ) {
		
		new Main();
		
	}

}