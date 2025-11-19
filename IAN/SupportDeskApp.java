import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class SupportDeskApp extends Application {

    private SupportDesk supportDesk = new SupportDesk();

    // Form controls
    private TextField idField;
    private TextField nameField;
    private TextArea issueArea;
    private ComboBox<String> priorityBox;

    // Display area
    private TextArea displayArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("MDC Tech Support Ticket System");

        // ========= Form Section =========
        Label idLabel = new Label("Ticket ID:");
        idField = new TextField();
        idField.setPromptText("Enter ticket ID (integer)");

        Label nameLabel = new Label("Requester Name:");
        nameField = new TextField();
        nameField.setPromptText("Enter requester name");

        Label issueLabel = new Label("Issue Description:");
        issueArea = new TextArea();
        issueArea.setPromptText("Describe the issue");
        issueArea.setPrefRowCount(3);
        issueArea.setWrapText(true);

        Label priorityLabel = new Label("Priority:");
        priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("Low", "Medium", "High");

        Button addTicketButton = new Button("Add Ticket");
        addTicketButton.setOnAction(e -> handleAddTicket());

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10));

        formGrid.add(idLabel, 0, 0);
        formGrid.add(idField, 1, 0);
        formGrid.add(nameLabel, 0, 1);
        formGrid.add(nameField, 1, 1);
        formGrid.add(issueLabel, 0, 2);
        formGrid.add(issueArea, 1, 2);
        formGrid.add(priorityLabel, 0, 3);
        formGrid.add(priorityBox, 1, 3);
        formGrid.add(addTicketButton, 1, 4);

        // ========= Display Area =========
        displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setWrapText(true);
        displayArea.setPrefRowCount(15);

        TitledPane displayPane = new TitledPane("Ticket Display", displayArea);
        displayPane.setCollapsible(false);

        // ========= Control Buttons =========
        Button processNextButton = new Button("Process Next Ticket");
        processNextButton.setOnAction(e -> handleProcessNextTicket());

        Button viewActiveButton = new Button("View All Active Tickets");
        viewActiveButton.setOnAction(e -> handleViewActiveTickets());

        Button viewResolvedButton = new Button("View Recently Resolved");
        viewResolvedButton.setOnAction(e -> handleViewResolvedTickets());

        Button reopenLastButton = new Button("Reopen Last Resolved");
        reopenLastButton.setOnAction(e -> handleReopenLastResolved());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());

        VBox controlButtons = new VBox(10,
                processNextButton,
                viewActiveButton,
                viewResolvedButton,
                reopenLastButton,
                exitButton
        );
        controlButtons.setPadding(new Insets(10));

        // ========= Layout =========
        BorderPane root = new BorderPane();
        root.setTop(formGrid);
        root.setCenter(displayPane);
        root.setRight(controlButtons);

        Scene scene = new Scene(root, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ======== Handlers ========

    private void handleAddTicket() {
        try {
            String idText = idField.getText().trim();
            String name = nameField.getText().trim();
            String issue = issueArea.getText().trim();
            String priority = priorityBox.getValue();

            // Validation
            if (idText.isEmpty() || name.isEmpty() || issue.isEmpty() || priority == null) {
                showAlert(Alert.AlertType.ERROR,
                        "Invalid Input",
                        "All fields (ID, Name, Issue, Priority) must be filled.");
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR,
                        "Invalid Ticket ID",
                        "Ticket ID must be an integer.");
                return;
            }

            Ticket t = new Ticket(id, name, issue, priority);
            supportDesk.addTicket(t);

            showAlert(Alert.AlertType.INFORMATION,
                    "Ticket Added",
                    "Ticket " + id + " has been added successfully.");

            clearForm();
            handleViewActiveTickets(); // Refresh display
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR,
                    "Error",
                    "An unexpected error occurred while adding a ticket.");
        }
    }

    private void handleProcessNextTicket() {
        try {
            Ticket processed = supportDesk.processNextTicket();
            if (processed == null) {
                showAlert(Alert.AlertType.INFORMATION,
                        "No Tickets",
                        "There are no active tickets to process.");
            } else {
                showAlert(Alert.AlertType.INFORMATION,
                        "Ticket Processed",
                        "Processed ticket ID: " + processed.getId());
                handleViewActiveTickets();
            }
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR,
                    "Error",
                    "An unexpected error occurred while processing a ticket.");
        }
    }

    private void handleViewActiveTickets() {
        try {
            List<Ticket> active = supportDesk.viewAllActiveTickets();
            if (active.isEmpty()) {
                displayArea.setText("No active tickets.\n");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Active Tickets:\n\n");
                for (Ticket t : active) {
                    sb.append(t.toString());
                }
                displayArea.setText(sb.toString());
            }
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR,
                    "Error",
                    "An unexpected error occurred while viewing active tickets.");
        }
    }

    private void handleViewResolvedTickets() {
        try {
            List<Ticket> resolved = supportDesk.viewRecentResolved();
            if (resolved.isEmpty()) {
                displayArea.setText("No resolved tickets.\n");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Recently Resolved Tickets:\n\n");
                for (Ticket t : resolved) {
                    sb.append(t.toString());
                }
                displayArea.setText(sb.toString());
            }
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR,
                    "Error",
                    "An unexpected error occurred while viewing resolved tickets.");
        }
    }

    private void handleReopenLastResolved() {
        try {
            Ticket reopened = supportDesk.reopenLastResolved();
            if (reopened == null) {
                showAlert(Alert.AlertType.INFORMATION,
                        "No Resolved Tickets",
                        "There are no resolved tickets to reopen.");
            } else {
                showAlert(Alert.AlertType.INFORMATION,
                        "Ticket Reopened",
                        "Reopened ticket ID: " + reopened.getId());
                handleViewActiveTickets();
            }
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR,
                    "Error",
                    "An unexpected error occurred while reopening a ticket.");
        }
    }

    // ======== Helpers ========

    private void clearForm() {
        idField.clear();
        nameField.clear();
        issueArea.clear();
        priorityBox.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
