package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Title:           HelloFX
//Files:           Main.java
//
//Author:          Zi Lee
//Email:           jlee@wisc.edu
//Lecturer's Name: Deb Deppeler
//
/////////////////////////////////////////////////////////////////////////////

public class Main extends Application {

	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 500;
	private static final String APP_TITLE = "Cases of COVID-19 in the United States";

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();

		// Add the Label to the top of the border pane
		root.setTop(new Label(APP_TITLE));
		
		// Add a ComboBox in the left panel, that shows a drop down box with
		// three or more items that can be selected
//		String combo[] = { "option 1", "option 2", "option 3" };
//		root.setLeft(
//				new ComboBox<String>(FXCollections.observableArrayList(combo)));

		// Add an ImageView of an Image in the center panel
		Image image = new Image("map.jpg"); //load the image
		ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setFitWidth(400);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);
		root.setCenter(iv);
		
		//Add a Button in the bottom panel with the label "Done"
		//Button button = new Button("Done");
		
		root.setBottom(new Label("Total Cases: " + "\n Recoverd: " + "\n Deaths: "));
		
		//Add vertical box to the right of the root pane 
		//root.setRight(new Label("\n \n <-- A picture of my mom and I   "));
		
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
