package application;

import application.MainApp;
import javafx.scene.image.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class smkrTobacco extends Thread implements Initializable{

	public MainApp main;
	
    @FXML
    private ImageView image;
    
    @Override
    public void initialize(URL url,ResourceBundle rb) {
    	
    }
	
	public void run() {
		
        try {
            Thread.currentThread().sleep(5000);
         } catch (Exception e) {
            System.out.println(e);
         }
		
		image.setImage(new Image("file:///Users/linzichao/Desktop/Java/OS_smoker/src/M.png"));
		this.move();
		System.out.println("hi");
		
	}
	
    public void move() {
    	
		TranslateTransition fade = new TranslateTransition();
		fade.setDuration(Duration.seconds(10));
		fade.setNode(image);
		
		fade.setToY(-200);
		fade.setToX(100);
		
		fade.play();
    	
    }
	
}
