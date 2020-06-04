package ui;

import model.Book;
import model.BookTracker;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BookTrackerGUI extends JFrame {

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    private Image backgroundImage = Toolkit.getDefaultToolkit().getImage("./src/Background.png");
    public static final Color LIGHT_SALMON = new Color(255, 175, 156);
    private Container container;
    private JButton addButton = new JButton("Add a book");
    private JButton removeButton = new JButton("Remove a book");
    private JButton getTotalNumberBooksRead = new JButton("Total number of books read");
    private JButton getTotalNumberBooksReadByGenre = new JButton("Total books read by genre");
    private JButton getRecommendation = new JButton("Recommendation by genre");
    private JButton print = new JButton("Get your list of books read");
    private JButton save = new JButton("Save");
    private JButton quit = new JButton("Quit");

    private static final String BTRACKER_FILE = "./data/BookTrackers.txt";
    private Scanner input;
    private BookTracker myReadList = new BookTracker("");
    private BookTrackerGUI gui;

    public BookTrackerGUI() {
        super("BookTracker");
        loadAccounts();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this BookTrackerApp will operate, and populates the features used
    private void initializeGraphics() {
        container = new JLabel(new ImageIcon("./src/Background.png"));
        SpringLayout layout = new SpringLayout();
        container.setLayout(new FlowLayout());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayMenu();
        add(container);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: loads BookTracker from BTRACKER_FILE, if that file exists;
    // otherwise initializes accounts with default values
    private void loadAccounts() {
        String name = JOptionPane.showInputDialog(this,
                "What is your name?", null);
        try {
            List<BookTracker> bookTrackers = Reader.readBookTracker(new File(BTRACKER_FILE));
            for (BookTracker bt: bookTrackers) {
                if (bt.getName().equals(name)) {
                    myReadList = bt;
                }
            }
        } catch (IOException e) {
            init(name);
        }
    }

    //REQUIRES: input does not contain any spaces, each word starts with a capital
    //           ex: Jane Eyre -> JaneEyre
    private void init(String name) {
        myReadList = new BookTracker(name);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        LinkedList<JButton> list = new LinkedList<>();
        list.add(addButton);
        list.add(removeButton);
        list.add(getTotalNumberBooksRead);
        list.add(getTotalNumberBooksReadByGenre);
        list.add(getRecommendation);
        list.add(print);
        list.add(save);
        list.add(quit);
        list = setButtons(list);
        doActionListeners();
        for (JButton b: list) {
            container.add(b);
        }
    }

    // EFFECT: adds actionListeners to all the buttons
    private void doActionListeners() {
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doAdd();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doRemove();
            }
        });
        getTotalNumberBooksRead.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doTotalRead();
            }
        });
        doActionListeners2();
    }

    // EFFECT: continuation of doActionListeners()
    private void doActionListeners2() {
        getTotalNumberBooksReadByGenre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doTotalGenre();
            }
        });
        getRecommendation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doRecommend();
            }
        });
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doPrint();
            }
        });
        doActionListeners3();
    }

    // EFFECT: continuation of doActionListeners2()
    private void doActionListeners3() {
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveBookTracker();
            }
        });
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    // MODIFIES: buttons
    // EFFECTS: changes the properties of the buttons: size and colour
    private LinkedList<JButton> setButtons(LinkedList<JButton> buttons) {
        LinkedList<JButton> list = new LinkedList<>();
        for (JButton button: buttons) {
            button.setPreferredSize(new Dimension(200, 150));
            button.setBackground(LIGHT_SALMON);
            list.add(button);
        }
        return list;
    }

    // EFFECTS: saves state of this to BTRACKER_FILE
    private void saveBookTracker() {
        try {
            Writer writer = new Writer(new File(BTRACKER_FILE));
            writer.write(myReadList);
            writer.close();
            System.out.println("Books saved to file " + BTRACKER_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save accounts to " + BTRACKER_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    // REQUIRES: input does not contain any spaces, each word starts with a capital
    //           ex: Jane Eyre -> JaneEyre
    // MODIFIES: this
    // EFFECTS: adds a book
    private void doAdd() {
        JTextField title = new JTextField();
        JTextField author = new JTextField();
        JTextField genre = new JTextField();
        JTextField rating = new JTextField();
        final JComponent[] inputs = new JComponent[] {
                new JLabel("Title"), title, new JLabel("Author Name"), author, new JLabel("Genre"), genre,
                new JLabel("Rating"), rating
        };
        int result;
        result = JOptionPane.showConfirmDialog(this, inputs, "Please fill in the fields", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String t = title.getText();
            String a = author.getText();
            String g = genre.getText();
            String r = rating.getText();
            if (!myReadList.contains(t)) {
                myReadList.addBook(new Book(t, a, g, Integer.parseInt(r)));
            } else {
                System.out.println("This book has already been added...\n");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a book
    private void doRemove() {
        JTextField title = new JTextField();
        int result;
        result = JOptionPane.showConfirmDialog(this, title, "Enter the title of the book:", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String t = title.getText();
            if (myReadList.contains(t)) {
                myReadList.removeBook(myReadList.getBook(t));
            } else {
                System.out.println("This book has not been added...\n");
            }
        }
    }

    // EFFECTS: prints out the list of all books
    private void doPrint() {
        LinkedList<JComponent> inputs = getBooks(myReadList.toStringList());
        final JComponent[] books = inputs.toArray(new JComponent[0]);
        JOptionPane.showConfirmDialog(this, books, "Your Books", JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: returns a list of JLabels of all the strings in the given books list
    private LinkedList<JComponent> getBooks(LinkedList<String> books) {
        LinkedList<JComponent> inputs = new LinkedList<>();
        if (books.isEmpty()) {
            inputs.add(new JLabel("You currently have no books recorded"));
        } else {
            for (String s : books) {
                inputs.add(new JLabel(s));
            }
        }
        return inputs;
    }

    // EFFECTS: prints out the total number of books read
    private void doTotalRead() {
        String s = "You have recorded " + myReadList.getTotalNumberOfBooks() + " books";
        JOptionPane.showConfirmDialog(this, new JLabel(s), "Your Total Number of Books", JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: prints out the total number of books read in the genre
    private void doTotalGenre() {
        Object[] possibilities = {"Romance", "Fantasy", "Non-Fiction"};
        String genre = (String)JOptionPane.showInputDialog(
                this,
                "Enter a genre",
                "Total Number of Books by Genre",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "Romance");
        String s;
        if (genre.equals("Romance")) {
            s = "There are " + myReadList.getTotalNumberOfBooksInGenre("Romance") + " books recorded in Romance";
        } else if (genre.equals("Fantasy")) {
            s = "There are " + myReadList.getTotalNumberOfBooksInGenre("Fantasy") + " books recorded in Fantasy";
        } else if (genre.equals("Non-Fiction")) {
            s = myReadList.getTotalNumberOfBooksInGenre("Non-Fiction") + " books recorded in Non-Fiction";
        } else {
            s = "Selection not valid...";
        }
        JOptionPane.showConfirmDialog(this, new JLabel(s), "Your Total Number of Books", JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: provides a book recommendation
    private void doRecommend() {
        Object[] possibilities = {"Romance", "Fantasy", "Non-Fiction"};
        String genre = (String)JOptionPane.showInputDialog(
                this,
                "What genre are you interested in?",
                "Find a Recommendation",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                "Romance");
        String s;
        if (genre.equals("Romance")) {
            s = myReadList.recommend("Romance");
        } else if (genre.equals("Fantasy")) {
            s = myReadList.recommend("Fantasy");
        } else if (genre.equals("Non-Fiction")) {
            s = myReadList.recommend("Non-Fiction");
        } else {
            s = "Selection not valid...";
        }
        JOptionPane.showConfirmDialog(this, new JLabel(s), "Your Recommendation", JOptionPane.PLAIN_MESSAGE);
    }
}
