package group11.group11;
import java.util.*;

public class Users
{
    //Profile
    private String password;

    //Non-profile
    private int userId;
    private String username;
    private String role;


    //constructor
    public Users(int userId, String username, String password,String fullName, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Users()
    {

    }

    //getters and setters
    public int getuserId()
    {
        return userId;
    }

    public void setuserId(int userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getrole()
    {
        return role;
    }

    public void setrole(String name)
    {
        this.role = role;
    }
}
