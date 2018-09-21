public class Line {

    private StringBuffer line;
    private int cursorAt;
    private boolean isInsert;

    public Line(){
        line = new StringBuffer();
        cursorAt = 0;
        isInsert= false; //Edit mode by default
    }

    public int getLeftMoves(){
        return line.length()-cursorAt;
    }

    public void insert(char car){
        if(!isInsert) {
            line.insert(cursorAt, car);
            cursorAt++;
        }else{
            if(line.length()==0 || line.length()==cursorAt){
                line.insert(cursorAt,car);
                cursorAt++;
            }else{
                line.deleteCharAt(cursorAt);
                line.insert(cursorAt,car);
                cursorAt++;
            }
        }
    }

    public void delete(){
        if(line.length()==0) return;
        String rplc = line.substring(cursorAt,line.length());
        cursorAt --;
        line.deleteCharAt(cursorAt);
        line.replace(cursorAt,line.length(),rplc);
    }

    public boolean RightArrow(){
        if(cursorAt==line.length()){
            return false;
        }
        cursorAt++;
        return true;
    }

    public boolean LeftArrow(){
        if(cursorAt==0){
            return false;
        }
        cursorAt--;
        return true;
    }

    public int End(){
        if(cursorAt==line.length()){
            return 0;
        }
        int numToEnd = line.length()-cursorAt;
        cursorAt=line.length();
        return numToEnd;
    }

    public int Home(){
        if(cursorAt==0){
            return 0;
        }
        int numToBeg= cursorAt;
        cursorAt=0;
        return numToBeg;
    }

    public boolean isInsert() {
        return isInsert;
    }

    public void setInsertMode(){
        isInsert=true;
    }

    public void unsetInsertMode(){
        isInsert = false;
    }

    public String getFromCursor(){
        if(cursorAt==line.length()){
            return " ";
        }
        return line.substring(cursorAt,line.length());
    }

    public String getLine(){
        return line.toString();
    }
}
