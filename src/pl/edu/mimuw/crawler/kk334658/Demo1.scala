package pl.edu.mimuw.crawler.kk334658
import java.net._
object Demo1 {

  def main(args: Array[String]): Unit = {
    var c: Crawler = new Demo1Crawler()
    c.crawl("http://lo28.internetdsl.pl/")
  }

}