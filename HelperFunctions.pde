  public void drawBoardScreen(int sizeSquare, int sizeBoard, int offsetX, int offsetY){
    background(183,185,168); 
    fill(177,110,65); // brow
    square(0, 0,sizeBoard);
    fill(0);
    line(sizeBoard, 0, sizeBoard, sizeBoard);
    fill(240,200,140); // white
    noStroke();
    boolean changed = false; // if we changed the square to white
    for (int i = 0; i < width - offsetX ; i+= sizeSquare){
      for (int j = 0; j < height  - offsetY; j+= sizeSquare){
    
        if (!changed){
          square(i , j , sizeSquare);
          if(j != sizeSquare * 7){
            changed = true;
          }
        }
        
        else{
          if (j != sizeSquare * 7){
            changed = !changed;
          }
        }
      }
    }
// for pause

    pause = new Button((int)(8.375*sizeSquare + sizeSquare / 4),(int)(3.5*sizeSquare),(int)(1.25*sizeSquare),(int)(1.25*sizeSquare), 0,0,0,"");
    drawButton(pause);
    fill(255);
    rect((int)(8.64*sizeSquare + sizeSquare / 4), (int)(3.75*sizeSquare), (int)(0.225*sizeSquare), (int)(0.75*sizeSquare));
    rect((int)(9.13*sizeSquare + sizeSquare / 4), (int)(3.75*sizeSquare), (int)(0.225*sizeSquare), (int)(0.75*sizeSquare)); // pause button

///// for outlines
        fill(0); 
    stroke(2);
    strokeWeight(2);
    rect((int)(sizeBoard), (int)(0), (int)(sizeSquare / 3), (int)(sizeBoard + sizeSquare / 3));
    rect((int)(0), (int)(sizeBoard), (int)(sizeBoard + sizeSquare / 3), (int)(sizeSquare / 3));
    char[] arr = {'A','B','C','D','E','F','G','H'};
    fill(240,200,140);
    for(int i = 0; i < 8; i++){
      textSize(20);
      text(8-i, sizeBoard + sizeSquare / 6, sizeSquare / 2 + i * sizeSquare);
      text(arr[i], sizeSquare / 2 + i * sizeSquare, sizeBoard + sizeSquare / 6);
    }
    
    //////// gor take back

    fill(255);
    square(sizeSquare * 8 + sizeSquare / 3, sizeSquare * 8 + sizeSquare / 3, sizeSquare * 2); // for takeback
    noStroke();
    IGtakeBack = new Button((int)(8.15*sizeSquare + sizeSquare / 3),(int)(8.4*sizeSquare + sizeSquare / 3),(int)(sizeSquare - sizeSquare / 3),(int)(1.7 * sizeSquare - sizeSquare / 3), 255,255,255,"TakeBack");
    save = new Button((int)(8.375*sizeSquare + sizeSquare / 4),(int)(6*sizeSquare),(int)(sizeSquare),(int)(sizeSquare), 255,255,255,"save");
    drawButton(save);
    drawButton(IGtakeBack);
  
////////////////







  }

////////////////

  public void drawTimerMenu(int sizeBoard){
    for (int j = 0; j < timerOptions.length; j++) {
      drawButton(timerButtons[j]);
    }
  }

