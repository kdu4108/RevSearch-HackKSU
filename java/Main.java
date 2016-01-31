import java.io.IOException;


import java.util.ArrayList;

//import java.util.HashMap<String, Integer>;
import java.util.*;

public class Main {
    
    public static String combineGetACRLAndAC(String urlInput)throws IOException
    {
        String allReviewsAsText = "";
        //creates String object URL that is the URL to the page with all of the reviews
        GetAllCustomerReviewLink link = new GetAllCustomerReviewLink(urlInput);
        String urlpageWithAllReviews = link.returnURLforAmazonCrawler();
       // System.out.println(urlpageWithAllReviews);
        
        //creates String object of all reviews (allReviewsAsText)
        AmazonCrawler crawler = new AmazonCrawler(urlpageWithAllReviews);
        allReviewsAsText = crawler.returnAllReviewsOneString();
        return allReviewsAsText;
    }
    public static void main(String[] args) throws IOException, InterruptedException 
    {
        //System.out.println("working");
        //runs and prints every customer review for every product on the initial URL (args[0])
        //accepts initial search URL
        GetAllProductLinks initPageLinksAL = new GetAllProductLinks(args[0]);
        //converts this URL to an ArrayList of the URLs of every product on the search pages
        ArrayList<String> allProductLinks = new ArrayList<String>();
        allProductLinks = initPageLinksAL.returnAllProductLinks();
//        System.out.println(allProductLinks.get(0));
//        System.out.println(allProductLinks.get(1));
//        System.out.println(allProductLinks.get(14));
        //Creates a new ArrayList that contains every comment for every URL in allProductLinks
        ArrayList<String> allTextReviewsAL = new ArrayList<String>();
        
        for (String link: allProductLinks)
        { 
            allTextReviewsAL.add(combineGetACRLAndAC(link));
        }
        
    //    System.out.println(allTextReviewsAL.size());

//        System.out.println(allTextReviewsAL.get(0));
//        System.out.println(allTextReviewsAL.get(1));
//        System.out.println(allTextReviewsAL.get(2));
//        System.out.println(allTextReviewsAL.get(3));
        
//        for (String asdf : allTextReviewsAL)
//        {
//            System.out.println(asdf);  
//        }
        
        
        //System.out.println("working");
        //System.out.println(args[0]);
        //System.out.println(args[1]);
        
        int numitems = allTextReviewsAL.size();
        //int[] counts = new int[numitems];
        Map<String, Integer> map = new HashMap<String, Integer>(numitems);
        for (int i = 0; i < numitems; i++)
        {
//            //System.out.println(text);
//            
            String pat = args[1]; // pattern (searching for this)
//            String txt = allTextReviewsAL.get(i);
            //System.out.println(pat);
//            BoyerMoore boyermoore = new BoyerMoore(pat);
//            
//            int count = 0;
//            int startindex = 0;
//            int offset = boyermoore.search(txt, startindex);
//            while (offset < txt.length()) {
//                startindex = offset + 1;
//                offset = boyermoore.search(txt, startindex);
//                count++;
//            }
            int count = allTextReviewsAL.get(i).split(pat, -1).length - 1;
            
            map.put(allProductLinks.get(i), count); // url, count
        }
       
        
        map = MapUtil.sortByValue(map);
        for (String key : map.keySet()) {
            //map.get(allTextReviewsAL.get(i)); //get values (should be ordered)
            System.out.println(key + ", Number of Hits: " + map.get(key));
            System.out.println();
            System.out.println();
        }

    }
}