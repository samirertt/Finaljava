package group11.group11;

public class Employee {
    private int id;
    private String fullname;
    private String username;
    private String password;
    private String role;

    // Updated constructor
    public Employee(int id, String fullname, String username, String password, String role) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Updated getter and setter for fullname
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    // Updated getter and setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Updated toString method
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}