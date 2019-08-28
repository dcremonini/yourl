package com.yourl.controller;

import com.google.common.hash.Hashing;
import com.yourl.controller.dto.ShortenUrlRequest;
import com.yourl.controller.dto.UrlResponseDto;
import com.yourl.service.IUrlStoreService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by david on 2015-06-02.
 */
@RestController
public class UrlController {

    private IUrlStoreService urlStoreService;

    public UrlController(IUrlStoreService urlStoreService) {
        this.urlStoreService = urlStoreService;
    }

    @GetMapping("/{id}")
    public void redirectToUrl(@PathVariable String id, HttpServletResponse resp) throws Exception {
        final String url = urlStoreService.findUrlById(id);
        if (url != null) {
            resp.addHeader("Location", url);
            resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("/urls")
    public List<UrlResponseDto> getAllUrls(HttpServletResponse resp) {
        return  urlStoreService.getAll();
    }

    @PostMapping("/")
    public void shortenUrl(HttpServletRequest httpRequest,
                           @Valid ShortenUrlRequest request,
                           BindingResult bindingResult) {
        String url = request.getUrl();

        if (!isUrlValid(url)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, String.format("Invalid url format: %s", url));
        }

        final String id = Hashing.murmur3_32()
                .hashString(url, StandardCharsets.UTF_8).toString();
        urlStoreService.storeUrl(id, url);
        String requestUrl = httpRequest.getRequestURL().toString();
        String prefix = requestUrl.substring(0, requestUrl.indexOf(httpRequest.getRequestURI(),
                "http://".length()));
    }

    private boolean isUrlValid(String url) {
        boolean valid = true;
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            valid = false;
        }
        return valid;
    }
}
