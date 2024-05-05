package org.esprit.newcontract;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.esprit.helpers.AddUserBoxHelper;
import org.esprit.helpers.AlertBoxHelper;
import org.esprit.helpers.AnimationHelper;
import org.esprit.helpers.GotoOtherPage;
import org.esprit.insurance.Insurance;
import org.esprit.insurance.InsuranceType;
import org.esprit.insurance.coverage.Coverage;
import org.esprit.insurance.coverage.InsuranceCoverages;
import org.esprit.insurance.health.HealthInsurance;
import org.esprit.insurance.home.ResidenceInsurance;
import org.esprit.insurance.vehicle.VehicleInsurance;
import org.esprit.insurance.vehicle.VehicleType;
import org.esprit.model.Residence;
import org.esprit.model.User;
import org.esprit.model.Vehicle;
import org.esprit.repository.ResidenceDao;
import org.esprit.repository.ResidenceDaoImpl;
import org.esprit.repository.VehicleDao;
import org.esprit.repository.VehicleDaoImpl;
import org.esprit.util.FactoryProvider;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

import static org.esprit.insurance.InsuranceType.*;
import static org.esprit.insurance.vehicle.VehicleType.*;

public class NewContractController implements Initializable {

    private User user;
    private ResidenceDao residenceDao;
    private VehicleDao vehicleDao;
    private Insurance insurance;
    private final Stack stack = new Stack();
    private Object object;

    private final ObservableList<User> users = FXCollections.observableArrayList();

    @FXML
    private ScrollPane scroll;
    @FXML
    public HBox registrationDateBox;

    @FXML
    private ComboBox insuranceType;

    @FXML
    private ComboBox vehicleType;

    @FXML
    private VBox vehicleTypeBox;

    @FXML
    private GridPane grid;

    @FXML
    private VBox addMember;

    @FXML
    private Button addMemberButton;

    @FXML
    private Label addMessage;

    @FXML
    private Button confirmButton;

    @FXML
    private Label vehicleTypeError;

    @FXML
    public VBox residenceData;
    @FXML
    public TextField addressLine;
    @FXML
    public TextField floor;
    @FXML
    public TextField sqrm;
    @FXML
    public Label floorError;
    @FXML
    public Label addressError;
    @FXML
    public Label sqrmError;
    @FXML
    private DatePicker registrationDate;

    private final TableView table = new TableView();

    private final ObservableList<InsuranceType> insuranceOptions =
            FXCollections.observableArrayList(
                    HEALTH,
                    VEHICLE,
                    RESIDENCE
            );

    private final ObservableList<VehicleType> vehicleOptions =
            FXCollections.observableArrayList(
                    CAR,
                    MOTORCYCLE,
                    FARM_MACHINERY
            );

    private final Label insuranceErrorLabel = new Label("You must select at least one fuse.");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SessionFactory sessionFactory = FactoryProvider.getSessionFactory();
        residenceDao = new ResidenceDaoImpl(sessionFactory);
        vehicleDao = new VehicleDaoImpl(sessionFactory);

//        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        usersTable();
        initializeComboboxes();

        addMemberButton.setOnAction(event -> {
            User u = new AddUserBoxHelper().addUser(addMessage.getText());

            if (u != null)
                addUserToList(u);
        });

