package operation;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String exerciseFile = "module\\packages\\operation\\Exercises.txt";
        String answerFile = "module\\packages\\operation\\Answer.txt";
        String Grade = "module\\packages\\operation\\Grade.txt";
        FileOperation fo = new FileOperation(exerciseFile,answerFile);
        fo.generateQuestionsAndAnswer(100);
        fo.compareResult(new FileWriter(Grade));
    }
}
