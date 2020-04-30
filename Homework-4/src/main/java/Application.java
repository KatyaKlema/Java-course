import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXBException;
import java.io.IOError;
import java.io.IOException;

public class Application {
    public static void main(String ... args) throws IllegalAccessException {
        Integer integer = 234;
        Serializer serializerImpl = new SerializerImpl(new JSONSerializer());
        String jsonOuput = serializerImpl.serialize(integer);
        System.out.println((jsonOuput));
    }
}
