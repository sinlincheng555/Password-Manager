package View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import Controller.PasswordController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import Until.Encryption;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import Model.SecureNote;

public class DashboardView extends Application {

    private static PasswordController controller;
    private StackPane contentArea;

    // Passwords panel — persists across re-opens of the panel
    private final ObservableList<PasswordEntry> masterData = FXCollections.observableArrayList();
    private final Encryption encryption = new Encryption();

    // Secure Notes panel — persists while dashboard is open
    private final ObservableList<SecureNote> noteItems = FXCollections.observableArrayList();

    // No-arg constructor required by JavaFX Application
    public DashboardView() {}

    // Constructor that accepts a controller directly
    public DashboardView(PasswordController c) {
        controller = c;
    }

    public static void setController(PasswordController c) {
        controller = c;
    }

    @Override
    public void start(Stage stage) {

        // ── Sidebar ───────────────────────────────────────────────────
        VBox sidebar = new VBox(12);
        sidebar.setPrefWidth(220);
        sidebar.setPadding(new Insets(20, 14, 20, 14));
        sidebar.setStyle("-fx-background-color: #3a3a3a;");

        Button btnAccount      = sidebarButton("Account");
        Button btnPasswords    = sidebarButton("Passwords");
        Button btnSecureNotes  = sidebarButton("Secure Notes");
        Button btnDeviceSync   = sidebarButton("Device Syncing");
        Button btnImportExport = sidebarButton("Import/Export");
        Button btnSettings     = sidebarButton("Settings");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button btnLogout = new Button("Logout");
        btnLogout.setPrefWidth(Double.MAX_VALUE);
        btnLogout.setPrefHeight(44);
        btnLogout.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btnLogout.setStyle(
                "-fx-background-color: #c0392b;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-style: italic;" +
                        "-fx-background-radius: 8;"
        );

        sidebar.getChildren().addAll(
                btnAccount, btnPasswords, btnSecureNotes, btnDeviceSync,
                btnImportExport, btnSettings,
                spacer, btnLogout
        );

        // ── Content area ──────────────────────────────────────────────
        contentArea = new StackPane();
        contentArea.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #e07b39, #c0392b);"
        );
        showAccountPanel(); // default view

        // Button actions
        btnAccount.setOnAction(e -> showAccountPanel());
        btnPasswords.setOnAction(e -> showPasswordsPanel());
        btnSecureNotes.setOnAction(e -> showSecureNotesPanel());
        btnDeviceSync.setOnAction(e -> showDeviceSyncPanel());
        btnImportExport.setOnAction(e -> showImportExportPanel());
        btnSettings.setOnAction(e -> showSettingsPanel());
        btnLogout.setOnAction(e -> stage.close());

        // ── Root ──────────────────────────────────────────────────────
        HBox root = new HBox();
        HBox.setHgrow(contentArea, Priority.ALWAYS);
        root.getChildren().addAll(sidebar, contentArea);

