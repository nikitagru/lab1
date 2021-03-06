import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private Node from;
    private Node to;

    public Graph(File file) throws IOException {
        BufferedReader bfReader = new BufferedReader(new FileReader(file));

        int count = Integer.parseInt(bfReader.readLine());

        String[] numbers;
        String line = bfReader.readLine();

        StringBuffer sb = new StringBuffer();

        while (line != null) {
            sb.append(line + " ");
            line = bfReader.readLine();
        }

        String numbersString = sb.toString();
        numbersString = numbersString.replaceAll("\n", " ");
        numbersString = numbersString.replaceAll("32767", "");
        numbers = numbersString.split(" ");

        int nodeCounter = 1;

        for (int i = 0; i < numbers.length; i++) {
            if (Integer.parseInt(numbers[i]) == count) {
                break;
            }
            int link = Integer.parseInt(numbers[i]);
            int gap = Integer.parseInt(numbers[i + 1]) - link;
            for (int j = 0; j < gap; j = j + 2) {
                if (nodes.size() < nodeCounter) {
                    Node node = new Node(String.valueOf(nodeCounter));
                    nodes.add(node);
                }
                int neighbourNum = Integer.parseInt(numbers[Integer.parseInt(numbers[i]) - 1 + j]);

                if (getNodeByName(String.valueOf(neighbourNum)) == null) {
                    Node node = new Node(numbers[Integer.parseInt(numbers[i]) - 1 + j]);
                    getNodeByName(String.valueOf(nodeCounter)).addNeighbors(node);
                    nodes.add(node);
                } else {
                    getNodeByName(String.valueOf(nodeCounter)).addNeighbors(getNodeByName(String.valueOf(neighbourNum)));
                }

                int finalNodeCounter = nodeCounter;

                Edge edge1 = edges.stream()
                        .filter(x -> (x.getPrevious().equals(getNodeByName(String.valueOf(finalNodeCounter)))
                            && x.getNext().equals(getNodeByName(String.valueOf(neighbourNum))))
                        || (x.getPrevious().equals(getNodeByName(String.valueOf(neighbourNum)))
                            && x.getNext().equals(getNodeByName(String.valueOf(finalNodeCounter)))))
                            .findFirst().orElse(null);

                if (edge1 == null) {
                    Edge edge = new Edge(getNodeByName(String.valueOf(nodeCounter)), getNodeByName(String.valueOf(neighbourNum)),
                            Integer.parseInt(numbers[Integer.parseInt(numbers[i]) + j]));

                    edges.add(edge);
                }
            }

            nodeCounter++;
        }
    }

    public Graph() {

    }

    public Node[] getNodes() {
        return nodes.toArray(Node[]::new);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    private Node getNodeByName(String name) {
        System.out.println(name);
        return nodes.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = Arrays.stream(nodes).collect(Collectors.toList());
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public void setTo(Node to) {
        this.to = to;
    }
}
