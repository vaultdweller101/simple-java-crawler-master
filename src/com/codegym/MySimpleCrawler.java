package com.codegym;

import com.codegym.crawler.Crawler;
import com.codegym.crawler.impl.BatdongsanCrawler;
import com.codegym.crawler.utils.IHttpService;
import com.codegym.crawler.utils.URLGetRequest;

public class MySimpleCrawler {

    public static void main(String[] args) throws Exception {
        IHttpService urlGetRequest = new URLGetRequest();
        Crawler[] crawlers = new Crawler[1];
        crawlers[0] = new BatdongsanCrawler(urlGetRequest);

        for (Crawler crawler: crawlers) {
            if (crawler != null) crawler.inspect();
        }
    }
}
