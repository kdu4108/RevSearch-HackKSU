import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;



public class GetAllProductLinks {
  private String url = "";
  
  public GetAllProductLinks(String urlInput) {
    url = urlInput;
  }
  public ArrayList<String> returnAllProductLinks() throws IOException{
    Document doc = Jsoup.connect(url).get(); //can't add to this one
    //System.out.println(doc.toString());
    //make Elements of (hopefully) all links on the page that is in the middle (under ul id = s-results-list-atf)
    Elements products = doc.select("ul#s-results-list-atf a.a-link-normal.s-access-detail-page.a-text-normal");
   // System.out.println(products.size());
    
    ArrayList<String> allproducts = new ArrayList<String>();
    //print all links of products in Elements, add to StringBuilder allproducts
    for (Element product : products) {
      allproducts.add(product.attr("abs:href"));
//      System.out.println(product.attr("abs:href"));
    }
    //create Elements list of next links (id=pagnNextLink)
    Elements nextlink_elements = doc.select("#pagnNextLink"); 
    //if more than one next button
    if (nextlink_elements.size() > 1) throw new RuntimeException("Uh oh there is more than one next button");
    //get nextlink URL as string
    String nextlink = nextlink_elements.attr("abs:href");
    int pagenum = 1;
    while (nextlink_elements.size() != 0) {
      //TimeUnit.MILLISECONDS.sleep(100); // delay to avoid HTTP 503 error
      //System.out.println("on page " + pagenum);
      pagenum++;
      if (pagenum > 1) break;
      //System.setProperty("http.agent", "");
      System.out.println(nextlink);
//      Document docNext = Jsoup.connect(nextlink).userAgent("Mozilla/5.0").get(); // set user agent to avoid HTTP 503 (bot?) error
      Document docNext = Jsoup.connect(nextlink).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
//      TimeUnit.MILLISECONDS.sleep(100); // delay to avoid HTTP 503 error
      // Get all products on the page
      products = docNext.select("ul#s-results-list-atf a.a-link-normal.s-access-detail-page.a-text-normal");
      System.out.println(products.size());
      for (Element product : products) {
        allproducts.add(product.attr("abs:href"));
//        System.out.println(product.attr("abs:href"));
      }
      
      // Get link to next page
      nextlink_elements = docNext.select("#pagnNextLink");
      if (nextlink_elements.size() > 1) throw new RuntimeException("Uh oh there is more than one next button");
      nextlink = nextlink_elements.attr("abs:href");
    }
//    for (String s: allproducts){
//     // System.out.println(s);
//        //stuff here
//        
//    }
    return allproducts;
  // System.out.println(allproducts.size());
  }
}