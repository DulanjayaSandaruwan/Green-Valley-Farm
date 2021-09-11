package model;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-06
 **/
public class Supplier {
    private String supID;
    private String supName;
    private String supAddress;
    private String supContact;

    public Supplier() {

    }

    public Supplier(String supID, String supName, String supAddress, String supContact) {
        this.supID = supID;
        this.supName = supName;
        this.supAddress = supAddress;
        this.supContact = supContact;
    }

    public String getSupID() {
        return supID;
    }

    public void setSupID(String supID) {
        this.supID =supID;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getSupAddress() {
        return supAddress;
    }

    public void setSupAddress(String supAddress) {
        this.supAddress = supAddress;
    }

    public String getSupContact() {
        return supContact;
    }

    public void setSupContact(String supContact) {
        this.supContact = supContact;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supID='" + supID + '\'' +
                ", supName='" + supName + '\'' +
                ", supAddress='" + supAddress + '\'' +
                ", supContact='" + supContact + '\'' +
                '}';
    }
}
