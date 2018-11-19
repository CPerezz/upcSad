import java.io.*;

public class Main {

    public static String execute(String command) {
        String res = "";
        String[] commands = {"/bin/bash", "-c", command};
        try {
            Process proc = new ProcessBuilder(commands).start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            res = stdInput.readLine();
        } catch (IOException e) {
            return e.getMessage();
        }
        return res;
    }
    
    public static void main(String args[]) {
        String cols = "";
        String lines = "";
        while (true) {
            String new_cols = execute("tput cols");
            String new_lines = execute("tput lines");
            if (!new_cols.equals(cols) || !new_lines.equals(lines)) {
                cols = new_cols;
                lines = new_lines;
                System.out.println("cols: " + cols);
                System.out.println("lines: " + lines);
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
        }
    }
}
