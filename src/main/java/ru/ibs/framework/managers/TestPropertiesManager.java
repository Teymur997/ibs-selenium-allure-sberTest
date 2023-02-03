package ru.ibs.framework.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestPropertiesManager {
    private final Properties properties = new Properties();
    private static TestPropertiesManager INSTANCE = null;

    private TestPropertiesManager() {
        try {
            properties.load(new FileInputStream(new File("src/main/resources/" +
                    System.getProperty("propFile", "application") + ".properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TestPropertiesManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestPropertiesManager();
        }
        return INSTANCE;
    }


    //Для защиты доступа, возврат значений осуществляется через оберточные методы
    public String getProperty(String key) {
        return TestPropertiesManager.getInstance().properties.getProperty(key);
    }
    public String getMavenProperty(String key, String defaultValue) {
        String mavenValue = System.getProperty(key);
        if (mavenValue!=null) {
            return mavenValue.toLowerCase();
        }

        return TestPropertiesManager.getInstance().properties.getProperty(key.toLowerCase(), defaultValue);
    }
}
