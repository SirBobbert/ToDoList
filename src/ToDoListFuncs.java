public interface ToDoListFuncs {
    void create();

    void readGrouped();

    void update(int x);

    void delete(int x);

    void updatePriority(int x);

    STATUS updateStatus(int x);

    void readSortedDeadlines();

    void readSortedPriorities();
}