import java.util.Arrays;  
import java.io.*;

Board b = new Board();
Board p = new Board(); // previous board (for takeback) 

Point validClickedIndex = new Point(-1,-1); // (3,5)
Point validCenter = new Point(-1,-1); // (350,550)

Point tempCoords = new Point(-1,-1); 
Point tempIndex = new Point(-1,-1);
Point tempCenter = new Point(-1,-1);
Point tempMoved = new Point(-1,-1); 

boolean clickedOnCircle = false;
boolean onWhite = false; 
boolean onBlack = false;
boolean mustEat = false;
boolean outOfBounds = false;
boolean gameStarted = false;
boolean dontContinue = false;


boolean takeBackMenu = false;
boolean timerMenu = false;
boolean InGameMenu = false;
boolean difficultyMenu = false;
boolean loaded = false;

int turnCounter = 1;
int winner = 0;
int selectedTimerOption = -1;
int selectedTakeBackOption = -1;
int selectedDifficltyOption = -1;
int TBcounter;
int timerCountDown;


String boardPath = "D:\\users\\rafis\\OneDrive\\fileWriterBoard.txt";
String statePlayer = "nothing";

Timer blackTimer = new Timer();
Timer whiteTimer = new Timer();

boolean mouseClicked = false;

Move[] possibleMoves = new int[64];

String[] timerOptions = {"1 min", "5 min", "10 min", "30 min"}; // changed here
String[] takeBackOptions = {"1 move", "3 moves", "5 moves", "Unlimited"};
String[] difficultyOptions = {"1","2","3","4","5"};

Button[] difficultyButtons = new Button[difficultyOptions.length];
Button[] timerButtons = new Button[timerOptions.length];
Button[] takeBackButtons = new Button[takeBackOptions.length];


Button difficulty = new Button();
Button start = new Button();
Button pause = new Button();
Button resume = new Button();
Button takeBack = new Button();
Button timer = new Button();
Button loadGame = new Button();
Button save = new Button();
Button IGtakeBack = new Button();

TextFile savedPosition = new TextFile();
TextFile f;

public void setup() {
  size(1000 ,1000);
  start = new Button((width / 10) * 4, (width / 10) * 4, (width / 10), (width / 10) * 2,255,255,255, "Start");
  frameRate(30);
  f = new TextFile("");
}

