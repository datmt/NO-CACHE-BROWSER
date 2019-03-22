package sample;

import java.util.prefs.Preferences;

public class UserSettings {

    private static final String CHROME_DRIVER_PATH = "chrome_driver_path";
    private static final String FIREFOX_DRIVER_PATH = "firefox_driver_path";

    private Preferences getPrefs()
    {
        return Preferences.userNodeForPackage(this.getClass());
    }

    public String getChromeDriverPath()
    {
        return getPrefs().get(CHROME_DRIVER_PATH, null);
    }

    public String getFirefoxDriverPath() {
        return getPrefs().get(FIREFOX_DRIVER_PATH, null);
    }

    public void setChromeDriverPath(String chromeDriverPath)
    {
        getPrefs().put(CHROME_DRIVER_PATH, chromeDriverPath);
    }

    public void setFirefoxDriverPath(String firefoxDriverPath)
    {
        getPrefs().put(FIREFOX_DRIVER_PATH, firefoxDriverPath);
    }


}
