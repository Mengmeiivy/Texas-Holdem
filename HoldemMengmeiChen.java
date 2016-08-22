
//Java Project (Spring 2013)
//Project Name: Texas Hold'em
//Professor Name: Evan Korth
//Student Name: Mengmei Chen

/*This is a complete Texas Hold'em poker game. 
 *All the codes are original.
 *Though some rules I use here might be a little different from the current form of the game, main principles are the same.
 *Rules are available at the beginning of the program.
 *I used sleep() method in order to emulate the real gaming experience and to print out the information slowly for players to read.
 *I used a three-dimensional array to keep track of each player's cards with their suit and number
 *in order to evaluate cards conveniently
 *Somehow, I spent tremendous time figuring out the right algorithm, especially for the betting part, 
 *so for now this game is displayed in the console. 
 *My next step would be adding exception handling, displaying the game using GUI and making it an online game.
 *ENJOY!!!
 */

import java.util.Scanner;

public class HoldemMengmeiChen
{
	public static void main(String[] args) 
	{
		
		//Welcome and Rules!
		welcomeAndRules();
		
		//Start the game!
		waitSecond(1000);
		System.out.println();
		System.out.println("Now, let's start the game!");
		
		//get number of players
		waitSecond(1000);
		int numberOfPlayer = getNumberOfPlayers();
		//create a string array of players' names
		String[] names = new String[numberOfPlayer];
		//get each player's name
		waitSecond(1000);
		getPlayersName(names, numberOfPlayer);
		
		//create a two-dimensional integer array of player's information (order of cards, current money and pot money)
		int[][] playerInfo = new int[numberOfPlayer][3];
		//set all the orders to 0, all the starting money to 1000 dollars, and the pot money to 0
		setPlayerInfo(playerInfo, numberOfPlayer);

		//create a three-dimensional boolean array matrix to keep track of each player's cards, 
		//folding condition and participating condition
		boolean[][][] playerDeck = new boolean[numberOfPlayer][4][14];
		//set false to players' card slots, true to "unfold" opton, and true to "participation in this round" option
		setPlayerDeck(playerDeck, numberOfPlayer);

		//create a deck of cards
		boolean[][] deck = new boolean[4][13];	
		
		//create a string array to store the cards players get
		String[] cards = new String[numberOfPlayer];
		for (int i = 0 ; i < numberOfPlayer; i++)
			cards[i] = "";
		int cardType = 0;
		int cardNumber = 0;
		
		//create an array to store the money deducted already for each player
		int[] moneyDeductedAlready = new int[numberOfPlayer];
			for (int i = 0; i < numberOfPlayer; i++)
				moneyDeductedAlready[i] = 0;
		//create current bet
		int bet = 0;
		
		//at first, everyone is eligible to play since everyone has starting money of 1000 dollars
		int currentPlayer = numberOfPlayer;
		
		//continue the game only if two or more players still have money and the players want to continue
		boolean nextRound = true;
		while ((currentPlayer > 1) && (nextRound == true))
		{
			//reset the deck
			resetDeck(deck);
			//reset the playerDeck
			resetPlayerDeck(playerDeck, numberOfPlayer);
			//reset the cards players get
			for (int i = 0 ; i < numberOfPlayer; i++)
				cards[i] = "";
			cardType = 0;
			cardNumber = 0;
			//reset orders of cards
			for (int i = 0; i < numberOfPlayer; i++)
				playerInfo[i][0] = 0;
			//reset pot money
			playerInfo[0][2] = 0;
			//reset money deducted already and bet
			for (int i = 0; i < numberOfPlayer; i++)
				moneyDeductedAlready[i] = 0;
			bet = 0;
			
			//begin a new round
			System.out.println();
			waitSecond(1500);
			System.out.println("A new round begins!");
			
			//Tell the players the card deck is shuffled.
			waitSecond(1000);
			System.out.println("Shuffling the cards...");
			waitSecond(1000);
			System.out.println("...");
			waitSecond(1000);
			System.out.println("...");
			
			//give each player two cards
			waitSecond(1000);
			System.out.println("Dealing each person two cards (hole) now...");
			waitSecond(1000);
			System.out.println("...");
			waitSecond(1000);
			System.out.println("...");
			waitSecond(1000);
			giveHoleCards(playerDeck, deck, numberOfPlayer);
			//find and assign the cards each of them gets 
			findCards(playerDeck, cards, numberOfPlayer, cardType, cardNumber);
			//print the cards according to player's request
			printCards(names, cards);
			
			//first round betting
			int playerIndex1 = numberOfPlayer;
			int playerIndex2 = -1;
			bet = betting(names, playerInfo, playerDeck, moneyDeductedAlready, playerIndex1, playerIndex2, 
					bet, numberOfPlayer);
			//print the information
			printBettingInformation(numberOfPlayer, names, playerInfo, playerDeck);
			
			//assign the three flop cards to each player
			System.out.println();
			waitSecond(1000);
			System.out.println("Displaying three common cards (flop) ...");
			waitSecond(1000);
			System.out.println("...");
			waitSecond(1000);
			System.out.println("...");
			waitSecond(1000);
			giveFlopCard(deck, numberOfPlayer, playerDeck, cardType, cardNumber, cards);
			//print the cards according to player's request
			printCards(names, cards);
			
			//second round betting
			bet = betting(names, playerInfo, playerDeck, moneyDeductedAlready, playerIndex1, playerIndex2, 
					bet, numberOfPlayer);
			//print the information
			printBettingInformation(numberOfPlayer, names, playerInfo, playerDeck);
			
			//assign the turn card to each player
			System.out.println();
			waitSecond(1000);
			System.out.println("Displaying another common card (turn) ...");
			waitSecond(1000);
			System.out.println("...");
			waitSecond(1000);
			System.out.println("...");
			waitSecond(1000);
			giveTurnCard(deck, numberOfPlayer, playerDeck, cardType, cardNumber, cards);
			//print the cards according to player's request
			printCards(names, cards);
		
			//third round betting
			bet = betting(names, playerInfo, playerDeck, moneyDeductedAlready, playerIndex1, playerIndex2, 
					bet, numberOfPlayer);
			//print the information
			printBettingInformation(numberOfPlayer, names, playerInfo, playerDeck);
			
			//assign the river card to each player
			System.out.println();
			waitSecond(1000);
			System.out.println("Displaying the last common card (river) ...");
			waitSecond(1000);
			System.out.println("...");
			waitSecond(1000);
			System.out.println("...");
			waitSecond(1000);
			giveTurnCard(deck, numberOfPlayer, playerDeck, cardType, cardNumber, cards);
			//print the cards according to player's request
			printCards(names, cards);
			
			//fourth round betting
			bet = betting(names, playerInfo, playerDeck, moneyDeductedAlready, playerIndex1, playerIndex2, 
					bet, numberOfPlayer);
			//print the information
			printBettingInformation(numberOfPlayer, names, playerInfo, playerDeck);
			
			
			//evaluate players' cards
			for (int b = 0; b < numberOfPlayer; b++)
			{
				if ((playerDeck[b][1][13]) && (playerDeck[b][0][13]))
					evaluateOrder(playerDeck[b], playerInfo, b);
			}
			
			//calculate and print this's round winning information
			int maxOrder = 0;
			for (int c = 0; c < numberOfPlayer; c++)
			{
				if (playerInfo[c][0] > maxOrder)
					maxOrder = playerInfo[c][0];
			}
			
			int numberOfWinner = 0;
			String cardsType = "";
			System.out.println();
			System.out.println("Showdown time!!! ");
			waitSecond(1000);
			System.out.println("...");
			waitSecond(1000);
			System.out.println("...");
			
			if (maxOrder > 0)
			{
				for (int c = 0; c < numberOfPlayer; c++)
				{
					if (playerInfo[c][0] == maxOrder)
						numberOfWinner++;
				}
				
				for (int c = 0; c < numberOfPlayer; c++)
				{
					if (playerInfo[c][0] == maxOrder)
					{
						playerInfo[c][1] += playerInfo[0][2]/numberOfWinner;
						cardsType = decideCardsTypeByOrder(playerInfo[c][0]);
						waitSecond(2000);
						System.out.println(names[c] + " wins by "  + cardsType + ": " + cards[c] + "and gets " + 
								playerInfo[0][2]/numberOfWinner + " dollars!");
						waitSecond(2000);
						System.out.println("Now you have " + playerInfo[c][1] + " dollars!");
					}
				}
			}
			else
			{
				waitSecond(1000);
				System.out.println("Everyone folds, so no one wins.");
			}
			
			//print the this round's final results for everyone
			System.out.println();
			waitSecond(1000);
			System.out.println("This round's results: ");
			for (int d = 0; d < numberOfPlayer; d++)
				System.out.println(names[d] + " now has " + playerInfo[d][1] + " dollars.");
			
			//decide how many people are eligible for playing next round
			int numberOfPeopleWithoutMoney = 0;
			for (int a = 0; a < numberOfPlayer; a++)
			{
				if (playerInfo[a][1] == 0)
				{
					numberOfPeopleWithoutMoney++;
					//they cannot play anymore 
					playerDeck[a][1][13] = false;
				}
			}
			//number of players who are eligible to play next round
			currentPlayer = numberOfPlayer - numberOfPeopleWithoutMoney;
			
			//print who are eligible for the next round
			System.out.println();
			waitSecond(1000);
			System.out.println("Players who are eligible for the next round: ");
			for (int e = 0; e < numberOfPlayer; e++)
			{
				if (playerInfo[e][1] > 0)
					System.out.println((e+1) + ". " + names[e]);
			}
			
			//if two or more players are eligible to play next round, ask them if they want to continue
			if (currentPlayer > 1)
			{
				System.out.println("Do you want to play another round? (y/n)");
				Scanner continuePlay = new Scanner(System.in);
				String continuePlayString = continuePlay.next();
				if (continuePlayString.equals("n"))
					//end the game if they answer no
					nextRound = false;
			}
			
		}//the game (while loop) ends
		
		//prints the final results
		int winnerIndex = 0;
		System.out.println();
		waitSecond(1000);
		System.out.println("Game Over!");
		
		//no winner if no one has money left
		if (currentPlayer == 0)
		{
			waitSecond(1000);
			System.out.println("No one wins at last!");
		}
		//print the final winner if the game ends naturally with only one player left with money
		if (currentPlayer == 1)
		{
			for (int f = 0; f < numberOfPlayer; f++)
			{	
				if (playerInfo[f][1] > 0)
					winnerIndex = f;
			}
			waitSecond(1000);
			System.out.println(names[winnerIndex] + " is the final winner!!!");
		}
		//print the potential winners who still have money when the game is ended by players
		if (currentPlayer > 1)
		{
			System.out.println("Potential Winners: ");
			for (int i = 0; i < numberOfPlayer; i++)
			{
				if (playerDeck[i][1][13] == true)
				{
					System.out.println(names[i] + ": " + playerInfo[i][1] + " dollars.");
				}
			}
		}
		
		//farewell!
		waitSecond(1000);
		System.out.println("Drive Carefully and Come Back Soon :) ");
	}//main ends

