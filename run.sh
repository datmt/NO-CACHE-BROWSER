#!/bin/bash

# Get the directory where the script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
JAR_PATH="$SCRIPT_DIR/target/no-cache-browser-1.0-SNAPSHOT.jar"

# Run the application with JavaFX modules
java --module-path "$SCRIPT_DIR/target/classes" \
     --add-modules javafx.controls,javafx.fxml,javafx.graphics \
     --add-opens javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED \
     --add-opens javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED \
     --add-opens javafx.base/com.sun.javafx.binding=ALL-UNNAMED \
     --add-opens javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED \
     --add-opens javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED \
     -jar "$JAR_PATH"
