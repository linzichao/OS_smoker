package application;

//import java.util.Random;
import org.apache.commons.math3.distribution.ExponentialDistribution; 

/// GUI Library
import javafx.scene.image.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SmokersAgents{

	public static final int TOBACCO = 0;
	public static final int PAPER = 1;
	public static final int MATCH = 2; 

	public static void main(String[] args){
	}	
}


class Table implements Initializable{
	
	/* GUI items */
	@FXML
	private ImageView table1;

	@FXML
	private ImageView table2;
		
	@FXML
	private Text txtSmoke;
	
	@FXML 
	private ImageView smoking;
	
	@FXML
	private Label timerLabel;
	
	@FXML
	public Text event;
	  
	@Override
	public void initialize(URL url,ResourceBundle rb) {
	  	
	}
	
	public TranslateTransition fade1 = new TranslateTransition(); // For thr first ingredient on table 
	public TranslateTransition fade2 = new TranslateTransition(); // For the second ingredient on table
	public TranslateTransition fade3 = new TranslateTransition(); // For the smoking image 
	private Timeline timeline;
	private IntegerProperty timeSeconds =  new SimpleIntegerProperty(0);
	
	/* GUI item end */
	
	private boolean[] tableIngred = new boolean[3]; // [TOBACCO, PAPER, MATCH].	
	private int numIngredInTable;
	
	ExponentialDistribution ed = new ExponentialDistribution(8);
	final int GUI_TRANSITION_TIME = 2000; // 2 sec

	synchronized void getIngred(String ingred) {
		if(numIngredInTable < 2) {
			
			int waitingTime = getGapTime();
			
			/* agent puts its ingredient on the table. */
			if(ingred.equals("TOBACCO") && tableIngred[0] == false) {
				tableIngred[0] = true;
				System.out.print("TOBACCO, gap time = " + waitingTime/1000 + " sec. ");
				// GUI control
		   		Platform.runLater(() ->{
		   			countdown(waitingTime/1000);
		   		});
				move(SmokersAgents.TOBACCO, numIngredInTable);
				// GUI control end
			}
			else if(ingred.equals("PAPER") && tableIngred[1] == false) {
				tableIngred[1] = true;
				System.out.print("PAPER, gap time = " + waitingTime/1000 + " sec. ");
				// GUI control
				Platform.runLater(() ->{
					countdown(waitingTime/1000);
		   		});
				move(SmokersAgents.PAPER,numIngredInTable);
				// GUI control end
			}
			else if(ingred.equals("MATCH") && tableIngred[2] == false) {
				tableIngred[2] = true;
				System.out.print("MATCH, gap time = " + waitingTime/1000 + " sec. ");
				// GUI control 
				Platform.runLater(() ->{
					countdown(waitingTime/1000);
		   		});
				move(SmokersAgents.MATCH,numIngredInTable);
				// GUI control end
			}
			numIngredInTable ++;
			
			/* Sleep for a time interval of exponential distribition after an agt puts ingred in table */
			try {
				Thread.sleep(waitingTime);
			}
			catch (Exception e) {}
			/*******************************************************************************************/
		}
		try {
			/* agt blocks here. */
			wait();
			/*******************/
		} catch(Exception e) {}
	}
	
	int getGapTime() {
		int waitingTime = 0;
		// Let waitingTime in the range 3 <= time <= 13
		while(waitingTime < 3 || waitingTime > 13) {
			waitingTime = (int) ed.sample();
		}
		return waitingTime * 1000 + GUI_TRANSITION_TIME;
	}

	synchronized boolean giveIngred(String smkrName) {
		if(numIngredInTable == 2) {
			if(smkrName.equals("TOBACCO") && tableIngred[0] == false) { // if smkrTOBACCO comes in , and no tobacco on the table.
				return true;
			}
			else if(smkrName.equals("PAPER") && tableIngred[1] == false) { // if smkrPAPER comes in , and no paper on the table.
				return true;
			}
			else if(smkrName.equals("MATCH") && tableIngred[2] == false) { // if smkrMATCH comes in , and no match on the table.
				return true;
			}
		}
		return false;
	}

	synchronized void consumeIngred() {
		try {
			// set table to empty.
			tableIngred[0] = false;
			tableIngred[1] = false;
			tableIngred[2] = false;
			numIngredInTable = 0;
			
			/* GUI remove */
			remove();
			
			System.out.println("\n==============================");
			System.out.print("Table now has: ");		
		} catch (Exception e) {}
	}

	synchronized void notifyAllPeople() {
		notifyAll();
	}
	
	/* GUI Image motion */
    public void move(int i,int time) {
    		
    		event.setText("Put Ingredients");
		
    		fade1.setDuration(Duration.seconds(2));
		fade1.setFromX(491);
		fade1.setFromY(getLayoutY(i)-271);
		fade1.setToY(0);
		fade1.setToX(0);
		
		fade2.setDuration(Duration.seconds(2));
		fade2.setFromX(379);
		fade2.setFromY(getLayoutY(i)-271);
		fade2.setToY(0);
		fade2.setToX(0);
    		
		if(time==0) {
		
			switch(i) {
				
				case 0:
					table1.setImage(new Image("file:src/image/tobacco.png"));
					fade1.setNode(table1);
					fade1.play();
					break;
				case	 1:
					table1.setImage(new Image("file:src/image/paper.png"));
					fade1.setNode(table1);
					fade1.play();
					break;
				case 2:
					table1.setImage(new Image("file:src/image/match.png"));
					fade1.setNode(table1);
					fade1.play();
					break;
			}
		
		}else {
			
			switch(i) {
				
				case 0:
					table2.setImage(new Image("file:src/image/tobacco.png"));
					fade2.setNode(table2);
					fade2.play();
					break;
				case	 1:
					table2.setImage(new Image("file:src/image/paper.png"));
					fade2.setNode(table2);
					fade2.play();
					break;
				case 2:
					table2.setImage(new Image("file:src/image/match.png"));
					fade2.setNode(table2);
					fade2.play();
					break;
			}
			
		}
		
    	
    }
	
