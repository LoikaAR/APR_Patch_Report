package LinkedList;

public class LinkedList {

    Node head;

    static class Node {
        Node next;
        Node prev;
        int data;

        Node(int d) {
            this.next = null;
            this.prev = null;
            this.data = d;
        }

        public void setData(int d) {
            this.data = d;
        }
    }

    public static LinkedList insert(LinkedList l, int data) {
        Node in = new Node(data);

        if (l.head == null) {
            l.head = in;
        } else {
            Node last = l.head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = in;
            in.prev = last;
        }
        return l;
    }

    /**
     * return index of target in the linked list
     *
     * @param target the target
     * @param l the list
     * @return index of target
     */
    public int getIndex(LinkedList l, int target) {
        Node hold = l.head;
        int idx = 0;
        while (hold != null) {
            if (hold.data == target) {
                break;
            } else {
                idx++;
                hold = hold.next;
            }
        }
        if (hold == null) {
            return -1;
        }
        return idx;
    }

    /**
     * return how many times target appears in list l
     * @param l the list
     * @param target the target int
     * @return count
     */
    public int getCount(LinkedList l, int target) {
        Node n = l.head;
        int count = 0;
        while (n != null) {
            if (n.data == target) {
                count++;
            }
            n = n.next;
        }
        return count;
    }

    /**
     * sort LL
     * @param l the list for sorting
     * @return sorted list
     */
    public LinkedList sort(LinkedList l) {
        return l;
    }

    /**
     * print the whole list
     * @param list
     */
    public void printList(LinkedList list) {
        Node current = list.head;

        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}
