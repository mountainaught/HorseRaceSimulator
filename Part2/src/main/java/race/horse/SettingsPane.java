package race.horse;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The SettingsPane is the user's primary interaction point for customisation and setup. As such it has several
 * important functions, namely Adding and Removal of horses, and the customisation and saving of information.
 * <p>
 * In this pursuit this class has many helper methods that redraw, update and error-check user inputs and
 * graphically displayed information.
 * <p>
 * A "Default" configuration option button is also present, in case the user wishes to skip Horse creation.
 * @author etunal
 * @version 1.2
 */

public class SettingsPane implements Initializable {

    public TextField horseConfField;
    public ChoiceBox<String> horseColorPicker;
    public TextField horseNameField;
    public ListView<String> horseListPane;

    Horse currentHorse;
    Lane currentLane;

    public void onSaveAction(ActionEvent actionEvent) { // Saving a Horse's info

        // Save the currently selected options as Strings - important as they are immune to null values at declaration.
        String horseName = horseNameField.getCharacters().toString();
        String color = horseColorPicker.getValue();
        String horseConfidenceString = horseConfField.getCharacters().toString();

        // Check if any of the values is empty or otherwise null
        if (horseName.isEmpty() || Objects.isNull(color) || horseConfidenceString.isBlank()) {
            Utils.errorCall("Empty field! Have you entered all necessary info?");
            return;
        }


        // If all checks out, we can assign a double value for the confidence, and check if it is valid.
        double horseConfidence = Double.parseDouble(horseConfidenceString);
        if (!Utils.validateValue(horseConfidence)) return; // If validation results in false, get outta here.


        Horse tempHorse = Utils.checkIfHorseExists(horseName); // Temporary Horse for dupe check
        if ( !Objects.isNull(tempHorse) ) { // Check if horse already exists,
            currentHorse = tempHorse;      // and update the horse's info accordingly
            currentHorse.setHorseColor(color);
            currentHorse.setHorseConfidence(horseConfidence);

            clearHorsePane();
            drawSettingsPane();
            return;
        }

        // The rest is just declaring a Horse/Lane pair, and adding them to the collective.
        currentHorse = new Horse(horseName, horseConfidence, color);

        Lane newLane = new Lane(0, 0, Settings.RACE_LENGTH, currentHorse, new Image("nanami.png"));
        Settings.laneList.add(newLane);
        Settings.LANE_COUNT++;

        clearHorsePane();
        drawSettingsPane();

        horseListPane.getSelectionModel().select(currentHorse.getName());

    }

    public void onAddAction(ActionEvent actionEvent) { // Setting things up to add a new horse

        if (Settings.LANE_COUNT >= Settings.MAX_LANE_COUNT) { // Make sure we're not over the limit.
            Utils.errorCall("Maximum Horse Amount Reached");
            return;
        }

        // Redraw the horse settings pane with fresh info.
        drawHorsePane("new");
        // Focus keyboard/entry on the name field, done cuz why not.
        horseNameField.requestFocus();
    }

    public void onRemoveAction() {
        // Remove horse from lane
        currentLane.getChildren().clear();

        // Remove the Horse and clear selection
        horseListPane.getItems().remove(currentHorse.getName());
        horseListPane.getSelectionModel().clearSelection();
        clearHorsePane();

        Settings.laneList.remove(currentLane);
        Settings.LANE_COUNT--;
    }

    // When a item is chosen (when you click within the listpane), it displays the relevant info.
    public void onChooseAction() {
        // Never a fun time with NullPointers.
        if (Objects.isNull(horseListPane.getSelectionModel().getSelectedItem())) {
            System.out.println("ignore");
            return;
        }

        // Fetch the corresponding Horse
        for (Lane lane : Settings.laneList) {
            Horse horse = lane.horse;
            if (horseListPane.getSelectionModel().getSelectedItem().equals(horse.getName())) {
                currentHorse = horse;
                currentLane = lane;
            }
        }

        drawHorsePane("existing");
    }

    // Methods for drawing and clearing the two sections of the Settings Panel
    public void clearHorsePane() {
        horseNameField.clear();
        horseConfField.clear();
        horseColorPicker.getSelectionModel().clearSelection();
    }

    public void drawHorsePane(String context) {
        if (context.equals("existing")) {
            horseNameField.setText(currentHorse.getName());
            horseConfField.setText(String.valueOf(currentHorse.getHorseConfidence()));
            horseColorPicker.getSelectionModel().select(currentHorse.getHorseColor());
        } else if (context.equals("new")) {
            horseNameField.setText("New Horse");
            horseConfField.setText("0.5");
            horseColorPicker.getSelectionModel().select(1);
        }

    }

    public void drawSettingsPane() {
        // Refresh the listPane
        horseListPane.getItems().clear();

        // Add all Horses associated to a Lane
        for (Lane lane : Settings.laneList) {
            String name = lane.horse.getName();
            horseListPane.getItems().add(name);
        }

        // Clear the color picker just to avoid having a blank option
        horseColorPicker.getItems().clear();
        // Add all associated color values
        horseColorPicker.getItems().addAll(Settings.colorList.keySet());
    }

    // Init the settings window
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { drawSettingsPane(); }

    public void onDefaultAction(ActionEvent actionEvent) {
        // Literally some random Default horses in case the user doesn't want to bother.
        Horse firstHorse = new Horse("Yuu", 0.6, "Black");
        Horse secondHorse = new Horse("Saeki", 0.8, "Brown");
        Horse thirdHorse = new Horse("Kase", 0.4, "Black");
        Horse fourthHorse = new Horse("Nico Bellic", 0.3, "Brown");
        Horse fifthHorse = new Horse("CSE Student", 1.0, "Brown");


        Lane firstLane = new Lane(firstHorse, new Image("nanami.png"));
        Lane secondLane = new Lane(secondHorse, new Image("nanami.png"));
        Lane thirdLane = new Lane(thirdHorse, new Image("nanami.png"));
        Lane fourthLane = new Lane(fourthHorse, new Image("nanami.png"));
        Lane fifthLane = new Lane(fifthHorse, new Image("nanami.png"));

        Settings.laneList.add(firstLane);
        Settings.laneList.add(secondLane);
        Settings.laneList.add(thirdLane);
        Settings.laneList.add(fourthLane);
        Settings.laneList.add(fifthLane);

        Settings.LANE_COUNT = 5;
        drawSettingsPane();
    }
}
