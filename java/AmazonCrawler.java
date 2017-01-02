import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.StringBuilder;
//import java.util.concurrent.TimeUnit;

public class AmazonCrawler{
    private String url;
    private StringBuilder allreviews;
    
    public AmazonCrawler(String url1) {
        url = url1;
    }
    
    public String returnAllReviewsOneString() throws IOException{
        if (url == null || url.isEmpty()) {
            //System.out.println("idiotic");
            return "";
        }
        else{
            Document doc_root = Jsoup.connect(url).userAgent("Mozilla/17.0").timeout(5000).get(); //.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
            //System.out.println(url);
            // Get all reviews on the page
            Elements reviews_elements = doc_root.select("span.a-size-base.review-text");
            //System.out.println(reviews_elements);
            allreviews = new StringBuilder(); // will contain all reviews for the item
            for (Element review : reviews_elements) {
               // System.out.println(review.text());
                allreviews.append(review.text());
                allreviews.append("\n");
                //System.out.println(allreviews.toString());
            }
            //System.out.println("alskdfjs");
            //System.out.println(allreviews.toString());
            
            // Get link to next page
            Elements nextlink_elements = doc_root.select("link[rel=\"next\"]");
            if (nextlink_elements.size() > 1) throw new RuntimeException("Uh oh there is more than one next button");
            String nextlink = nextlink_elements.attr("abs:href");
            
            // BEGIN CRAWL
            // Go to next page (if there is a next page)
            int pagenum = 1;
            while (nextlink_elements.size() != 0) {
                //TimeUnit.MILLISECONDS.sleep(100); // delay to avoid HTTP 503 error
                //System.out.println("on page " + pagenum);
                pagenum++;
                if (pagenum > 3) break;
                //System.setProperty("http.agent", "");
                Document doc = Jsoup.connect(nextlink).userAgent("Mozilla/17.0").timeout(5000).get(); // set user agent to avoid HTTP 503 (bot?) error
                
                // Get all reviews on the page
                reviews_elements = doc.select("span.a-size-base.review-text");
                for (Element review : reviews_elements) {
                    //System.out.println(review.text());
                    allreviews.append(review.text());
                    allreviews.append("\n");
                }
                
                // Get link to next page
                nextlink_elements = doc.select("link[rel=\"next\"]");
                if (nextlink_elements.size() > 1) throw new RuntimeException("Uh oh there is more than one next button");
                nextlink = nextlink_elements.attr("abs:href");
            } 
            return allreviews.toString();
        }
    }
    
    public static void main(String[] args) throws IOException  {
        System.out.println("hello");
        AmazonCrawler ac = new AmazonCrawler("https://www.amazon.com/Casio-MQ24-1E-Black-Resin-Watch/product-reviews/B000GAWSHM/ref=cm_cr_dp_see_all_summary?ie=UTF8&showViewpoints=1&sortBy=helpful");
        System.out.println(ac.returnAllReviewsOneString());
    }
}