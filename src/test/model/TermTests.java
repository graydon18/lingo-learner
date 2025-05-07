package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TermTests {
    Term testTerm;

    @BeforeEach
    void runBefore() {
        testTerm = new Term("gato", "noun", "cat (masc.)", "El gato bebe la leche");
    }

    @Test
    void constructorTest() {
        assertEquals("gato", testTerm.getName());
        assertEquals("noun", testTerm.getGType());
        assertEquals("cat (masc.)", testTerm.getTranslation());
        assertEquals("El gato bebe la leche", testTerm.getExample());
        assertEquals("", testTerm.getNotes());
        assertFalse(testTerm.getFavourite());
    }

    @Test
    void setNameTest() {
        testTerm.setName("gata");
        assertEquals("gata", testTerm.getName());
    }

    @Test
    void setGTypeTest() {
        testTerm.setGType("verb");
        assertEquals("verb", testTerm.getGType());
    }

    @Test
    void setTranslationTest() {
        testTerm.setTranslation("cat (fem.)");
        assertEquals("cat (fem.)", testTerm.getTranslation());
    }

    @Test
    void setExampleTest() {
        testTerm.setExample("La gata bebe la leche");
        assertEquals("La gata bebe la leche", testTerm.getExample());
    }

    @Test
    void setNotesTest() {
        testTerm.setNotes("Cat has two forms depending on gender");
        assertEquals("Cat has two forms depending on gender", testTerm.getNotes());
    }

    @Test
    void setFavouriteTest() {
        testTerm.setFavourite();
        assertTrue(testTerm.getFavourite());
        testTerm.setFavourite();
        assertTrue(testTerm.getFavourite());
    }

    @Test
    void setUnfavouriteTest() {
        testTerm.setFavourite();
        testTerm.setUnfavourite();
        assertFalse(testTerm.getFavourite());
        testTerm.setUnfavourite();
        assertFalse(testTerm.getFavourite());
    }
}
