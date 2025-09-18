public interface ToDoListFuncs {
    void create();

    void readGrouped();

    void update(int x);

    void delete(int x);

    PRIORITY updatePriority(int x);

    STATUS updateStatus(int x);

    void readSortedDeadlines();

    void readSortedPriorities();
}