import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void chooseAction(int x) {
        checkForOldDeadlines();

        //TODO: Remove dummy data
//        TASKS.add(new ToDoList("Køb mælk", STATUS.TODO, PRIORITY.LOW, LocalDateTime.now().plusDays(1)));
//        TASKS.add(new ToDoList("Aflever rapport", STATUS.IN_PROGRESS, PRIORITY.HIGH, LocalDateTime.now().plusHours(6)));
//        TASKS.add(new ToDoList("Træn i fitness", STATUS.DONE, PRIORITY.MEDIUM, LocalDateTime.now().minusDays(2)));
//        TASKS.add(new ToDoList("Lær Java Streams", STATUS.TODO, PRIORITY.HIGH, LocalDateTime.now().plusWeeks(1)));
//        TASKS.add(new ToDoList("Book ferie", STATUS.IN_PROGRESS, PRIORITY.LOW, LocalDateTime.now().plusDays(10)));

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
                int id = SCANNER.nextInt();
                SCANNER.nextLine();
                update(id);
            }
            case 4 -> {
                printHeader("Remove task");
                readGrouped();
                int id = SCANNER.nextInt();
                SCANNER.nextLine();
                delete(id);
                System.out.println("Task removed.");
            }
            case 5 -> {
                printHeader("Update priority");
                readGrouped();
                int id = SCANNER.nextInt();
                SCANNER.nextLine();
                updatePriority(id);
                System.out.println("Priority updated");
            }
            case 6 -> {
                readSortedDeadlines();
            }
            case 7 -> {
                readSortedPriorities();
            }
            case 8 -> {
                checkForOldDeadlines();
            }
            case 9 -> {
                sortByPriorityThenDeadline();
            }
        }
    }

    //TODO: Update toString?
    @Override
    public String toString() {
        return id + ": " + status + " - " + text;
    }


    @Override
    public void create() {
        printHeader("Add task");
        String addInput = SCANNER.nextLine();
        printHeader("Choose status");
        int statusInput = SCANNER.nextInt();
        printHeader("Choose priority");
        int priorityInput = SCANNER.nextInt();
        PRIORITY priority = switch (priorityInput) {
            case 1 -> PRIORITY.LOW;
            case 2 -> PRIORITY.MEDIUM;
            case 3 -> PRIORITY.HIGH;
            default -> throw new IllegalStateException("Unexpected value: " + statusInput);
        };


        printHeader("Choose deadline");
        System.out.println("Example: dd-mm-yyyy");
        // scanner bug
        String str = SCANNER.nextLine();
        String test = SCANNER.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        ToDoList t = new ToDoList(addInput, STATUS.TODO, priority, LocalDate.parse(test, dtf));
        TASKS.add(t);
        System.out.println("Task added successfully.");
    }

    @Override
    public void readGrouped() {
        // TODO: Add deadline upon creation
        printHeader("View tasks");
        Map<STATUS, List<ToDoList>> grouped =
                TASKS.stream().collect(Collectors.groupingBy(t -> t.status));
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
        ToDoList t = TASKS.get(indexById(x));
        System.out.println("you chose to update " + t.text + " with the status of " + t.status);
        t.status = t.status.next();
        System.out.println("Updated to " + t.status);
    }

    @Override
    public void delete(int x) {
        ToDoList removed = TASKS.remove((indexById(x)));
        System.out.println("Deleted: " + removed.id + " - " + removed.text);
    }

    @Override
    public void updatePriority(int x) {
        ToDoList t = TASKS.get(indexById(x));

        System.out.println("---------------");
        System.out.println(t.toString());

        System.out.println(t.priority);
        System.out.println("---------------");

        System.out.println("Current task has the prio of: " + t.priority);
        System.out.println("1 is LOW");
        System.out.println("2 is MEDIUM");
        System.out.println("3 is HIGH");
        System.out.println("Update new prio >>>");
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
        System.out.println(t + " has been updated to " + t.priority + " priority");
    }

    @Override
    public STATUS updateStatus(int x) {
        ToDoList t = TASKS.get(indexById(x));
        System.out.println("Current task has the prio of: " + t.status);
        System.out.println("1 is TODO");
        System.out.println("2 is IN_PROGRESS");
        System.out.println("3 is DONE");
        System.out.println("Update new status >>>");
        int userInput = SCANNER.nextInt();
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
        System.out.println("Sort array by deadlines");

        TASKS.stream()
                .sorted(Comparator.comparing(t -> t.deadline))
                .forEach(t -> System.out.println(
                        t.id + ": " + t.text + " (deadline " + t.deadline.getDayOfMonth() + "/" + t.deadline.getDayOfMonth() + "/" + t.deadline.getYear() + ")"
                ));
    }

    @Override
    public void readSortedPriorities() {
        System.out.println("Sort array by priorities");

        TASKS.stream()
                .sorted(Comparator.comparing((ToDoList t) -> t.priority).reversed())
                .forEach(t -> System.out.println(
                        t.id + ": " + t.text + " | priority " + t.priority
                ));
    }

    @Override
    public void checkForOldDeadlines() {
        printHeader("Old deadlines");
        for (ToDoList t : TASKS) {
            if (t.deadline.isBefore(LocalDate.now())) {

                TASKS.stream()
                        .sorted(Comparator.comparing(y -> t.deadline.isBefore(LocalDate.now())))
                        .forEach(y -> System.out.println(

                                t.id + ": " + t.text + " (deadline " + t.deadline.getDayOfMonth() + "/" + t.deadline.getDayOfMonth() + "/" + t.deadline.getYear() + ")"
                        ));
            }
        }
    }

    @Override
    public void sortByPriorityThenDeadline() {

        TASKS.sort(Comparator
                .comparing(ToDoList::getPriority).reversed()
                .thenComparing(ToDoList::getDeadline)
        );

        for (ToDoList t : TASKS) {
            TASKS.forEach(y -> System.out.println(
                    "ID: " + t.id + " | " + t.text + " | DEADLINE " + t.deadline.getDayOfMonth() + "/" + t.deadline.getDayOfMonth() + "/" + t.deadline.getYear() + " | PRIO: " + t.priority
            ));
        }

    }

    public PRIORITY getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}

