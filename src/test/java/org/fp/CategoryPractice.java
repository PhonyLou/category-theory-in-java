package org.fp;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class CategoryPractice {
    Function<Integer, String> f() {
        return x -> x.toString();
    }
}

class CategoryPracticeTest {
    @Test
    void testF() {
        CategoryPractice cp = new CategoryPractice();
        Function<Integer, String> func = cp.f();
        assert func.apply(5).equals("5");
        assert func.apply(10).equals("10");
    }
}