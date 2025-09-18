import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ToDoList implements ToDoListFuncs {

    private final List<ToDoList> tasks = new ArrayList<>();

    private static int counter = 1;
    private int id;
    private String text;
    private STATUS status = STATUS.TODO;
    private PRIORITY priority = PRIORITY.LOW;
    private LocalDateTime deadline;

    private Scanner scanner = new Scanner(System.in);


    public ToDoList(String text, STATUS status, PRIORITY priority, LocalDateTime deadline) {
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

    public void read() {
        if (tasks.isEmpty()) {
            System.out.println("(ingen opgaver)");
            return;
        }
        for (ToDoList t : tasks) {
            System.out.println(t.id + ": " + t.status + " - " + t.text + " " + t.priority + " - deadline: " + t.deadline);
        }
    }

    private int indexById(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).id == id) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unknown ID");
    }

    public void chooseAction(int x) {

        tasks.add(new ToDoList("Køb mælk", STATUS.TODO, PRIORITY.LOW, LocalDateTime.now().plusDays(1)));
        tasks.add(new ToDoList("Aflever rapport", STATUS.IN_PROGRESS, PRIORITY.HIGH, LocalDateTime.now().plusHours(6)));
        tasks.add(new ToDoList("Træn i fitness", STATUS.DONE, PRIORITY.MEDIUM, LocalDateTime.now().minusDays(2)));
        tasks.add(new ToDoList("Lær Java Streams", STATUS.TODO, PRIORITY.HIGH, LocalDateTime.now().plusWeeks(1)));
        tasks.add(new ToDoList("Book ferie", STATUS.IN_PROGRESS, PRIORITY.LOW, LocalDateTime.now().plusDays(10)));

        switch (x) {
            case 1 -> {
                create();
            }
            case 2 -> {
                readGrouped();
            }
            case 3 -> {
                printHeader("Update task status");
                readGrouped();
                int id = scanner.nextInt();
                scanner.nextLine();
                update(id);
            }
            case 4 -> {
                printHeader("Remove task");
                readGrouped();
                int id = scanner.nextInt();
                scanner.nextLine();
                delete(id);
                System.out.println("Task removed.");
            }
            case 5 -> {
                printHeader("Update priority");
                readGrouped();
                int id = scanner.nextInt();
                scanner.nextLine();
                updatePriority(id);
                System.out.println("Priority updated");
            }
            case 6 -> {
                readSortedDeadlines();
            }
            case 7 -> {
                readSortedPriorities();
            }
        }
    }


    @Override
    public String toString() {
        return id + ": " + status + " - " + text;
    }


    @Override
    public void create() {


        printHeader("Add task");
        String addInput = scanner.nextLine();

        printHeader("Choose status");
        int statusInput = scanner.nextInt();


        printHeader("Choose priority");
        int priorityInput = scanner.nextInt();

        PRIORITY priority = switch (priorityInput) {
            case 1 -> PRIORITY.LOW;
            case 2 -> PRIORITY.MEDIUM;
            case 3 -> PRIORITY.HIGH;
            default -> throw new IllegalStateException("Unexpected value: " + statusInput);
        };


        // TODO: Add deadline upon creation
        printHeader("Choose deadline");
        System.out.println("Example: ");
        LocalDateTime deadline = LocalDateTime.now();

        ToDoList t = new ToDoList(addInput, STATUS.TODO, priority, deadline);


        System.out.println(t);
        System.out.println("Task added successfully.");

        tasks.add(t);
    }

    @Override
    public void readGrouped() {
        printHeader("View tasks");

        Map<STATUS, List<ToDoList>> grouped =
                tasks.stream().collect(Collectors.groupingBy(t -> t.status));

        for (STATUS s : STATUS.values()) {
            System.out.println("\n" + s + ":");
            List<ToDoList> list = grouped.getOrDefault(s, List.of());
            if (list.isEmpty()) {

                System.out.println("  (ingen opgaver)");
            } else {
                list.stream()
                        .sorted(Comparator.comparingInt(t -> t.id))
                        .forEach(t -> System.out.println("ID: " + t.id + " | TASK: " + t.text + " | PRIO: " + t.priority + " | DEADLINE: " + t.deadline));

            }
        }
    }

    @Override
    public void update(int x) {
        ToDoList t = tasks.get(indexById(x));

        System.out.println("you chose to update " + t.text + " with the status of " + t.status);

        t.status = t.status.next();

        System.out.println("Updated to " + t.status);
    }

    @Override
    public void delete(int x) {
        ToDoList removed = tasks.remove((indexById(x)));
        System.out.println("Deleted: " + removed.id + " - " + removed.text);
    }

    @Override
    public PRIORITY updatePriority(int x) {
        ToDoList t = tasks.get(indexById(x));
        System.out.println("Current task has the prio of: " + t.priority);
        System.out.println("1 is LOW");
        System.out.println("2 is MEDIUM");
        System.out.println("3 is HIGH");
        System.out.println("Update new prio >>>");
        int userInput = scanner.nextInt();
        switch (userInput) {
            case 1:
                t.priority = PRIORITY.LOW;
                return PRIORITY.LOW;
            case 2:
                t.priority = PRIORITY.MEDIUM;
                return PRIORITY.LOW;
            case 3:
                t.priority = PRIORITY.HIGH;
                return PRIORITY.HIGH;
        }
        System.out.println(t + " has been updated to " + t.priority + " priority");
        return PRIORITY.LOW;
    }

    @Override
    public STATUS updateStatus(int x) {
        ToDoList t = tasks.get(indexById(x));
        System.out.println("Current task has the prio of: " + t.status);
        System.out.println("1 is TODO");
        System.out.println("2 is IN_PROGRESS");
        System.out.println("3 is DONE");
        System.out.println("Update new status >>>");
        int userInput = scanner.nextInt();
        switch (userInput) {
            case 1:
                t.status = STATUS.TODO;
                return STATUS.TODO;
            case 2:
                t.status = STATUS.IN_PROGRESS;
                return STATUS.IN_PROGRESS;
            case 3:
                t.status = STATUS.DONE;
                return STATUS.DONE;
        }
        System.out.println(t + " has been updated to " + t.priority + " priority");
        return STATUS.TODO;
    }

    @Override
    public void readSortedDeadlines() {
        //TODO: display entire 'tasks'-array sorted by deadlines
        System.out.println("Sort array by deadlines");

        tasks.stream()
                .sorted(Comparator.comparing(t -> t.deadline))
                .forEach(t -> System.out.println(
                        t.id + ": " + t.text + " (deadline " + t.deadline.getDayOfMonth() + "/" + t.deadline.getDayOfMonth() + "/" + t.deadline.getYear() + ")"
                ));
    }

    @Override
    public void readSortedPriorities() {
        //TODO: display entire 'tasks'-array sorted by priorities
        System.out.println("Sort array by priorities");


        tasks.stream()
                .sorted(Comparator.comparing((ToDoList t) -> t.priority).reversed())
                .forEach(t -> System.out.println(
                        t.id + ": " + t.text + " | priority " + t.priority
                ));
    }
}

