package com.sathish.productservice.test.generics;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        Pair<String, Integer> pair = new Pair<>();
        pair.setFirst("first");
        pair.setSecond(10);

        Pair<Integer, Integer> pair2 = new Pair<>();
        pair2.setFirst(20);
        pair2.setSecond(40);
        System.out.println(pair.getFirst());
        System.out.println(pair.getSecond());

        System.out.println(pair2.getFirst());
        System.out.println(pair2.getSecond());


        Pair.doSomething(1, "second");

        Shape shape = new Shape();
        shape.shape = "Generic Shape";

        ShapeCommand<Shape> shapeShapeCommand = new ShapeCommand<>();
        shapeShapeCommand.printShape(shape);

        List<Animal> dogs = new ArrayList<>();

        Test.doSomething(dogs);
    }
}
