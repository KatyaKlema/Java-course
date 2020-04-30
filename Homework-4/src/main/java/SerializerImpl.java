import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Set;

public class SerializerImpl implements Serializer {
    SerializerStrategy serializerStrategy;

    public SerializerImpl(SerializerStrategy serializerStrategy){
        this.serializerStrategy = serializerStrategy;
    }

    public boolean isSimpleType(Class<?> type){
        return Set.of(Float.class, Double.class,
                Short.class, Integer.class, Long.class,
                Character.class, Boolean.class).contains(type);
    }
    @Override
    public String serialize(Object o) throws IllegalAccessException {
        String className = o.getClass().getSimpleName();
        String output = "";

        //get begin of the output string
        output += serializerStrategy.head(className);

        for(Field field : o.getClass().getDeclaredFields()){
            field.setAccessible(true);
            Class fieldClass = field.getType();
            String fieldName = field.getName();
            if(isSimpleType(fieldClass)){
                serializerStrategy.body(output, fieldName);
            }
            else{
                System.out.println("Field type should be primitive!");
                return null;
            }
        }

        //get end of the output string
        output += serializerStrategy.footer(className);
        return output;
    }
}
