package src;

import java.util.ArrayList;

public class Students {
    
    private class Node {
        public Node next;
        public BinaryTree info;

        public Node(BinaryTree info) {
            this.info = info;
            this.next = null;
        }
    }

    private Node first;

    public Students() {
        this.first = null;
    }

    public void addStudent(BinaryTree nouEstudiant) {
        if (nouEstudiant == null || nouEstudiant.getName() == null) return;

        Node newNode = new Node(nouEstudiant);
        String newStudentName = nouEstudiant.getName();

        if (first == null || first.info.getName().compareTo(newStudentName) > 0) {
            newNode.next = first;
            first = newNode;
        } else {
            Node current = first;
            while (current.next != null && current.next.info.getName().compareTo(newStudentName) < 0) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
    }

    public void removeStudent(String name) {
        if (first == null) return;

        if (first.info.getName().equals(name)) {
            first = first.next;
        } else {
            Node current = first;
            while (current.next != null && !current.next.info.getName().equals(name)) {
                current = current.next;
            }
            if (current.next != null) {
                current.next = current.next.next;
            }
        }
    }

    public BinaryTree getStudent(String name) {
        if (first == null) return null;

        Node current = first;
        while (current != null) {
            if (current.info.getName().equals(name)) {
                return current.info;
            }
            current = current.next;
        }
        return null;
    }

    public ArrayList<String> getAllStudentsName() {
        ArrayList<String> names = new ArrayList<>();

        if (first == null) return null;

        Node current = first;
        while (current != null) {
            names.add(current.info.getName());
            current = current.next;
        }
        return names;
    }
}
