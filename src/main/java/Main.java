import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("In3.txt");
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


        while (nodes.size() < graph.getNodes().length) {
            List<Edge> nodeEdges;

            nodeEdges = graph.getEdges().stream()
                    .filter(x -> (nodes.contains(x.getPrevious()) && !nodes.contains(x.getNext()))
                            || (!nodes.contains(x.getPrevious()) && nodes.contains(x.getNext()))).collect(Collectors.toList());


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

            }
        }

        newGraph.setNodes(nodes.toArray(Node[]::new));
        newGraph.setEdges(edges);

        return newGraph;
    }

    private static void printResult(Graph graph) {
        try(FileWriter writer = new FileWriter("out.txt", false)) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < graph.getNodes().length; i++) {
                sb.append(graph.getNodes()[i].getName() + " ");
                int finalI = i;
                Node currentNode = graph.getNodes()[i];

                List<Edge> edgesFromCurrent = graph.getEdges().stream().filter(x -> x.getPrevious() == currentNode).collect(Collectors.toList());

                for (int j = 0; j < edgesFromCurrent.size(); j++) {
                    sb.append(edgesFromCurrent.get(j).getNext().getName() + " ");
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
