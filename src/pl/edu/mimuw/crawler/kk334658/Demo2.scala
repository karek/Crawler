package pl.edu.mimuw.crawler.kk334658

import org.jsoup._
import java.net._
import org.jsoup.nodes._
import org.jsoup.select._
import scala.collection.JavaConversions._
import java.io.{IOException,File}
import scala.collection.mutable._

object Demo2 {

  def main(args: Array[String]): Unit = {
    
    var plik: File = new File("/home/krzysiek/Pulpit/dump/1/index.html")
  	var f = ("/home/krzysiek/Pulpit/dump/1/index.html")
  	var c = new Demo2Crawler()
  	c.crawl(1,f)
  }

}