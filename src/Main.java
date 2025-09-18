import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ToDoList toDoList = new ToDoList();
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("============ TO DO APP ============");
            System.out.println("1 - Add task");
            System.out.println("2 - View all tasks");
            System.out.println("3 - Update task status");
            System.out.println("4 - Remove task");
            System.out.println("0 - Quit");
            System.out.println("===================================");

            int userInput = scanner.nextInt();

            if (userInput == 0) {
                System.out.println("Shutting down...");
                break;
            }

            toDoList.chooseAction(userInput);
        }

    }
}
