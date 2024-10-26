//import java.util.Arrays;



class Board{

    public char[][] positionsOnBoard;
    
    public Board()
    {
      positionsOnBoard = new char[8][8];
      for (int i = 0; i < 8 ; i++){
        for (int j = 0; j < 8 ; j++){
          
          if ((i + j) % 2 == 1){
            positionsOnBoard[i][j] = ' ';
          }
          
          else if(i == 3 || i == 4){
            positionsOnBoard[i][j] = ' ';
          }
          
          else{
            if (i <= 2){
             positionsOnBoard[i][j] = 'b';  
            }
            
            if (i >= 5){
             positionsOnBoard[i][j] = 'w'; 
            }
            
          }
      
        }
      }
    }

    public void copy(Board newBoard){ // x.copy(new) ---> x = new // working
      for(int i = 0; i < 8; i++){
        for(int j = 0; j < 8; j++){
          this.positionsOnBoard[i][j] = newBoard.positionsOnBoard[i][j];
        }
      }
    }
    
    public void makeMove(int x1 , int y1 , int x2 , int y2){ // gets index
      // add if its white or black turn


      char movingPieceType = positionsOnBoard[y1][x1];
      positionsOnBoard[y1][x1] = ' ';
      positionsOnBoard[y2][x2] = movingPieceType;       

    }
    
    public void makeEat(int x1 , int y1 , int x2 , int y2){  // gets index
      // add if its white or black turn
      char capturingPieceType = positionsOnBoard[y1][x1];

      positionsOnBoard[y1][x1] = ' ';
      positionsOnBoard[y2][x2] = capturingPieceType;
      positionsOnBoard[(y1 + y2) / 2][(x1 + x2) / 2] = ' ';
    }

/*
    public boolean canMove(int x, int y, int turnCounter){
      boolean canRight = x < 7;
      boolean canLeft = x > 0;
      boolean canUp = y > 0;
      boolean canDown = y < 7;
      char piece = positionsOnBoard[y][x];
      
      if(turnCounter % 2 == 1 && piece == 'w'){
        if(canUp && canRight && positionsOnBoard[y-1][x+1] == ' '){
          // can move
        }
        if(canUp && canLeft && positionsOnBoard[y-1][x-1] == ' '){
          // can move
        }
      }

      else if(turnCounter % 2 == 0 && piece == 'b'){
        if(canDown && canRight && positionsOnBoard[y+1][x+1] == ' '){
          // can move
        }
        if(canDown && canLeft && positionsOnBoard[y+1][x-1] == ' '){
          // can move
        }
      }

      else if(positionsOnBoard[y][x] == 'W' || piece == 'B'){
        if(canUp){
          if(canRight && positionsOnBoard[y-1][x+1] == ' '){
            // can move
          }
          if(canLeft && positionsOnBoard[y-1][x-1] == ' ') {
            // can move
          }
        }
        if(canDown){
          if(canRight && positionsOnBoard[y+1][x+1] == ' '){
            // can move
          }
            if(canLeft && positionsOnBoard[y+1][x-1] == ' '){
            // can move
          }
        }
      }
    }


*/






//////////////////////////////////////////////

//functions for human player




    public boolean isMoveValid(int x1 , int y1 , int x2 , int y2, boolean mustEat, int turnCounter){ // gets index
      if(mustEat){
        return false;
      }
      
      if(x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0)
        return false;


      if(turnCounter % 2 == 1 && positionsOnBoard[y1][x1] != 'w' && positionsOnBoard[y1][x1] != 'W'){
       return false; 
      }
      if(turnCounter % 2 == 0 && positionsOnBoard[y1][x1] != 'b' && positionsOnBoard[y1][x1] != 'B'){
       return false; 
      }
      
      boolean isLegal1 = false;

      if(positionsOnBoard[y2][x2] != ' ')
        return false;

      int dX = Math.abs(x1 - x2); // can do w/o abs 
      if(dX != 1){
        return false;
      }
      int dY = Math.abs(y1 - y2);
      if(positionsOnBoard[y1][x1] == 'w'){ // if its white piece
        if (((y1-y2) == 1)){ 
          isLegal1 = true; 
        }
      }

      else if (positionsOnBoard[y1][x1] == 'W'){ // if its a white queen
        if (dY == 1){ 
          isLegal1 = true; 
        }          
      }

      if(positionsOnBoard[y1][x1] == 'b'){ // if its black piece
        if ((y2-y1 == 1)){ 
          isLegal1 = true; 
        }
      }

      else if(positionsOnBoard[y1][x1] == 'B'){ // if its a black queen
        if (dY == 1){ 
          isLegal1 = true; 
        }          
      }

      return isLegal1;
    }
    

