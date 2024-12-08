package com.sathish.productservice.test.generics;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static  void doSomething(List<Animal> animalList) {
        List list = new ArrayList();
        Pair pair = new Pair();

    }

    // Wild cards are only applicable at method arguments level
    public static void doSomething1(List<? extends Animal> animalList) {

    }
}
