package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

import javafx.scene.control.TextArea;
import model.Boggle;
import model.DiceTray;

/**
 * We are testing the Boggle.java class to see if it works as intended. We are
 * not testing the board or tray, but the how the game progresses as the player
 * is playing.
 * 
 * @author HENGSOCHEAT POK
 *
 */
public class BoggleTest {

	private char[][] shortlWords = { { 'K', 'D', 'T', 'Y' }, { 'R', 'W', 'Q', 'T' }, { 'H', 'H', 'O', 'O' },
			{ 'E', 'N', 'R', 'Z' } };

	private char[][] longWords = { { 'C', 'M', 'B', 'E' }, { 'E', 'A', 'Q', 'R' }, { 'R', 'N', 'V', 'M' },
			{ 'A', 'M', 'A', 'E' } };

	private DiceTray tray = new DiceTray(shortlWords);
	private DiceTray tray1 = new DiceTray(longWords);

	private String[] arrayOfWords = { "hen", "hon", "hone", "hoot", "horn", "hot", "how", "nor", "not", "now", "ooh",
			"orzo", "ozone", "rho", "root", "rot", "row", "ton", "tone", "too", "torn", "tow", "two", "when", "who",
			"won", "woo", "worn", "wot", "zone" };
	private String[] arrayOfWords1 = { "amber", "camera", "caveman", "cavemana" };

	private Hashtable<Integer, String> dictionary = new Hashtable<>();
	private Hashtable<Integer, String> dictionary1 = new Hashtable<>();

	private void setDictionary() {
		for (int i = 0; i < arrayOfWords.length; i++) {
			dictionary.put(i, arrayOfWords[i]);
		}

		for (int i = 0; i < arrayOfWords1.length; i++) {
			dictionary1.put(i, arrayOfWords1[i]);
		}
	}

	private Boggle boggleGame = new Boggle(tray, dictionary);
	private Boggle boggleGame1 = new Boggle(tray, dictionary);
	private Boggle boggleGame2 = new Boggle(tray, dictionary);
	private Boggle boggleGame3 = new Boggle(tray1, dictionary1);

	private void setUp() {
		boggleGame.getAllWordsEntered().add("hen");
		boggleGame.getAllWordsEntered().add("horn");
		boggleGame.getAllWordsEntered().add("now");
		boggleGame.getAllWordsEntered().add("root");
		boggleGame.getAllWordsEntered().add("suffer");

		boggleGame1.getAllWordsEntered().add("dasdasd");
		boggleGame1.getAllWordsEntered().add("fgdf");
		boggleGame1.getAllWordsEntered().add("dfg");
		boggleGame1.getAllWordsEntered().add("root");
		boggleGame1.getAllWordsEntered().add("suffer");

		boggleGame2.getAllWordsEntered().add("zone");
		boggleGame2.getAllWordsEntered().add("wot");
		boggleGame2.getAllWordsEntered().add("nor");
		boggleGame2.getAllWordsEntered().add("not");
		boggleGame2.getAllWordsEntered().add("tone");

		boggleGame3.getAllWordsEntered().add("amber");
		boggleGame3.getAllWordsEntered().add("camera");
		boggleGame3.getAllWordsEntered().add("caveman");
		boggleGame3.getAllWordsEntered().add("cavemana");

		boggleGame.processListOfWordsToBeFound();
		boggleGame1.processListOfWordsToBeFound();
		boggleGame2.processListOfWordsToBeFound();
		boggleGame3.processListOfWordsToBeFound();

		boggleGame.processPlayerCorrectFoundWords();
		boggleGame1.processPlayerCorrectFoundWords();
		boggleGame2.processPlayerCorrectFoundWords();
		boggleGame3.processPlayerCorrectFoundWords();
	}

	@Test
	final void testGetScore() {
		setDictionary();
		setUp();
		assertEquals(4, boggleGame.getScore());
		assertEquals(1, boggleGame1.getScore());
		assertEquals(5, boggleGame2.getScore());
		assertEquals(21, boggleGame3.getScore());
	}

