package ua.vasylenko.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.providers.DataPropertyProvider;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException; 

public class HelloAgent extends Agent{

	@Override
	public void setup()
	{
		System.out.println("My name is : " +getAID().getName());

	}

	void bla() throws FileNotFoundException {
	 /*
		File file = new File(getClass().getClassLoader().getResource("rdf.owl").getFile());
		FileReader fileReader = new FileReader(file); 
		
		InputStream in = new FileInputStream(getClass().getClassLoader().getResource("rdf.owl").getFile());
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, null);
		model.read(in, null);
		
		OntClass paperClass = om.getOntClass("http://www.semanticweb.org/asus/ontologies/2017/9/untitled-ontology-20#Student" );
		System.out.println(paperClass);
		
		
		Iterator<OntClass> iterator = model.listClasses();
		while(iterator.hasNext()) {
			
			System.out.println( iterator.next().getLocalName());
		}*/
		
		// Get hold of an ontology manager
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        
        File file = new File(getClass().getClassLoader().getResource("ontology/VacanciesOntology.owl").getFile());
        // Now load the local copy
        OWLOntology ontology = null;
		try {
			ontology = manager.loadOntologyFromOntologyDocument(file);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		} 
		Set<OWLClass> classes = ontology.getClassesInSignature();
		/*
		for(OWLClass owlClass : classes) {
			Set<OWLNamedIndividual> individuals =  owlClass.getIRI().getIndividualsInSignature() ;
			for(OWLNamedIndividual i : individuals) {
				System.out.println(i);
			}
		}*/
        //OWLDataFactory owlDF = manager.getOWLDataFactory();
		
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createReasoner(ontology);
		reasoner.precomputeInferences();
		 
		
		for(OWLClass owlClass : classes) {
	        NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(owlClass,
	                true);
	        
	        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();
	        System.out.println("Обьекты: ");
	        for (OWLNamedIndividual ind : individuals) {
	        	/* OWLDataProperty name = manager.getOWLDataFactory().getOWLDataProperty(IRI.create("<http://www.semanticweb.org/asus/ontologies/2017/9/untitled-ontology-17#значення"));
	        	Set<OWLLiteral> names = reasoner.getDataPropertyValues(ind, name);
	        	// String nameInd = names.iterator().next().getLiteral(); */	            
	        	PrefixManager pm = new DefaultPrefixManager(
	                    "http://www.semanticweb.org/asus/ontologies/2017/9/untitled-ontology-17#");
	            OWLDataProperty hasAge = dataFactory.getOWLDataProperty(":значення", pm);
	            
	            Stream<OWLLiteral> asser = EntitySearcher.getDataPropertyValues(ind, hasAge, ontology);
	            String nameInd = asser.iterator().next().getLiteral();
	            
	        	System.out.println(nameInd +"    " + ind);
	        	
	        }
		} 
		
		/*Set<OWLLogicalAxiom> axiomSet=ontology.getLogicalAxioms();
	    Iterator<OWLLogicalAxiom> iteratorAxiom= axiomSet.iterator();

	    while(iteratorAxiom.hasNext()) {
	        OWLAxiom tempAx= iteratorAxiom.next();
	        if(!tempAx.getIndividualsInSignature().isEmpty()){
	            System.out.println(tempAx.getIndividualsInSignature());
	            System.out.println(tempAx.getDataPropertiesInSignature());
	            System.out.println(tempAx.getObjectPropertiesInSignature());
	        }
	    }*/
	}
	static String s = "<div itemprop=\"description\">" + 
			" <p><b>Вимоги:</b></p>" + 
			" <ul>" + 
			"  <li>Знання PHP + mysql + jquery/bootstrap <br></li>" + 
			"  <li>Досвід роботи від 1 р.<br></li>" + 
			" </ul>" + 
			" <p><b>Умови роботи:</b></p>" + 
			" <ul>" + 
			"  <li>Офісна робота, м. Львів., вул. Б.Хмельницького.<br></li>" + 
			"  <li>Пн-Пт 9:00-18:00 <br></li>" + 
			" </ul>" + 
			" <p><b>Обов'язки:</b></p>" + 
			" <ul>" + 
			"  <li>Разрабка нового функціоналу</li>" + 
			"  <li>Допрацювання і уудосконалення наявних проектів (внутрішні проекти компанії)</li>" + 
			" </ul>" + 
			" <p>&nbsp;</p>" + 
			"</div>";
	
