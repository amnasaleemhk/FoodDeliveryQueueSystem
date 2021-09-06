import com.example.Practice1.classes.SQS;
import com.example.Practice1.classes.sqsMessage;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import com.example.Practice1.classes.SQS;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SQSTest {
SQS object = new SQS();
sqsMessage obj = new sqsMessage(4, "CrushDeal" , "Burger" , "xyz@abc.com");
     //SQS object2 = new Gson().fromJson(String.valueOf(obj), sqsMessage.class);
    @Test
    void send() {
        System.out.println((object.send(obj)));
    }

    @Test
    void read() {
        System.out.println(object.read());
    }

    @Test
    void delete() {
        try{
            object.delete();
        }
        catch(Exception e){
            e.getMessage();

        }
    }

    @Test
    void Email() throws SQLException {
        object.EmailnDel();
    }

    @Test
    void updateDatabase() throws SQLException {
        object.updateDataBase();
    }
}
