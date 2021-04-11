import java.util.*;
import java.util.Arrays;	

	public class ZombieDice {
		Scanner in = new Scanner (System.in);
		
		
		private int currentPlayer = 0;
		private int newPlayer = 0;
		private static int  numBrains = 0;
		private int acumBrains = 0, acumShots = 0, acumFootprint = 0;
		private String numPlayers = "";
		private Player[] playerList = null;
		
		private String[][] dice= {
	            {"brain","footprint", "brain", "shot","brain","footprint"},//1-Green dice
	            {"brain","footprint", "brain", "shot","brain","footprint"},
	            {"brain","footprint", "brain", "shot","brain","footprint"},
	            {"brain","footprint", "brain", "shot","brain","footprint"},
	            {"brain","footprint", "brain", "shot","brain","footprint"},
	            {"brain","footprint", "brain", "shot","brain","footprint"},//6-Green dice
	            {"brain","footprint", "shot", "shot","shot","footprint"},//1-Red dice
	            {"brain","footprint", "shot", "shot","shot","footprint"},
	            {"brain","footprint", "shot", "shot","shot","footprint"},//3-Red-dice
	            {"brain","footprint", "brain", "shot","shot","footprint"},//1-Yellow dice
	            {"brain","footprint", "brain", "shot","shot","footprint"},
	            {"brain","footprint", "brain", "shot","shot","footprint"},
	            {"brain","footprint", "brain", "shot","shot","footprint"} //4 Yellow dice
	          
	        };
		
	public static void main(String[] args) {
		Scanner in = new Scanner (System.in);

		ZombieDice newGame = new ZombieDice();
		newGame.beginGame();  //Starting point of the game
	}

	public  void beginGame() {
		do {
			System.out.println("How many players?");
			numPlayers =in.nextLine();
			try {
				newPlayer = Integer.parseInt(numPlayers);
					if (newPlayer < 2 || newPlayer > 6) 
					System.out.println("Player allowed between 2 and 6");
			} 
			catch (NumberFormatException nfe) { 
				System.out.println("INVALID NUMBER OF PLAYERS!");
			}
		} while (newPlayer < 2 || newPlayer > 6);

		playerList = new Player[newPlayer]; //Create a new object player.

		try {
			nuevosJugadores();
		} catch (NullPointerException npe) {
			System.out.println("ERROR");
		}
	}
	
	private void nuevosJugadores() {
		String nuevoNombre = "";
		int duplicate = 0;
		for (int i = 0; i < newPlayer; i++) { // run loop depends on number of players entered.
			do {
					System.out.print("Player name " + (i + 1) + ": ");
					nuevoNombre = in.nextLine();
					duplicate = 0; 
				for (int j = 0; j < newPlayer; j++) {
					if(playerList[j] != null && playerList[j].getName().equals(nuevoNombre))
						duplicate++; //
				}
				if(duplicate == 1)
					System.out.println("Name already taken!");

				} 
			while (duplicate > 0);
				playerList[i] = new Player(nuevoNombre);
			}
		diceRoller(dice); // call diceRoller to get 3 new dice
	}
	

	public  void diceRoller(String[][] dice){

		if(dice.length-1 != 0){ // evaluate till dice has no more dice in it.
            System.out.println("======================");
            System.out.println("Dice Availables: "+dice.length+" ");
            System.out.println("======================");
            int diceOne = new Random().nextInt(dice.length-1);
			int diceTwo;
			int diceThree;
			do{
            diceTwo = new Random().nextInt(dice.length-1);
            diceThree = new Random().nextInt(dice.length-1);;
	
            }while (diceTwo == diceOne || diceThree == diceOne);    // avoid duplication!

            System.out.print("dice one:" + " " +diceOne + ", ");
            System.out.print("dice two: " + diceTwo + ", ");
            System.out.println("dice three: " + diceThree);
            myDice(diceOne, diceTwo, diceThree, dice); // call myDice to get values from dice found.
	    	}
			else{
				System.out.println("NO MORE DICE");
				changePlayer();
			}
		}
	   private  void myDice (int diceOne, int diceTwo, int diceThree, String[][] dice) {
		String response = "";
		Scanner in = new Scanner(System.in);
        int r1 = new Random().nextInt(5); // generate the value(brain,shot or footprint).
		int r2 = new Random().nextInt(5);
		int r3 = new Random().nextInt(5);
        String resultdiceOne = (dice[(diceOne)][(r1)]);
        String resultdiceTwo = (dice[diceTwo][r2]);
        String resultdiceThree = (dice[diceThree][r3]);


        String[] diceResult = {resultdiceOne,resultdiceTwo,resultdiceThree}; // store the string value of each dice result 
		
        System.out.print(diceResult[0] + ", "); // print each value to the console.
        System.out.print(diceResult[1] + ", ") ;
        System.out.println(diceResult[2]);
       
        getResult(diceResult, dice); // call getResult to evaluate each value of our dice and then increment accumulators
        //remove(diceOne,diceTwo,diceThree, dice, diceResult); // then call our remove method to remove the dices found.
		if(resultdiceOne == "brain" || resultdiceOne == "shot"){
			dice = rm(diceOne,dice);
		}
		if(resultdiceTwo == "brain" || resultdiceTwo == "shot"){
			dice = rm(diceTwo,dice);
		}
		if(resultdiceThree == "brain" || resultdiceThree == "shot"){
			dice = rm(diceThree,dice);
		}
		System.out.println("Do You want to roll again? (y/n)");
		response = in.nextLine();
		
			if (response.equalsIgnoreCase("n")){ // if player wants to stop rolling and score brains
				numBrains = acumBrains; // set brains to our numBrains variable
				playerList[currentPlayer].setScore(playerList[currentPlayer].getScore() + numBrains); //set score from our class player 
				// we restart our variables to 0 for next player.
				acumBrains = 0;
			  acumShots = 0;
			  acumFootprint = 0;
			  //show player brains found
			  System.out.println("Player: "+String.valueOf(playerList[currentPlayer].getName())+" has eaten " + String.valueOf(playerList[currentPlayer].getScore()) + " Brains");
			  System.out.println("///////////////////");
			  
			  gameWinner(); // check if our current player has 13 brain.
			changePlayer();// if not winner change to the next player.
		}

		else if (response.equalsIgnoreCase("y")){
			gameWinner(); // check if winner
			diceRoller(dice); // roll dice again without dice found.
			}
		}

	   private  void getResult(String[]diceResult, String[][]dice){
	        
	       for(int i=0; i< diceResult.length; i++){
	       if(diceResult[i] == "brain"){
	    	   acumBrains +=1;
	       }
	       else if(diceResult[i] == "shot"){
	    	   acumShots +=1;
	       }else{
	    	   acumFootprint +=1;
	      	 }
	       }
	       System.out.print("You have total brains: " + playerList[currentPlayer].getScore() + ", ");
		   System.out.print("you have brains in this round: " + acumBrains + ", ");
	       System.out.print("you have shots: " + acumShots + ", ");
	       System.out.println("You have footprint: " + acumFootprint);
	       deadPlayer(); // call method deadPlayer to check if the player got 3 shots if so it will loose the turn.
	   }

	   private String[][] rm (int diceP,String[][]dice){
		List<String[]> diceList = new ArrayList<String[]>(Arrays.asList(dice));
		System.out.println("List l:" + diceList.size() +"dice length: " + dice.length);
		System.out.println("Position: " + diceP);
		diceList.remove(diceP);
		String[][] newDice = diceList.toArray(new String[][]{});
		return newDice;
	   }

	  private void changePlayer() {
		  String response1 = "";
		  String response2 = "";
		  
		  System.out.println("next player turn? (y/n)");
		  response1 = in.nextLine();
		  
		 if (response1.equalsIgnoreCase("y")) {
		    if (currentPlayer == playerList.length - 1) { //change of player
				currentPlayer = 0;
				} 
		    	else { 
					currentPlayer++;
		    		}
				
		    System.out.println("/////////////////////");
		    System.out.println("Curren Player: "+String.valueOf(playerList[currentPlayer].getName()));
			System.out.println("Current Brains: "+String.valueOf(playerList[currentPlayer].getScore()));
			System.out.println("Roll Dice? (y/n)");// ask the player if they want to roll dice
				response2 = in.nextLine(); 
				if(response2.equalsIgnoreCase("y")){ // if yes roll dice
				diceRoller(dice);
					}
				
				}
		 	else if(!(response1.equalsIgnoreCase("y"))){ // if not yes try again.
		 		System.out.println("TRY AGAIN");
				 }
				
		}
	
	 private void gameWinner() {
			if(playerList[currentPlayer].getScore() >= 13 || playerList[currentPlayer].getScore()+acumBrains >=13) { // if player has 13 or more brains
				System.out.println(String.valueOf("Player: " + playerList[currentPlayer].getName())+ " Has Won" ); // we display current player name
				System.out.println(String.valueOf("Total Brains: " + playerList[currentPlayer].getScore()+acumBrains + "!")); //we display current player total of brains				
				System.exit(0);
 
				}
			}		

		private void deadPlayer() {
			
			if (acumShots >= 3) { // if player got 3 or more shots
				  numBrains = 0;
				  acumBrains = 0;
				  acumShots = 0;
				  acumFootprint = 0;
				System.out.println("Sorry You have been shot three times!");
				System.out.println("Player " + String.valueOf(playerList[currentPlayer].getName()) + " loose the turn");
				System.out.println("You have " + String.valueOf(playerList[currentPlayer].getScore() +  " total of brains"));
				
				changePlayer(); // we change the player automatically
			 }
	}
}