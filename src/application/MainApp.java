package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.Group;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import application.smkrTobacco;


public class MainApp extends Application {

	private Stage primaryStage;
    private BorderPane rootLayout;
    
    
//    public Image image; 
//    public ImageView imageview1;
    public smkrTobacco smoker;
    
    @Override
    public void start(Stage primaryStage) {
    
//    	  this.image = new Image("file:///Users/linzichao/Desktop/Java/OS_smoker/src/T.png"); 
//        this.imageview1 = new ImageView(image);
    		
        
        smoker = new smkrTobacco();

        
        
//        final Button startButton = new Button("Start");
//        startButton.setOnAction(new EventHandler<ActionEvent>() {
//          @Override
//          public void handle(ActionEvent event) {
//        	  		smoker.start();
//          }
//        });
 
        
    		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("OS_chainsmoker");
        
        this.primaryStage.setHeight(700);
        this.primaryStage.setWidth(800);
        
//        this.imageview1.setX(350);
//        this.imageview1.setY(325);
       
//        Group root = new Group(imageview1,startButton);
//        Scene scene = new Scene(root, 600, 500);
        
        initRootLayout();
    
        showPersonOverview();
        		
//        this.primaryStage.setScene(scene);
//        this.primaryStage.show();
        
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Mainview.fxml"));
            
            loader.setController(smoker);
            smoker.start();
            
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
