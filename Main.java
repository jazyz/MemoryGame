import java.util.*;

 /* This program is a memory game where you match pairs of letters. There are four 
 * modes: easy, medium, hard, and story mode. When you finish each mode, you get a 
 * score. The lower, the better.
 * Name: Jonathan Zhao
 * Date: Oct 30, 2021 */
public class Main {
	
  public static final String RED = "\u001B[31m";
  public static final String GREEN = "\u001B[32m";
  public static final String YELLOW = "\u001B[33m";
  public static final String BLUE = "\u001B[34m";
  public static final String PURPLE = "\u001B[35m";
  public static final String CYAN = "\u001B[36m";
  public static final String WHITE = "\u001B[37m";
  static Scanner sc = new Scanner(System.in);
  static List<Score> storyModeScores = new ArrayList<>();
  static List<Score> scores = new ArrayList<>();

  /* Reads an integer command 
   * Pre: N/A
   * Post: Returns integer between 0 and 7 */
  public static int readCommand() {
    int num;
    String s;
		while (true) {
      System.out.print(WHITE);
			s = sc.nextLine();
			try {
				num = Integer.parseInt(s);
				if (num >= 0 && num <= 7) {
					break;
				} else {
					System.out.println(RED + "Invalid input");
				}
			} catch (Exception e){
				System.out.println(RED + "Invalid input");
			}
			
		}
		
		return num;
  }

  /* Reads the integer of a card the user wants to flip
   * Pre: cards is not null, it contains all numbers of cards that can still be flipped, turn is either "first" or "second"
   * Post: Returns integer that the set of cards contains */
	public static int readInt(Set<Integer> cards, String turn) {
		int num;
    String s;
		System.out.println(WHITE + "Please enter the " + turn + " number that you want to flip.");
		while (true) {
      System.out.print(WHITE);
			s = sc.nextLine();
			try {
				num = Integer.parseInt(s);
				if (cards.contains(num)) {
					break;
				} else {
					System.out.println(RED + "Invalid input");
				}
			} catch (Exception e){
				System.out.println(RED + "Invalid input");
			}
			
		}
		
		return num;
	}

  /* Lets user go back to main menu
   * Pre: N/A
   * Post: User is back in main menu */
  public static void goToMainMenu() {
    int command;
    System.out.println(RED + "Type 7 to go back to main menu." + WHITE);
    while (true) {
      command = readCommand();
      if (command == 7) {
        break;
      }
    }
  }

