package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Reader;
import persistence.Writer;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookTrackerTest {

    private BookTracker myReadList = new BookTracker("Ashley");
    private static final String TEST_FILE = "./data/testBookTracker.txt";
    private PrintWriter testWriter;
    public Book r1 = myReadList.r1;
    public Book r2 = myReadList.r2;
    public Book f1 = myReadList.f1;
    public Book f2 = myReadList.f2;
    public Book nf1 = myReadList.nf1;
    public Book nf2 = myReadList.nf2;


    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        assertTrue(myReadList.addBook(r1));
        assertTrue(myReadList.addBook(f1));
        assertTrue(myReadList.addBook(nf1));
        assertEquals(3, myReadList.getTotalNumberOfBooks());
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Romance"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Fantasy"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Non-Fiction"));

        testWriter = new PrintWriter(new File(TEST_FILE), "UTF-8");
    }

    @Test
    void addBookTest() {
        assertFalse(myReadList.addBook(r1));
        assertFalse(myReadList.addBook(f1));
        assertFalse(myReadList.addBook(nf1));
        assertEquals(3, myReadList.getTotalNumberOfBooks());
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Romance"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Fantasy"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Non-Fiction"));

        assertTrue(myReadList.removeBook(r1));
        assertTrue(myReadList.removeBook(f1));
        assertTrue(myReadList.addBook(r1));
        assertTrue(myReadList.addBook(f1));
        assertEquals(3, myReadList.getTotalNumberOfBooks());
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Romance"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Fantasy"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Non-Fiction"));

        assertTrue(myReadList.addBook(r2));
        assertEquals(4, myReadList.getTotalNumberOfBooks());
        assertEquals(2, myReadList.getTotalNumberOfBooksInGenre("Romance"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Fantasy"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Non-Fiction"));

        assertTrue(myReadList.addBook(f2));
        assertEquals(5, myReadList.getTotalNumberOfBooks());
        assertEquals(2, myReadList.getTotalNumberOfBooksInGenre("Romance"));
        assertEquals(2, myReadList.getTotalNumberOfBooksInGenre("Fantasy"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Non-Fiction"));

        assertTrue(myReadList.addBook(nf2));
        assertEquals(6, myReadList.getTotalNumberOfBooks());
        assertEquals(2, myReadList.getTotalNumberOfBooksInGenre("Romance"));
        assertEquals(2, myReadList.getTotalNumberOfBooksInGenre("Fantasy"));
        assertEquals(2, myReadList.getTotalNumberOfBooksInGenre("Non-Fiction"));
    }

    @Test
    void removeBookTest() {
        assertFalse(myReadList.removeBook(r2));
        assertFalse(myReadList.removeBook(f2));
        assertFalse(myReadList.removeBook(nf2));
        assertEquals(3, myReadList.getTotalNumberOfBooks());
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Romance"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Fantasy"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Non-Fiction"));

        assertTrue(myReadList.addBook(r2));
        assertTrue(myReadList.addBook(f2));
        assertTrue(myReadList.removeBook(r2));
        assertTrue(myReadList.removeBook(f2));
        assertEquals(3, myReadList.getTotalNumberOfBooks());
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Romance"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Fantasy"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Non-Fiction"));

        assertTrue(myReadList.removeBook(r1));
        assertEquals(2, myReadList.getTotalNumberOfBooks());
        assertEquals(0, myReadList.getTotalNumberOfBooksInGenre("Romance"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Fantasy"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Non-Fiction"));

        assertTrue(myReadList.removeBook(f1));
        assertEquals(1, myReadList.getTotalNumberOfBooks());
        assertEquals(0, myReadList.getTotalNumberOfBooksInGenre("Romance"));
        assertEquals(0, myReadList.getTotalNumberOfBooksInGenre("Fantasy"));
        assertEquals(1, myReadList.getTotalNumberOfBooksInGenre("Non-Fiction"));

        assertTrue(myReadList.removeBook(nf1));
        assertEquals(0, myReadList.getTotalNumberOfBooks());
        assertEquals(0, myReadList.getTotalNumberOfBooksInGenre("Romance"));
        assertEquals(0, myReadList.getTotalNumberOfBooksInGenre("Fantasy"));
        assertEquals(0, myReadList.getTotalNumberOfBooksInGenre("Non-Fiction"));
    }

    @Test
    void getTotalNumberOfBooksWithRatingTest() {
        assertEquals(2, myReadList.getTotalNumberOfBooksWithRating(5));
        assertEquals(1, myReadList.getTotalNumberOfBooksWithRating(4));
        assertEquals(0, myReadList.getTotalNumberOfBooksWithRating(3));
        myReadList.addBook(r2);
        myReadList.addBook(f2);
        myReadList.addBook(nf2);
        assertEquals(4, myReadList.getTotalNumberOfBooksWithRating(5));
        assertEquals(1, myReadList.getTotalNumberOfBooksWithRating(4));
        assertEquals(1, myReadList.getTotalNumberOfBooksWithRating(3));
    }

    @Test
    void toStringTest() {
        assertEquals("Dating-ish by Penny Reid, Rated 5 stars\n" +
                "The Bear And The Nightingale by Katherine Arden, Rated 5 stars\n" +
                "The Glass Castle by Jeanette Walls, Rated 4 stars\n", myReadList.toString());
        myReadList.addBook(r2);
        myReadList.addBook(f2);
        myReadList.addBook(nf2);
        assertEquals("Dating-ish by Penny Reid, Rated 5 stars\n" +
                "The Bear And The Nightingale by Katherine Arden, Rated 5 stars\n" +
                "The Glass Castle by Jeanette Walls, Rated 4 stars\n" +
                "Love And Other Words by Christina Lauren, Rated 5 stars\n" +
                "The Cruel Prince by Holly Black, Rated 5 stars\n" +
                "Sapiens: A Brief History Of Humankind by Yuval Noah Harari, Rated 3 stars\n", myReadList.toString());

        BookTracker myReadList2 = new BookTracker("");
        assertEquals("No books have been recorded...", myReadList2.toString());
        myReadList2.addBook(r1);
        assertEquals("Dating-ish by Penny Reid, Rated 5 stars\n", myReadList2.toString());

        BookTracker myReadList3 = new BookTracker("");
        assertEquals("No books have been recorded...", myReadList3.toString());
        myReadList3.addBook(f1);
        assertEquals("The Bear And The Nightingale by Katherine Arden, Rated 5 stars\n",
                myReadList3.toString());

        BookTracker myReadList4 = new BookTracker("");
        assertEquals("No books have been recorded...", myReadList4.toString());
        myReadList4.addBook(nf1);
        assertEquals("The Glass Castle by Jeanette Walls, Rated 4 stars\n", myReadList4.toString());
    }

    @Test
    void toStringListTest() {
        assertEquals(3, myReadList.toStringList().size());
        myReadList.addBook(r2);
        myReadList.addBook(f2);
        myReadList.addBook(nf2);
        assertEquals(6, myReadList.toStringList().size());

        BookTracker myReadList2 = new BookTracker("");
        assertEquals(0, myReadList2.toStringList().size());
        myReadList2.addBook(r1);
        assertEquals(1, myReadList2.toStringList().size());

        BookTracker myReadList3 = new BookTracker("");
        assertEquals(0, myReadList3.toStringList().size());
        myReadList3.addBook(f1);
        assertEquals(1, myReadList3.toStringList().size());

        BookTracker myReadList4 = new BookTracker("");
        assertEquals(0, myReadList4.toStringList().size());
        myReadList4.addBook(nf1);
        assertEquals(1, myReadList4.toStringList().size());
    }

    @Test
    void getBookTest() {
        assertEquals(r1, myReadList.getBook("Dating-ish"));
        assertEquals(f1, myReadList.getBook("The Bear And The Nightingale"));
        assertEquals(nf1, myReadList.getBook("The Glass Castle"));

        assertEquals(null, myReadList.getBook("Love And Other Words"));
        assertEquals(null, myReadList.getBook(""));
    }

    @Test
    void recommendTest() {
        assertEquals("Love And Other Words by Christina Lauren",
                myReadList.recommend("Romance"));
        assertEquals( "The Cruel Prince by Holly Black" ,
                myReadList.recommend("Fantasy"));
        assertEquals( "Sapiens: A Brief History Of Humankind by Yuval Noah Harari",
                myReadList.recommend("Non-Fiction"));

        myReadList.addBook(r2);
        myReadList.addBook(f2);
        myReadList.addBook(nf2);
        assertEquals("", myReadList.recommend("Romance"));
        assertEquals("", myReadList.recommend("Fantasy"));
        assertEquals("", myReadList.recommend("Non-Fiction"));
    }

    @Test
    void saveTest() {
        myReadList.save(testWriter);
        BookTracker myReadList2 = new BookTracker("Ashley");

        try {
            List<BookTracker> bookTrackers = Reader.readBookTracker(new File(TEST_FILE));
            for (BookTracker bt: bookTrackers) {
                if (bt.getName().equals("Ashley")) {
                    myReadList2 = bt;
                }
            }
        } catch (IOException e) {
            myReadList = new BookTracker("Ashley");
        }
        for (Book b: myReadList2.myBookTracker){
            assertTrue(myReadList.myBookTracker.contains(b));
        }
    }
}