        Scene scene = new Scene(root, 800, 480);
        stage.setTitle("Password Manager");
        stage.setScene(scene);
        stage.setMinWidth(650);
        stage.setMinHeight(400);
        stage.show();
    }

    // ── Sidebar button style ──────────────────────────────────────────
    private Button sidebarButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(Double.MAX_VALUE);
        btn.setPrefHeight(44);
        btn.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        btn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #333;" +
                        "-fx-font-style: italic;" +
                        "-fx-background-radius: 8;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #ddd;" +
                        "-fx-text-fill: #111;" +
                        "-fx-font-style: italic;" +
                        "-fx-background-radius: 8;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #333;" +
                        "-fx-font-style: italic;" +
                        "-fx-background-radius: 8;"
        ));
        return btn;
    }

    // ── Label helpers ─────────────────────────────────────────────────
    private Label panelTitle(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        l.setStyle("-fx-text-fill: white;");
        return l;
    }

    private Label fieldLabel(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        l.setStyle("-fx-text-fill: white;");
        return l;
    }

    // ── Field helpers ─────────────────────────────────────────────────
    private TextField darkField(String prompt) {
        TextField f = new TextField();
        f.setPromptText(prompt);
        f.setStyle(
                "-fx-background-color: #2b2b2b;" +
                        "-fx-text-fill: white;" +
                        "-fx-prompt-text-fill: #aaa;" +
                        "-fx-font-style: italic;" +
                        "-fx-background-radius: 4;" +
                        "-fx-padding: 8 12 8 12;"
        );
        return f;
    }

    private PasswordField darkPassField(String prompt) {
        PasswordField f = new PasswordField();
        f.setPromptText(prompt);
        f.setStyle(
                "-fx-background-color: #2b2b2b;" +
                        "-fx-text-fill: white;" +
                        "-fx-prompt-text-fill: #aaa;" +
                        "-fx-font-style: italic;" +
                        "-fx-background-radius: 4;" +
                        "-fx-padding: 8 12 8 12;"
        );
        return f;
    }

    // ── Button helpers ────────────────────────────────────────────────
    private Button redButton(String text) {
        Button b = new Button(text);
        b.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        b.setStyle(
                "-fx-background-color: #c0392b;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-style: italic;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 8 16 8 16;"
        );
        return b;
    }

    private Button darkButton(String text) {
        Button b = new Button(text);
        b.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        b.setStyle(
                "-fx-background-color: #3a3a3a;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 8 16 8 16;"
        );
        return b;
    }

    // ── ACCOUNT panel ─────────────────────────────────────────────────
    private void showAccountPanel() {
        VBox panel = new VBox(14);
        panel.setPadding(new Insets(30, 40, 30, 40));
        panel.setAlignment(Pos.TOP_LEFT);

        TextField emailField = darkField("Example@gmail.com");
        emailField.setPrefWidth(400);

        PasswordField passField = darkPassField("Example12345!");
        passField.setPrefWidth(400);

        TextField dobField = darkField("Day/Month/Year");
        dobField.setPrefWidth(180);

        TextField phoneField = darkField("07000 00000");
        phoneField.setPrefWidth(220);

        HBox dobPhoneRow = new HBox(20,
                new VBox(6, fieldLabel("Date of Birth:"), dobField),
                new VBox(6, fieldLabel("Phone number:"), phoneField)
        );

        Button linkBtn    = redButton("LINK?");
        Button genPassBtn = redButton("Generate secure password");
        HBox twoFARow = new HBox(12, linkBtn, genPassBtn);

        Button confirmBtn = darkButton("Confirm Changes");
        HBox confirmRow = new HBox();
        confirmRow.setAlignment(Pos.BOTTOM_RIGHT);
        Region s = new Region();
        HBox.setHgrow(s, Priority.ALWAYS);
        confirmRow.getChildren().addAll(s, confirmBtn);

        panel.getChildren().addAll(
                panelTitle("My Account"),
                fieldLabel("Email:"),
                emailField,
                fieldLabel("Password:"),
                passField,
                dobPhoneRow,
                fieldLabel("2FA:"),
                twoFARow,
                new Region(),
                confirmRow
        );
        VBox.setVgrow(panel.getChildren().get(panel.getChildren().size() - 2), Priority.ALWAYS);

        contentArea.getChildren().setAll(panel);
    }

    // ── PASSWORDS panel (with search, filter/sort, show/hide, copy, edit, delete, encryption) ──
    private void showPasswordsPanel() {
        VBox panel = new VBox(16);
        panel.setPadding(new Insets(30, 40, 30, 40));

        // Search field
        TextField searchField = darkField("Search passwords...");

        // Add-entry fields
        TextField siteField   = darkField("Site / App name");
        TextField userField   = darkField("Username");
        PasswordField passField = darkPassField("Password");
        TextField passVisible = darkField("Password");
        passVisible.setVisible(false);

        passField.managedProperty().bind(passField.visibleProperty());
        passVisible.managedProperty().bind(passVisible.visibleProperty());

        Button toggleBtn = darkButton("Show");
        toggleBtn.setOnAction(e -> {
            boolean show = !passVisible.isVisible();
            if (show) {
                passVisible.setText(passField.getText());
            } else {
                passField.setText(passVisible.getText());
            }
            passVisible.setVisible(show);
            passField.setVisible(!show);
            toggleBtn.setText(show ? "Hide" : "Show");
        });

        Button addBtn = redButton("Add");
        HBox addRow = new HBox(12, siteField, userField, passField, passVisible, toggleBtn, addBtn);
        addRow.setAlignment(Pos.CENTER_LEFT);

        // Filter & sort
        FilteredList<PasswordEntry> filteredData = new FilteredList<>(masterData, p -> true);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(entry -> {
                if (newVal == null || newVal.isEmpty()) return true;
                String lowerFilter = newVal.toLowerCase();
                return entry.site.get().toLowerCase().contains(lowerFilter) ||
                        entry.username.get().toLowerCase().contains(lowerFilter);
            });
        });

        SortedList<PasswordEntry> sortedData = new SortedList<>(filteredData);
        TableView<PasswordEntry> table = new TableView<>(sortedData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setStyle("-fx-background-color: #2b2b2b;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn<PasswordEntry, String> siteCol = new TableColumn<>("Site");
        siteCol.setCellValueFactory(d -> d.getValue().site);
        TableColumn<PasswordEntry, String> userCol = new TableColumn<>("Username");
        userCol.setCellValueFactory(d -> d.getValue().username);
        TableColumn<PasswordEntry, String> passCol = new TableColumn<>("Password");
        passCol.setCellValueFactory(d -> d.getValue().password);

        // Action buttons: Copy / Edit / Delete
        TableColumn<PasswordEntry, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button delBtn  = new Button("Delete");
            private final Button copyBtn = new Button("Copy");
            private final HBox container = new HBox(8, copyBtn, editBtn, delBtn);
            {
                copyBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 10px;");
                editBtn.setStyle("-fx-background-color: #3a3a3a; -fx-text-fill: white; -fx-font-size: 10px;");
                delBtn.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-size: 10px;");
                container.setAlignment(Pos.CENTER);

                copyBtn.setOnAction(e -> {
                    PasswordEntry item = getTableView().getItems().get(getIndex());
                    String plainPassword = encryption.decrypt(item.password.get());
                    final Clipboard clipboard = Clipboard.getSystemClipboard();
                    final ClipboardContent content = new ClipboardContent();
                    content.putString(plainPassword);
                    clipboard.setContent(content);
                    copyBtn.setText("Copied!");
                    new java.util.Timer().schedule(new java.util.TimerTask() {
                        @Override public void run() {
                            javafx.application.Platform.runLater(() -> copyBtn.setText("Copy"));
                        }
                    }, 2000);
                });

                delBtn.setOnAction(e -> masterData.remove(getTableView().getItems().get(getIndex())));

                editBtn.setOnAction(e -> {
                    PasswordEntry item = getTableView().getItems().get(getIndex());
                    siteField.setText(item.site.get());
                    userField.setText(item.username.get());
                    passField.setText(encryption.decrypt(item.password.get()));
                    masterData.remove(item);
                });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });

        table.getColumns().addAll(siteCol, userCol, passCol, actionCol);

        // Add action — validates fields, encrypts password, stores entry
        addBtn.setOnAction(e -> {
            String pwd = passVisible.isVisible() ? passVisible.getText() : passField.getText();

            if (siteField.getText().isEmpty()) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Please enter site name");
                a.show();
            } else if (userField.getText().isEmpty()) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Please enter username");
                a.show();
            } else if (pwd.isEmpty()) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Please enter password");
                a.show();
            } else {
                String encrypted = encryption.encrypt(pwd);
                masterData.add(new PasswordEntry(siteField.getText(), userField.getText(), encrypted));
                siteField.clear();
                userField.clear();
                passField.clear();
                passVisible.clear();
            }
        });

        panel.getChildren().addAll(panelTitle("Passwords"), fieldLabel("Search:"), searchField, addRow, table);
        contentArea.getChildren().setAll(panel);
    }

    // ── SECURE NOTES panel ────────────────────────────────────────────
    private void showSecureNotesPanel() {

        VBox wrapper = new VBox(14);
        wrapper.setPadding(new Insets(30, 40, 30, 40));
        wrapper.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        wrapper.getChildren().add(panelTitle("Secure Notes"));

        // Body: LEFT editor | RIGHT list
        HBox body = new HBox(20);
        VBox.setVgrow(body, Priority.ALWAYS);

        // LEFT — editor
        VBox leftPane = new VBox(10);
        HBox.setHgrow(leftPane, Priority.ALWAYS);

        TextField titleField = darkField("Note title");
        titleField.setMaxWidth(Double.MAX_VALUE);

        TextArea noteContent = new TextArea();
        noteContent.setPromptText("Write your note here...");
        noteContent.setWrapText(true);
        noteContent.setStyle(
                "-fx-control-inner-background: #2b2b2b;" +
                        "-fx-background-color: #2b2b2b;" +
                        "-fx-text-fill: white;" +
                        "-fx-prompt-text-fill: #aaa;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-style: italic;" +
                        "-fx-background-radius: 4;" +
                        "-fx-padding: 8 12 8 12;"
        );
        VBox.setVgrow(noteContent, Priority.ALWAYS);

        Button saveBtn   = redButton("Save Note");
        Button deleteBtn = redButton("Delete Note");
        HBox btnRow = new HBox(10, saveBtn, deleteBtn);

        leftPane.getChildren().addAll(
                fieldLabel("Title:"), titleField,
                fieldLabel("Content:"), noteContent,
                btnRow
        );

        // RIGHT — list
        VBox rightPane = new VBox(8);
        rightPane.setPrefWidth(200);
        rightPane.setMinWidth(160);

        Label listHeader = new Label("Secure Notes");
        listHeader.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        listHeader.setStyle("-fx-text-fill: white;");

        ListView<SecureNote> noteList = new ListView<>(noteItems);
        noteList.setStyle(
                "-fx-background-color: #2b2b2b;" +
                        "-fx-background-radius: 4;"
        );
        noteList.setCellFactory(lv -> new ListCell<SecureNote>() {
            @Override
            protected void updateItem(SecureNote item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    setText(item.getTitle());
                    setStyle(
                            "-fx-background-color: transparent;" +
                                    "-fx-text-fill: white;" +
                                    "-fx-font-size: 13px;"
                    );
                }
            }
        });
        VBox.setVgrow(noteList, Priority.ALWAYS);

        rightPane.getChildren().addAll(listHeader, noteList);
        body.getChildren().addAll(leftPane, rightPane);
        wrapper.getChildren().add(body);
        contentArea.getChildren().setAll(wrapper);

        // Track which note is being edited
        final SecureNote[] editing = {null};

        noteList.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                editing[0] = selected;
                titleField.setText(selected.getTitle());
                noteContent.setText(selected.getContent());
            }
        });

        saveBtn.setOnAction(e -> {
            String title   = titleField.getText().trim();
            String content = noteContent.getText().trim();
            if (title.isEmpty() || content.isEmpty()) return;

            if (editing[0] != null) {
                editing[0].setContent(content);
                noteList.refresh();
            } else {
                noteItems.add(new SecureNote(title, content));
            }

            titleField.clear();
            noteContent.clear();
            editing[0] = null;
            noteList.getSelectionModel().clearSelection();
        });

        deleteBtn.setOnAction(e -> {
            SecureNote selected = noteList.getSelectionModel().getSelectedItem();
            if (selected == null) return;
            noteItems.remove(selected);
            titleField.clear();
            noteContent.clear();
            editing[0] = null;
        });
    }

    // ── DEVICE SYNCING panel ──────────────────────────────────────────
    private void showDeviceSyncPanel() {
        VBox panel = new VBox(16);
        panel.setPadding(new Insets(30, 40, 30, 40));

        Label info = new Label("Sync your passwords across devices.");
        info.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");

        TextField deviceField = darkField("Device name or ID");
        deviceField.setPrefWidth(300);

        Button syncBtn = redButton("Sync Now");

        panel.getChildren().addAll(panelTitle("Device Syncing"), info, fieldLabel("Add Device:"), deviceField, syncBtn);
        contentArea.getChildren().setAll(panel);
    }

    // ── IMPORT / EXPORT panel ─────────────────────────────────────────
    private void showImportExportPanel() {
        VBox panel = new VBox(16);
        panel.setPadding(new Insets(30, 40, 30, 40));

        Label info = new Label("Import or export your password vault.");
        info.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");

        Button importBtn = redButton("Import from CSV");
        Button exportBtn = redButton("Export to CSV");
        HBox btnRow = new HBox(14, importBtn, exportBtn);

        panel.getChildren().addAll(panelTitle("Import / Export"), info, btnRow);
        contentArea.getChildren().setAll(panel);
    }

    // ── SETTINGS panel ────────────────────────────────────────────────
    private void showSettingsPanel() {
        VBox panel = new VBox(16);
        panel.setPadding(new Insets(30, 40, 30, 40));

        CheckBox darkMode = new CheckBox("Dark Mode");
        CheckBox autoLock = new CheckBox("Auto-lock after 5 minutes");
        CheckBox showPass = new CheckBox("Show passwords by default");
        for (CheckBox cb : new CheckBox[]{darkMode, autoLock, showPass}) {
            cb.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
        }

        Button saveBtn = redButton("Save Settings");

        panel.getChildren().addAll(panelTitle("Settings"), darkMode, autoLock, showPass, saveBtn);
        contentArea.getChildren().setAll(panel);
    }

    // ── Password entry model ──────────────────────────────────────────
    public static class PasswordEntry {
        public javafx.beans.property.SimpleStringProperty site;
        public javafx.beans.property.SimpleStringProperty username;
        public javafx.beans.property.SimpleStringProperty password;

        public PasswordEntry(String site, String username, String password) {
            this.site     = new javafx.beans.property.SimpleStringProperty(site);
            this.username = new javafx.beans.property.SimpleStringProperty(username);
            this.password = new javafx.beans.property.SimpleStringProperty(password);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}