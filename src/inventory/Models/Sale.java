package inventory.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Sale {

    private final IntegerProperty prodid, custid, qty;

    private final DoubleProperty price;



    public Sale(int prodid, int custid, int qty, double price) {
        this.prodid = new SimpleIntegerProperty(prodid);
        this.custid = new SimpleIntegerProperty(custid);
        this.qty = new SimpleIntegerProperty(qty);
        this.price = new SimpleDoubleProperty(price);
    }

    public int getProdid() {
        return prodid.get();
    }

    public IntegerProperty prodidProperty() {
        return prodid;
    }

    public void setProdid(int prodid) {
        this.prodid.set(prodid);
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

    public int getQty() {
        return qty.get();
    }

    public IntegerProperty qtyProperty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty.set(qty);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
}
