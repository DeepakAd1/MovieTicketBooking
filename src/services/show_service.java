package services;

import java.sql.*;

public class show_service {

    public static void printAvailableShows(int screen_id, Date show_date) throws Exception {
        Connection con = DbConnection.getConnection();

        try {
            String query =
                    "SELECT s.show_id, m.movie_name, s.Start_ime, s.End_time " +
                            "FROM ShowTime s " +
                            "JOIN Movie m ON s.movie_id = m.movie_id " +
                            "WHERE s.screen_id = ? AND s.show_date = ? " +
                            "ORDER BY s.Start_ime";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, screen_id);
            pst.setDate(2, show_date);

            ResultSet rs = pst.executeQuery();

            System.out.println("Available Shows on " + show_date + ":");
            System.out.println("---------------------------------------------------");
            System.out.println("Show ID | Movie Name        | Start Time | End Time");
            System.out.println("---------------------------------------------------");

            boolean showsAvailable = false;
            while (rs.next()) {
                int show_id = rs.getInt("show_id");
                String movie_name = rs.getString("movie_name");
                Time start_time = rs.getTime("Start_ime");
                Time end_time = rs.getTime("End_time");

                System.out.printf("%-7d | %-16s | %-10s | %-8s\n",show_id, movie_name, start_time, end_time);
                showsAvailable = true;
            }

            if (!showsAvailable) {
                System.out.println("No shows available on this date for the selected screen.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
