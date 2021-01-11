package cz.markovda.controller;

import cz.markovda.connection.Connector;
import cz.markovda.view.Renderer;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;

/**
 * Low-level controller class for operating with the application itself.
 *
 * @author David Markov
 * @since 11. 1. 2021
 */
public class ApplicationController {

    /**
     * Terminates the application.
     */
    public void exit() {
        ButtonType confirmation = Renderer.showConfirmationWindow("Exit application?");

        if (confirmation == ButtonType.YES) {
            Connector.getInstance().disconnect();
            Platform.exit();
        }
    }
}
