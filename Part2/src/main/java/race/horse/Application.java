package race.horse;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static race.horse.Settings.laneList;

/**
 * The Horse Race program's primary Class. Application manages all submenus and functions.
 * @author etunal
 * @version 1.2
 */

public class Application implements Initializable {

    @FXML
    private VBox raceBox;

    private ArrayList<Lane> horsesInRunning = new ArrayList<>();

    @FXML
    private TextFlow infoDisplay;
    @FXML
    private TextFlow winnerDisplay;
    @FXML
    private TextFlow statDisplay;

    private AnimationTimer gameLoop;

    boolean raceOngoing = false;
    boolean settingsOpen = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Text text1 = new Text("Welcome to Horse Racing, User!");
        text1.setFill(Color.BLACK);
        text1.setFont(Font.font("Helvetica", FontPosture.ITALIC, 36));

        infoDisplay.getChildren().add(text1);
    }

    public void onStartAction(ActionEvent actionEvent) {

        if (raceOngoing) { // Error Checking - If a race is already under way.
            Utils.errorCall("There's already a race ongoing.");
            return;
        } else if (laneList.isEmpty()) { // Error Checking - If no horses are present
            Utils.errorCall("There are no horses on track.");
            return;
        } else if (settingsOpen) { // Error Checking - if settings is still open
            Utils.errorCall("Please close Settings first.");
            return;
        }

        winnerDisplay.getChildren().clear(); // Clears the Winner Display

        // Initialise some temp settings
        raceOngoing = true;
        setHorses();

        // Add horses to a "Running" list to keep track of finished and fallen horses.
        horsesInRunning.addAll(laneList);

        // Starts the main Game Loop, using JavaFX's AnimationTimer class.
        gameLoop = new AnimationTimer() {

            int finishingPlaces = 1; // A counter to rank all horses by finish order
            String winner = ""; // Holds the name of the winner. If blank when game ends, must be a tie.

            @Override
            public void handle(long now) {

                laneList.forEach(Lane::advanceHorse); // Calls the Advance method for each horse, see Lane::advanceHorse

                for (Lane lane : laneList) {

                    if (horsesInRunning.isEmpty()) { // If no horses are running, finalise the race.
                        Text endResult; // End result to be displayed.

                        if (winner.isBlank()) { // If blank, tie
                            endResult = new Text("It's a tie!");
                        } else { // Else we have a winner! Put their name up there!
                            endResult = new Text("And the winner is:\n" + winner + "!");
                        }

                        // Nice big font for the Winning horse.
                        endResult.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.ITALIC, 18));
                        winnerDisplay.getChildren().add(endResult);

                        gameLoop.stop(); // Stop the Game Loop, as race has ended.
                        raceOngoing = false; // Set ongoing to false so another race can begin.

                        setStatDisplay(); // Display all updated statistics.
                        return; // Return to repeat any further.
                    }

                    if (lane.horse.hasFallen()) { // If a horse has fallen, remove them from the running.
                        horsesInRunning.remove(lane);
                    } else if (lane.horse.hasFinished() && lane.getLastFinish() == 0) {
                        // Alternatively if a horse has finished but hasn't been given a rank, do so
                        lane.finishedRace(finishingPlaces);

                        if (finishingPlaces == 1) { // If they're the first finisher, they're the Winner!
                            lane.wonRace();
                            winner = lane.horse.getName();
                        }

                        horsesInRunning.remove(lane); // Remove lane from running.
                        finishingPlaces++; // Increase the rank order. Oof.
                    }

                }

            }

        };

        gameLoop.start(); // Start the above Race loop.
    }

    public void onSettingsAction() { // Launch settings menu. Basic JavaFX stage and scene setup.

        if (settingsOpen) {
            Utils.errorCall("Settings is already open.");
            return;
        }

        if(raceOngoing) {
            Utils.errorCall("There's a ongoing race!");
            return;
        }

        Stage stage = new Stage();
        settingsOpen = true;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("settings-panel.fxml"));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root, 600, 500));
        } catch (IOException ignored) {}

        stage.setResizable(false);
        stage.setTitle("Settings");
        stage.showAndWait();

        settingsOpen = false;
        setLanes(); // Reset the lanes on display.

        for (Lane lane : laneList) { // Clear the horses left on screen, as they haven't been updated for the new layout
            lane.getChildren().clear();
        }
    }


    private void setLanes() { // Reset the lanes
        raceBox.getChildren().clear(); // Since a Lane is just a JavaFX object, first we must clear all from the scene.

        double margin = ( (Settings.PANE_HEIGHT - 10) % Settings.LANE_COUNT); // Calculate the track margins. Neat.
        // Calculate lane height depending on amount of Lanes.
        double laneHeight = (Settings.PANE_HEIGHT - margin) / Settings.LANE_COUNT;

        raceBox.setSpacing(margin + 10); // Set spacing between lanes and walls.

        for (Lane lane : laneList) { // Change lane heights and add them one by one.
            lane.changeHeight(laneHeight);
            raceBox.getChildren().add(lane);
        }

    }

    // Again, since Horses are just JavaFX objects, we need to clear them and add them back.
    // Except each Lane can only have one Horse. Just like real life!
    private void setHorses() {
        for (Lane lane : laneList) {
            lane.getChildren().clear();

            Bounds bounds = lane.getLayoutBounds();

            double centre = ((bounds.getMaxY() - bounds.getMinY()) / 2) + bounds.getMinY();
            lane.setStartingPoint(bounds.getMinX(), centre);
            lane.getChildren().add(lane.horse);
            lane.initHorse();
        }
    }

    private void setStatDisplay() { // Set the statistics. I know, it's hella uggo, but meh doesn't matter!
        statDisplay.getChildren().clear();

        for (Lane lane : laneList) {

            Text name = new Text("Name: " + lane.horse.getName());
            name.setFont(Font.font("Helvetica", 14));
            Text lastPosition = new Text("Last Position: " + lane.getLastFinish());
            Text winsAndFinishes = new Text(String.format("Wins: %d, Finishes: %d", lane.getWins(), lane.getFinishes()));
            Text averageFinish = new Text("Average Finish: " + lane.getAverageFinish());

            // I know. It's awful. I learn to live with it.
            statDisplay.getChildren().add(name);
            statDisplay.getChildren().add(new Text(System.lineSeparator()));
            statDisplay.getChildren().add(lastPosition);
            statDisplay.getChildren().add(new Text(System.lineSeparator()));
            statDisplay.getChildren().add(winsAndFinishes);
            statDisplay.getChildren().add(new Text(System.lineSeparator()));
            statDisplay.getChildren().add(averageFinish);
            statDisplay.getChildren().add(new Text(System.lineSeparator()));
            statDisplay.getChildren().add(new Text(System.lineSeparator())); // Two here for some separation.

        }
    }

}
