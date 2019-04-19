package pl.maciejstanuch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Main {

    public static void main(String[] args) {

        if(args.length>0) {
            String filename = args[0];
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                try {

                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();
                    while (line != null) {
                        sb.append(line);
                        line = br.readLine();
                    }

                    String code = sb.toString();
                    BFInterpreter BFI = new BFInterpreter();
                    BFI.execute(code);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                System.out.println("Can't find the file: " + filename + "!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Usage: java -jar BFInterpreter.jar [filename]");
        }

    }
}
