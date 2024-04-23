package horserace;

import horserace.utils.Decimal;

/**
 * Write a description of class HorseRace.Horse here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Horse
{
    //Fields of class HorseRace.Horse
    private final String name;
    private char horseSymbol;
    private int distanceTravelled;
    private boolean hasFallen;
    private double horseConfidence;
      
    //Constructor of class HorseRace.Horse

    /**
     * @param horseSymbol a unicode symbol to represent the horse
     * @param horseName the name of the horse
     * @param horseConfidence a confidence value between 0.0 and 1.0
     */

    public Horse(char horseSymbol, String horseName, double horseConfidence) {
     this.name = horseName;
     this.horseSymbol = horseSymbol;
     this.horseConfidence = Decimal.validateValue(horseConfidence);
    }

    //Other methods of class HorseRace.Horse
    public void fall()
    {
        hasFallen = true;
    }
    
    public double getHorseConfidence()
    {
     return horseConfidence;
    }
    
    public int getDistanceTravelled()
    {
        return distanceTravelled;
    }
    
    public String getName()
    {
        return name;
    }
    
    public char getSymbol()
    {
     return horseSymbol;
    }
    
    public void goBackToStart()
    {
        distanceTravelled = 0;
    }
    
    public boolean hasFallen()
    {
        return hasFallen;
    }

    public void moveForward()
    {
        distanceTravelled += 1;
    }

    public void setHorseConfidence(double newConfidence)
    {
        horseConfidence = Decimal.validateValue(newConfidence);
    }
    
    public void setSymbol(char newSymbol)
    {
        horseSymbol = newSymbol;
    }
    
}
