package application;

//import java.util.Random;
import org.apache.commons.math3.distribution.PoissonDistribution; 

/// GUI Library
import javafx.scene.image.*;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;


public class SmokersAgents{

	public static final int TOBACCO = 0;
	public static final int PAPER = 1;
	public static final int MATCH = 2; 

	public static void main(String[] args){

		// For cmd line, use args.length != 3		
		// For eclipse, use args.length != 1
		if(args.length != 1) {
			System.out.println("Usage: java SmokersAgents [Poisson distribution mean value]");
			return;
		}
		
		 // For cmd line, use args[2],		
		 // For eclipse, use args[0], Run => Run Configurations => Arguments.
		int meanWaitingTime = Integer.parseInt(args[0]);
		System.out.println("Mean Waiting Time = " + meanWaitingTime);
		Table table = new Table();

		// Create smkrs.
		Smoker tobaccoSmoker =  new Smoker(table,"TOBACCO",TOBACCO, meanWaitingTime);
		Smoker paperSmoker = new Smoker(table,"PAPER",PAPER, meanWaitingTime); 
		Smoker matchSmoker = new Smoker(table,"MATCH",MATCH, meanWaitingTime);

		// Create agts.
		Agent tobaccoAgent = new Agent(table, "TOBACCO");
		Agent paperAgent = new Agent(table, "PAPER");
		Agent matchAgent = new Agent(table, "MATCH");

		System.out.print("Table now has: ");

		tobaccoAgent.start();
		paperAgent.start();
		matchAgent.start();

		tobaccoSmoker.start();
		paperSmoker.start();
		matchSmoker.start();
	}	
}


class Table implements Initializable{
	
	/* GUI items */
	@FXML
	private ImageView table1;

	@FXML
	private ImageView table2;
	  
	@Override
	public void initialize(URL url,ResourceBundle rb) {
	  	
	}
	
	public TranslateTransition fade1 = new TranslateTransition();
	public TranslateTransition fade2 = new TranslateTransition();
	
	private boolean[] tableIngred = new boolean[3]; // [TOBACCO, PAPER, MATCH].	
	private int numIngredInTable;

	synchronized void getIngred(String ingred) {
		if(numIngredInTable < 2) {
			/* agent puts its ingredient on the table. */
			if(ingred.equals("TOBACCO") && tableIngred[0] == false) {
				tableIngred[0] = true;
				System.out.print("TOBACCO ");
				move(SmokersAgents.TOBACCO, numIngredInTable);
		        try {
		            Thread.sleep(5000);
		         } catch (Exception e) {
		            System.out.println(e);
		         }
			}
			else if(ingred.equals("PAPER") && tableIngred[1] == false) {
				tableIngred[1] = true;
				System.out.print("PAPER ");
				move(SmokersAgents.PAPER,numIngredInTable);
		        try {
		            Thread.sleep(5000);
		         } catch (Exception e) {
		            System.out.println(e);
		         }
			}
			else if(ingred.equals("MATCH") && tableIngred[2] == false) {
				tableIngred[2] = true;
				System.out.print("MATCH ");
				move(SmokersAgents.MATCH,numIngredInTable);
		        try {
		            Thread.sleep(5000);
		         } catch (Exception e) {
		            System.out.println(e);
		         }
			}
			numIngredInTable ++;
		}
		try {
			
			/* agt blocks here. */
			wait();
			/*******************/
			
		} catch(Exception e) {}
	}

	synchronized boolean giveIngred(String smkrName) {
		if(numIngredInTable == 2) {
			if(smkrName.equals("TOBACCO") && tableIngred[0] == false) { // if smkrTOBACCO comes in , and no tobacco on the table.
				moveAll();
				return true;
			}
			else if(smkrName.equals("PAPER") && tableIngred[1] == false) { // if smkrPAPER comes in , and no paper on the table.
				moveAll();
				return true;
			}
			else if(smkrName.equals("MATCH") && tableIngred[2] == false) { // if smkrMATCH comes in , and no match on the table.
				moveAll();
				return true;
			}
		}
		return false;
	}

	synchronized void consumeIngred() {
		// set table to empty.
		tableIngred[0] = false;
		tableIngred[1] = false;
		tableIngred[2] = false;
		numIngredInTable = 0;
		System.out.println("\n==============================");
		System.out.print("Table now has: ");
	}

