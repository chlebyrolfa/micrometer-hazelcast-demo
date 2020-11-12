package com.example.demo;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.cache.CacheMetricsRegistrar;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    CacheManager cacheManager;
    @Autowired
    CacheMetricsRegistrar cacheMetricsRegistar;


    @Cacheable("books")
    public String getBookNameByIsbn(String isbn) {
        cacheMetricsRegistar.bindCacheToRegistry(cacheManager.getCache("books"));
        return findBookInSlowSource(isbn);
    }

    private String findBookInSlowSource(String isbn) {
        // some long processing
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        return "Sample Book Name";
    }

}
