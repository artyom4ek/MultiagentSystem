package ua.vasylenko.main.agent;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import ua.vasylenko.main.UrlConnectable;
import ua.vasylenko.main.agent.ContentPrepareAgent.ContentPrepareBehaviour;
import ua.vasylenko.main.agent.ontology.VacanciesOntology;

/*
 * Агент работы с онтологией.
 * @Created by Тёма on 12.09.2017.
 * @version 1.0
 */
public class OntologyAgent extends Agent implements UrlConnectable{
	//TODO: Допедалить реализацию класса работы с онтологиями.
	
	// This agent "speaks" the SL language.
	private Codec codec = new SLCodec();
		
	// This agent "knows" the VacanciesOntology.
	private Ontology ontology;
	 
	@Override
	public void setup()
	{
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);
	}
	
}
