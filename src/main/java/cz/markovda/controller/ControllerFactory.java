package cz.markovda.controller;

import cz.markovda.view.ViewLoader;
import cz.markovda.view.Window;
import javafx.fxml.FXMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Factory class for obtaining individual controllers.
 *
 * @author David Markov
 * @since 12. 1. 2021
 */
public class ControllerFactory {

    private static final Logger logger = LoggerFactory.getLogger(ControllerFactory.class);

    public static LoginController getLoginController() {
        try {
            FXMLLoader loader = getControllerLoader(Window.LOGIN_SCREEN);
            return loader.getController();
        } catch (IOException e) {
            logger.error("Error while obtaining login screen controller", e);
            return null;
        }
    }

    public static ConnectionController getConnectionController() {
        try {
            FXMLLoader loader = getControllerLoader(Window.CONNECTION_SCREEN);
            return loader.getController();
        } catch (IOException e) {
            logger.error("Error while obtaining connection controller", e);
            return null;
        }
    }

    private static FXMLLoader getControllerLoader(final Window window) throws IOException {
        FXMLLoader loader = new FXMLLoader(new ViewLoader().loadView(window));
        loader.load();

        return loader;
    }
}
