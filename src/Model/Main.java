package Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// JAVADOC FOLDER LOCATION - ElexisRox-QKM2/Javadoc

/**
 * The Main class, which creates an application for an Inventory Management System.
 *
 * FUTURE ENHANCEMENT
 * One ideal future enhancement would include allowing the user to easily
 * remove all Associated Parts from a Product while on the Main Inventory
 * Screen. This would streamline the product deletion process. Another
 * useful feature would be the addition of Associated Products. Currently,
 * a part can be deleted, even if it is associated with a Product. When
 * modifying or removing a product, a check could be made to determine
 * if any Products are currently linked with that Part.
 *
 * @author Elexis Rox
 */
public class Main extends Application {

    /** Sets up the Stage and loads MainScreen.fxml. The user is also prevented from resizing the application window.
     * @param primaryStage The primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false); //Prevents user from resizing the stage
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        primaryStage.setTitle("Elexis Rox - QKM2 Project for Software I");
        primaryStage.setScene(new Scene(root, 1100, 400));
        primaryStage.show();
    }

    /** The main method, which creates sample data comprised of parts and products.
     * @param args Starts the main method
     */
    public static void main(String[] args) {
        //Parts Sample Data - ID, Part Name, Price, Inventory/Stock, Min, Max, companyName/machineID
        Inventory.getAllParts().add(new Outsourced(222, "Bike Chain X11EL", 54.99, 4, 1, 10, "KMC"));
        Inventory.getAllParts().add(new InHouse(224, "Anti-Slip Grip Tape", 23.99, 26, 1, 60, 450));
        Inventory.getAllParts().add(new InHouse(226, "Brass Bicycle Bell", 13.99, 2, 1, 25, 222));

        //Products Sample Data - Associated Parts List, ID, Product Name, Price, Inventory/Stock, Min, Max
        Inventory.getAllProducts().add(new Product(453, "Transonic 2021 Road Bike", 3199.99, 1, 1, 5));

        launch(args);
    }


}
