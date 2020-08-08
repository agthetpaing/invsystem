package inventory.Models;



public class FreshProduct extends Product {
    private final int SHELF_LIFE = 5;


    public FreshProduct(int prodid, String desc, int quantity, int catid, double price) {
        super(prodid, desc, quantity, catid, price);
        REORDER_POINT = 15;
    }

    @Override
    public int getReorderPoint() {
        return REORDER_POINT;
    }

    public int getShelfLife(){
        return SHELF_LIFE;
    }
}
