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
    private double basePrice;
    private double ticketPrice;
    private String movieName;

    // Constructor
    public Ticket(int ticketId, int sessionId, String hall, String seatNumber, String name, String surname, int age, String orderNo, String movieName) {
        this.ticketId = ticketId;
        this.sessionId = sessionId; // Ensure this is assigned
        this.hall = hall;
        this.seatNumber = seatNumber;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.orderNo = orderNo;
        this.basePrice = Facade.getTicketPriceFromDB();
        this.ticketPrice = this.basePrice;
        this.movieName = movieName;
    }

    // Getters and Setters
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getSessionId() {
        return this.sessionId;
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

    public double getTicketPrice()
    {
        return  this.ticketPrice;
    }

    public String getMovieName() {
        return movieName;
    }

    public int getSeat()
    {
        if(this.hall.equals("A"))
        {
            switch (this.seatNumber)
            {
                case "A1": return 1;
                case "A2": return 2;
                case "A3": return 3;
                case "A4": return 4;
                case "B1": return 5;
                case "B2": return 6;
                case "B3": return 7;
                case "B4": return 8;
                case "C1": return 9;
                case "C2": return 10;
                case "C3": return 11;
                case "C4": return 12;
                case "D1": return 13;
                case "D2": return 14;
                case "D3": return 15;
                case "D4": return 16;
                default: return -1;
            }
        }
        else
        {
            switch (this.seatNumber) {
                case "A1":
                    return 1;
                case "A2":
                    return 2;
                case "A3":
                    return 3;
                case "A4":
                    return 4;
                case "A5":
                    return 5;
                case "A6":
                    return 6;
                case "A7":
                    return 7;
                case "A8":
                    return 8;
                case "B1":
                    return 9;
                case "B2":
                    return 10;
                case "B3":
                    return 11;
                case "B4":
                    return 12;
                case "B5":
                    return 13;
                case "B6":
                    return 14;
                case "B7":
                    return 15;
                case "B8":
                    return 16;
                case "C1":
                    return 17;
                case "C2":
                    return 18;
                case "C3":
                    return 19;
                case "C4":
                    return 20;
                case "C5":
                    return 21;
                case "C6":
                    return 22;
                case "C7":
                    return 23;
                case "C8":
                    return 24;
                case "D1":
                    return 25;
                case "D2":
                    return 26;
                case "D3":
                    return 27;
                case "D4":
                    return 28;
                case "D5":
                    return 29;
                case "D6":
                    return 30;
                case "D7":
                    return 31;
                case "D8":
                    return 32;
                case "E1":
                    return 33;
                case "E2":
                    return 34;
                case "E3":
                    return 35;
                case "E4":
                    return 36;
                case "E5":
                    return 37;
                case "E6":
                    return 38;
                case "E7":
                    return 39;
                case "E8":
                    return 40;
                case "F1":
                    return 41;
                case "F2":
                    return 42;
                case "F3":
                    return 43;
                case "F4":
                    return 44;
                case "F5":
                    return 45;
                case "F6":
                    return 46;
                case "F7":
                    return 47;
                case "F8":
                    return 48;
                default:
                    throw new IllegalArgumentException("Invalid seat name: " + seatNumber);
            }
        }
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
        double discountRate = Facade.getDiscountRateByAge(this.age);
        this.ticketPrice = this.basePrice * (1 - discountRate); // Subtract discount from the original price

        // Add 20% tax or fee (if applicable)
        this.ticketPrice += this.ticketPrice * 0.20;
    }
}