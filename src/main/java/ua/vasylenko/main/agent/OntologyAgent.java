package ua.vasylenko.main.agent;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.vasylenko.main.UrlConnectable;
import ua.vasylenko.main.agent.ContentPrepareAgent.ContentPrepareBehaviour; 

/*
 * Агент работы с онтологией.
 * @Created by Тёма on 12.09.2017.
 * @version 1.0
 */
public class OntologyAgent extends Agent implements UrlConnectable{

	@Override
	public void setup()
	{
		addBehaviour(new OntologyBehaviour());
	}
	
	/** Внутренний класс поведения агента работы с онтологией */		
	public class OntologyBehaviour extends CyclicBehaviour {
		private Document vacancyContentDocument = null;
		
		@Override
		public void action() { 
			ACLMessage msg = myAgent.receive();
			if (msg != null) {
				String vacancyContent = msg.getContent();
				vacancyContentDocument = Jsoup.parse(vacancyContent);
				 
			}else {
				block();
			}
			
		}
		
		
		
		private void defineClassOfWords(Document htmlString) {
			Element vacancyContent = htmlString.getElementsByAttributeValue("itemprop", "description").first();
			
			for (int i = 0; i < vacancyContent.childNodeSize(); i++) {
				if(vacancyContent.children().get(i).outerHtml().endsWith("</b></p>")){
					
					if(vacancyContent.children().get(i+1).nodeName().equalsIgnoreCase("ul")){
						
						traversalIndividuals(
								vacancyContent.children().get(i).text().toLowerCase().replaceAll(":", "")
						);
						
						/*
						for(int k=0; k<vacancyContent.children().get(i+1).children().size(); k++){
							if(vacancyContent.children().get(i+1).children().get(k).nodeName().equals("p")){
								vacancyContent.children().get(i+1).children().get(k).remove();
							}
						}
						*/
						
					}else {
						break;
					}
					
				}
				
				
			}
		}
		
	}
	
}
