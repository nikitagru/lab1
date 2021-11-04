import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("in.txt");
        Graph graph = new Graph(file);

        Graph newGraph = yarnik(graph);
        printResult(newGraph);
    }

    private static Graph yarnik(Graph graph) {
        int firstNodeIndex = (int) (Math.random() * graph.getNodes().length);
        Node node = graph.getNodes()[0];

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
