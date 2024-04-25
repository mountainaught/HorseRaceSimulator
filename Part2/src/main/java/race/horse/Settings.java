package race.horse;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

public class Settings {

    // Don't tamper with these, project specs don't request a dynamically resizing window!
    static double LANE_WIDTH = 1010;
    static double PANE_HEIGHT = 660;

    // Could just replace with laneList.size(); Will I?
    static double LANE_COUNT = 0;
    static double MAX_LANE_COUNT = 5; // Else it won't look very nice.
    static double RACE_LENGTH = 10; // Can be anything really. I've no clue the units though use your imagination.

    // As it's a common list used by pretty much everyone, no need to keep it safe.
    static ArrayList<Lane> laneList = new ArrayList<>();

    // Using a hashmap as a dictionary just for the horse images.
    static HashMap<String, Image> colorList = new HashMap<>() {{
        put("Brown", new Image("saeki.jpg"));
        put("Black", new Image("yuu_xs.png"));
    }};

}
