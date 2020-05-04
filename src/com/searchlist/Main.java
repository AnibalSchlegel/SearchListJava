package com.searchlist;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // write your code here
        List<Relation> relations = new ArrayList<>();
        relations.add(new Relation("A", "B", 2));
        relations.add(new Relation("A", "G", 10));
        relations.add(new Relation("B", "D", 2));
        relations.add(new Relation("A", "C", 3));
        relations.add(new Relation("B", "E", 3));
        relations.add(new Relation("C", "E", 2));
        relations.add(new Relation("C", "F", 3));
        relations.add(new Relation("C", "G", 5));
        relations.add(new Relation("D", "E", 1));
        relations.add(new Relation("D", "H", 4));
        relations.add(new Relation("E", "H", 3));
        relations.add(new Relation("E", "I", 4));
        relations.add(new Relation("F", "G", 1));
        relations.add(new Relation("F", "I", 5));
        relations.add(new Relation("F", "J", 3));
        relations.add(new Relation("G", "J", 2));
        relations.add(new Relation("G", "N", 3));
        relations.add(new Relation("H", "K", 2));
        relations.add(new Relation("H", "L", 3));
        relations.add(new Relation("I", "L", 2));
        relations.add(new Relation("I", "M", 3));
        relations.add(new Relation("J", "M", 1));
        relations.add(new Relation("J", "N", 1));
        relations.add(new Relation("N", "M", 2));
        relations.add(new Relation("K", "L", 4));
        relations.add(new Relation("K", "O", 5));
        relations.add(new Relation("L", "O", 1));
        relations.add(new Relation("M", "O", 2));

        var search = new SearchList("A", "M", relations, true);

        var total = 0;

        for(Relation r : search.run())
        {
            System.out.println(r.toString());
            total += r.Cost;
        }

        System.out.println(String.format("Total: %d",total));
    }
}
