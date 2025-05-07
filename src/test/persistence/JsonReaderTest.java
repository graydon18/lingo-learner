package persistence;

import model.Dictionary;
import model.Term;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/thisFileDoesntExist.json");
        try {
            Dictionary dict = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDictionary() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDictionary.json");
        try {
            Dictionary dict = reader.read();
            assertEquals("My Spanish Dictionary", dict.getName());
            assertEquals(0, dict.getTerms().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDictionary() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDictionary.json");
        try {
            Dictionary dict = reader.read();
            assertEquals("My Spanish Dictionary", dict.getName());
            List<Term> terms = dict.getTerms();
            assertEquals(2, terms.size());
            checkTerm("pastel", "noun", "cake", "Este pastel es muy delicioso", "", false, terms.get(0));
            checkTerm("hola", "phrase", "hello", "Hola! Como estas", "You can also say Que tal? as a greeting", true, 
                        terms.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

// I used the Workroom App as a reference as it informed me on JSON reader testing
