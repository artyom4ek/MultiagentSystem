package ua.vasylenko.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/*
 * Интерефейс настройки подключения Jsoup. 
 * @Created by Тёма on 11.09.2017.
 * @version 1.0
 */
public interface UrlConnectable {
	
	/**
	 * Метод подключения с сайту.
	 * @param url входная ссылка на страницу сайта.
	 * @return документ для обработки.
	 * @throws MalformedURLException если адрес указан неверно.
	 * @throws IOException ошибка ввода/вывода.
	 * @throws URISyntaxException если используется неправильный URI.
	 */
	static Document connectToUrl(String url) throws MalformedURLException, IOException, URISyntaxException {
		Document htmlDocument = Jsoup.connect(new URL(url).toURI().toASCIIString())
				.userAgent("Mozilla")
				.timeout(10 * 1000).get();
		
		return htmlDocument;
	}
	
}
