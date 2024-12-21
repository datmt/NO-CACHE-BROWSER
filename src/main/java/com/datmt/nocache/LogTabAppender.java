package com.datmt.nocache;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.PatternLayout;
import javafx.application.Platform;

public class LogTabAppender extends AppenderBase<ILoggingEvent> {
    private static LogTabController logTabController;
    private PatternLayout layout;

    @Override
    public void start() {
        layout = new PatternLayout();
        layout.setContext(getContext());
        layout.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        layout.start();
        super.start();
    }

    public static void setLogTabController(LogTabController controller) {
        logTabController = controller;
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (logTabController != null && layout != null) {
            String formattedMessage = layout.doLayout(event);
            Platform.runLater(() -> {
                try {
                    logTabController.appendLog(formattedMessage);
                } catch (Exception e) {
                    System.err.println("Error appending log: " + e.getMessage());
                }
            });
        }
    }
}
