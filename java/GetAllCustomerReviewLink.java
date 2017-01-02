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
//    System.out.println("preconnect");
    Document doc = Jsoup.connect(url).userAgent("Mozilla/17.0").timeout(5000).get(); //read doc added m
//    System.out.println("postconnect");
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
  public static void main(String[] args) throws IOException  
  {
    GetAllCustomerReviewLink element = new GetAllCustomerReviewLink(args[0]);
    String link = element.returnURLforAmazonCrawler();
    System.out.println(link);
  }
  
}