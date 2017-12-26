package application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import application.SmokersAgents.Agent;
import application.SmokersAgents.Smoker;
import application.SmokersAgents.Table;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;



public class MainApp extends Application {

	private Stage primaryStage;
    public BorderPane rootLayout;
        
    public startController _start = new startController();
    
    public SmokersAgents smkrAgt = new SmokersAgents();
    public Table table = smkrAgt.new Table();
    
    CountDownLatch startSignal = new CountDownLatch(1);
    
    Agent[] agts = new Agent[] {
    		smkrAgt.new Agent(table, "TOBACCO", startSignal), 
    		smkrAgt.new Agent(table, "PAPER", startSignal),
    		smkrAgt.new Agent(table, "MATCH", startSignal)};
    
    
    		
	// Create smkrs.
	public Smoker tobaccoSmoker =  smkrAgt.new Smoker(table,"TOBACCO",SmokersAgents.TOBACCO);
	public Smoker paperSmoker = smkrAgt.new Smoker(table,"PAPER",SmokersAgents.PAPER); 
	public Smoker matchSmoker = smkrAgt.new Smoker(table,"MATCH",SmokersAgents.MATCH);

	// Create agts.
	/*
	public Agent tobaccoAgent = smkrAgt.new Agent(table, "TOBACCO", startSignal);
	public Agent paperAgent = smkrAgt.new Agent(table, "PAPER", startSignal);
	public Agent matchAgent = smkrAgt.new Agent(table, "MATCH", startSignal);
    */
	
	
    
    @Override
    public void start(Stage primaryStage) throws Exception {
       
    	    this.primaryStage = primaryStage;
        this.primaryStage.setTitle("OS_chainsmoker");
        
        this.primaryStage.setHeight(700);
        this.primaryStage.setWidth(1200);
        
        
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
                loader.setController(table);
                
                AnchorPane personOverview = (AnchorPane) loader.load();
                
                // Set person overview into the center of root layout.
                rootLayout.setCenter(personOverview);
                
                Media media = new Media(getClass().getResource("/music/BGM.mp3").toURI().toString());      
                MediaPlayer player = new MediaPlayer(media);  
                player.setOnEndOfMedia(new Runnable() {
                    public void run() {
                      player.seek(Duration.ZERO);
                    }
                });
                //player.play();
                
	        		System.out.print("Table now has: ");
	
	        		/*
	        		tobaccoAgent.start();
	        		paperAgent.start();
	        		matchAgent.start();
	*/
	        		smkrAgt.setAgents(agts);
	        		agts[0].start();
	        		agts[1].start();
	        		agts[2].start();
	        		
	        		tobaccoSmoker.start();
	        		paperSmoker.start();
	        		matchSmoker.start();
	        		
	        		startSignal.countDown(); // start
	        		
                
                
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
				// TODO Auto-generated catch block
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
