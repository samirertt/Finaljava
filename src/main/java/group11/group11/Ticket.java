package group11.group11;

import java.sql.Time;

public class Ticket
{
    private int ticketId;
    private int sessionId;
    private String hall;
    private String seatNumber;
    private String name;
    private String surname;
    private int age;
    private String orderNo;
    private double ticketPrice;

    // Constructor
    public Ticket(int ticketId, int sessionId, String hall, String seatNumber, String name, String surname, int age, String orderNo) {
        this.ticketId = ticketId;
        this.sessionId = sessionId;
        this.hall = hall;
        this.seatNumber = seatNumber;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.orderNo = orderNo;
        this.ticketPrice = Facade.getTicketPriceFromDB();

    }

    // Getters and Setters
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getPrice()
    {
        return  this.ticketPrice;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", sessionId=" + sessionId +
                ", hall='" + hall + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", orderNo='" + orderNo + '\'' +
                '}';
    }

    public void calculateTicketPrice()
    {
        this.ticketPrice = - this.ticketPrice * Facade.getDiscountRateByAge(this.age);
        this.ticketPrice +=  this.ticketPrice * 0.20;
    }
}