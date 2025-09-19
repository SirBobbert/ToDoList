import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ToDoList toDoList = new ToDoList();


        while (true) {
            //TODO: Failsafe user input
            System.out.println("============ TO DO APP ============");
            System.out.println("1 - Add task");
            System.out.println("2 - View all tasks");
            System.out.println("3 - Update task status");
            System.out.println("4 - Remove task");
            System.out.println("5 - Update priority");
            System.out.println("6 - View all tasks sorted by deadlines");
            System.out.println("7 - View all tasks sorted by priority");
            System.out.println("8 - Check for old deadlines");
            System.out.println("9 - Sorts by priority then deadlines");
            System.out.println("0 - Quit");
            System.out.println("===================================");
            try {

                int userInput = scanner.nextInt();

                if (userInput == 0) {
                    System.out.println("Shutting down...");
                    break;
                }

                //TODO: fix the way we initialize ToDoList-class here
                toDoList.chooseAction(userInput);

            } catch (Exception e) {
                System.out.println("Invalid input - Main");
                scanner.nextLine();
            }
        }
    }
}
