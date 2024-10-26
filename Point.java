import java.util.Arrays;
import java.lang.Math;
class Point{

    public int x;
    public int y;
    
    
    
    public Point(int x, int y){
      this.x = x;
      this.y = y;
    }
    
    
    
    public int getX() {return this.x;}
    public int getY() {return this.y;}
    
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    
    public Point transferToSquareCords(int sizeBoard){ // Works good with offset 
      int k; int m;
      int sizeSquare = sizeBoard / 8;
      k = this.x / sizeSquare;
      m = this.y / sizeSquare;
      Point j = new Point(k,m);
      return j;
    }
    
    public Point transferToMiddleCords(int sizeBoard){ // from 745 to 750
      int sizeSquare = sizeBoard / 8;
      int k; int m;
      k = (this.x / sizeSquare) * sizeSquare + sizeSquare / 2;
      m = (this.y / sizeSquare) * sizeSquare + sizeSquare / 2;
      Point j = new Point(k,m);
      return j;
    }



    public String toString(){
     return ("(" + x + ", " + y + ")");
    }
    
     public void resetPoint(){ 
      this.x = -1;
      this.y = -1;
    }
    

    
    public double distance (Point p){
      return Math.sqrt(  Math.pow(this.x - p.getX(),2)  +  Math.pow(this.y - p.getY(),2)  );
    }
    
  
}
