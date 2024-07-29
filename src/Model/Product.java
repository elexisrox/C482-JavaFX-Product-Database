package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Product class, which contains all products.
 * @author Elexis Rox
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Product Constructor
     * @param id ID of the Product
     * @param name Name of the Product
     * @param price Price of the Product
     * @param stock Current number of Product in stock
     * @param min Minimum value of Product
     * @param max Maximum value of Product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    //BEGIN SETTERS
    /** Sets Product ID.
     * @param id the id to set
     */
    public void setId(int id) { this.id = id; }

    /** Sets Product name.
     * @param name the name to set
     */
    public void setName(String name) { this.name = name; }

    /** Sets Product price.
     * @param price the price to set
     */
    public void setPrice(double price) { this.price = price; }

    /** Sets current Product stock.
     * @param stock the stock to set
     */
    public void setStock(int stock) { this.stock = stock; }

    /** Sets the Product minimum value.
     * @param min the minimum to set
     */
    public void setMin(int min) { this.min = min; }

    /** Sets the Product maximum value.
     * @param max the maximum to set
     */
    public void setMax(int max) { this.max = max; }

    //BEGIN GETTERS

    /** Gets the Product ID.
     * @return the id
     */
    public int getId() { return id; }

    /** Gets the Product name.
     * @return the name
     */
    public String getName() { return name; }

    /** Gets the Product price.
     * @return the price
     */
    public double getPrice() { return price; }

    /** Gets the current number of Product in stock.
     * @return the stock
     */
    public int getStock() { return stock; }

    /** Gets the minimum Product value.
     * @return the minimum
     */
    public int getMin() { return min; }

    /** Gets the maximum Product value.
     * @return the maximum
     */
    public int getMax() { return max; }

    //BEGIN ASSOCIATED PARTS METHODS
    /** Adds an associated part.
     * @param part the part that will be added
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    };

    /** Deletes the selected Associated Part.
     * @param selectedAssociatedPart the Associated Part that is currently selected
     * @return true if associated part is successfully deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
       for (int i = 0; i < getAllAssociatedParts().size(); i++){
           if (associatedParts.get(i) == selectedAssociatedPart){
               associatedParts.remove(i);
               return true;
           }
       }
       return false;
   }

    /** Gets a list of all Associated Parts for that specific Product.
     * @return list of Associated Parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    };

}
