public enum STATUS {
    TODO, IN_PROGRESS, DONE;

    public STATUS next() {
        return switch (this) {
            case TODO -> IN_PROGRESS;
            case IN_PROGRESS, DONE -> DONE;
        };
    }
}
