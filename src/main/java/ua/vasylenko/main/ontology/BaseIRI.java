package ua.vasylenko.main.ontology;

import org.semanticweb.owlapi.model.IRI;

public class BaseIRI extends IRI {

	protected BaseIRI(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public String getShortForm() {
        if (!remainder.isEmpty()) {
            return remainder;
        }
        int lastSlashIndex = namespace.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex != namespace.length() - 1) {
            return namespace.substring(lastSlashIndex + 1);
        }
        return toQuotedString();
    }
}