	public static void waitSecond(int millisecond)
	{
		try
		{
		Thread.sleep(millisecond);
		}
		catch (InterruptedException e)
		{}
	}
		
	public static void welcomeAndRules()
	{
		System.out.println("Welcome to Hold'em!");
		waitSecond(1000);
		System.out.println("Do you want to see the rules? (y/n): ");
		Scanner input2 = new Scanner(System.in);
		String answer = input2.next();
		if (answer.equals("y"))
		{
			System.out.println("Texas Hold'em is a popular poker game for 2-9 people." +
					"\nYour goal is to win as much as money as possible by betting." +
					"\nAt the beginning of this game, each of you would be given 1000 dollars to start with." +
					"\nThen the dealer would draw two cards for each of you. These two cards are known as the hole cards." +
					"\nAfter everyone sees their own hole cards, the first round of betting takes place." +
					"\nThen three common cards known as the flop are drawn and displayed to all" +
					" and the second round of betting takes place." +
					"\nThen the fourth common card called the turn is drawn and displayed to all" +
					" and the third round of betting takes place." +
					"\nFinally, the fifth common card called the river is drawn and displayed to all" +
					" and the final round of betting takes place." +
					"\nIn each round of betting, you can choose to bet any money " +
					"no smaller than the current bet" +
					" and no bigger than your own property." +
					"\nUsually, how much you bet depends on how you evaluate your cards and others' cards" +
					"\nYou can also choose to fold if you don't feel like winning." +
					"\nRemember: if you are forced to fold because you don't have enough money to check, you will be given back the money you have bet in all the rounds of betting before." +
					"\nHowever, if you choose to fold, then you will lose all the money you have bet." +
					"\nIn a round, whenever someone raises the bet, all other players, in a round sequence, would be asked to either check/raise or fold." +
					"\nWhen asked again because someone raises, you can now choose to raise (even if you didn't raise just now) and generate another round of betting." +
					"\nA round of betting ends when everyone either folds or achieves the same bet." +
					"\nIn the end, the cards of those who have not folded their cards yet would be evaluated." +
					"\nPlayer(s) who have the highest rank would be given the pot money gathered from all others." +
					"\nTherefore, try to raise if you have good cards and be careful to keep betting if you have bad hands." +
					"\nHowever, when you have great hands, don't be too bold and obvious, otherwise others would fold instead of check/call." +
					"\nSimilarly, when you have terrible hands, you can still try bluffing to trick others to fold and decrease the competition at last.");
			System.out.println();
			System.out.println("The rank of cards is as follows (from highest to lowest with one example following each rank):" + 
					"\n10. Royal Flush (10, 11, 12, 13, 1 with the same suit)" + 
					"\n9. Straight Flush (4, 5, 6, 7, 8 with the same suit)" + 
					"\n8. Four of a Kind (5, 5, 5, 5, 9)" +
					"\n7. Full House (12, 12, 12, 3, 3)" + 
					"\n6. Flush (4, 6, 9, 10, 13 with the same suit)" + 
					"\n5. Straight (3, 4, 5, 6, 7)" + 
					"\n4. Three of a Kind (7, 7, 7, 10, 11)" +
					"\n3. Two Pairs (3, 3, 9, 9, 12)" + 
					"\n2. One Pair (8, 8, 4, 1, 3)" +
					"\n1. High Card/Nothing (2, 5, 6, 9, 12)" + 
					"\nGOOD LUCK!!!");
		}
	}
	
