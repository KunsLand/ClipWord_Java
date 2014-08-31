package cn.kunsland;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;


public class JSoupClient {
	private final static int TIMEOUT_SECONDS = 60;

	public static Response GetResponse(String url, Map<String, String> cookies)
			throws IOException {
		return Jsoup.connect(url).timeout(TIMEOUT_SECONDS * 1000)
				.followRedirects(true).cookies(cookies).method(Method.GET)
				.execute();
	}

	public static Response GetResponse(String url) throws IOException {
		return Jsoup.connect(url).timeout(TIMEOUT_SECONDS * 1000)
				.followRedirects(true).method(Method.GET).execute();
	}

	public static Response GetPostResponse(String url, String referrer,
			Map<String, String> data) throws IOException {
		return Jsoup.connect(url).timeout(60 * 1000).referrer(referrer)
				.data(data).userAgent("Mozilla").followRedirects(true)
				.method(Method.POST).execute();
	}

	public static Response GetPostResponse(String url, String referrer,
			Map<String, String> data, Map<String, String> cookies)
			throws IOException {
		return Jsoup.connect(url).timeout(60 * 1000).referrer(referrer)
				.data(data).cookies(cookies).userAgent("Mozilla")
				.followRedirects(true).method(Method.POST).execute();

	}

	public static Document getHtml(String url) throws IOException {
		return Jsoup.connect(url).timeout(TIMEOUT_SECONDS * 1000)
				.followRedirects(true).get();
	}

	public static Document getHtmlPage(String url, Map<String, String> cookies)
			throws IOException {
		return Jsoup.connect(url).timeout(TIMEOUT_SECONDS * 1000)
				.followRedirects(true).cookies(cookies).get();
	}

	public static String getJson(String ajaxurl, String charset) throws IOException {
		return getJson(ajaxurl, null, null, null, charset);
	}

	public static String getJson(String ajaxurl, String cookies, String charset)
			throws IOException {
		return getJson(ajaxurl, null, null, cookies, charset);
	}

	public static String getJson(String ajaxurl, String referer, String xRequestWith,
			String cookies, String charset) throws IOException {
		URL url = new URL(ajaxurl);
		URLConnection connection = url.openConnection();
		connection.addRequestProperty("User-Agent", "Mozilla");
		if (referer != null)
			connection.addRequestProperty("Referer", referer);
		if (xRequestWith != null)
			connection.addRequestProperty("X-Requested-With", xRequestWith);
		if (cookies != null)
			connection.addRequestProperty("Cookie", cookies);
		if (charset == null)
			charset = "utf-8";
		connection.setConnectTimeout(10 * 1000);
		connection.setReadTimeout(TIMEOUT_SECONDS * 1000);
		connection.connect();
		BufferedReader rd = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), Charset.forName(charset)));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		return sb.toString();
	}

}
