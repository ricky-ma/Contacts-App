package ui;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Contact;
import model.ContactMap;

import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {

    private GridPane grid0;
    private ContactMap contactMap = new ContactMap();
    private Stage mainStage;

    // EFFECTS: runs the program
    public static void main(String[] args) throws IOException {
        launch(args);
    }


    public void start(Stage primaryStage) {
        this.mainStage = primaryStage;
        primaryStage.setTitle("Contax");

        grid0 = new GridPane();
        grid0.setAlignment(Pos.CENTER);
        grid0.setHgap(10);
        grid0.setVgap(10);
        grid0.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("MENU");
        scenetitle.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 40));
        grid0.add(scenetitle, 0, 0, 2, 1);

        Scene scene1 = new Scene(grid0, 400, 600);
        primaryStage.setScene(scene1);

        buttonOne();
        buttonTwo();
        buttonThree();
        buttonFour();
        buttonFive();
        try {
            buttonSix();
        } catch (IOException e) {
            //
        }

        primaryStage.show();
    }

    private void buttonOne() {
        Button btn1 = new Button("NEW CONTACT");
        grid0.add(btn1, 0, 1);
        btn1.setOnAction(event -> newContact());
    }

    private void buttonTwo() {
        Button btn2 = new Button("FIND CONTACTS");
        grid0.add(btn2, 0, 2);
        btn2.setOnAction(event -> {

        });
    }

    private void buttonThree() {
        Button btn3 = new Button("EDIT CONTACTS");
        grid0.add(btn3, 0, 3);
        btn3.setOnAction(event -> {

        });
    }

    private void buttonFour() {
        Button btn4 = new Button("VIEW FAVORITES");
        grid0.add(btn4, 0, 4);
        btn4.setOnAction(event -> displayFavorites());
    }

    private void buttonFive() {
        Button btn5 = new Button("VIEW ALL");
        grid0.add(btn5, 0, 5);
        btn5.setOnAction(event -> displayAllContacts());
    }

    private void buttonSix() throws IOException {
        Button btn6 = new Button("EXIT");
        grid0.add(btn6, 0, 8);
        btn6.setOnAction(event -> System.exit(0));
        contactMap.save("contactfile.txt");
    }

    private Button backButton() {
        Button backBtn = new Button("MENU");
        backBtn.setOnAction(event -> start(mainStage));
        return backBtn;
    }

    private Button deleteButton(Contact c) {
        Button deleteBtn = new Button("DELETE");
        deleteBtn.setOnAction(event -> deleteContactWindow(c));
        return deleteBtn;
    }

    private void deleteContactWindow(Contact c) {
        GridPane deleteGrid = new GridPane();
        deleteGrid.setAlignment(Pos.CENTER);
        deleteGrid.setHgap(10);
        deleteGrid.setVgap(10);

        Stage stage = new Stage();
        stage.setTitle("Delete Contact");
        stage.setScene(new Scene(deleteGrid, 450, 450));

        Text scenetitle = new Text("DELETE CONTACT?");
        scenetitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        deleteGrid.add(scenetitle, 0, 0, 2, 1);

        Button yes = new Button("YES");
        yes.setOnAction(event -> {
            deleteContact(c);
            stage.close();
        });
        Button no = new Button("NO");
        no.setOnAction(event -> stage.close());
        deleteGrid.add(yes, 0, 1);
        deleteGrid.add(no, 1, 1);

        stage.show();
    }

    private void deleteContact(Contact c) {
        contactMap.deleteContact(c.getName());

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Stage stage = new Stage();
        stage.setTitle("Contact Deleted");
        stage.setScene(new Scene(grid, 200, 200));

        Text scenetitle = new Text("CONTACT DELETED");
        scenetitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Button okBtn = new Button("OK");
        grid.add(okBtn,0,1,2,1);
        okBtn.setOnAction(event -> {
            stage.close();
            start(mainStage);
        });
        stage.show();
    }

    private void newContact() {
        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setHgap(10);
        grid2.setVgap(10);
        grid2.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("INPUT CONTACT INFO");
        scenetitle.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 20));
        grid2.add(scenetitle, 0, 0, 2, 1);

        Scene scene = new Scene(grid2, 400, 600);
        mainStage.setScene(scene);

        backButton();
    }

    private void displayAllContacts() {
        ListView<String> contactList = new ListView<>();
        contactList.setFixedCellSize(50);
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Contact c: contactMap.getContactMap().values())  {
            items.add(c.getName());
        }
        contactList.setItems(items);

        Text scenetitle = new Text("CONTACTS");
        scenetitle.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 20));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(contactList, 0,1);
        grid.add(backButton(),0,2);
        Scene scene = new Scene(grid, 400, 600);
        mainStage.setScene(scene);

        contactList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> viewContact(contactMap.get(newValue)));
    }

    private void displayFavorites() {
        ListView<String> contactList = new ListView<>();
        contactList.setFixedCellSize(50);
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Contact c: contactMap.getFavoritesMap().values())  {
            items.add(c.getName());
        }
        contactList.setItems(items);

        Text scenetitle = new Text("FAVORITES");
        scenetitle.setFont(Font.font("Helvetica", FontWeight.EXTRA_BOLD, 20));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(contactList, 0,1);
        grid.add(backButton(),0,2);
        Scene scene = new Scene(grid, 400, 600);
        mainStage.setScene(scene);

        contactList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> viewContact(contactMap.get(newValue)));
    }

    private void viewContact(Contact c) {
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

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(name, 0, 0, 2, 1);
        grid.add(phone, 0, 1, 2, 1);
        grid.add(address, 0, 2, 2, 1);
        grid.add(email, 0, 3, 2, 1);
        grid.add(favorite, 0, 4, 2, 1);

        backButton();
        deleteButton(c);
        grid.add(backButton(),0,5,1,1);
        grid.add(deleteButton(c),1,5,1,1);
        Scene scene = new Scene(grid, 400, 600);
        mainStage.setScene(scene);
    }

























    private static void switchLoop(ContactMap contactMap, int n) {
        switch (n) {
            case 1: doCase1(contactMap);
                break;
            case 2: doCase2(contactMap);
                break;
            case 3: doCase3(contactMap);
                break;
            case 4: contactMap.printFavorites();
                break;
            case 5: contactMap.printAllContacts();
                break;
            case 6: doCase6(contactMap);
                break;
            default: System.out.println("Invalid input. Only digits 1-7 accepted.");
        }
    }


    private static String getNewContactInfoUI() {
        System.out.println();
        Scanner newInput = new Scanner(System.in);
        System.out.println("Name: ");
        String name = newInput.nextLine();
        System.out.println("Phone: ");
        String phone = newInput.nextLine();
        System.out.println("Address: ");
        String address = newInput.nextLine();
        System.out.println("Email: ");
        String email = newInput.nextLine();
        System.out.println("Favorite?");
        System.out.println("1.Yes");
        System.out.println("2.No");
        String favorite = newInput.nextLine();
        return name + "---" + phone + "---" + address + "---" + email + "---" + favorite;
    }


    private static int newIntInput() {
        Scanner newInput = new Scanner(System.in);
        return newInput.nextInt();
    }


    private static String newStringInput() {
        Scanner newInput = new Scanner(System.in);
        return newInput.nextLine();
    }


    private static void doCase1(ContactMap contactMap) {
        String contactInfo = getNewContactInfoUI();
        contactMap.addNewContact(contactInfo);
    }


    private static void doCase2(ContactMap contactMap) {
        System.out.println();
        System.out.println("First or last name: ");
        String search = newStringInput();
        contactMap.findContact(search);
    }


    private static void doCase3(ContactMap contactMap) {
        System.out.println();
        System.out.println("Enter full name of contact you would like to edit:");
        String name = newStringInput();
        Contact c2 = contactMap.editContact(name);
        int n = newIntInput();
        c2.editContactDetails(c2,n);
    }


    private static void doCase6(ContactMap contactMap) {
        System.out.println();
        System.out.println("Which contact do you want to delete?");
        System.out.println("Please enter full name:");
        String name2 = newStringInput();
        contactMap.deleteContact(name2);
    }
}

