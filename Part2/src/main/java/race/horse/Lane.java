package race.horse;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * The Lane class implements a Pane as that's pretty much it's purpose.
 * As a car has a road, as an Image has a ImageView and as a Programmer has a "Box of Antidepressants" (what is that?),
 * every Horse must have a lane. To avoid an absurd situation like a horse running on thin air,
 * every Lane must have a Horse, and a Horse cannot be created without a Lane.
 * <p>
 * Additionally, every Lane is tasked with holding the Horse's stats. In honesty this is done for the sake of
 * lessening complexity, as it's easier to think of a lane with a book on one end holding all the stats than a public
 * arraylist with all the info. Nah, some private variables and a few accessor methods work wonders.
 * <p>
 * Basically the Lane is a massive box holding a Horse and metadata. The Horse only know what it must know.
 *
 * @author etunal
 * @version 1.1
 */

public class Lane extends Pane {

    private double laneCenter, startingX;
    private final double width;
    private double height;

    double raceDistance;

    Image background;
    Horse horse; // As explained above, the Lane has a Horse associated with it.

    /**
     *  Lane object
     * @param width the width of the lane, likely a static value referenced from Settings
     * @param height height of the lane, variable and dependent on amount of lanes
     * @param raceDistance distance of the race, variable and scaling
     * @param horse horse in this lane
     * @param laneBackground background image for the lane
     */

    public Lane(double width, double height, double raceDistance, Horse horse, Image laneBackground) {
        this.width = width;
        this.height = height;
        this.background = laneBackground;
        this.raceDistance = raceDistance;
        this.horse = horse;

        drawLane();
    }

    /**
     * Overloaded as in most cases all parameters do not need to be supplied.
     * @param horse the horse in this lane
     * @param laneBackground background image for the lane
     */
    public Lane(Horse horse, Image laneBackground) {
        this.width = 0;
        this.height = 0;
        this.background = laneBackground;
        this.raceDistance = Settings.RACE_LENGTH;
        this.horse = horse;

        drawLane();
    }

    // Change Lane height - if more lanes are added for example.
    public void changeHeight(double newHeight) {
        height = newHeight;
        drawLane();
    }

    // Draw the lane itself using size information and the background image.
    // This version uses a filler image awaiting proper textures.
    private void drawLane() {
        setPrefSize(width, height);
        setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
    }

    // Setting start point for Horse
    public void setStartingPoint(double x, double y) {
        laneCenter = y - horse.centerY;
        startingX = x;
    }

    // initialise all temporary info - position, place, fall
    public void initHorse() {
        getChildren().get(0).relocate(startingX, laneCenter);
        getChildren().get(0).toFront();

        horse.setPosition(0); // Position on track, maybe find a better name
        horse.setFall(false);
        horse.setFinish(false);

        previousPlace = 0;
    }

    public void advanceHorse() { // Advances or Fells the Horse depending on RNG. Called by Race Loop.

        // If the horse hasn't fallen or finished, calculates a distance to advance by every lap
        //   based on track length and horse confidence. This distance is added to the current position and
        //     the horse's location on the Lane is updated accordingly.
        if (!horse.hasFallen() && !horse.hasFinished()) {
            double advancePerLap = ((100 * horse.getHorseConfidence()) / Settings.RACE_LENGTH);
            horse.setPosition(horse.getPosition() + advancePerLap);
            getChildren().get(0).relocate(horse.getPosition(), laneCenter);
        }

        // If the Position of the horse has passed the "Finishing Line", it's set as having finished.
        if (horse.getPosition() >= ( Settings.LANE_WIDTH - horse.getWidth()) ) {
            horse.setFinish(true);
            return; // Avoid a situ where horse finishes and falls
        }

        // Randomly fells a horse. Original method kept felling the horses too much so slightly modified.
        if (Math.random() < (0.01 * horse.getHorseConfidence() * horse.getHorseConfidence())) {
            horse.setFall(true);
        }

    }

    // Statistics variables, kept track of by the Lane and modified/accessed by Accessor methods
    // This way assures the best compliance with both memory security considerations and structural sense.
    private int wins = 0;
    private int finishes = 0;
    private int previousPlace = 0;
    private double averageFinish = 0.0;

    // Accessor Methods -- Modification
    public void wonRace() { wins++; }
    public void finishedRace(int place) {
        finishes++;
        previousPlace = place;
        averageFinish = (averageFinish > 0.0) ? (( averageFinish + place ) / 2) : place;
    }

    // Accessor Methods -- Retrieval
    public int getWins() { return wins; }
    public int getFinishes() { return finishes; }
    public int getLastFinish() { return previousPlace; }
    public double getAverageFinish() { return averageFinish; }
}
