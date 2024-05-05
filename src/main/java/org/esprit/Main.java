package org.esprit;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        org.esprit.helpers.GotoOtherPage.main(getClass(), primaryStage);
        Main.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}