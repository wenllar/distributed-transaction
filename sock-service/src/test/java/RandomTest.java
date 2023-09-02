import com.wenllar.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = {Application.class})
@RunWith(SpringRunner.class)
public class RandomTest {


    @Test
    public void testRandom(){
        System.out.println((int) (Math.floor(Math.random()* 1000000000)));

    }
}
