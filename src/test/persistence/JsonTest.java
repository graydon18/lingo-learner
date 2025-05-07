package persistence;

import model.Term;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTerm(String na, String gt, String tr, String ex, String no, Boolean f, Term term) {
        assertEquals(na, term.getName());
        assertEquals(gt, term.getGType());
        assertEquals(tr, term.getTranslation());
        assertEquals(ex, term.getExample());
        assertEquals(no, term.getNotes());
        assertEquals(f, term.getFavourite());
    }
}

// I used the Workroom App as a reference as it informed me on JSON writer testing

