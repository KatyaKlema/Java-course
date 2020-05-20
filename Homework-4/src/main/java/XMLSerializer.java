public class XMLSerializer implements SerializerStrategy{
    @Override
    public String head(String className) {
        return  "<" + className + ">/n";
    }

    @Override
    public String body(String output, String fieldName) {
        output += "\t<" + fieldName + "> ";
        return output;
    }

    @Override
    public String footer(String className) {
        return "</" + className + ">";
    }
}
