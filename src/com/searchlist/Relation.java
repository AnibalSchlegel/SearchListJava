package com.searchlist;

public class Relation
{
    public Node SourceNode;
    public Node TargetNode;
    public int Cost;

    public Relation(String source, String target, int cost) {
        this.SourceNode = new Node(source);
        this.TargetNode = new Node(target);
        this.Cost = cost;
    }

    private Relation(Node source, Node target, int cost) {
        this.SourceNode = source;
        this.TargetNode = target;
        this.Cost = cost;
    }

    public Relation copy() {
        return new Relation(this.SourceNode.copy(),this.TargetNode.copy(),this.Cost);
    }

    public boolean isEquals(Relation other) {
       return this.SourceNode.Name.equals(other.SourceNode.Name) && this.TargetNode.Name.equals(other.TargetNode.Name);
    }

    public boolean inverseEquals(Relation other) {
        return this.SourceNode.Name.equals(other.TargetNode.Name) && this.TargetNode.Name.equals(other.SourceNode.Name);
    }

    public Relation getInverse(){
        return new Relation(this.TargetNode.copy(), this.SourceNode.copy(), this.Cost);
    }

    @Override
    public String toString() {
        return String.format("{%s}->{%s} ({%d})", this.SourceNode.Name, this.TargetNode.Name, this.Cost);
    }
}
