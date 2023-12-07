package model;

import java.util.*;

/**
 * Model the tray of dice in the game Boggle. A model.DiceTray can
 * be constructed with a 4x4 array of characters for testing.
 *
 * @author HENGSOCHEAT POK
 */
public class DiceTray {

    private final int[][] availableDirections = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };
    private final char[][] theBoard;
    private final List<String> listOfRealWords = new ArrayList<>();


    /**
     * Construct a model.DiceTray object using a hard-coded 2D array of chars.
     * One is provided in the given unit test. You can create others.
     */
    public DiceTray(char[][] newBoard) {
        theBoard = newBoard;
    }

    /**
     * Return true if attempt can be found on the model.DiceTray following the rules of Boggle, like a die can only be used once.
     *
     * @param attempt A word that may be in the model.DiceTray by connecting consecutive letters
     * @return True if search is found in the model.DiceTray or false if not. You need not check the dictionary now.
     */
    public boolean found(String attempt) {
        if (attempt.equals(""))
            return false;
        if (attempt.equalsIgnoreCase("qu"))
            return false;
        else if (attempt.substring(0, 2).equalsIgnoreCase("qu") && attempt.length() < 3)
            return false;
        else if (attempt.length() < 3)
            return false;


        String attempted = attempt;
        if (attempt.substring(0, 2).equalsIgnoreCase("qu")) {
            attempted = attempt.charAt(0) + attempt.substring(2);
        }

        int numberOfRows = theBoard.length;
        int numberOfColumns = theBoard[0].length;
        String attemptIgnoreCase = attempted.toLowerCase();
        Set<String> foundWord = new HashSet<>(); // to store words found in the board that may match the attempted word 'attempt'.
        boolean[][] triedPositions = new boolean[numberOfRows][numberOfColumns];
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfColumns; column++) {
                String currentWordFound = "";
                findTheWord(currentWordFound, row, column, triedPositions, attemptIgnoreCase, foundWord); // call the helper method defined below.
            }
        }
        return foundWord.contains(attemptIgnoreCase);
    }

    private void findTheWord(String currentWord, int row, int column,
                             boolean[][] triedPositions, String attemptIgnoreCase, Set<String> foundWord) {
        int numberOfRows = theBoard.length;
        int numberOfCols = theBoard[0].length;

        if (row < 0 || row >= numberOfRows || column < 0 || column >= numberOfCols || triedPositions[row][column]) { // make sure it does not go beyond any of the borders.
            return;
        }

        currentWord += Character.toLowerCase(theBoard[row][column]); // concatenate characters found together.
        triedPositions[row][column] = true; // set location to 'tried'

        if (attemptIgnoreCase.equalsIgnoreCase(currentWord)) { // if the intended word 'attempt' matches the word found by the aforementioned concatenation.
            foundWord.add(currentWord); // add it to the foundWord set.
        }

        for (int[] possibleWay : availableDirections) { // directions that we can go from one character, usually 8.
            int newRow = row + possibleWay[0]; // move to the next available location
            int newCol = column + possibleWay[1]; // move to the next available location
            findTheWord(currentWord, newRow, newCol, triedPositions, attemptIgnoreCase, foundWord); // recurse, finding the word again starting from the new location.
        }

        triedPositions[row][column] = false; // set the found location to false before beginning another attempt of search.
    }

    public List<String> foundDictionaryBased(Hashtable<Integer, String> dictionary) {
        int numberOfRows = theBoard.length;
        int numberOfColumns = theBoard[0].length;
        List<String> foundWords = new ArrayList<>(); // to store words found in the board that may match the attempted word 'attempt'.
        boolean[][] triedPositions = new boolean[numberOfRows][numberOfColumns];
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfColumns; column++) {
                String currentWordFound = "";
                findTheWordDictionaryBased(currentWordFound, row, column, triedPositions, foundWords, dictionary); // call the helper method defined below.
            }
        }
        return foundWords;
    }

    private void findTheWordDictionaryBased(String currentWord, int row, int column, boolean[][] triedPositions, List<String> foundWords, Hashtable<Integer, String> dictionary) {
        int numberOfRows = theBoard.length;
        int numberOfCols = theBoard[0].length;

        if (row < 0 || row >= numberOfRows || column < 0 || column >= numberOfCols || triedPositions[row][column]) { // make sure it does not go beyond any of the borders.
            return;
        }

        currentWord += Character.toLowerCase(theBoard[row][column]); // concatenate characters found together.
        triedPositions[row][column] = true; // set location to 'tried'
        if (currentWord.startsWith("q") || currentWord.startsWith("Q")) {
            if (currentWord.length() == 1) {
                currentWord += "u";
            }
        }
/*        if (dictionary.containsValue(currentWord.toLowerCase())) { // if the intended word 'attempt' matches the word found by the aforementioned concatenation.
            System.out.print(currentWord + " ");*/
        if (currentWord.length() <= 8 && dictionary.containsValue(currentWord))
            foundWords.add(currentWord);// add it to the foundWord set.


        for (int[] possibleWay : availableDirections) { // directions that we can go from one character, usually 8.
            int newRow = row + possibleWay[0]; // move to the next available location
            int newCol = column + possibleWay[1]; // move to the next available location
            findTheWordDictionaryBased(currentWord, newRow, newCol, triedPositions, foundWords, dictionary); // recurse, finding the word again starting from the new location.
        }

        triedPositions[row][column] = false; // set the found location to false before beginning another attempt of search.

    }

    public char[][] getBoard() {
        return theBoard;
    }


}

