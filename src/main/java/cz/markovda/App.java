package cz.markovda;

import cz.markovda.view.Renderer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class of a client. Client is a JavaFX application.
 *
 * @author David Markov
 * @since 4. 11. 2020
 */
public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Renderer.initializeApp(stage);
    }
}