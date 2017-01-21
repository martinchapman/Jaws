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
 * A class to represent the last point of contact with a given shark.
 * 
 * @author Martin
 *
 */
public class Ping {

	private String name;
	
	private String time;
	
	/**
	 * Constructs a new Ping object with the given shark name
	 * and the time of the ping
	 * 
	 * @param name The name of the shark
	 * @param time The time of the ping
	 */
	public Ping( String name, String time ) {
		
		this.name = name;
		
		this.time = time;
		
	}

	/**
	 * @return The name of the shark to which the ping pertains
	 */
	public String getName() {
	
		return name;
	
	}

	/**
	 * @return The time at which the ping was received
	 */
	public String getTime() {
		
		return time;
	
	}
	
	public String toString() {
		
		return name + " " + time;
		
	}

}