package com.codegym.crawler;

import com.codegym.crawler.utils.IHttpService;
import com.codegym.models.ClassifiedAd;

import java.util.ArrayList;
import java.util.List;

public abstract class Crawler {
    protected IHttpService httpService;

    public Crawler(IHttpService httpService) {
        this.httpService = httpService;
    };

    /**
     * Đây là thao tác chung cho tất cả các trang web mà chúng ta muốn thu thập tin
     * @return Iterable<ClassifiedAd>
     */
    public Iterable<ClassifiedAd> inspect() throws Exception {
        List<ClassifiedAd> classifiedAds = new ArrayList<>();
        Iterable<String> categories = inspectHomepage();

        for (String category: categories) {
            System.out.println("Category: " + category);
            Iterable<String> classifiedAdLinks = inspectCategory(category);

            for (String classifiedAdLink: classifiedAdLinks) {
                System.out.println("Detail page: " + classifiedAdLink);
                ClassifiedAd classifiedAd = inspectClassifiedAdPage(classifiedAdLink);
                System.out.println(classifiedAd);
                classifiedAds.add(classifiedAd);
            }
        }

        return classifiedAds;
    }

    protected abstract Iterable<String> inspectHomepage();                    // bước 1
    protected abstract Iterable<String> inspectCategory(String category);       // bước 2
    protected abstract ClassifiedAd inspectClassifiedAdPage(String classifiedAd) throws Exception;     // bước 3
}
