package com.codegym.crawler.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class URLGetRequest implements IHttpService {
    @Override
    public String get(String link) {
        String content = "";
        try {
            // Gởi HTTP request và nhận về kết quả là chuỗi các thẻ HTML
            URL url = new URL(link);
            Scanner scanner = new Scanner(new InputStreamReader(url.openStream()));
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
            content = content.replaceAll("\\R", ""); // xoá các ký tự ngắt dòng (xuống dòng)
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
