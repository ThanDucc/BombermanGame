package uet.oop.bomberman.File;

import java.io.*;
import java.util.ArrayList;

public class ReadFile {

    public static ArrayList<String[]> read() {
        ArrayList<String[]> listInfo = new ArrayList<>();
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader("res/HighScore.txt"));
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] word = line.split("\t\t");
                listInfo.add(word);
                line = bufferedReader.readLine();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listInfo;
    }

    public static ArrayList<String[]> readAndSort() {
        ArrayList<String[]> listInfo = new ArrayList<>();
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader("res/HighScore.txt"));
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] word = line.split("\t\t");

                listInfo.add(word);
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!listInfo.isEmpty()) {
            listInfo.sort((o1, o2) -> {
                if (o1 != null && o2 != null) {
                    if (Integer.parseInt(o1[0]) < Integer.parseInt(o2[0])) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                return 0;
            });
        }

        return listInfo;
    }

}