    /* GUI table */
    public void nowSmoke(String name) {
      	
    		int y = 0;
    		
    		switch (name) {
    			case "TOBACCO":
    				y = 55;
    				break;
    			case "PAPER":
    				y = 279;
    				break;
    			case "MATCH":
    				y = 509;
    				break;
    		}
		fade3.setDuration(Duration.seconds(2));
		fade3.setFromX(0);
		fade3.setFromY(0);
		fade3.setToX(-355);
		fade3.setToY(y-271);
		smoking.setImage(new Image("file:src/image/smoking.png"));
		fade3.setNode(smoking);
		fade3.play();
		
    		table1.setImage(new Image("file:src/image/white.png"));
    		table2.setImage(new Image("file:src/image/white.png"));
    		
		txtSmoke.setText("Smoking!!!");
		event.setText("Smoker smoking");
    		
    }
    
    /* GUI finish one time */
    public void remove() {
    		smoking.setImage(new Image("file:src/image/white.png"));
    		txtSmoke.setText("");
    }
    
    /* GUI position */
    public int getLayoutY(int i) {
    		
    		int y=0;
    		
    		switch(i) {
    			
    			case 0:
    				y = 55;
    				break;
    			case 1:
    				y = 279;
    				break;
    			case 2:
    				y = 497;
    				break;
    		
    		}
    		return y;
    }
    
    /* GUI countdown */
    public void countdown(int waitingtime) {
    		
    		
    		timerLabel.textProperty().bind(timeSeconds.asString());    
    	
    		if (timeline != null) {
            timeline.stop();
        }
    		
    		timeSeconds.setValue(waitingtime);
	
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(waitingtime),
                new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();
    }
   
}

class Smoker extends Thread{
	Table table;
	int myIngred;
	String name;
	ExponentialDistribution ed;
	final int GUI_TRANSITION_TIME = 2000; // 2 second
	final int SMOKING_TIME = 5000; // 5 second 

	/* constructor */
	public Smoker(Table table, String name ,int myIngred){
		this.table = table;
		this.name = name;
		this.myIngred = myIngred;
		ed = new ExponentialDistribution(8);
	}

	public void run(){
		while(true) {
			getIngred();
		}
	}

	synchronized void getIngred() {

		if(table.giveIngred(name) == true) {
			// if it's the right smoker, go smoking, and then notify all agents.
			smoke();
			table.notifyAllPeople(); // 2 or 3 agts are waiting in table now, you need to wake'em up.
		}
	}

	synchronized void smoke() {
		try {
			int gapTime = getGapTime();
			System.out.print("\n" + name + "_owner is making cigarette, wait for 1 sec");
			
			//GUI:: remove ingredient and smoke
			table.nowSmoke(name); 
			//GUI:: countdown
			Platform.runLater(() ->{
	   			table.countdown(SMOKING_TIME/1000 + 1);
	   		});
			//GUI control end
			
			/* Making cigarette for 1 sec */
			Thread.sleep(1000);
			/******************************/
			
			System.out.print("\n" + name + "_owner is smoking, wait for " + SMOKING_TIME/1000 + " sec.");
			sleepLoadingBar(SMOKING_TIME); // smkr sleeps in this method.
			table.consumeIngred();
			
			System.out.print("\n Gap time = " + gapTime/1000 + " second.");	
			//GUI:: countdown
			Platform.runLater(() ->{
				table.event.setText("Gap Time");
	   			table.countdown(gapTime/1000);
	   		});
			//GUI control end
			
			/* Gap time after smkr smokes */
			Thread.sleep(gapTime);
			/******************************/
			
			System.out.println("\n==============================");
			System.out.print("Table now has: ");		
		} catch(Exception e) {}
	}
	
	int getGapTime() {
		int waitingTime = 0;
		// Let waitingTime in the range 3 <= time <= 13
		while(waitingTime < 3 || waitingTime > 13) {
			waitingTime = (int) ed.sample();
		}
		return waitingTime * 1000 + GUI_TRANSITION_TIME;
	}
	
	synchronized void sleepLoadingBar(int time) {
		try {
			int waitingTimePerRound = time / 100;
			String strLeft = "|";
			String strRight = "                                                                                                    |";
			
			System.out.println();
			for(int i = 0; i < 100; i++) {
				System.out.print(strLeft + strRight.substring(i) + "    " + i + "%\r");
				//System.out.print("DB_0");
				strLeft += "*";
				
				/* smkr sleeps here. */
				Thread.sleep(waitingTimePerRound);
				/********************/
			}
			System.out.print(strLeft + "|" + "    " + "100%");
		} catch(Exception e) {}			
	}
}

class Agent extends Thread {
	
	private Table table;
	private String myIngred;
	
	/* constructor */
	public Agent(Table table, String name) {
		this.table = table;
		this.myIngred = name;
	}

	public void run() {
		while(true) {    
			putIngred();
		}
	}

	synchronized void putIngred() {
		// The agt tries to put ingred onto table, then it waits.
		// If: 1. The table is available, the agt puts and then waits.
		//	   2. The table is full, the agt just waits.
		table.getIngred(myIngred);
	}
	
    
}
   
