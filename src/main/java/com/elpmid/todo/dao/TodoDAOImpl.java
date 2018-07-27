package com.elpmid.todo.dao;

import com.elpmid.todo.domain.TodoDomain;
import com.elpmid.todo.dto.TodoQueryStatus;
import com.elpmid.todo.dto.TodoStatus;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TodoDAOImpl implements TodoDAO {

    private final CacheManager cacheManager;

    public TodoDAOImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public List<TodoDomain> findAll(TodoQueryStatus status) {
        ConcurrentMapCache concurrentMapCache = (ConcurrentMapCache) cacheManager.getCache("todos");
        Set<Map.Entry<Object, Object>> concurrentHashMap = concurrentMapCache.getNativeCache().entrySet();
        List<TodoDomain> todoDomains = concurrentHashMap.stream().map(entry -> (TodoDomain) entry.getValue()).collect(Collectors.toList());
        switch (status) {
            case DONE:
                return todoDomains.stream().filter(todoDomain -> todoDomain.getStatus() == TodoStatus.DONE)
                        .sorted(Comparator.comparing(TodoDomain::getName, String.CASE_INSENSITIVE_ORDER))
                        .collect(Collectors.toList());
            case PENDING:
                return todoDomains.stream().filter(todoDomain -> todoDomain.getStatus() == TodoStatus.PENDING)
                        .sorted(Comparator.comparing(TodoDomain::getName, String.CASE_INSENSITIVE_ORDER))
                        .collect(Collectors.toList());
            default:
                return todoDomains.stream().sorted(Comparator.comparing(TodoDomain::getName, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList());
        }
    }

    @Override
    public Optional<TodoDomain> findOneById(UUID id) {
        ConcurrentMapCache concurrentMapCache = (ConcurrentMapCache) cacheManager.getCache("todos");
        TodoDomain todo = (TodoDomain) concurrentMapCache.getNativeCache().get(id);
        return Optional.ofNullable(todo);
    }

    @Override
    @Cacheable(cacheNames = {"todos"}, key = "#todo.id")
    public TodoDomain save(TodoDomain todo) {
        throw new DataIntegrityViolationException("");
        //return todo;
    }

    @Override
    @CacheEvict(cacheNames = {"todos"}, key = "#id")
    public void deleteById(UUID id) {
    }

    @Override
    @CacheEvict(cacheNames = {"todos"}, allEntries = true)
    public void deleteAll() {
    }


}
