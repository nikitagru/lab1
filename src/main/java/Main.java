import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();
        Node node5 = new Node();

        node1.setName("1");
        node2.setName("2");
        node3.setName("3");
        node4.setName("4");
        node5.setName("5");

        List<Node> node1Neigh = new ArrayList<>();
        node1Neigh.add(node2);
        node1Neigh.add(node3);
        node1.setNeighbors(node1Neigh);

        List<Node> node2Neigh = new ArrayList<>();
        node2Neigh.add(node1);
        node2Neigh.add(node3);
        node2.setNeighbors(node2Neigh);

        List<Node> node3Neigh = new ArrayList<>();
        node3Neigh.add(node1);
        node3Neigh.add(node4);
        node3.setNeighbors(node3Neigh);

        List<Node> node4Neigh = new ArrayList<>();
        node4Neigh.add(node3);
        node4.setNeighbors(node4Neigh);

        Edge edge1 = new Edge(node1, node2, 25);
        Edge edge2 = new Edge(node1, node3, 4);
        Edge edge3 = new Edge(node2, node3, 0);
        Edge edge4 = new Edge(node3, node4, 7);

        List<Edge> edges = new ArrayList<>();
        edges.add(edge1);
        edges.add(edge2);
        edges.add(edge3);
        edges.add(edge4);

        graph.setEdges(edges);
        graph.setNodes(new Node[] {node1, node2, node3, node4, node5});

        Graph newGraph = yarnik(graph);
        printResult(newGraph);
    }

    private static Graph yarnik(Graph graph) {
        int firstNodeIndex = (int) (Math.random() * graph.getNodes().length);
        Node node = graph.getNodes()[1];

        Graph newGraph = new Graph();
        List<Edge> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        nodes.add(node);


        for (int i = 1; i < graph.getNodes().length; i++) {
            List<Edge> nodeEdges = new ArrayList<>();
            for (int k = 0; k < graph.getEdges().size(); k++) {
                Edge edge = graph.getEdges().get(k);
                if ((nodes.contains(edge.getPrevious()) && !nodes.contains(edge.getNext()))
                        || (!nodes.contains(edge.getPrevious()) && nodes.contains(edge.getNext()))) {
                    nodeEdges.add(edge);
                }
            }


            if (nodeEdges.size() != 0) {
                Edge minEdge = nodeEdges.stream().min(Comparator.comparingInt(Edge::getWeight)).get();
                Node nextNode;
                if (minEdge.getNext().equals(node)) {
                    nextNode = minEdge.getPrevious();
                } else {
                    nextNode = minEdge.getNext();
                }
                nodes.add(nextNode);
                edges.add(minEdge);
                node = nextNode;

            } else {
                for (int j = 0; j < graph.getNodes().length; j++) {
                    node = graph.getNodes()[j];
                    if (!nodes.contains(node)) {
                        break;
                    }
                }
            }
        }

        newGraph.setNodes(nodes.toArray(Node[]::new));
        newGraph.setEdges(edges);

        return newGraph;
    }

    private static void printResult(Graph graph) {
        try(FileWriter writer = new FileWriter("out.txt", true)) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < graph.getNodes().length; i++) {
                sb.append(graph.getNodes()[i].getName() + " ");
                TreeSet<Node> nodeTreeSet = new TreeSet<>();
                for (int j = 0; j < graph.getNodes()[i].getNeighbors().size(); j++) {
                    nodeTreeSet.add(graph.getNodes()[i].getNeighbors().get(j));
                }

                for (int j = 0; j < nodeTreeSet.size(); j++) {
                    sb.append(nodeTreeSet.pollFirst().getName() + " ");
                }
                sb.append("0" + "\n");
            }
            writer.append(sb.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
