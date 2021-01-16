package cz.markovda.view;

import cz.markovda.controller.LobbyController;
import cz.markovda.controller.LoginController;
import cz.markovda.game.LobbyGame;
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
import java.util.List;

/**
 * Utility class for displaying objects onto the screen.
 *
 * @author David Markov
 * @since 11. 1. 2021
 */
public class Renderer {

    private static final Logger logger = LoggerFactory.getLogger(Renderer.class);

    private static Stage stage;
    private static Scene scene;
    private static double xOffset;
    private static double yOffset;

    private static LobbyController lobbyController;

    /**
     * Initializes application to the default entry state - displays connection screen for connecting
     * to the server.
     *
     * @param newStage JavaFX Application stage to display screens onto
     * @throws IOException if loading connection screen fxml fails
     */
    public static void initializeApp(Stage newStage) throws IOException {
        stage = newStage;

        scene = new Scene(loadFXML(Window.CONNECTION_SCREEN));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Draughts");
        stage.show();
        initializeScene();
    }

    public static void displayConnectionScreen() {
        try {
            setRoot(Window.CONNECTION_SCREEN);
        } catch (IOException e) {
            logger.error("Error displaying connection screen!", e);
        }
    }

    public static void displayLoginScreen(final String address, final String port) {
        FXMLLoader loader = new FXMLLoader(new ViewLoader().loadView(Window.LOGIN_SCREEN));
        try {
            Parent parent = loader.load();

            LoginController controller = loader.getController();
            controller.setServerInfo(address, port);

            scene.setRoot(parent);

        } catch (IOException e){
            logger.error("Error displaying login screen!", e);
            showInformationWindow("Error occurred while displaying login screen. See logs...");
        }
    }

    public static void displayLobby(final String serverInfo,
                                    final String nickname,
                                    final List<LobbyGame> games) {
        FXMLLoader loader = new FXMLLoader(new ViewLoader().loadView(Window.LOBBY));
        try {
            Parent parent = loader.load();

            lobbyController = loader.getController();
            lobbyController.setInfoLabels(serverInfo, nickname);
            lobbyController.addGames(games);

            scene.setRoot(parent);
            stage.sizeToScene();
        } catch (IOException e) {
            logger.error("Error displaying lobby!", e);
            showInformationWindow("Error occurred while displaying lobby. See logs...");
        }
    }

    public static void addLobbyGame(final LobbyGame newGame) {
        lobbyController.addGame(newGame);
    }

    public static void displayLoadingScreen() {
        FXMLLoader loader = new FXMLLoader(new ViewLoader().loadView(Window.LOADING_SCREEN));
        try {
            Parent parent = loader.load();

            scene.setRoot(parent);
            stage.sizeToScene();
        } catch (IOException e) {
            logger.error("Error displaying loading screen!", e);
            showInformationWindow("Error occurred while displaying loading screen. See logs...");
        }
    }

    public static void displayGameScreen() {
        try {
            setRoot(Window.GAME_SCREEN);
        } catch (IOException e) {
            logger.error("Error displaying game screen!", e);
            showInformationWindow("Error occurred while displaying game screen. See logs...");
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
        stage.sizeToScene();
    }


    private static Parent loadFXML(final Window window) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new ViewLoader().loadView(window));
        return fxmlLoader.load();
    }

    private static void initializeScene() {
        scene.setFill(Color.TRANSPARENT);
        setMoveListeners();
    }

    private static void setMoveListeners() {
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
