package movieTicketBooking;

import java.util.Scanner;

public class Customer {
    public int customer_id;
    public String customer_name;
    public String email_id;
    public String phone_no;
    public String password;

    public Customer(Scanner sc){
        System.out.print("Enter customer name: ");
        this.customer_name = sc.nextLine();

        System.out.print("Enter email ID: ");
        this.email_id = sc.nextLine();

        System.out.print("Enter phone number: ");
        this.phone_no = sc.nextLine();

        System.out.print("Enter password: ");
        this.password = sc.nextLine();
    }
}

