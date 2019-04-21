package pl.maciejstanuch;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class BFInterpreter {

    private Integer index;
    private Integer parserPos;
    private byte[] bytesArray;
    private static final int ARRAY_SIZE = 30000;
    private Integer loopStart;
    private Integer loopEnd;

    BFInterpreter() {
        index = 0;
        parserPos = 0;
        bytesArray = new byte[ARRAY_SIZE];
        for(int i=0;i<ARRAY_SIZE;i++){
            bytesArray[i]=0;
        }
        loopStart = null;
        loopEnd = null;
    }

    public void execute(String code) {

        while(parserPos<code.length()){
            char ch = code.charAt(parserPos);
            switch(ch){
                case '>':
                    incPtr();
                    break;

                case '<':
                    decPtr();
                    break;

                case '+':
                    incValue();
                    break;

                case '-':
                    decValue();
                    break;

                case '[':
                    startLoop();
                    break;

                case ']':
                    endLoop();
                    break;

                case '.':
                    writeChar();
                    break;

                case ',':
                    readChar();
                    break;

                default:
                    //Ignore other chars
                    break;
            }
        parserPos++;
        }



    }

    private void incPtr() { //>
        index++;
        if (index >= bytesArray.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private void decPtr() { //<
        index--;
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private void incValue() { //+
        bytesArray[index]++;
    }

    private void decValue() { //-
        bytesArray[index]--;
    }

    private void startLoop() { //[
        loopStart = parserPos;

        if (bytesArray[index] == 0) {
            parserPos = loopEnd;
            loopEnd = null;
            loopStart = null;
        }
    }

    private void endLoop() { //]

        if (loopStart == null) {
            throw new RuntimeException("Interpretation Error: Found \"]\" without \"[\".");
        } else {
            loopEnd = parserPos;
            parserPos = loopStart-1;
        }
    }

    private void readChar(){ //,
        try {
            char c = (char) System.in.read();
            bytesArray[index] = (byte)c;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeChar(){ //.
        char c = (char)bytesArray[index];
        System.out.print(c);
    }
}
