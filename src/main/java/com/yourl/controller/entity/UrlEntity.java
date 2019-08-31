package com.yourl.controller.entity;

import java.util.Objects;

public class UrlEntity {

    private String shortUrl;
    private String url;

    public UrlEntity(String shortUrl, String url) {
        this.shortUrl = shortUrl;
        this.url = url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlEntity)) return false;
        UrlEntity urlEntity = (UrlEntity) o;
        return shortUrl.equals(urlEntity.shortUrl) &&
                url.equals(urlEntity.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortUrl, url);
    }

    @Override
    public String toString() {
        return "UrlEntity{" +
                "shortUrl='" + shortUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
