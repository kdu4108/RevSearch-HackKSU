import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class GetAllCustomerReviewLink {
  private String url;
  
  public GetAllCustomerReviewLink(String urlInput) {
    url = urlInput;
  }
  
  public String returnURLforAmazonCrawler() throws IOException {
    
    Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get(); //read doc added m
    
    //gets the URL for going to allCustomerReviewspage
    Elements links = doc.select("a.a-link-emphasis.a-nowrap");    
    Element link = links.first();

    if (links.size()>0){
      url = link.attr("abs:href");
      return url;
    }
    else{
      url = "";
      return null;
    }
    
    
  }
}