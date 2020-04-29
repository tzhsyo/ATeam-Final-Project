package application;



import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: a3 team project final
// Files: Main.java, CVSReader.java
//
// Author: 
// 1. Jackie Chang, Lecture 002, and Jchang66@wisc.edu
// 2. Zi Lee, Lecture 001, and jlee828@wisc.edu
// 3. Jennifer Park, Lecture 002, and jpark536@wisc.edu
// 4. Tzyy Hsien Young, Lecture 002, and tyoung23@wisc.edu
// Lecturer's Name: Deb Deppeler
//
/////////////////////////////////////////////////////////////////////////////

public class Main extends Application {

    private static final int WINDOW_WIDTH = 1400;
    private static final int WINDOW_HEIGHT = 800;
    private static final String APP_TITLE = "Cases of COVID-19 in the United States";
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

    /**
     * This method creates a new GUI and adding all element within the GUI
     * 
     * @param Stage - start the GUI 
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void start(Stage primaryStage){
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

        // Set tital
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
        Text inftx = new Text("Image source: www.clipart.email"); // set image sourse
        inftx.setX(1150);
        inftx.setY(790);

        // Add a Button in the bottom panel with the label "Done"
        Button button = new Button("Exit"); //progame exist
        Button buttonClose = new Button("Done"); //Close instruction
        Button buttonExitLoad = new Button("Load"); //Exit save page
        Button buttonSave = new Button("Save"); //Save data
        Button saveButtonExit = new Button("Done"); //Exit save page
        Button buttonOpen = new Button("Help"); //open instruction
        Button errorExit = new Button("Done"); //Close error
        Button buttonLoad = new Button("LoadFile"); //Open load page
        Button buttonUpadte = new Button("Update"); // Open save page
        
        Button openFile = new Button("Select a File");
        button.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        buttonOpen.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        buttonLoad.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        buttonUpadte.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        
        //ComboBox
        String data[] = {"Cases", "Recovered", "Deaths"};
        ComboBox<String> datas = new ComboBox<String>(FXCollections.observableArrayList(data));
        ComboBox<String> states = new ComboBox<String>(FXCollections.observableArrayList(state));
        
        //TextField
        Label textFieldlabel = new Label("New data:");
        TextField dataField = new TextField();
        dataField.setPromptText("Number only");
        dataField.setMaxWidth(150);
        
        //Pie chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis); // the bar graph
        XYChart.Series series1 = new XYChart.Series(); // bar 1
        series1.setName("Total Cases");;
        XYChart.Series series2 = new XYChart.Series(); // bar 2
        series2.setName("Total Recovered");
        XYChart.Series series3 = new XYChart.Series(); // bar 3
        series3.setName("Total Deaths");
        bc.getData().addAll(series1, series2, series3); 
        bc.setMaxSize(300, 450);

        /*
         * Add to layout management
         */
        /*
         * Hbox set up
         */
        bottomButton.getChildren().addAll(button, buttonOpen, buttonLoad, buttonUpadte);
        UpDatebutton.getChildren().addAll(buttonSave,saveButtonExit);
        UpDatebutton.setAlignment(Pos.CENTER); //set to the center
        updataTextField.getChildren().addAll(textFieldlabel,dataField);
        updataTextField.setAlignment(Pos.CENTER);
        
