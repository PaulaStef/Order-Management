package dao;

import connection.ConnectionFactory;
import model.Bill;
import model.Order;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.sql.*;

public class OrderDAO extends AbstractDAO<Order> {
    private int generateOrder = 0;

    /**
     * In aceasta metoda cream pdf-ul care va reprezenta factura ("bill") pentru comanda realizata, si scriem toate datele
     * importante in acest pdf
     * @param bill - factura realizata la plasarea comenzii
     * @param order - comanda pentru care se realizeaza factura
     */
    public void generateMessage(Bill bill, Order order) {
        Document document = new Document();

        //find total price
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT price FROM product WHERE type = '");
        sb.append(order.getProduct() + "';");
        System.out.println(sb);
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        int price = 0;
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            findStatement = dbConnection.prepareStatement(sb.toString());
            rs = findStatement.executeQuery();
            rs.next();
            price = rs.getInt("price");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Write in pdf
        if(bill != null) {
            try {
                String s = order.getId() + "_Bill_for_" + order.getNameClient() + ".pdf";
                PdfWriter.getInstance(document, new FileOutputStream(s));
                document.open();
                document.add(new Paragraph("Bill number: " + order.getId()));
                document.add(Chunk.NEWLINE);
                document.add(new Paragraph("Client name: " + bill.getClientName() ));
                document.add(Chunk.NEWLINE);
                document.add( new Paragraph("Quantity: " + bill.getTotal()));
                document.add(Chunk.NEWLINE);
                document.add( new Paragraph("Product type: " + bill.getProductType()));
                document.add(Chunk.NEWLINE);
                document.add( new Paragraph("Total price: " + price*order.getOrderQuantity()));
                document.add(Chunk.NEWLINE);
                document.add(new Paragraph("Date: " + order.getTimestamp()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                String s = ++generateOrder + "_ERROR_for_" + order.getNameClient() + ".pdf"; //ERROR_for numa
                PdfWriter.getInstance(document, new FileOutputStream(s));
                document.open();
                Chunk chunk = new Chunk(s);
                document.add(chunk);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        document.close();
    }
}
