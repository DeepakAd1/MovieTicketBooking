package movieTicketBooking;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

public class Bookings {
    public int booking_id;
    public int customer_id;
    public int no_of_tickets;
    public Time show_time;
    public Date show_date;
    public int show_id;
    public int seat_id;
    public double price;
}
