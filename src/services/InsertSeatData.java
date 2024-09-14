package services;


    import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

    public class InsertSeatData {

        public static void main(String[] args) {
            Connection con = null;
            PreparedStatement pst = null;

            try {
                // Get a connection to the database
                con = DbConnection.getConnection();

                // Prepare the SQL insert statement (without seat_id)
                String sql = "INSERT INTO Seat (seat_type, seat_size, screen_id) VALUES (?, ?, ?)";
                pst = con.prepareStatement(sql);

                // Insert seats for Screen 1
                insertSeatsForScreen(pst, 1);
                // Insert seats for Screen 2
                insertSeatsForScreen(pst, 2);
                // Insert seats for Screen 3
                insertSeatsForScreen(pst, 3);

                System.out.println("Seat data inserted successfully!");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pst != null) pst.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        private static void insertSeatsForScreen(PreparedStatement pst, int screenId) throws SQLException {
            // Insert Regular Seats
            for (int i = 0; i < 40; i++) {
                pst.setString(1, "Regular");
                pst.setInt(2, 1);
                pst.setInt(3, screenId);
                pst.addBatch();
            }

            // Insert Premium Seats
            for (int i = 0; i < 40; i++) {
                pst.setString(1, "Premium");
                pst.setInt(2, 1);
                pst.setInt(3, screenId);
                pst.addBatch();
            }

            // Insert Luxury Seats
            for (int i = 0; i < 40; i++) {
                pst.setString(1, "Luxury");
                pst.setInt(2, 1);
                pst.setInt(3, screenId);
                pst.addBatch();
            }

            // Execute the batch for the screen
            pst.executeBatch();
        }
    }


