package com.polytech.web;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.polytech.business.CommunauteService;
import com.polytech.business.RechercheService;
import com.polytech.business.SignInService;
import com.polytech.models.*;
import com.polytech.repository.RequetMongoRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by dev on 3/15/17.
 */
@Controller
public class ApplicationController {

    @Autowired
    private SignInService signInService;

    @Autowired
    private CommunauteService communauteService;

    @Autowired
    private RechercheService rechercheService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() throws UnsupportedEncodingException {



        /*try {
            HttpResponse<JsonNode> response = Unirest.get("https://faroo-faroo-web-search.p.mashape.com/api?q=google&start=11")
                .header("X-Mashape-Key", "EjT6SCFnnZmshYDFS0vCgw9gKCGMp1qccC2jsnJP9c4hUzjpnv")
                .header("Accept", "application/json")
                .asJson();*/

            /*HttpResponse<JsonNode> response = Unirest.get("https://api.duckduckgo.com/?q=Test&format=json")
                    .header("X-Mashape-Key", "EjT6SCFnnZmshYDFS0vCgw9gKCGMp1qccC2jsnJP9c4hUzjpnv")
                    .header("Accept", "application/json")
                    .asJson();*/

            /*JSONObject myObj = response.getBody().getObject();
            System.out.println(myObj);
            JSONArray arr = myObj.getJSONArray("results");
            System.out.println(arr.length());
            for(int i=0; i<arr.length(); i++){
                JSONObject o = arr.getJSONObject(i);
                System.out.println(o.get("title"));
                //System.out.println(o.get("title"));
            }*/

            // extract fields from the object
            //String msg = myObj.getString("error_message");
            //JSONArray results = myObj.getJSONArray(msg);


        /*}catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("IN");*/
        /*final String accountKey = "9fe8b6df90574d3a90b1b83d824c556c";
        final String bingUrlPattern = "https://api.cognitive.microsoft.com/bing/v5.0/search?Query=%%27%s%%27&$format=JSON";


        try {
            final String query = URLEncoder.encode("'what      is omonoia'", Charset.defaultCharset().name());
            final String bingUrl = String.format(bingUrlPattern, query);

            final String accountKeyEnc = Base64.getEncoder().encodeToString((accountKey + ":" + accountKey).getBytes());

            final URL url = new URL(bingUrl);
            final URLConnection connection = url.openConnection();
            connection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
            System.out.println("IN2");
            try (final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                System.out.println("IN3 : " + in);
                final StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    System.out.println(response);
                }
                final JSONObject json = new JSONObject(response.toString());
                final JSONObject d = json.getJSONObject("d");
                final JSONArray results = d.getJSONArray("results");
                final int resultsLength = results.length();
                for (int i = 0; i < resultsLength; i++) {
                    final JSONObject aResult = results.getJSONObject(i);
                    System.out.println(aResult.get("Url"));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }*/

        //String accountKey = "773f65ede24a47268e23bb8243914b90";
        //byte[] accountKeyBytes = Base64.encodeBase64(accountKey.getBytes());
        //String accountKeyEnc = new String(accountKeyBytes);

        /*String accountKey = "b050424695f744cba2f5561284ae41b0";
        String accountKeyEnc = new sun.misc.BASE64Encoder().encode (accountKey.getBytes());

        String query="eagle";

        String bingURL = "https://api.cognitive.microsoft.com/bing/v5.0/search?q=sailing+lessons+seattle&mkt=en-us"+URLEncoder.encode(query, "UTF-8")+"%27&$format=json";

        JSONObject result = null;

        try {
            URL url = new URL(bingURL);
            //url.append(URLEncoder.encode(query, "UTF-8"));
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuilder sb = new StringBuilder();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            result = new JSONObject(sb.toString());
            System.out.println(result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        return "index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(){
        return "signup";
    }

    @RequestMapping(value = "/dosignup", method = RequestMethod.POST)
    public String dosignup(String username, String password, Principal principal){
        signInService.signIn(new User(username, new BCryptPasswordEncoder().encode(password), 1));
        return "login";
    }

    @RequestMapping(value = "/creer", method = RequestMethod.GET)
    public String creer(){
        return "creer";
    }

    @RequestMapping(value = "/creerCommunaute", method = RequestMethod.POST)
    public String creerCommunaute(Communaute communaute, Principal principal){
        communaute.setResponsableID(principal.getName());
        System.out.println(communaute.getNom());
        System.out.println(communaute.getResponsable());
        System.out.println(communaute.getDescription());
        communauteService.save(communaute);
        return "creer";
    }

    @RequestMapping(value = "/gerer", method = RequestMethod.GET)
    public String gerer(Model model){
        List<Communaute> communautes = communauteService.selectAll();
        model.addAttribute("resultats", communautes);
        return "gerer";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(Requete requete, Principal principal, Model model){
        //Elements resultats = new Elements();

        /*String google = "http://www.google.com/search?q=";
        String search = "stackoverflow";
        String charset = "UTF-8";
        String userAgent = "ExampleBot 1.0 (+http://example.com/bot)"; // Change this to your company's name and bot homepage!

        //search = requete.getQuery();

        System.out.println("# " + requete.getUsername() + " : " + requete.getQuery());

        try {
            Elements resultats = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select(".g>.r>a");
            //Elements resultats2 = Jsoup.connect(google + URLEncoder.encode(requete.getQuery()+"&start=10", charset)).userAgent(userAgent).get().select(".g>.r>a");
            System.out.println(resultats.size());
            for (Element link : resultats) {
                String title = link.text();
                String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
                url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");
                link.setBaseUri(url);
                if (!url.startsWith("http")) {
                    continue; // Ads/news/etc.
                }

                System.out.println("Title: " + title);
                System.out.println("URL: " + url);
            }


            /*for (Element link2 : resultats2) {
                String title2 = link2.text();
                String url2 = link2.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
                url2 = URLDecoder.decode(url2.substring(url2.indexOf('=') + 1, url2.indexOf('&')), "UTF-8");
                link2.setBaseUri(url2);
                if (!url2.startsWith("http")) {
                    continue; // Ads/news/etc.
                }
            }
            System.out.println(resultats.size());
            System.out.println(resultats2.size());
            //resultats.addAll(resultats);
            resultats.addAll(resultats2);*/
            requete.setUsername(principal.getName());
            rechercheService.save(requete);
            FunnyCrawler obj = new FunnyCrawler();
            Set<Result> result = obj.getDataFromGoogle(requete.getQuery());
            for(Result temp : result){
                System.out.println(temp.getTitle() + " | " + temp.getUri());
            //System.out.println(temp.getUri());
            }
            System.out.println(result.size());
            model.addAttribute("resultats", result);
        /*}catch(Exception e){
            e.printStackTrace();
        }*/
        return "index";
    }


    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    public String rate(Requete requete, Principal principal, Model model){



        return "index";
    }


}
