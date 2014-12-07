package pl.edu.mimuw.crawler.kk334658

import org.jsoup._
import java.net.{URI,URISyntaxException}
import scala.collection.mutable._
import org.jsoup.nodes._
import org.jsoup.select._
import scala.collection.JavaConversions._

class Demo1Crawler (e:Explorer, doPrzerobienia:Queue[URI], zrobione: HashSet[String]) 
									extends Crawler(e, doPrzerobienia, zrobione) {
  
			private var mapa: Map[String, Integer] = new HashMap[String, Integer]
			
			/** 
			 *  Konstruktor naszego crawlera, przerabia strony online
			 */
			def this() = this(new ExplorerOnline(), new Queue[URI],new HashSet[String])
			
			/** 
			 *  Funkcja inicjująca nasz crawling i wypisująca wynik
			 *  
			 * @param Ile: Int - określa na jaką maksymalnie głębokość mamy zejść
			 * @param strona: String - adres naszej pierwszej strony do przerobienia
			 */
							   
				    
			override def crawl(strona: String): Unit = {
			       aktualny = URI.create(strona)
				    
				    wrzuc(strona)
				    przerobWszystko()
				    
				    var l = mapa.toList
				    def comp(a: (String,Integer), b: (String,Integer)):Boolean = 
				      			if(a._2 != b._2) a._2 > b._2
				      			else a._1 < b._1
				    l = l.sortWith(comp)
				    for(it <- l) println(it._1 +" " + it._2)
			 }
			
			
			/**
			 * Wrzuca dany link do mapy trzymającej ilości wystąpień danych wierzchołków
			 * @param s:String - link do wrzucenia
			 */
			
			def zmapuj(s:String): Unit = {
			  		
			  		try {
			  		  val dom:String  = domena(s)
			  		
			  		
			  		if(mapa.containsKey(dom)) mapa += (dom -> (mapa(dom)+1))
			  		else mapa += (dom-> 1)
			  		}
			  		catch{
			  		  case e:NullPointerException => throw e
			  		}
			}
			/**
			 *  Wykonuje główne zadanie postawione 
			 * przed danym rodzajem crawlera dla pojedynczej strony
			 * 
			 * @param doc- dokument aktualnie do przetworzenia
			 */
			
			override def czynPowinnosc(doc: Document):Unit = {
			  val l = dajLinki(doc)
			  
			  var x: (List[String], List[String]) = (l.filter(x => czyPrzerobic(x)), l.filter(x => ! czyPrzerobic(x)))
			  
			  for(cos <- x._1)  wrzuc(cos)
			    
			  for(cos <- x._2) zmapuj(cos)			  
			}
			
			/**
			 * Funkcja sprawdzająca, czy chcemy daną stronę przerobić. 
			 * 
			 * @param strona - adres strony do sprawdzenia
			 * @return true, jeśli chcemy, wpp. false
			 */
			override def czyPrzerobic(strona: String): Boolean = {
			   
			   try{
			     val s =  URI.create(strona)
			     s.isAbsolute() && s.getHost().equals(aktualny.getHost())
			   }
			   catch{
			     case e: NullPointerException => System.err.println("Błąd przy sprawdzaniu, czy przerobić stronę (Strona: " + strona +" lub aktualny: " +aktualny)  
			    		 								false
			     case e: Exception => System.err.println("Błąd przy sprawdzaniu") 
			     										false
			   }

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