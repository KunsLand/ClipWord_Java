package cn.kunsland;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ContentController {
	
	public static String getWelcome(){
		Document doc = Jsoup.parse("Welcome to ClipWord!");
		return setAttribute(doc).toString();
	}

	public static String getWaiting(){
		Document doc = Jsoup.parse("Waiting...");
		return setAttribute(doc).toString();
	}

	public static String getSorry(){
		Document doc = Jsoup.parse("Sorry, failed to get data from server.");
		return setAttribute(doc).toString();
	}
	
	public static Document setAttribute(Document doc){
		doc.getElementsByTag("body").attr("style", "font-family:Microsoft YaHei;color:white");
		doc.getElementsByTag("body").attr("bgcolor", "rgb(56,98,86)");
		return doc;
	}

}
