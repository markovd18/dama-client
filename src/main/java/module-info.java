module cz.markovda {
    requires javafx.controls;
    requires javafx.fxml;

    opens cz.markovda to javafx.fxml;
    exports cz.markovda;
}