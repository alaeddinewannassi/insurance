package org.esprit.confirmpage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.esprit.helpers.AlertBoxHelper;
import org.esprit.helpers.GotoOtherPage;
import org.esprit.helpers.InfoBoxHelper;
import org.esprit.insurance.Insurance;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmController implements Initializable {

    @FXML
    private ScrollPane scroll;

    @FXML
    private ComboBox paymentType;

    @FXML
    private Label price;

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField cvv;

    @FXML
    private TextField expirationDateMonth;

    @FXML
    private TextField expirationDateYear;

    @FXML
    private TextField zipCode;

    @FXML
    private VBox cardData;

    @FXML
    private Button submissionButton;

    @FXML
    private ImageView cardTypeImage;

    @FXML
    private Label cardNumberError;

    @FXML
    private Label cvvError;

    @FXML
    private Label expirationDateError;

    @FXML
    private Label zipCodeError;

    private final ObservableList<String> insuranceOptions =
            FXCollections.observableArrayList(
                    "Credit card", "Cash"
            );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        cardTypeImage.setImage(new Image(getClass().getResource("/images/money.png").toExternalForm()));
        cardTypeImage.setFitHeight(26);
        cardTypeImage.setFitWidth(50);

        cardNumber.textProperty().addListener((ov, oldValue, newValue) -> {
            if (cardNumber.getText().length() > 16) {
                String s = cardNumber.getText().substring(0, 16);
                cardNumber.setText(s);
            }

            if (!newValue.matches("\\d*")) {
                cardNumber.setText(newValue.replaceAll("[^\\d]", ""));
            } else {
                switch (CardType.detect(cardNumber.getText())) {
                    case VISA:
                        cardTypeImage.setImage(new Image(getClass().getResource("/images/visa.png").toExternalForm()));
                        break;
                    case MASTERCARD:
                        cardTypeImage.setImage(new Image(getClass().getResource("/images/mastercard.png").toExternalForm()));
                        break;
                    case UNKNOWN:
                    default:
                        cardTypeImage.setImage(new Image(getClass().getResource("/images/money.png").toExternalForm()));
                        break;
                }
            }
        });


        addTextLimiter(cvv, 3);
        addTextLimiter(expirationDateMonth, 2);
        addTextLimiter(expirationDateYear, 2);

        acceptOnlyNumbers(cvv);
        acceptOnlyNumbers(expirationDateMonth);
        acceptOnlyNumbers(expirationDateYear);

        paymentType.setItems(insuranceOptions);
        paymentType.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    switch (newValue.toString()) {
                        case "Credit card":
                            cardData.setVisible(true);
                            break;
                        case "Cash":
                            cardData.setVisible(false);
                            break;
                    }
                    submissionButton.setVisible(true);
                });

        submissionButton.setOnAction(event -> {
            if (validate()) {
                try {
                    openMain();
                } catch (IOException | IllegalStateException | NullPointerException e) {
                    AlertBoxHelper.display("Error", "An error has occurred: Internal Error");
                    e.printStackTrace();
                }
            }
        });

    }

    private boolean validate() {
        boolean result = true;

        if (cardNumber.getText().isEmpty()) {
            cardNumberError.setVisible(true);
            result = false;
        }

        if (cvv.getText().isEmpty()) {
            cvvError.setVisible(true);
            result = false;
        }

        if (expirationDateMonth.getText().isEmpty() || expirationDateYear.getText().isEmpty()) {
            expirationDateError.setVisible(true);
            result = false;
        }

        if (zipCode.getText().isEmpty()) {
            zipCodeError.setVisible(true);
            result = false;
        }

        return result;
    }

    private static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((ov, oldValue, newValue) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }

    private static void acceptOnlyNumbers(final TextField tf) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void openMain() throws IOException {
        InfoBoxHelper.display("Contract creation complete", "Contract creation completed successfully!");
        GotoOtherPage.mainPage(getClass(), (Stage) submissionButton.getScene().getWindow());
    }

    public void getInsurance(Insurance insurance) {
        price.setText("Total Annual Amount " + insurance.calculateAmount() + "€");
        System.out.print(insurance.get().toString());
    }
}
