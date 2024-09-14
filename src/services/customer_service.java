package services;

import movieTicketBooking.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customer_service {
    public static int addNewCustomer(Customer customer) throws SQLException {
        Connection connection=DbConnection.getConnection();
        String query = "INSERT INTO Customer (customer_name, email_id, phone_no, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.customer_name);
            statement.setString(2, customer.email_id);
            statement.setString(3, customer.phone_no);
            statement.setString(4, customer.password);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated customer_id
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if insertion failed
    }
}
