package ua.vasylenko.main;

import java.io.IOException;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ua.vasylenko.main.agent.SingletonSetup;
import ua.vasylenko.main.controller.MainFormController;

/*
 * ��������� ����� ����������.
 * @Created by Ҹ�� on 10.09.2017.
 * @version 1.0
 */
public class MainForm extends Application {
	/** �������� ��������� ����. */
	private Stage primaryStage;
	
	/** �������� ���� �����. */
    private BorderPane rootLayout;
    
    /** ���������� ������� �����. */
    private MainFormController mainFormController;
    
    /** �������/������� */
    public MainFormController getMainFormController() {
		return mainFormController;
	}

	public void setMainFormController(MainFormController mainFormController) {
		this.mainFormController = mainFormController;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		// ���������� �������� �����.
		initRootLayout();
		
		// ���������� 
		showMainFormLayout();
	}

	/**
     * ����� �������������� �������� �����.
     */
    public void initRootLayout() {
        try {
            // ��������� �������� ����� �� fxml �����.
            FXMLLoader rootLayoutLoader = new FXMLLoader();
            
            // �������� ������ � �������� ��������.
            rootLayoutLoader.setLocation(getClass().getClassLoader().getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) rootLayoutLoader.load();

            // ���������� �����, ���������� �������� �����.
            Scene scene = new Scene(rootLayout);
            primaryStage.setTitle("������������� ������� v1.0");
            primaryStage.setScene(scene);
            primaryStage.setMinHeight(380);
            primaryStage.setMinWidth(480);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
     * ����� ���������� � �������� ������ ���������� �������� ����.
     */
    public void showMainFormLayout() {
        try {
            // ��������� ������� ���� �����.
            FXMLLoader mainFormLoader = new FXMLLoader();
            mainFormLoader.setLocation(getClass().getClassLoader().getResource("view/MainFormLayout.fxml"));
            GridPane gridPane = (GridPane) mainFormLoader.load();

            // �������� �������� �� ��������� � ����� ��������� ������.
            rootLayout.setCenter(gridPane);
            
            mainFormController = mainFormLoader.getController();
            // ������ ������� �� �������� ���� ����������.
            primaryStage.setOnHidden(e -> {
               mainFormController.exitApplication();
            });
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public static void main(String[] args) throws Exception {
        launch(args);
    }
}
