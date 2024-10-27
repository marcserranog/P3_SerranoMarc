package src;
public class Person {

    public static final int SINGLE = 0;
    public static final int MARRIED = 1;
    public static final int DIVORCED = 2;
    public static final int WIDOWED = 3;

    private int maritalStatus;
    private String placeOfOrigin;
    private String name;

    // Constructors
    public Person(String name, String placeOfOrigin, int maritalStatus) {
        if (!isValidMaritalStatus(maritalStatus)) {
            throw new IllegalArgumentException("Estado civil no válido");
        }
        this.name = name;
        this.placeOfOrigin = placeOfOrigin;
        this.maritalStatus = maritalStatus;
    }

    public Person(String formattedString) {
        String[] parts = formattedString.split(", ");

        if (parts.length != 3) {
            throw new IllegalArgumentException("The formatted string must have 3 parts: name, place of origin, marital status");
        }
        
        this.name = parts[0].replace("Name: ", "").trim();
        this.placeOfOrigin = parts[1].replace("place of Origin: ", "").trim();
        
        String maritalStatusString = parts[2].replace("marital status: ", "").trim();
        this.maritalStatus = parseMaritalStatus(maritalStatusString);
        
        if (!isValidMaritalStatus(this.maritalStatus)) {
            throw new IllegalArgumentException("Marital status is not valid: " + maritalStatusString);
        }
    }

    public String getName() {
        return name;
    }
    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }
    public int getMaritalStatus() {
        return maritalStatus;
    }  
    public String toString() {
        return "Name: " + name + ", place of Origin: " + placeOfOrigin + ", marital status: " + maritalStatusToString();
    }

    private String maritalStatusToString() {
        switch (maritalStatus) {
            case SINGLE: return "Single";
            case MARRIED: return "Married";
            case DIVORCED: return "Divorced";
            case WIDOWED: return "Widowed";
            default: throw new IllegalArgumentException("Marital status is not valid: " + maritalStatus);
        }
    }

    private int parseMaritalStatus(String status) {
        switch (status) {
            case "Single": return SINGLE;
            case "Married": return MARRIED;
            case "Divorced": return DIVORCED;
            case "Widowed": return WIDOWED;
            default: throw new IllegalArgumentException("Marital status is not valid: " + maritalStatus);
        }
    }

    private boolean isValidMaritalStatus(int status) {
        return status == SINGLE || status == MARRIED || status == DIVORCED || status == WIDOWED;
    }
}