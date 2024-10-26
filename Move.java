class Move{

    int previous;
    int after;


    public Move(){
        this.previous = -1;
        this.after = -1;
    }

    public Move(int previous, int after){
        this.previous = previous;
        this.after = after;
    }

    public void setPreviuos(int x) {this.previous = previous;}
    public void setAfter(int after) {this.after = after;}


    public Move[] addMoveToArray(Move[] arr, Move newMove) {
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == null){
                arr[i] = newMove;
                break;
            }
        }

    return arr;
    }

}