package com.codegym.crawler.impl;

import com.codegym.crawler.Crawler;
import com.codegym.crawler.utils.IHttpService;
import com.codegym.models.ClassifiedAd;
import com.codegym.models.Price;
import com.codegym.models.TypePrice;
import com.codegym.models.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BatdongsanCrawler extends Crawler {

    private final String HOME_PAGE_URL =  "https://batdongsan.com.vn";
    public static final String PATTERN_SELLING_MENU = "<li class='lv0'><a href='/nha-dat-ban' class='haslink '>Nhà đất bán</a><ul>(.*?)</ul>";
    public static final String PATTERN_RENTAL_MENU = "<li class='lv0'><a href='/nha-dat-cho-thue' class='haslink '>Nhà đất cho thuê</a><ul>(.*?)</ul>";
    public static final String PATTERN_SUB_CATEGORY_LINKS_IN_MENU = "<li class='lv1'><a href='(.*?)' class='haslink '>";
    public static final String PATTERN_CLASSIFIED_AD_LINKS_IN_SUB_CATEGORY_PAGE = "<div class='p-title'><h3><a href='(.*?)' title";
    public static final String PATTERN_CLASSIFIED_TITLE = "<h1 itemprop=\"name\">(.*?)</h1>";
    public static final String PATTERN_CLASSIFIED_PRICE = "<span class=\"gia-title mar-right-15\"><b>Giá:</b><strong>(.*?)</strong>";


    public BatdongsanCrawler(IHttpService httpService) {
        super(httpService);
    }

    private List<String> getLinksFromMenu(String content, String menuPattern) {
        // Regex
        List<String> links = new ArrayList<>();
        Pattern p = Pattern.compile(menuPattern);
        Matcher m = p.matcher(content);
        while (m.find()) {
            Pattern p2 = Pattern.compile(PATTERN_SUB_CATEGORY_LINKS_IN_MENU);
            Matcher m2 = p2.matcher(m.group(1));
            while (m2.find()) links.add( this.HOME_PAGE_URL + m2.group(1));
        }
        return links;
    }

    @Override
    protected Iterable<String> inspectHomepage() {
        List<String> res = new ArrayList<>();
        try {
            String content = this.httpService.get(this.HOME_PAGE_URL);

            List<String> sellLinks = getLinksFromMenu(content, PATTERN_SELLING_MENU);
            res.addAll(sellLinks);

            List<String> rentalLinks = getLinksFromMenu(content, PATTERN_RENTAL_MENU);
            res.addAll(rentalLinks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    protected Iterable<String> inspectCategory(String category) {
        String content = this.httpService.get(category);
        System.out.println("inspectCategory " + category);
        List<String> links = new ArrayList<>();
        Pattern p = Pattern.compile(PATTERN_CLASSIFIED_AD_LINKS_IN_SUB_CATEGORY_PAGE);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String text = m.group(1);
            links.add(this.HOME_PAGE_URL + text);
        }
        return links;
    }
    @Override
    protected ClassifiedAd inspectClassifiedAdPage(String classifiedAd) throws Exception {
        String content = this.httpService.get(classifiedAd);

        Pattern pTitle = Pattern.compile(PATTERN_CLASSIFIED_TITLE + ".*" + PATTERN_CLASSIFIED_PRICE);
        Matcher m = pTitle.matcher(content);
        if (m.find()) {
            ClassifiedAd ad = new ClassifiedAd();
            ad.setTitle(m.group(1));
            ad.setPrice(new Price(1f, TypePrice.TOTAL_PRICE, Unit.BILLION_VND));
            return ad;
        }

        throw new Exception("Cannot find detail information");
    }
}
