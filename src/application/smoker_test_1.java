package application;

import java.util.Random;

public class smoker_test_1{
	
	public static final int TABACCO = 0;
	public static final int PAPER = 1;
	public static final int MATCH = 2; 
	
	public static void main(String[] args){
		Agent agent = new Agent();
		agent.placeIngredients();
		Smoker TABACCO_smoker =  new Smoker(agent,"TABACCO_OWNER",TABACCO);
	    Smoker PAPER_smoker = new Smoker(agent,"PAPER_OWNER",PAPER); 
		Smoker MATCH_smoker = new Smoker(agent,"MACTCH_OWNER",MATCH);
		TABACCO_smoker.start();
		PAPER_smoker.start();
		MATCH_smoker.start();
    }
}

class Agent{
	
	public boolean tobacco;
	public boolean paper;
	public boolean match;
	
	public Agent(){
		tobacco = false;
		paper = false;
		match = false;
	}
	synchronized boolean setValue(int draw){
		switch(draw){
		  case 0:
		  
	        if(tobacco == true){return false;}  	
  		    tobacco = true;
		    break;
		    
		  case 1:
			
			if(paper == true){return false;}
			paper = true;
			break;
		  
		  case 2:
			  
			if(match == true){return false;}
			match = true;
			break;
 		}
		return true;
	}
	synchronized void placeIngredients(){
		resetTable();
		Random random = new Random();
		while(!setValue(random.nextInt(3)));
		while(!setValue(random.nextInt(3)));
	}
	synchronized void resetTable(){
		tobacco = false;
		paper = false;
		match = false;
	}
	synchronized void getIngredients(int code,String name){
		
			int need =10;
			String in_table="";
			if(tobacco == false){
				need = 0;
				in_table = "PAPER & MATCH";
			}
			if(paper == false){
				need = 1;
				in_table = "TOBACCO & MATCH";
			}
			if(match == false){
				need = 2;
				in_table = "TOBACCO & PAPER";
			}
		
			int time = (int)(Math.random()*1000);
		
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
				try{
					Thread.sleep(1000);
					
				}catch(InterruptedException e){}
				
				placeIngredients();
				notifyAll();
			
			}else {
				
				try{
					Thread.sleep(1000);					
				}catch(InterruptedException e){}
				
			}
		
	}
}
class Smoker extends Thread{
		Agent a;
		int my_Ingred;
		String name;
		
		public Smoker(Agent a,String name ,int my_Ingred){
			this.a = a;
			this.name = name;
			this.my_Ingred = my_Ingred;			
		}
		
		public void run(){
			
			while(true) {
				a.getIngredients(my_Ingred,name);
//				try{
//					wait();
//				}catch(InterruptedException e){
//					
//				}
			}
		
		}
}







