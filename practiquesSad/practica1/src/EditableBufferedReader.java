import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.*;


public class EditableBufferedReader extends BufferedReader {

    //Model Object that stores the state of the text introduced.
    private final int RIGHT_ARROW= 1111;
    private Line line;
    private Reader rr;

    //Map<

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
        int pre =0;
        char[] ch = new char[3];
        int numCh = super.read(ch);
        if(numCh==3){
          if((int) ch[0]==27){
            return RIGHT_ARROW;
          }
        }else{
          return (int) ch[0];
        }

        return pre;
    }

    public String readLine(){

        this.setRaw();//Setting up the raw mode to read charcter by character introduced in the terminal
        try {
            while(true){
              int ch = this.read();
              if(ch==RIGHT_ARROW){
                char esc = 0x1B;
                int n = 1;
                System.out.print(String.format("%c[%dD",esc,n));
              }else{
                System.out.print((char) ch);
              }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.unsetRaw();
        return line.getLine();
    }
}
