import com.example.Practice1.resources.HelloResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloResourceTest {

    @Test
    void hello() {
        try {
            String test = new HelloResource().hello();
            assertEquals("Sit Tight! Your meal is just few minutes away",test);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void takeOrder() {
        Response test = null;
        try {
            test = new HelloResource().postJSON("{\n" +
                    "\"ItemName\": \"Stuffed Chicken tikka\",\n" +
                    "\"Category\": \"Burger\",\n" +
                    "\"PreparationTime\": 15,\n" +
                    "\"Status\": \"Purchased\",\n" +
                    "\"CustomerName\": “amna”,\n" +
                    "\"EmailID\":\"amna@abc.com\",\n" +

                    "}");
            assertEquals(200,test.getStatus());
            test.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void next() throws Exception {
        Response test = new HelloResource().getJSON();
        assertEquals(200,test.getStatus());
    }

    @Test
    void Email() throws SQLException {
        Response test = new HelloResource().postJson();
        assertEquals(200, test.getStatus());
    }
}
