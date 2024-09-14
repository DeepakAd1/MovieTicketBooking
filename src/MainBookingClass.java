import movieTicketBooking.Customer;
import services.DbConnection;

import java.sql.*;
import java.sql.Date;
import java.util.Scanner;

import java.util.*;

import static services.booking_service.addBookings;
import static services.customer_service.addNewCustomer;
import static services.printing_screen.printing_screens;
import static services.screen_service.printAvailableScreens;
import static services.show_service.printAvailableShows;

public class MainBookingClass {
    Scanner sc = new Scanner(System.in);




    public static void main(String[] args) throws SQLException {

        printing_screens(2,4);

//        Scanner sc=new Scanner(System.in);
//
//        System.out.println("Enter new customer or not (y/n): ");
//        char ch = sc.next().charAt(0);
//
//        if(ch=='y'){
//            Customer c=new Customer(sc);
//            System.out.println("Enter your name: ");
//            int customer_name = sc.nextInt();
//            int id=addNewCustomer(c);
//            System.out.println("Your id: "+id);
//        }
//
//        try {
//            System.out.println("Enter customer ID: ");
//            int customer_id = sc.nextInt();
//
//            System.out.println("Enter theater ID: ");
//            int theater_id = sc.nextInt();
//
//            printAvailableScreens(theater_id);
//
//            System.out.println("Enter screen ID: ");
//            int screen_id = sc.nextInt();
//
//
//
//            System.out.println("Enter show date (YYYY-MM-DD): ");
//            String showDateInput = sc.next();
//            Date show_date = Date.valueOf(showDateInput);
//            printAvailableShows(screen_id, Date.valueOf(showDateInput));
//            System.out.println("Enter show ID: ");
//            int show_id = sc.nextInt();
//
//            System.out.println("Enter show time (HH:MM:SS): ");
//            String showTimeInput = sc.next();
//            Time show_time = Time.valueOf(showTimeInput);
//            printing_screens(screen_id,show_id);
//
//            System.out.println("Enter the number of seats to book: ");
//            int no_of_tickets = sc.nextInt();
//
//            List<Integer> seatIds = new ArrayList<>();
//            for (int i = 1; i <= no_of_tickets; i++) {
//                System.out.println("Enter seat ID " + i + ": ");
//                int seat_id = sc.nextInt();
//                seatIds.add(seat_id);
//            }
//
//            // Call the addBookings method with the collected data
//            addBookings(customer_id, show_time, show_date, show_id, seatIds);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}

