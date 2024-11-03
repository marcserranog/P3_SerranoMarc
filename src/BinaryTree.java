package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BinaryTree {
    public static class NodeA {
        public Person inf;
        public NodeA esq;
        public NodeA dret;

        public NodeA(Person inf) {
            this.inf = inf;
            this.esq = null;
            this.dret = null;
        }

        public NodeA(Person inf, NodeA esq, NodeA dret) {
            this.inf = inf;
            this.esq = esq;
            this.dret = dret;
        }

        public void displaySubtree(int depth) {
            for (int i = 0; i < depth; i++) {
                System.out.print("   "); // Mostrar espais per representar la profunditat de l'arbre
            }
            System.out.println(inf != null ? inf.getName() : "(null)");

            if (esq != null)
                esq.displaySubtree(depth + 1);
            if (dret != null)
                dret.displaySubtree(depth + 1);
        }

        public void preorderSaveRecursive(BufferedWriter writer) throws IOException {
            if (inf != null) {
                writer.write(inf.toString());
                writer.newLine();
            } else {
                writer.write("null");
                writer.newLine();
            }

            if (esq != null) {
                esq.preorderSaveRecursive(writer);
            } else {
                writer.write(";");
                writer.newLine();
            }

            if (dret != null) {
                dret.preorderSaveRecursive(writer);
            } else {
                writer.write(";");
                writer.newLine();
            }
        }

        public NodeA findMin() {
            NodeA current = this;
            while (current.esq != null) {
                current = current.esq;
            }
            return current;
        }

        private boolean isDescentFromRecursive(NodeA node, String place) {
            if (node == null) {
                return false;
            }
            if (node.inf.getPlaceOfOrigin().equalsIgnoreCase(place)) {
                return true;
            }
            return isDescentFromRecursive(node.esq, place) || isDescentFromRecursive(node.dret, place);
        }

        public boolean addNodeRecursive(Person unaPersona, String level, int index) {
            if (index == level.length() - 1) {
                if (level.charAt(index) == 'L') {
                    if (this.esq == null) {
                        this.esq = new NodeA(unaPersona);
                        return true;
                    } else {
                        throw new IllegalArgumentException("The node in the left position already exists");
                    }
                } else if (level.charAt(index) == 'R') {
                    if (this.dret == null) {
                        this.dret = new NodeA(unaPersona);
                        return true;
                    } else {
                        throw new IllegalArgumentException("The node in the right position already exists");
                    }
                }
            } else {
                if (level.charAt(index) == 'L') {
                    if (this.esq == null) {
                        this.esq = new NodeA(null);
                    }
                    return this.esq.addNodeRecursive(unaPersona, level, index + 1);
                } else if (level.charAt(index) == 'R') {
                    if (this.dret == null) {
                        this.dret = new NodeA(null);
                    }
                    return this.dret.addNodeRecursive(unaPersona, level, index + 1);
                }
            }
            return false;
        }

        public int countNodesRecursive() {
            int count = 1;
            if (esq != null)
                count += esq.countNodesRecursive();
            if (dret != null)
                count += dret.countNodesRecursive();
            return count;
        }
    }

    public NodeA arrel;

    public BinaryTree() {
        this.arrel = null;
    }

    public BinaryTree(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            this.arrel = preorderLoad(br);
        } catch (IOException e) {
            this.arrel = null;
        }
    }

    private NodeA preorderLoad(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null || line.equals("null")) {
            return null;
        }
        Person person = new Person(line);
        NodeA node = new NodeA(person);
        node.esq = preorderLoad(br);
        node.dret = preorderLoad(br);
        return node;
    }

    public String getName() {
        return this.arrel.inf.getName();
    }

    public boolean addNode(Person unaPersona, String level) {
        if (arrel == null) {
            arrel = new NodeA(unaPersona);
            return true;
        }
        return arrel.addNodeRecursive(unaPersona, level, 0);
    }

    public void displayTree() {
        if (arrel != null) {
            arrel.displaySubtree(0);
        } else {
            System.out.println("(Empty tree)");
        }
    }

    public void preorderSave() {
        if (arrel == null || arrel.inf == null) {
            throw new IllegalStateException("L'arbre esta buit i no es pot guardar.");
        }
        String filename = arrel.inf.getName() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            arrel.preorderSaveRecursive(writer);
            System.out.println("Arbre guardat al fitxer: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removePerson(String name) {
        if (arrel == null) {
            System.out.println("El árbol está vacío.");
        } else if (arrel.inf.getName().equals(name)) {
            System.out.println("No se puede eliminar la raíz del árbol.");
        } else {
            arrel = removePersonRecursive(arrel, name);
        }
    }

    private NodeA removePersonRecursive(NodeA node, String name) {
        if (node == null) {
            return null;
        }
        if (name.compareTo(node.inf.getName()) < 0) {
            node.esq = removePersonRecursive(node.esq, name);
        } else if (name.compareTo(node.inf.getName()) > 0) {
            node.dret = removePersonRecursive(node.dret, name);
        } else {
            if (node.esq == null && node.dret == null) {
                return null;
            } else if (node.esq == null) {
                return node.dret;
            } else if (node.dret == null) {
                return node.esq;
            } else {
                NodeA minNode = node.dret.findMin();
                node.inf = minNode.inf;
                node.dret = removePersonRecursive(node.dret, minNode.inf.getName());
            }
        }
        return node;
    }

    public boolean isFrom(String place) {
        if (arrel != null && arrel.inf != null) {
            return arrel.inf.getPlaceOfOrigin().equalsIgnoreCase(place);
        }
        return false;
    }

    public boolean isDescentFrom(String place) {
        return arrel != null && arrel.isDescentFromRecursive(arrel, place);
    }

    public int howManyParents() {
        int count = 0;
        if (arrel != null) {
            if (arrel.esq != null)
                count++;
            if (arrel.dret != null)
                count++;
        }
        return count;
    }

    public int howManyGrandParents() {
        int count = 0;
        if (arrel != null) {
            if (arrel.esq != null) {
                count += arrel.esq.countNodesRecursive();
            }
            if (arrel.dret != null) {
                count += arrel.dret.countNodesRecursive();
            }
        }
        return count;
    }

    public boolean marriedParents() {
        if (arrel == null)
            return false;
        boolean leftMarried = arrel.esq != null && arrel.esq.inf.getMaritalStatus() == Person.MARRIED;
        boolean rightMarried = arrel.dret != null && arrel.dret.inf.getMaritalStatus() == Person.MARRIED;
        return leftMarried && rightMarried;
    }
}
