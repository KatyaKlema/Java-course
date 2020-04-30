public interface SerializerStrategy {
    String head (String className);

    String body (String output, String fieldName);

    String footer(String className);
}
