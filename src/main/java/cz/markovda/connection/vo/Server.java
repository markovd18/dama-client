package cz.markovda.connection.vo;

/**
 * VO representing server instance.
 *
 * @author David Markov
 * @since 12. 1. 2021
 */
public class Server {

    private String address;
    private int port;

    public Server(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
