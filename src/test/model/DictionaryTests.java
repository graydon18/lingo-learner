package model;



import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTests {
    Dictionary testDictionary; 
    Dictionary testDictionaryFull;
    Term testTerm1;
    Term testTerm2;

    @BeforeEach
    void runBefore() {
        testDictionary = new Dictionary("My Spanish Dictionary");
        testTerm1 = new Term("gato", "noun", "cat (masc.)", "El gato bebe la leche");
        testTerm2 = new Term("perro", "noun", "dog (masc.)", "El perro bebe el agua");
        testDictionaryFull = new Dictionary("My Spanish Dictionary 2");
        testDictionaryFull.addTerm(testTerm1);
        testDictionaryFull.addTerm(testTerm2);
    }

    @Test
    void constructorTest() {
        assertEquals("My Spanish Dictionary", testDictionary.getName());
        assertEquals(0, testDictionary.getTerms().size());
    }

    @Test
    void setNameTest() {
        testDictionary.setName("My French Dictionary");
        assertEquals("My French Dictionary", testDictionary.getName());
    }

    @Test
    void addOneTermTest() {
        testDictionary.addTerm(testTerm1);
        assertEquals(testTerm1, testDictionary.getTerms().get(0));
        assertEquals(1, testDictionary.getTerms().size());
    }

    @Test
    void addMultipleTermTest() {
        testDictionary.addTerm(testTerm1);
        testDictionary.addTerm(testTerm2);
        assertEquals(testTerm1, testDictionary.getTerms().get(0));
        assertEquals(testTerm2, testDictionary.getTerms().get(1));
        assertEquals(2, testDictionary.getTerms().size());
    }

    @Test
    void deleteOneTermTest() {
        testDictionaryFull.deleteTerm("perro");
        assertEquals(1, testDictionaryFull.getTerms().size());
        assertEquals(testTerm1, testDictionaryFull.getTerms().get(0));
    }

    @Test
    void deleteNonLastTermTest() {
        testDictionaryFull.deleteTerm("gato");
        assertEquals(1, testDictionaryFull.getTerms().size());
        assertEquals(testTerm2, testDictionaryFull.getTerms().get(0));
    }

    @Test
    void deleteMultipleTermTest() {
        testDictionaryFull.deleteTerm("perro");
        testDictionaryFull.deleteTerm("gato");
        assertEquals(0, testDictionaryFull.getTerms().size());
    }

    @Test
    void findTermTest() {
        assertEquals(testTerm1, testDictionaryFull.findTerm("gato"));
        assertEquals(testTerm2, testDictionaryFull.findTerm("perro"));
    }

    @Test
    void findTermNotInTermsTest() {
        assertEquals(null, testDictionary.findTerm("gato"));
    }

    @Test
    void filterFavTest() {
        testDictionary.addTerm(testTerm1);
        testDictionary.addTerm(testTerm2);
        testTerm2.setFavourite();
        ArrayList<Term> favs = testDictionary.filterFavTerms();
        assertEquals(1, favs.size());
        assertFalse(favs.contains(testTerm1));
        assertTrue(favs.contains(testTerm2));
    }
}
