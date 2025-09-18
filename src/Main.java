import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ToDoList toDoList = new ToDoList();
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("-----------------------------------");
            System.out.println("What command would you like to use?");
            System.out.println("Press the following:");

            System.out.println("1 - Add to list");
            System.out.println("2 - Remove from list");
            System.out.println("3 - View all ToDo's on list");
            System.out.println("4 - Mark ToDo as finished");
            System.out.println("0 - Quit");
            System.out.println("-----------------------------------");

            int userInput = scanner.nextInt();

            if (userInput == 0) {
                System.out.println("Shutting down...");
                break;
            }

            toDoList.chooseAction(userInput);
        }

    }
}
