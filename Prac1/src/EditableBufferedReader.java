import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class EditableBufferedReader extends BufferedReader {


    //Model Object that stores the state of the text introduced.
    private Line line;

    public EditableBufferedReader(Reader reader) {
        super(reader);
        line = new Line();
    }

    public void setRaw(){
        try {
            String[] cmd = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unsetRaw(){
        try {
            String[] cmd = {"/bin/sh", "-c", "stty echo cooked </dev/tty"};
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int read() throws IOException{

        //The read characters or sequence of characters are stored in this array read each time some key is pressed
        char[] ch = new char[6];
        int numChars = super.read(ch);

        if(numChars == 1){
            if((int)ch[0]<32 || (int)ch[0]>126){
                //some mapping of the characters
                switch(ch[0]){
                    case 13 : return EsqSec.ENTER;
                    case 27: return EsqSec.ESC;
                    case 127: return EsqSec.BACKSPACE;
                    default : return 0;
                }
            }else {
                return ch[0];
            }
        }else{
            if(ch[0]==27 && ch[1]==91){
                switch(ch[2]){
                    case  67: return EsqSec.RIGHT_ARROW;
                    case  68: return EsqSec.LEFT_ARROW;
                    case  49: return EsqSec.INSERT;
                    case  72: return EsqSec.HOME;
                    case  70: return EsqSec.END;
                }
            }
        }
        for(char c : ch ){
            System.out.print((int) c+" ");
        }
        return 0;
    }

    public String readLine(){

        this.setRaw();
        LineActionMapper lnM = new LineActionMapper(this.line);
        lnM.initializeMap();
        try {
            int read = 0;
            while(read != EsqSec.ENTER){
                lnM.matchF(read);
                read = this.read();
            }
        } catch (IOException e) { 
            e.printStackTrace();
        }
        this.unsetRaw();
        return line.getLine();
    }
}

class LineActionMapper{
    private Map<Integer, Runnable> actionsMap;
    private Line line;

    public LineActionMapper(Line line){
        this.actionsMap= new HashMap<>();
        this.line = line;
    }

    //Mapping of the actions for incoming read characters or sequence of characters
    public void initializeMap(){

        this.actionsMap.put( EsqSec.RIGHT_ARROW ,()->{
            boolean isRight = line.RightArrow();
            if(isRight) System.out.print(EsqSec.RIGHT_ARROW_s);
            return;
        });
        this.actionsMap.put( EsqSec.LEFT_ARROW ,()->{
            boolean isLeft =line.LeftArrow();
            if(isLeft) System.out.print(EsqSec.LEFT_ARROW_s);
            return;
        });
        this.actionsMap.put( EsqSec.BACKSPACE ,()->{
            line.delete();
            System.out.print(EsqSec.BACKSPACE_s);
            return;
        });
        this.actionsMap.put( EsqSec.END,()->{
            int numToEnd = line.End();
            if(numToEnd!=0) System.out.print("\033["+numToEnd+"C");

        });
        this.actionsMap.put( EsqSec.HOME,()->{
            int numToBeg = line.Home();
            if(numToBeg!=0) System.out.print("\033["+numToBeg+"D");
        });
        this.actionsMap.put( EsqSec.INSERT,()->{
            line.setInsertMode();
        });
        this.actionsMap.put( EsqSec.ESC,()->{
            line.unsetInsertMode();
        });

    }

    public void matchF(int read){

        if( !this.actionsMap.containsKey(read)) {
            if(read == 0){
                return;
            }
            boolean isInsert = line.isInsert();
            if(isInsert){
                line.insert((char) read);
                System.out.print(String.valueOf((char) read));
            }else{
                System.out.print("\033[K");
                System.out.print(String.valueOf((char) read));
                line.insert((char) read);
                System.out.print(line.getFromCursor());
                int moves = line.getLeftMoves();
                System.out.print("\033["+moves+"D");
            }
            return;
        }
        this.actionsMap.get(read).run();
    }
}
