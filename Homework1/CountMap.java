package com.countmappackage;

import java.util.*;
public interface CountMap<K, V> {
    // добавляет элемент в этот контейнер. 
    <T extends K, V>void add(T o);

    //Возвращает количество добавлений данного элемента
    <T extends K, V>int getCount(T o);

    //Получает массив ключей
    ArrayList<K> getKeys();
    //Удаляет элемент и контейнера и возвращает количество его добавлений(до удаления)
    <T extends K, V>int remove(T o);

    //количество разных элементов
    int size();

    //Добавить все элементы из source в текущий контейнер, при совпадении ключей,     суммировать
    //значения
    void addAll(CountMap<?super K, V> source);

    //Вернуть java.util.Map. ключ - добавленный элемент, значение - количество его добавлений
    Map<? super K, Integer> toMap();

    //Тот же самый контракт как и toMap(), только всю информацию записать в destination
    void toMap(Map<? extends K, Integer> destination);
}


