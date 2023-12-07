module BoggleFX {
	requires javafx.controls;
	requires javafx.graphics;
	requires junit;
	requires org.junit.jupiter.api;
	
	opens view to javafx.graphics, javafx.fxml;
}
