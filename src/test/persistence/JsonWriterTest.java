package persistence;

import model.Dictionary;
import model.Term;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Dictionary dict = new Dictionary("My Spanish Dictionary");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.openWriter();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDictionary() {
        try {
            Dictionary dict = new Dictionary("My Spanish Dictionary");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDictionary.json");
            writer.openWriter();
            writer.write(dict);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDictionary.json");
            dict = reader.read();
            assertEquals("My Spanish Dictionary", dict.getName());
            assertEquals(0, dict.getTerms().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDictionary() {
        try {
            Dictionary dict = new Dictionary("My Spanish Dictionary");
            Term term1 = new Term("pastel", "noun", "cake", "Este pastel es muy delicioso");
            Term term2 = new Term("hola", "phrase", "hello", "Hola! Como estas");
            term2.setNotes("You can also say Que tal? as a greeting");
            term2.setFavourite();
            dict.addTerm(term1);
            dict.addTerm(term2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDictionary.json");
            writer.openWriter();
            writer.write(dict);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDictionary.json");
            dict = reader.read();
            assertEquals("My Spanish Dictionary", dict.getName());
            List<Term> terms = dict.getTerms();
            assertEquals(2, terms.size());
            checkTerm("pastel", "noun", "cake", "Este pastel es muy delicioso", "", false, terms.get(0));
            checkTerm("hola", "phrase", "hello", "Hola! Como estas", "You can also say Que tal? as a greeting", true, 
                        terms.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

// I used the Workroom App as a reference as it informed me on JSON writer testing
