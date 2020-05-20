1) Ознакомится с 
http://docs.oracle.com/javase/tutorial/java/generics/  
http://howtodoinjava.com/2014/07/24/java-generics-what-is-pecs-producer-extends-consumer-super/
2) Посмотреть доклад &#39;Неочевидные Дженерики&#39; https://www.youtube.com/watch?v=_0c9Fd9FacU&amp;t=1534s
3) Параметризовать CountMap и реализовать его. 
```java
public interface CountMap {
    // добавляет элемент в этот контейнер. 
    void add(Object o);

     //Возвращает количество добавлений данного элемента
    int getCount(Object o);

    //Удаляет элемент и контейнера и возвращает количество его добавлений(до удаления)
    int remove(Object o);

    //количество разных элементов
    int size();

     //Добавить все элементы из source в текущий контейнер, при совпадении ключей,     суммировать
значения
    void addAll(CountMap source);

    //Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений
    Map toMap();

    //Тот же самый контракт как и toMap(), только всю информацию записать в destination
    void toMap(Map destination);
}

пример использования 
        CountMap<Integer>; map = new CountMapIml<>();

        map.add(10);
        map.add(10);
        map.add(5);
        map.add(6);
        map.add(5);
        map.add(10);
/*
        int count = map.getCout(5); // 2
        int count = map.getCout(6); // 1
        int count = map.getCout(10); // 3*/
        
```