////////////////////////

  public void drawStartMenu(int sizeBoard){
    stroke(0);              // Black outline
    strokeWeight(5);         // Thicker outline
    background(240,200,140); 
    fill(177,110,65);

    rect(0, 0, sizeBoard, sizeBoard / 6); // top rect
    fill(255,255,255);
    drawButton(start);
    
    textSize(90);
    text("CHECKERS", sizeBoard / 2, sizeBoard /12);


    takeBack = new Button((int)((sizeBoard / 10)*6) , (int)((sizeBoard / 10)*5.5) ,sizeBoard / 15 ,sizeBoard / 4, 255,255,255,"Take-back");
    timer = new Button((int)((sizeBoard / 10)*2) , (int)((sizeBoard / 10)*5.5) ,sizeBoard / 15 ,sizeBoard / 5, 255,255,255,"Timer");
    difficulty = new Button(int((sizeBoard / 10) * 4.25), sizeBoard / 6, (sizeBoard / 13), (int)((sizeBoard / 10) * 1.5), 255,255,255,"Difficulty");
    loadGame = new Button((int)(sizeBoard / 2) , (int)((sizeBoard / 10)*7) ,sizeBoard / 15 ,sizeBoard / 5, 255,255,255,"Load");

    drawButton(loadGame);
    drawButton(difficulty);
    drawButton(timer);
    drawButton(takeBack);




  }

/////////

  public void drawWinningScreen(int sizeBoard, int winner){
    background(240,200,140); 
    textSize(90);
    fill(0);
    if(winner == 1){ // the winning screen for white
      text("White won!", sizeBoard / 2, sizeBoard /12);
    }
    else{
      text("Black won!", sizeBoard / 2, sizeBoard /12);
    }

  }

/////////



  public boolean clickedButton(Button b){
    if(mouseX >= b.x && mouseX <= b.x + b.width &&
       mouseY >= b.y && mouseY <= b.y + b.height){ // check if clicked on start button 
            return true;
    }
    return false;
  }

/////////////

public void drawButton(Button b){ 
    fill(b.R,b.G,b.B);
    rect(b.x, b.y, b.width, b.height);
    
    textAlign(CENTER, CENTER);
    PFont boldFont = createFont("Arial Bold", (int)(b.height / 2.5));
    textFont(boldFont);  // Set the font to be bold
    fill(0);
    text(b.text, b.x + b.width / 2, b.y + b.height / 2);

}
//////

public void mouseClicked(){
  mouseClicked = true;
}

/////

public void timerDropDownMenu(){
  for (int i = 0; i < timerButtons.length; i++) {
    if (clickedButton(timerButtons[i])) {
      selectedTimerOption = i;  // Record the selected option
      timerMenu = false; // Hide dropdown after selection
      if(i == 0)
        timerCountDown = 60;
      
      else if(i == 1)
        timerCountDown = 300;
      
      else if(i == 2)
        timerCountDown = 600;
      
      else if(i == 3)
        timerCountDown = 1800;

      blackTimer.setMaxTime(timerCountDown);
      whiteTimer.setMaxTime(timerCountDown);
    }
  }
}

///////////////////

public void takeBackDropDownMenu(){
  for (int i = 0; i < takeBackButtons.length; i++) {
    if (clickedButton(takeBackButtons[i])) {
      selectedTakeBackOption = i; 
      takeBackMenu = false; 
      if(i == 0){
        TBcounter = 1;
      }
      else if(i == 1){
        TBcounter = 3;
      }
      else if(i == 2){
        TBcounter = 5;
      }
      else if(i == 3){
        TBcounter = 1000000;
      }      
    }
  }  
}

////////

public void drawTakeBackMenu(int sizeBoard){
  for (int j = 0; j < takeBackOptions.length; j++) {
    drawButton(takeBackButtons[j]);
  }
}

//////////

public void difficultyDropDownMenu(){
  for (int i = 0; i < difficultyButtons.length; i++) {
    if (clickedButton(difficultyButtons[i])) {
      selectedDifficltyOption = i; 
      difficultyMenu = false; 
    }
  }  
}


//////
public void drawDifficultyMenu(int sizeBoard){
  for (int j = 0; j < difficultyOptions.length; j++) {
    drawButton(difficultyButtons[j]);
  }
}


public void drawInGameMenu(int sizeBoard){

  background(30,50,50); 
  fill(177,110,65);
  resume = new Button(int((sizeBoard / 10) * 4.25), sizeBoard / 6, (sizeBoard / 13), (int)((sizeBoard / 10) * 1.5), 255,255,255,"resume");
  drawButton(resume);
}