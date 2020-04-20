package application;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: HelloFX
// Files: Main.java
//
// Author: Zi Lee
// Email: jlee@wisc.edu
// Lecturer's Name: Deb Deppeler
//
/////////////////////////////////////////////////////////////////////////////

public class Main extends Application {

    private static final int WINDOW_WIDTH = 1400;
    private static final int WINDOW_HEIGHT = 800;
    private static final String APP_TITLE = "Cases of COVID-19 in the United States";

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Layout management
        BorderPane root = new BorderPane();
        VBox vboxinfomation = new VBox();

        // Label
        Label tital = new Label(APP_TITLE);
        tital.setFont(new Font(36));
        tital.setTextFill(Color.WHITE);



        // Add an ImageView of an Image in the center panel
        Image image = new Image("map.png"); // load the image
        ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setFitWidth(900);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);


        // Add a Button in the bottom panel with the label "Done"
        Button button = new Button("Done");
        button.setOnAction(e -> Platform.exit());
        button.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

        // Add to layout management
        // Vbox
        vboxinfomation.getChildren().addAll(addLabel("Total Cases in US:", 24),
                addLabel("Total Cases in local:", 20), addLabel("New cases:", 18),
                addLabel("Recovered:", 18), addLabel("Deaths:", 18));
        // BorderPane
        root.setTop(tital);
        root.setCenter(iv);
        root.setLeft(vboxinfomation);
        root.setBottom(button);
        root.setStyle("-fx-background-color: BLACK; -fx-text-fill: white;");


        
        //canvas to draw circle
        Canvas canvas = new Canvas(700, 400);
        Group ng = new Group();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawcircle(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(ng));
        primaryStage.show();
        

        Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        // mainScene.getStylesheets().add("Style.css");

        // Add the stuff and set the primary stage
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    //method to draw circle 
    private void drawcircle(GraphicsContext gc) {
      gc.setStroke(Color.RED);
      gc.setLineWidth(5);
      gc.strokeOval(60, 60, 30, 30);
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Label addLabel(String fillText, int size) {
        Label label = new Label(fillText);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font(size));
        return label;
    }
}
