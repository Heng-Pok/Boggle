package view;

import javafx.scene.control.TextArea;
import model.Boggle;
import model.DiceTray;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This is where we get all the classes working together to make the whole game
 * happen. We will be defining and using some minor methods here to make the
 * game work, such as a method for reading in the dictionary txt file as well as
 * other methods that help utilize other classes to produce results, simulating
 * the game.
 *
 * @author HENGSOCHEAT POK
 */

public class BoggleConsole {

	public static char[][] boardChoice() {
		/**
		 * This generates a random board for the player to play with Takes no argument
		 * but returns a 2D array representing the board.
		 */

		List<char[]> diceList = new ArrayList<>();
		char[] dice1 = { 'L', 'R', 'Y', 'T', 'T', 'E' };
		char[] dice2 = { 'A', 'N', 'A', 'E', 'E', 'G' };
		char[] dice3 = { 'A', 'F', 'P', 'K', 'F', 'S' };
		char[] dice4 = { 'Y', 'L', 'D', 'E', 'V', 'R' };
		char[] dice5 = { 'V', 'T', 'H', 'R', 'W', 'E' };
		char[] dice6 = { 'I', 'D', 'S', 'Y', 'T', 'T' };
		char[] dice7 = { 'X', 'L', 'D', 'E', 'R', 'I' };
		char[] dice8 = { 'Z', 'N', 'R', 'N', 'H', 'L' };
		char[] dice9 = { 'E', 'G', 'H', 'W', 'N', 'E' };
		char[] dice10 = { 'O', 'A', 'T', 'T', 'O', 'W' };
		char[] dice11 = { 'H', 'C', 'P', 'O', 'A', 'S' };
		char[] dice12 = { 'N', 'M', 'I', 'H', 'U', 'Q' };
		char[] dice13 = { 'S', 'E', 'O', 'T', 'I', 'S' };
		char[] dice14 = { 'M', 'T', 'O', 'I', 'C', 'U' };
		char[] dice15 = { 'E', 'N', 'S', 'I', 'E', 'U' };
		char[] dice16 = { 'O', 'B', 'B', 'A', 'O', 'J' };
		diceList.add(dice1);
		diceList.add(dice2);
		diceList.add(dice3);
		diceList.add(dice4);
		diceList.add(dice5);
		diceList.add(dice6);
		diceList.add(dice7);
		diceList.add(dice8);
		diceList.add(dice9);
		diceList.add(dice10);
		diceList.add(dice11);
		diceList.add(dice12);
		diceList.add(dice13);
		diceList.add(dice14);
		diceList.add(dice15);
		diceList.add(dice16);

		char[][] randomBoard = new char[4][4];
		ArrayList<Integer> chosenDiceNumbers = new ArrayList<>();
		for (int row = 0; row < randomBoard.length; row++) {
			for (int col = 0; col < randomBoard.length; col++) {
				Random randomDie = new Random(); // random die selector
				Random randomCharacter = new Random(); // random character selector within a die.
				int selectedCharacter = randomCharacter.nextInt(6); // select a die
				int dieChosen = randomDie.nextInt(16); // select a character of the die
				if (!chosenDiceNumbers.contains(dieChosen)) // if the die has not been selected
					chosenDiceNumbers.add(dieChosen);
				else {
					while (chosenDiceNumbers.contains(dieChosen)) {
						dieChosen = randomDie.nextInt(16); // choose another die, a new one.
					}
				}
				randomBoard[row][col] = diceList.get(dieChosen)[selectedCharacter];
			}
		}
		return randomBoard; // the finalized board to play with.
	}

	private static Hashtable<Integer, String> Dictionary(String filename) throws IOException {
		Hashtable<Integer, String> dict = new Hashtable<>();
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		int index = -1;
		while (scanner.hasNextLine()) {
			index++;
			dict.put(index, scanner.nextLine());
		}
		return dict;
	}

	public static void printWordsPlayersCouldHaveFound(Boggle game, TextArea outputField) {
		outputField.setText(outputField.getText() + "Here are the words you missed: \n");
		outputField.setText(outputField.getText() + "===================================\n");
		int counter = 0;
		for (String theWord : game.getListOfMoreWordsToBeFound()) {
			if (counter < 10) {
				outputField.setText(outputField.getText() + theWord + " ");
				counter++;
			} else {
				counter = 0;
				outputField.setText(outputField.getText() + "\n");
			}
		}
		outputField.setText(outputField.getText() + "\n");
		outputField
				.setText(outputField.getText() + "(" + game.getListOfMoreWordsToBeFound().size() + " words missed)\n");
	}

	public static void getPlayersIncorrectWords(Boggle game, TextArea outputField) {
		String word;
		outputField.setText(outputField.getText() + "Incorrect Words: \n=====================\n");
		for (int i = 0; i < game.getListOfIncorrectWordsEntered().size(); i++) {
			word = game.getListOfIncorrectWordsEntered().get(i);
			outputField.setText(outputField.getText() + word + " ");
		}
		outputField.setText(outputField.getText() + "\n");
	}

	public static void getPlayersCorrectWords(Boggle game, TextArea outputField) {
		game.processListOfWordsToBeFound();
		game.processPlayerCorrectFoundWords();
		outputField.setText(outputField.getText() + "Your score: " + game.getScore() + "\n\n");
		outputField.setText(outputField.getText() + "Correct Words You Found: \n");
		outputField.setText(outputField.getText() + "===========================\n");
		String word;
		for (int i = 0; i < game.getPlayerCorrectFoundWords().size(); i++) {
			word = game.getPlayerCorrectFoundWords().get(i);
			outputField.setText(outputField.getText() + word + " ");
		}
		outputField.setText(outputField.getText() + "\n");
		outputField.setText(outputField.getText() + "\n");
	}

	public static void getPlayersWords(Boggle game, TextArea userField, TextArea outputField) {

		game.getUserInput(userField);
		outputField.setText("Words you entered: \n");
		outputField.setText(outputField.getText() + "==========================\n");
		String word;
		for (int i = 0; i < game.getAllWordsEntered().size(); i++) {
			word = game.getAllWordsEntered().get(i);
			outputField.setText(outputField.getText() + word + " ");
		}
		outputField.setText(outputField.getText() + "\n");
	}

	public static Hashtable<Integer, String> simplifiedDictionary(DiceTray diceTray) throws IOException {
		List<Character> charactersInBoard = new ArrayList<>();
		for (char[] row : diceTray.getBoard()) {
			for (char letter : row) {
				charactersInBoard.add(letter);
			}
		}
		Hashtable<Integer, String> dict = Dictionary("BoggleWords.txt"); // our main dictionary of all words, a
																			// hashtable.
		List<Character> charactersList = new ArrayList<>();
		// this will be used to eliminate unnecessary words in the dictionary to make it
		// shorter and take less time to iterate.
		for (char[] row : diceTray.getBoard()) {
			for (char c : row) {
				charactersList.add(Character.toLowerCase(c));
			}
		}

		// this is to shorten the dictionary to favor time complexity without affecting
		// its accuracy when searching for
		// words in the diceTray using it
		// Removing unnecessary words, words that will not be found in the tray.
		for (int i = 0; i < dict.size(); i++) {
			dict.get(i);
			if (dict.get(i).length() > 2) {
				if (!charactersList.contains(dict.get(i).charAt(0))) {
					dict.remove(i);
				}
			}
		}
		return dict;
	}

	public static void main(String[] args) throws IOException {

	}
}
