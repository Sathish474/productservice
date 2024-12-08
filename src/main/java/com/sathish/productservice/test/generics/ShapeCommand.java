package com.sathish.productservice.test.generics;

// Generics bounds
public class ShapeCommand<E extends Shape> {

    public void printShape(E shape) {
        System.out.println(shape.shape);
    }


    public <T extends Shape>  T  printShape1(T shape) {
        System.out.println(shape.shape);
        return null;
    }
}
