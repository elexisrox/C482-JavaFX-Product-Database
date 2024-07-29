package Model;

/**
 * The InHouse class, which inherits the Part class.
 * @author Elexis Rox
 */
public class InHouse extends Part {
    private int machineID;

    /** InHouse Constructor
     * @param id ID of InHouse Part
     * @param name Name of InHouse Part
     * @param price Price of InHouse Part
     * @param stock Number of InHouse Part in current stock
     * @param min Minimum value of InHouse Part
     * @param max Maximum value of InHouse Part
     * @param machineID Machine ID of InHouse Part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** Sets Machine ID.
     * @param machineID Machine ID to set
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /** Gets Machine ID.
     * @return the Machine ID of the InHouse part
     */
    public int getMachineID() {
        return this.machineID;
    }

}
