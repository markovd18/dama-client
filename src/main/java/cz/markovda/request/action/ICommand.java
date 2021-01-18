package cz.markovda.request.action;

/**
 * Interface of the Command design pattern.
 *
 * @author David Markov
 * @since 177. 01. 2021
 */
public interface ICommand {

    /**
     * Executes the code inside of this command. All required parameters have to be passed through
     * the individual implementing classes' constructor.
     * @param parameters parameters required to execute the command
     */
    void execute(final String[] parameters);
}
