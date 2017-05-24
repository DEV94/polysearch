package com.polytech.models;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FunnyCrawler {

    private static Pattern patternDomainName;
    private Matcher matcher;
    private static final String DOMAIN_NAME_PATTERN
            = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
    static {
        patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
    }

    public static void main(String[] args) {

        FunnyCrawler obj = new FunnyCrawler();
        Set<Result> result = obj.getDataFromGoogle("mario");
        for(Result temp : result){
            System.out.println(temp.getTitle() + " | " + temp.getUri());
            //System.out.println(temp.getUri());
        }
        System.out.println(result.size());
    }

    public String getDomainName(String url){

        String domainName = "";
        matcher = patternDomainName.matcher(url);
        if (matcher.find()) {
            domainName = matcher.group(0).toLowerCase().trim();
        }
        return domainName;

    }

    public Set<Result> getDataFromGoogle(String query) {

        Set<Result> result = new HashSet<Result>();
        String request = "https://www.google.com/search?q=" + query + "&num=70";
        System.out.println("Sending request..." + request);

        try {

            // need http protocol, set this as a Google bot agent :)
            Document doc = Jsoup
                    .connect(request)
                    .userAgent(
                            "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
                    .timeout(5000).get();

            // get all links
            Elements links = doc.select("a[href]");
            int id = 0;
            for (Element link : links) {
                String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
                //url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");
                //link.setBaseUri(url);
                /*if (!url.startsWith("http")) {
                    continue; // Ads/news/etc.
                }*/

                //System.out.println("Text : " + link.text());
                //System.out.println("URL : " + url);


                String temp = link.attr("href");
                if(temp.startsWith("/url?q=")){
                    //use regex to get domain name
                    //System.out.println(link.text());
                    //System.out.println(getDomainName(temp));
                    //Result result1 = new Result(link.text(), getDomainName(temp));
                    if(!link.text().isEmpty()) {
                        if(!link.text().startsWith("En cache")) {
                            String url2 = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
                            url2 = URLDecoder.decode(url2.substring(url2.indexOf('=') + 1, url2.indexOf('&')), "UTF-8");
                            link.setBaseUri(url2);
                            id++;
                            Result res = new Result(id, link.text(), url2);
                            result.add(res);
                        }
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}