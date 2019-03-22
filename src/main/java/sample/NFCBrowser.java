package sample;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.util.HashMap;

public class NFCBrowser {

    private String startURL;

    private WebDriver webDriver;

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public NFCBrowser(String startURL, String browserName)
    {
        this.startURL = startURL;
        UserSettings us = new UserSettings();


        if (browserName.equals("firefox"))
        {
            if (us.getFirefoxDriverPath() == null)
            {
                NFCAlert.error("You don't have firefox driver. Download and set its path in settings");
                return;
            }

            if (!new File(us.getFirefoxDriverPath()).exists())
            {
                NFCAlert.error("Your path to Firefox driver is invalid. Please set it again in settings");
                return;
            }

            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-application-cache");
            System.setProperty("webdriver.gecko.driver", us.getFirefoxDriverPath());
            webDriver = new FirefoxDriver(options);


        } else if (browserName.equals("chrome"))
        {
            if (us.getChromeDriverPath() == null)
            {
                NFCAlert.error("You don't have Chrome driver. Download and set its path in settings");
                return;
            }

            if (!new File(us.getChromeDriverPath()).exists())
            {
                NFCAlert.error("Your path to Chrome driver is invalid. Please set it again in settings");
                return;
            }
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-application-cache");
            System.setProperty("webdriver.chrome.driver", us.getChromeDriverPath());
            webDriver = new ChromeDriver(options);

        }


    }


    public void visit()
    {
        if (webDriver == null)
        {
            NFCAlert.error("Web driver is null, please set it first in settings");
            return;
        }
        webDriver.manage().deleteAllCookies();

        webDriver.get(startURL);

    }
}
