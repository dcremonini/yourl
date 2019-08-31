package com.yourl.controller.dto;

import java.util.Objects;

public class UrlResponseDto {

    private String shortUrl;
    private String url;

    public UrlResponseDto() {
    }

    public UrlResponseDto(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.url = longUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UrlResponseDto)) return false;
        UrlResponseDto that = (UrlResponseDto) o;
        return shortUrl.equals(that.shortUrl) &&
                url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortUrl, url);
    }

    @Override
    public String toString() {
        return "UrlResponseDto{" +
                "shortUrl='" + shortUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
