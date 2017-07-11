package generic.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sanver.
 */
public class ConfigProperties {
    private Object lock = new Object();
    private volatile boolean validFile;
    private Properties p;

    public ConfigProperties(String fileName) {
        synchronized (lock) {
            p = new Properties();
            try(InputStream input = ClassLoader.getSystemResourceAsStream(fileName)) {
                p.load(input);
                validFile = true;
            } catch (FileNotFoundException e) {
                System.out.println("Config file is not found" + e.getMessage());
            } catch (IOException e) {
                System.out.println("Config file cannot be read" + e.getMessage());
            }
        }
    }

    public String getValue(String key) {
        if(p == null) {
            synchronized (lock) {
                while (validFile){}
            }
        }
        return validFile ? p.getProperty(key) : "";
    }
}
