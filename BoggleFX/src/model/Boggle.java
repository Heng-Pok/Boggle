package model;

import javafx.scene.control.TextArea;

import java.util.*;

/**
 * This class will work closely with the DiceTray class to simulate the game
 * from the player's perspective as well as evaluating the game. Did the player
 * win? How did the player win? what could the player have done to win if the
 * player loses?
 *
 * @author HENGSOCHEAT POK
 */
public class Boggle {
	private int playerScore;
	private final Hashtable<Integer, String> dictionary;
	private final DiceTray diceTray;
	private final List<String> playerCorrectFoundWords;

	private final List<String> playerIncorrectWords;

	private final List<String> allWordsEntered;

	private List<String> listToBeFoundWords;

	private final TreeSet<String> listOfMoreToBeFoundWords;

	public Boggle(DiceTray diceTray, Hashtable<Integer, String> dictionary) {
		this.diceTray = diceTray;
		this.dictionary = dictionary;
		playerCorrectFoundWords = new ArrayList<>();
		playerScore = 0;
		playerIncorrectWords = new ArrayList<>();
		allWordsEntered = new ArrayList<>();
		listToBeFoundWords = new ArrayList<>();
		listOfMoreToBeFoundWords = new TreeSet<>();
	}

	public int getUserInput(TextArea userField) {
		String userWords = userField.getText();
		String[] arrayOfEnteredWords = userWords.split(" ");
		allWordsEntered.addAll(Arrays.asList(arrayOfEnteredWords));

		return 0;
	}

	public int printBoard() {
		for (char[] row : diceTray.getBoard()) {
			for (char character : row) {
				if (character == 'Q')
					System.out.print("Qu" + " ");
				else
					System.out.print(character + " ");
			}
			System.out.println();
		}
		return 0;
	}

	public int getScore() {
		return playerScore;
	}

	public int processPlayerCorrectFoundWords() {

		for (String word : allWordsEntered) {
			if (word.length() >= 3 && dictionary.containsValue(word.toLowerCase())
					&& !playerCorrectFoundWords.contains(word.toLowerCase())
					&& listToBeFoundWords.contains(word.toLowerCase()))
				playerCorrectFoundWords.add(word.toLowerCase());
			else
				playerIncorrectWords.add(word.toLowerCase());
		}

		for (String word : playerCorrectFoundWords) {
			if (word.length() == 3 || word.length() == 4)
				playerScore += 1;
			if (word.length() == 5)
				playerScore += 2;
			if (word.length() == 6)
				playerScore += 3;
			if (word.length() == 7)
				playerScore += 5;
			if (word.length() >= 8)
				playerScore += 11;
		}
		return 0;
	}

	public List<String> getAllWordsEntered() {
		return allWordsEntered;
	}

	public int processListOfWordsToBeFound() {
		listToBeFoundWords = diceTray.foundDictionaryBased(dictionary);
		for (String word : listToBeFoundWords)
			if (!playerCorrectFoundWords.contains(word))
				listOfMoreToBeFoundWords.add(word);
		return 0;
	}

	public TreeSet<String> getListOfMoreWordsToBeFound() {
		return listOfMoreToBeFoundWords;
	}

	public List<String> getPlayerCorrectFoundWords() {
		return playerCorrectFoundWords;
	}

	public List<String> getListOfIncorrectWordsEntered() {
		return playerIncorrectWords;
	}

	public int printDicitonary() {
		for (int i = 0; i < dictionary.size(); i++) {
			String word = dictionary.get(i);
			System.out.println(word);
		}
		return 0;
	}
}