	/*
	for(int k=0; k<htmlString.children().get(i+1).children().size(); k++){
		if(htmlString.children().get(i+1).children().get(k).nodeName().equals("p")){
			htmlString.children().get(i+1).children().get(k).remove();
		}
	}
	*/
	private static void defineClassOfWords(Document htmlString, String inputWord) {
		Element vacancyContent = htmlString.getElementsByAttributeValue("itemprop", "description").first();
		
		for (int i = 0; i < vacancyContent.childNodeSize(); i++) {

			 System.out.println(vacancyContent.children().get(i).html().toLowerCase());
			/*
			if(vacancyContent.children().get(i).html().toLowerCase().indexOf(inputWord)!=-1  && vacancyContent.children().get(i).outerHtml().endsWith("</b></p>")){
				
				if(vacancyContent.children().get(i+1).nodeName().equalsIgnoreCase("ul")){
					System.out.println("найдено");
					
				}else {
					break;
				}
				
			}*/
			
		}
	}
	
	
	
	
	private void traversalIndividuals(String inputValue) {
		// Get hold of an ontology manager
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file = new File(getClass().getClassLoader().getResource("ontology/VacanciesOntology.owl").getFile());
        
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        PrefixManager prefixManager = new DefaultPrefixManager(
                "http://www.semanticweb.org/asus/ontologies/2017/9/untitled-ontology-17#");
        OWLDataProperty individualValue = dataFactory.getOWLDataProperty(":значення", prefixManager);
        
        // Now load the local copy
        OWLOntology ontology = null;
		try {
			ontology = manager.loadOntologyFromOntologyDocument(file);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}  
		
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createReasoner(ontology);
		reasoner.precomputeInferences();
		
		Set<OWLClass> classes = ontology.getClassesInSignature();
		for(OWLClass owlClass : classes) {
	        NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(owlClass,
	                true);
	        
	        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();   
	        for(OWLNamedIndividual individual : individuals) {
	            Stream<OWLLiteral> asser = EntitySearcher.getDataPropertyValues(individual, individualValue, ontology);
	            String value = asser.iterator().next().getLiteral();
	            
	            if(value.equalsIgnoreCase(inputValue)) {
	            	System.out.println("Слово найдено в классе: " + owlClass.getIRI().getShortForm());
	            	System.out.println("    обьект: " + extractShortForm(individual.getIRI()));
	            	
	            }
	            
	        	
	            
	        }
		}
			
	}
	
	private String extractShortForm(IRI iri) {
		StringBuilder iriString = new StringBuilder(iri.getIRIString());
		int startWord = iriString.lastIndexOf("#")+1;
		
		return iriString.substring(startWord);  
	}
	
	private void defineClassOfWords(Document htmlString) {
		Element vacancyContent = htmlString.getElementsByAttributeValue("itemprop", "description").first();
		
		for (int i = 0; i < vacancyContent.childNodeSize(); i++) {
			if(vacancyContent.children().get(i).outerHtml().endsWith("</b></p>")){
				
				if(vacancyContent.children().get(i+1).nodeName().equalsIgnoreCase("ul")){
					
					traversalIndividuals(
							vacancyContent.children().get(i).text().toLowerCase().replaceAll(":", "")
					);
					
				}else {
					break;
				}
				
			}
			
			
		}
	}
	
	public static void main(String... args) {
		Document vacancyContentDocument = Jsoup.parse(s);
		new HelloAgent().defineClassOfWords(vacancyContentDocument);
		/*try {
			new HelloAgent().bla();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*Runtime runtime = Runtime.instance();
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		profile.setParameter(Profile.GUI, "true");
		ContainerController containerController = runtime.createMainContainer(profile);
		AgentController agentController;
		try {
			// "ua.vasylenko.main.agent.HelloAgent"
			agentController = containerController.createNewAgent("SearcherLinksAgent1", "ua.vasylenko.main.agent.LinksSearcherAgent", null);
			agentController.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
}
