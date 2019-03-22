package sample;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.openqa.selenium.WebDriver;

import java.awt.Desktop;


import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class Controller {

    @FXML
    BorderPane rootPane;


    @FXML
    TextField chromePathTF, firefoxPathTF, startURLTF;

    @FXML
    RadioButton chromeRadio, firefoxRadio;


    ArrayList<WebDriver> allDrivers = new ArrayList<WebDriver>();
    @FXML
    public void initialize()
    {
        ToggleGroup radioToggleGroup = new ToggleGroup();
        firefoxRadio.setToggleGroup(radioToggleGroup);
        chromeRadio.setToggleGroup(radioToggleGroup);
        firefoxRadio.setSelected(true);

        setChromePathTF();
        setFirefoxPathTF();
    }

    public void startBrowsing()
    {
        String startURL = startURLTF.getText();

        if (startURL.trim().equals(""))
            startURL = "https://www.binarycarpenter.com";

        if (chromeRadio.isSelected())
        {
            NFCBrowser nfcBrowser = new NFCBrowser(startURL, "chrome");
            allDrivers.add(nfcBrowser.getWebDriver());
            nfcBrowser.visit();
        } else
        {
            NFCBrowser nfcBrowser = new NFCBrowser(startURL, "firefox");
            allDrivers.add(nfcBrowser.getWebDriver());
            nfcBrowser.visit();

        }
    }

    public void visitBinaryCarpenter()
    {
        try {
            Desktop.getDesktop().browse(new URI("https://www.binarycarpenter.com?src=nfcb_app"));
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public void selectChromePath()
    {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Chrome driver file");
        File chromeDriver = chooser.showOpenDialog(rootPane.getScene().getWindow());

        if (chromeDriver!=null)
        {
            new UserSettings().setChromeDriverPath(chromeDriver.getPath());
            setChromePathTF();
        }
    }

    private void setChromePathTF()
    {
        String chromePath = new UserSettings().getChromeDriverPath();
        if (chromePathTF!=null )
            chromePathTF.setText(chromePath);
    }

    public void killAllBrowsers()
    {
        for (WebDriver webDriver : allDrivers)
            webDriver.close();
    }

    private void setFirefoxPathTF()
    {
        String firefoxPath = new UserSettings().getFirefoxDriverPath();
        if (firefoxPath!=null )
            firefoxPathTF.setText(firefoxPath);
    }


    public void selectFirefoxPath()
    {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Firefox driver file");
        File firefoxDriver = chooser.showOpenDialog(rootPane.getScene().getWindow());

        if (firefoxDriver!=null)
        {
            new UserSettings().setFirefoxDriverPath(firefoxDriver.getPath());
            setFirefoxPathTF();
        }
    }

}