        confirmButton.setOnAction(event -> {
            if (validate()) {
                try {
                    // add residence/ vehicle to user
                    if (insurance instanceof ResidenceInsurance) {
                        var residenceInsurance = (ResidenceInsurance) insurance;
                        var residence = residenceInsurance.getResidence();
                        residence.setAddress(addressLine.getText());
                        residence.setFloor(Integer.parseInt(floor.getText()));
                        residence.setSqrm(Double.parseDouble(sqrm.getText()));
                        residenceDao.saveResidence(residence);
                    }

                    if (insurance instanceof VehicleInsurance) {
                        var vehicleInsurance = (VehicleInsurance) insurance;
                        var vehicle = vehicleInsurance.getVehicle();
                        vehicle.setRegistrationDate(registrationDate.getValue());
                        vehicleDao.saveVehicle(vehicle);
                    }
                    openConfirmPage();
                } catch (IOException | IllegalStateException | NullPointerException e) {
                    AlertBoxHelper.display("Error", "An error has occurred: Internal Error");
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean validate() {
        boolean result = true;
        if (insurance.getCoverages().isEmpty()) {
            insuranceErrorLabel.setVisible(true);
            result = false;
        }
        if (insurance.get() instanceof Vehicle) {
            if (((Vehicle) insurance.get()).getType() == null) {
                vehicleTypeError.setVisible(true);
                result = false;
            }
        }

        if (insurance.get() instanceof Residence) {
            if (sqrm.getText().isEmpty()) {
                sqrmError.setVisible(true);
                result = false;
            } else if (addressLine.getText().isEmpty()) {
                addressError.setVisible(true);
                result = false;
            } else if (floor.getText().isEmpty()) {
                floorError.setVisible(true);
                result = false;
            }
        }

        return result;
    }

    private void initializeComboboxes() {
        insuranceType.setItems(insuranceOptions);
        insuranceType.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    InsuranceType insuranceType = (InsuranceType) newValue;

                    switch (insuranceType) {
                        case HEALTH:
                            AnimationHelper.fadeOut(vehicleTypeBox);
                            generateCoverages(InsuranceCoverages.healthCoverages());
                            insurance = new HealthInsurance();
                            object = new ArrayList<User>();
                            addMessage.setText("Add insurance member");
                            addMember.setVisible(true);
                            setTableVisibility(true);
                            confirmButton.setVisible(true);
                            residenceData.setVisible(false);
                            registrationDateBox.setVisible(false);
                            break;
                        case VEHICLE:
                            AnimationHelper.fadeIn(vehicleTypeBox);
                            generateCoverages(InsuranceCoverages.vehicleCoverages());
                            insurance = new VehicleInsurance();
                            object = new Vehicle();
                            addMember.setVisible(false);
                            setTableVisibility(false);
                            residenceData.setVisible(false);
                            registrationDateBox.setVisible(true);
                            break;
                        case RESIDENCE:
                            AnimationHelper.fadeOut(vehicleTypeBox);
                            generateCoverages(InsuranceCoverages.residenceCoverages());
                            insurance = new ResidenceInsurance();
                            object = new Residence();
                            addMessage.setText("Add resident");
                            addMember.setVisible(true);
                            setTableVisibility(true);
                            confirmButton.setVisible(true);
                            residenceData.setVisible(true);
                            registrationDateBox.setVisible(false);
                            break;
                    }
                    addUserToList(user);
                    insurance.set(object);
                }));


        vehicleType.setItems(vehicleOptions);
        vehicleType.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    VehicleType vehicleType = (VehicleType) newValue;

                    switch (vehicleType) {
                        case CAR:
                            ((Vehicle) object).setType(CAR);
                            break;
                        case MOTORCYCLE:
                            ((Vehicle) object).setType(MOTORCYCLE);
                            break;
                        case FARM_MACHINERY:
                            ((Vehicle) object).setType(FARM_MACHINERY);
                            break;
                    }
                    confirmButton.setVisible(true);
                    ((Vehicle) object).setOwner(user);
                }));
    }

    private void addUserToList(User usr) {
        if (object instanceof ArrayList<?>) {
            if (!((ArrayList<User>) object).contains(usr))
                ((ArrayList<User>) object).add(usr);
        } else if (object instanceof Residence) {
            ((Residence) object).getInhabitants().add(usr);
        }

        if (!users.contains(usr))
            users.addAll(usr);
    }

    private void generateCoverages(List<Coverage> coverages) {
        if (!stack.empty()) {
            grid.getChildren().remove(stack.get(0));
            stack.pop();
        }

        VBox mainLayout = new VBox(15);


        for (Coverage coverage : coverages) {
            VBox child = new VBox(10);
            CheckBox checkBox = new CheckBox(coverage.getName());
            Label price = new Label(coverage.getPrice() + "€/month");
            checkBox.setOnAction(checkBoxAction);

            Label label = new Label(coverage.getDescription());
            child.getChildren().addAll(checkBox, price, label);
            mainLayout.getChildren().add(child);
        }

        insuranceErrorLabel.setTextFill(Paint.valueOf("#FF0000"));
        mainLayout.getChildren().add(insuranceErrorLabel);
        grid.add(mainLayout, 0, 2);

        insuranceErrorLabel.setVisible(false);

        stack.push(mainLayout);
    }

    private void usersTable() {
        table.setPlaceholder(new Label("There are no new users"));
        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("Name");
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last name");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("lastName"));

        TableColumn ageCol = new TableColumn("Age");
        ageCol.setCellValueFactory(
                new PropertyValueFactory<User, Integer>("age"));

        table.setItems(users);

        table.getColumns().addAll(firstNameCol, lastNameCol, ageCol);

        grid.add(table, 0, 4);

        table.setVisible(false);
    }

    private void setTableVisibility(boolean visibility) {
        table.setVisible(visibility);
    }

    private final EventHandler<ActionEvent> checkBoxAction = event -> {
        if (event.getSource() instanceof CheckBox) {
            CheckBox chk = (CheckBox) event.getSource();
            List<Coverage> coverages = null;
            Coverage selectedCoverage = null;
            if (insurance instanceof HealthInsurance) {
                coverages = InsuranceCoverages.healthCoverages();
            } else if (insurance instanceof VehicleInsurance) {
                coverages = InsuranceCoverages.vehicleCoverages();
            } else if (insurance instanceof ResidenceInsurance) {
                coverages = InsuranceCoverages.residenceCoverages();
            }

            assert coverages != null;
            for (Coverage coverage : coverages) {
                if (coverage.getName().equals(chk.getText())) {
                    selectedCoverage = coverage;
                }
            }

            if (chk.isSelected()) {
                insurance.addCoverage(selectedCoverage);
            } else {
                insurance.removeCoverage(selectedCoverage);
            }
        }
    };

    public void getUser(User user) {
        this.user = user;
    }

    private void openConfirmPage() throws IOException {
        GotoOtherPage.confirmPage(insurance, getClass(), (Stage) confirmButton.getScene().getWindow());
    }
}
