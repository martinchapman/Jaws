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
 * A class to represents all the pertinent details of a shark.
 * 
 * @author Martin
 *
 */
public final class Shark {

	/**
	 * 
	 */
	private String name, gender, stageOfLife, species, length, weight, tagLocation, description;
	
	/**
	 * @return The shark's name
	 */
	public String getName() {
	
		return name;
	
	}

	/**
	 * @return The shark's gender
	 */
	public String getGender() {
	
		return gender;
	
	}

	/**
	 * @return The shark's stage of life
	 */
	public String getStageOfLife() {
	
		return stageOfLife;
	
	}

	/**
	 * @return The species of shark
	 */
	public String getSpecies() {
	
		return species;

	}

	/**
	 * @return The length of the shark
	 */
	public String getLength() {
	
		return length;
	
	}
	
	/**
	 * @return The weight of the shark
	 */
	public String getWeight() {
	
		return weight;
	
	}

	/**
	 * @return The location in which the shark was tagged (had a tracking chip inserted)
	 */
	public String getTagLocation() {
	
		return tagLocation;
	
	}
	
	/**
	 * @return A description of the shark
	 */
	public String getDescription() {
		
		return description;
		
	}

	protected Shark ( String name, String species, String stageOfLife, String gender, String length, String weight, String tagLocation, String description ) { 
		
		this.name = name;
		
		this.species = species;
		
		this.stageOfLife = stageOfLife;

		this.gender = gender;
		
		this.length = length;
		
		this.weight = weight;
		
		this.tagLocation = tagLocation;
		
		this.description = description;
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		
		return name;
		
	}
	
}