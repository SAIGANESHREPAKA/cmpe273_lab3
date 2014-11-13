
package edu.sjsu.cmpe.cache.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        
        
        
        
        
        CacheServiceInterface server1 = new DistributedCacheService(
                "http://localhost:3000");
        CacheServiceInterface server2 = new DistributedCacheService(
                "http://localhost:3001");
        CacheServiceInterface server3 = new DistributedCacheService(
                "http://localhost:3002");
        CacheServerLookup<CacheServiceInterface> servers = new CacheServerLookup<CacheServiceInterface>();

        servers.add(server1);

        servers.add(server2);

        servers.add(server3);

       
        HashMap<Integer, String> data = new HashMap<Integer, String>();
        data.put(1, "a");
        data.put(2, "b");
        data.put(3, "c");
        data.put(4, "d");
        data.put(5, "e");
        data.put(6, "f");
        data.put(7, "g");
        data.put(8, "h");
        data.put(9, "i");
        data.put(10, "j");
        String dataValue = "";

        CacheServiceInterface cache = null;

        int bucket = 0;

        for (int i = 1; i <= 10; i++) {
            dataValue = data.get(i);
            bucket = Hashing.consistentHash(
                    Hashing.md5().hashString(Integer.toString(i)),
                    servers.getSize());
            cache = servers.get(bucket);

            System.out.println("Put =" + dataValue
                    + " into cache => " + cache.toString());
            cache.put(i, dataValue);

        }

        for (int i = 1; i <= 10; i++) {
            dataValue = data.get(i);
            bucket = Hashing.consistentHash(
                    Hashing.md5().hashString(Integer.toString(i)),
                    servers.getSize());
            cache = servers.get(bucket);

            System.out.println("get => "
                    + cache.toString() + ", value = " + cache.get(i));
        }

        System.out.println("Exiting Cache Client...");
    }

}