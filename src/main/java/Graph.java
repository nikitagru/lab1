import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {
    private Node[] nodes;
    private List<Edge> edges = new ArrayList<>();
    private Node from;
    private Node to;

    public Graph(File file) throws IOException {
        BufferedReader bfReader = new BufferedReader(new FileReader(file));

        String line = bfReader.readLine();
        nodes = new Node[Integer.parseInt(line)];

        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(String.valueOf(i + 1));
        }

        for (int i = 0; i < nodes.length; i++) {
            line = bfReader.readLine();
            for (int j = 0; j < nodes.length; j++) {
                String[] inputs = line.split("\\s+");
                if (Integer.parseInt(inputs[j]) != -32768) {
                    int finalJ = j + 1;
                    Node neighbor = Arrays.stream(nodes).filter(x -> x.getName().equals(String.valueOf(finalJ))).findFirst().get();
                    nodes[i].addNeighbors(neighbor);
                    edges.add(new Edge(nodes[i], neighbor, Integer.parseInt(inputs[j])));
                }
            }
        }

        createDest(bfReader);
    }

    public Graph() {

    }

    private void createDest(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        String finalLine1 = line;
        this.from = Arrays.stream(nodes).filter(x -> x.getName().equals(finalLine1)).findFirst().get();
        line = reader.readLine();
        String finalLine = line;
        this.to = Arrays.stream(nodes).filter(x -> x.getName().equals(finalLine)).findFirst().get();
    }

    public Node[] getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
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
