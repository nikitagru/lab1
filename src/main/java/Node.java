import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node> {
    private String name;
    private List<Node> neighbors;

    public Node(String name) {
        this.name = name;
        neighbors = new ArrayList<>();
    }

    public void addNeighbors(Node neighbor) {
        this.neighbors.add(neighbor);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Node> neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name) && Objects.equals(neighbors, node.neighbors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, neighbors);
    }

    @Override
    public int compareTo(Node o) {
        return Integer.parseInt(name) - Integer.parseInt(o.getName());
    }
}
