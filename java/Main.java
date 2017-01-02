import java.io.IOException;


import java.util.ArrayList;
import java.util.concurrent.*;
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
  public static ArrayList<String> processInputs(ArrayList<String> inputs)
    throws InterruptedException, ExecutionException {
    
    int threads = 100;//Runtime.getRuntime().availableProcessors();
    System.out.println("# of threads: " + threads);
    ExecutorService service = Executors.newFixedThreadPool(threads);
    
    ArrayList<Future<String>> futures = new ArrayList<Future<String>>();
    for (final String input : inputs) {
      Callable<String> callable = new Callable<String>() {
        public String call() throws Exception {
          String output = "";
          // process your input here and compute the output
          output = combineGetACRLAndAC(input);
          output = output + "\n\n";
          return output;
        }
      };
      futures.add(service.submit(callable));
    }
    
    service.shutdown();
    
    ArrayList<String> outputs = new ArrayList<String>();
    for (Future<String> future : futures) {
      outputs.add(future.get());
    }
    return outputs;
  }
  public static void main(String[] args) throws IOException, InterruptedException, ExecutionException 
  {
    //System.out.println("working");
    //runs and prints every customer review for every product on the initial URL (args[0])
    //accepts initial search URL
    long benchmarkTime = System.nanoTime();
    System.out.println("initiated: time elasped = " + 0);
    
    GetAllProductLinks initPageLinksAL = new GetAllProductLinks(args[0]);
    //converts this URL to an ArrayList of the URLs of every product on the search pages
    ArrayList<String> allProductLinks = new ArrayList<String>();
    allProductLinks = initPageLinksAL.returnAllProductLinks();
//        System.out.println(allProductLinks.get(0));
//        System.out.println(allProductLinks.get(1));
//        System.out.println(allProductLinks.get(14));
    //Creates a new ArrayList that contains every comment for every URL in allProductLinks
    
    System.out.println("Created ArrayList of all product links (size = " + allProductLinks.size() + ") : time elapsed = " + ((System.nanoTime()-benchmarkTime)/1000000000));
    
    ArrayList<String> allTextReviewsAL = new ArrayList<String>();
    allTextReviewsAL = processInputs(allProductLinks);
//    
//    for (String link: allProductLinks)
//    { 
//      allTextReviewsAL.add(combineGetACRLAndAC(link));
//    }
    
    System.out.println("created ArrayList of all reviews for each product: time elapsed = " + ((System.nanoTime()-benchmarkTime)/1000000000));
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
    System.out.println("Total time elapsed = " + ((System.nanoTime()-benchmarkTime)/1000000000));
    
  }
}