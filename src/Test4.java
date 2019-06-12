import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.DOWN;



public class Test4 extends Application {
    private StackPane[][] a = {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
    };
    private GridPane board = new GridPane();
    private StackPane dualCenter = new StackPane();
    public void start (Stage ps) {

        BorderPane background = new BorderPane();
        dualCenter = new StackPane();

        dualCenter.getChildren().add(board);

        board.setStyle("-fx-background-color: #bbada0");

        // Top
        HBox topBar = new HBox();
        topBar.setPrefHeight(150);
        topBar.setSpacing(200);
        topBar.setAlignment(Pos.CENTER);
        GridPane scoreAndBest = new GridPane();
        VBox scorePane = new VBox();
        VBox bestScorePane = new VBox();

        Text text = new Text("2048");
        text.setFont(Font.font("Calibri", FontWeight.BOLD, 80));
        text.setFill(Color.web("#776e65"));

        Text scoreTitle = new Text("Score");
        scoreTitle.setFont(Font.font("Calibri", 30));
        scoreTitle.setFill(Color.web("#eee4da"));

        Text scoreText = new Text("0");
        int score = 0;
        scoreText.setFont(Font.font("Calibri", 30));
        scoreText.setFill(Color.web("#ffffff"));

        scoreAndBest.add(scorePane, 0, 0);
        scoreAndBest.add(bestScorePane, 1, 0);
        scoreAndBest.setHgap(20);
        scoreAndBest.setVgap(50);

        scorePane.setPrefHeight(70);
        scorePane.setPrefWidth(75);
        scorePane.getChildren().add(scoreTitle);
        scorePane.getChildren().add(scoreText);
        scorePane.setAlignment(Pos.CENTER);
        scorePane.setStyle("-fx-background-color: #bbada0; -fx-background-radius: 10;");

        Text bestScore = new Text("Best");
        bestScore.setFont(Font.font("Calibri", 30));
        bestScore.setFill(Color.web("#eee4da"));

        Text bestScoreText = new Text("0");
        bestScoreText.setFont(Font.font("Calibri", 30));
        bestScoreText.setFill(Color.web("#ffffff"));

        bestScorePane.setPrefWidth(75);
        bestScorePane.setPrefHeight(70);
        bestScorePane.getChildren().add(bestScore);
        bestScorePane.getChildren().add(bestScoreText);
        bestScorePane.setAlignment(Pos.CENTER);
        bestScorePane.setStyle("-fx-background-color: #bbada0; -fx-background-radius: 10;");

        StackPane newGame = makeNewGame();
        newGame.setOnMouseClicked(e -> resetBoard());
        newGame.setLayoutX(500);
        newGame.setLayoutY(100);
        scoreAndBest.getChildren().add(newGame);


        topBar.getChildren().addAll(text, scoreAndBest);

        //Starting the instructions
        Pane instructionPane = new Pane();
        instructionPane.setPrefHeight(120);
        background.setBottom(instructionPane);

        //instruction header text:
        Text instructionsHead = new Text( "HOW TO PLAY: ");
        instructionsHead.setFont(Font.font ("Calibri", FontWeight.BOLD, 20));
        instructionsHead.setFill(Color.web("#776e65"));
        instructionsHead.setStroke(Color.web("#776e65"));
        instructionsHead.xProperty().bind(instructionPane.widthProperty().divide(2).subtract(200));
        instructionsHead.yProperty().bind(instructionPane.heightProperty().divide(2).subtract(20));

        //instructions body text:
        Text instructionsOne = new Text("Use your arrow keys to move the tiles.");
        instructionsOne.setFont(Font.font ("Calibri",15));
        instructionsOne.setFill(Color.web("#776e65"));
        instructionsOne.setStroke(Color.web("#776e65"));
        instructionsOne.xProperty().bind(instructionsHead.xProperty().add(130));
        instructionsOne.yProperty().bind(instructionPane.heightProperty().divide(2).subtract(20));

        Text instructionsTwo = new Text("When two tiles with the same " +
                "number touch, they merge into one!");
        instructionsTwo.setFont(Font.font ("Calibri",15));
        instructionsTwo.setFill(Color.web("#776e65"));
        instructionsTwo.setStroke(Color.web("#776e65"));
        instructionsTwo.xProperty().bind(instructionPane.widthProperty().divide(2).subtract(200));
        instructionsTwo.yProperty().bind(instructionPane.heightProperty().divide(2).subtract(5));
        instructionPane.getChildren().addAll(instructionsHead, instructionsOne, instructionsTwo);

        // Sides
        VBox side1 = new VBox();
        VBox side2 = new VBox();
        side1.setPrefWidth(131.5);
        side2.setPrefWidth(131.5);

        // Center
        // This is a color: 776e65


        board.setHgap(5);
        board.setVgap(5);
        board.setPadding(new Insets(5, 5, 5, 5));

        background.setCenter(dualCenter);

        for (int row=0; row<4; row++) {
            for (int column = 0; column < 4; column++) {
                board.add(makeRectangle(), row, column);
            }
        }

        for (int j = 0; j < 2; j++) {
            int row = (int)(Math.random()*4);
            int column = (int)(int)(Math.random()*4);
            int twoOrFour = (int)(Math.random()*10+1);
            while (a[row][column] != null) {
                column = (int)(Math.random()*4);
                row = (int)(Math.random()*4);
            }
            if (twoOrFour - 10 == 0) {
                StackPane s4 = makeS4();
                a[row][column] = s4;
            }
            else {
                StackPane s2 = makeS2();
                a[row][column] = s2;
            }
            board.add(a[row][column], column, row);
        }

        board.setOnKeyPressed(e -> {

            // What happens if they click the right arrow key
            if (e.getCode() == RIGHT) {
                int ifMoved = 0;
                for (int row = 0; row < 4; row++) {
                    for (int c = 3; c >= 0; c--) {
                        if (a[row][c] != null && c != 3) {

                            for (int i = c + 1; i < 4; i++) {
                                if (a[row][i] != null) {
                                    if (a[row][c].getAccessibleText().equals(a[row][i].getAccessibleText())) {
                                        ifMoved++;
                                        board.add(makeRectangle(), c, row);
                                        if (a[row][c].getAccessibleText().equals("2")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS4(), i, row);
                                            a[row][i] = makeS4();
                                            scoreText.setText((Integer.toString(score)));
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("4")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS8(), i, row);
                                            a[row][i] = makeS8();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("8")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS16(), i, row);
                                            a[row][i] = makeS16();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("16")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS32(), i, row);
                                            a[row][i] = makeS32();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("32")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS64(), i, row);
                                            a[row][i] = makeS64();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("64")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS128(), i, row);
                                            a[row][i] = makeS128();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("128")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS256(), i, row);
                                            a[row][i] = makeS256();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("256")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS512(), i, row);
                                            a[row][i] = makeS512();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("512")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS1024(), i, row);
                                            a[row][i] = makeS1024();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("1024")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS2048(), i, row);
                                            a[row][i] = makeS2048();
                                            a[row][c] = null;
                                            break;
                                        }


