package persistence;

import model.Book;
import model.BookTracker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A reader that can read bookTracker data from a file
public class Reader {
    public static final String DELIMITER = ", ";
    public static final String EMPTYLINE = "//";

    // EFFECTS: returns a list of BookTracker parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<BookTracker> readBookTracker(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of BookTracker parsed from list of strings
    // where the first string is the username and other strings contain data for a book
    private static List<BookTracker> parseContent(List<String> fileContent) {
        List<BookTracker> bookTrackers = new ArrayList<>();
        List<String> temp = new ArrayList<>();

        if (!fileContent.isEmpty()) {
            for (String l : fileContent) {
                if (!l.contains(EMPTYLINE)) {
                    temp.add(l);
                } else {
                    bookTrackers.add(parseBookTracker(temp));
                    temp.clear();
                }
            }
        }

        return bookTrackers;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size >= 0 where the first line represents the username
    // with lines of size 4 afterwards where element 0 represents the
    // title of the next book constructed, element 1 represents
    // the author, elements 2 represents the genre and element 3 represents
    // the rating of the book to be constructed
    // EFFECTS: returns an BookTracker constructed from components
    private static BookTracker parseBookTracker(List<String> components) {
        String userID = components.get(0);
        BookTracker bt = new BookTracker(userID);
        for (String s: components) {
            if (s.contains(", ")) {
                ArrayList<String> lineComponents = splitString(s);
                String title = lineComponents.get(0);
                String author = lineComponents.get(1);
                String genre = lineComponents.get(2);
                int rating = Integer.parseInt(lineComponents.get(3));
                bt.addBook(new Book(title, author, genre, rating));
            }
        }
        return bt;
    }
}
