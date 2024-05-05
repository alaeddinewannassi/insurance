package org.esprit.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import org.esprit.Main;
import org.esprit.model.User;
import org.esprit.newcontract.NewContractController;
import org.esprit.repository.UserDao;
import org.esprit.repository.UserDaoImpl;
import org.esprit.util.FactoryProvider;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    JFXTextField userName;
    @FXML
    JFXPasswordField password;

    private SessionFactory sessionFactory;
    private UserDao userDao;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.sessionFactory = FactoryProvider.getSessionFactory();
        userDao = new UserDaoImpl(sessionFactory);
    }

    @FXML
    public void signInButtonOnAction() throws IOException {
        String userNameText = userName.getText();
        String passwordText = password.getText();
        if (userNameText.isEmpty() || passwordText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Both Username and Password are required");
            alert.showAndWait();
        } else {
            User user = userDao.getUser(userNameText, passwordText);
            if (user != null) {
                String fxmlFile = "/fxml/newcontract.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Scene scene = new Scene(loader.load());
                scene.getStylesheets().add("/stylesheets/styles.css");
                NewContractController controller = loader.getController();
                controller.getUser(user);
                Main.primaryStage.hide();
                Main.primaryStage.setScene(scene);
                Main.primaryStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Login Failed");
                alert.setContentText("Invalid Username or Password");
                alert.showAndWait();
            }
        }

    }

    @FXML
    public void signUpButtonOnAction() throws IOException {
        String fxmlFile = "/fxml/signup.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add("/stylesheets/styles.css");
        Main.primaryStage.hide();
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }

}
