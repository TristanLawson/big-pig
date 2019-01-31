import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Assn1_16tjl4 {
	//start scanner
	public static final Scanner screen = new Scanner(System.in);
	
	//receives int from user after ">>>"
	public static int userInt() {
		int userNum = 0;
		boolean inputOK = false;
		String dump = null;
		while (!inputOK) {
			try {
				System.out.print(">>> ");
				userNum = screen.nextInt();
				dump = screen.nextLine();
				inputOK = true;
			} catch (InputMismatchException e) {
				dump = screen.nextLine();
				System.out.println("\"" + dump + "\" is not a legal integer, " +
						"please try again!");
			} // end try-catch block
		} // end input loop
		return userNum;
	}
	
	//true if 1, false if any other int
	public static boolean userBool() {
		int bool = userInt();
		if (bool == 1) return true;
		else return false;
	}
	
	//random int in range [0,upper-1]
	public static int randomInt(int upper){
		Random random = new Random();
		return random.nextInt(upper);
	}
	
	public static void welcome() {
		System.out.println("Welcome to the game of PIG");
		System.out.print(
	"	 __,---.__\n"+
    "    __,-'         `-.\n"+
    "   /_ /_,'           \\&\n"+
    "   _,''               \\\n"+
    "  (\")            .    |\n"+
    "    ``--|__|--..-'`.__|\n");
		boolean cont = false;
		while(!cont) {
		System.out.println("Type 1 to play. Type any other number to read the rules.");
		cont = userBool();
		}
		return;
	}
	
	//includes pig rules for re-rolls, returns 0 if turn is over
	public static int rollDice(int turnScore) {
		int d1,d2;
		d1 = randomInt(6)+1;
		d2 = randomInt(6)+1;
		System.out.println("/"+d1+"/ /"+d2+"/");
		if (d1 == d2) {									//increment turnScore, roll again
			if (d1 == 1) turnScore += 25;
			else turnScore += 4*d1;
			System.out.println("Score : "+turnScore);
			System.out.println("rolling again...");
			turnScore = rollDice(turnScore);
		} else if (d1 == 1 || d2 == 1) {				//return 0
			System.out.println("End of turn\n");
			return 0;
		} else {										//increment turnScore, return
			turnScore += (d1 + d2);
			System.out.println("Score : "+turnScore+"\n");
			}
		return turnScore;
	}
	
	public static int compTurn(int score) {
		System.out.println("\n"
				+ "Opponent's turn_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n");
		int turnScore = 0;
		boolean cont = true;
		while (cont) {
			turnScore = rollDice(turnScore);
			if (turnScore == 0) cont = false;
			else if (turnScore + score >= 100) cont = false;
			else if (turnScore >= 40) cont = false;
		}
		score += turnScore;
		return score;
	}
	
	public static int playerTurn(int score) {
		System.out.println("\n"
				+ "Your turn_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n");
		int turnScore = 0;
		boolean cont = true;
		while (cont) {
			turnScore = rollDice(turnScore);
			if (turnScore == 0) cont = false;
			//else ask user if continuing
			else {
				System.out.println("Score this turn : "+turnScore);
				System.out.println("Potential score : "+(turnScore + score));
				System.out.println("Type 1 to roll again. Type any other number to end turn.");
				cont = userBool();
			}
		}
		score += turnScore;
		return score;
	}
	
	public static void printScores(int playerScore, int compScore) {
		System.out.println("Your Score :\t\t" + playerScore);
		System.out.println("Opponent's Score :\t" + compScore);
		return;
	}
	
	//if a player wins, calls finalScreen and returns false, else returns true
	public static boolean checkWinCond(int playerScore, int compScore) {
		if (playerScore >= 100 || compScore >= 100) {
			finalScreen(playerScore,compScore);
			return false;
		} else return true;
	}
	
	public static void finalScreen(int playerScore, int compScore) {
		if (playerScore >= 100) System.out.println("Y O U   W I N !");
		else System.out.println("G A M E   O V E R");
		System.out.println("The final score was : " + playerScore + "-" + compScore);
	}

	//main
	public static void main(String[] args) {
		//initiate variables
		welcome();
		boolean gameCont = true;
		int playerScore = 0;
		int compScore = 0;
		int turn = 1;
		//main loop
		while(gameCont) {
			if (turn == 1) {
				playerScore = playerTurn(playerScore);
				turn = 0;
			} else {
				compScore = compTurn(compScore);
				turn = 1;
			}
			gameCont = checkWinCond(playerScore,compScore);
			if (gameCont) {
				printScores(playerScore,compScore);
				System.out.println("Type 1 to continue. Type any other number to exit.");
				gameCont = userBool();
			}
		}
		//end of game
		System.out.println("\nThank you for playing!");
		screen.close();
	}

}
