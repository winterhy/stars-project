package edu.brown.cs.student.stars;

import java.util.List;

/**
 * This is a star object. Includes ID, Name, and Coordinates
 */
public class Star {

    public Number ID;
    public String Name;
    public List<Number> Coordinates;

    /**
     * Takes a Number, a String, and a List of Numbers
     * and constructs a Star object
     *
     * @param ID
     * @param Name
     * @param Coordinates
     */
    public Star(Number ID, String Name, List<Number> Coordinates) {
        this.ID = ID;
        this.Name = Name;
        this.Coordinates = Coordinates;
    }


}
