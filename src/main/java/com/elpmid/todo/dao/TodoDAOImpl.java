package com.elpmid.todo.dao;

import com.elpmid.todo.domain.TodoDomain;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@CacheConfig(cacheNames = {"todos"})
public class TodoDAOImpl implements TodoDAO {

    private final CacheManager cacheManager;

    public TodoDAOImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public List<TodoDomain> findAll() {
        ConcurrentMapCache concurrentMapCache = (ConcurrentMapCache) cacheManager.getCache("todos");
        Set<Map.Entry<Object, Object>> concurrentHashMap = concurrentMapCache.getNativeCache().entrySet();
        return concurrentHashMap.stream().map(entry -> (TodoDomain) entry.getValue()).collect(Collectors.toList());
    }

    @Override
    public Optional<TodoDomain> findOneById(UUID id) {
        ConcurrentMapCache concurrentMapCache = (ConcurrentMapCache) cacheManager.getCache("todos");
        TodoDomain todo = (TodoDomain) concurrentMapCache.getNativeCache().get(id);
        return Optional.of(todo);
    }

    @Override
    @Cacheable(key = "#todo.id")
    public TodoDomain save(TodoDomain todo) {
        return todo;
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteById(UUID id) {
    }
}
