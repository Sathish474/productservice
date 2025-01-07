package com.sathish.productservice.test.generics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Client {
    public static void main(String[] args) {
       /* Pair<String, Integer> pair = new Pair<>();
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

        Test.doSomething(dogs);*/
        example1();
    }

    public static void example1(){
        List<Integer> list = List.of(1,2,3,4,5,6);
        Stream<Integer> stream1 = list.stream();
        // Terminal operation ends the stream and the same stream cannot be used again
        stream1.forEach(i -> System.out.print(i + " "));
        System.out.println();

        Stream<Integer> stream2 = list.stream();
        stream2.forEach(i -> System.out.print(i + " "));
        System.out.println();

        Stream<Integer> stream3 = list.stream();
        stream3.map((i) -> i*i).forEach( i -> System.out.print(i + " "));
        System.out.println();

        Stream<Integer> stream4 = list.stream();
        stream4.filter(i -> i%2 == 0).map(i -> i * i).forEach(i -> System.out.print(i + " "));
        System.out.println();

        list.forEach(i -> System.out.print(i + " "));
        System.out.println();

        //list.stream().reduce();
    }
}
