package com.rpgroyale.combatserver.utils;

import com.rpgroyale.combatserver.entities.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathFindingUtil {
    // pathfinding variables
    private ArrayList closed = new ArrayList();
    private SortedList open = new SortedList();
    private int maxSearchDistance;
    private Node[][] nodes;
    private Boolean[][] visited;
    private Boolean allowDiagonalMovement;
    private CombatGrid combatGrid;

    public PathFindingUtil(CombatGrid combatGrid) {

        instance = this;
        this.combatGrid = combatGrid;
    }



    public static PathFindingUtil instance;

    public static float getDistance(int startX, int startY, int endX, int endY)
    {
        float distX = endX - startX;
        float distY = endY - startY;

        float result = (float)(Math.sqrt((distX * distX) + (distY * distY)));

        return result;
    }

    // the goal here is to move away from the target (i.e. casters and ranged attackers)
    public GridLocation findPathAway(int locX, int locY, int targetX, int targetY) {

        // look at each spot around and pick the one that is farthest away
        // todo: also prefer spots with an ally in an adjacent spot
        List<Node> neighborNodes = new ArrayList<>();
        for(int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Node neighborNode = new Node(locX + x, locY + y);
                if (isValidLocation(neighborNode.x, neighborNode.y)) {
                    GridLocation neigborLocation = combatGrid.getLocationAt(neighborNode.x, neighborNode.y);
                    //current tile or tile is occupied, skip it as a destination
                    if (((x == 0) && (y == 0)) || neigborLocation.isOccupied) {
                        neighborNode.cost = 0;
                        continue;
                    }

                    neighborNode.cost = PathFindingUtil.getDistance(neighborNode.x, neighborNode.y, targetX, targetY);
                    neighborNodes.add(neighborNode);
                }
            }
        }

        Collections.sort(neighborNodes);
        Collections.reverse(neighborNodes);

        Node nodeToMoveTo = neighborNodes.get(0);
        GridLocation locationToMoveTo = combatGrid.getLocationAt(nodeToMoveTo.x, nodeToMoveTo.y);

        return locationToMoveTo;
    }

    public GridLocation findPathTowards(CombatUnit source, CombatUnit target) {
        // look at each spot around and pick the one that is farthest away
        // todo: also prefer spots with an ally in an adjacent spot
        List<Node> neighborNodes = new ArrayList<>();
        for(int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                Node neighborNode = new Node(source.location.intX + x, source.location.intY + y);

                if(isValidLocation(neighborNode.x, neighborNode.y)) {
                    GridLocation neigborLocation = combatGrid.getLocationAt(neighborNode.x, neighborNode.y);
                    //current tile or tile is occupied, skip it as a destination
                    if (((x == 0) && (y == 0)) || neigborLocation.isOccupied) {
                        neighborNode.cost = 100;
                        continue;
                    }

                    neighborNode.cost = PathFindingUtil.getDistance(neighborNode.x, neighborNode.y, target.location.intX, target.location.intY);
                    neighborNodes.add(neighborNode);
                }
            }
        }

        Collections.sort(neighborNodes);

        if (neighborNodes.size() != 0) {
            Node nodeToMoveTo = neighborNodes.get(0);
            GridLocation locationToMoveTo = combatGrid.getLocationAt(nodeToMoveTo.x, nodeToMoveTo.y);
            return locationToMoveTo;
        }

        // couldn't find a spot to move, need to stay put for now
        return null;
    }

    public Path findPath(int startX, int startY, int endX, int endY)
    {
        //initialize search routine. clear both lists, and set the
        //only node so far as the one we are in
        // initialize pathfinder variables
        this.maxSearchDistance = 20;
        this.allowDiagonalMovement = true;

        nodes = new Node[combatGrid.getWidth()][combatGrid.getHeight()];
        visited = new Boolean[combatGrid.getWidth()][combatGrid.getHeight()];
        for (int x = 0; x < combatGrid.getWidth(); x++)
        {
            for (int y = 0; y < combatGrid.getHeight(); y++)
            {
                nodes[x][y] = new Node(x, y);
                visited[x][y] = false;
            }
        }

        int index = 0;
        nodes[startX][startY].cost = 0;
        nodes[startX][startY].depth = 0;
        closed.clear();
        open.clear();
        open.add(nodes[startX][startY]);
        index++;
        nodes[endX][endY].setParent(null);

        // loop finding nodes until max search depth is reached
        int maxDepth = 0;
        while ((maxDepth < maxSearchDistance) && (open.size() != 0))
        {
            Node current = getFirstInOpen();
            if (current == nodes[endX][endY])
            {
                break;
            }

            open.remove(current);
            closed.add(current);

            // search all the neighbor nodes evaluating them as next step towards target
            for (int x = -1; x < 2; x++)
            {
                for (int y = -1; y < 2; y++)
                {
                    //current tile, skip over
                    if ((x == 0) && (y == 0))
                    {
                        continue;
                    }

                    //now allowing diagonal, then only one x or y change per move
                    if (!allowDiagonalMovement)
                    {
                        if ((x != 0) && (y != 0))
                        {
                            continue;
                        }
                    }

                    int neighborX = x + current.x;
                    int neighborY = y + current.y;

                    if (isValidLocation(neighborX, neighborY))
                    {

                        // cost is the distance between this location and the target
                        float nextStepCost = current.cost + getMovementCost(current.x, current.y);
                        Node neighbor = nodes[neighborX][neighborY];
                        visited[neighborX][neighborY] = true;

                        if (nextStepCost < neighbor.cost)
                        {
                            if(inOpenList(neighbor))
                            {
                                open.remove(neighbor);
                            }
                            if (inClosedList(neighbor))
                            {
                                closed.remove(neighbor);
                            }
                        }

                        if(!inOpenList(neighbor) && !(inClosedList(neighbor)))
                        {
                            neighbor.cost = nextStepCost;
                            neighbor.cost += PathFindingUtil.getDistance(neighborX, neighborY, endX, endY);
                            maxDepth = Math.max(maxDepth, neighbor.setParent(current));
                            open.add(neighbor);
                            index++;
                        }
                    }

                }
            }
        }

        if (nodes[endX][endY].parent == null)
        {
            return null;
        }

        Path path = new Path();
        Node target = nodes[endX][endY];
        while(target != nodes[startX][startY])
        {
            GridLocation location = new GridLocation(target.x, target.y, false, null);
            path.prependStep(location);
            target = target.parent;
        }
        GridLocation startLocation = new GridLocation(startX, startY, false, null);
        path.prependStep(startLocation);

        return path;
    }

    private float getMovementCost(int locX, int locY)
    {
        // not applicable currently
        return 1;
    }

    private Node getFirstInOpen()
    {
        return (Node)open.first();
    }

    private Boolean inOpenList(Node node)
    {
        return open.contains(node);
    }

    private Boolean inClosedList(Node node)
    {
        return closed.contains(node);
    }

    private Boolean isValidLocation(int locX, int locY)
    {
        Boolean invalid = (locX < 0) || (locY < 0) || (locX >= combatGrid.getWidth()) || (locY >= combatGrid.getHeight());
        return !invalid;
    }

    private class Node implements Comparable
    {
        /** The x coordinate of the node */
        public int x;
        /** The y coordinate of the node */
        public int y;

        /** The parent of this node, how we reached it in the search */
        public Node parent;
        /** The heuristic cost of this node */
        public float cost;
        /** The search depth of this node */
        public int depth;

        /**
         * Create a new node
         *
         * @param x The x coordinate of the node
         * @param y The y coordinate of the node
         */
        public Node(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        /**
         * Set the parent of this node
         *
         * @param parent The parent node which lead us to this node
         * @return The depth we have no reached in searching
         */
        public int setParent(Node parent)
        {
            if (parent == null) { return 0; }
            depth = parent.depth + 1;
            this.parent = parent;

            return depth;
        }

        @Override
        public int compareTo(Object obj) {
            Node o = (Node)obj;

            float f = cost;
            float of = o.cost;

            if (f < of)
            {
                return -1;
            }
            else if (f > of)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }
}
