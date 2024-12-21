#!/bin/bash
java --module-path target/lib \
     --add-modules javafx.controls,javafx.fxml,javafx.graphics \
     -jar target/no-cache-browser-1.0-SNAPSHOT.jar
