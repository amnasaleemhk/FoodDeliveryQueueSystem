import com.example.Practice1.classes.SES;
import org.junit.jupiter.api.Test;

public class SESTest {
    SES object = new SES();
    @Test
    void notifyUserByEmail() {
        object.notifyUserByEmail("abc@nmop");

    }
}
