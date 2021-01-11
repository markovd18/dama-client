package cz.markovda.view;

/**
 * Enumeration for working with individual application windows and screens.
 *
 * @author David Markov
 * @since 11. 1. 2021
 */
public enum Window {
    CONNECTION_SCREEN("connectionScreen.fxml"),
    LOGIN_SCREEN("loginScreen.fxml");

    private final String path;

    Window(final String path){
        this.path = path;
    }

    /**
     * Returns relative path (filename) to the fxml file containing window description.
     *
     * @return relative path (filename) to the fxml file
     */
    public String getPath() {
        return path;
    }
}
