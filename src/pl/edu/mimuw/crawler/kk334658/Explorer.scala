package pl.edu.mimuw.crawler.kk334658
import java.net.URI
import org.jsoup.nodes.Document

trait Explorer {

  /**
   * Funkcja do tworzenia dokumentu Jsoup posiadająć tylko URI (ściąga stronę)
   * 
   * @param strona - URI naszej strony, którą chcemy wczytać
   * @return Dokument Jsoup, ściągniętej strony
   */
	def wczytaj(strona:URI): Document
}