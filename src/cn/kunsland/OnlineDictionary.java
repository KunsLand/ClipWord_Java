package cn.kunsland;

import java.io.IOException;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OnlineDictionary {

	public static String getFromIciba(String word) {
		String url = "http://open.iciba.com/huaci/dict.php?word=" + word;
		String res_str = null;
		try {
			res_str = JSoupClient.getJson(url, null);
			// System.out.println(res_str);
			res_str = res_str.substring(res_str.indexOf("{") + 1,
					res_str.indexOf("}"));
			res_str = res_str.substring(res_str.indexOf("'") + 1,
					res_str.indexOf("';"));
			Document doc = Jsoup.parse(StringEscapeUtils
					.unescapeJava(StringEscapeUtils.unescapeHtml(res_str)));
			// System.out.println(doc);
			Element e = doc.getElementById("CIBA_JOINWORD");
			if (e != null)
				e.remove();
			Elements es = doc.getElementsByClass("icIBahyI-footer");
			if (es != null)
				es.remove();
			doc = ContentController.setAttribute(doc);
			res_str = doc.toString();
			// System.out.println(res_str);
		} catch (IOException e) {
			System.out.println(e.getClass()+":"+e.getMessage());
			res_str = ContentController.getSorry();
		}
		return res_str;
	}
}
