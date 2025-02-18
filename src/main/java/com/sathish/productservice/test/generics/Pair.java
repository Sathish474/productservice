package com.sathish.productservice.test.generics;

public class Pair<K, V> {
    public K first;
    public V second;

    public void setFirst(K first) {
        this.first = first;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    // Place holders for method level
    public static <L, M> void doSomething(L first, M second){
        System.out.println(first + " " + second);
    }
}