        /*
         * Vbox set up
         */
        //information when not data load
        vboxinfomation.getChildren().addAll( // infomation going to display on the left of main page
                addLabel("Total Cases in US:", 22),
                addLabel("Total Recovered in US:", 22),
                addLabel("Total Deaths in US:", 22),
                addLabel("State: ", 20),
                addLabel("Total Cases in State: ", 20), 
                addLabel("New cases: ", 18),
                addLabel("Recovered: ", 18), 
                addLabel("Deaths: ", 18),
                addLabel("Not File Load", 18),
                bc
                );     
        //Start page instruction
        vboxOverLay.getChildren().addAll(
                addLabel("Program instruction", 14), 
                addLabel("Clicked red circle to display statistics on the left.",12),
                addLabel("Use the \"Exit\" button to exit the program.", 12),
                addLabel("Use the \"Done\" button to exit the current page.",12),
                addLabel("Use the \"Help\" button to reopen the instruction page.",12),
                addLabel("Use the \"LoadFile\" button to open load file page.", 12),
                addLabel("Use the \"Update\" button to open data upadate page.", 12),
                addLabel("Use the \"Save\" button to save update data.",12),
                 buttonClose);
        vboxOverLay.setAlignment(Pos.CENTER);
        //Load page element
        vboxLoad.getChildren().addAll(addLabel("Load File", 18), openFile, buttonExitLoad);
        vboxLoad.setAlignment(Pos.CENTER);
        //Upadte page element
        vboxUpadte.getChildren().addAll(addLabel("Update", 18), datas ,states,updataTextField,UpDatebutton);
        vboxUpadte.setAlignment(Pos.CENTER);
        //Error page element
        vboxError.getChildren().addAll(
                addLabel("Error", 18),
                addLabel("File format contain incorrect ", 12),errorExit);
        vboxError.setAlignment(Pos.CENTER);
        
        
        /*
         * BorderPane
         */
        // Map layer
        BorderPane.setMargin(iv,new Insets(0,0,0,200));
        underlay.setCenter(iv); // add map to main page 
        underlay.getChildren().add(inftx);
        underlay.setStyle("-fx-background-color: #F7D576; -fx-text-fill: white;");
        // Main pages
        root.setTop(tital);
        root.setLeft(vboxinfomation);
        BorderPane.setMargin(vboxinfomation,new Insets(10,0,0,10));
        root.setBottom(bottomButton);
        root.setStyle("-fx-background-color: TRANSPARENT; -fx-text-fill: white;");
        // StartOverlay //instruction page
        windowSetUp(overLay);
        overLay.setCenter(vboxOverLay);
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
        
        /*
         *  Add to stack pane
         */
        stack_pane = new StackPane(underlay,root,loadWindow, overLay);

