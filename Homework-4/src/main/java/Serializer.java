import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface Serializer {
    String serialize(Object o) throws IllegalAccessException;
}


