package cz.markovda.view;

import cz.markovda.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Utility class for displaying objects onto the screen.
 *
 * @author David Markov
 * @since 11. 1. 2021
 */
public class Renderer {

    private static final Logger logger = LoggerFactory.getLogger(Renderer.class);

    private static Scene scene;
    private static double xOffset;
    private static double yOffset;

    /**
     * Initializes application to the default entry state - displays connection screen for connecting
     * to the server.
     *
     * @param stage JavaFX Application stage to display screens onto
     * @throws IOException if loading connection screen fxml fails
     */
    public static void initializeApp(Stage stage) throws IOException {
        scene = new Scene(loadFXML(Window.CONNECTION_SCREEN));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.show();
        setMoveListeners(stage);
    }

    public static void displayConnectionScreen() {
        try {
            setRoot(Window.CONNECTION_SCREEN);
        } catch (IOException e) {
            logger.error("Error displaying connection screen!", e);
        }
    }

    public static void displayLoginScreen() {
        try {
            setRoot(Window.LOGIN_SCREEN);
        } catch (IOException e) {
            logger.error("Error displaying login screen!", e);
        }
    }

    /**
     * Displays confirmation window with given description. Window is blocking, meaning the action has to be confirmed
     * or declined in order to continue using the application.
     *
     * @param text text describing action that needs confirmation
     * @return ButtonType.YES if confirmed, otherwise ButtonType.NO
     * @see ButtonType
     */
    public static ButtonType showConfirmationWindow(final String text) {
        Alert confirmWindow = new Alert(Alert.AlertType.CONFIRMATION, text, ButtonType.NO, ButtonType.YES);
        confirmWindow.showAndWait();

        return confirmWindow.getResult();
    }

    /**
     * Displays information window with given description text. Window is blocking, meaning the action has to be confirmed
     * or declined in order to continue using the application.
     * @param text text describing informing action
     *
     */
    public static void showInformationWindow(final String text) {
        Alert infoWindow = new Alert(Alert.AlertType.INFORMATION, text);
        infoWindow.showAndWait();
    }

    /**
     * Sets new root fxml resource to scene, resulting in new graphics background.
     *
     * @param window window to render
     * @throws IOException if path or resource is invalid
     */
    private static void setRoot(final Window window) throws IOException {
        scene.setRoot(loadFXML(window));
    }


    private static Parent loadFXML(final Window window) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(window.getPath()));
        return fxmlLoader.load();
    }

    private static void setMoveListeners(Stage stage) {
        scene.setOnMousePressed(mouseEvent ->  {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        scene.setOnMouseDragged(mouseEvent ->  {
            stage.setX(mouseEvent.getScreenX()-xOffset);
            stage.setY(mouseEvent.getScreenY()-yOffset);
        });
    }
}
