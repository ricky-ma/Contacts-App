package ui;


import model.ContactList;

public class Main {

    /*
    // EFFECTS: creates and shows the user interface with contact list
    private static void createAndShowUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Contacts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new ContactPanel());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: runs the app
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowUI();
            }
        });
    }*/



    // EFFECTS: runs the program
    public static void main(String[] args) {
        ContactList contacts = new ContactList();
    }
}

