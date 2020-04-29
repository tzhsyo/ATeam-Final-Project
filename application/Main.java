package application;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
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
import javafx.stage.FileChooser;
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
    private Desktop desktop = Desktop.getDesktop();
    private final String state[] = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado",
            "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois",
            "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
            "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
            "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York",
            "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
            "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah",
            "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
    private String filePath = null;
    private CSVReader reader = new CSVReader();
    private boolean check = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Layout management
        StackPane stack_pane;
        BorderPane underlay = new BorderPane();
        BorderPane root = new BorderPane();
        BorderPane overLay = new BorderPane();
        BorderPane loadWindow = new BorderPane();
        BorderPane updateWindow = new BorderPane();
        BorderPane errorWindow = new BorderPane();
        VBox vboxinfomation = new VBox();
        VBox vboxOverLay = new VBox();
        VBox vboxLoad = new VBox();
        VBox vboxUpadte = new VBox();
        VBox vboxError = new VBox();
        HBox bottomButton = new HBox();
        HBox UpDatebutton = new HBox();
        HBox updataTextField = new HBox();
        
        //CVS reader and Load file
        final FileChooser fileChooser = new FileChooser();

        // Label
        Label tital = new Label(APP_TITLE);
        tital.setFont(new Font(36));
        tital.setTextFill(Color.BLACK);

        // Add an ImageView of an Image in the center panel
        Image image = new Image("map.png"); // load the image
        ImageView iv = new ImageView();
        iv.setImage(image);
        iv.setFitWidth(1000);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);

        // Add a Button in the bottom panel with the label "Done"
        Button button = new Button("Exit"); //progame exist
        button.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        Button buttonClose = new Button("Dismiss");
        Button buttonExitLoad = new Button("Load");
        Button buttonSave = new Button("Save");
        Button saveButtonExit = new Button("Done");
        Button buttonOpen = new Button("Help");
        Button errorExit = new Button("Dismiss");
        buttonOpen.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        Button buttonLoad = new Button("LoadFile");
        buttonLoad.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        Button buttonUpadte = new Button("Update");
        buttonUpadte.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        
        Button openFile = new Button("Open a File");


        // Add rectangle
        Rectangle rectangle = new Rectangle(450, 320, 500, 160);
        rectangle.setFill(Color.WHITE);

        //ComboBox
        String data[] = {"Cases", "Recovered", "Deaths"};
        ComboBox<String> datas = new ComboBox<String>(FXCollections.observableArrayList(data));
        ComboBox<String> states = new ComboBox<String>(FXCollections.observableArrayList(state));
        
        //TextField
        Label textFieldlabel = new Label("New data:");
        TextField dataField = new TextField();
        dataField.setPromptText("Number only");
        dataField.setMaxWidth(150);
        


        // Add to layout management
        // Hbox
        bottomButton.getChildren().addAll(button, buttonOpen, buttonLoad, buttonUpadte);
        UpDatebutton.getChildren().addAll(buttonSave,saveButtonExit);
        UpDatebutton.setAlignment(Pos.CENTER);
        updataTextField.getChildren().addAll(textFieldlabel,dataField);
        updataTextField.setAlignment(Pos.CENTER);
        // Vbox
        //information
        vboxinfomation.getChildren().addAll(
                addLabel("Total Cases in US:", 22),
                addLabel("Total Recovered in US:", 22),
                addLabel("Total Deaths in US:", 22),
                addLabel("State: ", 20),
                addLabel("Total Cases in State: ", 20), 
                addLabel("New cases: ", 18),
                addLabel("Recovered: ", 18), 
                addLabel("Deaths: ", 18));
        //Start page 
        vboxOverLay.getChildren().addAll(
                addLabel("Program instruction", 18), 
                addLabel("Clicked red circle to display statistics on the left.",12),
                addLabel("Use the \"Done\" button to exit the program.", 12),
                addLabel("Use the \"Dismiss\" button to exit the instruction.",12),
                addLabel("Use the \"Help\" button to reopen the instruction page.",12),
                addLabel("Use the \"LoadFile\" button to load file. (Status: complete)", 12),
                addLabel("Use the \"Update\" button to upadate file data and save. (Status: complete)", 12),
                 buttonClose);
        vboxOverLay.setAlignment(Pos.CENTER);
        //Load page 
        vboxLoad.getChildren().addAll(addLabel("Load File", 18), openFile, buttonExitLoad);
        vboxLoad.setAlignment(Pos.CENTER);
        //Upadte page 
        vboxUpadte.getChildren().addAll(addLabel("Update", 18), datas ,states,updataTextField,UpDatebutton);
        vboxUpadte.setAlignment(Pos.CENTER);
        //Error page
        vboxError.getChildren().addAll(
                addLabel("Error", 18),
                addLabel("File format contain incorrect ", 12),errorExit);
        vboxError.setAlignment(Pos.CENTER);
        
        

        // Map layer
        BorderPane.setMargin(iv,new Insets(0,0,0,200));
        underlay.setCenter(iv);
        underlay.setStyle("-fx-background-color: #F7D576; -fx-text-fill: white;");

        // BorderPane// Main pages
        root.setTop(tital);
        root.setLeft(vboxinfomation);
        root.setBottom(bottomButton);
        root.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: white;");

        // StartOverlay
        overLay.getChildren().add(rectangle);
        overLay.setCenter(vboxOverLay);
        overLay.setStyle("-fx-background-color: rgba(105,105,105, 0.9);");

        // loadWindow
        windowSetUp(loadWindow);
        loadWindow.setCenter(vboxLoad);

        // UpdateWindow
        windowSetUp(updateWindow);
        updateWindow.setCenter(vboxUpadte);
        
        // Error window
        windowSetUp(errorWindow);
        errorWindow.setStyle("-fx-background-color: transparent;");
        errorWindow.setCenter(vboxError);

        // Create stack pane and set in
        stack_pane = new StackPane(underlay,root, overLay);

        // Button Action
        // Start overlay
        buttonClose.setOnAction(e -> stack_pane.getChildren().remove(overLay));
        buttonOpen.setOnAction(e -> stack_pane.getChildren().add(overLay));
        // Load overlay
        buttonLoad.setOnAction(e -> stack_pane.getChildren().add(loadWindow));
        buttonExitLoad.setOnAction(e -> stack_pane.getChildren().remove(loadWindow));
        // Error overlay
        errorExit.setOnAction(e -> stack_pane.getChildren().remove(errorWindow));
        // Upadte overlay
        buttonUpadte.setOnAction(e -> {stack_pane.getChildren().add(updateWindow);
            if(check == false) {
                vboxUpadte.getChildren().clear();
                vboxUpadte.getChildren().addAll(addLabel("Update", 18), addLabel("Require load file", 14),
                        datas ,states,updataTextField,UpDatebutton);  
            }
            });
        saveButtonExit.setOnAction(e -> {stack_pane.getChildren().remove(updateWindow);
            vboxUpadte.getChildren().clear();
            vboxUpadte.getChildren().addAll(addLabel("Update", 18), datas ,states,updataTextField,UpDatebutton);
            });
        buttonSave.setOnAction(e ->  {
                if(check == true) {
                    String newData = dataField.getText();
                    String upDataType = datas.getValue();
                    String stateUpDate = states.getValue();
                    try {
                        int value = Integer.parseInt(newData);
                        if(newData != null&&upDataType != null) {
                            reader.addNew(upDataType, stateUpDate, value);
                            vboxUpadte.getChildren().clear();
                            vboxUpadte.getChildren().addAll(addLabel("Update", 18), addLabel("Success updated", 14),
                                datas ,states,updataTextField,UpDatebutton);
                        }

                    }catch(NumberFormatException c){
                        vboxUpadte.getChildren().clear();
                        vboxUpadte.getChildren().addAll(addLabel("Update", 18), addLabel("Invalid update", 14),
                                datas ,states,updataTextField,UpDatebutton);
                    }
                }
        });
        // Exit progam
        button.setOnAction(e -> {Platform.exit();try {
            reader.printFile();
        } catch (IOException e1) {
        }});
        //Get file and check parses into Map tree
        openFile.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(primaryStage);
                        if (file != null) {
                            filePath = file.getPath();
                            if(!filePath.contains(".csv")) {
                                filePath = "Unable to load";
                                stack_pane.getChildren().add(errorWindow);
                            }else {
                                reader = new CSVReader(filePath);
                                if(reader.getCheck() == true) {
                                    check = true;
                                } else {
                                    stack_pane.getChildren().add(errorWindow);
                                }
                            }
                            vboxLoad.getChildren().clear();
                            vboxLoad.getChildren().addAll(
                                    addLabel("Load File", 18), 
                                    openFile,
                                    addLabel("File Name: "+filePath, 12),
                                    buttonExitLoad);
                        }
                    }
                });
        
        //Draw circle
        final Map<String,Circle> circles = new HashMap<>();
        addAllState(circles);
        circles.forEach((String, bounds) -> {
            Circle circle = new Circle(bounds.getCenterX(), bounds.getCenterY(),bounds.getRadius());
            circle.setStroke(Color.RED);
            circle.setFill(Color.RED.deriveColor(1, 1, 1, 0.5));
            Tooltip tooltip = new Tooltip(String);
            tooltip.setShowDelay(Duration.seconds(0.5));
            tooltip.setHideDelay(Duration.seconds(0));
            Tooltip.install(circle, tooltip);
            root.getChildren().add(circle);
        });
        
        //Mouse event
        setMouseEvent(root, circles, vboxinfomation);
        
        // Main GUI
        Scene mainScene = new Scene(stack_pane, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Add the stuff and set the primary stage
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        
    }
    
    private void windowSetUp (BorderPane window) {
        window.setStyle("-fx-background-color: rgba(105,105,105, 0.9);");
        Rectangle box = new Rectangle(450, 320, 500, 160);
        window.getChildren().add(box);
        box.setFill(Color.WHITE);
    }
    

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }


    private Label addLabel(String fillText, int size) {
        Label label = new Label(fillText);
        label.setTextFill(Color.BLACK);
        label.setFont(new Font(size));
        return label;
    }
    
    private void setMouseEvent(Node node, Map<String,Circle> circles,VBox vboxinfomation) { 
            node.setOnMouseClicked(e -> {
                circles.forEach((State, bounds) -> {
                    if(check == true) {
                        if (bounds.contains(e.getX(), e.getY())&& e.getClickCount() == 1) {
                            vboxinfomation.getChildren().clear();
                            vboxinfomation.getChildren().addAll(
                                    addLabel("Total Cases in US: "+ reader.getTotalCases(), 22),
                                    addLabel("Total Recovered in US: "+ reader.getTotalRecovered(), 22),
                                    addLabel("Total Deaths in US: "+ reader.getTotalDeaths(), 22),
                                    addLabel("State: "+State, 20),
                                    addLabel("Total Cases in State: "+reader.getTotalCases(State), 20), 
                                    addLabel("Recovered: "+reader.getTotalRecovered(State), 18), 
                                    addLabel("Deaths: "+reader.getTotalDeaths(State), 18));
                            
                        }
                        if (bounds.contains(e.getX(), e.getY())&& e.getClickCount() == 2) {
                            vboxinfomation.getChildren().clear();
                            vboxinfomation.getChildren().addAll(
                                    addLabel("Total Cases in US: "+ reader.getTotalCases(), 22),
                                    addLabel("Total Recovered in US: "+ reader.getTotalRecovered(), 22),
                                    addLabel("Total Deaths in US: "+ reader.getTotalDeaths(), 22),
                                    addLabel("State: ", 20),
                                    addLabel("Total Cases in State: ", 20), 
                                    addLabel("Recovered: ", 18), 
                                    addLabel("Deaths: ", 18));
                        }
                    }else {
                        
                    }
                });
            });
    }

    private void addAllState(Map<String,Circle> circles) {
        circles.put("Alabama",new Circle(994, 537, 10));
        circles.put("Alaska", new Circle(405, 635, 10));
        circles.put("Arizona", new Circle(499, 469, 10));
        circles.put("Arkansas", new Circle(877, 488, 10));
        circles.put("California", new Circle(361, 395, 10));
        circles.put("Colorado", new Circle(629, 386, 10));
        circles.put("Connecticut", new Circle(1221, 273, 10));
        circles.put("Delaware", new Circle(1186, 345, 10));
        //circles.put("Dist of Columbia", new Circle(1020, 355, 10));
        circles.put("Florida", new Circle(1118, 651, 10));
        circles.put("Georgia", new Circle(1057, 528, 10));
        circles.put("Hawaii", new Circle(550, 687, 10));
        circles.put("Idaho", new Circle(480, 215, 10));
        circles.put("Illinois", new Circle(930, 366, 10));
        circles.put("Indiana", new Circle(987, 357, 10));
        circles.put("Iowa", new Circle(852, 322, 10));
        circles.put("Kansas", new Circle(761, 404, 10));
        circles.put("Kentucky", new Circle(1028, 390, 10));
        circles.put("Louisiana", new Circle(876, 575, 10));
        circles.put("Maine", new Circle(1251,186, 10));
        circles.put("Maryland", new Circle(1160, 346, 10));
        circles.put("Massachusetts", new Circle(1232, 250, 10));
        circles.put("Michigan", new Circle(1008, 285, 10));
        circles.put("Minnesota", new Circle(833, 211, 10));
        circles.put("Mississippi", new Circle(931, 531, 10));
        circles.put("Missouri", new Circle(876, 410, 10));
        circles.put("Montana", new Circle(597, 192, 10));
        circles.put("Nebraska", new Circle(741, 331, 10));
        circles.put("Nevada", new Circle(426, 342, 10));
        circles.put("New Hampshire", new Circle(1228, 219, 10));
        circles.put("New Jersey", new Circle(1197, 316, 10));
        circles.put("New Mexico", new Circle(607, 494, 10));
        circles.put("New York", new Circle(1170, 224, 10));
        circles.put("North Carolina", new Circle(1150, 452, 10));
        circles.put("North Dakota", new Circle(734, 178, 10));
        circles.put("Ohio", new Circle(1045, 350, 10));
        circles.put("Oklahoma", new Circle(782, 478, 10));
        circles.put("Oregon", new Circle(387, 216, 10));
        circles.put("Pennsylvania", new Circle(1108, 328, 10));
        circles.put("Rhode Island", new Circle(1301, 272, 10));
        circles.put("South Carolina", new Circle(1110, 497, 10));
        circles.put("South Dakota", new Circle(733, 260, 10));
        circles.put("Tennessee", new Circle(942, 453, 10));
        circles.put("Texas", new Circle(732, 576, 10));
        circles.put("Utah", new Circle(516, 365, 10));
        circles.put("Vermont", new Circle(1205, 214, 10));
        circles.put("Virginia", new Circle(1113, 400, 10));
        circles.put("Washington", new Circle(409, 103, 10));
        circles.put("West Virginia", new Circle(1081, 390, 10));
        circles.put("Wisconsin", new Circle(916, 259, 10));
        circles.put("Wyoming", new Circle(605, 288, 10));
    }
    
    
}


