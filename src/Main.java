import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String[] COMMANDS = {"binaryfloat", "runlength", "occurtable", "charprob", "help", "quit"};
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String input;
        String selection;
        do {
            System.out.println("Please select a run option:");
            selection = scan.nextLine();
            switch (selection) {
                case "binaryfloat":
                    FloatingPoints.run();
                    break;

                case "help":
                    System.out.println("Commands:");
                    for (String command : COMMANDS) {
                        System.out.print(command + " ");
                    }
                    System.out.println();
                    break;

                case "quit":
                    System.out.println("Bye!");
                    scan.close();
                    break;

                case "runlength":
                    System.out.println("Please enter a string to encode:");
                    input = scan.nextLine();
                    System.out.println("Here is your encoded string: " + Encoding.encodeRunLength(input));
                    break;

                case "occurtable":
                    System.out.println("Please enter a string:");
                    input = scan.nextLine();
                    Map<Character, Integer> occurtable = Encoding.charOccurrences(input);

                    System.out.println("Char | Occurrences");
                    System.out.println("------------------");
                    for (Map.Entry<Character, Integer> entry : occurtable.entrySet()) {
                        char key = entry.getKey();
                        int value = entry.getValue();
                        System.out.printf("%4s | %d%n", key, value);
                    }
                    break;

                case "charprob":
                    System.out.println("Please enter a string:");
                    input = scan.nextLine();
                    Map<Character, Double> charprob = Encoding.charOccurProbability(input);

                    System.out.println("Char | Probability");
                    System.out.println("------------------");
                    for (Map.Entry<Character, Double> entry : charprob.entrySet()) {
                        char key = entry.getKey();
                        double value = entry.getValue();
                        System.out.printf("%4s | %.3f%n", key, value);
                    }
                    break;

                default:
                    System.out.println("Invalid option. See 'help' for more info.");
            }
        } while (!selection.equals("quit"));
    }
}
