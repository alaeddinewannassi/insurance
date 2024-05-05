package org.esprit.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.esprit.confirmpage.ConfirmController;
import org.esprit.insurance.Insurance;
import org.esprit.model.User;
import org.esprit.newcontract.NewContractController;

import java.io.IOException;

public class GotoOtherPage {

    public static void main(Class clazz, Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(clazz.getResource("/fxml/login.fxml"));
        Parent sceneMain = fxmlLoader.load();
        primaryStage.setTitle("Insurance - Login");
        Scene scene = new Scene(sceneMain, 1000, 675);
        scene.getStylesheets().add(String.valueOf(clazz.getResource("/stylesheets/main.css")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void mainPage(Class clazz, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(clazz.getResource("/fxml/login.fxml"));
        Parent sceneMain = loader.load();
        stage.setTitle("Insurance - Login");
        Scene scene = new Scene(sceneMain, 1000, 675);
        scene.getStylesheets().add(String.valueOf(clazz.getResource("/stylesheets/main.css")));
        stage.setScene(scene);
    }

    public static void newContract(User user, Class clazz, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(clazz.getResource("/fxml/newcontract.fxml"));
        Parent sceneMain = loader.load();
        NewContractController controller = loader.getController();
        controller.getUser(user);

        stage.setTitle("Create a new contract");
        Scene scene = new Scene(sceneMain, 1000, 675);
        scene.getStylesheets().add(String.valueOf(clazz.getResource("/stylesheets/main.css")));
        stage.setScene(scene);
    }

    public static void confirmPage(Insurance insurance, Class clazz, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(clazz.getResource("/fxml/confirm.fxml"));
        Parent sceneMain = loader.load();
        ConfirmController controller = loader.getController();
        controller.getInsurance(insurance);

        stage.setTitle("validation and Payment");
        Scene scene = new Scene(sceneMain, 1000, 675);
        scene.getStylesheets().add(String.valueOf(clazz.getResource("/stylesheets/main.css")));
        stage.setScene(scene);
    }
}
