/**
 * Aidan Kiser (ark0053)
 * 8 November 2023
 * COMP 3270
 * Programming Assignment 2
**/

import java.util.*;

public class ProgrammingAssignment2 {

    private static class Node {
        String id;
        List<Node> neighbors;

        Node(String id) {
            this.id = id;
            this.neighbors = new ArrayList<>();
        }

        void addNeighbor(Node neighbor) {
            this.neighbors.add(neighbor);
        }
    }

    private Map<String, Node> graph;

    public ProgrammingAssignment2() {
        this.graph = new HashMap<>();
    }

    public void addEdge(String edge) {
        String[] nodes = edge.split(",");
        String node1 = nodes[0];
        String node2 = nodes[1];

        if (!graph.containsKey(node1)) {
            graph.put(node1, new Node(node1));
        }

        if (!graph.containsKey(node2)) {
            graph.put(node2, new Node(node2));
        }

        graph.get(node1).addNeighbor(graph.get(node2));
        graph.get(node2).addNeighbor(graph.get(node1));
    }

    public void bfs(String source) {
        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, Integer> distance = new HashMap<>();
        Map<String, Long> time = new HashMap<>();

        Node sourceNode = graph.get(source);
        queue.add(sourceNode);
        visited.add(sourceNode.id);
        distance.put(sourceNode.id, 0);
        time.put(sourceNode.id, System.nanoTime());

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            for (Node neighbor : current.neighbors) {
                if (!visited.contains(neighbor.id)) {
                    queue.add(neighbor);
                    visited.add(neighbor.id);
                    distance.put(neighbor.id, distance.get(current.id) + 1);
                    time.put(neighbor.id, System.nanoTime());
                }
            }
        }

        printResults("BFS", distance, time);
    }

    public void dfs(String source) {
        Set<String> visited = new HashSet<>();
        Map<String, Integer> distance = new HashMap<>();
        Map<String, Long> time = new HashMap<>();

        Node sourceNode = graph.get(source);
        dfsHelper(sourceNode, visited, distance, time, 0);

        printResults("DFS", distance, time);
    }

    private void dfsHelper(Node node, Set<String> visited, Map<String, Integer> distance, Map<String, Long> time, int currentTime) {
        visited.add(node.id);
        distance.put(node.id, currentTime);
        time.put(node.id, System.nanoTime());

        for (Node neighbor : node.neighbors) {
            if (!visited.contains(neighbor.id)) {
                dfsHelper(neighbor, visited, distance, time, currentTime + 1);
            }
        }
    }

    private void printResults(String algorithm, Map<String, Integer> distance, Map<String, Long> time) {
        System.out.println("Results for " + algorithm + ":");
        for (Map.Entry<String, Integer> entry : distance.entrySet()) {
            System.out.println("Node " + entry.getKey() + " - Distance: " + entry.getValue() + ", Time: " + (time.get(entry.getKey()) - time.get("N_0")));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ProgrammingAssignment2 analyzer = new ProgrammingAssignment2();

        // Replace this path with the actual path to your edge list file
        String filePath = "/Users/aidankiser/Documents/Fall 2023 Classes/Intro to Algorithms/Programming Assignment 2/Test_Case_Assignment2.txt";

        try (Scanner scanner = new Scanner(new java.io.File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                analyzer.addEdge(line);
            }
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }

        analyzer.bfs("N_0");
        analyzer.dfs("N_0");
    }
}