module race.horse {
    requires javafx.controls;
    requires javafx.fxml;


    opens race.horse to javafx.fxml;
    exports race.horse;
}