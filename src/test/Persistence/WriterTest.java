package Persistence;

import model.BookTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {
    private static final String TEST_FILE = "./data/testBookTracker.txt";
    private Writer testWriter;
    private BookTracker myReadList;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        myReadList = new BookTracker("Katie");
    }

    @Test
    void testWriteAccounts() {
        // save chequing and savings accounts to file
        testWriter.write(myReadList);
        testWriter.close();

        // now read them back in and verify that the accounts have the expected values
        try {
            List<BookTracker> bookTrackers = Reader.readBookTracker(new File(TEST_FILE));
            BookTracker bt1 = bookTrackers.get(0);
            assertEquals("Katie", bt1.getName());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
