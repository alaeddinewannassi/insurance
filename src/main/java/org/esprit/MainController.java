package org.esprit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.esprit.helpers.AlertBoxHelper;
import org.esprit.helpers.GotoOtherPage;
import org.esprit.model.User;
import org.esprit.util.FactoryProvider;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private SessionFactory sessionFactory;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField firstName;

    @FXML
    private Label firstNameError;

    @FXML
    private TextField lastName;

    @FXML
    private Label lastNameError;

    @FXML
    private DatePicker age;

    @FXML
    private Label ageError;

    @FXML
    private StackPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sessionFactory = FactoryProvider.getSessionFactory();

        loginBtn.setOnAction((event) -> {
            if (validate()) {
                try {
                    openNewContractScreen(new User(firstName.getText().trim(), lastName.getText().trim(), Calendar.getInstance().get(Calendar.YEAR) - age.getValue().getYear()));
                } catch (IOException | IllegalStateException | NullPointerException e) {
                    AlertBoxHelper.display("Error", "An error has occurred: Internal Error");
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean validate() {
        boolean result = true;
        if (firstName.getText().isEmpty()) {
            firstNameError.setVisible(true);
            result = false;
        } else {
            firstNameError.setVisible(false);
        }

        if (lastName.getText().isEmpty()) {
            lastNameError.setVisible(true);
            result = false;
        } else {
            lastNameError.setVisible(false);
        }

        if (age.getValue() == null) {
            ageError.setVisible(true);
            result = false;
        } else {
            ageError.setVisible(false);
        }

        return result;
    }

    private void openNewContractScreen(User user) throws IOException {
        GotoOtherPage.newContract(user, getClass(), (Stage) loginBtn.getScene().getWindow());
    }
}
