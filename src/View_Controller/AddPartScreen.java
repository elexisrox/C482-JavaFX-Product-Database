package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller class for AddPartScreen.fxml.
 * @author Elexis Rox
 */
public class AddPartScreen implements Initializable {

    Stage stage;
    Parent scene;

    //Objects from AddPartScreen.fxml
    @FXML
    public Label errorMessage;
    @FXML
    public Label starStock;
    @FXML
    public Label starMinMax;
    @FXML
    public Label starName;
    @FXML
    public Label starCompany;
    @FXML
    public AnchorPane addPartScreen;
    @FXML
    private ToggleGroup partTG;
    @FXML
    private RadioButton inRBtn;
    @FXML
    private RadioButton outRBtn;
    @FXML
    private TextField partInOutTxt;
    @FXML
    private Label partInOutLbl;
    @FXML
    private TextField partMaxTxt;
    @FXML
    private TextField partPriceTxt;
    @FXML
    private TextField partInvTxt;
    @FXML
    private TextField partNameTxt;
    @FXML
    private TextField partIdTxt;
    @FXML
    private TextField partMinTxt;

    /** Initialize method for AddPartScreen
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    //BEGIN BUTTON HANDLERS
    /** Saves the new Part to Inventory and parses all data to the correct fields. Catches exceptions and sets appropriate error messages to display.
     * @param actionEvent the on-action event that occurs when the Save button is clicked
     */
    @FXML
    void saveHandler(ActionEvent actionEvent) {
        int id = 2; //Declare ID
        for (int i = 0; i<Inventory.getAllParts().size(); i++) {
            if (id <= Inventory.getAllParts().get(i).getId()) //Finds max ID number in the full list of parts and adds 2.
            id = Inventory.getAllParts().get(i).getId() + 2;
            }
        try { //Parsing fields
            starStock.setText(null);
            starMinMax.setText(null);
            starName.setText(null);
            starCompany.setText(null);
            String name = partNameTxt.getText().strip(); //Get partNameTxt. Strip() removes excess trailing and leading whitespace.
            int stock = Integer.parseInt(partInvTxt.getText().strip());
            double price = Double.parseDouble(partPriceTxt.getText().strip());
            int max = Integer.parseInt(partMaxTxt.getText().strip());
            int min = Integer.parseInt(partMinTxt.getText().strip());

            //Exception triggers
            if (max < min) //Min should be less than Max
                throw new Exception("minMaxError");
            if (stock < min || stock > max) //Inventory should be between those two values.
                throw new Exception("stockError");
            if (name.trim().isEmpty())
                throw new Exception("nullNameError");

            //Radio Buttons add Part functionality
            if (inRBtn.isSelected()) { //If InHouse radio button is selected.
                int machineID = Integer.parseInt(partInOutTxt.getText());
                InHouse addedInPart = new InHouse(id, name, price, stock, min, max, machineID);
                Inventory.addPart(addedInPart);
            } else if (outRBtn.isSelected()) { //If Outsourced radio button is selected.
                String companyName = partInOutTxt.getText().strip();
                if (companyName.trim().isEmpty())
                    throw new Exception("nullCompanyError");
                Outsourced addedOutPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.addPart(addedOutPart);
            }

            transitionMain(actionEvent); // Go back to main Inventory screen.

        } catch (Exception exception) {
            if (inRBtn.isSelected()) {
                switch (exception.getMessage()) {
                    case "minMaxError":
                        errorMessage.setText("Minimum or Maximum value incorrect. Minimum must be less than maximum.");
                        starMinMax.setText("*");
                        break;
                    case "stockError":
                        errorMessage.setText("Inventory value incorrect. Inventory must be between min and max.");
                        starStock.setText("*");
                        break;
                    case "nullNameError":
                        errorMessage.setText("Please enter Product Name.");
                        starName.setText("*");
                        break;
                    default:
                        errorMessage.setText("Incorrect data entry. Inventory, Price, Max, Min, and MachineID should all be numeric values.");
                        break;
                }
            } else if (outRBtn.isSelected()) {
                switch (exception.getMessage()) {
                    case "minMaxError":
                        errorMessage.setText("Minimum or Maximum value incorrect. Minimum must be less than maximum.");
                        starMinMax.setText("*");
                        break;
                    case "stockError":
                        errorMessage.setText("Inventory value incorrect. Inventory must be between min and max.");
                        starStock.setText("*");
                        break;
                    case "nullNameError":
                        errorMessage.setText("Please enter Product Name.");
                        starName.setText("*");
                        break;
                    case "nullCompanyError":
                        errorMessage.setText("Please enter Company Name.");
                        starCompany.setText("*");
                        break;
                    default:
                        errorMessage.setText("Incorrect data entry. Inventory, Price, Max, and Min should all be numeric values.");
                        break;
                }
            }
        }
    }

    /** Displays a dialog box prompting the user to confirm exit. Upon confirmation, returns to Main Screen.
     * @param actionEvent the on-action event that occurs when the Cancel button is clicked
     */
    @FXML
    void cancelHandler(ActionEvent actionEvent) throws IOException {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.initModality(Modality.APPLICATION_MODAL);
        cancelAlert.setTitle("Exit Confirmation");
        cancelAlert.setHeaderText("Do you want to return to the main screen?");
        Label alertLabel = new Label(null);
        cancelAlert.getDialogPane().setContent(alertLabel);
        Optional<ButtonType> result = cancelAlert.showAndWait();

        if (result.get() == ButtonType.OK) {
            transitionMain(actionEvent);
        } else {
            System.out.println("EXIT CANCELED");
        }
    }

    /** Sets label text and prompt text to MachineID when the InHouse radio button is selected.
     * @param actionEvent the on-action event that occurs when the InHouse radio button is selected
     */
    public void onInHouse(ActionEvent actionEvent) {
        partInOutLbl.setText("Machine ID");
        partInOutTxt.setPromptText("Machine ID");
    }

    /** Sets label text and prompt text to Company Name when the Outsourced radio button is selected.
     * @param actionEvent the on-action event that occurs when the Outsourced radio button is selected
     */
    public void onOutsourced(ActionEvent actionEvent) {
        partInOutLbl.setText("Company Name");
        partInOutTxt.setPromptText("Company Name");
    }

    /** Utilized by other methods to transition back to the Main screen.
     * @param actionEvent the transition to the Main screen
     * @throws IOException IOException
     */
    public void transitionMain(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Elexis Rox - Inventory Management System");
        stage.show();
    }
}
