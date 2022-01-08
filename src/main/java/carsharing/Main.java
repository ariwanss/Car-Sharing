package carsharing;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        String dbName = "carsharing";

        try {
            dbName = args[1];
        } catch (Exception ignored) {}

        CarSharing carSharing = new CarSharing(dbName);
        carSharing.run();
    }
}