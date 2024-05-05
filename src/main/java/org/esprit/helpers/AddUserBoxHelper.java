package org.esprit.helpers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.esprit.model.User;

import java.util.Calendar;

public class AddUserBoxHelper {

    private final TextField nameTextField = new TextField();
    private final Label nameError = new Label("Please provide a first name.");
    private final TextField surnameTextField = new TextField();
    private final Label surnameError = new Label("Please provide a last name.");
    private final DatePicker agePicker = new DatePicker();
    private final Label ageError = new Label("Please provide a date of birth.");
    private User user = null;
    private final Stage stage = new Stage();

    public User addUser(String title) {

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinHeight(300);
        stage.setMinWidth(450);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        Label name = new Label("First name");
        gridPane.add(name, 0, 0);
        Label surname = new Label("Last name");
        gridPane.add(surname, 0, 2);
        Label age = new Label("Date of birth");
        gridPane.add(age, 0, 3);

        VBox nameBox = new VBox(10);

        nameError.setVisible(false);
        nameError.setTextFill(Paint.valueOf("#FF0000"));
        nameBox.getChildren().addAll(nameTextField, nameError);
        nameBox.setAlignment(Pos.BOTTOM_RIGHT);
        gridPane.add(nameBox, 1, 0);

        VBox surnameBox = new VBox(10);
        surnameError.setVisible(false);
        surnameError.setTextFill(Paint.valueOf("#FF0000"));
        surnameBox.getChildren().addAll(surnameTextField, surnameError);
        surnameBox.setAlignment(Pos.BOTTOM_RIGHT);
        gridPane.add(surnameBox, 1, 2);

        VBox ageBox = new VBox(10);
        ageError.setVisible(false);
        ageError.setTextFill(Paint.valueOf("#FF0000"));
        ageBox.getChildren().addAll(agePicker, ageError);
        ageBox.setAlignment(Pos.BOTTOM_RIGHT);
        gridPane.add(ageBox, 1, 3);

        HBox buttonBox = new HBox(10);
        Button button = new Button("Add");
        buttonBox.getChildren().addAll(button);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        gridPane.add(buttonBox, 1, 4);

        button.setOnAction(event -> {
            if (validate()) {
                user = new User();
                user.setFirstName(nameTextField.getText().trim());
                user.setLastName(surnameTextField.getText().trim());
                user.setAge(Calendar.getInstance().get(Calendar.YEAR) - agePicker.getValue().getYear());
                stage.close();
            }
        });

        Scene scene = new Scene(gridPane, 450, 300);
        scene.getStylesheets().addAll(String.valueOf(getClass().getResource("/stylesheets/main.css")));
        stage.setScene(scene);
        stage.showAndWait();


        return user;
    }

    private boolean validate() {
        boolean result = true;
        if (nameTextField.getText().isEmpty()) {
            nameError.setVisible(true);
            result = false;
        } else {
            nameError.setVisible(false);
        }

        if (surnameTextField.getText().isEmpty()) {
            surnameError.setVisible(true);
            result = false;
        } else {
            surnameError.setVisible(false);
        }

        if (agePicker.getValue() == null) {
            ageError.setVisible(true);
            result = false;
        } else {
            ageError.setVisible(false);
        }

        return result;
    }
}
