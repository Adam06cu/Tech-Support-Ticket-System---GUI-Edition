import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SupportDeskApp extends Application {

    private SupportDesk desk = new SupportDesk();

    @Override
    public void start(Stage stage) {

        // Title
        Label title = new Label("MDC Tech Support Ticket System");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Form Fields
        TextField idField = new TextField();
        idField.setEditable(false);
        idField.setPromptText("Auto");

        TextField nameField = new TextField();
        nameField.setPromptText("Requester Name");

        TextField issueField = new TextField();
        issueField.setPromptText("Issue Description");

        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("Low", "Medium", "High");
        priorityBox.setPromptText("Priority");

        Button addButton = new Button("Add Ticket");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(10));

        form.add(new Label("Ticket ID:"), 0, 0);
        form.add(idField, 1, 0);

        form.add(new Label("Name:"), 0, 1);
        form.add(nameField, 1, 1);

        form.add(new Label("Issue:"), 0, 2);
        form.add(issueField, 1, 2);

        form.add(new Label("Priority:"), 0, 3);
        form.add(priorityBox, 1, 3);

        form.add(addButton, 1, 4);

        // Display Area
        TextArea display = new TextArea();
        display.setEditable(false);
        display.setPrefHeight(300);

        // Buttons
        Button processButton = new Button("Process Next Ticket");
        Button viewActiveButton = new Button("View All Active Tickets");
        Button viewResolvedButton = new Button("View Recently Resolved");
        Button reopenButton = new Button("Reopen Last Resolved");
        Button exitButton = new Button("Exit");

        HBox controls = new HBox(10, processButton, viewActiveButton, viewResolvedButton, reopenButton, exitButton);
        controls.setPadding(new Insets(10));

        // Auto-update next ID
        idField.setText(String.valueOf(desk.getNextId()));

        // EVENT HANDLERS
        addButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String issue = issueField.getText();
                String priority = priorityBox.getValue();

                if (name.isEmpty() || issue.isEmpty() || priority == null) {
                    showAlert(Alert.AlertType.ERROR, "Please fill out all fields.");
                    return;
                }

                Ticket t = new Ticket(id, name, issue, priority);
                desk.addTicket(t);

                showAlert(Alert.AlertType.INFORMATION, "Ticket added successfully!");

                idField.setText(String.valueOf(desk.getNextId()));
                nameField.clear();
                issueField.clear();
                priorityBox.setValue(null);

            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid input.");
            }
        });

        processButton.setOnAction(e -> {
            Ticket t = desk.processNextTicket();
            if (t == null) showAlert(Alert.AlertType.WARNING, "No tickets to process.");
            else showAlert(Alert.AlertType.INFORMATION, "Processed:\n" + t);
        });

        viewActiveButton.setOnAction(e -> {
            display.setText(desk.viewAllActiveTickets());
        });

        viewResolvedButton.setOnAction(e -> {
            display.setText(desk.viewRecentResolved());
        });

        reopenButton.setOnAction(e -> {
            Ticket t = desk.reopenLastResolved();
            if (t == null) showAlert(Alert.AlertType.WARNING, "No resolved tickets to reopen.");
            else showAlert(Alert.AlertType.INFORMATION, "Reopened:\n" + t);
        });

        exitButton.setOnAction(e -> stage.close());

        // Layout
        VBox root = new VBox(15, title, form, display, controls);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 650, 600);
        stage.setScene(scene);
        stage.setTitle("MDC Tech Support Ticket System");
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
