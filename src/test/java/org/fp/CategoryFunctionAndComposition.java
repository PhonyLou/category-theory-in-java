package org.fp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class CategoryFunctionAndComposition {
    Function<Integer, String> f() {
        return Object::toString;
    }

    Function<String, Boolean> g() {
        return s -> s.length() > 5;
    }

    public static <A, B, C> Function<A, C> compose(Function<A, B> f, Function<B, C> g) {
        return x -> g.apply(f.apply(x));
    }

    public static <T> Function<T, T> identity() {
        return x -> x;
    }
}

class CategoryFunctionAndCompositionTest {
    @Test
    void testF() {
        CategoryFunctionAndComposition cp = new CategoryFunctionAndComposition();
        Function<Integer, String> func = cp.f();
        assert func.apply(5).equals("5");
        assert func.apply(10).equals("10");
    }

    @Test
    void testG() {
        CategoryFunctionAndComposition cp = new CategoryFunctionAndComposition();
        Function<String, Boolean> func = cp.g();
        assert func.apply("after") == false;
        assert func.apply("full composition") == true;
    }

    @Test
    void testBaseCompose() {
        CategoryFunctionAndComposition cp = new CategoryFunctionAndComposition();
        Function<Integer, Boolean> gAfterF = CategoryFunctionAndComposition.compose(cp.f(), cp.g());
        assert gAfterF.apply(5) == false;
        assert gAfterF.apply(123456) == true;
    }

    @Test
    void testIdentity() {
        Function<Integer, Integer> id = CategoryFunctionAndComposition.identity();
        assert id.apply(5).equals(5);

        Function<String, String> id_s = CategoryFunctionAndComposition.identity();
        assert id_s.apply("10").equals("10");
    }

    /**
     * 单位元律 (Identity Law): 任何函数f与恒等函数组合，结果仍然是f。
     */
    @Test
    void testIdentityLaw_for_int() {
        CategoryFunctionAndComposition cp = new CategoryFunctionAndComposition();
        Function<Integer, String> f = cp.f();
        Function<String, String> id_s = CategoryFunctionAndComposition.identity();

        Function<Integer, String> fAfterId = CategoryFunctionAndComposition.compose(f, id_s);
        Assertions.assertEquals(fAfterId.apply(600), f.apply(600));
        Assertions.assertEquals(fAfterId.apply(0), f.apply(0));

        Function<Integer, Integer> id_i = CategoryFunctionAndComposition.identity();
        Function<Integer, String> idAfterF = CategoryFunctionAndComposition.compose(id_i, f);
        Assertions.assertEquals(idAfterF.apply(600), f.apply(600));
    }
}