import com.example.Practice1.classes.sqsMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class sqsMessageTest {

    sqsMessage object = new sqsMessage(6, "Chicken tikka" , "Pizza" , "xyz@abc.com");

    @Test
    void getOrderid() { assertEquals(6, object.getOrderid()); }

    @Test
    void getItemName() { assertEquals("Chicken tikka", object.getItemname());}

    @Test
    void getCategory() { assertEquals("Pizza", object.getCategory()); }

    @Test
    void getEmail() { assertEquals("xyz@abc.com", object.getEmail());
    }

    @Test
    void SQSMessageBody() {
        object.SQSMessageBody();
        assertEquals(6, object.getOrderid());
        assertEquals("Chicken tikka" , object.getItemname());
        assertEquals("Pizza" , object.getCategory());
        assertEquals("xyz@abc.com" , object.getEmail());
    }
}
