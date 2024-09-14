package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class booking_service {
    public static void addBookings(int customer_id, Time show_time, Date show_date, int show_id, List<Integer> seatIds) throws Exception {
        Connection con = DbConnection.getConnection();


        try {
            double totalPrice = 0.0;
            List<Double> seatPrices = new ArrayList<>();

            // Check seat availability and calculate the total price
            for (int seat_id : seatIds) {
                String checkSeatQuery = "SELECT * FROM Multiple_Tickets WHERE seat_id = ? AND booking_id IN (SELECT booking_id FROM bookings WHERE show_id = ? AND show_date = ?)";
                PreparedStatement pstCheck = con.prepareStatement(checkSeatQuery);
                pstCheck.setInt(1, seat_id);
                pstCheck.setInt(2, show_id);
                pstCheck.setDate(3, show_date);

                ResultSet rsCheck = pstCheck.executeQuery();
                if (rsCheck.next()) {
                    throw new Exception("Seat ID " + seat_id + " is already booked.");
                }

                // Retrieve the price based on seat_id
                String seatPriceQuery = "SELECT f.price FROM Seat s JOIN Fare f ON s.seat_type = f.seat_type WHERE s.Seat_id = ? AND f.screen_id = (SELECT screen_id FROM ShowTime WHERE show_id = ?)";
                PreparedStatement pstPrice = con.prepareStatement(seatPriceQuery);
                pstPrice.setInt(1, seat_id);
                pstPrice.setInt(2, show_id);

                ResultSet rsPrice = pstPrice.executeQuery();
                if (rsPrice.next()) {
                    double price = rsPrice.getDouble("price");
                    totalPrice += price;
                    seatPrices.add(price);
                } else {
                    throw new Exception("Price for Seat ID " + seat_id + " not found.");
                }
            }

            // Insert into bookings table
            String bookingQuery = "INSERT INTO bookings (customer_id, no_of_tickets, show_time, show_date, show_id, seat_id, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(bookingQuery, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, customer_id);
            pst.setInt(2, seatIds.size());
            pst.setTime(3, show_time);
            pst.setDate(4, show_date);
            pst.setInt(5, show_id);
            pst.setInt(6, seatIds.get(0));  // Arbitrarily setting the first seat_id (for reference)
            pst.setDouble(7, totalPrice);
            pst.executeUpdate();

            // Retrieve the generated booking_id
            ResultSet rs = pst.getGeneratedKeys();
            int booking_id;
            if (rs.next()) {
                booking_id = rs.getInt(1);
            } else {
                throw new Exception("Failed to retrieve booking_id.");
            }

            // Insert into Multiple_Tickets table
            String multipleTicketsQuery = "INSERT INTO Multiple_Tickets (booking_id, seat_id) VALUES (?, ?)";
            PreparedStatement pstMultipleTickets = con.prepareStatement(multipleTicketsQuery);

            for (int i = 0; i < seatIds.size(); i++) {
                pstMultipleTickets.setInt(1, booking_id);
                pstMultipleTickets.setInt(2, seatIds.get(i));
                pstMultipleTickets.executeUpdate();
            }


            System.out.println("Booking successful. Total Price: " + totalPrice);

        } catch (Exception e) {
            throw e;
        }
    }
}