                                        //do 4, 8, 16 etc.
                                    }
                                    else if ((c != i - 1 && !a[row][c].getAccessibleText().equals(a[row][i].getAccessibleText()))) {
                                        ifMoved++;
                                        board.add(makeRectangle(), c, row);
                                        if (a[row][c].getAccessibleText().equals("2")) {
                                            board.add(makeS2(), i - 1, row);
                                            a[row][i - 1] = makeS2();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("4")) {
                                            board.add(makeS4(), i - 1, row);
                                            a[row][i - 1] = makeS4();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("8")) {
                                            board.add(makeS8(), i - 1, row);
                                            a[row][i - 1] = makeS8();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("16")) {
                                            board.add(makeS16(), i - 1, row);
                                            a[row][i - 1] = makeS16();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("32")) {
                                            board.add(makeS32(), i - 1, row);
                                            a[row][i - 1] = makeS32();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("64")) {
                                            board.add(makeS64(), i - 1, row);
                                            a[row][i - 1] = makeS64();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("128")) {
                                            board.add(makeS128(), i - 1, row);
                                            a[row][i - 1] = makeS128();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("256")) {
                                            board.add(makeS256(), i - 1, row);
                                            a[row][i - 1] = makeS256();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("512")) {
                                            board.add(makeS512(), i - 1, row);
                                            a[row][i - 1] = makeS512();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("1024")) {
                                            board.add(makeS1024(), i - 1, row);
                                            a[row][i - 1] = makeS1024();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("2048")) {
                                            board.add(makeS2048(), i - 1, row);
                                            a[row][i - 1] = makeS2048();
                                            a[row][c] = null;
                                            break;
                                        }

                                    }
                                    else if (c == i - 1 && !a[row][c].getAccessibleText().equals(a[row][i].getAccessibleText())) {
                                        break;
                                    }
                                }
                                else if (i == 3 && a[row][i] == null) {
                                    ifMoved++;
                                    board.add(makeRectangle(), c, row);
                                    if (a[row][c].getAccessibleText().equals("2")) {
                                        board.add(makeS2(), 3, row);
                                        a[row][i] = makeS2();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("4")) {
                                        board.add(makeS4(), 3, row);
                                        a[row][i] = makeS4();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("8")) {
                                        board.add(makeS8(), 3, row);
                                        a[row][i] = makeS8();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("16")) {
                                        board.add(makeS16(), 3, row);
                                        a[row][i] = makeS16();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("32")) {
                                        board.add(makeS32(), 3, row);
                                        a[row][i] = makeS32();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("64")) {
                                        board.add(makeS64(), 3, row);
                                        a[row][i] = makeS64();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("128")) {
                                        board.add(makeS128(), 3, row);
                                        a[row][i] = makeS128();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("256")) {
                                        board.add(makeS256(), 3, row);
                                        a[row][i] = makeS256();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("512")) {
                                        board.add(makeS512(), 3, row);
                                        a[row][i] = makeS512();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("1024")) {
                                        board.add(makeS1024(), 3, row);
                                        a[row][i] = makeS1024();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("2048")) {
                                        board.add(makeS2048(), 3, row);
                                        a[row][i] = makeS2048();
                                        a[row][c] = null;
                                        break;
                                    }

                                    //do 4, 8, 16 etc.
                                }
                            }

                            //a[row][c] = null

                        }
                    }

                }
                if (ifMoved != 0) {
                    makeNew();
                }
            }

            // What happens if they click the left arrow key
            if (e.getCode() == LEFT) {
                int ifMoved = 0;
                for (int row = 0; row < 4; row++) {
                    for (int c = 0; c < 4; c++) {
                        if (a[row][c] != null && c != 0) {


                            for (int i = c - 1; i >= 0; i--) {
                                if (a[row][i] != null) {
                                    if (a[row][c].getAccessibleText().equals(a[row][i].getAccessibleText())) {
                                        ifMoved++;
                                        board.add(makeRectangle(), c, row);
                                        if (a[row][c].getAccessibleText().equals("2")) {
                                            board.add(makeS4(), i, row);
                                            a[row][i] = makeS4();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("4")) {
                                            board.add(makeS8(), i, row);
                                            a[row][i] = makeS8();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("8")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS16(), i, row);
                                            a[row][i] = makeS16();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("16")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS32(), i, row);
                                            a[row][i] = makeS32();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("32")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS64(), i, row);
                                            a[row][i] = makeS64();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("64")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS128(), i, row);
                                            a[row][i] = makeS128();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("128")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS256(), i, row);
                                            a[row][i] = makeS256();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("256")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS512(), i, row);
                                            a[row][i] = makeS512();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("512")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS1024(), i, row);
                                            a[row][i] = makeS1024();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("1024")) {
                                            board.add(makeRectangle(), i, row);
                                            board.add(makeS2048(), i, row);
                                            a[row][i] = makeS2048();
                                            a[row][c] = null;
                                            break;
                                        }

                                        //do 4, 8, 16 etc.
                                    }
                                    else if ((c != i + 1 && !a[row][c].getAccessibleText().equals(a[row][i].getAccessibleText()))) {
                                        ifMoved++;
                                        board.add(makeRectangle(), c, row);
                                        if (a[row][c].getAccessibleText().equals("2")) {
                                            board.add(makeS2(), i+1, row);
                                            a[row][i+1] = makeS2();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("4")) {
                                            board.add(makeS4(), i+1, row);
                                            a[row][i+1] = makeS4();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("8")) {
                                            board.add(makeS8(), i + 1, row);
                                            a[row][i + 1] = makeS8();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("16")) {
                                            board.add(makeS16(), i + 1, row);
                                            a[row][i + 1] = makeS16();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("32")) {
                                            board.add(makeS32(), i + 1, row);
                                            a[row][i + 1] = makeS32();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("64")) {
                                            board.add(makeS64(), i + 1, row);
                                            a[row][i + 1] = makeS64();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("128")) {
                                            board.add(makeS128(), i + 1, row);
                                            a[row][i + 1] = makeS128();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("256")) {
                                            board.add(makeS256(), i + 1, row);
                                            a[row][i + 1] = makeS256();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("512")) {
                                            board.add(makeS512(), i + 1, row);
                                            a[row][i + 1] = makeS512();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("1024")) {
                                            board.add(makeS1024(), i + 1, row);
                                            a[row][i + 1] = makeS1024();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("2048")) {
                                            board.add(makeS2048(), i + 1, row);
                                            a[row][i + 1] = makeS2048();
                                            a[row][c] = null;
                                            break;
                                        }

                                    }
                                    else if (!(a[row][c].getAccessibleText().equals(a[row][i].getAccessibleText())) && c == i + 1) {
                                        break;
                                    }

                                }
                                else if (i == 0 && a[row][i] == null) {
                                    ifMoved++;
                                    board.add(makeRectangle(), c, row);
                                    if (a[row][c].getAccessibleText().equals("2")) {
                                        board.add(makeS2(), 0, row);
                                        a[row][i] = makeS2();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("4")) {
                                        board.add(makeS4(), 0, row);
                                        a[row][i] = makeS4();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("8")) {
                                        board.add(makeS8(), 0, row);
                                        a[row][i] = makeS8();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("16")) {
                                        board.add(makeS16(), 0, row);
                                        a[row][i] = makeS16();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("32")) {
                                        board.add(makeS32(), 0, row);
                                        a[row][i] = makeS32();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("64")) {
                                        board.add(makeS64(), 0, row);
                                        a[row][i] = makeS64();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("128")) {
                                        board.add(makeS128(), 0, row);
                                        a[row][i] = makeS128();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("256")) {
                                        board.add(makeS256(), 0, row);
                                        a[row][i] = makeS256();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("512")) {
                                        board.add(makeS512(), 0, row);
                                        a[row][i] = makeS512();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("1024")) {
                                        board.add(makeS1024(), 0, row);
                                        a[row][i] = makeS1024();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("2048")) {
                                        board.add(makeS2048(), 0, row);
                                        a[row][i] = makeS2048();
                                        a[row][c] = null;
                                        break;
                                    }

                                }
                            }

                            //a[row][c] = null;

                        }

                    }

                }
                if (ifMoved != 0) {
                    makeNew();
                }
            }

            // What happens if they click the up arrow key
            if (e.getCode() == UP) {
                int ifMoved = 0;
                for (int c = 0; c < 4; c++) {
                    for (int row = 0; row < 4; row++) {
                        if (a[row][c] != null && row != 0) {


                            for (int i = row - 1; i >= 0; i--) {
                                if (a[i][c] != null) {
                                    if (a[row][c].getAccessibleText().equals(a[i][c].getAccessibleText())) {
                                        ifMoved++;
                                        board.add(makeRectangle(), c, row);
                                        if (a[row][c].getAccessibleText().equals("2")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS4(), c, i);
                                            a[i][c] = makeS4();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("4")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS8(), c, i);
                                            a[i][c] = makeS8();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("8")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS16(), c, i);
                                            a[i][c] = makeS16();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("16")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS32(), c, i);
                                            a[i][c] = makeS32();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("32")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS64(), c, i);
                                            a[i][c] = makeS64();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("64")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS128(), c, i);
                                            a[i][c] = makeS128();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("128")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS256(), c, i);
                                            a[i][c] = makeS256();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("256")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS512(), c, i);
                                            a[i][c] = makeS512();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("512")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS1024(), c, i);
                                            a[i][c] = makeS1024();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("1024")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS2048(), c, i);
                                            a[i][c] = makeS2048();
                                            a[row][c] = null;
                                            break;
                                        }


                                        //do 4, 8, 16 etc.
                                    }
                                    else if ((row != i + 1 && !a[row][c].getAccessibleText().equals(a[i][c].getAccessibleText()))) {
                                        ifMoved++;
                                        board.add(makeRectangle(), c, row);
                                        if (a[row][c].getAccessibleText().equals("2")) {
                                            board.add(makeS2(), c, i+1);
                                            a[i+1][c] = makeS2();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("4")) {
                                            board.add(makeS4(), c, i+1);
                                            a[i+1][c] = makeS4();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("8")) {
                                            board.add(makeS8(), c, i+1);
                                            a[i+1][c] = makeS8();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("16")) {
                                            board.add(makeS16(), c, i+1);
                                            a[i+1][c] = makeS16();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("32")) {
                                            board.add(makeS32(), c, i+1);
                                            a[i+1][c] = makeS32();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("64")) {
                                            board.add(makeS64(), c, i+1);
                                            a[i+1][c] = makeS64();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("128")) {
                                            board.add(makeS128(), c, i+1);
                                            a[i+1][c] = makeS128();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("256")) {
                                            board.add(makeS256(), c, i+1);
                                            a[i+1][c] = makeS256();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("512")) {
                                            board.add(makeS512(), c, i+1);
                                            a[i+1][c] = makeS512();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("1024")) {
                                            board.add(makeS1024(), c, i+1);
                                            a[i+1][c] = makeS1024();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("2048")) {
                                            board.add(makeS2048(), c, i+1);
                                            a[i+1][c] = makeS2048();
                                            a[row][c] = null;
                                            break;
                                        }

                                    }
                                    else if (row == i + 1 && !a[row][c].getAccessibleText().equals(a[i][c].getAccessibleText())) {
                                        break;
                                    }
                                }
                                else if (i == 0 && a[i][c] == null) {
                                    ifMoved++;
                                    board.add(makeRectangle(), c, row);
                                    if (a[row][c].getAccessibleText().equals("2")) {
                                        board.add(makeS2(), c, 0);
                                        a[i][c] = makeS2();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("4")) {
                                        board.add(makeS4(), c, 0);
                                        a[i][c] = makeS4();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("8")) {
                                        board.add(makeS8(), c, 0);
                                        a[i][c] = makeS8();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("16")) {
                                        board.add(makeS16(), c, 0);
                                        a[i][c] = makeS16();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("32")) {
                                        board.add(makeS32(), c, 0);
                                        a[i][c] = makeS32();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("64")) {
                                        board.add(makeS64(), c, 0);
                                        a[i][c] = makeS64();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("128")) {
                                        board.add(makeS128(), c, 0);
                                        a[i][c] = makeS128();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("256")) {
                                        board.add(makeS256(), c, 0);
                                        a[i][c] = makeS256();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("512")) {
                                        board.add(makeS512(), c, 0);
                                        a[i][c] = makeS512();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("1024")) {
                                        board.add(makeS1024(), c, 0);
                                        a[i][c] = makeS1024();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("2048")) {
                                        board.add(makeS2048(), c, 0);
                                        a[i][c] = makeS2048();
                                        a[row][c] = null;
                                        break;
                                    }

                                }
                            }
                            //a[row][c] = null;

                        }

                    }
                }
                if (ifMoved != 0) {
                    makeNew();
                }
            }

            // What happens if they click the down arrow key
            if (e.getCode() == DOWN) {
                int ifMoved = 0;
                for (int c = 0; c < 4; c++) {
                    for (int row = 3; row >= 0; row--) {
                        if (a[row][c] != null && row != 3) {


                            for (int i = row + 1; i < 4; i++) {
                                if (a[i][c] != null) {
                                    if (a[row][c].getAccessibleText().equals(a[i][c].getAccessibleText())) {
                                        ifMoved++;
                                        board.add(makeRectangle(), c, row);
                                        if (a[row][c].getAccessibleText().equals("2")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS4(), c, i);
                                            a[i][c] = makeS4();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("4")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS8(), c, i);
                                            a[i][c] = makeS8();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("8")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS16(), c, i);
                                            a[i][c] = makeS16();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("16")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS32(), c, i);
                                            a[i][c] = makeS32();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("32")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS64(), c, i);
                                            a[i][c] = makeS64();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("64")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS128(), c, i);
                                            a[i][c] = makeS128();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("128")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS256(), c, i);
                                            a[i][c] = makeS256();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("256")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS512(), c, i);
                                            a[i][c] = makeS512();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("512")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS1024(), c, i);
                                            a[i][c] = makeS1024();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("1024")) {
                                            board.add(makeRectangle(), c, i);
                                            board.add(makeS2048(), c, i);
                                            a[i][c] = makeS2048();
                                            a[row][c] = null;
                                            break;
                                        }

                                        //do 4, 8, 16 etc.
                                    }

                                    else if ((row != i - 1 && !a[row][c].getAccessibleText().equals(a[i][c].getAccessibleText()))) {
                                        ifMoved++;
                                        board.add(makeRectangle(), c, row);
                                        if (a[row][c].getAccessibleText().equals("2")) {
                                            board.add(makeS2(), c, i-1);
                                            a[i-1][c] = makeS2();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("4")) {
                                            board.add(makeS4(), c, i-1);
                                            a[i-1][c] = makeS4();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("8")) {
                                            board.add(makeS8(), c, i-1);
                                            a[i-1][c] = makeS8();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("16")) {
                                            board.add(makeS16(), c, i-1);
                                            a[i-1][c] = makeS16();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("32")) {
                                            board.add(makeS32(), c, i-1);
                                            a[i-1][c] = makeS32();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("64")) {
                                            board.add(makeS64(), c, i-1);
                                            a[i-1][c] = makeS64();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("128")) {
                                            board.add(makeS128(), c, i-1);
                                            a[i-1][c] = makeS128();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("256")) {
                                            board.add(makeS256(), c, i-1);
                                            a[i-1][c] = makeS256();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("512")) {
                                            board.add(makeS512(), c, i-1);
                                            a[i-1][c] = makeS512();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("1024")) {
                                            board.add(makeS1024(), c, i-1);
                                            a[i-1][c] = makeS1024();
                                            a[row][c] = null;
                                            break;
                                        }
                                        else if (a[row][c].getAccessibleText().equals("2048")) {
                                            board.add(makeS2048(), c, i-1);
                                            a[i-1][c] = makeS2048();
                                            a[row][c] = null;
                                            break;
                                        }

                                    }
                                    else if (row == i - 1 && !a[row][c].getAccessibleText().equals(a[i][c].getAccessibleText())) {
                                        break;
                                    }
                                }
                                else if (i == 3 && a[i][c] == null) {
                                    ifMoved++;
                                    board.add(makeRectangle(), c, row);
                                    if (a[row][c].getAccessibleText().equals("2")) {
                                        board.add(makeS2(), c, 3);
                                        a[i][c] = makeS2();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("4")) {
                                        board.add(makeS4(), c, 3);
                                        a[i][c] = makeS4();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("8")) {
                                        board.add(makeS8(), c, 3);
                                        a[i][c] = makeS8();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("16")) {
                                        board.add(makeS16(), c, 3);
                                        a[i][c] = makeS16();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("32")) {
                                        board.add(makeS32(), c, 3);
                                        a[i][c] = makeS32();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("64")) {
                                        board.add(makeS64(), c, 3);
                                        a[i][c] = makeS64();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("128")) {
                                        board.add(makeS128(), c, 3);
                                        a[i][c] = makeS128();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("256")) {
                                        board.add(makeS256(), c, 3);
                                        a[i][c] = makeS256();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("512")) {
                                        board.add(makeS512(), c, 3);
                                        a[i][c] = makeS512();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("1024")) {
                                        board.add(makeS1024(), c, 3);
                                        a[i][c] = makeS1024();
                                        a[row][c] = null;
                                        break;
                                    }
                                    else if (a[row][c].getAccessibleText().equals("2048")) {
                                        board.add(makeS2048(), c, 3);
                                        a[i][c] = makeS2048();
                                        a[row][c] = null;
                                        break;
                                    }

                                }
                            }
                            //a[row][c] = null;

                        }

                    }
                }
                if (ifMoved > 0) {
                    makeNew();
                }
            }
            gameOver();
            Win();

            for (int row = 0; row < 4; row++) {
                for (int column = 0; column < 4; column++) {
                    if (a[row][column] == null) {
                        board.add(makeRectangle(), column, row);
                    }
                }
            }
        });

        background.setTop(topBar);
        background.setBottom(instructionPane);
        background.setLeft(side1);
        background.setRight(side2);

        background.setStyle("-fx-background-color: rgba(242, 226, 210, 0.5)");
        Scene scene = new Scene(background, 600, 600);
        ps.setTitle("Test4");
        ps.setScene(scene);
        ps.show();
        board.requestFocus();
    }

    public Rectangle makeRectangle() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#cdc1b4"));
        r.setHeight(79);
        r.setWidth(79);
        r.setAccessibleText("0");
        return r;
    }

    public StackPane makeS2() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#eee4da"));
        r.setHeight(79);
        r.setWidth(79);
        Text t = new Text("2");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 40));
        t.setFill(Color.web("#776e65"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        s.setAccessibleText("2");
        return s;
    }

    public StackPane makeS4() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#ede0c8"));
        r.setHeight(79);
        r.setWidth(79);
        Text t = new Text("4");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 40));
        t.setFill(Color.web("#776e65"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        s.setAccessibleText("4");
        return s;
    }

    public StackPane makeS8() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#f2b179"));
        r.setHeight(79);
        r.setWidth(79);
        Text t = new Text("8");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 40));
        t.setFill(Color.web("#f9f6f2"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        s.setAccessibleText("8");
        return s;
    }

    public StackPane makeS16() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#f59563"));
        r.setHeight(79);
        r.setWidth(79);
        Text t = new Text("16");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 40));
        t.setFill(Color.web("#f9f6f2"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        s.setAccessibleText("16");
        return s;
    }

    public StackPane makeS32() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#f67c5f"));
        r.setHeight(79);
        r.setWidth(79);
        Text t = new Text("32");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 40));
        t.setFill(Color.web("#f9f6f2"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        s.setAccessibleText("32");
        return s;
    }

    public StackPane makeS64() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#f65e3b"));
        r.setHeight(79);
        r.setWidth(79);
        Text t = new Text("64");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 40));
        t.setFill(Color.web("#f9f6f2"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        s.setAccessibleText("64");
        return s;
    }

    public StackPane makeS128() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#edcf72"));
        r.setHeight(79);
        r.setWidth(79);
        Text t = new Text("128");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 40));
        t.setFill(Color.web("#f9f6f2"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        s.setAccessibleText("128");
        return s;
    }

    public StackPane makeS256() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#edcc61"));
        r.setHeight(79);
        r.setWidth(79);
        Text t = new Text("256");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 40));
        t.setFill(Color.web("#f9f6f2"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        s.setAccessibleText("256");
        return s;
    }

    public StackPane makeS512() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#edc850"));//Change the color to the actual color ya know?
        r.setHeight(79);
        r.setWidth(79);
        Text t = new Text("512");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 40));
        t.setFill(Color.web("#f9f6f2"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        s.setAccessibleText("512");
        return s;
    }

    public StackPane makeS1024() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#edc53f"));//Change the color to the actual color ya know?
        r.setHeight(79);
        r.setWidth(79);
        Text t = new Text("1024");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 40));
        t.setFill(Color.web("#f9f6f2"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        s.setAccessibleText("1024");
        return s;
    }

    public StackPane makeS2048() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#f65e3b"));//Change the color to the actual color ya know?
        r.setHeight(79);
        r.setWidth(79);
        Text t = new Text("2048");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 40));
        t.setFill(Color.web("#f9f6f2"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        s.setAccessibleText("2048");
        return s;
    }

    public StackPane makeS4096() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#f65e3b"));//Change the color to the actual color ya know?
        r.setHeight(79);
        r.setWidth(79);
        Text t = new Text("4096");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 40));
        t.setFill(Color.web("#f9f6f2"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        s.setAccessibleText("4096");
        return s;
    }

    public StackPane makeS8192() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#f65e3b"));//Change the color to the actual color ya know?
        r.setHeight(79);
        r.setWidth(79);
        Text t = new Text("8192");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 40));
        t.setFill(Color.web("#f9f6f2"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        s.setAccessibleText("8192");
        return s;
    }

    public void makeNew() {
        int twoOrFour = (int)(Math.random()*10 + 1);
        int r1 = (int)(Math.random()*4);
        int c1 = (int)(Math.random()*4);
        while (a[r1][c1] != null) {
            r1 = (int)(Math.random()*4);
            c1 = (int)(Math.random()*4);
        }
        if (twoOrFour - 10 == 0) {
            board.add(makeS4(), c1, r1);
            a[r1][c1] = makeS4();
        }
        else {
            board.add(makeS2(), c1, r1);
            a[r1][c1] = makeS2();
        }
    }
    public void Win(){
        boolean win = false;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                if (a[row][column] != null && a[row][column].getAccessibleText().equals("8")) {
                    win = true;
                }
            }
        }
        if(win){
            Rectangle winRec = new Rectangle(0,0,340, 340);
            winRec.setFill(Color.LIGHTYELLOW);
            winRec.setOpacity(0.3);
            Text t = new Text("You Win!");
            t.setFont(Font.font ("Calibri", FontWeight.BOLD, 60));
            t.setFill(Color.web("#776e65"));
            StackPane stack = new StackPane();
            stack.getChildren().addAll(winRec, t);
            dualCenter.getChildren().add(stack);
        }
    }
    public void gameOver() {
        boolean allFilled = true;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                if (a[row][column] == null) {
                    allFilled = false;
                }
            }
        }
        boolean canMove = false;
        if (allFilled) {
            outerLoop:
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    if (r == 0) {
                        if (c == 0) {
                            if (a[r][c].getAccessibleText().equals(a[r][c + 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r + 1][c].getAccessibleText())) {
                                canMove = true;
                                break outerLoop;
                            }
                        }
                        else if (c == 3) {
                            if (a[r][c].getAccessibleText().equals(a[r][c - 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r + 1][c].getAccessibleText())) {
                                canMove = true;
                                break outerLoop;
                            }
                        }
                        else {
                            if (a[r][c].getAccessibleText().equals(a[r][c - 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r + 1][c].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r][c + 1].getAccessibleText())) {
                                canMove = true;
                                break outerLoop;
                            }
                        }
                    }
                    if (r == 3) {
                        if (c == 0) {
                            if (a[r][c].getAccessibleText().equals(a[r][c + 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r - 1][c].getAccessibleText())) {
                                canMove = true;
                                break outerLoop;
                            }
                        }
                        else if (c == 3) {
                            if (a[r][c].getAccessibleText().equals(a[r][c - 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r - 1][c].getAccessibleText())) {
                                canMove = true;
                                break outerLoop;
                            }
                        }
                        else {
                            if (a[r][c].getAccessibleText().equals(a[r][c - 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r - 1][c].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r][c + 1].getAccessibleText())) {
                                canMove = true;
                                break outerLoop;
                            }
                        }
                    }
                    if (r == 2) {
                        if (c == 0) {
                            if (a[r][c].getAccessibleText().equals(a[r][c + 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r + 1][c].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r - 1][c].getAccessibleText())) {
                                canMove = true;
                                break outerLoop;
                            }
                        }
                        else if (c == 3) {
                            if (a[r][c].getAccessibleText().equals(a[r][c - 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r + 1][c].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r - 1][c].getAccessibleText())) {
                                canMove = true;
                                break outerLoop;
                            }
                        }
                        else {
                            if (a[r][c].getAccessibleText().equals(a[r][c - 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r + 1][c].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r][c + 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r - 1][c].getAccessibleText())) {
                                canMove = true;
                                break outerLoop;
                            }
                        }
                    }
                    if (r == 1) {
                        if (c == 0) {
                            if (a[r][c].getAccessibleText().equals(a[r][c + 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r + 1][c].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r - 1][c].getAccessibleText())) {
                                canMove = true;
                                break outerLoop;
                            }
                        }
                        else if (c == 3) {
                            if (a[r][c].getAccessibleText().equals(a[r][c - 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r + 1][c].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r - 1][c].getAccessibleText())) {
                                canMove = true;
                                break outerLoop;
                            }
                        }
                        else {
                            if (a[r][c].getAccessibleText().equals(a[r][c - 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r + 1][c].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r][c + 1].getAccessibleText()) ||
                                    a[r][c].getAccessibleText().equals(a[r - 1][c].getAccessibleText())) {
                                canMove = true;
                                break outerLoop;
                            }
                        }
                    }
                }
            }
        }

        if (!canMove && allFilled) {
            System.out.println("woAH");
            Rectangle gameOver = new Rectangle(0,0,340, 340);
            gameOver.setFill(Color.LIGHTGRAY);
            gameOver.setOpacity(0.1);
            Text t = new Text("Game Over!");
            t.setFont(Font.font ("Calibri", FontWeight.BOLD, 60));
            t.setFill(Color.web("#776e65"));
            StackPane stack = new StackPane();
            stack.getChildren().addAll(gameOver, t);
            dualCenter.getChildren().add(stack);
        }
    }

    public void resetBoard() {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                a[r][c] = null;
                board.add(makeRectangle(), r, c);
            }
        }
    }

    public StackPane makeNewGame() {
        Rectangle r = new Rectangle();
        r.setArcHeight(10);
        r.setArcWidth(10);
        r.setFill(Color.web("#8f7a66"));//Change the color to the actual color ya know?
        r.setHeight(50);
        r.setWidth(100);
        r.setX(100);
        r.setY(100);
        Text t = new Text("New Game");
        t.setFont(Font.font ("Calibri", FontWeight.BOLD, 25));
        t.setFill(Color.web("#f9f6f2"));
        StackPane s = new StackPane();
        s.getChildren().addAll(r, t);
        return s;
    }

}
