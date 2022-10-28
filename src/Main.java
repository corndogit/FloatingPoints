import java.util.Scanner;

public class Main {
    private static final String[] commands = {"binaryfloat", "help", "quit"};
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
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
                    for (String command : commands) {
                        System.out.print(command + " ");
                    }
                    System.out.println();
                    break;

                case "quit":
                    System.out.println("Bye!");
                    scan.close();
                    break;

                default:
                    System.out.println("Invalid option. See 'help' for more info.");
            }
        } while (!selection.equals("quit"));
    }
}
