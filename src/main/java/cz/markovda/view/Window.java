package cz.markovda.view;

/**
 * Enumeration for working with individual application windows and screens.
 *
 * @author David Markov
 * @since 11. 1. 2021
 */
public enum Window {
    CONNECTION_SCREEN("connectionScreen.fxml"),
    LOGIN_SCREEN("loginScreen.fxml"),
    LOBBY("lobby.fxml"),
    LOADING_SCREEN("gameLoadingScreen.fxml"),
    GAME_SCREEN("gameScreen.fxml");

    private final String fileName;

    Window(final String path){
        this.fileName = path;
    }

    /**
     * Returns relative path (filename) to the fxml file containing window description.
     *
     * @return relative path (filename) to the fxml file
     */
    public String getFileName() {
        return fileName;
    }
}
