package inventory.Models;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Customer {

    //private int id;

    //String Property being used to enable the binding of properties

    private final StringProperty fname, lname, phone,
            email, street, suburb, state, pcode;

    private final IntegerProperty custid;





    public Customer(int custid, String fname, String lname, String phone, String email,
                    String street, String suburb, String state, String pcode) {

        this.custid = new SimpleIntegerProperty(custid);
        this.fname = new SimpleStringProperty(fname);
        this.lname = new SimpleStringProperty(lname);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.street = new SimpleStringProperty(street);
        this.suburb = new SimpleStringProperty(suburb);
        this.state = new SimpleStringProperty(state);
        this.pcode = new SimpleStringProperty(pcode);
    }

    public int getCustid() {
        return custid.get();
    }

    public IntegerProperty custidProperty() {
        return custid;
    }

    public void setCustid(int custid) {
        this.custid.set(custid);
    }

    public abstract double getDiscountRate();


    public String getFname() {
        return fname.get();
    }

    public void setFname(String fname) {
        this.fname.set(fname);
    }

    public String getLname() {
        return lname.get();
    }

    public void setLname(String lname) {
        this.lname.set(lname);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getSuburb() {
        return suburb.get();
    }

    public void setSuburb(String suburb) {
        this.suburb.set(suburb);
    }

    public String getState() {
        return state.get();
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public String getPcode() {
        return pcode.get();
    }

    public void setPcode(String pcode) {
        this.pcode.set(pcode);
    }
}
