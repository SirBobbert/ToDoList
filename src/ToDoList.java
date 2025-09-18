import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ToDoList implements ToDoListFuncs {

    List<String> toDo = new ArrayList<>();

    public void chooseAction(int x) {

        Scanner scanner = new Scanner(System.in);

        switch (x) {
            case 1:
                System.out.println("Add to list: >>>");
                String addInput = scanner.nextLine();
                create(addInput);
                break;
            case 2:
                System.out.println("Remove from list: >>>");
                for (int i = 0; i < toDo.size(); i++) {
                    System.out.println(i + ": " + toDo.get(i));
                }
                int deleteInput = scanner.nextInt();
                delete(deleteInput);
                break;
            case 3:
                System.out.println("View list: >>>");
                for (int i = 0; i < toDo.size(); i++) {
                    System.out.println(i + ": " + toDo.get(i));
                }
                break;
            case 4:
                System.out.println("Update list status >>>");
                for (int i = 0; i < toDo.size(); i++) {
                    System.out.println(i + ": " + toDo.get(i));
                }
                int updateInput = scanner.nextInt();
                update(updateInput);
                break;
        }
    }


    @Override
    public String toString() {
        return "ToDoList{" +
                "toDo=" + toDo +
                '}';
    }

    @Override
    public void create(String x) {
        toDo.add(x);
    }

    @Override
    public void read(int x) {
    }

    @Override
    public void update(int x) {
        toDo.set(x, toDo.get(x) + " - DONE");
    }

    @Override
    public void delete(int x) {
        toDo.remove(x);
    }
}
