import java.util.ArrayList;

public class Line {

    private ArrayList<Integer> line;

    public Line(){
        line = new ArrayList<Integer>();
    }

    public void addCharacter(Integer car){
        System.out.println(car);
        line.add(car);

    }

    public String getLine(){
        String res = "";
        for(int carac :line){
            res = res+Integer.toString(carac);
        }
        return res;
    }

    public String setLineStatus(){
        return "";
    }
}
