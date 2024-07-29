package View_Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.IOException;

/** Controller class for ModifyProductScreen.fxml.
 * @author Elexis Rox
 */
public class ModifyProductScreen implements Initializable {


    Stage stage;
    Parent scene;

    int productLocation; //Product's index location
    private Product selectedProduct;

    //Objects from ModifyProductScreen.fxml
    @FXML
    public TextField searchPartsField;
    @FXML
    public AnchorPane modifyProductScreen;
    @FXML
    private Label prodTitleLbl;
    @FXML
    private Label partsErrorLbl;
    @FXML
    private Label searchPartsLbl;
    @FXML
    private Label associatedErrorLbl;
    @FXML
    private TextField prodMaxTxt;
    @FXML
    private TextField prodPriceTxt;
    @FXML
    private TextField prodInvTxt;
    @FXML
    private TextField prodNameTxt;
    @FXML
    private TextField prodIdTxt;
    @FXML
    private TextField prodMinTxt;
    @FXML
    public Label errorMessage;
    @FXML
    public Label starStock;
    @FXML
    public Label starMinMax;
    @FXML
    public Label starName;
    //Tables and Columns from ModifyProductScreen.fxml
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> idColumn;
    @FXML
    private TableColumn<Part, String> nameColumn;
    @FXML
    private TableColumn<Part, Integer> inventoryLvlColumn;
    @FXML
    private TableColumn<Part, Double> priceColumn;
    @FXML
    private TableView<Part> associatedTable;
    @FXML
    private TableColumn<Part, Integer> idColumnA;
    @FXML
    private TableColumn<Part, String> nameColumnA;
    @FXML
    private TableColumn<Part, Integer> inventoryLvlColumnA;
    @FXML
    private TableColumn<Part, Double> priceColumnA;


    private static ObservableList<Part> associatedTableList = FXCollections.observableArrayList(); //Temporary list of associated parts to create a list for each product.

    /** Sends the product originally selected on the main screen to be modified. Parses the data to display in the correct text fields. Populates the bottom table with associated parts specific to the product being modified.
     * @param selectedProduct the part originally selected from the main screen that will be modified
     */
    public void sendProduct(Product selectedProduct) {
        productLocation = Inventory.getAllProducts().indexOf(selectedProduct);

        prodIdTxt.setText(Integer.toString(selectedProduct.getId()));
        prodNameTxt.setText(selectedProduct.getName());
        prodInvTxt.setText(String.valueOf(selectedProduct.getStock()));
        prodPriceTxt.setText(String.valueOf(selectedProduct.getStock()));
        prodMaxTxt.setText(String.valueOf(selectedProduct.getMax()));
        prodMinTxt.setText(String.valueOf(selectedProduct.getMin()));

        associatedTableList.addAll(selectedProduct.getAllAssociatedParts());
        associatedTable.setItems(associatedTableList);

        this.selectedProduct = selectedProduct;
        modifyProductScreen.requestFocus();

    }