	synchronized void notifyAllPeople() {
		notifyAll();
	}
	
	/* GUI Image motion */
    public void move(int i,int time) {
    	
		fade1.setDuration(Duration.seconds(3));
		fade1.setToY(100);
		fade1.setToX(100);
		fade1.setAutoReverse(true);
		fade1.setCycleCount(2);
		
		fade2.setDuration(Duration.seconds(3));
		fade2.setToY(100);
		fade2.setToX(100);
		fade2.setAutoReverse(true);
		fade2.setCycleCount(2);
    		
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
	
//    public void moveMatch(int i) {
//		
//    		if(i==0) {
//			table1.setImage(new Image("file:src/image/match.png"));
//			fade.setNode(table1);
//    		}else {
//    			table2.setImage(new Image("file:src/image/match.png"));
//    			fade.setNode(table2);
//    		}
//		
//    		fade.setDuration(Duration.seconds(3));
//    		fade.setToY(100);
//    		fade.setToX(100);
//    		fade.setAutoReverse(true);
//    		fade.setCycleCount(2);
//    		fade.play();
//    		
//		//table.setImage(new Image("file:src/image/white.png"));
//	
//    }
//    
//    public void movePaper(int i) {
//		
//    		if(i==0) {
//			table1.setImage(new Image("file:src/image/paper.png"));
//			fade.setNode(table1);
//    		}else {
//    			table2.setImage(new Image("file:src/image/paper.png"));
//    			fade.setNode(table2);
//    		}
//			
//    		fade.setDuration(Duration.seconds(3));
//    		fade.setToY(100);
//    		fade.setToX(100);
//    		fade.setAutoReverse(true);
//    		fade.setCycleCount(2);
//    		fade.play();
//
//		//table.setImage(new Image("file:src/image/white.png"));
//	
//    }
    
    public void moveAll() {
    		
    		//table1.setImage(new Image("file:src/image/white.png"));
    		//table2.setImage(new Image("file:src/image/white.png"));
    		
    }

   
}

class Smoker extends Thread{
	Table table;
	int myIngred;
	String name;
	int meanWaitingTime;
	PoissonDistribution pd;

	/* constructor */
	public Smoker(Table table, String name ,int myIngred, int meanWaitingTime){
		this.table = table;
		this.name = name;
		this.myIngred = myIngred;
		this.meanWaitingTime = meanWaitingTime;
		pd = new PoissonDistribution(meanWaitingTime);
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
			int waitingTime;
			// Let waitingTime > 0.
			while(true) {
				waitingTime = pd.sample();
				if(waitingTime != 0) {
					break;
				}
			}
			
			System.out.print("\n" + name + "_owner is smoking, wait for " + waitingTime + " sec");
			sleepLoadingBar(waitingTime);
			table.consumeIngred();
		} catch(Exception e) {}
	}
	
	synchronized void sleepLoadingBar(int time) {
		try {
			int waitingTimePerRound = time * 1000 / 100;
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

//	/* GUI items */
//    @FXML
//    private ImageView tobacco;
//    
//    @FXML
//    private ImageView match;
//    
//    @Override
//    public void initialize(URL url,ResourceBundle rb) {
//    	
//    }
	
	/* constructor */
	public Agent(Table table, String name) {
		this.table = table;
		this.myIngred = name;
	}

	public void run() {
		//tobacco.setImage(new Image("file:src/image/match.png"));
		while(true) {    
			//this.moveMatch(); // GUI image motion
			putIngred();
		}
	}

	synchronized void putIngred() {
		// The agt tries to put ingred onto table, then it waits.
		// If: 1. The table is available, the agt puts and then waits.
		//	   2. The table is full, the agt just waits.
		table.getIngred(myIngred);
	}
	
//	/* GUI Image motion */
//    public void moveTobacco() {
//    	
//		TranslateTransition fade = new TranslateTransition();
//		fade.setDuration(Duration.seconds(10));
//		fade.setNode(tobacco);	
//		fade.setToY(-100);
//		fade.setToX(100);	
//		fade.play();
//    	
//    }
//	
//    public void moveMatch() {
//    	
//		TranslateTransition fade = new TranslateTransition();
//		fade.setDuration(Duration.seconds(10));
//		fade.setNode(match);	
//		fade.setToY(-100);
//		fade.setToX(100);	
//		fade.play();
//    	
//    }
    
}
   