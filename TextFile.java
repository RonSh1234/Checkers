import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class TextFile {
    
    public String text;
    private FileWriter moveWriter;
    private FileWriter boardWriter;
    private BufferedReader boardReader;

    public TextFile(String text){
        try {
          this.text = text;
          new FileWriter("D:\\users\\rafis\\OneDrive\\fileWriter.txt").close(); // This clears the file
          this.moveWriter = new FileWriter("D:\\users\\rafis\\OneDrive\\fileWriter.txt", true); // 'true' enables append mode
        }
  
        catch (IOException e) {
          e.printStackTrace();
        }
    }

    public TextFile(){
      try {
        this.text = "";
        this.boardWriter = new FileWriter("D:\\users\\rafis\\OneDrive\\fileWriterBoard.txt", true);
      }

      catch (IOException e) {
        e.printStackTrace();
      }
    }





    public void addText(String text, int turnCounter, Point previous, Point current){ 

      try {
        this.moveWriter.write(turnCounter + ". " + text + previous + " to " + current + "\n");
        this.moveWriter.flush(); // Ensures the content is written immediately without closing the file
      }

      catch (IOException e) {
        e.printStackTrace();
      }
    }


    public void savePositionFile(Board b, int turnCounter){       
      try {
        new FileWriter("D:\\users\\rafis\\OneDrive\\fileWriterBoard.txt").close(); // Clears the file before saving
        this.boardWriter = new FileWriter("D:\\users\\rafis\\OneDrive\\fileWriterBoard.txt", true);
        for(int i = 0; i < 8; i ++){
          for(int j = 0; j < 8; j ++){
            char piece = b.positionsOnBoard[i][j];
            if (piece == ' ') {
              this.boardWriter.write(i + "" + j + 'e' + "\n"); // Empty square
            } 
            else {
              this.boardWriter.write(i + "" + j + piece + "\n"); // Save piece
            }
          }
        }
        this.boardWriter.write("Turn: " + turnCounter + "\n");
        this.boardWriter.flush(); 
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    



    public void close() throws IOException {
        if (boardWriter != null) {
            boardWriter.close();
        }
        if (moveWriter != null) {
            moveWriter.close();
        }
    }





public int loadPositionFile(Board b) { 
  int turnCounter = -1; // Declare turnCounter outside the try block to make it accessible everywhere
  try {
    String line;
    this.boardReader = new BufferedReader(new FileReader("D:\\users\\rafis\\OneDrive\\fileWriterBoard.txt"));
    
    // Read each line from the file
    while ((line = this.boardReader.readLine()) != null) {
      
      if (line.startsWith("Turn: ")) {
        System.out.println("inside");
        turnCounter = Integer.parseInt(line.substring(6));
        System.out.println(turnCounter);
      } else {
        // Read the row (i), column (j), and the piece ('e', 'b', 'B', 'w', 'W')
        int i = Character.getNumericValue(line.charAt(0)); 
        int j = Character.getNumericValue(line.charAt(1)); 
        char piece = line.charAt(2); 

        if (piece == 'e') {
          b.positionsOnBoard[i][j] = ' '; 
        } else {
          b.positionsOnBoard[i][j] = piece; 
        }
      }
    }
    this.boardReader.close(); 
  } 
  catch (IOException e) {
    e.printStackTrace();
  }

  return turnCounter; // Now turnCounter is accessible here
}

    
}
