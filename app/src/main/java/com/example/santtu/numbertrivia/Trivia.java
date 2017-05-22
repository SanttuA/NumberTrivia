package com.example.santtu.numbertrivia;

/**
 * Holds data for a single number trivia
 */

public class Trivia {


    private String text;    //the trivia
    private boolean found;  //was any trivia found for this number
    private float number;   //the number the trivia is for
    private String type;    //the type of trivia (trivia, math, date, year)
    private String date;    //a day sometimes associated with some of the year facts
    private String year;    //a year sometimes associated with some date facts

    public Trivia()
    {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
