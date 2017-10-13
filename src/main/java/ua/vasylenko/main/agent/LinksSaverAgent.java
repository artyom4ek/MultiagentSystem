package ua.vasylenko.main.agent;

import java.util.ArrayList;
import java.util.List;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.StaleProxyException;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import ua.vasylenko.main.agent.ontology.VacanciesOntology;

/*
 * Агент работы с сохранением полученных ссылок или резалтов.
 * @Created by Тёма on 11.09.2017.
 * @version 1.0
 */
public class LinksSaverAgent extends Agent {
	/** Ссылки на компоненты главной формы. */
	private TextArea textAreaTemp;
	private ProgressBar progressBarTemp;
	
	@Override
	public void setup()
	{
		// Получаем аргументы, которым соответствуют компоненты главной формы.
		textAreaTemp = (TextArea)getArguments()[0];
		progressBarTemp = (ProgressBar)getArguments()[1];

		// Добавляем поведение к агенту.
		addBehaviour(new MessageBehaviour());
	}
	
	/** Внутренний класс поведения агента работы с сохранением ссылок или резалтов. */
	public class MessageBehaviour extends CyclicBehaviour {
		public void action() {
			ACLMessage msg = myAgent.receive();
			if (msg != null) {
				
				// Посколько работаем не с GUI потока, необходима такая конструкция для доступа к GUI элементам
				// основного потока.
				Platform.runLater(() -> {
					progressBarTemp.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
				});
				
				try { 
					ArrayList<String> arrayListTemp = (ArrayList<String>)msg.getContentObject();
					for(String s : arrayListTemp) {
						Platform.runLater(() -> {
							textAreaTemp.setText(
									textAreaTemp.getText()+msg.getSender().getLocalName()+": " + s + "\n"
								);
							textAreaTemp.selectPositionCaret(textAreaTemp.getLength()); 
							textAreaTemp.deselect();
						});
						// Усыпим тред для отображения работы прогресса.
						Thread.sleep(200);
					} 
					
					
				} catch (UnreadableException | InterruptedException e) {
					e.printStackTrace();
				} 
				
				Platform.runLater(() -> {
					progressBarTemp.setProgress(0);
				});
			}else {
				block();
			}
		}
	}
	
}
