package inventory.Enums;


public enum Mode {
    Staff, Manager, Marketing_Director;


    public String value(){
        return name();
    }

    public static Mode fromValue(String value){
        return valueOf(value);
    }
}
