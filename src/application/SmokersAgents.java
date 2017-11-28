    /// <summary>
    /// File record modification
	/// 
	/// 1. Created by 104703033
	/// 2. Modified by 104703042 : Implement GUI interface
	///
    /// </summary>

package application;

//import java.util.Random;
import org.apache.commons.math3.distribution.PoissonDistribution; 

/// GUI Library
import javafx.scene.image.*;
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

		try {
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

			/* Main thread sleeps for 3 sec before smkrs and agts start. */
			Thread.sleep(3000);
			/*************************************************************/
		
			tobaccoAgent.start();
			paperAgent.start();
			matchAgent.start();

			tobaccoSmoker.start();
			paperSmoker.start();
			matchSmoker.start();
		} catch (Exception e) {}
	}	
}


class Table {
	private boolean[] tableIngred = new boolean[3]; // [TOBACCO, PAPER, MATCH].	
	private int numIngredInTable;

	synchronized void getIngred(String ingred) {
		if(numIngredInTable < 2) {
			/* agent puts its ingredient on the table. */
			if(ingred.equals("TOBACCO") && tableIngred[0] == false) {
				tableIngred[0] = true;
				System.out.print("TOBACCO ");
			}
			else if(ingred.equals("PAPER") && tableIngred[1] == false) {
				tableIngred[1] = true;
				System.out.print("PAPER ");
			}
			else if(ingred.equals("MATCH") && tableIngred[2] == false) {
				tableIngred[2] = true;
				System.out.print("MATCH ");
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

class Agent extends Thread implements Initializable{
	
	private Table table;
	private String myIngred;

	/* GUI items */
    @FXML
    private ImageView tobacco;
    
    @Override
    public void initialize(URL url,ResourceBundle rb) {
    	
    }
	
	/* constructor */
	public Agent(Table table, String name) {
		this.table = table;
		this.myIngred = name;
	}

	public void run() {
		//tobacco.setImage(new Image("file:src/image/match.png"));
		while(true) {
			this.move(); // GUI image motion
			putIngred();
		}
	}

	synchronized void putIngred() {
		// The agt tries to put ingred onto table, then it waits.
		// If: 1. The table is available, the agt puts and then waits.
		//	   2. The table is full, the agt just waits.
		table.getIngred(myIngred);
	}
	
	/* GUI Image motion */
    public void move() {
    	
		TranslateTransition fade = new TranslateTransition();
		fade.setDuration(Duration.seconds(10));
		fade.setNode(tobacco);	
		fade.setToY(-100);
		fade.setToX(100);	
		fade.play();
    	
    }
	
}
   