  /* Pauses execution for an input amount of milliseconds
   * Pre: ms >= 0
   * Post: Execution paused for an input amount of milliseconds */
	public static void pause(int ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			
		}
	}

  /* Clears console
   * Pre: N/A
   * Post: Clears the console */
  public static void clearConsole() {
    System.out.print("\033[H\033[2J");
		System.out.flush();
  }

  
  /* Prints the scoreboard in sorted order
   * Pre: scores is not null, it is a list of Score objects
   * Post: Prints the scoreboard in sorted order */
  public static void printSortedScoreboard(List<Score> scores) {
    int place = 1;
    clearConsole();
    Collections.sort(scores);
    for (Score a : scores) {
      System.out.println(WHITE + place + " " + a.name + " " + a.score);
      place++;
    }
    goToMainMenu();
  }

  /* Prints the instructions
   * Pre: N/A
   * Post: Prints the instructions */
  public static void printInstructions() {
    clearConsole();
    System.out.println(WHITE + "This is a memory game.\nChoose two numbers of the cells you want to flip and try to match the letters underneath.");
    goToMainMenu();
  }

  /* Prints 60 stars
   * Pre: N/A
   * Post: Prints 60 stars */
  public static void printStars() {
    System.out.print(WHITE);
    for (int i = 0; i < 60; i++) {
      System.out.print("*");
    }
    System.out.println();
  }

  /* Prints the main menu
   * Pre: N/A
   * Post: Main menu is printed */
  public static void printMainMenu() {
    clearConsole();
    printStars();
    for (int j = 0; j < 15; j++) {
      System.out.print(" ");
    }
    System.out.println(PURPLE + "Welcome to Memory Madness!");
    printStars();
    pause(1000);
    System.out.println(CYAN + "Main Menu\n");
    System.out.println(WHITE + "Type 0 to view instructions\n");
    System.out.println(GREEN + "Type 1 to play easy mode");
    System.out.println(BLUE + "Type 2 to play medium mode");
    System.out.println(RED + "Type 3 to play hard mode");
    System.out.println(YELLOW + "Type 4 to play story mode\n");
    System.out.println(PURPLE + "Type 5 to view the regular modes scoreboard");
    System.out.println(YELLOW + "Type 6 to view story mode scoreboard\n");
    System.out.println(RED + "Type 7 to quit" + WHITE);
  }
	
  /* Initializes all necessary background arrays and the set of cards
   * Pre: n is a positive even integer, cards is not null, display is not null, hidden is not null and initial is not null.
   * Post: all elements in display are between 1 and n*n, all elements in hidden are letters, cards contains all integers from 1 to n*n */
  public static void initialize(Set<Integer> cards, int[][] display, char[][] hidden, char[] initial, int n) {
		int x = 1;
    int k = 1;
		for (int i = 1; i <= n*n; i+=2) {
      // Adding two of the same letters to an initial array
			initial[i] = (char) (k + '@');
			initial[i+1] = (char) (k + '@');
			k++;
		}
    // Randomly shuffling the initial array
		for (int i = n*n; i >= 2; i--) {
			int idx = (int) ((i-1) * Math.random() + 1);
			char temp = initial[idx];
			initial[idx] = initial[i];
			initial[i] = temp;
		}
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				display[i][j] = x;
				hidden[i][j] = initial[x];
				cards.add(x);
				x++;
			}
		}
  }

  /* Reveals a hidden card and prints the updated board
   * Pre: 1 <= i <= n, 1 <= j <= n, n is even positive integer, display and hidden are both declared as (n+1)x(n+1) size 2D array, all elements in display are between 1 and n*n, all elements in hidden are letters
   * Post: Updated board is printed */
  public static void printHidden(int i, int j, int n, int[][] display, char[][] hidden) {
			display[i][j] = -1;
			printBoard(display, hidden, n);
      pause(300);
  }

  /* Prints out the current board
   * Pre: n is even positive integer, display and hidden are both declared as (n+1)x(n+1) size 2D array, all elements in display are between 1 and n*n, all elements in hidden are letters
   * Post: Prints out the current board */
	public static void printBoard(int[][] display, char[][] hidden, int n) {
		clearConsole();
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (display[i][j] == 0) { // print the hidden letter which has already been matched
					System.out.printf("%-15s", GREEN + hidden[i][j]);
				} else if (display[i][j] == -1){ // print the hidden letter that is being revealed
					System.out.printf("%-15s", BLUE + hidden[i][j]);
				} else { // print the display number 
          System.out.printf("%-15s", WHITE + display[i][j]);
        }
			}
			for (int j = 1; j <= 5; j++) {
				System.out.println();
			}
		}
	}

  /* Runs the main game
   * Pre: n is a positive even integer
   * Post: The game is finished */
	public static int runGame(int n, boolean isStoryMode) {
		Set<Integer> cards = new HashSet<Integer>();
		int[][] display = new int[n+1][n+1];
		char[][] hidden = new char[n+1][n+1];
		char[] initial = new char[n*n+1];
		int cardsMatched = 0;
		int moves = 0;
    int a, ai, aj, b, bi, bj;
    long endTime, startTime, time;
    int score;
    String name = "";

    initialize(cards, display, hidden, initial, n);
    clearConsole();
    if (!isStoryMode) {
      System.out.println(WHITE + "Enter your name:");
      name = sc.nextLine();
    }
    startTime = System.nanoTime();
		while (true) {
			if (cardsMatched == n*n) {
				break;
			}
			printBoard(display, hidden, n);
			moves++;
			a = readInt(cards, "first");
      // Convert the input integer a to coordinates in a 2D array
			ai = 1 + (a-1)/n;
			aj = (a-1) % n + 1;
      printHidden(ai, aj, n, display, hidden);
      // a cannot be input again as the second number, as it has already been revealed
			cards.remove(a);
			b = readInt(cards, "second");
      // Convert the input integer b to coordinates in a 2D array
			bi = 1 + (b-1)/n;
			bj = (b-1) % n + 1;
      printHidden(bi, bj, n, display, hidden);
      if (hidden[ai][aj] == hidden[bi][bj]) {
				cardsMatched+=2;
        // b cannot be input again, as it is already found
				cards.remove(b);
        display[ai][aj] = 0;
				display[bi][bj] = 0;
				System.out.println(GREEN + "You got it right.");
			} else {
        // Cards did not match, set the two input numbers back to their original values
				display[ai][aj] = a;
				display[bi][bj] = b;
        // a can now be input again
				cards.add(a);
				System.out.println(RED + "You got it wrong.");
			}
			pause(1000);
		}
    endTime = System.nanoTime();
    time = (endTime - startTime) / 1000000000;
    score = (int) (time) + 2*moves;
    if (n == 2) {
      score *= 40;
    } else if (n == 4) {
      score *= 5;
    }
    printBoard(display, hidden, n); 
		System.out.println(WHITE + "Your score was " + score);
    if (!isStoryMode) {
      scores.add(new Score(name, score));
    }
    
    pause(3000);
    return score; 
 	}

  /* Runs the story mode game
   * Pre: n is a positive even integer, targetScore allows level to be completed
   * Post: Story mode game is finished */
  public static int storyModeGame(int n, int targetScore) {
    int totalScore = 0;
    int score;
    while (true) {
      score = runGame(n, true);
      totalScore += score;
      if (score <= targetScore) {
        System.out.println(GREEN + "You completed the level!");
        break;
      } else {
        System.out.println(RED + "Your score was too high. Try again.");
        pause(3000);
      }
    }
    pause(3000);
    return totalScore;
  }

  /* Runs story mode
   * Pre: N/A
   * Post: Story mode is complete */
  public static void storyMode() {
    String name;
    int totalScore = 0;
    clearConsole();
    System.out.println(WHITE + "Enter your name:");
    name = sc.nextLine();
    System.out.println("This is story mode. \nYour score is the total score from all the games you play. \nThe lower the score, the better.");
    pause(5000);
    clearConsole();
    System.out.println(WHITE + "Level 1: Get a score of 800 or less to pass.\nYour score is 40 * (the number of seconds you take + 2 * the number of moves made).");
    pause(5000);
    totalScore += storyModeGame(2, 800);
    clearConsole();
    System.out.println(WHITE + "Level 2: Get a score of 1000 or less to pass.\nYour score is 5 * (the number of seconds you take + 2 * the number of moves made)");
    pause(5000);
    totalScore += storyModeGame(4, 1000);
    clearConsole();
    System.out.println(WHITE + "Level 3: Get a score of 1200 or less to pass.\nYour score is (the number of seconds you take + 2 * the number of moves made)");
    pause(5000);
    totalScore += storyModeGame(6, 1200);
    clearConsole();
    System.out.println(GREEN + "Congratulations! You completed the story with a score of " + totalScore + ".");
    pause(5000);
    storyModeScores.add(new Score(name, totalScore));
  }

  /* Runs easy mode
   * Pre: N/A
   * Post: easy mode is complete */
  public static void easyMode() {
    clearConsole();
    System.out.println("This is easy mode. \nYour score is 40 * (the number of seconds you take + 2 * the number of moves made)");
    pause(5000);
    runGame(2, false);
  }
 
  /* Runs medium mode
   * Pre: N/A
   * Post: medium mode is complete */
  public static void mediumMode() {
    clearConsole();
    System.out.println("This is medium mode. \nYour score is 5 * (the number of seconds you take + 2 * the number of moves made)");
    pause(5000);
    runGame(4, false);
  }

  /* Runs hard mode
   * Pre: N/A
   * Post: hard mode is complete */
  public static void hardMode() {
    clearConsole();
    System.out.println("This is hard mode. \nYour score is (the number of seconds you take + 2 * the number of moves made)");
    pause(5000);
    runGame(6, false);
  }
 
  /* Runs the game
   * Pre: N/A
   * Post: the game is complete */
	public static void main(String[] args) {
    int command;
    while (true) {
      printMainMenu();
      command = readCommand();
      if (command == 0) {
        printInstructions();
      } else if (command == 1) {
        easyMode();
      } else if (command == 2) {
        mediumMode();
      } else if (command == 3) {
        hardMode();
      } else if (command == 4) {
        storyMode();
      } else if (command == 5) {
        printSortedScoreboard(scores);
      } else if (command == 6) {
        printSortedScoreboard(storyModeScores);
      } else if (command == 7) {
        break;
      }
    }
    sc.close();
	}

}
