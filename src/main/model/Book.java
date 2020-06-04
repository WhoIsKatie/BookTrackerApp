package model;

public class Book {
    String title;
    String author;
    String genre;
    int rating;

    public Book(String title, String author, String genre, Integer rating) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.rating = rating;
    }

    // EFFECTS: returns the title of this book
    public String getTitle() {
        return title;
    }

    // EFFECTS: returns the author of this book
    public String getAuthor() {
        return author;
    }

    // EFFECTS: returns the genre of this book
    public String getGenre() {
        return genre;
    }

    // EFFECTS: returns the rating of this book
    public int getRating() {
        return rating;
    }
}
