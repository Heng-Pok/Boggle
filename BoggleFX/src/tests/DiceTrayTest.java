package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.junit.Test;

import model.DiceTray;

/**
 * Grader tests for DiceTray. Just tests word searches.
 * We are only testing to see if DiceTray.java works well as intended.
 *
 * @author mercer, michaels, HENGSOCHEAT POK
 * @version 1.2
 */

public class DiceTrayTest {

    private char[][] longWords = {
            { 'A', 'B', 'S', 'E' },
            { 'I', 'M', 'T', 'N' },
            { 'N', 'D', 'E', 'D' },
            { 'S', 'S', 'E', 'N' } };

    private DiceTray tray = new DiceTray(longWords);

    private char[][] longWordsLowerCase = {
            { 'a', 'b', 's', 'e' },
            { 'i', 'm', 't', 'n' },
            { 'n', 'r', 'e', 'd' },
            { 's', 'q', 'e', 'n' } };
    
    private char[][] longWords2 = {
            { 'e', 'e', 'w', 't' },
            { 'f', 'u', 's', 'w' },
            { 'a', 't', 'c', 'd' },
            { 'p', 'd', 'l', 'z' } };
    
    private char[][] wordsTray = {
    		{'H', 'E', 'R', 'K'},
    		{'S', 'S', 'E', 'R'}, 
    		{'V', 'N', 'O', 'H'}, 
    		{'O', 'A', 'T', 'E'}};
    
    private char[][] simpleBoard = {
    		{'C', 'M', 'B', 'E'},
    		{'q', 'A', 'q', 'R'},
    		{'P', 'q', 'V', 'q'},
    		{'A', 'C', 'q', 'E'}};
    
    private Hashtable<Integer, String> dict = new Hashtable<>();

    private DiceTray trayLowerCase = new DiceTray(longWordsLowerCase);
    
    private DiceTray tray2 = new DiceTray(longWords2);
    
    private DiceTray simpleTray = new DiceTray(simpleBoard);
    
    private DiceTray finalTray = new DiceTray(wordsTray);
    
    private void setUpDictionary() {
    	dict.put(0, "amber");
    	dict.put(1, "cave");
    	dict.put(2, "cap");
    	dict.put(3, "map");
    	
    }
    
    
    
    @Test
    public void testForSmallStringsNotRealWords() {
        // We are not looking for words in a dictionary now, just strings.
        //
        // searchBoard must return false for strings < length() of 3
        // asserts can take a string argument that prints when the assert fails.
        //
        assertFalse(tray.found(""));
        assertFalse(tray.found("TS"));  // Not a word, but the sequence exists
        assertTrue(tray.found("TMI"));
        assertTrue(tray.found("aBs")); // Case insensitive
        assertTrue(tray.found("AbS"));
    }

    @Test
    public void testFound2() {
        assertTrue(tray.found( "sent"));
        assertTrue(tray.found( "SENT"));
        assertTrue(tray.found( "minded"));
        assertTrue(tray.found( "teen"));
        assertTrue(tray.found( "dibtd"));
        assertTrue(tray2.found("fuse"));
    }

    @Test
    public void testForLongerStrings() {
        assertTrue(tray.found( "NTMINDED")); // Not a word, but the sequence exists
        assertTrue(tray.found( "ABSENTMINDEDNESS"));
    }

    @Test
    public void testFound3() {
        assertTrue(trayLowerCase.found( "queen"));
        assertTrue(trayLowerCase.found( "Queen"));
        assertTrue(trayLowerCase.found( "aim"));
        assertTrue(trayLowerCase.found( "ENteR"));
        assertTrue(trayLowerCase.found( "redNEST"));
        assertTrue(trayLowerCase.found( "que"));
        assertFalse(trayLowerCase.found( "Qu"));
        assertFalse(trayLowerCase.found( "amess"));
    }
    
    @Test
    public void testFoundDictionaryBased() {
    	setUpDictionary();
    	List<String> listOfWordsToBeFound = simpleTray.foundDictionaryBased(dict);
    	assertFalse(listOfWordsToBeFound.contains("Suffer"));
    	assertFalse(listOfWordsToBeFound.contains("cameraman"));
    	assertTrue(listOfWordsToBeFound.contains("cave"));
    	assertTrue(listOfWordsToBeFound.contains("amber"));
    	
    }
    
    @Test
    public void testWordsTray()
    {
    	assertTrue(finalTray.found("renovate"));
    }
}

