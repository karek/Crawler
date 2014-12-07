package pl.edu.mimuw.crawler.kk334658
import java.net.URI
import org.jsoup._
import org.jsoup.nodes.Document
import java.io.{IOException,File}

class ExplorerOffline extends Explorer {

	def wczytaj(strona:URI): Document = {
			try {
				var plik: File = new File(strona.toString())
				Jsoup.parse(plik, null , plik.getParentFile().getAbsolutePath())
			}
			catch {
			case e: IOException =>System.err.println("Błędny adres: " + strona) 
					null
			case e: Exception => System.err.println("Błąd przy wczytywaniu")
					null
			}
	}

}