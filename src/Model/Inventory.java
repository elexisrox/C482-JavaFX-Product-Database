package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

/**
 * The Inventory class, which contains static lists of Parts and Products.
 * @author Elexis Rox
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList(); //List of all parts, both InHouse and Outsourced, in inventory
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList(); //List of all products in inventory

    /** Adds a new part to inventory.
     * @param newPart the new part that will be added to the allParts list
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Adds a new product to inventory.
     * @param newProduct the new product that will be added to the allProducts list
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** Returns search result for parts by ID Number.
     * @param partID the ID number the user enters into the search bar
     * @return the part with an ID that matches the user's search
     */
    public static Part lookupPart(int partID) {
        for(int i = 0; i < allParts.size(); i++) {
            Part sp = allParts.get(i);

            if(sp.getId() == partID){
                return sp;
            }
        }
        return null;
    }

    /** Returns search result for products by ID Number.
     * @param productID the ID number the user enters into the search bar
     * @return the part with an ID that matches the user's search
     */
    public static Product lookupProduct(int productID) {
        for(int i = 0; i < allProducts.size(); i++) {
            Product sq = allProducts.get(i);

            if(sq.getId() == productID){
                return sq;
            }
        }
        return null;
    }

    /** Returns a list of parts with names that either match or contain the user's search.
     * @param partName the partial or full name the user enters into the search bar
     * @return a list of parts that contain partName
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part sp : allParts){
            if(sp.getName().contains(partName)){
                namedParts.add(sp);
            }
        }
        return namedParts;
    }

    /** Returns a list of products with names that either match or contain the user's search.
     * @param productName the partial or full name the user enters into the search bar
     * @return a list of parts that contain productName
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product sq : allProducts){
            if(sq.getName().contains(productName)){
                namedProducts.add(sq);
            }
        }
        return namedProducts;
    }

    /** Replaces the part with the updated selectedPart at the provided index location
     * @param index Index location of the part being updated
     * @param selectedPart the new updated part that will replace the original part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);}

    /** Replaces the product with the updated selectedProduct at the provided index location
     * @param index Index location of the product being updated
     * @param newProduct the new updated product that will replace the original product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);}

    /** Removes the selected Part from allParts.
     * @param selectedPart the part that will be deleted from allParts
     * @return true if part is successfully deleted
     */
    public static boolean deletePart(Part selectedPart) {
        if (Inventory.getAllParts().contains(selectedPart)){
            Inventory.getAllParts().remove(selectedPart);
        }
        return false;
    }

    /** Removes the selected Product from allProducts.
     * @param selectedProduct the product that will be deleted from allProducts
     * @return true if product is successfully deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (Inventory.getAllProducts().contains(selectedProduct)){
            Inventory.getAllProducts().remove(selectedProduct);
        }
        return false;
    }

    /** Gets a list of all Parts in inventory.
     * @return list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** Gets a list of all Products in inventory.
     * @return list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
