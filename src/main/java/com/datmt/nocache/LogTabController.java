package com.datmt.nocache;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTabController {
    private static final Logger logger = LoggerFactory.getLogger(LogTabController.class);

    @FXML
    private TextArea logTextArea;

    @FXML
    private Button clearButton;

    @FXML
    public void initialize() {
        if (logTextArea == null) {
            System.err.println("LogTextArea is null in initialize!");
            return;
        }

        // Make the text area read-only and use monospace font
        logTextArea.setEditable(false);
        logTextArea.setStyle("-fx-font-family: 'monospace';");
        
        // Set up clear button
        clearButton.setOnAction(event -> clearLogs());
        
        // Register this controller with the appender
        LogTabAppender.setLogTabController(this);
        
        // Test log message
        logger.info("Log tab initialized");
    }

    public void appendLog(String logMessage) {
        if (logTextArea == null) {
            System.err.println("LogTextArea is null when appending: " + logMessage);
            return;
        }
        
        // Append the message and scroll to bottom
        logTextArea.appendText(logMessage);
        logTextArea.setScrollTop(Double.MAX_VALUE);
    }

    @FXML
    private void clearLogs() {
        if (logTextArea != null) {
            logTextArea.clear();
            logger.info("Logs cleared");
        }
    }
}
