package api;

import api.jaws.Jaws;

public class Main {

	public Main() {
		
		Jaws jaws = new Jaws( "<PrivateKey>", "<PublicKey>", true );

	}
	
	public static void main( String[] args ) {
		
		new Main();
		
	}

}