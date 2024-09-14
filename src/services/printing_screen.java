package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class printing_screen {





    public static void printing_screens(int screen_id,int show_id) throws SQLException {
        Connection con=DbConnection.getConnection();
        String sql1="select s.seat_id,m.seat_id is not null as booked_status from seat s left join multiple_tickets m on m.seat_id=s.seat_id "
                +"left join bookings b on b.booking_id=m.booking_id and b.show_id=? "
                +" where  screen_id=?";
        PreparedStatement pst= con.prepareStatement(sql1);
        pst.setInt(2,screen_id);
        pst.setInt(1,show_id);
        ResultSet booked_seats= pst.executeQuery();
        ResultSet b_s=booked_seats;




    // Corrected SQL query with the proper column name

        ResultSet rowsandseats=rowsandseats(screen_id);



        ResultSet rs_path_exit=rs_path_exit(screen_id);
        PreparedStatement pseatid=con.prepareStatement("select seat_id from seat where screen_id=?");
        pseatid.setInt(1,screen_id);
        ResultSet seat_id=pseatid.executeQuery();
        seat_id.next();
        int st_seat=seat_id.getInt("seat_id");
        int c=1;


        int maxCols=findMaxscreen(0,rowsandseats,rs_path_exit);

        rowsandseats=rowsandseats(screen_id);
        rs_path_exit=rs_path_exit(screen_id);
        for(int i=0;i<maxCols;i++){
            System.out.print("_"+"__");
        }
        System.out.println();

        while(rowsandseats.next()){

        int rows =-1;
        int seats=-1;
        int path_st_col=-1 ;
        int exit_st_row =-1;
        int exit_size=-1 ;
        int path_size=-1;
            rows = rowsandseats.getInt("total_no_of_rows");
            seats = rowsandseats.getInt("total_no_of_seats");
//            System.out.print("row :"+rows);
//            System.out.print("seats :"+seats);


        if(rs_path_exit.next()){
            path_st_col = rs_path_exit.getInt("start_column"); // e.g., 4, 3, 6
            exit_st_row = rs_path_exit.getInt("start_row"); // e.g., 4, 8
            exit_size = rs_path_exit.getInt("exit_size"); // e.g., 2, 2
            path_size = rs_path_exit.getInt("path_size");
        }

        print_class(rows,seats,path_size,path_st_col,exit_size,exit_st_row,2,st_seat,booked_seats,maxCols);

        }
    }

    private static int findMaxscreen(int maxCol,ResultSet rowsandseats,ResultSet rs_path_exit) throws SQLException {

        while(rowsandseats.next()){

            int rows =-1;
            int seats=-1;
            int path_st_col=-1 ;
            int exit_st_row =-1;
            int exit_size=-1 ;
            int path_size=-1;
            rows = rowsandseats.getInt("total_no_of_rows");
            seats = rowsandseats.getInt("total_no_of_seats");
//            System.out.print("row :"+rows);
//            System.out.print("seats :"+seats);


            if(rs_path_exit.next()){
                path_st_col = rs_path_exit.getInt("start_column"); // e.g., 4, 3, 6
                exit_st_row = rs_path_exit.getInt("start_row"); // e.g., 4, 8
                exit_size = rs_path_exit.getInt("exit_size"); // e.g., 2, 2
                path_size = rs_path_exit.getInt("path_size");
            }

           maxCol=Math.max(maxCol,seats/rows+path_size);

        }
        return maxCol;
    }


    public static void  print_class(int rows,int seats,int path_size,int path_st_col,int exit_size,int exit_st_row,int b,int st_seat,ResultSet booked,int max) throws SQLException {
            int rr = 0;
            int rc=0;

            for (int i = 1; i <= rows + rr; i++) {
                if (i == exit_st_row){
                    for(int l=0;l<exit_size;l++){
                    for (int j = 0; j < seats / rows + path_size; j++) {
                        System.out.print("E"+"  ");
                    }
                    System.out.println();
                    }
                    rr++;
                    continue;
                }

                for (int j = 1; j < seats / rows + path_size; j++) {
                    if (j == path_st_col) {
                        for (int k = 0; k < path_size; k++) {
                            System.out.print("P"+"  ");
                        }
                        rc++;
                        continue;
                    }
                    System.out.print(book(st_seat,booked)?"B  ":"NB "+"");
                }
                System.out.println();


            }
            for(int k = 1; k <=b; k++){
                for(int j=1;j<=max;j++){
                    System.out.print("-"+"--");
                }
                System.out.println();
            }
        }

    private static boolean book(int stSeat,ResultSet book) throws SQLException {
        int i=0;
        stSeat=stSeat%120;
        while(stSeat>i){
            book.next();
            i++;
        }
        return book.getBoolean("booked_status");
    }

    public static ResultSet rs_path_exit(int screen_id) throws SQLException {
        String path_exit_query =
                "SELECT p.start_column, p.path_size, e.start_row, e.exit_size " +
                        "FROM path p " +
                        "LEFT JOIN exits e ON p.class_id = e.class_id " +
                        "WHERE p.class_id IN (SELECT class_id FROM screen_class WHERE screen_id = ?)";
        Connection con=DbConnection.getConnection();
        PreparedStatement pst1 = con.prepareStatement(path_exit_query);
        pst1.setInt(1, screen_id);

        return pst1.executeQuery();
    }

    public static ResultSet rowsandseats(int screen_id) throws SQLException {
        Connection con=DbConnection.getConnection();
        PreparedStatement pst3 = con.prepareStatement("SELECT class_id, total_no_of_seats, total_no_of_rows,total_no_path FROM screen_class WHERE screen_id = ?");
        pst3.setInt(1, screen_id);
        return pst3.executeQuery();
    }
}


