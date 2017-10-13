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
 * Начальная форма приложения.
 * @Created by Тёма on 10.09.2017.
 * @version 1.0
 */
public class MainForm extends Application {
	/** Основной контейнер окна. */
	private Stage primaryStage;
	
	/** Корневой слой сцены. */
    private BorderPane rootLayout;
    
    /** Контроллер главной формы. */
    private MainFormController mainFormController;
    
    /** Геттеры/сеттеры */
    public MainFormController getMainFormController() {
		return mainFormController;
	}

	public void setMainFormController(MainFormController mainFormController) {
		this.mainFormController = mainFormController;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		// Отображаем корневой макет.
		initRootLayout();
		
		// Отображаем 
		showMainFormLayout();
	}

	/**
     * Метод инициализирует корневой макет.
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader rootLayoutLoader = new FXMLLoader();
            
            // Получаем доступ к каталогу ресурсов.
            rootLayoutLoader.setLocation(getClass().getClassLoader().getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) rootLayoutLoader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setTitle("Мультиагентна система v1.0");
            primaryStage.setScene(scene);
            primaryStage.setMinHeight(380);
            primaryStage.setMinWidth(480);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
     * Метод отображаем в корневом макете компоненты главного окна.
     */
    public void showMainFormLayout() {
        try {
            // Загружаем главный слой формы.
            FXMLLoader mainFormLoader = new FXMLLoader();
            mainFormLoader.setLocation(getClass().getClassLoader().getResource("view/MainFormLayout.fxml"));
            GridPane gridPane = (GridPane) mainFormLoader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(gridPane);
            
            mainFormController = mainFormLoader.getController();
            // Вешаем событие на закрытие окна приложения.
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
