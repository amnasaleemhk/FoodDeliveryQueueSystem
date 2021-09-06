package com.example.Practice1.classes;

import java.sql.*;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Orders {
     int idOrder;
     String ItemName;
     String Category;
     int PreparationTime;
     String Status;
     String CustomerName;
     String EmailID;

    //Constructors
    public Orders() {
        super();
    }

    public Orders(int Order_ID, String item_name, String category,
                  int prep_time, String status, String customer_name, String email_ID) {
        this.idOrder = Order_ID;
        this.ItemName = item_name;
        this.Category = category;
        this.PreparationTime = prep_time;
        this.Status = status;
        this.CustomerName = customer_name;
        this.EmailID = email_ID;
    }

    //Getters
    public int getIdOrder() {
        return idOrder;
    }

    public String getItemName() {
        return ItemName;
    }

    public String getCategory() {
        return Category;
    }

    public int getPreparationTime() {
        return PreparationTime;
    }

    public String getStatus() {
        return Status;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getEmailID() {
        return EmailID;
    }


    public static void insertpayloadintoDB(String itemname, String category, int preparationtime, String status, String customername, String emailid) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        int rs3 = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fooddelivery", "root", "");
            stmt = conn.createStatement();
            rs3 = stmt.executeUpdate("INSERT into fooddelivery.orders(ItemName, Category, PreparationTime, Status, CustomerName, EmailID) VALUES ('"+itemname+"','"+category+"', " + preparationtime + " ,'" + status + "','" + customername + "','" + emailid + "')");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            conn.close();
            stmt.close();
        }
    }

    public  String Receipt() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fooddelivery", "root", "");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM fooddelivery.orders WHERE idOrder=(SELECT max(idOrder) FROM fooddelivery.orders)");

            while (rs.next()) {
                idOrder =  rs.getInt("idOrder");
                CustomerName = rs.getString("CustomerName");
                Status = rs.getString("Status");

                System.out.println("Dear " + rs.getString("CustomerName") + "," + " Thank You for ordering!");
                System.out.println("Order placing time: " + LocalDateTime.now());
                System.out.println("________________Order Details_________________");
                System.out.println(("Order #") + (rs.getString("idOrder")));
                System.out.println(("Customer: ") + (rs.getString("CustomerName")));
                System.out.println(("Status: ") + (rs.getString("Status")));

            }

            ResultSet rs2 = stmt.executeQuery("SELECT SUM(PreparationTime) as sum_prep_time FROM fooddelivery.orders");
            while (rs2.next()) {
                int minimum_expected_time = rs2.getInt("sum_prep_time");
                int maximum_expected_time = minimum_expected_time + 10;
                 PreparationTime = (int) Math.floor(Math.random() * (maximum_expected_time - minimum_expected_time + 1) + minimum_expected_time);
                System.out.println("Expected Preparation Time of your meal  is " + PreparationTime + " minutes");
                System.out.println("You'll receive an email once your order is ready to deliver . . .");
                System.out.println("______________________________________________");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
            stmt.close();
            rs.close();

        }
        return "{" +
                "Order # " + idOrder + '\n' +
                "CustomerName: " + CustomerName + '\n' +
                "Status: " + Status + '\n' +
                "Expected Preparation Time (minutes): " + PreparationTime  +  '\n' +
                '}';
    }
}
