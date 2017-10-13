package ua.vasylenko.main.agent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import ua.vasylenko.main.UrlConnectable;

/*
 * Класс агента поиска ссылок вакансий на заданной странице. 
 * @Created by Тёма on 11.09.2017.
 * @version 1.0
 */
public class LinksSearcherAgent extends Agent implements UrlConnectable{
	// private String url = null; // "http://rabota.ua/zapros/php-developer/pg"
	
	@Override
	public void setup()
	{
		Object[] tempArguments = getArguments();
		if (tempArguments != null && tempArguments.length >=1)
		{
			String url = tempArguments[0].toString();
			addBehaviour(new SearchBehaviour(this, url));
		}

	}
	
	/** Внутренний класс поведения агента поиска ссылок вакансий. */
	private class SearchBehaviour extends OneShotBehaviour implements UrlConnectable{
		/** Ссылка на страницу списка вакансий. */
		private String inputUrl;
		
		/** Храним полностью html документ для дальнейшего его анализа. */
		private Document htmlDocument = null;
		
		private LinksSearcherAgent linksSearcherAgent;
		
		public SearchBehaviour(LinksSearcherAgent linksSearcherAgent, String inputUrl) {
			this.linksSearcherAgent = linksSearcherAgent;
			this.inputUrl = inputUrl;
		}

		public void action(){
			//System.out.println(linksSearcherAgent.getName()+": " + inputUrl);
			try {
				htmlDocument = UrlConnectable.connectToUrl(inputUrl);
				/*for(String s : getVacancyLinks()) {
					System.out.println("Агент " + linksSearcherAgent.getName() + ": " + s);
				}*/
				/*  
				// Передаем список найденных ссылок вакансий со страницы.
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.addReceiver(new AID("LinksSaverAgent", AID.ISLOCALNAME));
				 msg.setContentObject(getVacancyLinks());
				// msg.setContent("Hello from " + myAgent.getLocalName());
				send(msg);*/
				 
				// Тестим передачу и парсинг необходимого контента.
				sendUrlToAgent();
			} catch (IOException | URISyntaxException e) {
				System.out.println("Ошибка в методе action() класса LinksSearcherAgent: " + e.getMessage()); 
				e.printStackTrace();
			}
		}
		
		/**
		 * Метод извлекает ссылки с заданной страницы. 
		 * @return список ссылок.
		 */
		private ArrayList<String> getVacancyLinks() {
			ArrayList<String> vacancyLinksList = new ArrayList<String>();
			
			Element table = htmlDocument.select("table").first();
			Elements urls = table.select("tr td h3 a");
			
			for(Element element : urls) {
				String urlVacancy = "http://rabota.ua"+element.attr("href");
				vacancyLinksList.add(urlVacancy);
			}
			
			return vacancyLinksList;
		}
		
		/**
		 * Метод отправляет ACL-сообщение с найденной ссылкой другому агенту.
		 */
		private void sendUrlToAgent() {
			Element table = htmlDocument.select("table").first();
			Elements urls = table.select("tr td h3 a");
			
			for(Element element : urls) {
				String urlVacancy = "http://rabota.ua"+element.attr("href");
				
				// Передаем список найденных ссылок вакансий со страницы.
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.addReceiver(new AID("ContentPrepareAgent", AID.ISLOCALNAME));
				msg.setContent(urlVacancy); 
				send(msg);
			}
		}
		
		
    }
	
}
