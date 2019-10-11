package inc.softserve.security;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class JavaNativeSaltGenTest {

    private static SaltGen saltGen;

    @BeforeAll
    static void init(){
        saltGen = new JavaNativeSaltGen();
    }

    @RepeatedTest(100)
    void get(){
        assertEquals(32, saltGen.get().length());
    }

}