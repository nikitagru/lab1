import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();

        node1.setName("1");
        node2.setName("2");
        node3.setName("3");
        node4.setName("4");

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
        graph.setNodes(new Node[] {node1, node2, node3, node4});

        yarnik(graph);
    }

    private static void yarnik(Graph graph) {
        int firstNodeIndex = (int) (Math.random() * 4);
        Node node = graph.getNodes()[firstNodeIndex];

        Graph newGraph = new Graph();
        List<Edge> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        nodes.add(node);


        for (int i = 1; i < graph.getNodes().length; i++) {
            List<Edge> nodeEdges;
            Node finalNode = node;
            nodeEdges = graph.getEdges().stream().filter(x -> x.getPrevious().equals(finalNode)).collect(Collectors.toList());

            if (nodeEdges.size() > 0) {
                Edge minEdge = nodeEdges.stream().min(Comparator.comparingInt(Edge::getWeight)).get();
                nodes.add(minEdge.getNext());
                edges.add(minEdge);
                node = minEdge.getNext();
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


        System.out.println();
    }
}
