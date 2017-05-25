package com.polytech.models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {

    private static Pattern patternDomainName;
    private Matcher matcher;
    private static final String DOMAIN_NAME_PATTERN = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
    static {
        patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
    }

    public String getDomainName(String url){

        String domainName = "";
        matcher = patternDomainName.matcher(url);
        if (matcher.find()) {
            domainName = matcher.group(0).toLowerCase().trim();
        }
        return domainName;

    }

    public List<Result> getDataFromGoogle(String query) {

        List<Result> results = new ArrayList<>();
        String request = "https://www.google.com/search?q=" + query + "&num=70";
        System.out.println("Sending request..." + request);

        try {

            // need http protocol, set this as a Google bot agent :)
            Document doc = Jsoup
                    .connect(request)
                    .userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
                    .timeout(5000).get();

            // get all links
            Elements links = doc.select("a[href]");
            int id = 0;
            for (Element link : links) {
                String temp = link.attr("href");
                if(temp.startsWith("/url?q=")){
                    if(!link.text().isEmpty()) {
                        if(!link.text().startsWith("En cache")) {
                            String url2 = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
                            url2 = URLDecoder.decode(url2.substring(url2.indexOf('=') + 1, url2.indexOf('&')), "UTF-8");
                            link.setBaseUri(url2);
                            id++;
                            Result res = new Result(id+"", link.text(), url2);
                            results.add(res);
                        }
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

}