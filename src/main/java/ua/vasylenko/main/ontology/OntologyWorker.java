package ua.vasylenko.main.ontology;

import java.io.File;
import java.util.Set;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
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

import ua.vasylenko.main.HelloAgent;

public class OntologyWorker implements OntologySetuble{

	@Override
	public void defineClassOfWords(Document htmlString) throws OWLOntologyCreationException {
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
	
	 
	private void traversalIndividuals(String inputValue) throws OWLOntologyCreationException {
		boolean stopSearch = false;
		
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file = new File(getClass().getClassLoader().getResource("ontology/"+ONTOLOGY_NAME).getFile());
        
        OWLDataFactory dataFactory = manager.getOWLDataFactory();
        PrefixManager prefixManager = new DefaultPrefixManager(ONTOLOGY_IRI);
        OWLDataProperty individualValue = dataFactory.getOWLDataProperty(":значення", prefixManager);
        
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
		
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createReasoner(ontology);
		reasoner.precomputeInferences();
		
		Set<OWLClass> classes = ontology.getClassesInSignature();
		for(OWLClass owlClass : classes) {
			if(!extractShortForm(owlClass.getIRI()).toLowerCase().equalsIgnoreCase("Unknown")) {
				if(!stopSearch ) {
					System.out.println("Class: "+ extractShortForm(owlClass.getIRI()));
			        NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(owlClass, true);
			        
			        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();   
			        for(OWLNamedIndividual individual : individuals) {
			            Stream<OWLLiteral> asser = EntitySearcher.getDataPropertyValues(individual, individualValue, ontology);
			            // свойства обьекта: значення
			            String value = asser.iterator().next().getLiteral();
			            // System.out.println(value + " : " + inputValue);
			            if(value.equalsIgnoreCase(inputValue)) {
			            	stopSearch = true;
			            	// System.out.println("Слово найдено в классе: " + owlClass.getIRI().getShortForm());
			            	System.out.println("    обьект: " + extractShortForm(individual.getIRI()));
			            	break;
			            } 
			            
			        }
				}else {
					break;
				}
				}//-----
			}
		
			if(!stopSearch) {
				System.out.println(inputValue + "  : слово не найдено!!!!!!!");
			}
		 
	}

	private String extractShortForm(IRI iri) {
		StringBuilder iriString = new StringBuilder(iri.getIRIString());
		int startWord = iriString.lastIndexOf("#")+1;
		
		return iriString.substring(startWord);  
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
	
	public static void main(String... args) {
		Document vacancyContentDocument = Jsoup.parse(s);
		try {
			new OntologyWorker().defineClassOfWords(vacancyContentDocument);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
