package cz.markovda.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class used for communicating with server instance.
 *
 * @author David Markov
 * @since 12. 1. 2021
 */
public class Request {
    private final RequestType requestType;
    private final List<String> parameters;

    public Request(final RequestType requestType, String... parameters) {
        this.requestType = requestType;
        this.parameters = new ArrayList<>(Arrays.asList(parameters));
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public List<String> getParameters() {
        return parameters;
    }
}
