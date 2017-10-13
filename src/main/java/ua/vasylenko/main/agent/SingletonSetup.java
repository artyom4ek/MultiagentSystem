package ua.vasylenko.main.agent;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;

/*
 * Класс для настройки контейнера агентов.
 * @Created by Тёма on 11.09.2017.
 * @version 1.0
 */
public class SingletonSetup {
	/** Ссылка на инстанс. */
	private static volatile SingletonSetup instance;
	
	/** Ссылка на контроллер контейнера агентов. */
	private static ContainerController containerController;
	
	/**
	 * Геттер к доступу к контейнеру.
	 */
	public ContainerController getContainerController() {
		return containerController;
	}

	/** Конструктор по-умолчанию для Singleton. */
	private SingletonSetup() { }
	
	/**
	 * Метод реализует Singleton.
	 * @return инстанс.
	 */
	public static SingletonSetup getInstance() {
		
		if(instance == null) {
			synchronized (SingletonSetup.class) {
				if(instance == null) {
					// Необходимо только раз создать контейнер и работать с ним.
					containerController = createContainerAgent();
					return instance = new SingletonSetup();
				}
			}
		}
		
		return instance;
	}
	
	/**
	 * Метод создает контейнер для агентов и возвращает его контроллер.
	 * @return контроллер созданного контейнера.
	 */
	private static ContainerController createContainerAgent() {
		Runtime runtime = Runtime.instance();
		
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		profile.setParameter(Profile.GUI, "true");
		
		ContainerController containerController = runtime.createMainContainer(profile);
		 
		return containerController;
	}
}
