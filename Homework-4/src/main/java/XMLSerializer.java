public class XMLSerializer implements SerializerStrategy{
    @Override
    public String head(String className) {
        return  "<" + className + ">/n";
    }

    @Override
    public String body(String output, String fieldName) {
        return "\t<" + fieldName + "> ";
    }

    @Override
    public String footer(String className) {
        return "</" + className + ">";
    }
}
