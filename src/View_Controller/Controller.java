package View_Controller;

import Model.*;
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
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

/** Main Controller class for MainScreen.fxml.
 *
 * RUNTIME ERROR
 * Although the addition of sample data was optional in this product, I wanted to
 * prepopulate my tables with sample products and parts for testing purposes.
 * Initially, I included that data in the Initializeable class in this file,
 * Controller.java. I discovered that by doing this, each time the main screen was
 * reloaded, the data set would be added again, creating duplicate copies.
 * Fortunately, I was able to resolve this error by adding the sample data to
 * the main function in Main.java instead. This way, the data is only loaded once,
 * rather than each time the user returns to the Main Screen.
 * @author Elexis Rox
 */
public class Controller implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private AnchorPane mainScreen;

    //Parts Table (left side)
    @FXML
    private TextField searchText;
    @FXML
    private TableView<Part> partsTable;

    //Parts Table Columns
    @FXML
    private TableColumn<Part, Integer> idColumn;
    @FXML
    private TableColumn<Part, String> nameColumn;
    @FXML
    private TableColumn<Part, Integer> inventoryLvlColumn;
    @FXML
    private TableColumn<Part, Double> priceColumn;

    //Products Table (right side) - NOTE: Suffix of "1" associates that field/column with Products.
    @FXML
    private TextField searchText1;
    @FXML
    private TableView<Product> productsTable;

    //Products Table Columns
    @FXML
    private TableColumn<Product, Integer> idColumn1;
    @FXML
    private TableColumn<Product, String> nameColumn1;
    @FXML
    private TableColumn<Product, Integer> inventoryLvlColumn1;
    @FXML
    private TableColumn<Product, Double> priceColumn1;

    //Labels
    @FXML
    public Label modPartErrorLbl;
    @FXML
    public Label modProdErrorLbl;
    @FXML
    public Label searchPartsLbl;
    @FXML
    public Label searchProdsLbl;

    /** Initialize method for MainScreen that sets table columns and populates both tables with the appropriate data.
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("INITIALIZED");

        setPartsColumns();
        setProductsColumns();

        //Table data population
        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getAllProducts());
    }

    /** Sets left Parts Table columns.
     */
    public void setPartsColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryLvlColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /** Sets right Products Table columns.
     */
    public void setProductsColumns() {
        idColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryLvlColumn1.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    //BEGIN BUTTON HANDLERS
    /** Upon being clicked, transitions to the AddPartScreen.
     * @param actionEvent the on-action event that occurs when the Add button is clicked
     * @throws IOException IOException
     */
    public void addHandler(ActionEvent actionEvent) throws IOException {
        System.out.println("ADD PART CLICKED");
        transitionAddPart(actionEvent);
    }

    /** Upon being clicked, transitions to the ModifyPartScreen, sending the data of the selected Part.
     * @param actionEvent the on-action event that occurs when the Modify button is clicked
     * @throws IOException IOException
     */
    @FXML
    private void modifyHandler(ActionEvent actionEvent) throws IOException {
        System.out.println("MODIFY PART CLICKED");
        searchPartsLbl.setText(null);

        try {
            Part selectedModPart = partsTable.getSelectionModel().getSelectedItem();

            if (selectedModPart == null)
                throw new Exception("modPartError");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View_Controller/ModifyPartScreen.fxml"));
            loader.load();

            ModifyPartScreen ModPartController = loader.getController();
            ModPartController.sendPart(selectedModPart);

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setTitle("Modify Part");
            stage.showAndWait();
        } catch (Exception exception) {
            if (exception.getMessage().equals("modPartError"))
                modPartErrorLbl.setText("Please select a part to modify.");
        }
    }

    /** Prompts the user with a dialog confirmation to confirm or cancel part deletion. If a part has not been selected, an error message displays to let the user know.
     * @param actionEvent the on-action event that occurs when the Delete button is clicked
     */
    public void deleteHandler(ActionEvent actionEvent) {
        System.out.println("DELETE PART CLICKED");
        searchPartsLbl.setText(null);
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            modPartErrorLbl.setText("Please select a part to delete.");
        } else {
            Alert partDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            partDeleteAlert.initModality(Modality.APPLICATION_MODAL);
            partDeleteAlert.setTitle("Part Deletion Confirmation");
            partDeleteAlert.setHeaderText("Delete Part");
            Label alertLabel = new Label("Are you sure you want to permanently delete this part?");
            partDeleteAlert.getDialogPane().setContent(alertLabel);

            Optional<ButtonType> result = partDeleteAlert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
                System.out.println("PART DELETED");
            } else {
                System.out.println("DELETION CANCELED");
            }
        }
    }

    /** Upon being clicked, transitions to the AddProductScreen.
     * @param actionEvent the on-action event that occurs when the Add button is clicked
     * @throws IOException IOException
     */
    public void addHandler1(ActionEvent actionEvent) throws IOException {
        System.out.println("ADD PRODUCT CLICKED"); //For development reference.
        transitionAddProd(actionEvent);
    }

    /** Upon being clicked, transitions to the ModifyProductScreen, sending the data of the selected Product.
     * @param actionEvent the on-action event that occurs when the Modify button is clicked
     * @throws IOException IOException
     */
    public void modifyHandler1(ActionEvent actionEvent) throws IOException{
        System.out.println("MODIFY PRODUCT CLICKED");
        searchProdsLbl.setText(null);

        try {
            Product selectedModProduct= productsTable.getSelectionModel().getSelectedItem();

            if (selectedModProduct == null)
                throw new Exception("modProductError");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View_Controller/ModifyProductScreen.fxml"));
            loader.load();

            ModifyProductScreen ModProductController = loader.getController();
            ModProductController.sendProduct(selectedModProduct);

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setTitle("Modify Product");
            stage.showAndWait();
        } catch (Exception exception) {
            if (exception.getMessage().equals("modProductError"))
                modProdErrorLbl.setText("Please select a product to modify.");
        }
    }

    /** Prompts the user with a dialog confirmation to confirm or cancel product deletion. If a product has not been selected, an error message displays to let the user know.
     * @param actionEvent the on-action event that occurs when the Delete button is clicked
     */
    public void deleteHandler1(ActionEvent actionEvent) {
        searchProdsLbl.setText(null);
        System.out.println("DELETE PRODUCT CLICKED");

        Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();

            if (selectedProduct == null) {
                modProdErrorLbl.setText("Please select a product to delete.");
            } else if (selectedProduct.getAllAssociatedParts().size() > 0) {
                Alert prodErrorAlert = new Alert(Alert.AlertType.ERROR);
                prodErrorAlert.initModality(Modality.APPLICATION_MODAL);
                prodErrorAlert.setTitle("Product Deletion Error");
                prodErrorAlert.setHeaderText("Product could not be deleted.");

                Label alertLabel = new Label("This product has associated parts. To delete, first modify the product and remove all parts.");
                prodErrorAlert.getDialogPane().setContent(alertLabel);

                prodErrorAlert.showAndWait();

                System.out.println("PRODUCT CANNOT BE DELETED");
            } else if (selectedProduct.getAllAssociatedParts().size() == 0) {
                Alert prodDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
                prodDeleteAlert.initModality(Modality.APPLICATION_MODAL);
                prodDeleteAlert.setTitle("Product Deletion Confirmation");
                prodDeleteAlert.setHeaderText("Delete Product");
                Label alertLabel = new Label("Are you sure you want to permanently delete this part?");
                prodDeleteAlert.getDialogPane().setContent(alertLabel);

                Optional<ButtonType> result = prodDeleteAlert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(selectedProduct);
                    System.out.println("PRODUCT DELETED");
                } else {
                    System.out.println("DELETION CANCELED");
                }
            }
    }

    /** Displays a dialog box prompting the user to confirm exit. Upon confirmation, returns to Main Screen.
     * @param actionEvent the on-action event that occurs when the Exit button is clicked
     */
    public void exitHandler(ActionEvent actionEvent) {
        System.out.println("EXIT CLICKED");

        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.initModality(Modality.APPLICATION_MODAL);
        exitAlert.setTitle("Exit System Confirmation");
        exitAlert.setHeaderText("Are you sure you want to exit?");
        Label alertLabel = new Label("Please confirm exit.");
        exitAlert.getDialogPane().setContent(alertLabel);
        Optional<ButtonType> result = exitAlert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            System.out.println("EXIT CANCELED");
        }
    }

    //BEGIN SCREEN TRANSITION METHODS
    /** Utilized by other methods to transition to the AddPartScreen.
     * @param actionEvent the transition to the AddPartScreen
     * @throws IOException IOException
     */
    public void transitionAddPart(ActionEvent actionEvent) throws IOException{
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/AddPartScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Part");
        stage.show();
    }

    /** Utilized by other methods to transition to the AddProductScreen.
     * @param actionEvent the transition to the AddProductScreen
     * @throws IOException IOException
     */
    public void transitionAddProd(ActionEvent actionEvent) throws IOException{
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/AddProductScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Product");
        stage.show();
    }

    //BEGIN SEARCH METHODS
    /** Searches list of parts by partial string or ID number.
     * @param actionEvent occurs upon pressing Enter key in the search field on the left
     */
    public void getSearchResults(ActionEvent actionEvent) {
        modPartErrorLbl.setText("");
        searchPartsLbl.setText("");

        String s = searchText.getText().trim();

        if (s.matches("")) {
            partsTable.setItems(Inventory.getAllParts());
            searchPartsLbl.setText("");
            mainScreen.requestFocus();
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
                modPartErrorLbl.setText("Part not found.");
        }
    }

    /** Searches list of products by partial string or ID number.
     * @param actionEvent occurs upon pressing Enter key in the search field on the right
     */
    public void getSearchResults1(ActionEvent actionEvent) {
        modProdErrorLbl.setText("");
        searchProdsLbl.setText("");

        String q = searchText1.getText().trim();

        if (q.matches("")) {
            productsTable.setItems(Inventory.getAllProducts());
            searchProdsLbl.setText("");
            mainScreen.requestFocus();
        } else {
            ObservableList<Product> productsResults = Inventory.lookupProduct(q);
            try { //If there are no matching String results, search by ID instead.
                int productID = Integer.parseInt(q);
                Product sq = Inventory.lookupProduct(productID);
                if (sq != null)
                    productsResults.add(sq);
            } catch (NumberFormatException e) {
                //catch and ignore
            }
            productsTable.setItems(productsResults);

            if (productsResults.size() == 1)
                searchProdsLbl.setText("1 result found.");
            if (productsResults.size() > 1)
                searchProdsLbl.setText(productsResults.size() + " results found.");
            if (productsResults.size() == 0)
                modProdErrorLbl.setText("Part not found.");
        }

    }


}
