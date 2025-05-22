package org.example.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

public class DriverFactory {

    private static final String grid_url = System.getenv("GRID_URL");
    private static WebDriver driver;
    private static final String browser = Optional
            .ofNullable(System.getenv("BROWSER"))
            .orElse("chrome");

    public static WebDriver getDriver() {
        if (grid_url != null) {
            return getRemoteDriver(browser);
        } else {
            return getLocalDriver(browser);
        }

    }

    private static WebDriver getRemoteDriver(String browser) {
       /* if (grid_url != null) {
            return getRemoteDriver(browser);
        } else {
            return getLocalDriver(browser);
        }*/

            if (driver == null) {
                try {
                     browser = System.getenv("BROWSER");
                    String gridUrl = System.getenv("GRID_URL");

                    if (browser == null || gridUrl == null) {
                        throw new RuntimeException("BROWSER or GRID_URL environment variable not set");
                    }

                    URL url = new URL(gridUrl);

                    if (browser.equalsIgnoreCase("chrome")) {
                        ChromeOptions options = new ChromeOptions();
                        driver = new RemoteWebDriver(url, options);
                    } else if (browser.equalsIgnoreCase("firefox")) {
                        FirefoxOptions options = new FirefoxOptions();
                        driver = new RemoteWebDriver(url, options);
                    } else {
                        throw new RuntimeException("Unsupported browser: " + browser);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Invalid Grid URL");
                }
            }
            return driver;
        }


    private static WebDriver getLocalDriver(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            return new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            return new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}