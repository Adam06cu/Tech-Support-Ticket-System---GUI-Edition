/*---------------------------------------------------
Name:  Ian Sanz, Eric Castro Leyva, Adam Martinez
COP 2805C - Java Programming 2
Fall 2025 - W 5:30 PM - 8:50 PM
Assignment # 4
Plagiarism Statement
I certify that this assignment is my own work and that I have not
copied in part or whole or otherwise plagiarized the work of other
students, persons, Generative Pre-trained Generators (GPTs) or any other AI tools.
I understand that students involved in academic dishonesty will face
disciplinary sanctions in accordance with the College's Student Rights
and Responsibilities Handbook (https://www.mdc.edu/rightsandresponsibilities)
----------------------------------------------------------*/

public class Ticket {

    int id;
    String name;
    String issue;
    String priority;
        
    public Ticket(int id, String name, String issue , String priority) {
        this.id = id;
        this.name = name;
        this.issue = issue;
        this.priority = priority;
    }
    
    @Override
    public String toString() {
        return "Ticket{" + "id=" + id + ", name=" + name + ", issue=" + issue + ", priority=" + priority + '}';
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
               
}
