package Model;

/**
 * The Outsourced class, which inherits the Part class.
 * @author Elexis Rox
 */
public class Outsourced extends Part {
    private String companyName;

    /** Outsourced Constructor
     * @param id ID of Outsourced Part
     * @param name Name of Outsourced Part
     * @param price Price of Outsourced Part
     * @param stock Number of Outsourced Part in current stock
     * @param min Minimum value of Outsourced Part
     * @param max Maximum value of Outsourced Part
     * @param companyName Name of company that produces Outsourced Part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Sets Company Name.
     * @param companyName Name of company that produces Outsourced Part
     */
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    /** Gets Company Name.
     * @return the name of the company that produces Outsourced Part
     */
    public String getCompanyName() { return companyName; }

}



