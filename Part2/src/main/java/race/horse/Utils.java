package race.horse;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * A (not) common collection of methods that don't really fit anywhere else, kept here for sake of keeping all classes
 * tidy.
 * @author etunal
 * @version 1.0
 */

public class Utils {

    // Used for the Confidence value, checks if a value is between 0.0 and 1.0, and if it has 1 digit of precision.
    public static boolean validateValue(double value) {
        int decimalRemainder = (int) (value * 100) % 10;
        if (value > 1.0 || value < 0.0) {
            errorCall("The value must be between 0.0 and 1.0.");
            return false;
        } else if ( decimalRemainder != 0) {
            errorCall("The value must only have 1 digit of precision");
            return false;
        }

        return true;
    }

    // Check if a horse still exists. Used as a way of making sure duplicates don't cause headaches.
    public static Horse checkIfHorseExists(String horseName) {
        Horse horse = null;
        for (Lane lane : Settings.laneList) {
            if (horseName.equals(lane.horse.getName())) horse = lane.horse;
        }
        return horse;
    }

    /**
     * A JavaFX pane dedicated to displaying user-side errors encountered during the Application execution in an
     * aesthetically pleasing manor.
     * @param error the error message relating to the cause of the problem. ex. a value not entered.
     */
    public static void errorCall(String error) {

        Stage stage = new Stage();

        AnchorPane errorPane = new AnchorPane();
        errorPane.setPrefSize(500, 100);

        Label largeLabel = new Label("Hey, Error Time!");
        errorPane.getChildren().add(largeLabel);
        largeLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        largeLabel.relocate(14, 14);

        Label errorLabel = new Label(error);
        errorPane.getChildren().add(errorLabel);
        errorLabel.setFont(new Font("Helvetica", 18));
        errorLabel.relocate(14, 59);

        // Close button
        EventHandler<ActionEvent> buttonHandler = event -> {
            stage.close();
            event.consume();
        };

        Button closeButton = new Button();
        closeButton.setText("Close");
        errorPane.getChildren().add(closeButton);
        closeButton.relocate(434, 60);
        closeButton.setOnAction(buttonHandler);

        stage.setResizable(false);
        stage.setTitle("Error!");
        stage.setScene(new Scene(errorPane, 500, 100));
        stage.showAndWait();
    }

}
