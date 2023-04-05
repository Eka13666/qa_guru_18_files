import com.fasterxml.jackson.databind.ObjectMapper;
import model.Cat;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonParsingTest {

    ClassLoader cl = JsonParsingTest.class.getClassLoader();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void jsonParsingTest() throws Exception {

        try (
                InputStream resource = cl.getResourceAsStream("cat.json");
                InputStreamReader reader = new InputStreamReader(resource)
        ) {
            Cat jsonCat = objectMapper.readValue(reader, Cat.class);
            assertThat(jsonCat.name).contains("Shuuji");
            assertThat(jsonCat.breed).contains("snow-shoes");
            assertThat(jsonCat.color).contains("gray");
            assertThat(jsonCat.age).isEqualTo(0.3);
            assertThat(jsonCat.relatives).contains("Beckham");
        }
    }
}
