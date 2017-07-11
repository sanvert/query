package generic.io;

/**
 * Created by sanver.
 */
public interface Interaction {
    /**
     * Method to write parameter via interaction interface
     * @param parameter
     * @param value
     */
    void writeParameter(String parameter, String value);

    /**
     * Method to read parameter via interaction interface
     * @param parameter
     * @return String parameter value
     */
    String readByParameter(String parameter);
}
