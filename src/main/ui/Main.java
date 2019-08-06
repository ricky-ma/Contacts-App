package ui;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Contact;
import model.ContactMap;
import model.exceptions.ContactAlreadyExistsException;
import model.interfaces.ContactMapObserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main extends Application implements ContactMapObserver {

    private ContactMap contactMap;
    private Stage mainStage;

    private final ListView<String> contactList = new ListView<>();
    private final ListView<String> favoritesList = new ListView<>();

    // EFFECTS: runs the program
    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {
        this.mainStage = primaryStage;
        createAndPopulateModel();
        primaryStage.setTitle("Contax");
        displayAllContacts();
        primaryStage.show();
    }

    @Override
    public void stop() {
        try {
            contactMap.save("contactfile.txt");
        } catch (IOException e) {
            System.out.println("File not saved.");
        }
    }

    public void updateModel() {
        updateListView(contactMap.getContactMap(), contactList);
        updateListView(contactMap.getContactMap(), favoritesList);
    }

    // MODIFIES: contacts
    // EFFECTS: gets parameters of each contact from contactfile.txt and creates a new RegularContact object
    //          adds newly created contact to contactMap
    private void load() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("contactfile.txt"));
        for (String line : lines) {
            try {
                contactMap.addNewContact(line);
            } catch (ContactAlreadyExistsException e) {
                // skips that contact
            }
        }
    }

    private void loadCSV() throws IOException {
        //noinspection MismatchedQueryAndUpdateOfCollection
        List<String[]> content = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("contacts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line.split(","));
//                try {
//                    addNewCSVContact(line);
//                } catch (ContactAlreadyExistsException e) {
//                    // skips that contact
//                }
            }
        } catch (FileNotFoundException e) {
            //Some error logging
        }
    }

    private void createAndPopulateModel() {
        contactMap = new ContactMap();
        try {
            load();
            loadCSV();
        } catch (IOException e) {
            //
        }
        contactMap.addObserver(this);
    }

    private void setSceneTitle(GridPane grid, String s, FontWeight bold, int i) {
        Text sceneTitle = new Text(s);
        sceneTitle.setFont(Font.font("Helvetica", bold, 30));
        grid.add(sceneTitle, 0, 0, i, 1);
    }

    private Scene setNewScene(GridPane grid, int width, int height) {
        Scene scene = new Scene(grid, width, height);
        scene.getStylesheets().add("file:///C:/Users/mrric/IdeaProjects/project_rickyma/stylesheet");
        return scene;
    }

    private static GridPane setNewGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        return grid;
    }

    private Button addButton() {
        Button btn1 = new Button("ADD");
        btn1.setOnAction(event -> newContact());
        return btn1;
    }

    private Button favoritesButton() {
        Button btn4 = new Button("FAVORITES");
        btn4.setOnAction(event -> displayFavorites());
        return btn4;
    }

    private Button allButton() {
        Button btn5 = new Button("ALL");
        btn5.setOnAction(event -> displayAllContacts());
        return btn5;
    }

    private Button backButton() {
        Button backBtn = new Button("BACK TO CONTACTS");
        backBtn.setOnAction(event -> displayAllContacts());
        return backBtn;
    }

    private Button editButton(Contact c) {
        Button editBtn = new Button("EDIT");
        editBtn.setOnAction(event -> editContact(c));
        return editBtn;
    }

    private Button deleteButton(Contact c) {
        Button deleteBtn = new Button("DELETE");
        deleteBtn.setOnAction(event -> deleteContact(c));
        return deleteBtn;
    }

    private Button submitButton(TextField nameTextField, TextField phoneTextField, TextField addressTextField,
                                TextField emailTextField, CheckBox favoriteCheckbox, boolean newContact) {
        Button btn = new Button("SUBMIT");
        btn.setOnAction(event -> {
            String s = nameTextField.getText() + "---"
                    + phoneTextField.getText() + "---"
                    + addressTextField.getText() + "---"
                    + emailTextField.getText() + "---"
                    + favoriteCheckbox.isSelected();
            submitNewOrEdit(nameTextField, newContact, s);
        });
        return btn;
    }

    private void submitNewOrEdit(TextField nameTextField, boolean newContact, String s) {
        if (newContact) {
            try {
                contactMap.addNewContact(s);
            } catch (ContactAlreadyExistsException e) {
                contactAlreadyExistsWindow(contactMap.get(nameTextField.getText()));
            } finally {
                viewContact(contactMap.get(nameTextField.getText()));
            }
        } else {
            contactMap.editContact(s);
            viewContact(contactMap.get(nameTextField.getText()));
        }
    }


    // --------------------------------- ADD AND EDIT CONTACT ---------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------
    private void newContact() {
        GridPane grid = setNewGrid();
        setSceneTitle(grid, "INPUT CONTACT INFO", FontWeight.EXTRA_BOLD, 2);

        TextField nameTextField = getTextField(grid, "Name:", "",1, "[A-Za-z ]*");
        TextField phoneTextField = getTextField(grid, "Phone:", "",2, "([1-9][0-9]*)?");
        TextField addressTextField = getTextField(grid, "Address:", "",3, ".*");
        TextField emailTextField = getTextField(grid, "Email:", "",4, ".*");
        CheckBox favoriteCheckbox = getCheckBox(grid, false);

        Button btn = submitButton(nameTextField, phoneTextField, addressTextField,
                    emailTextField, favoriteCheckbox, true);

        grid.add(btn, 0, 6, 1, 1);
        grid.add(backButton(), 1, 6, 2, 1);
        Scene scene = setNewScene(grid, 400, 600);
        mainStage.setScene(scene);
    }

    private void editContact(Contact c) {
        GridPane grid = setNewGrid();
        setSceneTitle(grid, "EDIT CONTACT INFO", FontWeight.EXTRA_BOLD, 2);

        TextField nameTextField = getTextField(grid, "Name:", c.getName(),1, "[A-Za-z ]*");
        TextField phoneTextField = getTextField(grid, "Phone:", c.getPhone(),2, "([1-9][0-9]*)?");
        TextField addressTextField = getTextField(grid, "Address:", c.getAddress(),3, ".*");
        TextField emailTextField = getTextField(grid, "Email:", c.getEmail(),4, ".*");
        CheckBox favoriteCheckbox = getCheckBox(grid, c.getFavorite());

        Button btn = submitButton(nameTextField, phoneTextField, addressTextField,
                emailTextField, favoriteCheckbox, false);

        grid.add(btn, 0, 6, 1, 1);
        grid.add(backButton(), 1, 6, 2, 1);
        Scene scene = setNewScene(grid, 400, 600);
        mainStage.setScene(scene);
    }

    private void contactAlreadyExistsWindow(Contact c) {
        GridPane grid = setNewGrid();

        Stage stage = new Stage();
        stage.setTitle("Contact Already Exists");
        stage.setScene(setNewScene(grid, 300, 300));

        setSceneTitle(grid, "CONTACT ALREADY EXISTS", FontWeight.BOLD, 2);

        Button okBtn = new Button("OK");
        grid.add(okBtn, 0, 1, 1, 1);
        okBtn.setOnAction(event -> {
            stage.close();
            viewContact(c);
        });
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    private TextField getTextField(GridPane grid, String s0, String s1, int i, String s2) {
        Label name = new Label(s0);
        grid.add(name, 0, i,3,1);
        TextField nameTextField = new TextField(s1);
        grid.add(nameTextField, 1, i);
        nameTextField.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches(s2)) ? change : null));
        return nameTextField;
    }

    private CheckBox getCheckBox(GridPane grid, boolean b) {
        Label favorite = new Label("Favorite?");
        grid.add(favorite, 0, 5);
        CheckBox favoriteCheckbox = new CheckBox();
        favoriteCheckbox.setSelected(b);
        grid.add(favoriteCheckbox, 1, 5);
        return favoriteCheckbox;
    }


    // --------------------------------------- DELETE CONTACT ---------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------
    private void deleteContact(Contact c) {
        GridPane deleteGrid = setNewGrid();
        setSceneTitle(deleteGrid, "DELETE CONTACT?", FontWeight.BOLD, 2);

        Stage stage = new Stage();
        stage.setTitle("Delete Contact");
        stage.setScene(setNewScene(deleteGrid, 300, 300));


        Button yes = new Button("YES");
        yes.setOnAction(event -> {
            deleteContactConfirmation(c);
            stage.close();
        });
        Button no = new Button("NO");
        no.setOnAction(event -> stage.close());
        deleteGrid.add(yes, 0, 1);
        deleteGrid.add(no, 1, 1);

        stage.show();
    }

    private void deleteContactConfirmation(Contact c) {
        GridPane grid = setNewGrid();
        setSceneTitle(grid, "CONTACT DELETED", FontWeight.BOLD, 1);

        Stage stage = new Stage();
        stage.setTitle("Contact Deleted");
        stage.setScene(setNewScene(grid, 300, 300));

        contactMap.deleteContact(c.getName());

        Button okBtn = new Button("OK");
        grid.add(okBtn, 0, 1, 1, 1);
        okBtn.setOnAction(event -> {
            stage.close();
            displayAllContacts();
        });
        stage.show();
    }


    // ------------------------------------- DISPLAY CONTACTS ---------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------
    private void displayAllContacts() {
        displayContactList("CONTACTS", contactList, contactMap.getContactMap());
    }

    private void displayFavorites() {
        displayContactList("FAVORITES", favoritesList, contactMap.getFavoritesMap());
    }

    private void displayContactList(String title, ListView<String> listView, Map<String, Contact> contactMap) {
        GridPane grid = setNewGrid();
        setSceneTitle(grid, title, FontWeight.EXTRA_BOLD, 2);
        Scene scene = setNewScene(grid, 400, 600);
        listView.getSelectionModel().clearSelection();
        listView.getItems().clear();

        updateListView(contactMap, listView);

        grid.add(addButton(), 0, 1, 1, 1);
        grid.add(favoritesButton(), 1, 1, 1, 1);
        grid.add(allButton(), 2, 1, 1, 1);
        grid.add(searchBox(listView, contactMap), 0, 2, 3, 1);
        grid.add(listView, 0, 3, 3, 1);

        mainStage.setScene(scene);

        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> viewContact(contactMap.get(newValue)));
    }

    private void updateListView(Map<String, Contact> contactMap, ListView<String> contactList) {
        ObservableList<String> entries = FXCollections.observableArrayList();
        for (Contact c : contactMap.values()) {
            entries.add(c.getName());
        }
        contactList.setItems(entries);
    }

    @SuppressWarnings("unchecked")
    private void handleSearchByKey(String oldVal, String newVal, ListView list, Map<String, Contact> contactMap) {
        ObservableList<String> entries = FXCollections.observableArrayList();
        for (Contact c : contactMap.values()) {
            entries.add(c.getName());
        }
        list.setItems(entries);
        if (oldVal != null && (newVal.length() < oldVal.length())) {
            list.setItems(entries);
        }
        newVal = newVal.toUpperCase();
        ObservableList<String> subentries = FXCollections.observableArrayList();
        for (Object entry: list.getItems()) {
            String entryText = (String)entry;
            if (entryText.toUpperCase().contains(newVal)) {
                subentries.add(entryText);
            }
        }
        list.setItems(subentries);
    }

    private TextField searchBox(ListView list, Map<String, Contact> contactMap) {
        TextField txt = new TextField();
        txt.setPromptText("Search");
        txt.textProperty().addListener(
                (observable, oldVal, newVal) -> handleSearchByKey(oldVal, newVal, list, contactMap));
        return txt;
    }

    // -------------------------------------- DISPLAY CONTACT ---------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------
    private void viewContact(Contact c) {
        GridPane grid = setNewGrid();
        Scene scene = setNewScene(grid, 400, 600);

        Text name = new Text(c.getName());
        name.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 20));
        Text phone = new Text("PHONE: " + c.getPhone());
        phone.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        Text address = new Text("ADDRESS: " + c.getAddress());
        address.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        Text email = new Text("EMAIL: " + c.getEmail());
        email.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        Text favorite = new Text("FAVORITE: " + c.getFavorite());
        favorite.setFont(Font.font("Helvetica", FontWeight.BOLD, 15));

        viewContactDisplay(grid, name, phone, address, email, favorite);
        grid.add(backButton(), 0, 5, 1, 1);
        grid.add(editButton(c), 1, 5, 1, 1);
        grid.add(deleteButton(c), 2, 5, 1, 1);
        mainStage.setScene(scene);
    }

    private static void viewContactDisplay(GridPane grid, Text name, Text phone,
                                           Text address, Text email, Text favorite) {
        grid.add(name, 0, 0, 2, 1);
        grid.add(phone, 0, 1, 2, 1);
        grid.add(address, 0, 2, 2, 1);
        grid.add(email, 0, 3, 2, 1);
        grid.add(favorite, 0, 4, 2, 1);
    }


    // ----------------------------------------- FIND CONTACT ---------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------

}





















