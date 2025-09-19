import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ToDoList implements ToDoListFuncs {

    private final List<ToDoList> TASKS = new ArrayList<>();

    private static int counter = 1;
    private int id;
    private String text;
    private STATUS status = STATUS.TODO;
    private PRIORITY priority = PRIORITY.LOW;
    private LocalDate deadline;

    private final Scanner SCANNER = new Scanner(System.in);

    public void addDummyData() {
        // TODO: Remove dummy data
        TASKS.add(new ToDoList("Køb mælk", STATUS.TODO, PRIORITY.LOW, LocalDate.now().plusDays(1)));
        TASKS.add(new ToDoList("Aflever rapport", STATUS.IN_PROGRESS, PRIORITY.HIGH, LocalDate.now()));
        TASKS.add(new ToDoList("Træn i fitness", STATUS.DONE, PRIORITY.MEDIUM, LocalDate.now().minusDays(2)));
        TASKS.add(new ToDoList("Lær Java Streams", STATUS.TODO, PRIORITY.HIGH, LocalDate.now().plusWeeks(1)));
        TASKS.add(new ToDoList("Book ferie", STATUS.IN_PROGRESS, PRIORITY.LOW, LocalDate.now().plusDays(10)));
    }


    public ToDoList(String text, STATUS status, PRIORITY priority, LocalDate deadline) {
        this.id = counter++;
        this.text = text;
        this.status = status;
        this.priority = priority;
        this.deadline = deadline;
    }

    public ToDoList() {
    }

    // helper functions
    private void printHeader(String action) {
        System.out.println("---- " + action.toUpperCase() + " ----");
    }

    private int indexById(int id) {
        for (int i = 0; i < TASKS.size(); i++) {
            if (TASKS.get(i).id == id) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unknown ID");
    }

    private void printPriorities() {

        for (int i = 0; i < PRIORITY.values().length; i++) {
            System.out.println(i + 1 + ":" + PRIORITY.values()[i]);
        }
    }

    private void printStatuses() {
        for (int i = 0; i < STATUS.values().length; i++) {
            System.out.println(i + 1 + ":" + STATUS.values()[i]);
        }
    }

    public void chooseAction(int x) {

        // addDummyData();


        switch (x) {
            case 1 -> create();
            case 2 -> {
                printHeader("View tasks");

                readGrouped();
            }
            case 3 -> update();
            case 4 -> delete();
            case 5 -> updatePriority();
            case 6 -> readSortedDeadlines();
            case 7 -> readSortedPriorities();
            case 8 -> checkForOldDeadlines();
            case 9 -> sortByPriorityThenDeadline();
        }
    }

//TODO: Tweaking
@Override
public String toString() {
    return "ToDoList{" +
            "TASKS=" + TASKS +
            ", id=" + id +
            ", text='" + text + '\'' +
            ", status=" + status +
            ", priority=" + priority +
            ", deadline=" + deadline +
            '}';
}

@Override
public void create() {
    printHeader("Add task");
    System.out.println("Name of the task: >>>");
    String taskName = SCANNER.nextLine();
    printHeader("Choose priority");
    printPriorities();
    int taskPriority = SCANNER.nextInt();

    PRIORITY priority = switch (taskPriority) {
        case 1 -> PRIORITY.LOW;
        case 2 -> PRIORITY.MEDIUM;
        case 3 -> PRIORITY.HIGH;
        default -> throw new IllegalStateException("Unexpected value: " + taskPriority);
    };


    printHeader("Choose deadline");
    System.out.println("Example: dd-mm-yyyy");

    //scanner bug
    String str = SCANNER.nextLine();

    String test = SCANNER.nextLine();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    if (test.isEmpty()) {
        deadline = LocalDate.now();
    } else {
        deadline = LocalDate.parse(test, dtf);
    }

    ToDoList newTask = new ToDoList(taskName, STATUS.TODO, priority, deadline);
    TASKS.add(newTask);
    System.out.println(newTask);
    System.out.println("Task added successfully.");
}

@Override
public void readGrouped() {

    if (TASKS.isEmpty()) {
        printHeader("Task list is empty");
    } else {


        Map<STATUS, List<ToDoList>> grouped =
                TASKS.stream().collect(Collectors.groupingBy(t -> t.status));

        for (STATUS s : STATUS.values()) {
            System.out.println("\n" + s + ":");
            List<ToDoList> list = grouped.getOrDefault(s, List.of());
            if (list.isEmpty()) {
                System.out.println("(ingen opgaver)");
            } else {
                list.stream()
                        .sorted(Comparator.comparingInt(t -> t.id))
                        .forEach(System.out::println);
            }
        }
    }
}

@Override
public void update() {
    printHeader("Update task status");
    System.out.println("Pick task based on id: >>>");
    readGrouped();

    int id = SCANNER.nextInt();
    ToDoList t = TASKS.get(indexById(id));

    t.status = t.status.next();
    System.out.println("The task: " + t.text + " has been updated to " + t.status);
}

@Override
public void delete() {
    printHeader("Remove task");
    System.out.println("Remove task based on id: >>>");
    readGrouped();

    int id = SCANNER.nextInt();
    ToDoList removed = TASKS.remove((indexById(id)));

    System.out.println("The task: " + removed.text + " has successfully been removed");
}

@Override
public void updatePriority() {
    printHeader("Update priority");
    System.out.println("Update priority based on id: >>>");
    readGrouped();


    int id = SCANNER.nextInt();
    ToDoList t = TASKS.get(indexById(id));

    System.out.println("Current task has the priority of: " + t.priority);
    System.out.println("Choose new priority based on id: >>>");
    printPriorities();

    int userInput = SCANNER.nextInt();
    switch (userInput) {
        case 1:
            t.priority = PRIORITY.LOW;
            break;
        case 2:
            t.priority = PRIORITY.MEDIUM;
            break;
        case 3:
            t.priority = PRIORITY.HIGH;
            break;
    }

    System.out.println("The task: " + t.text + " has been updated to " + t.priority);
}

@Override
public void readSortedDeadlines() {
    printHeader("Sort array by deadlines");

    TASKS.stream()
            .sorted(Comparator.comparing(t -> t.deadline))
            .forEach(System.out::println);
}

@Override
public void readSortedPriorities() {
    printHeader("Sort array by priorities");

    TASKS.stream()
            .sorted(Comparator.comparing((ToDoList t) -> t.priority).reversed())
            .forEach(System.out::println);
}

@Override
public void checkForOldDeadlines() {
    printHeader("Passed deadlines");

    TASKS.stream()
            .filter(x -> x.deadline.isBefore(LocalDate.now()))
            .sorted(Comparator.comparing(ToDoList::getDeadline))
            .forEach(System.out::println);
}

@Override
public void sortByPriorityThenDeadline() {
    // doesn't show passed deadlines
    TASKS.stream().filter(x -> x.deadline.isBefore(LocalDate.now()));

    TASKS.sort(Comparator
            .comparing(ToDoList::getPriority).reversed()
            .thenComparing(ToDoList::getDeadline)
    );

    for (ToDoList t : TASKS) {
        System.out.println(t.toString());
    }
}

public PRIORITY getPriority() {
    return priority;
}

public LocalDate getDeadline() {
    return deadline;
}
}

