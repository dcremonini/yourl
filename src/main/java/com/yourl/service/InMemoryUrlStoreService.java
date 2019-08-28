package com.yourl.service;

import com.yourl.controller.dto.UrlResponseDto;
import org.springframework.stereotype.Service;

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
    public List<UrlResponseDto> getAll() {
        return null;
    }
}
