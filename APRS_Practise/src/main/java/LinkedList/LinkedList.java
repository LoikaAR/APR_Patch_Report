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
     * Swap ptr1 and ptr2
     * @param ptr1 pointer to node 1
     * @param ptr2 pointer to node 2
     */
    public static void swap(Node ptr1, Node ptr2) {
        int tmp = ptr2.data;
        ptr2.data = ptr1.data;
        ptr1.data = tmp;
    }

    /**
     *  sort the LL
     * @param head the head of LL
     */
    public static void bubbleSort(Node head) {
        boolean swapped;
        Node current;

        if (head == null)
            return;

        do {
            swapped = false;
            current = head;

            while (current.next != null) {
                if (current.data > current.next.data) {
                    swap(current, current.next);
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    /**
     * print the whole list
     * @param list the list
     */
    public void printList(LinkedList list) {
        Node current = list.head;

        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}

// 1) write binary search, make an error
// 2) write two test cases, pass and fail
// 3) get state of the program before patch: CFG, vars, valuables - using dynamic analysis framework - JaCoCo, opentelemetery
// 4) make patch - manually
// 5) get state of the program after the patch