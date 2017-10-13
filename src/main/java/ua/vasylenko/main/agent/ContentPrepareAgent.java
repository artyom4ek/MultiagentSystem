package ua.vasylenko.main.agent;

import java.io.IOException;
import java.net.URISyntaxException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.vasylenko.main.UrlConnectable;
import ua.vasylenko.main.agent.LinksSaverAgent.MessageBehaviour;

/*
 * ����� ������ � ��������� �������� ���������� ������.
 * @Created by Ҹ�� on 11.09.2017.
 * @version 1.0
 */
public class ContentPrepareAgent extends Agent implements UrlConnectable{
	//
	@Override
	public void setup()
	{
		// ��������� ��������� � ������.
		addBehaviour(new ContentPrepareBehaviour());
	}
	
	/** ���������� ����� ��������� ������ ������ � ��������� ��������. */		
	public class ContentPrepareBehaviour extends CyclicBehaviour {
		private Document htmlVacancyDocument = null;
		
		public void action() {
			ACLMessage msg = myAgent.receive();
			if (msg != null) {
				try {
					htmlVacancyDocument = UrlConnectable.connectToUrl(msg.getContent());
					Element vacancyContentDescription = htmlVacancyDocument.getElementsByAttributeValue("itemprop", "description").first();
					if(vacancyContentDescription != null){
						
						// ��������� ���� ������� ��� �����.
						System.out.println(vacancyContentDescription);
					}
					
				} catch (IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}else {
				block();
			}
		}
		
	}
}
