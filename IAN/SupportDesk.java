import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.ArrayList;

public class SupportDesk {

    private Queue<Ticket> activeTickets;
    private Stack<Ticket> resolvedTickets;

    public SupportDesk() {
        activeTickets = new LinkedList<>();
        resolvedTickets = new Stack<>();
    }

    // Add a new ticket to the active queue
    public void addTicket(Ticket t) {
        if (t != null) {
            activeTickets.offer(t);
        }
    }

    // Process the next ticket (remove from queue, push to resolved stack)
    // Returns the processed ticket or null if none
    public Ticket processNextTicket() {
        Ticket next = activeTickets.poll();
        if (next != null) {
            resolvedTickets.push(next);
        }
        return next;
    }

    // Return all active tickets as a List (for GUI display)
    public List<Ticket> viewAllActiveTickets() {
        return new ArrayList<>(activeTickets);
    }

    // Return recently resolved tickets (all in stack for simplicity)
    public List<Ticket> viewRecentResolved() {
        return new ArrayList<>(resolvedTickets);
    }

    // Pop last resolved ticket and put it back into active queue
    public Ticket reopenLastResolved() {
        if (resolvedTickets.isEmpty()) {
            return null;
        }
        Ticket t = resolvedTickets.pop();
        activeTickets.offer(t);
        return t;
    }

    public boolean hasActiveTickets() {
        return !activeTickets.isEmpty();
    }

    public boolean hasResolvedTickets() {
        return !resolvedTickets.isEmpty();
    }
}
