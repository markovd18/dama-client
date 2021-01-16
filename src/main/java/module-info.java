module cz.markovda {
    requires javafx.controls;
    requires javafx.fxml;
    requires slf4j.api;

    opens cz.markovda.controller to javafx.fxml;
    opens cz.markovda.view to javafx.fxml;
    opens cz.markovda to javafx.fxml;
    exports cz.markovda.game;
    exports cz.markovda.controller;
    exports cz.markovda.view;
    exports cz.markovda;
}