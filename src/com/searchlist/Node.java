package com.searchlist;

public class Node {
    public int Weight = 0;
    public int TotalWeight = 0;
    public String Name;

    public Node(String name)
    {
        this.Name = name;
    }

    private Node(String name, int totalWeight, int weight){
        this.Name = name;
        this.TotalWeight = totalWeight;
        this.Weight = weight;
    }

    public Node copy() {
        return new Node(this.Name, this.TotalWeight, this.Weight);
    }
}
