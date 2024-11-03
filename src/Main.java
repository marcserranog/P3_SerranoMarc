package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Students students = new Students();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        loadStudentsFromFiles(students);

        while (!exit) {
            System.out.println("Menú Principal:");
            System.out.println("\t1. Mostrar llistat d'estudiants");
            System.out.println("\t2. Mostrar família d'un estudiant");
            System.out.println("\t3. Afegir un estudiant");
            System.out.println("\t4. Modificar un estudiant");
            System.out.println("\t5. Mostrar el informe");
            System.out.println("\t6. Guardar i Sortir");
            System.out.print("Tria una opció: ");
            int option = scanner.nextInt();
            scanner.nextLine();  

            switch (option) {
                case 1:
                    System.out.println("Mostrem els noms dels estudiants:");
                    ArrayList<String> studentNames = students.getAllStudentsName();
                    if (studentNames != null) {
                        for (String name : studentNames) {
                            System.out.println("\t" + name);
                        }
                    } else {
                        System.out.println("No hi ha estudiants enregistrats.");
                    }
                    break;

                case 2:
                    System.out.print("Introduce el nom de l'estudiant: ");
                    String studentName = scanner.nextLine();
                    BinaryTree studentTree = students.getStudent(studentName);
                    if (studentTree != null) {
                        System.out.println("Família de " + studentName + ":");
                        studentTree.displayTree();
                    } else {
                        System.out.println("Estudiant no trobat.");
                    }
                    break;

                case 3:
                    System.out.print("Introduce el nom de l'estudiant: ");
                    String nameToAdd = scanner.nextLine();
                    System.out.print("Introduce el lloc d'origen: ");
                    String placeOfOrigin = scanner.nextLine();
                    System.out.print("Introduce l'estat civil (Single, Married, Divorced, Widowed): ");
                    String maritalStatus = scanner.nextLine();

                    try {
                        Person newPerson = new Person(nameToAdd, placeOfOrigin, parseMaritalStatus(maritalStatus));
                        BinaryTree newStudentTree = new BinaryTree();
                        newStudentTree.addNode(newPerson, ""); // Aquí se asume que el primer nodo se añade sin nivel
                        students.addStudent(newStudentTree);
                        System.out.println("Estudiant afegit amb èxit.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error al afegir l'estudiant: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Introduce el nom de l'estudiant a modificar: ");
                    String nameToModify = scanner.nextLine();
                    break;

                case 5:
                    System.out.print("Indica la ciutat de naixement a buscar: ");
                    String birthCity = scanner.nextLine();
                    System.out.print("Indica la ciutat de procedència a buscar: ");
                    String originCity = scanner.nextLine();
                    mostrarInforme(students, birthCity, originCity);
                    break;

                case 6:
                    saveStudentsToFiles(students);
                    exit = true;
                    System.out.println("Sortint del programa.");
                    break;

                default:
                    System.out.println("Opció no vàlida. Si us plau, tria una altra opció.");
            }
        }

        scanner.close();
    }

    private static void loadStudentsFromFiles(Students students) {

        String[] studentFiles = {"Alex.txt", "Chris.txt", "Dana.txt", "Morgan.txt", "Nicol.txt", "Riley.txt"};
        
        for (String fileName : studentFiles) {

            BinaryTree studentTree = new BinaryTree("src/Files/" + fileName);
            students.addStudent(studentTree);

            System.out.println("Alumne carregat des del fitxer: " + fileName);
            System.out.println("Arbre binari estructurat:");
            studentTree.displayTree();
        }
    }

    private static void saveStudentsToFiles(Students students) {

        ArrayList<String> studentNames = students.getAllStudentsName();
        for (String name : studentNames) {
            BinaryTree studentTree = students.getStudent(name);
            if (studentTree != null) {
                studentTree.preorderSave();
                System.out.println("Alumne guardat al fitxer: " + name);
            }
        }
    }

    private static void mostrarInforme(Students students, String birthCity, String originCity) {
        int totalStudents = 0;  // Total de alumnes
        int countBirthCity = 0; // Compta alumnes de la ciutat de naixement
        int countOriginCity = 0; // Compta alumnes de la ciutat d'origen
        int countSingleParents = 0; // Compta alumnes amb un únic progenitor
        int countUnmarriedParents = 0; // Compta alumnes amb progenitors no casats
        int countGrandparents = 0; // Compta alumnes amb dos o més avis

        // Contar alumnos y sus estadísticas
        ArrayList<String> studentNames = students.getAllStudentsName();
        if (studentNames != null) {
            totalStudents = studentNames.size();
            for (String name : studentNames) {
                BinaryTree studentTree = students.getStudent(name);
                if (studentTree != null) {
                    if (studentTree.isFrom(birthCity)) {
                        countBirthCity++;
                    }
                    if (studentTree.isDescentFrom(originCity)) {
                        countOriginCity++;
                    }
                    if (studentTree.howManyParents() == 1) {
                        countSingleParents++;
                    }
                    if (!studentTree.marriedParents()) {
                        countUnmarriedParents++;
                    }
                    countGrandparents += studentTree.howManyGrandParents();
                }
            }
        }

        // Mostrar informe
        System.out.println("Informe:");
        System.out.println("Nombre d'alumnes totals: " + totalStudents);
        System.out.println("Hi ha " + countBirthCity + " alumnes de " + birthCity);
        System.out.println("Hi ha " + countOriginCity + " alumnes descendents de " + originCity);
        System.out.println("Hi ha " + countSingleParents + " alumnes amb un únic progenitor.");
        System.out.println("Hi ha " + countUnmarriedParents + " alumnes amb progenitors no casats.");
        System.out.println("Hi ha " + countGrandparents + " alumnes amb dos o més avis o àvies.");
    }

    private static int parseMaritalStatus(String status) {
        switch (status) {
            case "Single": return Person.SINGLE;
            case "Married": return Person.MARRIED;
            case "Divorced": return Person.DIVORCED;
            case "Widowed": return Person.WIDOWED;
            default: throw new IllegalArgumentException("Estat civil no vàlid: " + status);
        }
    }
}
