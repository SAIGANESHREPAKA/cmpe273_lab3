package edu.sjsu.cmpe.cache.client;

import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.hash.Hashing;

public class CacheServerLookup<T> {
    
    private TreeMap<Integer, T> serversMap = new TreeMap<Integer, T>();
    
    public int getSize() {
        return serversMap.size();
    }

    public T get(int key) {
    int hash=Hashing.md5().hashLong(key).asInt();
        if (serversMap.isEmpty()) {
            return null;
        }
        if (!serversMap.containsKey(hash)) {

            SortedMap<Integer, T> tailMap = serversMap.tailMap(hash);

            hash = tailMap.isEmpty() ? serversMap.firstKey() : tailMap.firstKey();
        }
        return serversMap.get(hash);
    }

    public void add(T server) {
        int index = server.toString().lastIndexOf(':');
        int key = Integer.parseInt(server.toString().substring(index + 1));
        int hash=Hashing.md5().hashLong(key).asInt();
        serversMap.put(hash, server);
    }
    
}