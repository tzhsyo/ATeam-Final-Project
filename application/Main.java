package application;


import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;

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
    private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);


    @Override
    public void start(Stage primaryStage) throws Exception {

        // Layout management
        BorderPane root = new BorderPane();
        BorderPane overLay = new BorderPane();
        VBox vboxinfomation = new VBox();
        StackPane stack_pane;

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
        
        Button button2 = new Button("Dismiss");
        
       

        // Add to layout management
        // Vbox
        vboxinfomation.getChildren().addAll(addLabel("Total Cases in US:", 24),
                addLabel("Total Cases in local:", 20), addLabel("New cases:", 18),
                addLabel("Recovered:", 18), addLabel("Deaths:", 18));
        
        // Add circle
        Circle circle = new Circle( 425, 140, 30);
        circle.setStroke(Color.RED);
        circle.setFill(Color.RED.deriveColor(1, 1, 1, 0.7));
        
        //Add rectangle
        Rectangle rectangle = new Rectangle(550, 300, 300, 160);
        rectangle.setFill(Color.WHITE);
        
        // BorderPane// Main pages
        root.setTop(tital);
        root.setCenter(iv);
        root.setLeft(vboxinfomation);
        root.setBottom(button);
        root.setStyle("-fx-background-color: BLACK; -fx-text-fill: white;");
        root.getChildren().add(circle);
        
        //Overlay
        overLay.getChildren().add(rectangle);
        overLay.setCenter(button2);
        overLay.setStyle("-fx-background-color: rgba(105,105,105, 0.9);");
        
        //InfoOverlay
        stack_pane = new StackPane(root,overLay);
        //button to delect the overlay
        button2.setOnAction(e -> stack_pane.getChildren().remove(overLay));
        
        //canvas to draw circle
//        Canvas canvas = new Canvas(700, 400);
//        Group ng = new Group();
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//        drawcircle(gc);
//        root.getChildren().add(circle);
//        primaryStage.setScene(new Scene(ng));
//        primaryStage.show();
        

        Scene mainScene = new Scene(stack_pane, WINDOW_WIDTH, WINDOW_HEIGHT);
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
      gc.strokeOval(300, 300, 30, 30);
    }
    
    
    //method to transition circle
    private TranslateTransition createTranslateTransition(Circle circle) {
      final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
      transition.setOnFinished(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent t) {
          circle.setCenterX(circle.getTranslateX() + circle.getCenterX());
          circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
          circle.setTranslateX(0);
          circle.setTranslateY(0);
        }
      }
      );
      return transition;
    }
    
    //method to move circle to where mouse pressed
    private void moveCircle(Scene mainScene, Circle circle, TranslateTransition transition) {
      mainScene.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          if (!event.isControlDown()) {
            circle.setCenterX(event.getSceneX());
            circle.setCenterY(event.getSceneY());
          } else {
            transition.setToX(event.getSceneX() - circle.getCenterX());
            transition.setToY(event.getSceneY() - circle.getCenterY());
            transition.playFromStart();
          }
        }
        });
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
   

