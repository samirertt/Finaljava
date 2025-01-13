/**
 * The HTMLGenerator class provides utility methods to generate HTML content for tickets and invoices.
 * It allows creating HTML files for individual tickets and invoices for entire orders.
 */
package group11.group11;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class HTMLGenerator
{
    /**
     * Generates an HTML ticket for a given movie ticket and saves it to the specified file path.
     *
     * @param ticket   The ticket object containing details like movie name, seat number, price, etc.
     * @param date     The date of the event.
     * @param time     The time of the event.
     * @param Hall     The hall where the event will take place.
     * @param filePath The file path where the HTML ticket will be saved.
     */
    public static void generateTicketHTML(Ticket ticket, Date date, Time time, String Hall, String filePath) {
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Ticket</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; }\n" +
                "        .ticket { border: 1px solid #000; padding: 20px; width: 300px; margin: 20px auto; text-align: center; }\n" +
                "        .ticket h1 { margin: 0; }\n" +
                "        .ticket p { margin: 5px 0; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"ticket\">\n" +
                "        <h1>Event Ticket</h1>\n" +
                "        <p><strong>Movie:</strong> " + ticket.getMovieName() + "</p>\n" +
                "        <p><strong>Date:</strong> " + date + "</p>\n" +
                "        <p><strong>Time:</strong> " + time + "</p>\n" +
                "        <p><strong>Hall:</strong> " + Hall + "</p>\n" +
                "        <p><strong>Seat:</strong> " + ticket.getSeatNumber() + "</p>\n" +
                "        <p><strong>Price:</strong> $" + ticket.getTicketPrice() + "</p>\n" +
                "        <p><strong>Name:</strong> " + ticket.getName() + " " + ticket.getSurname() + "</p>\n" +
                "        <p><strong>Age:</strong> " + ticket.getAge() + "</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(htmlContent);
            System.out.println("Generated ticket: " + filePath);
            Facade.saveDocumentToDatabase(ticket.getOrderNo(), "TICKET", htmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates an HTML invoice for an entire order, including all tickets and products, and saves it to the specified file path.
     *
     * @param orderNo    The order number associated with the invoice.
     * @param totalPrice The total price of the order.
     * @param tickets    A list of tickets included in the order.
     * @param products   A list of products included in the order.
     * @param filePath   The file path where the HTML invoice will be saved.
     */
    public static void generateInvoiceHTML(String orderNo, double totalPrice, List<Ticket> tickets, List<Product> products, String filePath) {
        StringBuilder ticketsList = new StringBuilder();
        for (Ticket ticket : tickets) {
            ticketsList.append("<li>")
                    .append(ticket.getMovieName())
                    .append(", Seat: ")
                    .append(ticket.getSeatNumber())
                    .append(", Price: TL")
                    .append(ticket.getTicketPrice())
                    .append("</li>\n");
        }

        StringBuilder productsList = new StringBuilder();
        for (Product product : products) {
            productsList.append("<li>")
                    .append(product.getName())
                    .append(", Quantity: ")
                    .append(product.getQuantity())
                    .append(", Price: TL")
                    .append(product.getTaxedPrice() * product.getQuantity())
                    .append("</li>\n");
        }

        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Invoice</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; }\n" +
                "        .invoice { border: 1px solid #000; padding: 20px; width: 400px; margin: 20px auto; }\n" +
                "        .invoice h1 { margin: 0; }\n" +
                "        .invoice p { margin: 5px 0; }\n" +
                "        .invoice ul { list-style-type: none; padding: 0; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"invoice\">\n" +
                "        <h1>Invoice</h1>\n" +
                "        <p><strong>Order Number:</strong> " + orderNo + "</p>\n" +
                "        <p><strong>Total Price:</strong> $" + totalPrice + "</p>\n" +
                "        <p><strong>Tickets:</strong></p>\n" +
                "        <ul>\n" +
                ticketsList.toString() +
                "        </ul>\n" +
                "        <p><strong>Products:</strong></p>\n" +
                "        <ul>\n" +
                productsList.toString() +
                "        </ul>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(htmlContent);
            System.out.println("Generated invoice: " + filePath);
            Facade.saveDocumentToDatabase(orderNo, "INVOICE", htmlContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
