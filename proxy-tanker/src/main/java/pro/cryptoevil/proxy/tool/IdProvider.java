package pro.cryptoevil.proxy.tool;

public class IdProvider {

    private static int id = 0;

    public static String getStringId() {
        return String.valueOf(id++);
    }

    public static int getId() {
        return id++;
    }
}
