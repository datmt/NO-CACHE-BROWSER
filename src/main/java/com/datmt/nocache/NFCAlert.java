package com.datmt.nocache;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;



public class NFCAlert {

    public static void error(String message)
    {
        Notifications.create()
                .darkStyle()
                .title("Error")
                .text(message)
                .graphic(new Rectangle(30, 30, Color.RED)) // sets node to display
                .hideAfter(Duration.seconds(5))
                .show();
    }

    public static void info(String message)
    {
        Notifications.create()
                .darkStyle()
                .title("Info")
                .text(message)
                .graphic(new Rectangle(30, 30, Color.BLUE)) // sets node to display
                .hideAfter(Duration.seconds(5))
                .show();
    }
}
