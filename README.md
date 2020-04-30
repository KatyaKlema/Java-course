Реализовать следующий интерфейс:

```Java
public interface Serializer {
    String serialize(Object o);
}
```

Интерфейс на вход принимает любой объект и переводит его в строку. Нужно сделать две реализации, первая переводит объект в Json, вторая в Xml.

Все поля объекта должны сериализовываться рекурсивно (кроме примитивных типов и сторк), отдельный формат сериализации должен поддерживаться для массивов и коллекций. Должны быть отступы для вложенных полей.
При реализации можно использовать только стандартные классы jdk из пакетов java.lang и java.util. Подключать сторонние библиотеки нельзя.

Обратите внимание, что ваши 2 реализации сериалайзера не должны иметь дублирующую логику. Для избежание дублирования логики можно использовать паттерн template method, а лучше strategy. Код должен быть хорошо читаем, и содержать методы, удовлетворяющие принципу single responsibility.

Пример:
Если вы сериализуете экземпляр класса Person, то результирующий формат должен выглядеть так:
```Java
class Person {
    private final String firstName;
    private final String lastName;
    private final Address address;
    private final List<String> phoneNumbers;
}
class Address {
    private final String city;
    private final String postalCode;
}
```
```JSON
Json:

{
   "firstName": "Иван",
   "lastName": "Иванов",
   "address": { 
       "city": "Москва",
       "postalCode": "101101"
   },
   "phoneNumbers": [ # массив или коллекция
       "123-1234-523",
       "432-23-232-23"
   ]
}
```
```XML
Xml:


<Person>
    <firstName>Иван<firstName>
    <lastName>Иванов</lastName>
    <address> 
        <city>Москва</city>
        <postalCode>101101<postalCode>
    </address>
    <phoneNumbers>
        <1>123-1234-523</1>
        <2>432-23-232-23</2>
    </phoneNumbers>
</Person>
```
