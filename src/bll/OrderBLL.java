package bll;

import connection.ConnectionFactory;
import dao.OrderDAO;
import model.Bill;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {

    private OrderDAO orderDAO;

    public OrderBLL() {
        this.orderDAO = new OrderDAO();
    }

    /**
     * Imi va genera toate comenzile existente in baza de date
     * @return - va returna o lista cu toate aceste comenzi
     */
    public List<Order> findAll(){
        return orderDAO.findAll();
    }

    /**
     * O metoda prin care cautam comanda dupa id-ul acesteia
     * @param id - id-ul comenzii pe care o cautam
     * @return - va returna comanda daca aceasta a fost gasita, sau v-a arunca o exceptie(No suchElementException)
     * in caz contrar
     */
    public Order findOrderById(int id){
        if(orderDAO.findById(id) != null)
          return orderDAO.findById(id);
        else
            throw new NoSuchElementException("Order with id = " + id + " not found");
    }

    /**
     * In aceasta metoda doar apelam metoda de generare de factura din OrderDAO
     * @param bill - factura generata
     * @param order - comanda pentru care generam factura
     */
    public void generateMessage(Bill bill,Order order){
       orderDAO.generateMessage(bill,order);
    }

    /**
     * In metoda insert din OrderBLL se va face in principiu inserarea parametrului in baza de date apeland metoda
     * de inserare realizata in OrderDAO, dar inainte se verifica daca clientul si produsul, pentru care se realizeaza
     * comanda, exista in baza de date. In caz afirmativ, se va genera id-ul comenzii ( prin accesarea id-ului maxim
     * existent in baza de date si incrementarea acestuia) si se va prelua pretul produsului cumparat din baza de date.
     * De asemenea,pe langa inserarea comenzii in baza de date, vom face un update pentru cantitatea retinuta in baza de
     * date pentru produsul cumparat, astfel incat sa scadem cantitatea cumparata.
     * @param order - Comanda pe care dorim sa o inseram
     * @return va returna comanda inserata daca nu au fost probleme la inserare, sau null in caz contrar
     */

    public Order insert(Order order){
        int idOrder = 1;
        StringBuilder sb1 = new StringBuilder();
        sb1.append("SELECT MAX(id) FROM management.order;");
        PreparedStatement findStatement1 = null;
        ResultSet rs1 = null;
        Connection dbConnection1 = ConnectionFactory.getConnection();
        try {
            findStatement1 = dbConnection1.prepareStatement(sb1.toString());
            rs1 = findStatement1.executeQuery();
            rs1.next();
            idOrder = rs1.getInt("Max(id)");
            idOrder++;
        } catch (SQLException throwables) {
            throwables.getMessage();
        }
        order.setId(idOrder);

        //De verificat daca exista clientul
        StringBuilder sb3 = new StringBuilder();
        sb3.append("SELECT id FROM client WHERE name = '");
        sb3.append(order.getProduct() + "';");
        //System.out.println(sb);
        PreparedStatement findStatement3 = null;
        ResultSet rs3 = null;
        Connection dbConnection3 = ConnectionFactory.getConnection();
        try {
            findStatement3 = dbConnection3.prepareStatement(sb3.toString());
            rs3 = findStatement3.executeQuery();
            rs3.next();
            if(rs3 == null) {
                System.out.println("Client not found");
                return null;
            }
        } catch (SQLException throwables) {
            throwables.getMessage();
        }

        //luam cantitatea;
        int quantity = order.getOrderQuantity();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT quantity FROM product WHERE type = '");
        sb.append(order.getProduct() + "';");
        //System.out.println(sb);
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        int actualQuantity = 0;
        Connection dbConnection = ConnectionFactory.getConnection();
        try {
            ///  findStatement.setInt(quantity, 4);
            findStatement = dbConnection.prepareStatement(sb.toString());
            rs = findStatement.executeQuery();
            rs.next();
            if(rs != null) {
                actualQuantity = rs.getInt("quantity");
            }
            else{
                System.out.println("Product not found");
                return null;
            }
        } catch (SQLException throwables) {
            throwables.getMessage();
        }
        if(actualQuantity > order.getOrderQuantity()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("UPDATE product SET quantity = ");
            sb2.append(actualQuantity - quantity);
            sb2.append(" WHERE type = '");
            sb2.append(order.getProduct() + "';");
            System.out.println(sb2);
            PreparedStatement updateStatement = null;
            try {
                updateStatement = dbConnection.prepareStatement(sb2.toString());
                updateStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.getMessage();
            }
        }
        else{
            System.out.println("Not enough products");
            return null;
        }
        return orderDAO.insert(order);
    }

}
