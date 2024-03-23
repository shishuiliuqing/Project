package operation;

import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("命令行参数输入有误");
            return;
        }

        String exerciseFile = "Exercises.txt";
        String answerFile = "Answer.txt";
        String Grade = "Grade.txt";
        if (args[0].equals("-n")) {
            try {
                int range;
                int n;
                n = Integer.parseInt(args[1]);
                if (args[2].equals("-r")) range = Integer.parseInt(args[3]);
                else {
                    System.out.println("命令行参数输入有误");
                    return;
                }
                FileOperation fo = new FileOperation(exerciseFile, answerFile);
                fo.generateQuestionsAndAnswer(n, range);
            } catch (Exception e) {
                System.out.println("命令行参数输入有误");
            }
        } else if (args[0].equals("-e")) {
            try {
                exerciseFile = args[1];
                if (args[2].equals("-a")) answerFile = args[3];
                else {
                    System.out.println("命令行参数有误");
                    return;
                }
                FileOperation fo = new FileOperation(exerciseFile, answerFile);
                fo.compareResult(new FileWriter(Grade));
            } catch (Exception e) {
                System.out.println("命令行输入参数有误");
            }
        } else {
            System.out.println("命令行参数有误");
        }
    }
}
