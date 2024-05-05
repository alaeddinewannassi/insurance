package org.esprit.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import org.esprit.Main;
import org.esprit.model.User;
import org.esprit.repository.UserDao;
import org.esprit.repository.UserDaoImpl;
import org.esprit.util.FactoryProvider;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.Calendar;

public class SignupController {

    @FXML
    JFXTextField firstName;
    @FXML
    JFXTextField lastName;
    @FXML
    DatePicker age;
    @FXML
    JFXTextField userName;
    @FXML
    JFXPasswordField password;
    @FXML
    JFXPasswordField password1;

    private final UserDao userDao;

    public SignupController() {
        SessionFactory sessionFactory = FactoryProvider.getSessionFactory();
        userDao = new UserDaoImpl(sessionFactory);
    }

    public void createAccount() throws IOException {
        String userNameText = userName.getText();
        String firstNameText = firstName.getText();
        String lastNameText = lastName.getText();
        Integer ageText = age.getValue() == null ? null : Calendar.getInstance().get(Calendar.YEAR) - age.getValue().getYear();
        String passwordText = password.getText();
        String confirmPassword = password1.getText();

        if (firstNameText.isEmpty() || lastNameText.isEmpty() || ageText == null || userNameText.isEmpty() || passwordText.isEmpty() || confirmPassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Empty Fields!");
            alert.setContentText("All Fields are Required");
            alert.showAndWait();
            return;
        }
        if (passwordText.equals(confirmPassword)) {
            User user = new User(userNameText, passwordText, firstNameText, lastNameText, ageText);
            boolean isAdded = userDao.saveUser(user);
            if (isAdded) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Success!");
                alert.setContentText("Record Saved Successfully");
                alert.showAndWait();
                signIn();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Failure!");
                alert.setContentText("Failed to Save Record");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Password Mismatch");
            alert.setContentText("Password Doesn't match.");
            alert.showAndWait();
        }
    }

    @FXML
    public void signIn() throws IOException {
        String fxmlFile = "/fxml/Login.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add("/stylesheets/styles.css");
        Main.primaryStage.hide();
        Main.primaryStage.setScene(scene);
        Main.primaryStage.show();
    }
}
