package net.hackatom.configs;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

public class CacheEventLogger implements CacheEventListener<Object, Object> {

    public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
        System.out.println("KEY:" + cacheEvent.getKey().toString() + " --- ");
    }
}
