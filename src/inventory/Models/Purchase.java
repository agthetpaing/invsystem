package inventory.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Purchase {

    private final IntegerProperty prodid, supid, qty;

    private final DoubleProperty price;



    public Purchase(int prodid, int supid, int qty, double price) {

        this.prodid = new SimpleIntegerProperty(prodid);
        this.supid = new SimpleIntegerProperty(supid);
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

    public int getSupid() {
        return supid.get();
    }

    public IntegerProperty supidProperty() {
        return supid;
    }

    public void setSupid(int supid) {
        this.supid.set(supid);
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
