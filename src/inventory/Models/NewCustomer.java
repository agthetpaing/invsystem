package inventory.Models;


public class NewCustomer extends Customer {
    private final double DISCOUNT_RATE = 1.0;



    public NewCustomer(int custid, String fname, String lname, String phone,
                       String email, String street, String suburb, String state, String pcode) {
        super(custid, fname, lname, phone, email, street, suburb, state, pcode);
    }

    @Override
    public double getDiscountRate() {
        return DISCOUNT_RATE;

    }
}
