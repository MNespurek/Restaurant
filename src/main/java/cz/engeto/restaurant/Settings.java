package cz.engeto.restaurant;

public class Settings {
    private static final String DISHES = "resources/dishes.txt";

    private static final String TAB = "\t";
    private static final String COMA = ".";

    private static final String SPACE = " ";

    private static final String COLON = ":";

    private static final String CURRENCY = " Kč";

    private static final String DASH = "-";

    private static final String BEGINBRACKET = "(";

    private static final String ENDBRACKET = ")";

    private static final String ORDERINGTEXT = "Objednávka pro stůl č. ";

    private static final String TABLESTARS = "**";

    private static final String BEGINORDERINGSTARS = "****";

    private static final String ENDORDERINGSTARS = "******";

    private static final String MULTIPLYINGAMOUNTOFFOOD = "x";

    private static final String RESOURCES = "resources/";

    private static final String FILESUFFIX = ".txt";

    public static String getFileSuffix() {return FILESUFFIX;}

    public static String getEndorderingstars() {return ENDORDERINGSTARS;}

    public static String getResources() {return RESOURCES;}

    public static String getTableStars() {return TABLESTARS;}

    public static String getBeginOrderingStars() {return BEGINORDERINGSTARS;}

    public static String getMultiplyingamountoffood() {return MULTIPLYINGAMOUNTOFFOOD;}

    public static String getTab() {
        return TAB;
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
