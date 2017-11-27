package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.Group;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;



public class MainApp extends Application {

	private Stage primaryStage;
    public BorderPane rootLayout;
    
    public static Table table = new Table();
    public Agent agent = new Agent(MainApp.table, "TOBACCO");
    public smkrTobacco smoker;
    
    public startController _start;
    
    @Override
    public void start(Stage primaryStage) {
    
    	
        smoker = new smkrTobacco();
        
        _start = new startController();
        
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
        this.primaryStage.setWidth(1000);
        
        
        initRootLayout();
    
        startOverview();
        
        
        		
        
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
     * Shows the start overview inside the root layout.
     */
    public void startOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Startview.fxml"));          
            loader.setController(_start);
            
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            personOverview.setStyle(
            		"-fx-background-image: url(" + "'@../../image/background.jpg'" +
            		"); " +
            	    "-fx-background-size: cover;"	
            	);
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * startController controls the button event.
     */
    public class startController implements Initializable{

	    	@FXML
	    	private Button startButton;
        
        @Override
        public void initialize(URL url,ResourceBundle rb) {
        	
        }
        
        @FXML protected void handleSubmitButtonAction(ActionEvent event) {
            try {
                // Load person overview.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/Mainview.fxml"));          
                loader.setController(agent);
                
                AnchorPane personOverview = (AnchorPane) loader.load();
                
                // Set person overview into the center of root layout.
                rootLayout.setCenter(personOverview);
                
                agent.start();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
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
