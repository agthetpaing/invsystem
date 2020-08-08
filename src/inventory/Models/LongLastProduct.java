package inventory.Models;



public class LongLastProduct extends Product{


    public LongLastProduct(int prodid, String desc, int quantity, int catid, double price) {
        super(prodid, desc, quantity, catid, price);
        REORDER_POINT = 15;
    }


    @Override
    public int getReorderPoint() {
        return REORDER_POINT;
    }

}
