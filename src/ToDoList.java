import java.util.*;
import java.util.stream.Collectors;

public class ToDoList implements ToDoListFuncs {

    private final List<ToDoList> tasks = new ArrayList<>();
    String text;
    STATUS status = STATUS.TODO;

    public ToDoList(String text, STATUS status) {
        this.text = text;
        this.status = status;
    }

    public ToDoList() {
    }


    // helper functions
    private void printHeader(String action) {
        System.out.println("---- " + action.toUpperCase() + " ----");
    }

    public void read() {
        for (int i = 0; i < tasks.size(); i++) {
            ToDoList currentItem = tasks.get(i);
            System.out.println(i + ": " + currentItem.status + " - " + currentItem.text);
        }
    }

    public void chooseAction(int x) {

        Scanner scanner = new Scanner(System.in);

        switch (x) {
            case 1 -> {
                printHeader("Add task");
                String addInput = scanner.nextLine();
                create(new ToDoList(addInput, STATUS.TODO));
                System.out.println("Task added successfully.");
            }
            case 2 -> {
                printHeader("View tasks");
                readGrouped();
            }
            case 3 -> {
                printHeader("Update task status");
                read();
                int updateInput = scanner.nextInt();
                update(updateInput);
            }
            case 4 -> {
                printHeader("Remove task");
                read();
                int deleteInput = scanner.nextInt();
                delete(deleteInput);
                System.out.println("Task removed.");
            }
        }
    }


    @Override
    public String toString() {
        return status + " - " + text;
    }


    @Override
    public void create(ToDoList x) {
        tasks.add(x);
    }

    @Override
    public void readGrouped() {
        Map<STATUS, List<ToDoList>> grouped =
                tasks.stream().collect(Collectors.groupingBy(t -> t.status));

        for (STATUS status : STATUS.values()) {
            System.out.println("\n" + status + ":");
            List<ToDoList> list = grouped.getOrDefault(status, List.of());
            if (list.isEmpty()) {
                System.out.println("  (ingen opgaver)");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    ToDoList t = list.get(i);
                    System.out.println("  " + (i + 1) + " -> " + t.text);
                }
            }
        }
    }

    @Override
    public void update(int x) {

        ToDoList currentItem = tasks.get(x);

        System.out.println("you chose to update " + currentItem.text + " with the status of " + currentItem.status);

        currentItem.status = currentItem.status.next();

        System.out.println("Updated to " + currentItem.status);
    }

    @Override
    public void delete(int x) {
        tasks.remove(x);
    }



}

