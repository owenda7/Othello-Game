import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Group;

import javafx.scene.control.Label;
import javafx.scene.control.Button;

import javafx.scene.shape.Rectangle;

import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.geometry.Insets;
import javafx.scene.shape.Circle;

import javax.swing.text.View;

public class Othello extends Application {
    /**
     * Various Object and Container Declarations. If you add a container, please add it here
     */
    private Game g;
    private Board b;
    public GridPane gridPane; //GridPane that will hold 64 tilePane objects
    public BorderPane borderPane = new BorderPane(); //BorderPane will hold all other containers(VBox and HBox)
    Scene scene;
    HBox topHBox = new HBox();//holds points VBoxes and titleVBox
    HBox bottomHBox = new HBox();//BottomHBow will hold buttons such as new game,pass turn, timer
    VBox titleVBox = new VBox();//TitleHBox will hold title
    VBox messageVBox = new VBox();//will contain messages that update, such as whose turn, if the game in ended, if a move is invalid, ect.
    VBox p1PointsVBox = new VBox(5);//VBox to contain players points
    VBox p2PointsVBox = new VBox(5);
    VBox menuVBox = new VBox();//will hold the menu of options
    VBox ruleVBox = new VBox();//will contain rules
    Button newGameButton, passTurnButton,exitButton;//buttons
    Pane menuPane, rulePane; //panes to hold text
    /**
     * Color and Background Declarations. Feel free to change if you want a different aesthetic
     */
    Color neonLightBlue = Color.rgb(102,252,241);
    Background neonLightBlueBackground = new Background(new BackgroundFill(Color.web("#66fcf1"), CornerRadii.EMPTY, Insets.EMPTY));
    Background darkBlueBackground = new Background(new BackgroundFill(Color.web("#1f2833"), CornerRadii.EMPTY, Insets.EMPTY));
    Background pastelRedBackground = new Background(new BackgroundFill(Color.web("#FF9AA2"), CornerRadii.EMPTY, Insets.EMPTY));
    Background pastelDarkRedBackground = new Background(new BackgroundFill(Color.web("#FF3A49"), CornerRadii.EMPTY, Insets.EMPTY));
    Background mediumBlueBackground = new Background(new BackgroundFill(Color.web("#429E9D"), CornerRadii.EMPTY, Insets.EMPTY));
    Background darkGreenBackground = new Background(new BackgroundFill(Color.web("#0A3A2A"), CornerRadii.EMPTY, Insets.EMPTY));
    Border neonLightBlueBorder = new Border(new BorderStroke(neonLightBlue,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    Border darkGreenBorder = new Border(new BorderStroke(Color.web("#0A3A2A"),
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    @Override
    public void start(Stage primaryStage){
        b = new Board();
        //TODO:: Change. This is temporary because I'm confused
        ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(new Player(1,"human"),new Player(2,"human")));
        g = new Game(b,players);

        //TODO:: Add timer class and create timer

        /**
         *
         *
         * Various Buttons. If you add a button please add it here.
         *
         *
         */
        newGameButton = new Button("New Game");
        newGameButton.setBackground(neonLightBlueBackground);
        newGameButton.setOnMouseEntered(this::mouseEnterButton);
        newGameButton.setOnMouseExited(this::mouseExitButton);
        newGameButton.setOnMouseReleased(this::mouseReleasedButton);
        newGameButton.setOnMousePressed(this::mousePressedButton);
        newGameButton.setOnAction(event->
        {
            //TODO::Add fucntionality
            newGameButton.setBackground(pastelDarkRedBackground);

        });
        passTurnButton = new Button("Pass Turn");
        passTurnButton.setBackground(neonLightBlueBackground);
        passTurnButton.setOnMouseEntered(this::mouseEnterButton);
        passTurnButton.setOnMouseExited(this::mouseExitButton);
        passTurnButton.setOnMouseReleased(this::mouseReleasedButton);
        passTurnButton.setOnMousePressed(this::mousePressedButton);
        passTurnButton.setOnAction(event->
        {
            passTurnButton.setBackground(pastelDarkRedBackground);
            //TODO::ADD functionality
        });

        exitButton = new Button("Exit Program");
        exitButton.setBackground(neonLightBlueBackground);
        exitButton.setOnMouseEntered(this::mouseEnterButton);
        exitButton.setOnMouseExited(this::mouseExitButton);
        exitButton.setOnMouseReleased(this::mouseReleasedButton);
        exitButton.setOnMousePressed(this::mousePressedButton);
        exitButton.setOnAction(event ->
        {
            newGameButton.setBackground(pastelDarkRedBackground);
            Platform.exit();

        });



        /**
         *
         * A lot of messy container stuff. All Boxes and Texts
         *
         */
        //Construct VBoxes for players points -> will be contained in topHBox
        Circle wCircle = new Circle(30, Color.WHITE);//Symbols that represent player
        Circle bCircle = new Circle(30, Color.BLACK);
        Text p1Points = new Text("2 Pieces");//TODO::Construct a function to update these values
        p1Points.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        p1Points.setFill(neonLightBlue);
        Text p2Points = new Text("2 Pieces");
        p2Points.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        p2Points.setFill(neonLightBlue);
        p1PointsVBox.getChildren().addAll(bCircle,p1Points);//Add circles and texts to VBoxes
        p2PointsVBox.getChildren().addAll(wCircle,p2Points);
        p1PointsVBox.setAlignment(Pos.CENTER_LEFT);//set position in box
        p2PointsVBox.setAlignment(Pos.CENTER_RIGHT);
        p1PointsVBox.setMinWidth(300);//set width to keep from squishing containers together
        p2PointsVBox.setMinWidth(300);

        //Construct titleVBox and title text -> will be contained in topHBox
        Text titleText = new Text("Othello");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        titleText.setFill(neonLightBlue);
        titleVBox.setMinHeight(20);
        titleVBox.setMaxHeight(20);
        titleVBox.setAlignment(Pos.CENTER);
        titleVBox.getChildren().add(titleText);

        //Construct topHBox -> contains points and title
        //TODO:: Add Points functionality and whose turn fucntionality
        topHBox.setBackground(darkBlueBackground);
        topHBox.setStyle("fx-border-width:2px;"+ "-fx-border-color:#45a29e;");
        topHBox.setMinHeight(150);
        topHBox.setMaxHeight(150);
        topHBox.setAlignment(Pos.CENTER);
        topHBox.getChildren().addAll(p1PointsVBox,titleVBox,p2PointsVBox);

        //Construct bottomHBox -> contains buttons and timer(when I add it)
        //TODO:: Addd new game and timer
        bottomHBox.setPadding(new Insets(20));
        bottomHBox.setSpacing(15);
        bottomHBox.setBackground(darkBlueBackground);
        bottomHBox.setStyle("fx-border-width:2px;"+ "-fx-border-color:#45a29e;");
        bottomHBox.setMinHeight(150);
        bottomHBox.setMaxHeight(150);
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.getChildren().addAll(newGameButton,passTurnButton,exitButton);

        //Construct menuVBox -> will hold the menu of options
        Text menuText = new Text("Menu");
        menuText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        menuText.setFill(neonLightBlue);
        menuPane = new Pane(menuText);
        menuVBox.getChildren().add(menuText);
        menuVBox.setPadding(new Insets(20,100,20,100));
        menuVBox.setStyle("-fx-border-color:#45a29e; -fx-border-width : 0 2 0 2 ");
        menuVBox.setMinWidth(300);
        menuVBox.setMaxWidth(300);

        //Construct ruleVBox -> will hold the menu of options
        Text ruleText = new Text();
        ruleText.setText("\n          Rules:\n\n-The objective of the\n game is to have the\n most pieces flipped\n to your color when\n the game ends" +
                "\n\n-Flip opponents pieces\n to your color by\n sandwiching their\n pieces between\n your own. This can\n be done horizontally," +
                "\n vertically or diagonally\n\n"+ "-You can only place\n pieces adjacent to\n " +
                "your opponents pieces\n in a move that\n that would sandwich\n one or more of \n their pieces\n\n-The game ends when\n no more valid\n" +
                " moves exist\n\n   Good Luck!");
        ruleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        ruleText.setFill(neonLightBlue);
        rulePane = new Pane(ruleText);
        ruleVBox.getChildren().add(ruleText);
        ruleVBox.setPadding(new Insets(20,50,20,50));
        ruleVBox.setStyle("-fx-border-color:#45a29e; -fx-border-width : 0 2 0 2 ");
        ruleVBox.setMinWidth(300);
        ruleVBox.setMaxWidth(300);

        //Construct board and get gridPane
        VBox middle = new VBox();
        setGridPane();

        //Construct messageVBox and message text. Message text will be updated through out game
        Text messageText = new Text("Messages");
        messageText.setFill(neonLightBlue);
        messageText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        messageVBox.setMaxHeight(50);
        messageVBox.setMinHeight(50);
        messageVBox.setMinWidth(400);
        messageVBox.setAlignment(Pos.CENTER);
        messageVBox.setPadding(new Insets(0,0,20,0));
        messageVBox.getChildren().add(messageText);
        middle.getChildren().addAll(messageVBox,gridPane);
        middle.setAlignment(Pos.CENTER);
        middle.setPadding(new Insets(10));



        //Add all panes to borderPane and set style properties of borderPane
        borderPane.setTop(topHBox);
        borderPane.setRight(ruleVBox);
        borderPane.setLeft(menuVBox);
        borderPane.setCenter(middle);
        borderPane.setBottom(bottomHBox);
        borderPane.setBackground(darkBlueBackground);

        //Create Scene and setScene
        scene = new Scene(borderPane,1350,1050);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Othello");
        primaryStage.show();
    }

    public void handleClick(MouseEvent e) {

    }
    public void mouseEnterButton(MouseEvent e){
        Button b = (Button)e.getSource();
        b.setBackground(pastelRedBackground);
    }
    public void mouseExitButton(MouseEvent e){
        Button b = (Button)e.getSource();
        b.setBackground(neonLightBlueBackground);
    }
    public void mousePressedButton(MouseEvent e){
        Button b = (Button)e.getSource();
        b.setBackground(pastelDarkRedBackground);
    }
    public void mouseReleasedButton(MouseEvent e){
        Button b = (Button)e.getSource();
        b.setBackground(pastelRedBackground);
    }
    public void mouseEnter(MouseEvent e){
        Color playerColor = Color.BLACK;
        TilePane tp=(TilePane)e.getSource();
        tp.changePieceColor(playerColor);
        tp.setBorder(neonLightBlueBorder);

    }
    public void mouseExit(MouseEvent e) {
        Color playerColor = Color.BLACK;
        TilePane tp = (TilePane) e.getSource();
        tp.setPlayerControl(tp.getControl());
        tp.setBorder(darkGreenBorder);
    }
    //Method will call drawBoard and set style of properties of gridPane
    public void setGridPane()
    {
        gridPane=drawBoard();
        gridPane.setMaxHeight(600);
        gridPane.setMinHeight(600);
        gridPane.setMaxWidth(600);
        gridPane.setMinWidth(600);
        gridPane.setStyle("-fx-border-color:#0A3A2A;"+"-fx-border-width:6px;");//Give GridPane a border and color it

    }
    //Method will use game class to get tiles and construct tilePanes, and add to gridPane
    public GridPane drawBoard(){
        GridPane gp = new GridPane();
        TilePane tp;
        Square s;
        for(int r=1; r<9;r++)
        {
            for(int c=1;c<9;c++)
            {
                s=g.getSquare(r,c);//get Tile object from game(which gets it from board)
                tp=new TilePane(s);
                tp.setOnMouseClicked(this::handleClick);
                tp.setOnMouseEntered(this::mouseEnter);
                tp.setOnMouseExited(this::mouseExit);
                gp.add(tp,c,r);
            }
        }
        return gp;
    }
    public static void main(String[] args){
        launch(args);
    }

}
