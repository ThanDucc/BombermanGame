package uet.oop.bomberman.HighScore;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class WriteFile {

    public static void write(String word) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("res/HighScore.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert fileWriter != null;
        Writer writer = new BufferedWriter(fileWriter);
        try {
            writer.append(word);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
