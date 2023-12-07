package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Boggle;
import model.DiceTray;

import java.io.IOException;
import java.util.Hashtable;

/**
 * This class works similarly to how the BoggleConsole.java works. The only
 * difference is it uses all the classes beside itself (including BoggleConsole)
 * to create a GUI representation of the game for the player to interact with to
 * play the game.
 *
 * @Author: HENGSOCHEAT POK
 */

public class BoggleFX extends Application {
	private final Button startButton = new Button("New Game");
	private final Button stopButton = new Button("Submit");
	private final TextArea userField = new TextArea(null);
	private final TextArea trayField = new TextArea(null);
	private final TextArea outputField = new TextArea(null);
	private final BorderPane leftBorderPane = new BorderPane();
	private final BorderPane middleBorderPane = new BorderPane();
	private final BorderPane rightBorderPane = new BorderPane();
	private final Label userInputLabel = new Label("Type in your words here:");
	private final Label resultLabel = new Label(
			"Results: (Do not panic if it looks stuck! Even if it says 'Not Responding'. Approx Wait Time: 2.5 minutes)");
	private final GridPane gridPane = new GridPane();

	private Boggle boggleGame;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		// modify the border panes
		leftBorderPane.setLeft(startButton);
		leftBorderPane.setRight(stopButton);
		Font labelFont = Font.font("ChalkBoard", 15);
		userInputLabel.setFont(labelFont);
		middleBorderPane.setLeft(userInputLabel);
		resultLabel.setFont(labelFont);
		rightBorderPane.setLeft(resultLabel);

		// set up the grid
		gridPane.setHgap(12);
		gridPane.setVgap(12);
		// add one pane to another
		gridPane.add(leftBorderPane, 1, 1);
		gridPane.add(middleBorderPane, 2, 1);
		gridPane.add(rightBorderPane, 3, 1);
		// get the fonts for the text fields.
		Font trayFont = Font.font("Courier New", FontWeight.BOLD, 45);
		Font userFont = Font.font("ChalkBoard", 18);
		Font outputFont = Font.font("ChalkBoard", 18);

		// modify the tray text field
		trayField.setFont(trayFont);
		trayField.setMaxWidth(370);
		trayField.setMaxHeight(300);
		trayField.setEditable(false);
		registerListeners();

		// modify the user's text field
		userField.setFont(userFont);
		userField.setMaxWidth(300);
		userField.setEditable(true);
		userField.setWrapText(true);
		userField.setPromptText("Click on New Game and type in your words here.........");

		// modify the output's text field
		outputField.setFont(outputFont);
		outputField.setEditable(false);
		outputField.setWrapText(true);
		outputField.setPromptText(
				"(After you enter your words, please do not panic if it looks stuck! The results may take up to no more than 2.5 minutes to show up. It will work!)");

		// add text fields to the grid pane and the grid pane to the scene and the scene
		// to the stage.
		gridPane.add(trayField, 1, 2);
		gridPane.add(userField, 2, 2);
		gridPane.add(outputField, 3, 2);
		Scene scene = new Scene(gridPane, 1450, 370);
		stage.setScene(scene);
		stage.setTitle("Boggle");
		stage.show();
	}

	private void registerListeners() {
		startButton.setOnAction(new startButtonListener());
		stopButton.setOnAction(new stopButtonListener());
	}

	private class startButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent actionEvent) {
			userField.clear();
			outputField.clear();
			userField.setEditable(true);
			if (!outputField.getPromptText().equals(
					"(After you click the Submit button, please do not panic if it looks stuck! The results may take up to 2.5 minutes to show up. It will work!)"))
				outputField.setPromptText(
						"(After you click the Submit button, please do not panic if it looks stuck! The results may take up to 2.5 minutes to show up. It will work!)");
			prepareTray(); // stage 1: prepare the tray as well as a whole new game for the player
			userField.setPromptText("Click on New Game and type in your words here.........");
			if (userField.isFocused() && userField.getText() != null)
				userField.setPromptText("Click on New Game and type in your words here.........");
		}

		private void prepareTray() {
			/**
			 * Stage 1 of the game, prepare the tray for the player, setting up the game.
			 */
			char[][] diceArray = BoggleConsole.boardChoice();
			String rowOfTray = "";
			for (char[] row : diceArray) {
				for (char character : row) {
					if (character == 'q' || character == 'Q')
						rowOfTray += "Qu" + " ";
					else
						rowOfTray += character + "  ";
				}
				rowOfTray += "\n";
			}
			trayField.setText(rowOfTray);
			DiceTray diceTray = new DiceTray(diceArray);
			Hashtable<Integer, String> dictionary;
			try {
				dictionary = BoggleConsole.simplifiedDictionary(diceTray); // create a modified dictionary
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			boggleGame = new Boggle(diceTray, dictionary); // initialize a boggle game object
		}
	}

	private class stopButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent actionEvent) {

			if (!(trayField.getText() == null)) {
				String userTypedText = userField.getText();
				String[] userTypedTextSplit = userTypedText.split(" ");
				String wordsPrintedOut = "";
				for (String word : userTypedTextSplit) {
					wordsPrintedOut += word + " ";
				}
				outputField.setText(wordsPrintedOut);
				BoggleConsole.getPlayersWords(boggleGame, userField, outputField); // stage 2: get player's words.
				// outputField.setText(outputField.getText() + "Calculating...........\nThis may
				// take a while (may take up to 2 or 3 minutes max, trust me it'll
				// work).......\nPlease wait.....\n\n");
				BoggleConsole.getPlayersCorrectWords(boggleGame, outputField); // stage 3: get player's correct words
																				// and his score.
				BoggleConsole.getPlayersIncorrectWords(boggleGame, outputField); // stage 4: show player's incorrect
																					// words.
				BoggleConsole.printWordsPlayersCouldHaveFound(boggleGame, outputField); // stage 5: show player all the
																						// words he/she could have found
																						// and the number.
				outputField.setText(outputField.getText() + "\nPlease click on 'New Game' to start a new game");
				userField.setText(userField.getText()
						+ "\n\n\n(Attempts disabled. Please start a new game to enter new attempts.....)");
				userField.setEditable(false);
			}
		}
	}
}