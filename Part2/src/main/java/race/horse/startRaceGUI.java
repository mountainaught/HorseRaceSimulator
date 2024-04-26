package race.horse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Main method that starts up the JavaFX Application
 * @author etunal
 * @version 1.0
 */
public class startRaceGUI extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader =
                new FXMLLoader(this.getClass().getResource("main-panel.fxml"));
        Scene scene = new Scene(loader.load(), 1280, 720);
        stage.setTitle("Horse Race");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
