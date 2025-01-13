package group11.group11;

public class Employee {
    private int id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String role;

    // Constructor with validation
    public Employee(int id, String name, String surname, String username, String password, String role) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty.");
        if (surname == null || surname.isEmpty()) throw new IllegalArgumentException("Surname cannot be null or empty.");
        if (username == null || username.isEmpty()) throw new IllegalArgumentException("Username cannot be null or empty.");
        if (role == null || role.isEmpty()) throw new IllegalArgumentException("Role cannot be null or empty.");

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty.");
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.isEmpty()) throw new IllegalArgumentException("Surname cannot be null or empty.");
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isEmpty()) throw new IllegalArgumentException("Username cannot be null or empty.");
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) throw new IllegalArgumentException("Password cannot be null or empty.");
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role == null || role.isEmpty()) throw new IllegalArgumentException("Role cannot be null or empty.");
        this.role = role;
    }

    // Updated toString method
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
