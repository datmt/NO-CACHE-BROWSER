package com.datmt.nocache;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @FXML
    BorderPane rootPane;

    @FXML
    TextArea favoriteURlsTA;

    @FXML
    ListView favoriteURLLV;
    @FXML
    TextField chromePathTF, firefoxPathTF, startURLTF;

    @FXML
    CheckBox startNewBrowserCB;

    @FXML
    RadioButton chromeRadio, firefoxRadio;


    private ArrayList<WebDriver> allFireFoxDrivers = new ArrayList<WebDriver>();
    private ArrayList<WebDriver> allChromeDrivers = new ArrayList<WebDriver>();
    @FXML
    public void initialize()
    {
        logger.info("Initializing browser controller");
        ToggleGroup radioToggleGroup = new ToggleGroup();
        firefoxRadio.setToggleGroup(radioToggleGroup);
        chromeRadio.setToggleGroup(radioToggleGroup);
        firefoxRadio.setSelected(true);
        startNewBrowserCB.setSelected(true);

        setChromePathTF();
        setFirefoxPathTF();
        populateFavoriteURLs();

        populateFavoriteURLsLV();
        handleClickOnFavoriteURLsClick();
        logger.info("Browser controller initialized");
    }

    public void startBrowsing()
    {
        logger.info("Starting browsing");
        String startURL = startURLTF.getText();

        if (startURL.trim().equals(""))
            startURL = "https://www.binarycarpenter.com";




        if (chromeRadio.isSelected())
        {
            NFCBrowser nfcBrowser = new NFCBrowser(startURL, "chrome");
            allChromeDrivers.add(nfcBrowser.getWebDriver());
            nfcBrowser.visit();
            logger.info("Started browsing with Chrome");

        } else
        {
            NFCBrowser nfcBrowser = new NFCBrowser(startURL, "firefox");
            allFireFoxDrivers.add(nfcBrowser.getWebDriver());
            nfcBrowser.visit();
            logger.info("Started browsing with Firefox");
        }
    }

    public void visitBinaryCarpenter()
    {
        logger.info("Visiting Binary Carpenter");
        try {
            Desktop.getDesktop().browse(new URI("https://www.binarycarpenter.com?src=nfcb_app"));
        } catch (Exception ex)
        {
            logger.error("Error visiting Binary Carpenter", ex);
        }

    }

    public void selectChromePath()
    {
        logger.info("Selecting Chrome path");
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Chrome driver file");
        File chromeDriver = chooser.showOpenDialog(rootPane.getScene().getWindow());

        if (chromeDriver!=null)
        {
            new UserSettings().setChromeDriverPath(chromeDriver.getPath());
            setChromePathTF();
            logger.info("Selected Chrome path: {}", chromeDriver.getPath());
        }
    }

    private void setChromePathTF()
    {
        logger.info("Setting Chrome path text field");
        String chromePath = new UserSettings().getChromeDriverPath();
        if (chromePathTF!=null )
            chromePathTF.setText(chromePath);
    }

    public void killAllBrowsers()
    {
        logger.info("Killing all browsers");
        for (WebDriver webDriver : allFireFoxDrivers)
            if (webDriver!=null)
            {
                try
                {
                    webDriver.close();
                } catch (Exception ex)
                {
                    logger.error("Error killing Firefox browser", ex);
                }

            }

        for (WebDriver webDriver : allChromeDrivers)
            if (webDriver!=null)
            {
                try
                {
                    webDriver.close();
                } catch (Exception ex)
                {
                    logger.error("Error killing Chrome browser", ex);
                }

            }

        NFCAlert.info("All instances killed!");
        logger.info("All browsers killed");
    }

    private void setFirefoxPathTF()
    {
        logger.info("Setting Firefox path text field");
        String firefoxPath = new UserSettings().getFirefoxDriverPath();
        if (firefoxPath!=null )
            firefoxPathTF.setText(firefoxPath);
    }


    public void selectFirefoxPath()
    {
        logger.info("Selecting Firefox path");
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Firefox driver file");
        File firefoxDriver = chooser.showOpenDialog(rootPane.getScene().getWindow());

        if (firefoxDriver!=null)
        {
            new UserSettings().setFirefoxDriverPath(firefoxDriver.getPath());
            setFirefoxPathTF();
            logger.info("Selected Firefox path: {}", firefoxDriver.getPath());
        }
    }

    public void saveFavoriteURLs()
    {
        logger.info("Saving favorite URLs");
        new UserSettings().setFavoriteUrls(favoriteURlsTA.getText());
        populateFavoriteURLsLV();
    }

    private void populateFavoriteURLsLV()
    {
        logger.info("Populating favorite URLs list view");
        favoriteURLLV.getItems().clear();
        ObservableList<String> urls = FXCollections.observableArrayList();
        urls.addAll(new UserSettings().getFavoriteUrls().trim().split("\n"));
        favoriteURLLV.setItems(urls);
    }

    private void populateFavoriteURLs()
    {
        logger.info("Populating favorite URLs text area");
        favoriteURlsTA.setText(new UserSettings().getFavoriteUrls());
    }

    private void handleClickOnFavoriteURLsClick()
    {
        logger.info("Handling click on favorite URLs");
        favoriteURLLV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String url = favoriteURLLV.getSelectionModel().getSelectedItem().toString();
                logger.info("Clicked on favorite URL: {}", url);
                startURLTF.setText(url);
                if (event.getClickCount() > 1)
                {
                    startBrowsing();
                }

            }
        });
    }

}
