document.getElementById('search-form').addEventListener("submit", function(e) {
  e.preventDefault();
  var content = document.getElementById('user-entry').value.trim();
  //console.log(content);
  //alert(content);

 // var SENDTOPHP = window.location.href;
  //var SENDTOPHP = tab.url;
  chrome.tabs.getSelected(null,function(tab) {
    var tablink = tab.url;

    SENDTOPHP = tablink + "934812739" + content;

    console.log(SENDTOPHP);

    handleText(SENDTOPHP);

    function handleText(pageurl) {
      getReviewsFromJava(pageurl, function(data) {
        //window.alert(data); //display the new results from java backend code
        //console.log(data);
        document.getElementById('log').innerHTML = data;
      });
    }

    function getReviewsFromJava(rooturl, cb) {
      if (!rooturl) throw new Error('Page data was undefined.');

      var xhr = new XMLHttpRequest();
      var data = new FormData();

      // asynchronous
      xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
          if (xhr.status === 200) {
            //console.log("working");
            var v = xhr.responseText; // response returned by php/java execution
            //console.log(v); 
            //console.log("where is v");
            return cb(v);
          }
          else {
            throw new Error('Posting to server failed!');
          }
        }
      }

      data.append("data" , rooturl);
      //console.log(data);
      xhr.open("POST", "http://localhost:8888/hackKSU/javaExecution.php", true); //https://wordquake.me/java/javaExecution.php
      xhr.send(data);
    }

  });

}, false);

// Due to Access-Control-Allow-Origin error 
// edited .../MAMP/conf/apache/httpd.conf (added line  \\\\\ Header set Access-Control-Allow-Origin "*"  \\\\\ under <directory>)
// see http://enable-cors.org/server_apache.html