    public boolean canMakeCapture(int x1 , int y1 , int x2 , int y2, int turnCounter){ // gets index ------   maybe dont need turn counter // was can eat
      
      if(x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0)
        return false;

      if(turnCounter % 2 == 1 && positionsOnBoard[y1][x1] != 'w' && positionsOnBoard[y1][x1] != 'W'){
       return false; 
      }
      if(turnCounter % 2 == 0 && positionsOnBoard[y1][x1] != 'b' && positionsOnBoard[y1][x1] != 'B'){
       return false; 
      }
      
      int dX = Math.abs(x1 - x2); // can do w/o abs 
      if(dX != 2){
        return false;
      }
      int dY = Math.abs(y1 - y2);

      if(x1 == x2 || y1 == y2){ // cant eat in the same row or column
         return false; 
       }
      if(x2 < 0 || x2 > 7 || y2 < 0 || y2 > 7){
        return false;
      }
      if(x1 < 0 || x1 > 7 || y1 < 0 || y1 > 7){
        return false;
      }

      boolean canMakeCapture = false;
      boolean onAnotherCircle = positionsOnBoard[y2][x2] != ' '; 
      boolean rightSide = x2 > x1; // whether or not the new click is on the right side  
      char piece; 

        if(positionsOnBoard[y1][x1] == 'w'){

          if(rightSide){
            piece = positionsOnBoard[y1 - 1][x1 + 1]; // the pieces that may be eaten
          }

          else{
            piece =  positionsOnBoard[y1 - 1][x1 - 1];
          }

          if (!onAnotherCircle && (piece == 'b' || piece == 'B') && ((y1 - y2) == 2))
          {  // if the capture is from down to top, the middle piece is black, if the clicked place is empty
            canMakeCapture = true;
          }
        }

        else if(positionsOnBoard[y1][x1] == 'W'){ // if its white queen
          if(y1 > y2){
            if(rightSide){
              piece = positionsOnBoard[y1 - 1][x1 + 1]; // the pieces that may be eaten
            }

            else{
              piece =  positionsOnBoard[y1 - 1][x1 - 1];
            }
          }        
        
          else{
            if(rightSide){ // white queen cant capture regular
              piece =  positionsOnBoard[y1 + 1][x1 + 1];
            }
            
            else{
              piece =  positionsOnBoard[y1 + 1][x1 - 1];
            }          
          }

          if (!onAnotherCircle && (piece == 'b' || piece == 'B') && dY == 2){  // if the capture is from down to top, the middle piece is black, if the clicked place is empty
            canMakeCapture = true;
          }        
        }



        else if(positionsOnBoard[y1][x1] == 'b'){

          if(rightSide){
            piece =  positionsOnBoard[y1 + 1][x1 + 1];
          }
          
          else{
            piece =  positionsOnBoard[y1 + 1][x1 - 1];
          }
          
          if (!onAnotherCircle && (piece == 'w' || piece == 'W') && ((y2 - y1) == 2)){  //  if the capture is top to down, the middle piece is white, if the clicked location is empty
            canMakeCapture = true; 
          }
        }



        else{ // black queen
          if(y1 < y2){
            if(rightSide){
              piece =  positionsOnBoard[y1 + 1][x1 + 1];
            }
            
            else{
              piece =  positionsOnBoard[y1 + 1][x1 - 1];
            }
          }

          else{
            if(rightSide){
              piece = positionsOnBoard[y1 - 1][x1 + 1]; // the pieces that may be eaten
            }

            else{
              piece =  positionsOnBoard[y1 - 1][x1 - 1];
            }          
          }

          if (!onAnotherCircle && (piece == 'w' || piece == 'W') && dY == 2)
          {  // if the capture is from down to top, the middle piece is black, if the clicked place is empty
            canMakeCapture = true;
          }        

        }

     return canMakeCapture;
    }




//////////////////////////////////////////////////////////////////////////



    
    
