package ua.vasylenko.main.ontology;

import org.jsoup.nodes.Document;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

public interface OntologySetuble {
	String ONTOLOGY_NAME = "VacanciesOntology.owl";
	String ONTOLOGY_IRI = "http://www.semanticweb.org/asus/ontologies/2017/9/untitled-ontology-17#";
	
	void defineClassOfWords(Document htmlString) throws OWLOntologyCreationException;
}
