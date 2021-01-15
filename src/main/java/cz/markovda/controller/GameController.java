package cz.markovda.controller;

import cz.markovda.connection.Connector;
import cz.markovda.view.Renderer;
import javafx.fxml.FXML;


public class GameController {


    @FXML
    private void surrender() {
        String address = Connector.getInstance().getSessionInfo().getServer().getAddress();
        int port = Connector.getInstance().getSessionInfo().getServer().getPort();
        Renderer.displayLobby(address, String.valueOf(port));
    }

}