    /** Initialize method for ModifyProductScreen that sets table columns and populates the tables with data.
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPartsColumns();
        setPartsAColumns();

        partsTable.setItems(Inventory.getAllParts());
        associatedTable.setItems(associatedTableList);
    }

    /** Sets top Parts Table columns.
     */
    public void setPartsColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryLvlColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /** Sets bottom Associated Parts Table columns.
     */
    public void setPartsAColumns() {
        idColumnA.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnA.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumnA.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryLvlColumnA.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /** Creates a temporary list of associated parts to populate the bottom table.
     * @return a temporary list of associated parts
     */
    public static ObservableList<Part> getAssociatedTableList() {
        return associatedTableList;
    }


    /** Searches list of parts by partial string or ID number.
     * @param actionEvent occurs upon pressing Enter key in the search field
     */
    public void getSearchResults(ActionEvent actionEvent) {
        partsErrorLbl.setText("");
        searchPartsLbl.setText("");

        String s = searchPartsField.getText().trim();

        if (s.matches("")) {
            partsTable.setItems(Inventory.getAllParts());
            searchPartsLbl.setText("");
            modifyProductScreen.requestFocus();
        } else {
            ObservableList<Part> partsResults = Inventory.lookupPart(s);
            try { //If there are no matching String results, search by ID instead.
                int partID = Integer.parseInt(s);
                Part sp = Inventory.lookupPart(partID);
                if (sp != null)
                    partsResults.add(sp);
            } catch (NumberFormatException e) {
                //catch and ignore
            }
            partsTable.setItems(partsResults);

            if (partsResults.size() == 1)
                searchPartsLbl.setText("1 result found.");
            if (partsResults.size() > 1)
                searchPartsLbl.setText(partsResults.size() + " results found.");
            if (partsResults.size() == 0)
                partsErrorLbl.setText("Part not found.");
        }
    }

    //BEGIN BUTTON HANDLERS
    /** Saves the new Part to Inventory and parses all data to the correct fields. Catches exceptions and sets appropriate error messages to display.
     * @param actionEvent the on-action event that occurs when the Save button is clicked
     */
    public void saveHandler(ActionEvent actionEvent) {
        errorMessage.setWrapText(true);
        errorMessage.setMaxWidth(300);
        try { //Parsing fields
            int id = Integer.parseInt(prodIdTxt.getText());
            String name = prodNameTxt.getText().strip(); //Get partNameTxt. Strip() removes excess trailing and leading whitespace.
            int stock = Integer.parseInt(prodInvTxt.getText().strip());
            double price = Double.parseDouble(prodPriceTxt.getText().strip());
            int max = Integer.parseInt(prodMaxTxt.getText().strip());
            int min = Integer.parseInt(prodMinTxt.getText().strip());
            starStock.setText(null);
            starMinMax.setText(null);
            starName.setText(null);

            //Exception triggers
            if (max < min) //Min should be less than Max
                throw new Exception("minMaxError1");
            if (stock < min || stock > max) //Inventory should be between those two values.
                throw new Exception("stockError1");
            if (name.trim().isEmpty())
                throw new Exception("nullNameError");

            Product moddedProduct = new Product(id, name, price, stock, min, max);
            for (int i=0; i<associatedTableList.size(); i++){
                moddedProduct.addAssociatedPart(associatedTableList.get(i));
            }
            Inventory.updateProduct(Inventory.getAllProducts().indexOf(selectedProduct), moddedProduct);

            transitionMain(actionEvent);

        } catch (Exception exception) {
            switch (exception.getMessage()) {
                case "minMaxError1":
                    errorMessage.setText("Minimum or Maximum value incorrect. Minimum must be less than maximum.");
                    starMinMax.setText("*");
                    break;
                case "stockError1":
                    errorMessage.setText("Inventory value incorrect. Inventory must be between min and max.");
                    starStock.setText("*");
                    break;
                case "nullNameError":
                    errorMessage.setText("Please enter Product Name.");
                    starName.setText("*");
                    break;
                default:
                    errorMessage.setText("Incorrect data entry. Inventory, Price, Max, and Min should all be numeric values.");
                    break;
            }
        }
    }

    /** Displays a dialog box prompting the user to confirm exit. Upon confirmation, returns to Main Screen.
     * @param actionEvent the on-action event that occurs when the Cancel button is clicked
     */
    public void cancelHandler (ActionEvent actionEvent) throws IOException {
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

    /** Adds the selected part to the temporary AssociatedTableList in the table below.
     * @param actionEvent the on-action event that occurs when the Add button is clicked
     */
    public void addAssociatedHandler (ActionEvent actionEvent){
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        partsErrorLbl.setText(null); //Reset Error Labels
        associatedErrorLbl.setText(null);

        if (selectedPart == null)
            partsErrorLbl.setText("Please select a part.");
        else
            associatedTableList.add(selectedPart);

        associatedTable.setItems(associatedTableList); //Update list
    }

    /** Removes the selected part from the temporary AssociatedTableList. Displays a dialog confirmation screen, prompting the user to confirm or cancel part removal.
     * @param actionEvent the on-action event that occurs when the Remove button is clicked
     */
    public void removeAssociatedHandler (ActionEvent actionEvent){
        Part selectedRemovePart = associatedTable.getSelectionModel().getSelectedItem();
        associatedErrorLbl.setText(null);

        if (selectedRemovePart == null) {
            associatedErrorLbl.setText("Select a part to remove.");
        } else {
            Alert removeAlert = new Alert(Alert.AlertType.CONFIRMATION);
            removeAlert.initModality(Modality.APPLICATION_MODAL);
            removeAlert.setTitle("Part Removal Confirmation");
            removeAlert.setHeaderText("Remove Associated Part");
            Label alertLabel = new Label("Are you sure you want to remove this associated part from the product?");
            removeAlert.getDialogPane().setContent(alertLabel);

            Optional<ButtonType> result = removeAlert.showAndWait();

            if (result.get() == ButtonType.OK) {
                associatedTableList.remove(selectedRemovePart);
                System.out.println("ASSOCIATED PART REMOVED");
            } else {
                System.out.println("REMOVAL CANCELED");
            }
        }
        associatedTable.setItems(associatedTableList); //Update list
    }

    /** Utilized by other methods to transition back to the Main screen.
     * @param actionEvent the transition to the Main screen
     * @throws IOException IOException
     */
    public void transitionMain (ActionEvent actionEvent) throws IOException {
        associatedTableList.clear(); //Clears temporary list
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Elexis Rox - Inventory Management System");
        stage.show();
    }


}