	public static int getNumberOfPlayers()
	{
		System.out.print("How many people are playing this game?(2-9) ");
		Scanner input = new Scanner(System.in);
		return input.nextInt();
	}
	
	public static void getPlayersName(String[] names, int numberOfPlayer)
	{
		System.out.println("Please type in each player's name below.");
		for (int i = 1; i <= numberOfPlayer; i++)
		{
			Scanner input6 = new Scanner(System.in);
			System.out.print("Player" + i + ": ");
			names[i-1] = input6.nextLine();
		}
	}
		
	public static void setPlayerInfo(int[][] playerInfo, int numberOfPlayer)
	{
		for (int i = 0; i < numberOfPlayer; i++)
		{
			playerInfo[i][0] = 0;
			playerInfo[i][1] = 1000;
		}
		playerInfo[0][2] = 0;
	}
		
	public static void setPlayerDeck(boolean[][][] playerDeck, int numberOfPlayer)
	{
		for (int i = 0; i < numberOfPlayer; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				for (int k = 0; k < 13; k++)
				playerDeck[i][j][k] = false;
			}
			
			playerDeck[i][0][13] = true;
			playerDeck[i][1][13] = true;
		}
	}
	
	public static void resetDeck(boolean[][] deck)
	{
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 13; j++)
				deck[i][j] = true;
		}
	}
	
	public static void resetPlayerDeck(boolean[][][] playerDeck, int numberOfPlayer)
	{
		for (int i = 0; i < numberOfPlayer; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				for (int k = 0; k < 13; k++)
				playerDeck[i][j][k] = false;
			}
			playerDeck[i][0][13] = true;
			
		}
	}
	
	public static void giveHoleCards(boolean[][][] playerDeck, boolean[][] deck, int numberOfPlayer)
	{
		for (int i = 0; i < numberOfPlayer; i++)
		{
			if (playerDeck[i][1][13] == true)
			{
				for (int m = 0; m < 2; m++)
				{
					int k = (int)(Math.random()*3);
					int j = (int)(Math.random()*13);
					while (deck[k][j] == false)
					{
						k = (int)(Math.random()*3);
						j = (int)(Math.random()*13);
					}
						
					playerDeck[i][k][j] = true;
					deck[k][j] = false;
				}
			}
		}
	}
	
	public static void findCards(boolean[][][] playerDeck, String[] cards, int numberOfPlayer, 
			int cardType, int cardNumber)
	{
		for (int i = 0; i < numberOfPlayer; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				for (int k = 0; k < 13; k++)
				{
					if (playerDeck[i][j][k] == true)
					{
						cardType = j;
						cardNumber = k;
						switch (cardType)
						{
						case 0: cards[i] += "spade";
								break;
						case 1: cards[i] += "diamond";
								break;
						case 2: cards[i] += "heart";
								break;
						case 3: cards[i] += "club";
						}	
						
						cardNumber ++;
						cards[i] += cardNumber + " ";
					}
				}
			}
		}
	}
	
	public static void printCards(String[] names, String[] cards)
	{
		boolean keepgoing = true;
		while (keepgoing == true)
		{
			System.out.println();
			System.out.println("To those who have not folded: " +
					"Please type in your player number to see your cards and the possible common cards (0 for continuing the game): ");
			Scanner input3 = new Scanner(System.in);
			int playerNumber = input3.nextInt();
			String playerNumberAsString = playerNumber + "";
			
			if (playerNumberAsString.equals("0"))
				keepgoing = false;
			else
				System.out.println(names[playerNumber-1] + ": " + cards[playerNumber-1]);
		}
	}
	
	public static void printBettingInformation(int numberOfPlayer, String[] names, int[][] playerInfo, boolean[][][] playerDeck)
	{
		waitSecond(1000);
		System.out.println("The amount of money each player has now:");
		for (int i = 0; i < numberOfPlayer; i++)
		{
			if (playerDeck[i][1][13] == true)
			{
				System.out.println(names[i] + ": " + playerInfo[i][1] + " dollars.");
			}
		}
	}
	
	public static int betting(String[] names, int[][]playerInfo, boolean[][][]playerDeck, 
			int[] moneyDeductedAlready, int playerIndex1, int playerIndex2, int bettingMoney, int numberOfPlayer)
	{
		//assign value to playerIndex2 for the next time
		int playerIndex1Next = playerIndex1;
		int playerIndex2Next;
		if (playerIndex1 == 0)
			playerIndex2Next = numberOfPlayer;
		else
			playerIndex2Next = playerIndex1 - 1;
		
		int bettingMoneyTemp = 0;
		
		//ask the players between two indices to bet
		if (playerIndex2 < playerIndex1)
		{
			for (int i = playerIndex2 + 1; i < playerIndex1; i++)
			{
				if ((playerDeck[i][0][13] == true) && (playerDeck[i][1][13] == true))
				{
					waitSecond(1000);
					System.out.println();
					System.out.println(names[i] + ", you have " + playerInfo[i][1] + " dollars now.");
					
					if (playerInfo[i][1] < (bettingMoney - moneyDeductedAlready[i]))
					{
						//can't play this round anymore
						playerDeck[i][0][13] = false;
						//take his own money back
						playerInfo[i][1] += moneyDeductedAlready[i];
						//deduct his money from pot money
						playerInfo[0][2] -= moneyDeductedAlready[i];
						System.out.println("Sorry, " + names[i] + ", you don't have enough money to continue.");
						System.out.println("You got your money back for this round. Now you have " + playerInfo[i][1] + 
								" dollars. Please wait until next round!");
					}
					else
					{
						if (playerInfo[i][1] == 0)
						{
							System.out.println("You are All in. Skip this step.");
						}
						else
						{
							System.out.println("Bet: " + bettingMoney); 
							System.out.println("You have already bet: " + moneyDeductedAlready[i]);
							System.out.println("Now, you need to bet/add at least " +
									(bettingMoney - moneyDeductedAlready[i]) + " dollars. " +
											"How much do you want to bet/add? (-1 to fold)");
							Scanner input4= new Scanner(System.in);
							bettingMoneyTemp = input4.nextInt();
							String bettingMoneyAsString = bettingMoneyTemp + "";
							int totalMoney = bettingMoneyTemp + moneyDeductedAlready[i];
							
							boolean stop = false;
							while (((totalMoney < bettingMoney)||(bettingMoneyTemp > playerInfo[i][1])||
									(bettingMoneyTemp == -1))&&(stop == false))
							{
								if (bettingMoneyAsString.equals("-1"))
								{
									playerDeck[i][0][13] = false;
									System.out.println(names[i] + " folded.");
									stop = true;
								}
								else
								{	
									waitSecond(1000);
									System.out.println("Sorry, you only have " + playerInfo[i][1] + " dollars " + "and you must bet/add " +
											"at least " + (bettingMoney - moneyDeductedAlready[i]) + " dollars. How much would you like" +
													" to bet/add? (-1 to fold) ");
									Scanner input5= new Scanner(System.in);
									bettingMoneyTemp = input5.nextInt();
									bettingMoneyAsString = bettingMoneyTemp + "";
									totalMoney = bettingMoneyTemp + moneyDeductedAlready[i];
								}
							}
							
							if (totalMoney == bettingMoney)
							{	
								playerInfo[i][1] -= bettingMoneyTemp;
								playerInfo[0][2] += bettingMoneyTemp;
								moneyDeductedAlready[i] += bettingMoneyTemp;
								System.out.println(names[i] + " checked/called.");
								
							}
							
							if (totalMoney > bettingMoney)
							{
								playerInfo[i][1] -= bettingMoneyTemp;
								playerInfo[0][2] += bettingMoneyTemp;
								bettingMoney = totalMoney;
								moneyDeductedAlready[i] += bettingMoneyTemp;
								playerIndex1Next = i;
								System.out.println();
								waitSecond(500);
								System.out.println(names[i] + " raised the bet to " + bettingMoney + " dollars!!!");
							}	
						}
						
					}
							
				}
	
			}
			System.out.println();
		}
		
		
		//ask the player after playerIndex2 and then before playerIndex1
		if (playerIndex2 >= playerIndex1)
		{
			if (playerIndex2 <= (numberOfPlayer - 2))
			{
				for (int i = (playerIndex2 + 1); i <= (numberOfPlayer - 1); i++)
				{
					if ((playerDeck[i][0][13] == true) && (playerDeck[i][1][13] == true))
					{
						waitSecond(1000);
						System.out.println();
						System.out.println(names[i] + ", you have " + playerInfo[i][1] + " dollars now.");
						
								if (playerInfo[i][1] < (bettingMoney - moneyDeductedAlready[i]))
								{
									//can't play this round
									playerDeck[i][0][13] = false;
									//take his own money back
									playerInfo[i][1] += moneyDeductedAlready[i];
									//deduct his money from pot money
									playerInfo[0][2] -= moneyDeductedAlready[i];
									System.out.println("Sorry, " + names[i] + ", you don't have enough money to continue.");
									System.out.println("You got your money back for this round. Now you have " + playerInfo[i][1] + 
											" dollars. Please wait until next round!");
								}
								else
								{
									if (playerInfo[i][1] == 0)
									{
										System.out.println("You are All in. Skip this step.");
									}
									else
									{
										System.out.println("Bet: " + bettingMoney); 
										System.out.println("You have already bet: " + moneyDeductedAlready[i]);
										System.out.println(names[i] + ", you need to bet/add at least " +
												(bettingMoney - moneyDeductedAlready[i]) + " dollars. " +
														"How much do you want to bet/add? (-1 to fold)");
										Scanner input4= new Scanner(System.in);
										bettingMoneyTemp = input4.nextInt();
										String bettingMoneyAsString = bettingMoneyTemp + "";
										int totalMoney = bettingMoneyTemp + moneyDeductedAlready[i];
									
										boolean stop = false;
										while (((totalMoney < bettingMoney)||(bettingMoneyTemp > playerInfo[i][1])||
												(bettingMoneyTemp == -1))&&(stop == false))
										{
											if (bettingMoneyAsString.equals("-1"))
											{
												playerDeck[i][0][13] = false;
												System.out.println(names[i] + " folded.");
												stop = true;
											}
											else
											{	
												waitSecond(1000);
												System.out.println("Sorry, you only have " + playerInfo[i][1] + " dollars " + "and you must bet/add" +
														" at least " + (bettingMoney - moneyDeductedAlready[i]) + " dollars. How much would you like" +
																" to bet/add? (-1 to fold) ");
												Scanner input5= new Scanner(System.in);
												bettingMoneyTemp = input5.nextInt();
												bettingMoneyAsString = bettingMoneyTemp + "";
												totalMoney = bettingMoneyTemp + moneyDeductedAlready[i];
											}
										}
										
										if (totalMoney == bettingMoney)
										{	
											playerInfo[i][1] -= bettingMoneyTemp;
											playerInfo[0][2] += bettingMoneyTemp;
											moneyDeductedAlready[i] += bettingMoneyTemp;
											System.out.println(names[i] + " checked/called.");
										}
										
										if (totalMoney > bettingMoney)
										{
											playerInfo[i][1] -= bettingMoneyTemp;
											playerInfo[0][2] += bettingMoneyTemp;
											bettingMoney = totalMoney;
											moneyDeductedAlready[i] += bettingMoneyTemp;
											playerIndex1Next = i;
											System.out.println();
											waitSecond(500);
											System.out.println(names[i] + " just raised the bet to " + bettingMoney + " dollars!");
										}
									}
									
								}
								
				
							
						
					}
				}
				System.out.println();
			}
			
			if (playerIndex1 >= 1)
			{
				for (int i = 0; i < playerIndex1; i++)
				{
					if ((playerDeck[i][0][13] == true) && (playerDeck[i][1][13] == true))
					{
						waitSecond(1000);
						System.out.println();
						System.out.println(names[i] + ", you have " + playerInfo[i][1] + " dollars now.");
						
						if (playerInfo[i][1] < (bettingMoney - moneyDeductedAlready[i]))
						{
							//can't play this round
							playerDeck[i][0][13] = false;
							//take his own money back
							playerInfo[i][1] += moneyDeductedAlready[i];
							//deduct his money from pot money
							playerInfo[0][2] -= moneyDeductedAlready[i];
							System.out.println("Sorry, " + names[i] + ", you don't have enough money to continue.");
							System.out.println("You got your money back for this round. Now you have " + playerInfo[i][1] + 
									" dollars. Please wait until next round!");
						}
						else
						{
							if (playerInfo[i][1] == 0)
							{
								System.out.println("You are All in. Skip this step.");
							}
							else
							{	
								System.out.println("Bet: " + bettingMoney); 
								System.out.println("You have already bet: " + moneyDeductedAlready[i]);
								System.out.println(names[i] + ", you need to bet/add at least " +
										(bettingMoney - moneyDeductedAlready[i]) + " dollars. " +
												"How much do you want to bet/add? (-1 to fold)");
								Scanner input4= new Scanner(System.in);
								bettingMoneyTemp = input4.nextInt();
								String bettingMoneyAsString = bettingMoneyTemp + "";
								int totalMoney = bettingMoneyTemp + moneyDeductedAlready[i];
								
								boolean stop = false;
								while (((totalMoney < bettingMoney)||(bettingMoneyTemp > playerInfo[i][1])||
										(bettingMoneyTemp == -1))&&(stop == false))
								{
									if (bettingMoneyAsString.equals("-1"))
									{
										playerDeck[i][0][13] = false;
										System.out.println(names[i] + " folded.");
										stop = true;
									}
									else
									{	
										waitSecond(1000);
										System.out.println("Sorry, you only have " + playerInfo[i][1] + " dollars " + "and you must bet/add " +
												"at least " + (bettingMoney - moneyDeductedAlready[i]) + " dollars. How much would you like" +
														" to bet/add? (-1 to fold) ");
										Scanner input5= new Scanner(System.in);
										bettingMoneyTemp = input5.nextInt();
										bettingMoneyAsString = bettingMoneyTemp + "";
										totalMoney = bettingMoneyTemp + moneyDeductedAlready[i];
									}
								}
								
								if (totalMoney == bettingMoney)
								{	
									playerInfo[i][1] -= bettingMoneyTemp;
									playerInfo[0][2] += bettingMoneyTemp;
									moneyDeductedAlready[i] += bettingMoneyTemp;
									System.out.println(names[i] + " checked/called.");
								}
								
								if (totalMoney > bettingMoney)
								{
									playerInfo[i][1] -= bettingMoneyTemp;
									playerInfo[0][2] += bettingMoneyTemp;
									bettingMoney = totalMoney;
									moneyDeductedAlready[i] += bettingMoneyTemp;
									playerIndex1Next = i;
									System.out.println();
									waitSecond(500);
									System.out.println(names[i] + " just raised the bet to " + bettingMoney + " dollars!");
								}
							}
						}
						
		
					}
				}
				System.out.println();
			}
			
		}
			
		//do recursion if someone raises the bet
		if (playerIndex1Next != playerIndex1)
			bettingMoney = betting(names, playerInfo, playerDeck, moneyDeductedAlready, playerIndex1Next, playerIndex2Next, 
					bettingMoney, numberOfPlayer);
		//return bettingMoney
		return bettingMoney;
	}//method ends
	
	public static void giveFlopCard(boolean[][] deck, int numberOfPlayer, boolean[][][] playerDeck, 
			int cardType, int cardNumber, String[] cards)
	{
		int i, j, k;
		for (k = 0; k < 3; k++)
		{
			do 
			{
				i = (int)(Math.random()*3);
				j = (int)(Math.random()*13);
			} while (deck[i][j] == false);
			
			deck[i][j] = false;
			
			for (int m = 0; m < numberOfPlayer; m++)
			{
				if ((playerDeck[m][1][13]) && (playerDeck[m][0][13] == true))
				{
					playerDeck[m][i][j] = true;
					
					cardType = i;
					cardNumber = j;
					
					switch (cardType)
					{
					case 0: cards[m] += "spade";
							break;
					case 1: cards[m] += "diamond";
							break;
					case 2: cards[m] += "heart";
							break;
					case 3: cards[m] += "club";
					}	
					
					cardNumber ++;
					cards[m] += cardNumber + " ";
				}
			}
		}
	}
	
	public static void giveTurnCard(boolean[][] deck, int numberOfPlayer, boolean[][][] playerDeck, 
			int cardType, int cardNumber, String[] cards)
	{
		int i, j, k;
		do 
		{
			i = (int)(Math.random()*3);
			j = (int)(Math.random()*13);
		} while (deck[i][j] == false);
			
		deck[i][j] = false;
			
		for (int m = 0; m < numberOfPlayer; m++)
		{
			if ((playerDeck[m][1][13]) && (playerDeck[m][0][13] == true))
			{
				playerDeck[m][i][j] = true;
					
				cardType = i;
				cardNumber = j;
					
				switch (cardType)
				{
				case 0: cards[m] += "spade";
						break;
				case 1: cards[m] += "diamond";
						break;
				case 2: cards[m] += "heart";
						break;
				case 3: cards[m] += "club";
				}	
					
				cardNumber ++;
				cards[m] += cardNumber + " ";
			}
		}
	}
	
	public static void evaluateOrder(boolean[][] playerDeck, int[][] playerInfo, int b)
	{
		//assign 1 to the order since everyone must at least has High Card
		playerInfo[b][0] = 1;
		
		//Start checking. 
		//Higher rank will replace lower rank if a set of cards satisfies multiple ranks
		
		//check if it has One Pair
		int[] numberOfSameCard = new int[13];
		for (int i = 0; i < 13; i++)
			numberOfSameCard[i] = 0;
		for (int i = 0; i < 13; i++)
		{
			for (int j = 0; j < 4; j++)
			{	
				if (playerDeck[j][i] == true)
					numberOfSameCard[i]++;
			}
		}
		for (int i = 0; i < 13; i++)
		{
			if (numberOfSameCard[i] > 1)
				playerInfo[b][0] = 2;
		}
		
		//check if it has Two Pairs
		int numberOfPairs = 0;
		for (int i = 0; i < 13; i++)
		{
			if (numberOfSameCard[i] > 1)
				numberOfPairs++;
		}
		if (numberOfPairs > 1)
			playerInfo[b][0] = 3;
		
		//check if it has Three of a Kind
		for (int i = 0; i < 13; i++)
		{
			if (numberOfSameCard[i] > 2)
				playerInfo[b][0] = 4;
		}
		
		//check if it has Straight
		for (int i = 0; i < 9; i++)
		{
			if ((numberOfSameCard[i] > 0) && (numberOfSameCard[i+1] > 0) && 
				(numberOfSameCard[i+2] > 0) && (numberOfSameCard[i+3] > 0) &&
				(numberOfSameCard[i+4] > 0))
				playerInfo[b][0] = 5;
		}
		//1, 9, 10, 11, 12 is also a Straight
		if ((numberOfSameCard[0] > 0) && (numberOfSameCard[9] > 0) && 
				(numberOfSameCard[10] > 0) && (numberOfSameCard[11] > 0) &&
				(numberOfSameCard[12] > 0))
			playerInfo[b][0] = 5;
		
		//check if it has Flush
		int[] numberOfSameType = new int[4];
		for (int i = 0; i < 4; i++)
			numberOfSameType[i] = 0;
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 13; j++)
			{
				if (playerDeck[i][j] == true)
					numberOfSameType[i]++;
			}
		}
		for (int i = 0; i < 4; i++)
		{
			if (numberOfSameType[i] > 4)
				playerInfo[b][0] = 6;
		}
		
		//check if it has Full House
		boolean hasAtLeastThreeSameNumber = false;
		boolean hasAtLeastTwoSameNumber = false;
		int hasAtLeastThreeSameNumberIndex = 0;
		for (int i = 0; i < 13; i++)
		{
			if (numberOfSameCard[i] > 2)
			{
				hasAtLeastThreeSameNumber = true;
				hasAtLeastThreeSameNumberIndex = i;
			}
			
			if ((numberOfSameCard[i] > 1) && (i != hasAtLeastThreeSameNumberIndex))
			{
				hasAtLeastTwoSameNumber = true;
			}
		}
		if (hasAtLeastThreeSameNumber && hasAtLeastTwoSameNumber)
			playerInfo[b][0] = 7;
		
		
		//check if it is Four of a Kind
		for (int i = 0; i < 13; i++)
		{
			if (numberOfSameCard[i] == 4)
				playerInfo[b][0] = 8;
		}
		
		//check if it is Straight Flush
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
				if ((playerDeck[i][j] == true) && (playerDeck[i][j+1] == true) && (playerDeck[i][j+2] == true) && 
						(playerDeck[i][j+3] == true) && (playerDeck[i][j+4] == true))
					playerInfo[b][0] = 9;
		}
		
		//check if it is Royal Flush
		for (int i = 0; i < 3; i++)
		{
			if ((playerDeck[i][0] == true) && (playerDeck[i][9] == true) && (playerDeck[i][10] == true) && 
				(playerDeck[i][11] == true) && (playerDeck[i][12] == true))
				playerInfo[b][0] = 10;
		}
	
	}
	
	public static String decideCardsTypeByOrder(int order)
	{
		switch (order)
		{
		case 1: return "High Cards";
		case 2: return "One Pair";
		case 3: return "Two Pairs";
		case 4: return "Three of a kind";
		case 5: return "Straight";
		case 6: return "Flush";
		case 7: return "Full House";
		case 8: return "Four of a Kind";
		case 9: return "Straight Flush";
		case 10: return "Royal Flush";
		default: return "0";
		}
	}
}
