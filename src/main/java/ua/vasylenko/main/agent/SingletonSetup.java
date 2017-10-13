package ua.vasylenko.main.agent;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;

/*
 * ����� ��� ��������� ���������� �������.
 * @Created by Ҹ�� on 11.09.2017.
 * @version 1.0
 */
public class SingletonSetup {
	/** ������ �� �������. */
	private static volatile SingletonSetup instance;
	
	/** ������ �� ���������� ���������� �������. */
	private static ContainerController containerController;
	
	/**
	 * ������ � ������� � ����������.
	 */
	public ContainerController getContainerController() {
		return containerController;
	}

	/** ����������� ��-��������� ��� Singleton. */
	private SingletonSetup() { }
	
	/**
	 * ����� ��������� Singleton.
	 * @return �������.
	 */
	public static SingletonSetup getInstance() {
		
		if(instance == null) {
			synchronized (SingletonSetup.class) {
				if(instance == null) {
					// ���������� ������ ��� ������� ��������� � �������� � ���.
					containerController = createContainerAgent();
					return instance = new SingletonSetup();
				}
			}
		}
		
		return instance;
	}
	
	/**
	 * ����� ������� ��������� ��� ������� � ���������� ��� ����������.
	 * @return ���������� ���������� ����������.
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
