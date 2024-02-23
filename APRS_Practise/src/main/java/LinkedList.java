public class LinkedList {

    public class Node {
        Node next;
        int data;

        public Node() {
            this.next = null;
        }

        public void setData(int d) {
            this.data = d;
        }
    }

    Node first = new Node();

    public LinkedList() {
        first = null;
    }

    public void insert(LinkedList l, int data) {
        Node n = l.first;
        Node in = new Node();
        in.data = data;

        while (n.next != null) {
            n = n.next;
        }

        n.next = in;
    }
}
