package ua.vasylenko.main.model;

/*
 * Класс-модель вакансии.
 * @Created by Тёма on 10.09.2017.
 * @version 1.0
 */
public class VacancyModel {
	private String title = null;
	private String requirements = null;
	private String responsibilities = null;
	private String conditions = null;
	
	public VacancyModel(){
		
	}
	
	public VacancyModel(String title, String requirements, String responsibilities, String conditions) {
		this.title = title;
		this.requirements = requirements;
		this.responsibilities = responsibilities;
		this.conditions = conditions;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String imperativeRequirements) {
		this.requirements = imperativeRequirements;
	}

	public String getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

}
