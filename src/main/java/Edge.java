public class Edge {
    private Node previous;
    private Node next;
    private int weight;

    public Edge(Node previous, Node next, int weight) {
        this.previous = previous;
        this.next = next;
        this.weight = weight;
    }

    public Node getPrevious() {
        return previous;
    }

    public Node getNext() {
        return next;
    }

    public int getWeight() {
        return weight;
    }
}
