package cz.markovda.view;

import java.net.URL;

/**
 * Utility class for loading view resources.
 *
 * @author David Markov
 * @since 12. 1. 2021
 */
public class ViewLoader {

    /**
     * Loads fxml file of given window as URL from resources.
     *
     * @param window window to load
     * @return resource as URL
     */
    public URL loadView(final Window window) {
        return getClass().getResource(window.getFileName());
    }

}
