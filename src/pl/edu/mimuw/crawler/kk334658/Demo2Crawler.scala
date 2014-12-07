package pl.edu.mimuw.crawler.kk334658

import org.jsoup._
import java.net.{URI,URISyntaxException}
import scala.collection.mutable._
import org.jsoup.nodes._
import org.jsoup.select._
import scala.collection.JavaConversions._

class Demo2Crawler (e:Explorer, doPrzerobienia:Queue[URI], zrobione: HashSet[String])
									extends Crawler(e, doPrzerobienia, zrobione) {
			
			var ans = 0 
			var n = 0
			var graf: HashMap[String, Int] = new HashMap[String,Int]
			
			/** 
			 *  Konstruktor naszego crawlera, przerabia strony offline
			 */
			
			def this() = this(new ExplorerOffline(), new Queue[URI],new HashSet[String])

			/** 
			 *  Funkcja inicjująca nasz crawling i wypisująca wynik
			 *  
			 * @param Ile: Int - określa na jaką maksymalnie głębokość mamy zejść
			 * @param strona: String - adres naszej pierwszej strony do przerobienia
			 */
			  def crawl(ile: Int, strona: String): Unit = {
				    n = ile
				    graf += (strona -> 0)
				    aktualny = URI.create(strona)
				    wrzuc(aktualny.toString())				   
				    ans = 0	
				    
				    przerobWszystko()
			
				    System.out.println(ans)
			  }
  
			
			override def czynPowinnosc(doc: Document): Unit = {
				ans = ans + 1
				
				var anchors: List[String] = dajLinki(doc)
 
				for(anch <- anchors) {
				  try {
				    
				   if(czyPrzerobic(anch) && !graf.contains(anch)) 
				       graf += anch -> (graf(aktualny.toString()) + 1)
				       wrzuc(anch)
				  	}
				  catch{
				    case e: Exception => System.err.println("Błąd przy wczytywaniu: "+ anch)
				  }
				}
				
			}

			override def czyPrzerobic(strona: String): Boolean = {
					(graf(aktualny.toString()) <= n-1)&& URI.create(strona).getHost() == aktualny.getHost() && isLocalFile(strona)
			}
			
			
			override def domena(s:String): String = {
			  try {
			    URI.create(s).getHost().toString()	
			  }
			  catch {
			    case e: NullPointerException => System.err.println("String podany do znalezienia domeny był nullem lub nie był adresem strony: "+" " +s)
			    									""
			    case e:Exception => System.err.println("Wyjątek przy wyznaczaniu domeny")
			    									""
			  }
			}
			
}