package src;

import java.io.BufferedReader;
import java.io.FileReader;
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
    }

    public NodeA arrel;

    public BinaryTree() {
        this.arrel = null;
    }

    public BinaryTree(String filename) {

        /*
         * Aquest constructor carrega un arbre binari des d'un fitxer. Llegeix el fitxer
         * on l'arbre es va desar prèviament en format preordre i recrea l'arbre binari
         * a partir de la informació desada. S’invoca el mètode privat
         * preorderLoad(BufferedReader bur).
         */

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

    public void addNode(Person unaPersona, String level) {
        // Add a node to the binary tree
        /*
         * Aquest mètode públic afegeix un nou node a l'arbre, cridant al mètode
         * recursiu intern addNodeRecursive. El paràmetre level indica la posició del
         * nou node dins l'arbre, seguint el mateix format de “L” per esquerra i “R” per
         * dreta. Per exemple, alguns valors vàlids serien: 'L', 'R', 'LL', 'LR', 'RL',
         * 'RR'.
         */

        if (arrel == null)
            arrel = new NodeA(unaPersona);
        else
            addNodeRecursive(arrel, unaPersona, level, 0);
    }

    private void addNodeRecursive(NodeA current, Person unaPersona, String level, int index) {
        // Si estem al final de la cadena level, afegir el node a la posició corresponent
        if (index == level.length() - 1) {
            if (level.charAt(index) == 'L') {
                if (current.esq == null) {
                    current.esq = new NodeA(unaPersona);
                } else {
                    throw new IllegalArgumentException("The node in the left position already exists");
                }
            } else if (level.charAt(index) == 'R') {
                if (current.dret == null) {
                    current.dret = new NodeA(unaPersona);
                } else {
                    throw new IllegalArgumentException("The node in the right position already exists");
                }
            }
        } else {
            // Avancem a l'arbre fins a la posició on s'ha de crear el nou node
            if (level.charAt(index) == 'L') {
                if (current.esq == null) {
                    current.esq = new NodeA(null); // Crear un node intermedio si no existeix
                }
                addNodeRecursive(current.esq, unaPersona, level, index + 1);
            } else if (level.charAt(index) == 'R') {
                if (current.dret == null) {
                    current.dret = new NodeA(null); 
                }
                addNodeRecursive(current.dret, unaPersona, level, index + 1);
            }
        }
    }

    public void displayTree() {
        // Display the binary tree
        /*
         * Aquest mètode mostra l'arbre binari de manera estructurada a la consola,
         * cridant el mètode recursiu displayTreeRecursive. Comença el recorregut des de
         * l'arrel de l'arbre.
         */
    }

    public void preorderSave() {
        /*
         * Aquest mètode desa l'arbre binari en format preordre a un fitxer de text. Si
         * l'arbre és buit, llança una excepció. El nom del fitxer és derivat del nom de
         * la persona emmagatzemada a l'arrel de l'arbre. Crida al mètode recursiu
         * preorderSaveRecursive per guardar cada node a una línia, on s’afegeix la
         * marca “;” per saber si no hi ha node esquerra o dreta. Us recomano veure el
         * fitxer Nicol.txt.
         */
    }

    public void removePerson(String name) {

    }
}
