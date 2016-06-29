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
    Document doc = Jsoup.connect(url).userAgent("Mozilla/17.0").get(); //can't add to this one
    System.out.println("processed doc");
    //System.out.println(doc.toString());
    //make Elements of (hopefully) all links on the page that is in the middle (under ul id = s-results-list-atf)
    Elements products = doc.select("ul.s-result-list.s-col-3.s-result-list-hgrid.s-height-equalized.s-grid-view.s-text-condensed a.a-link-normal.s-access-detail-page.a-text-normal");
    System.out.println(products.size());
    String trueURL = "";
    String candidateURL = "";
    ArrayList<String> allproducts = new ArrayList<String>();
    //print all links of products in Elements, add to StringBuilder allproducts
    for (Element product : products) {
      candidateURL = product.attr("abs:href");
      trueURL = Jsoup.connect(candidateURL).userAgent("Mozilla/17.0").get().select("link[rel=canonical]").attr("abs:href");
      if (!(allproducts.contains(trueURL)))
      {
        allproducts.add(trueURL);
      }
      System.out.println(trueURL);
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
      System.out.println("on page " + pagenum);
      pagenum++;
      if (pagenum > 5) break;
      //System.setProperty("http.agent", "");
      System.out.println(nextlink);
//      Document docNext = Jsoup.connect(nextlink).userAgent("Mozilla/5.0").get(); // set user agent to avoid HTTP 503 (bot?) error
      Document docNext = Jsoup.connect(nextlink).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
//      TimeUnit.MILLISECONDS.sleep(100); // delay to avoid HTTP 503 error
      // Get all products on the page
      products = docNext.select("ul.s-result-list.s-col-3.s-result-list-hgrid.s-height-equalized.s-grid-view.s-text-condensed a.a-link-normal.s-access-detail-page.a-text-normal");
      System.out.println(products.size());
      for (Element product : products) {
        candidateURL = product.attr("abs:href");
        trueURL = Jsoup.connect(candidateURL).userAgent("Mozilla/17.0").get().select("link[rel=canonical]").attr("abs:href");
        if (!(allproducts.contains(trueURL)))
        {
          allproducts.add(trueURL);
        }
        System.out.println(trueURL);
//        allproducts.add(product.attr("abs:href"));
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
  public static void main(String[] args) throws IOException  {
    System.out.println("hello");
    GetAllProductLinks listlinks = new GetAllProductLinks(args[0]);
    ArrayList<String>links = new ArrayList<String>();
    ArrayList<String>titles = new ArrayList<String>();
    links = listlinks.returnAllProductLinks();
    System.out.println(links);
    System.out.println(links.size());
    for(String link: links){
      Document docNewLink = Jsoup.connect(link).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
      if (!(titles.contains(docNewLink.select("span#productTitle").text()))){
        titles.add(docNewLink.select("span#productTitle").text());
        System.out.println(docNewLink.select("span#productTitle").text());
      }
    }
    System.out.println(titles);
    System.out.println(titles.size());
  }
}
