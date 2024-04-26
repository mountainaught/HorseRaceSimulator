package race.horse;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * Compared to the Horse class in Part 1, this class is different in nearly every way. The most fundamental improvement once
 * again complements the use of JavaFX, that being the entire Horse class extending a JavaFX Region object. This means
 * we can treat it as a graphical object, add and remove image layers as necessary and place the Horse object itself on
 * a Pane, or in this case a Lane (see Lane documentation - Lane extends Pane).
 * <p>
 * Additionally, the Horse no longer holds any methods pertaining to data processing. It only knows what it needs to,
 * that being Position, Name, whether it's Finished or Fallen, and similar properties. Methods within the horse are
 * entirely composed of Accessor methods, for retrieving and modifying data.
 * @author etunal
 * @version 1.2
 */
public class Horse extends Region {

    //Fields of class HorseRace.Horse
    private String name;
    private double position = 0;
    private boolean hasFallen;
    private boolean hasFinished;
    private double horseConfidence;

    private Image horseColor;
    private String horseColorName;

    final double centerX;
    final double centerY;


    /**
     * Name is passed along directly. The Confidence and Color values need some processing and validation, however.
     * @param horseName the name of the horse
     * @param horseConfidence a confidence value between 0.0 and 1.0, is validated
     * @param horseColor the color (or the image which represents the color) of the horse. image associated to the color
     *                   is fetched and placed as a child of the Horse object.
     */

    public Horse(String horseName, double horseConfidence, String horseColor) {
        this.name = horseName;
        this.horseConfidence = horseConfidence;

        this.horseColor = Settings.colorList.get(horseColor);
        this.horseColorName = horseColor;
        getChildren().add(new ImageView(this.horseColor));

        this.centerX = this.horseColor.getWidth() / 2;
        this.centerY = this.horseColor.getHeight() / 2;
    }

    // -------- Accessor Methods Below --------

    // Falling
    public void setFall(boolean fall)
    {
        hasFallen = fall;
    }
    public boolean hasFallen()
    {
        return hasFallen;
    }

    // Finishing
    public void setFinish(boolean finish)
    {
        hasFinished = finish;
    }
    public boolean hasFinished()
    {
        return hasFinished;
    }

    // Position
    public double getPosition()
    {
        return position;
    }
    public void setPosition(double newPosition) { position = newPosition; }

    // Name
    public String getName()
    {
        return name;
    }
    public void setName(String newName) {  name = newName; }

    // Confidence
    public double getHorseConfidence()
    {
        return horseConfidence;
    }
    public void setHorseConfidence(double newConfidence) { horseConfidence = newConfidence; }

    // Color
    public String getHorseColor() { return horseColorName; }
    public void setHorseColor(String color) {
        horseColorName = color;
        horseColor = Settings.colorList.get(color);
        getChildren().clear();
        getChildren().add(new ImageView(horseColor));
    }


}
