/**
 * Represents an employee with attributes such as ID, name, surname, username, password, and role.
 */
package group11.group11;

public class Employee {
    private int id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String role;

   /**
     * Constructs an Employee object with the specified attributes.
     *
     * @param id       The employee's ID (must be positive).
     * @param name     The employee's name (cannot be null or empty).
     * @param surname  The employee's surname (cannot be null or empty).
     * @param username The employee's username (cannot be null or empty).
     * @param password The employee's password (cannot be null or empty).
     * @param role     The employee's role (cannot be null or empty).
     * @throws IllegalArgumentException If any of the validation rules are violated.
     */
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

    /**
     * Returns the employee's ID.
     *
     * @return The employee's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the employee's ID.
     *
     * @param id The employee's ID (must be positive).
     * @throws IllegalArgumentException If the ID is not positive.
     */
    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        this.id = id;
    }

    /**
     * Returns the employee's name.
     *
     * @return The employee's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the employee's name.
     *
     * @param name The employee's name (cannot be null or empty).
     * @throws IllegalArgumentException If the name is null or empty.
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty.");
        this.name = name;
    }

     /**
     * Returns the employee's surname.
     *
     * @return The employee's surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the employee's surname.
     *
     * @param surname The employee's surname (cannot be null or empty).
     * @throws IllegalArgumentException If the surname is null or empty.
     */
    public void setSurname(String surname) {
        if (surname == null || surname.isEmpty()) throw new IllegalArgumentException("Surname cannot be null or empty.");
        this.surname = surname;
    }

    /**
     * Returns the employee's username.
     *
     * @return The employee's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the employee's username.
     *
     * @param username The employee's username (cannot be null or empty).
     * @throws IllegalArgumentException If the username is null or empty.
     */
    public void setUsername(String username) {
        if (username == null || username.isEmpty()) throw new IllegalArgumentException("Username cannot be null or empty.");
        this.username = username;
    }

    /**
     * Returns the employee's password.
     *
     * @return The employee's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the employee's password.
     *
     * @param password The employee's password (cannot be null or empty).
     * @throws IllegalArgumentException If the password is null or empty.
     */
    public void setPassword(String password) {
        if (password == null || password.isEmpty()) throw new IllegalArgumentException("Password cannot be null or empty.");
        this.password = password;
    }

    /**
     * Returns the employee's role.
     *
     * @return The employee's role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the employee's role.
     *
     * @param role The employee's role (cannot be null or empty).
     * @throws IllegalArgumentException If the role is null or empty.
     */
    public void setRole(String role) {
        if (role == null || role.isEmpty()) throw new IllegalArgumentException("Role cannot be null or empty.");
        this.role = role;
    }

    /**
     * Returns a string representation of the Employee object.
     *
     * @return A string representation of the Employee object.
     */
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
