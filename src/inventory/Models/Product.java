package inventory.Models;


import javafx.beans.property.*;

public abstract class Product {


    private final StringProperty desc;

    private final IntegerProperty quantity, catid, prodid;

    private final DoubleProperty price;

    public static final int LONG_LIFE = 1;

    public static final int FRESH = 2;

    public int REORDER_POINT;

    public final int REORDER_QTY = 5;

    public int getProdid() {
        return prodid.get();
    }

    public IntegerProperty prodidProperty() {
        return prodid;
    }

    public void setProdid(int prodid) {
        this.prodid.set(prodid);
    }

    public Product(int prodid, String desc, int quantity, int catid, double price) {
        this.prodid = new SimpleIntegerProperty(prodid);

        this.desc = new SimpleStringProperty(desc);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.catid = new SimpleIntegerProperty(catid);
        this.price = new SimpleDoubleProperty(price);

    }

    public abstract int getReorderPoint();


    public String getDesc() {
        return desc.get();
    }

    public StringProperty descProperty() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc.set(desc);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public int getCatid() {
        return catid.get();
    }

    public IntegerProperty catidProperty() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid.set(catid);
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
