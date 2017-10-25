package application;

import javafx.scene.image.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

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
