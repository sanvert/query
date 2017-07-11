package generic.io;

/**
 * Created by sanver.
 */
public class ArgumentInteraction implements Interaction {
    @Override
    public void writeParameter(String parameter, String value) {
        System.setProperty(parameter, value);
    }

    @Override
    public String readByParameter(String parameter) {
        return System.getProperty(parameter);
    }
}
