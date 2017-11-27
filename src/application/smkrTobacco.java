package application;

import javafx.scene.image.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;



public class smkrTobacco extends Thread implements Initializable{
	
    @FXML
    private ImageView tobacco;
    
    @Override
    public void initialize(URL url,ResourceBundle rb) {
    	
    }
	
	public void run() {
		
        try {
            Thread.sleep(3000);
         } catch (Exception e) {
            System.out.println(e);
         }
		
		tobacco.setImage(new Image("file:src/image/match.png"));
		this.move();
		System.out.println("hi");
		
		
		
	}
	
    public void move() {
    	
		TranslateTransition fade = new TranslateTransition();
		fade.setDuration(Duration.seconds(10));
		fade.setNode(tobacco);
		
		fade.setToY(-100);
		fade.setToX(100);
		
		fade.play();
    	
    }
	
}

//class startController implements Initializable{
//
//	@FXML
//	private Button startButton;
//    
//    @Override
//    public void initialize(URL url,ResourceBundle rb) {
//    	
//    }
//    
//    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
//        try {
//            // Load person overview.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainApp.class.getResource("view/Mainview.fxml"));          
//            //loader.setController(_start);
//            
//            AnchorPane personOverview = (AnchorPane) loader.load();
//            
//            // Set person overview into the center of root layout.
//            MainApp.rootLayout.setCenter(personOverview);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    
//	
//}

