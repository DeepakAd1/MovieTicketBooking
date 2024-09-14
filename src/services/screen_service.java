package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class screen_service {
    public static void printAvailableScreens(int theater_id) throws Exception {
        Connection con = DbConnection.getConnection();

        try {
            String query =
                    "SELECT screen_id, total_no_of_Seats, total_no_class, Ac " +
                            "FROM Screen " +
                            "WHERE theater_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, theater_id);

            ResultSet rs = pst.executeQuery();

            System.out.println("Available Screens in Theater ID " + theater_id + ":");
            System.out.println("----------------------------------------------------");
            System.out.println("Screen ID | Total Seats | Classes | AC (Y/N)");
            System.out.println("----------------------------------------------------");

            boolean screensAvailable = false;
            while (rs.next()) {
                int screen_id = rs.getInt("screen_id");
                int total_no_of_Seats = rs.getInt("total_no_of_Seats");
                int total_no_class = rs.getInt("total_no_class");
                boolean ac = rs.getBoolean("Ac");

                System.out.printf("%-9d | %-11d | %-7d | %-8s\n",
                        screen_id, total_no_of_Seats, total_no_class, ac ? "Y" : "N");
                screensAvailable = true;
            }

            if (!screensAvailable) {
                System.out.println("No screens available in this theater.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
}
}