        /*
         *  Button Action
         */
        // Start overlay
        buttonClose.setOnAction(e -> stack_pane.getChildren().remove(overLay)); //close
        buttonOpen.setOnAction(e -> stack_pane.getChildren().add(overLay)); //open
        // Load overlay
        buttonLoad.setOnAction(e -> stack_pane.getChildren().add(loadWindow)); //open
        buttonExitLoad.setOnAction(e -> {stack_pane.getChildren().remove(loadWindow); //close
            if(check == true) { // update lable after file load
                updateInfo(vboxinfomation,series1,series2,series3, bc); //updata display info after close
            }
            });
        // Error overlay
        errorExit.setOnAction(e -> stack_pane.getChildren().remove(errorWindow)); //close
        // Upadte overlay
        buttonUpadte.setOnAction(e -> {stack_pane.getChildren().add(updateWindow); // open
            if(check == false) { // not file load into pragam
                vboxUpadte.getChildren().clear();
                vboxUpadte.getChildren().addAll(addLabel("Update", 18), addLabel("Require load file", 14),
                        datas ,states,updataTextField,UpDatebutton);  
            }
            });
        saveButtonExit.setOnAction(e -> {stack_pane.getChildren().remove(updateWindow); //close
            vboxUpadte.getChildren().clear();
            vboxUpadte.getChildren().addAll(addLabel("Update", 18), datas ,states,updataTextField,UpDatebutton);
            if(check == true) { // update lable after file load
                updateInfo(vboxinfomation,series1,series2,series3, bc); //updata display info after close
            }
            });
        buttonSave.setOnAction(e ->  { // save data
                if(check == true) { // checking file loaded
                    String newData = dataField.getText(); //User input
                    String upDataType = datas.getValue(); // Type on updata
                    String stateUpDate = states.getValue(); // which state
                    try {
                        int value = Integer.parseInt(newData);
                        if(newData != null&&upDataType != null) { //update data
                            reader.addNew(upDataType, stateUpDate, value); //add the back end 
                            vboxUpadte.getChildren().clear();
                            vboxUpadte.getChildren().addAll(addLabel("Update", 18), addLabel("Success updated", 14),
                                datas ,states,updataTextField,UpDatebutton);
                        }else { // ComboBox not pick
                            vboxUpadte.getChildren().clear();
                            vboxUpadte.getChildren().addAll(addLabel("Update", 18), addLabel("Invalid update", 14),
                                    datas ,states,updataTextField,UpDatebutton);
                        }
                    }catch(NumberFormatException c){ //input contain more than number character
                        vboxUpadte.getChildren().clear();
                        vboxUpadte.getChildren().addAll(addLabel("Update", 18), addLabel("Invalid update", 14),
                                datas ,states,updataTextField,UpDatebutton);
                    }
                }
        });
        // Exit progam
        button.setOnAction(e -> {Platform.exit();try {
            reader.printFile(); // print update log
        } catch (IOException e1) {
        }});
        //Get file and check parses into Map tree//load file
        openFile.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(primaryStage); //open file picker
                        if (file != null) {
                            filePath = file.getPath(); // get file path
                            if(!filePath.contains(".csv")) { // check if is .csv
                                filePath = "Unable to load";
                                stack_pane.getChildren().add(errorWindow);
                            }else {
                                reader = new CSVReader(filePath); //pass to backend load file
                                if(reader.getCheck() == true) {// check it load all current
                                    check = true;
                                } else {
                                    stack_pane.getChildren().add(errorWindow);
                                }
                            }
                            vboxLoad.getChildren().clear();
                            vboxLoad.getChildren().addAll( // show the loaded file
                                    addLabel("Load File", 18), 
                                    openFile,
                                    addLabel("File Name: "+filePath, 12),
                                    buttonExitLoad);
                        }
                    }
                });
        
        /*
         *  Draw circle
         */
        final Map<String,Circle> circles = new HashMap<>();
        addAllState(circles); // add all state and coordinate on map
        circles.forEach((String, bounds) -> { // for each element in tree map
            Circle circle = new Circle(bounds.getCenterX(), bounds.getCenterY(),bounds.getRadius());
            circle.setStroke(Color.RED);
            circle.setFill(Color.RED.deriveColor(1, 1, 1, 0.5)); // set color
            Tooltip tooltip = new Tooltip(String); // set up toolip
            tooltip.setShowDelay(Duration.seconds(0.5));
            tooltip.setHideDelay(Duration.seconds(0));
            Tooltip.install(circle, tooltip); // add toolip
            root.getChildren().add(circle); // add to Main BorderPane
        });
         
        /*
         *  Mouse event
         */
        setMouseEvent(root, circles, vboxinfomation,series1,series2,series3, bc);
        
        // Main GUI
        Scene mainScene = new Scene(stack_pane, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Add the stuff and set the primary stage
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        
    }
   
    /**
     * Main driver
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * This method creates a new GUI and adding all element within the GUI
     * 
     * @param VBox - infomation display on main page
     * @param XYChart.Series - bar in bar grapgh #1
     * @param XYChart.Series - bar in bar grapgh #2
     * @param XYChart.Series - bar in bar grapgh #3
     * @param BarChart<String,Number> - bar grapgh
     */
    @SuppressWarnings("rawtypes")
    private void updateInfo(
            VBox vboxinfomation,XYChart.Series series1,XYChart.Series series2,XYChart.Series series3,BarChart<String,Number> bc) {
        series1.getData().clear(); // clear all data store
        series2.getData().clear();
        series3.getData().clear();
        bc.getData().clear(); // clear bar graph
        bc.setTitle(null); 
        vboxinfomation.getChildren().clear(); // update display info after close load or save
        vboxinfomation.getChildren().addAll(
                addLabel("Total Cases in US: "+ reader.getTotalCases(), 22),
                addLabel("Total Recovered in US: "+ reader.getTotalRecovered(), 22),
                addLabel("Total Deaths in US: "+ reader.getTotalDeaths(), 22),
                addLabel("State: ", 20),
                addLabel("Total Cases in State: ", 20), 
                addLabel("Recovered: ", 18), 
                addLabel("Deaths: ", 18),
                bc);    
    }
    
    /**
     * This method set up window style layout
     * 
     * @param BorderPane window - borderPane that going to set up
     */
    private void windowSetUp (BorderPane window) {
        window.setStyle("-fx-background-color: rgba(105,105,105, 0.9);");
        Rectangle box = new Rectangle(450, 320, 500, 160);
        window.getChildren().add(box);
        box.setFill(Color.WHITE);
    }

    /**
     * This method set up display label 
     * 
     * @param Text - label text
     * @param Size - label size
     * @return label - set up label
     */
    private Label addLabel(String fillText, int size) {
        Label label = new Label(fillText);
        label.setTextFill(Color.BLACK);
        label.setFont(new Font(size));
        return label;
    }
    
    /**
     * This method set up Mouse event and update display infomation
     * 
     * @param Node - Layer mouse is track
     * @param Map<String,Circle> - tree map of all circle
     * @param VBox - display information
     * @param XYChart.Series - bar in bar grapgh #1
     * @param XYChart.Series - bar in bar grapgh #2
     * @param XYChart.Series - bar in bar grapgh #3
     * @param BarChart<String,Number> - bar grapgh
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setMouseEvent(
            Node node, Map<String,Circle> circles,VBox vboxinfomation,XYChart.Series series1,XYChart.Series series2,XYChart.Series series3,BarChart<String,Number> bc) { 
            node.setOnMouseClicked(e -> { // new mouse event
                circles.forEach((State, bounds) -> {
                    if(check == true) { // load date found 
                        // on one of the state circle with one chlicked
                        if (bounds.contains(e.getX(), e.getY())&& e.getClickCount() == 1) {
                            series1.getData().clear(); // clear all old data in bar graph
                            series2.getData().clear();
                            series3.getData().clear();
                            bc.getData().clear();
                            // set new data into bar graph
                            series1.getData().add(new XYChart.Data("Cases", reader.getTotalCases(State)));
                            series2.getData().add(new XYChart.Data("Recovered", reader.getTotalRecovered(State)));
                            series3.getData().add(new XYChart.Data("Deaths", reader.getTotalDeaths(State)));
                            bc.setTitle(State);
                            bc.getData().addAll(series1, series2, series3);
                            vboxinfomation.getChildren().clear();
                            vboxinfomation.getChildren().addAll( // update new display infomation 
                                    addLabel("Total Cases in US: "+ reader.getTotalCases(), 22),
                                    addLabel("Total Recovered in US: "+ reader.getTotalRecovered(), 22),
                                    addLabel("Total Deaths in US: "+ reader.getTotalDeaths(), 22),
                                    addLabel("State: "+State, 20),
                                    addLabel("Total Cases in State: "+reader.getTotalCases(State), 20), 
                                    addLabel("Recovered: "+reader.getTotalRecovered(State), 18), 
                                    addLabel("Deaths: "+reader.getTotalDeaths(State), 18),
                                    bc);
                            
                        }
                        // on one of the state circle with double chlicked 
                        if (bounds.contains(e.getX(), e.getY())&& e.getClickCount() == 2) {
                            series1.getData().clear(); // clear out all data add to display 
                            series2.getData().clear();
                            series3.getData().clear();
                            bc.getData().clear();
                            bc.getData().addAll(series1, series2, series3);
                            updateInfo(vboxinfomation,series1,series2,series3,bc);
                        }
                    }
                });
            });
    }

    /**
     * This method set up all state and coordinate on map into Treemap
     * 
     * @param Map<String,Circle> - tree map of all circle
     */
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
        circles.put("Washington", new Circle(415, 150, 10));
        circles.put("West Virginia", new Circle(1081, 390, 10));
        circles.put("Wisconsin", new Circle(916, 259, 10));
        circles.put("Wyoming", new Circle(605, 288, 10));
    }
    
    
}


