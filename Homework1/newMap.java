package com.countmappackage;
import java.util. *;

public class newMap<K, V> implements CountMap<K, V>{
    private Map<K, Integer> m = new HashMap<>();
    private Integer isUsed = 1;
    public newMap(Map<K, Integer> _m){
        this.m = _m;
    }

    @Override
    public <T extends K, Integer> void add(T o){
        this.m.put(o, isUsed);
    }
    @Override
    public <T extends K, Integer> int getCount(T o){
        return this.m.get(o);
    }

    @Override
    public ArrayList<K> getKeys(){
        ArrayList<K> ret = new ArrayList<>();
        for (K key: m.keySet()){
            ret.add(key);
        }
        return ret;
    }

    @Override
    public <T extends K, V> int remove(T o){
        return this.m.remove(o);
    }

    @Override
    public int size(){
        return this.m.size();
    }

    @Override
    public void addAll(CountMap<? super K, V> source){
        ArrayList<K> keys = (ArrayList<K>)source.getKeys();
        for (K key : keys){
            for(int i = 0; i < this.getCount(key); ++i){
                this.m.put(key, isUsed);
            }
        }
    }

    @Override
    public Map<? super K, Integer> toMap(){
        return this.m;
    }

    @Override
    public void toMap(Map<? extends K, Integer> destination){
        destination = this.m;
    }
}
