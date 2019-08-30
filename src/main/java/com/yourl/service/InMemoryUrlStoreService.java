package com.yourl.service;

import com.yourl.controller.entity.UrlEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by david on 2015-06-02.
 */
@Service
public class InMemoryUrlStoreService implements IUrlStoreService {

    private Map<String, String> urlByIdMap = new ConcurrentHashMap<>();

    @Override
    public String findUrlById(String id) {
        return urlByIdMap.get(id);
    }

    @Override
    public void storeUrl(String id, String url) {
        urlByIdMap.put(id, url);
    }

    @Override
    public List<UrlEntity> getAll() {
        List<UrlEntity> urlResponseDtos = new ArrayList<>(2);
        urlResponseDtos.add(new UrlEntity("short1", "http://long1"));
        urlResponseDtos.add(new UrlEntity("short2", "http://long2"));
        return urlResponseDtos;
    }

    @Override
    public void clear() {
        this.urlByIdMap.clear();
    }
}
