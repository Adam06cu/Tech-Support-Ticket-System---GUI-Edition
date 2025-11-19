public class Ticket {

    private int id;
    private String name;
    private String issue;
    private String priority; // "Low", "Medium", "High"

    public Ticket(int id, String name, String issue, String priority) {
        this.id = id;
        this.name = name;
        this.issue = issue;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIssue() {
        return issue;
    }

    public String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Ticket ID: " + id +
               "\nRequester: " + name +
               "\nPriority: " + priority +
               "\nIssue: " + issue +
               "\n-----------------------------\n";
    }
}
