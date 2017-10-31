//package application;

import java.util.Random;
import org.apache.commons.math3.distribution.PoissonDistribution; 


public class SmokersAgents{


	public static final int TOBACCO = 0;
	public static final int PAPER = 1;
	public static final int MATCH = 2; 
	public static Table table;
	//public static PoissonDistribution poissonDistribution = new PoissonDistribution(3);

	public static void main(String[] args){

		table = new Table();

		Smoker tobaccoSmoker =  new Smoker(table,"TOBACCO",TOBACCO);
		Smoker paperSmoker = new Smoker(table,"PAPER",PAPER); 
		Smoker matchSmoker = new Smoker(table,"MATCH",MATCH);

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


class Table {
	private boolean[] tableIngred = new boolean[3]; // [TOBACCO, PAPER, MATCH]	
	private int numIngredInTable;


	synchronized void getIngred(String ingred) {
		if(numIngredInTable < 2) {
			/* agent puts its ingredient on the table */
			if(ingred == "TOBACCO" && tableIngred[0] == false) {
				tableIngred[0] = true;
				System.out.print("TOBACCO ");
			}
			else if(ingred == "PAPER" && tableIngred[1] == false) {
				tableIngred[1] = true;
				System.out.print("PAPER ");
			}
			else if(ingred == "MATCH" && tableIngred[2] == false) {
				tableIngred[2] = true;
				System.out.print("MATCH ");
			}
			numIngredInTable ++;
		}
		try {
			System.out.print(" " + ingred + "Waiting ");
			wait();
			System.out.print(" " + ingred + "Notified ");
		} catch(Exception e) {}
	}

	synchronized boolean giveIngred(String smkrName) {
		if(numIngredInTable == 2) {
			if(smkrName == "TOBACCO" && tableIngred[0] == false) {
				/* matches */
				return true;
			}
			else if(smkrName == "PAPER" && tableIngred[1] == false) {
				/* matches */
				return true;
			}
			else if(smkrName == "MATCH" && tableIngred[2] == false) {
				/* matches */
				return true;
			}
		}
		return false;
	}

	synchronized void consumeIngred() {
		tableIngred[0] = false;
		tableIngred[1] = false;
		tableIngred[2] = false;
		numIngredInTable = 0;
		System.out.println("==============================");
		System.out.print("Table now has: ");
	}

	synchronized void notifyAllPeople() {
		notifyAll();
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
		//synchronized(table) {
			/* the agent try to put ingred onto table, then it waits.
			 * If: 1. The table is available, the agent put and then wait.
			 *	   2. The table is full, the agent just wait. */
			table.getIngred(myIngred);
		//}
	}
}


class Smoker extends Thread{
	Table table;
	int my_Ingred;
	String name;

	/* constructor */
	public Smoker(Table table, String name ,int my_Ingred){
		this.table = table;
		this.name = name;
		this.my_Ingred = my_Ingred;			
	}

	public void run(){
		while(true) {
			//System.out.println(name + " is in run()");
			getIngred();
			//System.out.println("DB_0");
		}
	}

	synchronized void getIngred() {
		//synchronized(table) {

			if(table.giveIngred(name) == true) {
				//System.out.println("DB_1");
				// if it's the right smoker, go smoking, and then notify all smokers and agents
				smoke();
				table.notifyAllPeople();
			}
		//}
	}

	synchronized void smoke() {
		try {
			System.out.println("\n" + name + "_owner is smoking");
			Thread.sleep(2000);
			table.consumeIngred();
				//System.out.println("DB_3");
		} catch(Exception e) {}
	}
}
/*

   if(code == need){
   String smoker_own ="";
   if(code == 0){
   smoker_own = "TABACCO";
   }
   if(code == 1){
   smoker_own = "PAPER";
   }
   if(code == 2){
   smoker_own = "MATCH";
   }
   System.out.println("==================");
   System.out.println("Agent delivers: "+ in_table +" in the table");
   System.out.println("Smoker : " +name+" has "+smoker_own+" and is ready to smoke");
 */
