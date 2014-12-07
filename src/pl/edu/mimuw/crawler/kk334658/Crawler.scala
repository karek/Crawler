package pl.edu.mimuw.crawler.kk334658
import org.jsoup._
import java.net.{URI,URISyntaxException,URL}
import scala.collection.mutable._
import org.jsoup.nodes._
import org.jsoup.select._
import scala.collection.JavaConversions._
import java.io.{IOException,File}


abstract class Crawler (var e:Explorer,var doPrzerobienia:Queue[URI],var zrobione: HashSet[String]){

			 var aktualny: URI = null
			 
			/** 
			 *  Konstruktor naszego crawlera
			 *  
			 * @param e: Explorer - określa sposób w jaki będziemy przerabiali strony(offline/online) 
			 */
			def this(e:Explorer) = this(e, new Queue[URI],new HashSet[String])


			/**
			 * Sprawdza czy nasza kolejka do przerobienia jest pusta
			 * 
			 * @return Jeśli jest pusta => true, wpp. false
			 */new Document("")
			
			def czyPusta(): Boolean = doPrzerobienia.isEmpty

			/**
			 *  Wyjmuje z naszej kolejki pierwszy element i przerabia go w odpowiedni sposób,
			 * zależny od rodzaju Crawlera 
			 * 
			 */
			
			def przerob(): Unit = {
					try {
					  
								aktualny = doPrzerobienia.dequeue
								val d = e.wczytaj(aktualny)
								czynPowinnosc(d)
								zrobione += aktualny.toString()
					}
					catch {
						case e:NoSuchElementException => System.err.println("Pusta kolejka")
						case e:Exception =>  System.err.println("Błąd przy przerabianu strony: " +aktualny)
					}
			}

			/**
			 * Funkcja przetwarzająca całą stronę w specyficzny dla danego crawlera sposób
			 * 
			 */

			def przerobWszystko(): Unit = 
					while(!czyPusta()) przerob()

			/**
			 * Funkcja czynPowinnosc do zaimplementowania w podklasach crawlera, wykonuje główne zadanie postawione 
			 * przed danym rodzajem crawlera dla pojedynczej strony
			 * 
			 * @param doc- dokument aktualnie do przetworzenia
			 */

			def czynPowinnosc(doc: Document): Unit

			/**
			 * Funkcja zaczynająca crawling po naszej stronie w zadany sposób
			 * 
			 * @param strona - adres naszej strony, z której zaczynamy 
			 */

			def crawl(strona: String): Unit = {
							wrzuc(strona) 
							przerobWszystko()
					}

			/**
			 * Funkcja sprawdzająca, czy chcemy daną stronę przerobić. 
			 * Należy ją doimplementować w podklasach crawlera
			 * 
			 * @param strona - adres strony do sprawdzenia
			 * @return true, jeśli chcemy, wpp. false
			 */
			
			def czyPrzerobic(strona: String): Boolean = true
			
		

			/**
			 * Funkcja wrzucająca  daną stronę do naszej kolejki, jeśli spełnia nasze wymagania
			 * 
			 * @param strona - adres strony do wrzucenia
			 */

			def wrzuc(strona: String): Unit = { 

						try {
						  
							if(czyPrzerobic(strona) && strona != null && !zrobione.contains(strona)) {
									
							  doPrzerobienia += URI.create(strona)
							  zrobione += strona
								}
						}
							catch {
								case e: Exception => System.err.println("Błąd przy wrzucaniu strony do kolejki")
							}
					}


		
			
	/**
	 * Zwraca listę odnośników w dokumencie.
	 * 
	 * @param doc dokument, z którego będą pobierane odnośniki
	 * @return lista linków do stron
	 */
	def dajLinki(doc: Document): List[String] = {
	 try {
	  var anchors: Elements = doc.select("a")
		var l: List[String] = List() 
		for(anch <- anchors){
		  if(anch.attr("abs:href") == "")  l = (new File(doc.baseUri()+"/"+anch.attr("href")).getCanonicalFile().getAbsolutePath())::l
		  else
		   l = anch.attr("abs:href")::l
		}
	  
			l
	  }
	catch {
	  case e: java.lang.NullPointerException => System.err.println("Pusty dokument")
			  								List() 
	  }
	}
	
	/**
	 * Obcina link tylko do jego domeny
	 * 
	 * @param s: String - string z nazwą strony
	 * @return Nazwa strony obcięta do domeny
	 */
	def domena(s:String): String
	
	/**
	 * Sprawdza, czy link dana strona jest stroną lokalną
	 * @param s:String - strona do sprawdzenia
	 * 
	 * @return True, jeśli jest lokalna, false wpp.
	 */
	def isLocalFile (s: String): Boolean = {
	  try{
	    if(s.indexOf("file:/")==0) true
	    new URL(s)
	    false
	  }
	  catch {
	    case e: Exception => true
	  }
	}

}
