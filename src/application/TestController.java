package application;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class TestController implements Initializable {

    @FXML
    private ImageView image;
    
    @Override
    public void initialize(URL url,ResourceBundle rb) {
    		
    		TranslateTransition fade = new TranslateTransition();
    		fade.setDuration(Duration.seconds(10));
    		fade.setNode(image);
    		
    		fade.setToY(-200);
    		fade.setToX(100);
    		
    		fade.play();
    		
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
