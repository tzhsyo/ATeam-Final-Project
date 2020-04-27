package application;


import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
        BorderPane loadWindow = new BorderPane();
        BorderPane updateWindow = new BorderPane();
        VBox vboxinfomation = new VBox();
        VBox vboxOverLay = new VBox();
        VBox vboxLoad = new VBox();
        VBox vboxUpadte = new VBox();
        HBox bottomButton = new HBox();
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
        Button buttonClose = new Button("Dismiss");
        Button buttonOK = new Button("Load");
        Button buttonOK2 = new Button("Save");
        Button buttonOpen = new Button("Help");
        buttonOpen.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        Button buttonLoad = new Button("LoadFile");
        buttonLoad.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        Button buttonUpadte = new Button("Update");
        buttonUpadte.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

        // Add circle
        Circle circle = new Circle(450, 140, 30);
        circle.setStroke(Color.RED);
        circle.setFill(Color.RED.deriveColor(1, 1, 1, 0.7));
        // canvas to draw circle
        // Canvas canvas = new Canvas(700, 400);
        // Group ng = new Group();
        // GraphicsContext gc = canvas.getGraphicsContext2D();
        // drawcircle(gc);
        // root.getChildren().add(circle);
        // primaryStage.setScene(new Scene(ng));
        // primaryStage.show();

        // Add rectangle
        Rectangle rectangle = new Rectangle(450, 320, 500, 160);
        rectangle.setFill(Color.WHITE);

        //
        String file[] = {"04-17-2020.csv", "04-18-2020.csv", "04-19-2020.csv", "04-20-2020.csv"};
        ComboBox<String> combo_box1 = new ComboBox<String>(FXCollections.observableArrayList(file));

        String data[] = {"Cases", "Recovered", "Deaths"};
        ComboBox<String> combo_box2 = new ComboBox<String>(FXCollections.observableArrayList(data));

        String state[] = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado",
                "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois",
                "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
                "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
                "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York",
                "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
                "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah",
                "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
        
        ComboBox<String> combo_box3 = new ComboBox<String>(FXCollections.observableArrayList(state));
        
        //TextField
        TextField dataField = new TextField();
        dataField.setPromptText("Enter text here");
        dataField.setMaxWidth(150);
        String username = dataField.getText();


        // Add to layout management
        // Vbox
        vboxinfomation.getChildren().addAll(addLabel("Total Cases in US:", 24),
                addLabel("Total Cases in local: 776,093", 20), addLabel("New cases: 22,732", 18),
                addLabel("Recovered: 82,620", 18), addLabel("Deaths: 41,758", 18));

        vboxOverLay.getChildren().addAll(addLabel2("Program instruction", 18), addLabel2(
                "Hover over the red circle to display statistics on the left. (Status: incomplete)",
                12),
                addLabel2("Use the \"Done\" button to exit the program. (Status: complete)", 12),
                addLabel2("Use the \"Dismiss\" button to exit the instruction. (Status: complete)",
                        12),
                addLabel2(
                        "Use the \"Help\" button to reopen the instruction page. (Status: complete)",
                        12),
                addLabel2("Use the \"LoadFile\" button to load file. (Status: complete)", 12),
                addLabel2("Use the \"Update\" button to upadate file data and save. (Status: complete)", 12),
                 buttonClose);
        vboxOverLay.setAlignment(Pos.CENTER);

        vboxLoad.getChildren().addAll(addLabel2("Load File", 18), combo_box1, buttonOK);
        vboxLoad.setAlignment(Pos.CENTER);

        vboxUpadte.getChildren().addAll(addLabel2("Update", 18), combo_box2,combo_box3,dataField,buttonOK2);
        vboxUpadte.setAlignment(Pos.CENTER);
        // Hbox
        bottomButton.getChildren().addAll(button, buttonOpen, buttonLoad, buttonUpadte);

        // BorderPane// Main pages
        root.setTop(tital);
        root.setCenter(iv);
        root.setLeft(vboxinfomation);
        root.setBottom(bottomButton);
        root.setStyle("-fx-background-color: BLACK; -fx-text-fill: white;");
        root.getChildren().add(circle);

        // Overlay
        overLay.getChildren().add(rectangle);
        overLay.setCenter(vboxOverLay);
        overLay.setStyle("-fx-background-color: rgba(105,105,105, 0.9);");

        // loadWindow
        loadWindow.setStyle("-fx-background-color: rgba(105,105,105, 0.9);");
        Rectangle box = new Rectangle(450, 320, 500, 160);
        loadWindow.getChildren().add(box);
        box.setFill(Color.WHITE);
        loadWindow.setCenter(vboxLoad);

        // UpdateWindow
        updateWindow.setStyle("-fx-background-color: rgba(105,105,105, 0.9);");
        Rectangle box2 = new Rectangle(450, 320, 500, 160);
        updateWindow.getChildren().add(box2);
        box2.setFill(Color.WHITE);
        updateWindow.setCenter(vboxUpadte);

        // InfoOverlay
        stack_pane = new StackPane(root, overLay);

        // Button Action
        buttonClose.setOnAction(e -> stack_pane.getChildren().remove(overLay));
        buttonOpen.setOnAction(e -> stack_pane.getChildren().add(overLay));
        buttonLoad.setOnAction(e -> stack_pane.getChildren().add(loadWindow));
        buttonOK.setOnAction(e -> stack_pane.getChildren().remove(loadWindow));
        buttonUpadte.setOnAction(e -> stack_pane.getChildren().add(updateWindow));
        buttonOK2.setOnAction(e -> stack_pane.getChildren().remove(updateWindow));
        Scene mainScene = new Scene(stack_pane, WINDOW_WIDTH, WINDOW_HEIGHT);
        // mainScene.getStylesheets().add("Style.css");

        // Add the stuff and set the primary stage
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // method to draw circle
    private void drawcircle(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(5);
        gc.strokeOval(300, 300, 30, 30);
    }


    // method to transition circle
    private TranslateTransition createTranslateTransition(Circle circle) {
        final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, circle);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                circle.setCenterX(circle.getTranslateX() + circle.getCenterX());
                circle.setCenterY(circle.getTranslateY() + circle.getCenterY());
                circle.setTranslateX(0);
                circle.setTranslateY(0);
            }
        });
        return transition;
    }

    // method to move circle to where mouse pressed
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

    private Label addLabel2(String fillText, int size) {
        Label label = new Label(fillText);
        label.setTextFill(Color.BLACK);
        label.setFont(new Font(size));
        return label;
    }
}


