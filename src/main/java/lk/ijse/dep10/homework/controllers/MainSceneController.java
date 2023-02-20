package lk.ijse.dep10.homework.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lk.ijse.dep10.homework.model.Student;
import lk.ijse.dep10.homework.util.Gender;

import java.util.ArrayList;

public class MainSceneController {

    public TableView<Student> tblInfo;
    public Label lblGender;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBackward;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnForward;

    @FXML
    private Button btnNewStudent;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnSave;

    @FXML
    private CheckBox chkPartTime;

    @FXML
    private ComboBox<String> cmbDegree;

    @FXML
    private Label lblShortCode;

    @FXML
    private Label lblId;

    @FXML
    private ListView<String> lstContacts;

    @FXML
    private ListView<String> lstModules;

    @FXML
    private ListView<String> lstSelectedModules;

    @FXML
    private ListView<Student> lstStudents;

    @FXML
    private RadioButton rdoFemale;

    @FXML
    private RadioButton rdoMale;

    @FXML
    private TextField txtContacts;

    @FXML
    private TextField txtName;
    private int lastCivilId,lastSoftwareId;
    public void initialize() {
        lastSoftwareId=0;
        lastCivilId=0;
        ObservableList<String> degreeList = cmbDegree.getItems();
        degreeList.addAll("Civil Engineering", "Computer Engineering");

        cmbDegree.getSelectionModel().selectedItemProperty().addListener((value, previous, current) -> {
            lstModules.getItems().clear();
            lstSelectedModules.getItems().clear();
            if (cmbDegree.getSelectionModel().isSelected(0)) {
                lstModules.getItems().addAll("Structural", "Geology", "Mathematics", "Environmental Engineering");
                lblShortCode.setText("CE");
            } else {
                lstModules.getItems().addAll("OOP", "Networking", "MYSQL", "SpringBoot");
            lblShortCode.setText("SE");
            }
        });

        lstModules.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lstSelectedModules.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        txtName.textProperty().addListener((value, previousText, currentText) -> {
            txtName.getStyleClass().remove("invalid");
            for (char c : txtName.getText().strip().toCharArray()) {
                if (!(Character.isLetter(c) || Character.isSpaceChar(c))) {
                    txtName.getStyleClass().add("invalid");
                    return;
                }

            }
        });
        txtContacts.textProperty().addListener((value, previousContactNo, currentContactNo) -> {
            txtContacts.getStyleClass().remove("invalid");

            if (currentContactNo.isEmpty()) {
                btnAdd.setDisable(true);
                return;

            }
            btnAdd.setDisable(false);
            if (!(currentContactNo.strip().length() == 11 &&
                    isNumber(currentContactNo.strip().substring(0, 3)) && isNumber(currentContactNo.substring(4)) &&
                    currentContactNo.charAt(3) == '-')) {
                txtContacts.getStyleClass().add("invalid");
                btnAdd.setDisable(true);

            }
        });
        lstContacts.getSelectionModel().selectedItemProperty().addListener((value, previous, current) -> {
            if (current == null) {
                btnRemove.setDisable(true);
                return;
            }
            btnRemove.setDisable(false);
        });


        lstModules.getSelectionModel().selectedItemProperty().addListener((value, previous, current) -> {
            btnForward.setDisable(current == null);
        });
        lstSelectedModules.getSelectionModel().selectedItemProperty().addListener((value, previous, current) -> {
            btnBackward.setDisable(current == null);
        });
        tblInfo.getSelectionModel().selectedItemProperty().addListener((value,previous,current)->{
            btnDelete.setDisable(current==null);
            if (current==null) return;
            lblId.setText(current.getId());
            txtName.setText(current.getName());
            lstContacts.getItems().addAll(current.getContacts());
            if (current.getGender() == Gender.MALE){
                rdoMale.getToggleGroup().selectToggle(rdoMale);
            }else{
                rdoFemale.getToggleGroup().selectToggle(rdoFemale);
            }
            txtContacts.clear();
            lstContacts.getItems().clear();
            lstContacts.getItems().addAll(current.getContacts());
            lstSelectedModules.getItems().clear();
            lstSelectedModules.getItems().addAll(current.getSelectedModules());
            if (current.getDegree()=="Civil Engineering"){
             //   lblId.getText().replaceAll("SE","CE");
                lstModules.getItems().clear();
                lstModules.getItems().addAll("Structural", "Geology", "Mathematics", "Environmental Engineering");
                lstModules.getItems().remove(current.getSelectedModules());
            }else{
             //   lblId.getText().replaceAll("CE","SE");
                lstModules.getItems().clear();
                lstModules.getItems().addAll("OOP", "Networking", "MYSQL", "SpringBoot");
                lstModules.getItems().remove(current.getSelectedModules());
            }
            lstModules.getSelectionModel().clearSelection();
            lstContacts.getSelectionModel().clearSelection();
            lstSelectedModules.getSelectionModel().clearSelection();
        });
    }