	@Test
	final void testGetAllWordsEntered() {
		setDictionary();
		setUp();

		List<String> answer = new ArrayList<>();
		List<String> answer1 = new ArrayList<>();
		List<String> answer2 = new ArrayList<>();
		List<String> answer3 = new ArrayList<>();

		answer.add("hen");
		answer.add("horn");
		answer.add("now");
		answer.add("root");
		answer.add("suffer");

		answer1.add("dasdasd");
		answer1.add("fgdf");
		answer1.add("dfg");
		answer1.add("root");
		answer1.add("suffer");

		answer2.add("zone");
		answer2.add("wot");
		answer2.add("nor");
		answer2.add("not");
		answer2.add("tone");

		answer3.add("amber");
		answer3.add("camera");
		answer3.add("caveman");
		answer3.add("cavemana");

		assertEquals(answer, boggleGame.getAllWordsEntered());
		assertEquals(answer1, boggleGame1.getAllWordsEntered());
		assertEquals(answer2, boggleGame2.getAllWordsEntered());
		assertEquals(answer3, boggleGame3.getAllWordsEntered());

	}

	@Test
	final void testGetListOfMoreWordsToBeFound() {
		setDictionary();
		setUp();

		List<String> answer = new ArrayList<>(Arrays.asList(arrayOfWords));
		List<String> answer1 = new ArrayList<>(Arrays.asList(arrayOfWords));
		List<String> answer2 = new ArrayList<>(Arrays.asList(arrayOfWords));

		answer.remove("hen");
		answer.remove("horn");
		answer.remove("now");
		answer.remove("root");

		answer1.remove("root");

		answer2.remove("zone");
		answer2.remove("wot");
		answer2.remove("nor");
		answer2.remove("not");
		answer2.remove("tone");

		for (String word : answer) {
			assertTrue(boggleGame.getListOfMoreWordsToBeFound().contains(word));
		}

		for (String word : answer1) {
			assertTrue(boggleGame1.getListOfMoreWordsToBeFound().contains(word));
		}

		for (String word : answer2) {
			assertTrue(boggleGame2.getListOfMoreWordsToBeFound().contains(word));
		}
	}

	@Test
	final void testGetPlayerCorrectFoundWords() {
		setDictionary();
		setUp();

		List<String> answer = new ArrayList<>();
		List<String> answer1 = new ArrayList<>();
		List<String> answer2 = new ArrayList<>();

		answer.add("hen");
		answer.add("horn");
		answer.add("now");
		answer.add("root");

		answer1.add("root");

		answer2.add("zone");
		answer2.add("wot");
		answer2.add("nor");
		answer2.add("not");
		answer2.add("tone");

		assertEquals(answer, boggleGame.getPlayerCorrectFoundWords());
		assertEquals(answer1, boggleGame1.getPlayerCorrectFoundWords());
		assertEquals(answer2, boggleGame2.getPlayerCorrectFoundWords());
	}

	@Test
	final void testGetListOfIncorrectWordsEntered() {
		setDictionary();
		setUp();

		List<String> answer = new ArrayList<>();
		List<String> answer1 = new ArrayList<>();
		List<String> answer2 = new ArrayList<>();

		answer.add("suffer");

		answer1.add("dasdasd");
		answer1.add("fgdf");
		answer1.add("dfg");
		answer1.add("suffer");

		assertEquals(answer, boggleGame.getListOfIncorrectWordsEntered());
		assertEquals(answer1, boggleGame1.getListOfIncorrectWordsEntered());
		assertEquals(answer2, boggleGame2.getListOfIncorrectWordsEntered());
	}

	@Test
	final void testPrintDictionary() {
		setDictionary();
		setUp();
		assertEquals(0, boggleGame.printDicitonary());
	}

	@Test
	final void testPrintBoard() {
		setDictionary();
		setUp();
		assertEquals(0, boggleGame.printBoard());
	}

	@Test
	final void testGetUserInput() {
		System.out.println();
		System.out.println(
				"Please ignore what is printed above. Please type in something on this line and press enter and then");
		System.out.println("type in zz or ZZ on the second line to finish the test");
		setDictionary();
		setUp();
		assertEquals(0, boggleGame.getUserInput(new TextArea()));
	}

}