  public boolean canEat(int x1, int y1, int turnCounter) { // was can eat again
    if(turnCounter % 2 == 1 && (positionsOnBoard[y1][x1] != 'w' && positionsOnBoard[y1][x1] != 'W')){
      return false; 
    }
    if(turnCounter % 2 == 0 && positionsOnBoard[y1][x1] != 'b' && positionsOnBoard[y1][x1] != 'B'){
      return false; 
    }


    int xRight, xLeft, yUp, yDown;

    xRight = x1 + 2;
    xLeft = x1 - 2;
    yUp = y1 - 2; 
    yDown = y1 + 2; 
    
    // Check boundaries for all directions
    if ((xRight < 8 && xRight >= 0) || (xLeft < 8 && xLeft >= 0)) {
        
        // Check up-right (diagonal capture)
        if (xRight < 8 && yUp >= 0) {
            if (canMakeCapture(x1, y1, xRight, yUp, turnCounter)) {
                return true;
            }
        }

        // Check up-left (diagonal capture)
        if (xLeft >= 0 && yUp >= 0) {
            if (canMakeCapture(x1, y1, xLeft, yUp, turnCounter)) {
                return true;
            }
        }

        // Check down-right (diagonal capture)
        if (xRight < 8 && yDown < 8) {
            if (canMakeCapture(x1, y1, xRight, yDown, turnCounter)) {
                return true;
            }
        }

        // Check down-left (diagonal capture)
        if (xLeft >= 0 && yDown < 8) {
            if (canMakeCapture(x1, y1, xLeft, yDown, turnCounter)) {
                return true;
            }
        }
    }
    
    return false;
}
 
///////////////////////////////////





/*
  public void checkFurtherCaptures(int x, int y, Move[] arr) {
    int xRight, xLeft, yUp, yDown, newY;
    xRight = x + 2;
    xLeft = x - 2;
    yUp = y - 2; 
    yDown = y + 2;
    char piece = positionsOnBoard[y][x];    

    if(piece == 'w'){
      newY = yUp;
      if(newY >= 0){

              // Capture to the right
        if (x + 2 < 8 && canMakeCapture(x, y, x + 2, newY)) {


          checkFurtherCaptures(x + 2, newY); // Recursive call
        }

                    // Capture to the left
        if (x - 2 >= 0 && canMakeCapture(x, y, x - 2, newY)) {

            // was able to eat

          checkFurtherCaptures(x - 2, newY); // Recursive call
        }
      }

    }


    else if(piece == 'b'){
      newY = yDown;
      // Capture to the right
      if(newY <= 7){
        if (x + 2 < 8 && canMakeCapture(x, y, x + 2, newY)) {

            // was able to eat

          checkFurtherCaptures(x + 2, newY); // Recursive call
        }

                    // Capture to the left
        if (x - 2 >= 0 && canMakeCapture(x, y, x - 2, newY)) {

            // was able to eat

          checkFurtherCaptures(x - 2, newY); // Recursive call
        }
      }
    }


    else if(piece == 'W'){
      newY = yUp;
      if(newY <= 7){
        if (x + 2 < 8 && canMakeCapture(x, y, x + 2, newY)) {

            // was able to eat

          checkFurtherCaptures(x + 2, newY); // Recursive call
        }

                    // Capture to the left
        if (x - 2 >= 0 && canMakeCapture(x, y, x - 2, newY)) {

            // was able to eat

          checkFurtherCaptures(x - 2, newY); // Recursive call
        }
      }
      newY = yDown;
      if(newY <= 0){
              // Capture to the right
        if (x + 2 < 8 && canMakeCapture(x, y, x + 2, newY)) {

            // was able to eat

          checkFurtherCaptures(x + 2, newY); // Recursive call
        }

                    // Capture to the left
        if (x - 2 >= 0 && canMakeCapture(x, y, x - 2, newY)) {

            // was able to eat

          checkFurtherCaptures(x - 2, newY); // Recursive call
        }
      }

    }
    else if(piece == 'B'){
      
      newY = yUp;
      if(newY <= 7){
        if (x + 2 < 8 && canMakeCapture(x, y, x + 2, newY)) {

          checkFurtherCaptures(x + 2, newY); // Recursive call
        }

                    // Capture to the left
        if (x - 2 >= 0 && canMakeCapture(x, y, x - 2, newY)) {

          checkFurtherCaptures(x - 2, newY); // Recursive call
        }
      }
      newY = yDown;
      if(newY <= 0){
              // Capture to the right
        if (x + 2 < 8 && canMakeCapture(x, y, x + 2, newY)) {

          checkFurtherCaptures(x + 2, newY); // Recursive call
        }

                    // Capture to the left
        if (x - 2 >= 0 && canMakeCapture(x, y, x - 2, newY)) {

          checkFurtherCaptures(x - 2, newY); // Recursive call
        }
      }


    }

  }
  */


