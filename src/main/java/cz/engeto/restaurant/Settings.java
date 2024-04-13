package cz.engeto.restaurant;

public class Settings {
    private static final String ORDERS = "resources/orders.txt";
    private static final String DISHES = "resources/dishes.txt";

    private static final String DELIMITER = "\t";
    private static final String COMA = ".";

    private static final String SPACE = " ";

    private static final String COLON = ":";

    private static final String CURRENCY = " Kč";

    private static final String DASH = "-";

    private static final String BEGINBRACKET = "(";

    private static final String ENDBRACKET = ")";

    private static final String ORDERINGTEXT = "Objednávka pro stůl č. ";

    public static String getDelimiter() {
        return DELIMITER;
    }

    public static String getOrders() {
        return ORDERS;
    }

    public static String getDishes() {
        return DISHES;
    }

    public static String getComa() {return COMA;}

    public static String getSpace() {return SPACE;}

    public static String getColon() {return COLON;}

    public static String getCurrency() {return CURRENCY;}

    public static String getDash() {return DASH;}

    public static String getBeginbracket() {return BEGINBRACKET;}

    public static String getEndbracket() {return ENDBRACKET;}

    public static String getOrderingtext() {return ORDERINGTEXT;}




}