    public boolean isNumber(String contactNo) {
        for (char c : contactNo.toCharArray()) {
            if (!(Character.isDigit(c))) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        txtContacts.getStyleClass().remove("invalid");
        for (String contact : lstContacts.getItems()) {
            if (contact.equals(txtContacts.getText())) {
                txtContacts.getStyleClass().add("invalid");
                txtContacts.requestFocus();
                txtContacts.selectAll();
                return;
            }
        }
        lstContacts.getItems().add(txtContacts.getText().strip());
        txtContacts.clear();
        txtContacts.requestFocus();
    }

    @FXML
    void btnBackwardOnAction(ActionEvent event) {
        lstModules.getItems().addAll(lstSelectedModules.getSelectionModel().getSelectedItems());
        lstSelectedModules.getItems().removeAll(lstSelectedModules.getSelectionModel().getSelectedItems());
        lstSelectedModules.getSelectionModel().clearSelection();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        tblInfo.getItems().remove(tblInfo.getSelectionModel().getSelectedItem());
        tblInfo.getSelectionModel().clearSelection();
    }

    @FXML
    void btnForwardOnAction(ActionEvent event) {
        lstSelectedModules.getItems().addAll(lstModules.getSelectionModel().getSelectedItems());
        lstModules.getItems().removeAll(lstModules.getSelectionModel().getSelectedItems());
        lstModules.getSelectionModel().clearSelection();
        lstSelectedModules.getStyleClass().remove("invalid");
        lstModules.requestFocus();
    }


    @FXML
    void btnNewStudentOnAction(ActionEvent event) {
        tblInfo.getSelectionModel().clearSelection();
        lblGender.getStyleClass().remove("invalid");
        lstContacts.getStyleClass().remove("invalid");
        lstSelectedModules.getStyleClass().remove("invalid");
        txtName.getStyleClass().remove("invalid");
        lblGender.getStyleClass().remove("invalid");

        txtName.setDisable(false);
        txtContacts.setDisable(false);
        rdoMale.setDisable(false);
        rdoFemale.setDisable(false);
        lstContacts.setDisable(false);
        lstModules.setDisable(false);
        lstSelectedModules.setDisable(false);
        btnSave.setDisable(false);
        btnAdd.setDisable(false);
        cmbDegree.setDisable(false);
        chkPartTime.setDisable(false);
        tblInfo.setDisable(false);
        txtName.clear();
        txtContacts.clear();
        rdoMale.getToggleGroup().selectToggle(null);
        txtName.requestFocus();
        cmbDegree.getSelectionModel().select(0);

        lstContacts.getItems().clear();
        lstSelectedModules.getItems().clear();
        lstModules.getItems().clear();
        lstModules.getItems().clear();
        lblId.setText("");
        lblShortCode.setText("");
        if (cmbDegree.getSelectionModel().isSelected(0)) {
            lstModules.getItems().addAll("Structural", "Geology", "Mathematics", "Environmental Engineering");

        } else {
            lstModules.getItems().addAll("OOP", "Networking", "MYSQL", "SpringBoot");
        }


    }

    public String generateNewId() {


            if (cmbDegree.getSelectionModel().isSelected(0)){
                ++lastCivilId;
                return String.format("CE-S%03d",lastCivilId);
            }
           else{
                ++lastSoftwareId;
                return String.format("CE-S%03d",lastSoftwareId);
            }

    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        lstContacts.getItems().remove(lstContacts.getSelectionModel().getSelectedItem());
        lstContacts.getSelectionModel().clearSelection();
        txtContacts.requestFocus();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean isDataValid = true;
        lblGender.setTextFill(Color.BLACK);
        lstContacts.getStyleClass().remove("invalid");
        lstSelectedModules.getStyleClass().remove("invalid");
        lblGender.getStyleClass().remove("invalid");


        // Data Validation

        if (lstSelectedModules.getItems().size() < 2) {
            isDataValid = false;
            lstSelectedModules.getStyleClass().add("invalid");
            lstModules.requestFocus();
        }
        if (rdoMale.getToggleGroup().getSelectedToggle() == null) {
            isDataValid = false;
            lblGender.setTextFill(Color.RED);
            rdoMale.requestFocus();
        }
        if (lstContacts.getItems().isEmpty()) {
            isDataValid = false;
            lstContacts.getStyleClass().add("invalid");
            lstContacts.requestFocus();
        }


        if (txtName.getText().isBlank() || txtName.getStyleClass().contains("invalid")) {
            isDataValid = false;
            if (!(txtName.getStyleClass().contains("invalid"))) {
                txtName.getStyleClass().add("invalid");
            }
            txtName.requestFocus();
        }
        System.out.println(isDataValid);
        if (isDataValid==false) return;

        //Business Validation
        ObservableList<String> contactList = lstContacts.getItems();
        Student selectedStudent = tblInfo.getSelectionModel().getSelectedItem();


        for (String enteredContact : contactList) {
            for (Student student : tblInfo.getItems()) {
                if (student==selectedStudent) continue;
                if (student.getContacts().contains(enteredContact)){
                    new Alert(Alert.AlertType.WARNING,"Number already exists").showAndWait();
                    lstContacts.getStyleClass().add("invalid");
                    lstContacts.requestFocus();
                    return;
                }
            }
        }

        // column Mapping
        tblInfo.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblInfo.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblInfo.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("gender"));
        tblInfo.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contacts"));
        tblInfo.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("degree"));
        tblInfo.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("selectedModules"));
        tblInfo.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("partTime") );

        //Add
        lblId.setText(generateNewId());
       // Student selectedStudent = tblInfo.getSelectionModel().getSelectedItem();


        if (tblInfo.getSelectionModel().getSelectedItem() == null) {
            Student student = new Student(
                    lblId.getText(),
                    txtName.getText().strip(),
                    rdoMale.isSelected() ? Gender.MALE : Gender.FEMALE,
                    new ArrayList<>(lstContacts.getItems()),
                    cmbDegree.getSelectionModel().getSelectedItem(),
                    new ArrayList<>(lstSelectedModules.getItems()),
                    chkPartTime.isSelected()
            );
            tblInfo.getItems().add(student);

        } else {

          
            selectedStudent.setName(txtName.getText().strip());
            selectedStudent.setGender(rdoMale.isSelected() ? Gender.MALE : Gender.FEMALE);
            selectedStudent.setContacts(new ArrayList<>(lstContacts.getItems()));
            selectedStudent.setDegree(cmbDegree.getSelectionModel().getSelectedItem());
            selectedStudent.setSelectedModules(new ArrayList<>(lstSelectedModules.getItems()));
            selectedStudent.setPartTime(chkPartTime.isSelected());
            tblInfo.refresh();

        }
        btnNewStudent.fire();


    }

    @FXML
    void chkPartTimeOnAction(ActionEvent event) {

    }

    @FXML
    void cmbDegreeOnAction(ActionEvent event) {

    }

    @FXML
    void txtContactsOnAction(ActionEvent event) {
        btnAdd.fire();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {

    }

    public void tblInfoOnMouseClicked(MouseEvent mouseEvent) {
    }

    public void lstModulesOnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            btnForward.fire();
        }
    }

    public void lstSelectedModulesOnKeyReleased(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            btnBackward.fire();
        }
    }
}