public void draw() {
  
  int offsetX = 200;
  int offsetY = 200;
  int sizeBoard = width - offsetX;
  int sizeSquare = sizeBoard / 8 ;
  
  if(!gameStarted && !InGameMenu){

    drawStartMenu(width); // drawing the menu according to the screen size

      if (mouseClicked){ // check where we clicked 

        if(clickedButton(loadGame)){
          println("before: " + turnCounter);
          turnCounter = savedPosition.loadPositionFile(b);
                    println("after: " + turnCounter);
          gameStarted = true;
          loaded = true;
        }


        if(clickedButton(start)){      
          gameStarted = true;
          if(selectedTimerOption > -1){
            whiteTimer.startTimer();
          }


        }

        if(clickedButton(timer)){
          for (int i = 0; i < timerOptions.length; i++) {
          timerButtons[i] = new Button(timer.getX(), timer.getY() + timer.getHeight() + (i * timer.getHeight()),
                                     timer.getHeight(), timer.getWidth(),255,255,255, timerOptions[i]);
          }
          timerMenu = !timerMenu;
        }

        if(timerMenu){
          timerDropDownMenu();
        }
///////// take back

        if(clickedButton(takeBack)){
          for (int i = 0; i < takeBackOptions.length; i++) {
            takeBackButtons[i] = new Button(takeBack.getX(), takeBack.getY() + takeBack.getHeight() + (i * takeBack.getHeight()),
                                      takeBack.getHeight(), takeBack.getWidth(),255,255,255, takeBackOptions[i]);

          }
          takeBackMenu = !takeBackMenu;
        }

        if(takeBackMenu){
          takeBackDropDownMenu();
        }
//////// difficulty

        if(clickedButton(difficulty)){
          for (int i = 0; i < difficultyOptions.length; i++) {
          difficultyButtons[i] = new Button(difficulty.getX() - (int)(2.5 * sizeBoard / 16) + i * (sizeBoard / 10), difficulty.getY() + difficulty.getHeight(),
                                     difficulty.getHeight(), sizeBoard / 10,255,255,255, difficultyOptions[i]);
          }
          difficultyMenu = !difficultyMenu;
        }

        if(difficultyMenu){
          difficultyDropDownMenu();
        }

      }

////////////////////////////////// timer
      if(timerMenu){
          drawTimerMenu(sizeBoard);
      }

      if (selectedTimerOption != -1) {
        fill(0);
        textAlign(CENTER);
        text("Selected: " + timerOptions[selectedTimerOption], width / 4, height - 30);
      }

//////////////////////////// take back

      if(takeBackMenu){
          drawTakeBackMenu(sizeBoard);
      }

      if (selectedTakeBackOption != -1) {
        fill(0);
        textAlign(CENTER);
        text("Selected: " + takeBackOptions[selectedTakeBackOption], (width / 4) * 3, height - 30);
      }


/////////////////// difficulty

      if(difficultyMenu){
        drawDifficultyMenu(sizeBoard);
      }

      if (selectedDifficltyOption != -1) {
        fill(0);
        textAlign(CENTER);
        text("Selected: " + difficultyOptions[selectedDifficltyOption], (width / 4) * 2, height - 30);
      }

    }

  else if(InGameMenu) {
    drawInGameMenu(sizeBoard);
    if(mouseClicked){
      if(clickedButton(resume)){
        InGameMenu = false;
        gameStarted = true;
      }

    }


  }


  else{


    drawBoardScreen(sizeSquare ,sizeBoard, offsetX, offsetY);
    int middleX; int middleY;
    float radius = sizeSquare * 0.65f;






    if(turnCounter % 2 == 1)
    { // white turn

      if(selectedTimerOption > -1){
        whiteTimer.moveTimer();
      }




      if(mouseClicked){


      if(clickedButton(IGtakeBack) && TBcounter > 0){
        if(turnCounter > 1){
          turnCounter--;
        }
        TBcounter--;
        b.copy(p);
      } 

      if (!outOfBounds && onWhite){   // ERROR WHEN CLICKING ON PIECE AND THEN ON OUTOFBOUNDS
        dontContinue = false;
        tempCoords.setX(mouseX);
        tempCoords.setY(mouseY);
        tempCenter = tempCoords.transferToMiddleCords(sizeBoard);
        tempIndex = tempCenter.transferToSquareCords(sizeBoard);
        if(mouseX > 800 || mouseY > 800){
          outOfBounds = true;
          dontContinue = true;
        }
        if(!dontContinue){
          if(b.isMoveValid( validClickedIndex.getX(),validClickedIndex.getY(),tempIndex.getX(),tempIndex.getY(), mustEat ,turnCounter)){ 
            p.copy(b); // copy the board before movement to the p (previous) variable
            b.makeMove(validClickedIndex.getX(),validClickedIndex.getY(),tempIndex.getX(),tempIndex.getY());

            if(tempIndex.getY() == 0)
              b.positionsOnBoard[tempIndex.getY()][tempIndex.getX()] = 'W';
            f.addText("White Moved from ", turnCounter, validClickedIndex, tempIndex);
            turnCounter++;

            if(selectedTimerOption > -1){
                          blackTimer.startTimer();
            }

            
            statePlayer = "nothing";
            validClickedIndex.resetPoint();
            validCenter.resetPoint();
            // clickedOnCircle = false;
          
          }
          
          else{

            if(b.canMakeCapture(validClickedIndex.getX(),validClickedIndex.getY(),tempIndex.getX(),tempIndex.getY(), turnCounter)){
              p.copy(b); // copy the board before movement to the p (previous) variable
              b.makeEat(validClickedIndex.getX(),validClickedIndex.getY() ,tempIndex.getX() ,tempIndex.getY());
              statePlayer = "moved";
              
              if(tempIndex.getY() == 0)
                b.positionsOnBoard[tempIndex.getY()][tempIndex.getX()] = 'W';

              f.addText("White captured from ", turnCounter, validClickedIndex, tempIndex);


              if(b.canEat(tempIndex.getX() ,tempIndex.getY(), turnCounter)){
                mustEat = true;
                tempMoved.setX(tempIndex.getX());
                tempMoved.setY(tempIndex.getY());

              }
              
              else{
                tempMoved.resetPoint();
                mustEat = false;
                turnCounter++;
                if(selectedTimerOption > -1){
                  blackTimer.startTimer();
                }

                validClickedIndex.resetPoint();
                validCenter.resetPoint();
                statePlayer = "nothing";
                // clickedOnCircle = false;
              }
            }
          }
        }

      }
      
      if(mouseX >= sizeBoard || mouseY >= sizeBoard){
        outOfBounds = true;
        if(clickedButton(pause)){
          InGameMenu = true;
        }

        if(clickedButton(save)){
          savedPosition.savePositionFile(b, turnCounter);
        }
      }

      else{
        outOfBounds = false;
        tempCoords.setX(mouseX);
        tempCoords.setY(mouseY);
        tempIndex = tempCoords.transferToSquareCords(sizeBoard);
        tempCenter = tempCoords.transferToMiddleCords(sizeBoard);
        double distance = tempCoords.distance(tempCenter);
        clickedOnCircle = radius / 2 >= distance;

        if (!outOfBounds && clickedOnCircle && (b.positionsOnBoard[tempIndex.getY()][tempIndex.getX()] == 'w' ||
           b.positionsOnBoard[tempIndex.getY()][tempIndex.getX()] == 'W')){ // if clicked inside the circle and on white piece

          if(mustEat){
            if(tempMoved.getX() == tempIndex.getX() && tempMoved.getY() == tempIndex.getY())
            { // if its on eating streak so only if i select the piece that currently is on the streak the click will count as a correct one 
              validCenter.setX(tempCenter.getX());
              validCenter.setY(tempCenter.getY());
              validClickedIndex.setX(tempIndex.getX());
              validClickedIndex.setY(tempIndex.getY());
              statePlayer = "selected";
              onWhite = true; 
            }
          }

          else{

            if(turnCounter % 2 == 1){

              validCenter.setX(tempCenter.getX());
              validCenter.setY(tempCenter.getY());
              validClickedIndex.setX(tempIndex.getX());
              validClickedIndex.setY(tempIndex.getY());
              statePlayer = "selected";
              onWhite = true; 
            }

          }
      
        }
        else{
        onWhite = false;
        validClickedIndex.resetPoint();
        statePlayer = "nothing";
       //clickedOnCircle = false;
        }
      }
         
    }
    
    } // end of white turn      
    else{     // black turn (BOT)

      if(selectedTimerOption > -1){
              blackTimer.moveTimer();
      }


      if(mouseClicked){

        if(clickedButton(IGtakeBack) && TBcounter > 0){
          if(turnCounter > 1){
            turnCounter--;
          }
          TBcounter--;
          b.copy(p);
        } 


      if (!outOfBounds && onBlack){   // ERROR WHEN CLICKING ON PIECE AND THEN ON OUTOFBOUNDS
        dontContinue = false;
        tempCoords.setX(mouseX);
        tempCoords.setY(mouseY);
        tempCenter = tempCoords.transferToMiddleCords(sizeBoard);
        tempIndex = tempCenter.transferToSquareCords(sizeBoard);
        if(mouseX > 800 || mouseY > 800){
          outOfBounds = true;
          dontContinue = true;
        }

        if(!dontContinue){
          if(b.isMoveValid( validClickedIndex.getX(),validClickedIndex.getY(),tempIndex.getX(),tempIndex.getY(), mustEat, turnCounter)){ 
            p.copy(b); // copy the board before movement to the p (previous) variable
            b.makeMove(validClickedIndex.getX(),validClickedIndex.getY(),tempIndex.getX(),tempIndex.getY());

            if(tempIndex.getY() == 7)
                b.positionsOnBoard[tempIndex.getY()][tempIndex.getX()] = 'B';
            
            
            f.addText("Black Moved from ", turnCounter, validClickedIndex, tempIndex);

            turnCounter++; 

            if(selectedTimerOption > -1){
                          whiteTimer.startTimer();
            }

            statePlayer = "nothing";
            validClickedIndex.resetPoint();
            validCenter.resetPoint();
            // clickedOnCircle = false; 
          }
          
          else{
            if(b.canMakeCapture(validClickedIndex.getX(),validClickedIndex.getY(),tempIndex.getX(),tempIndex.getY(), turnCounter)){
              p.copy(b); // copy the board before movement to the p (previous) variable
              b.makeEat(validClickedIndex.getX(),validClickedIndex.getY() ,tempIndex.getX() ,tempIndex.getY());
              statePlayer = "moved";
              if(tempIndex.getY() == 7)
                b.positionsOnBoard[tempIndex.getY()][tempIndex.getX()] = 'B';
                
              f.addText("Black captured from ", turnCounter, validClickedIndex, tempIndex);
              
              if(b.canEat(tempIndex.getX(),tempIndex.getY(), turnCounter))
              { // if black can eat again
                mustEat = true;
                tempMoved.setX(tempIndex.getX());
                tempMoved.setY(tempIndex.getY());
              }
              
              else
              {
                mustEat = false;
                tempMoved.resetPoint();
                turnCounter++;

                if(selectedTimerOption > -1){
                  whiteTimer.startTimer();
                }

                statePlayer = "nothing";
                validClickedIndex.resetPoint();
                validCenter.resetPoint();
                // clickedOnCircle = false;
              }
            }
          }
        }


      }
      

      if(mouseX >= sizeBoard || mouseY >= sizeBoard){
        outOfBounds = true;
        if(clickedButton(pause)){
          InGameMenu = true;
        }
        
        if(clickedButton(save)){
          savedPosition.savePositionFile(b, turnCounter);
          println(1);
        }


      }
      else{
        outOfBounds = false;
        tempCoords.setX(mouseX);
        tempCoords.setY(mouseY);
        tempIndex = tempCoords.transferToSquareCords(sizeBoard);
        tempCenter = tempCoords.transferToMiddleCords(sizeBoard);
        double distance = tempCoords.distance(tempCenter);
        clickedOnCircle = radius / 2 >= distance;
      

        if (clickedOnCircle && (b.positionsOnBoard[tempIndex.getY()][tempIndex.getX()] == 'b' ||
            b.positionsOnBoard[tempIndex.getY()][tempIndex.getX()] == 'B')){ // (clicked on the piece and the piece is black)

          if(mustEat){
            if(tempMoved.getX() == tempIndex.getX() && tempMoved.getY() == tempIndex.getY())
            { // if on eating streak and the piece is the same piece that previously ate
              validCenter.setX(tempCenter.getX());
              validCenter.setY(tempCenter.getY());
              validClickedIndex.setX(tempIndex.getX());
              validClickedIndex.setY(tempIndex.getY());
              statePlayer = "selected";
              onBlack = true; 
            }
          }
        
          else
          {
            if(turnCounter % 2 == 0){
              validCenter.setX(tempCenter.getX());
              validCenter.setY(tempCenter.getY());
              validClickedIndex.setX(tempIndex.getX());
              validClickedIndex.setY(tempIndex.getY());
              statePlayer = "selected";
              onBlack = true; 
            }
          }
        }

        else{
        onBlack = false; 
        validClickedIndex.resetPoint();
        statePlayer = "nothing";
       //clickedOnCircle = false;
        }
      }
    }
  }// end of black turn
      
      // drawing the circles

  for (int i = 0; i < 8 ; i++)
  {
    for (int j = 0; j < 8 ; j++)
    {
      middleX = (j * sizeSquare) + sizeSquare / 2; // can crate another function
      middleY = (i * sizeSquare) + sizeSquare / 2;
      if (b.positionsOnBoard[i][j] == 'b'){
        fill(0);
        circle(middleX , middleY , radius); // drawing black circle
      }

      else if(b.positionsOnBoard[i][j] == 'B'){ // black queen
        fill(0);
        stroke(255,255,0);
        strokeWeight(6); 
        circle(middleX , middleY , radius); // drawing circle
        noStroke();
      }
      
      else if (b.positionsOnBoard[i][j] == 'w'){
        fill(255);
        circle(middleX , middleY , radius); // drawing white circle
      }

      else if(b.positionsOnBoard[i][j] == 'W'){ // white queen
        fill(255);
        stroke(255,255,0);
        strokeWeight(6); 
        circle(middleX , middleY , radius); // drawing circle
        noStroke();
      }

      if(validClickedIndex.getX() >= 0 && validClickedIndex.getY() >= 0) 
      {
        if (b.positionsOnBoard[validClickedIndex.getY()][validClickedIndex.getX()] != ' ' && statePlayer.equals("selected") || statePlayer.equals("moved")){ // check if there is currently white circle on position
          fill(255, 0, 0); // red
          circle(validClickedIndex.getX() * sizeSquare + sizeSquare / 2 , validClickedIndex.getY() * sizeSquare + sizeSquare / 2 , radius / 3); // drawing red circle
        }

      }
      
    }
  }
  
  } // end of main code

  mouseClicked = false;
} // end of draw func

// ---------------------------------------------util functions-----------------------------------------------------------

  public void checkWinner(Board b, int winner, int turnCounter){
    
    boolean black = false;
    boolean white = false;

    for (int i = 0; i < 8; i++){
      for (int j = 0; j < 8; j++){
        if(b.positionsOnBoard[i][j] == 'b' || b.positionsOnBoard[i][j] == 'B'){
          black = true;
        }
        if(b.positionsOnBoard[i][j] == 'w' || b.positionsOnBoard[i][j] == 'W'){
          white = true;
        }

      }
    }

    if(!black){
      println("White Won!");
    }

    if(!white){
      println("Black Won!");
    }



    if(!black || !white){
      
      if(turnCounter % 2 == 0){

        // white wins(black turn)

      }
      else{
        // black wins(white turn)
      }
      // draw winning screen

    }

  }
