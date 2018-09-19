import java.util.ArrayList;

public class Line {

    private StringBuffer line;
    private int cursorAt;
    private int lineLength;

    public Line(){
        line = new StringBuffer();
        cursorAt = 0;
        lineLength=0;
    }

    public void insert(char car){
        line.insert(cursorAt,car);
        cursorAt++;
        lineLength++;
    }

    public void delete(){
        if(lineLength==0) return;
        String rplc = line.substring(cursorAt,lineLength);
        cursorAt --;
        line.deleteCharAt(cursorAt);
        line.replace(cursorAt,lineLength-1,rplc);
        lineLength--;
    }

    public void RightArrow(){
        if(cursorAt==lineLength){
            lineLength++;
        }
        cursorAt++;
    }

    public void Arrow(){
        if(cursorAt==lineLength){
            lineLength++;
        }
        cursorAt++;
    }

    public void LeftArrow(){
        if(cursorAt==0){
            return;
        }
        cursorAt--;
    }

    public String getLine(){
        return line.toString();
    }
}
