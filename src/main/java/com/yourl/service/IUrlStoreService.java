package com.yourl.service;

import com.yourl.controller.entity.UrlEntity;

import java.util.List;

/**
 * Created by david on 2015-06-02.
 */
public interface IUrlStoreService {

    String findUrlById(String id);

    void storeUrl(String id, String url);

    List<UrlEntity> getAll();

    void clear();
}
