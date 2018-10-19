package project5;

import java.util.ArrayList;

/**
 * An extension of AVLTree that stores Name objects for a given year
 * @author Joshua Donelly-Higgins
 */
public class YearNames extends AVLTree<Name> implements Comparable<YearNames> {
    private int year;
    private int totalCount;

    /**
     * Constructor for a YearNames object
     * @param year the year specific to this YearNames object, between 1900 and 2018 inclusive
     */
    public YearNames(int year) {
        setYear(year);
        setTotalCount(0);
    }

    /**
     * Setter for year
     * Allows ints between 1900 and 2018 inclusive
     * @param year the input year
     */
    public void setYear(int year) {
        if (1899 < year && year < 2019) {
            this.year = year;
        } else {
            throw new IllegalArgumentException("Year was not valid.");
        }
    }

    /**
     * Getter for year
     * @return year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Getter for totalCount
     * @return totalCount
     */
    public int getTotalCount() {
        return this.totalCount;
    }

    /**
     * setter for totalCount
     * @param count input count
     */
    public void setTotalCount(int count) {
        this.totalCount = count;
    }

    /**
     * Adds a name to the list and updates the totalcount
     * Will throw an IllegalArgumentException if the name already exists
     * @param name the Name object to be added
     */
    public void add(Name name) {
        super.add(name);
        totalCount += name.getCount();
    }

    /**
     * Gets the total count by name
     * Includes all entries for that name, as the name can have multiple valid entries
     * If name doesn't exist, returns 0
     * @param name String input name
     * @return the total count for entries with that name
     */
    public int getCountByName (String name) {
        int currentCount = 0;
        for (Name element : searchName(name)) {
            currentCount += element.getCount();
        }
        return currentCount;
    }

    /**
     * Gets the total count for a county
     * if county.equals("all"), get totalCount
     * To get total count, the entire tree must be traversed as Name is compared primarily by name
     * @param county String input county
     * @return total count for a county
     */
    public int getTotalCountCounty (String county) {
        if (county.equals("all")) {return getTotalCount();}

        int currentCount = 0;
        for (Name element : searchCounty(county)) {
            currentCount += element.getCount();
        }
        return currentCount;

    }

    /**
     * Gets the total count by name within a county
     * Includes all entries for a name within a county
     * If name doesn't exist within county, return 0
     * if county.equals("all"), get totalCount
     * @param name String input name
     * @param county String input county
     * @return the total count for entries with that county
     */
    public int getCountByNameCounty (String name, String county) {
        if (county.equalsIgnoreCase("all")) {return getCountByName(name);}

        int currentCount = 0;
        for (Name element : searchNameCounty(name, county)) {
            currentCount += element.getCount();
        }
        return currentCount;
    }

    /**
     * Gets the fraction of total names for the name given
     * @param name the name that acts as the numerator
     * @return double from 0 to 1
     */
    public double getFractionByName (String name) {
        return ((double) getCountByName(name) / getTotalCount());
    }

    /**
     * Gets the fraction of babies born with a name within a county for a year
     * @param name String input name
     * @param county String input county
     * @return double from 0 to 1
     */
    public double getFractionByNameCounty (String name, String county) {
        int totalCounty = getTotalCountCounty(county);
        if (totalCounty == 0) return 0;
        return ((double) getCountByNameCounty(name, county) / totalCounty);
    }

    /**
     * Overrides default equals method
     * Objects are equal if their year is equal, irrespective of other attributes
     * @param obj the object to compare
     * @return boolean true if equal, false if not equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof YearNames)) {
            return false;
        }
        YearNames other = (YearNames) obj;
        if (other.getYear() == getYear()) {
            return true;
        }
        return false;
    }

    /**
     * Returns a String representation of YearNames
     * @return "List of names for year [year]"
     */
    @Override
    public String toString() {
        return String.format("List of names for year %d", getYear());
    }

    /**
     * Overrides compareTo method
     * @param o the YearNames object to compare to
     * @return the year of this minus the year of the parameter o
     */
    @Override
    public int compareTo(YearNames o) {
        return this.getYear() - o.getYear();
    }

    /**
     * Search for element in AVLTree; return arraylist of values with names that match
     * @param name Name to search for
     * @return ArrayList of Name values that match
     */
    private ArrayList<Name> searchName(String name){
        return searchName(name, this.root, new ArrayList<>());
    }

    /**
     * Search for element in AVLTree; return arraylist of values with counties that match
     * @param county County to search for
     * @return ArrayList of Name values that match
     */
    private ArrayList<Name> searchCounty(String county){
        return super.searchCounty(county, this.root, new ArrayList<>());
    }

    /**
     * Search for element in AVLTree; return arraylist of values with names and counties that match
     * filters name nodes for correct county
     * @param name Name to search for
     * @return ArrayList of Name values that match
     */
    private ArrayList<Name> searchNameCounty(String name, String county){
        ArrayList<Name> output = new ArrayList<>();
        for (Name nameVal : searchName(name, this.root, new ArrayList<>())) {
            if (nameVal.getCounty().equals(county)) output.add(nameVal);
        }
        return output;
    }
}
