package model;

import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.LinkedList;

public class BookTracker implements Saveable {

    private String name;
    public LinkedList<Book> myBookTracker;
    private LinkedList<Book> romance;
    private LinkedList<Book> fantasy;
    private LinkedList<Book> nonFiction;

    // (RECOMMENDATION) LIBRARY:
    // Romance:
    public Book r1 = new Book("Dating-ish", "Penny Reid", "Romance", 5);
    public Book r2 = new Book("Love And Other Words", "Christina Lauren", "Romance", 5);
    // Fantasy:
    public Book f1 = new Book("The Bear And The Nightingale", "Katherine Arden", "Fantasy", 5);
    public Book f2 = new Book("The Cruel Prince", "Holly Black", "Fantasy", 5);
    // Non-Fiction:
    public Book nf1 = new Book("The Glass Castle", "Jeanette Walls", "Non-Fiction", 4);
    public Book nf2 = new Book("Sapiens: A Brief History Of Humankind", "Yuval Noah Harari",
            "Non-Fiction", 3);


    public BookTracker(String name) {
        this.name = name;
        myBookTracker = new LinkedList<>();

        romance = new LinkedList<>();
        fantasy = new LinkedList<>();
        nonFiction = new LinkedList<>();

        romance.add(r1);
        romance.add(r2);
        fantasy.add(f1);
        fantasy.add(f2);
        nonFiction.add(nf1);
        nonFiction.add(nf2);
    }

    // MODIFIES: this
    // EFFECTS: returns true and adds the book to the read list if this does not contain it already, otherwise
    // produce false
    public Boolean addBook(Book book) {
        if (!myBookTracker.contains(book)) {
            myBookTracker.add(book);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: returns true and removes the book to the read list if this does contain it, otherwise
    // produce false
    public Boolean removeBook(Book book) {
        if (myBookTracker.contains(book)) {
            myBookTracker.remove(book);
            return true;
        }
        return false;
    }

    // EFFECTS: returns the number of books in this read list
    public int getTotalNumberOfBooks() {
        return myBookTracker.size();
    }

    // EFFECTS: returns the number of books with the given rating
    public int getTotalNumberOfBooksWithRating(int rating) {
        int temp = 0;
        for (Book b: myBookTracker) {
            if (b.getRating() == rating) {
                temp++;
            }
        }
        return temp;
    }

    // EFFECTS: returns the number of books in the given genre
    public int getTotalNumberOfBooksInGenre(String genre) {
        int temp = 0;
        for (Book b: myBookTracker) {
            if (b.getGenre().equals(genre)) {
                temp++;
            }
        }
        return temp;
    }

    // EFFECTS: returns true if the book is in the list, otherwise returns false
    public boolean contains(String title) {
        boolean c = false;
        if (!myBookTracker.isEmpty()) {
            for (Book b : myBookTracker) {
                if (b.getTitle().equals(title)) {
                    c = true;
                }
            }
        }
        return c;
    }

    public String getName() {
        return name;
    }

    public Book getBook(String title) {
        for (Book b: myBookTracker) {
            if (b.getTitle().equals(title)) {
                return b;
            }
        }
        return null;
    }

    // EFFECTS: returns a string representing this list of books where each
    // book is recorded on a line of its own in the format
    // (title) by (author), Rated (rating) stars
    // For example:
    // Jane Eyre by Charlotte Bronte, Rated 5 stars
    // The Glass Castle by Jeannette Walls, Rated\ 4 stars
    // The Cruel Prince by Holly Black, Rated 5 stars
    public String toString() {
        String stringRead = "";
        for (Book b: myBookTracker) {
            String title = b.getTitle();
            stringRead += b.getTitle() + " by " + b.getAuthor() + ", Rated " + b.getRating() + " stars\n";
        }

        if (stringRead.equals("")) {
            return "No books have been recorded...";
        } else {
            return stringRead;
        }
    }

    public LinkedList<String> toStringList() {
        LinkedList<String> books = new LinkedList<>();
        for (Book b: myBookTracker) {
            String title = b.getTitle();
            books.add(b.getTitle() + " by " + b.getAuthor() + ", Rated " + b.getRating() + " stars");

        }

        return books;
    }

    // REQUIRES: genre is one of "Romance", "Fantasy", or "Non-Fiction"
    // EFFECTS: returns a book recommendation that is not in the book list
    public String recommend(String genre) {
        BookTracker temp = new BookTracker("temp");
        if (genre.equals("Romance")) {
            temp = addsNewBook(romance, temp);
        } else if (genre.equals("Fantasy")) {
            temp = addsNewBook(fantasy, temp);
        } else {
            temp = addsNewBook(nonFiction, temp);
        }
        if (temp.getTotalNumberOfBooks() == 0) {
            return "";
        } else {
            int delete = temp.toString().indexOf(", Rated");
            return temp.toString().substring(0, delete);
        }
    }

    // EFFECTS: returns a book that has not been recorded
    public BookTracker addsNewBook(LinkedList<Book> genre, BookTracker temp) {
        for (Book b: genre) {
            if (!this.contains(b.getTitle())) {
                temp.addBook(b);
            }
        }
        return temp;
    }

    //EFFECTS: prints username and book(s) onto textfile
    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(name + "\n");
        for (Book b: myBookTracker) {
            printWriter.print(b.getTitle() + ", " + b.getAuthor() + ", " + b.getGenre() + ", " + b.getRating() + "\n");
        }
        printWriter.print("//" + "\n");
    }
}