  public void checkFurtherCaptures(int x, int y, Move[] arr) {
    int xRight, xLeft, yUp, yDown;
    xRight = x + 2;
    xLeft = x - 2;
    yUp = y - 2; 
    yDown = y + 2;
    char piece = positionsOnBoard[y][x];    

    if (piece == 'w') {
        // Capture upwards
        if (yUp >= 0){
            // Capture to the right
            if (x + 2 < 8 && canMakeCapture(x, y, x + 2, yUp)) {
                arr = addMoveToArray(arr, new Move(x + 8 * y, x + 2 + 8 * yUp)); 
                return; 
            }
            // Capture to the left
            if (x - 2 >= 0 && canMakeCapture(x, y, x - 2, yUp)) {
                arr = addMoveToArray(arr, new Move(x + 8 * y, x - 2 + 8 * yUp)); 
                return; 
            }
        }
    } 
    else if (piece == 'b') {
        // Capture downwards
        if (yDown < 8) {
            // Capture to the right
            if (x + 2 < 8 && canMakeCapture(x, y, x + 2, yDown)) {
                arr = addMoveToArray(arr, new Move(x + 8 * y, x + 2 + 8 * yDown));
                return; 
            }
            // Capture to the left
            if (x - 2 >= 0 && canMakeCapture(x, y, x - 2, yDown)) {
                arr = addMoveToArray(arr, new Move(x + 8 * y, x - 2 + 8 * yDown));
                return; 
            }
        }
    } 
    else if (piece == 'W') {
        // Capture upwards
        if (yUp >= 0) {
            // Capture to the right
            if (x + 2 < 8 && canMakeCapture(x, y, x + 2, yUp)) {
                arr = addMoveToArray(arr, new Move(x + 8 * y, x + 2 + 8 * yUp)); 
                return; 
            }
            // Capture to the left
            if (x - 2 >= 0 && canMakeCapture(x, y, x - 2, yUp)) {
                arr = addMoveToArray(arr, new Move(x + 8 * y, x - 2 + 8 * yUp)); 
                return; 
            }
        }
        // Capture downwards
        if (yDown < 8) {
            // Capture to the right
            if (x + 2 < 8 && canMakeCapture(x, y, x + 2, yDown)) {
                arr = addMoveToArray(arr, new Move(x + 8 * y, x + 2 + 8 * yDown)); 
                return; 
            }
            // Capture to the left
            if (x - 2 >= 0 && canMakeCapture(x, y, x - 2, yDown)) {
                arr = addMoveToArray(arr, new Move(x + 8 * y, x - 2 + 8 * yDown)); 
                return; 
            }
        }
    } 






    ///////// can colaborate with white queen
    else if (piece == 'B') {
        // Capture upwards
        if (yUp >= 0) {
            // Capture to the right
            if (x + 2 < 8 && canMakeCapture(x, y, x + 2, yUp)) {
                arr = addMoveToArray(arr, new Move(x + 8 * y, x + 2 + 8 * yUp)); 
                return; 
            }
            // Capture to the left
            if (x - 2 >= 0 && canMakeCapture(x, y, x - 2, yUp)) {
                arr = addMoveToArray(arr, new Move(x + 8 * y, x - 2 + 8 * yUp)); 
                return; 
            }
        }
        // Capture downwards
        if (yDown < 8) {
            // Capture to the right
            if (x + 2 < 8 && canMakeCapture(x, y, x + 2, yDown)) {
                arr = addMoveToArray(arr, new Move(x + 8 * y, x + 2 + 8 * yDown)); 
                return; 
            }
            // Capture to the left
            if (x - 2 >= 0 && canMakeCapture(x, y, x - 2, yDown)) {
                arr = addMoveToArray(arr, new Move(x + 8 * y, x - 2 + 8 * yDown)); 
                return; 
            }
        }
    }
}






///////// in the future i need to do that if the piece can capture it wouldnt go to check regular moves

public Move[] returnPossiblePlaces(int x, int y, int turnCounter, Move[] arr, boolean mustEat) { // function for BOT
    char piece = positionsOnBoard[y][x];
    
    // White's turn
    if (turnCounter % 2 == 1) {
        if (piece == 'w' || piece == 'W') {
            checkFurtherCaptures(x, y, arr);
            
            if (arr[0] != null && mustEat) {
              return arr;
            }

            canMove(x, y, arr);
        }
    } 

    else {
        if (piece == 'b' || piece == 'B') { 
            checkFurtherCaptures(x, y, arr);
            
            if (arr[0] != null && mustEat) {
                return arr;
            }

            canMove(x, y, arr);
        }
    }
    return arr;
}



