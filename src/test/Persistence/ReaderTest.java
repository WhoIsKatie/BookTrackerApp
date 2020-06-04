package Persistence;

import model.BookTracker;
import org.junit.jupiter.api.Test;
import persistence.Reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {
    @Test
    void testParseAccountsFile1() {
        try {
            System.out.println("HI");
            List<BookTracker> bookTrackers = Reader.readBookTracker(new File("./data/testBookTrackerFile.txt"));
            BookTracker bt1 = bookTrackers.get(0);
            assertEquals("Katie", bt1.getName());

            BookTracker bt2 = bookTrackers.get(1);
            assertEquals("Ashley", bt2.getName());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testIOException() {
        try {
            Reader.readBookTracker(new File("./path/does/not/exist/testBookTracker.txt"));
        } catch (IOException e) {
            // expected
        }
    }
}
