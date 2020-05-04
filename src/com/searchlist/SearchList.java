package com.searchlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SearchList {
    String StartNode;
    String EndNode;
    HashMap<String, List<Relation>> SearchTree = new HashMap<String, List<Relation>>();

    public SearchList(String startNode, String endNode, List<Relation> relations, boolean twoWays)
    {
        this.StartNode = startNode;
        this.EndNode = endNode;

        for (Relation relation : relations) {
            buildSearchTree(relation);

            if (twoWays)
                buildSearchTree(relation.getInverse());
        }
    }

    private void buildSearchTree(Relation relation)
    {
        if (!relation.TargetNode.Name.equals(this.StartNode) && !relation.SourceNode.Name.equals(this.EndNode))
        {
            if (!SearchTree.containsKey(relation.SourceNode.Name))
                SearchTree.put(relation.SourceNode.Name, new ArrayList<Relation>());

            if (!SearchTree.containsKey(relation.TargetNode.Name))
                SearchTree.put(relation.TargetNode.Name, new ArrayList<Relation>());

            SearchTree.get(relation.SourceNode.Name).add(relation);
        }
    }

    public List<Relation> run()
    {
        List<Relation> openList = new ArrayList<Relation>();
        List<Relation> closedList = new ArrayList<Relation>();
        Relation lowestWeight;
        Relation temp;
        Node lastClosed = null;

        for (int j = 0; j < SearchTree.get(this.StartNode).size(); j++)
        {
            temp = SearchTree.get(this.StartNode).get(j).copy();
            temp.TargetNode.Weight = temp.Cost;
            temp.TargetNode.TotalWeight = temp.TargetNode.Weight;
            openList.add(temp);
        }

        while (notFinished(closedList))
        {
            updateOpenList(openList, getNextOpenNodes(lastClosed, closedList, openList));
            lowestWeight = extractLowestWeightedNode(openList);
            updateClosedList(closedList, lowestWeight);
            lastClosed = lowestWeight.TargetNode;
        }

        return buildPath(closedList);
    }

    private boolean notFinished(List<Relation> closedList)
    {
        for (int j = 0; j < closedList.size(); j++)
        {
            if (this.EndNode.equals(closedList.get(j).TargetNode.Name))
                return false;
        }
        return true;
    }

    private void updateOpenList(List<Relation> openList, List<Relation> newNodes)
    {
        boolean updated;

        for (int i = 0; i < newNodes.size(); i++)
        {
            updated = false;
            for (int j = openList.size() - 1; j >= 0; j--)
            {
                if (newNodes.get(i).TargetNode.Name.equals(openList.get(j).TargetNode.Name))
                {
                    if (newNodes.get(i).TargetNode.TotalWeight < openList.get(j).TargetNode.TotalWeight)
                    {
                        openList.set(j ,newNodes.get(i));
                        updated = true;
                    }
                    break;
                }
            }
            if (!updated)
                openList.add(newNodes.get(i));
        }
    }

    private void updateClosedList(List<Relation> closedList, Relation lowestWeight)
    {
        for (int i = 0; i < closedList.size(); i++)
        {
            if (closedList.get(i).TargetNode.Name == lowestWeight.TargetNode.Name)
            {
                if (closedList.get(i).TargetNode.TotalWeight > lowestWeight.TargetNode.TotalWeight)
                    closedList.set(i, lowestWeight.copy());
                return;
            }
        }

        closedList.add(lowestWeight.copy());
    }

    private List<Relation> buildPath(List<Relation> closedList)
    {
        if (closedList.isEmpty()) return new ArrayList<Relation>();

        List<Relation> finalPath = new ArrayList<Relation>();

        Relation last = findLastNodeInPath(closedList);

        if (last == null)
            return new ArrayList<Relation>();
        else
        {
            finalPath.add(last);

            if (last.SourceNode.Name == this.StartNode)
                return finalPath;

            closedList.remove(last);

            for (int i = closedList.size() - 1; i >= 0; i--)
            {
                if (finalPath.get(finalPath.size() - 1).SourceNode.Name.equals(closedList.get(i).TargetNode.Name))
                    finalPath.add(closedList.get(i));
            }

            Collections.reverse(finalPath);

            return finalPath;
        }
    }

    private Relation findLastNodeInPath(List<Relation> closedList)
    {
        int minWeight = Integer.MAX_VALUE;
        Relation relation = null;

        for (int i = 0; i < closedList.size(); i++)
        {
            if (closedList.get(i).TargetNode.Name.equals(this.EndNode) && closedList.get(i).TargetNode.TotalWeight < minWeight)
            {
                minWeight = closedList.get(i).TargetNode.TotalWeight;
                relation= closedList.get(i);
            }
        }

        return relation;
    }

    private Relation extractLowestWeightedNode(List<Relation> openList)
    {
        int minWeight = Integer.MAX_VALUE;
        Relation relation = null;

        for (int i = 0; i < openList.size(); i++)
        {
            if (openList.get(i).TargetNode.TotalWeight < minWeight)
            {
                minWeight = openList.get(i).TargetNode.TotalWeight;
                relation = openList.get(i);
            }
        }

        if (relation != null)
        {
            Relation cloned = relation.copy();
            openList.remove(relation);

            return cloned;
        }
        else return null;
    }

    private List<Relation> getNextOpenNodes(Node lastClosed, List<Relation> closedList, List<Relation> openList)
    {
        List<Relation> newOpens = new ArrayList<Relation>();

        if (lastClosed != null)
        {
            for (int i = 0; i < SearchTree.get(lastClosed.Name).size(); i++)
            {
                final Relation temp = SearchTree.get(lastClosed.Name).get(i).copy();

                if (closedList.stream().anyMatch(x -> x.inverseEquals(temp)) || openList.stream().anyMatch(x -> x.isEquals(temp)))
                    continue;

                temp.TargetNode.Weight = temp.Cost;
                temp.TargetNode.TotalWeight = temp.TargetNode.Weight + lastClosed.TotalWeight;
                newOpens.add(temp);
            }
        }

        return newOpens;
    }

}