  public Move[] canMove(int x, int y, Move[] arr) {
    boolean canRight = x < 7;
    boolean canLeft = x > 0;
    boolean canUp = y > 0;
    boolean canDown = y < 7;
    char piece = positionsOnBoard[y][x];
    
    if (piece == 'w') {
        if (canUp && canRight && positionsOnBoard[y - 1][x + 1] == ' ') {
            arr = addMoveToArray(arr, new Move(x + 8*y, x + 1 + 8*(y - 1))); // up-right
        }
        if (canUp && canLeft && positionsOnBoard[y - 1][x - 1] == ' ') {
            arr = addMoveToArray(arr, new Move(x + 8*y, x - 1 + 8*(y - 1))); // up-left
        }
    }


    else if (piece == 'b') {
        if (canDown && canRight && positionsOnBoard[y + 1][x + 1] == ' ') {
            arr = addMoveToArray(arr, new Move(x + 8*y, x + 1 + 8*(y + 1))); // down-right
        }
        if (canDown && canLeft && positionsOnBoard[y + 1][x - 1] == ' ') {
            arr = addMoveToArray(arr, new Move(x + 8*y, x - 1 + 8*(y + 1))); // down-left
        }
    }


    else if (piece == 'W' || piece == 'B') {
        if (canUp) {
            if (canRight && positionsOnBoard[y - 1][x + 1] == ' ') {
                arr = addMoveToArray(arr, new Move(x + 8*y, x + 1 + 8*(y - 1))); // up-right
            }
            if (canLeft && positionsOnBoard[y - 1][x - 1] == ' ') {
                arr = addMoveToArray(arr, new Move(x + 8*y, x - 1 + 8*(y - 1))); // up-left
            }
        }
        if (canDown) {
            if (canRight && positionsOnBoard[y + 1][x + 1] == ' ') {
                arr = addMoveToArray(arr, new Move(x + 8*y, x + 1 + 8*(y + 1))); // down-right
            }
            if (canLeft && positionsOnBoard[y + 1][x - 1] == ' ') {
                arr = addMoveToArray(arr, new Move(x + 8*y, x - 1 + 8*(y + 1))); // down-left
            }
        }
    }
    
    return arr;
  } 



}