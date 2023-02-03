package ru.ibs.framework.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.ibs.framework.utils.PropsConst;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;


public class DriverManager {
    private static DriverManager INSTANCE;
    private TestPropertiesManager testProperties = TestPropertiesManager.getInstance();
    private WebDriver driver;




    public static DriverManager getInstance () {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }
    private DriverManager() {
    }

    public WebDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }

    private WebDriver createDriver() {
        switch (testProperties.getMavenProperty("browser", "firefox")) {
            case "edge":
                System.setProperty(PropsConst.PATH_EDGE_DRIVER, testProperties.getProperty(PropsConst.PATH_EDGE_DRIVER));
                DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setAcceptInsecureCerts(true);
                return new EdgeDriver(desiredCapabilities);
            case "chrome":
                System.setProperty(PropsConst.PATH_CHROME_DRIVER, testProperties.getProperty(PropsConst.PATH_CHROME_DRIVER));
                ChromeOptions chromeSslHandling = new ChromeOptions();
                chromeSslHandling.setAcceptInsecureCerts(true);
                return new ChromeDriver(chromeSslHandling);
            case "remote":
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("browserName", "chrome");
                capabilities.setCapability("browserVersion", "109.0");
                capabilities.setCapability("enableVNC", true);
                capabilities.setCapability("enableVideo", false);
                try {
                    return new RemoteWebDriver(
                            URI.create("http://selenoid/wd/hub").toURL(),
                            capabilities);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            default:
                System.setProperty(PropsConst.PATH_FIREFOX_DRIVER, testProperties.getProperty(PropsConst.PATH_FIREFOX_DRIVER));
                FirefoxOptions fireFoxOptions = new FirefoxOptions();
                fireFoxOptions.setAcceptInsecureCerts(true);
                return new FirefoxDriver(fireFoxOptions);
        }
    }
    public void quitDriver() {
        if (driver !=null) {
            driver.quit();
            driver =null;
        }
    }

}
