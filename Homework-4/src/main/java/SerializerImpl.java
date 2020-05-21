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
        return Set.of(float.class, double.class,
                short.class, int.class, long.class,
                char.class, boolean.class).contains(type);
    }
    public boolean isSimpleClass(String className){
        return Set.of("Float", "Double",
                "Short", "Integer", "Long",
                "Character", "Boolean").contains(className);
    }
    @Override
    public String serialize(Object o) throws IllegalAccessException {
        String className = o.getClass().getSimpleName();
        String output = "";
        //get begin of the output string
        output += serializerStrategy.head(className);
        if(isSimpleClass(className)){
            output = serializerStrategy.body(output, o.toString());
        }
        else {
            for (Field field : o.getClass().getFields()) {
                field.setAccessible(true);
                Class fieldClass = field.getType();
                String fieldName = field.getName();
                if (isSimpleType(fieldClass)) {
                    output = serializerStrategy.body(output, fieldName);
                } else {
                    System.out.println("Field type should be primitive!");
                    return null;
                }
            }
        }

        //get end of the output string
        output += serializerStrategy.footer(className);
        return output;
    }
}
