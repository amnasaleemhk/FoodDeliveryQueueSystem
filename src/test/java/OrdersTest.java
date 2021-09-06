import com.example.Practice1.classes.Orders;

import org.junit.jupiter.api.Test;

import java.sql.*;

import static com.example.Practice1.classes.Orders.insertpayloadintoDB;
import static org.junit.jupiter.api.Assertions.*;

class OrdersTest {
    Orders object = new Orders(1, "Fajita" , "Pizza", 5 , "Purchased" , "Amna" , "abc@gmail.com");


    @Test
     void getIdOrder() {
        assertEquals(1, object.getIdOrder());
    }

    @Test
     void getItemName(){
        assertEquals("Fajita" , object.getItemName());
    }

    @Test
     void getCategory(){
        assertEquals("Pizza", object.getCategory());
    }

    @Test
     void getPreparationTime(){
        assertEquals(5, object.getPreparationTime());
    }

    @Test
     void getStatus(){
        assertEquals("Purchased" , object.getStatus());
    }

    @Test
     void getCustomerName(){
        assertEquals("Amna" , object.getCustomerName());
    }

    @Test
     void getEmailID(){
        assertEquals("abc@gmail.com" , object.getEmailID());
    }

    @Test
     void insertpayloadintoDBTest () throws SQLException {
        Orders object2 = new Orders(2, "fajita" , "Pizza", 5 , "Purchased" , "Amna" , "abc@gmail.com");

        String itemname = object2.getItemName();
        String category = object2.getCategory();
        int preparationtime = object2.getPreparationTime();
        String status = object2.getStatus();
        String customername = object2.getCustomerName();
        String emailid = object2.getEmailID();

        insertpayloadintoDB(itemname,category,preparationtime,status,customername,emailid);
        Connection conn = null;
        Statement stmt = null;
        ResultSet testrs1;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fooddelivery", "root", "");
            stmt = conn.createStatement();
            testrs1 = stmt.executeQuery("SELECT * FROM fooddelivery.orders WHERE idOrder=(SELECT max(idOrder) FROM fooddelivery.orders)");

            assertTrue(testrs1.next());

            assertEquals(itemname, testrs1.getString("ItemName"));
            assertEquals(category, testrs1.getString("Category"));
            assertEquals(preparationtime, testrs1.getInt("PreparationTime"));
            assertEquals(status, testrs1.getString("Status"));
            assertEquals(customername, testrs1.getString("CustomerName"));
            assertEquals(emailid, testrs1.getString("EmailID"));

            assertFalse(testrs1.next());
        }

        catch(Exception e){
            fail(e.toString());
        }
    }

    @Test
    void ReceiptTest2() throws SQLException {
        object.Receipt();
    }
}


