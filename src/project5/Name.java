package project5;

/**
 * The Name class acts as an object for a name for a specific year
 * It stores the name, count, and gender for the name
 * @author Joshua Donelly-Higgins
 */
public class Name implements Comparable<Name> {

    private int count;
    private String name;
    private String gender;
    private String county;

    /**
     * Constructs a new Name object
     * @param name String name in question
     * @param gender either "m" or "f"
     * @param count int number of people with that name
     */
    public Name (String name, String gender, int count, String county) {
        setGender(gender);
        setName(name);
        setCount(count);
        setCounty(county);
    }

    /**
     * getter for count
     * @return the int count attribute
     */
    public int getCount () {
        return count;
    }

    /**
     * getter for name
     * @return the String name attribute
     */
    public String getName () {
        return name;
    }

    /**
     * getter for gender
     * @return the String gender attribute, either "m" or "f"
     */
    public String getGender () {
        return gender;
    }

    /**
     * getter for county
     * @return the String county attribute
     */
    public String getCounty () { return county; }

    /**
     * setter for name, setting the name to lowercase text
     * @param name String name input
     */
    public void setName(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Invalid name argument given.");
        }
        this.name = name.toLowerCase();
    }

    /**
     * setter for gender, setting the gender to either "m", "F", "f", or "M"
     * @param genderArg String name input
     */
    public void setGender(String genderArg) {
        if (genderArg == null || (!genderArg.toLowerCase().equals("m") && !genderArg.toLowerCase().equals("f"))) {
            throw new IllegalArgumentException("Invalid gender argument given.");
        } else {
            this.gender = genderArg.toLowerCase();
        }
    }

    /**
     * setter for count; only accepts values that are positive or 0
     * @param count int count input
     */
    public void setCount(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Invalid count argument given.");
        } else {
            this.count = count;
        }
    }

    /**
     * setter for county, setting the county to lowercase text
     * @param county String county input
     */
    public void setCounty(String county) {
        if (county == null || county.equals("")) {
            throw new IllegalArgumentException("Invalid county argument given.");
        }
        this.county = county.toLowerCase();
    }

    /**
     * equals method that overrides default
     * if the name, gender, and count fields of an object are equal to this object, returns true
     * otherwise returns false
     * @param obj the object to test
     * @return a boolean true if they are equal and false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Name)) return false;
        Name other = (Name) obj;
        if (other.count == this.count && other.name.equals(this.name) && other.gender.equals(this.gender)) {
            return true;
        }
        return false;
    }

    /**
     * Compares two Name objects, overrides default.
     * key precedence is:
     * name (alphabetical)
     * county (alphabetical)
     * count (numerical)
     * gender (alphabetical)
     * 0 indicates an equal comparison
     * a positive int indicates that this is before o
     * a negative int indicates that o is before this
     * @param o the Name object being compared to
     * @return an int, positive (before), negative (after), or 0 (equal)
     */
    @Override
    public int compareTo(Name o) {
        if (o == null || this == null) return 0; //null case
        if (this.equals(o)) return 0;
        if (!o.getName().equals(getName())) {
            return getName().compareTo(o.getName());
        } else if (!o.getCounty().equals(getCounty())) {
            return getCounty().compareTo(o.getCounty());
        } else if (o.getCount() != getCount()) {
            return getCount() - o.getCount();
        } else if (!getGender().equals(o.getGender())) {
            return (int) o.getGender().charAt(0) - (int) getGender().charAt(0);
        }
        return 0;
    }

    /**
     * Converts a Name object to a string
     * @return "Name [name] is gender [gender], with count [count]."
     */
    @Override
    public String toString() {
        return String.format("Name %s is gender %s, with count %d, belonging to county %s.", getName(), getGender(), getCount(), getCounty());
    }
}
