class Button{
    int x;
    int y;
    int height;
    int width;
    int R;
    int G;
    int B;

    String text;


    public Button(int x, int y, int height, int width, int R, int G, int B,String text){
      this.x = x;
      this.y = y;
      this.R = R;
      this.G = G;
      this.B = B;
      this.height = height;
      this.width = width;
      this.text = text;
    }

    public Button(){
      this.x = 0;
      this.y = 0;
      this.R = 0;
      this.G = 0;
      this.B = 0;
      this.height = 0;
      this.width = 0;
      this.text = "";
    }

    public int getX() {return this.x;}
    public int getY() {return this.y;}
    public int getHeight() {return this.height;}
    public int getWidth() {return this.width;}




}
