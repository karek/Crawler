package pl.edu.mimuw.crawler.kk334658
import java.net.URI
import org.jsoup.nodes.Document
import org.jsoup._

class ExplorerOnline extends Explorer {
  
	def wczytaj(strona:URI): Document = { 
	  var doc: Document = null
	  
	   try {
	     doc = Jsoup.connect(strona.toString()).get()    
	   }
	   catch {
	     
	     case e: java.net.SocketTimeoutException => System.err.println("Za wolny net")
	     case e: Exception => System.err.println("Wyjątek przy wczytywaniu: "+ strona) 
	   } 
	   doc
	}
}