package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import connection.ConnectionFactory;
import dao.OrderDAO;
import model.Bill;
import model.Client;
import model.Order;
import presentation.Tables.ClientTable;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderPage extends Component {
    private  JPanel panelC;
    private JTextField nameClient;
    private JTextField product;
    private JTextField quantity;
    private JButton add;
    private JButton bill;
    private JButton show;
    private JTable table;
    private OrderControl control = new OrderControl(this);

    public OrderPage(JPanel panelC) {
        this.panelC = panelC;
    }

    /**
     * O metoda in care imi adaug in panel obiectele (butoanele/textField-urile) necesare pentru interfata grafica
     * a a unei comenzi
     */
    public void initializeOrder(){
        nameClient = new JTextField();
        nameClient.setBounds(225, 10,200,50);
        nameClient.setBackground(Color.PINK);
        JLabel label_1 = new JLabel("Client Name: ");
        label_1.setBounds(40,10,150,30);
        label_1.setFont(new Font("Times New Roman",Font.PLAIN,20));
        label_1.setForeground(Color.PINK);
        panelC.add(label_1);
        panelC.add(nameClient);

        product = new JTextField();
        product.setBounds(90, 80,200,50);
        product.setBackground(Color.PINK);
        JLabel label_2 = new JLabel("Product:  ");
        label_2.setBounds(15,70,100,50);
        label_2.setFont(new Font("Times New Roman",Font.PLAIN,20));
        label_2.setForeground(Color.PINK);
        panelC.add(label_2);
        panelC.add(product);

        quantity = new JTextField();
        quantity.setBounds( 400, 80,200,50);
        quantity.setBackground(Color.PINK);
        JLabel label_4 = new JLabel("Quantity:  ");
        label_4.setBounds(310,70,100,50);
        label_4.setFont(new Font("Times New Roman",Font.PLAIN,20));
        label_4.setForeground(Color.PINK);
        panelC.add(label_4);
        panelC.add(quantity);

        add = new JButton("Create Order");
        add.setBounds(90,220,150,50);
        add.setBackground(Color.WHITE);
        add.setFont(new Font("Times New Roman",Font.PLAIN,20));
        add.setFocusable(false);
        add.addActionListener(control);
        panelC.add(add);

        bill = new JButton("Bill");
        bill.setBounds(90,300,150,50);
        bill.setBackground(Color.WHITE);
        bill.setFont(new Font("Times New Roman",Font.PLAIN,20));
        bill.setFocusable(false);
        bill.addActionListener(control);
        panelC.add(bill);

    }

    public class OrderControl implements ActionListener {
        presentation.OrderPage page;
        public OrderControl(OrderPage orderPage) {
            this.page = orderPage;
        }
           int idOrder = 0;
        @Override
        /**
         * O metoda in care pentru fiecare buton apasat corespunzator unei comenzi se vor realiza setarile necesare
         * si se apeleaza metodele corespunzatoare
         */
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == page.getAdd()){
                idOrder++;
                Order order = new Order(idOrder,page.getNameClient().getText(),page.getProduct().getText(),Integer.parseInt(page.getQuantity().getText()));
                OrderBLL orderBLL = new OrderBLL();
                if (orderBLL.insert(order) == null)
                    page.sendError("Order Error! Not enough products!");
            }
            else if(e.getSource() == page.getBill()){
                System.out.println("In bill");
                String nameClient = page.getNameClient().getText();
                String product = page.getProduct().getText();
                int quantity = Integer.parseInt(page.getQuantity().getText());
                Bill bill = new Bill(nameClient,product,quantity);
                Order order = new Order(idOrder,page.getNameClient().getText(),page.getProduct().getText(),Integer.parseInt(page.getQuantity().getText()));
                OrderBLL orderBLL = new OrderBLL();
                orderBLL.generateMessage(bill,order);
            }

        }
    }

    public void sendError(String message) {
        JOptionPane.showMessageDialog(this, message, "Eroare!", JOptionPane.ERROR_MESSAGE);
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JTable getTable() {
        return table;
    }

    public JButton getAdd() {
        return add;
    }


    public JButton getBill() {
        return bill;
    }


    public JButton getShow() {
        return show;
    }

    public JTextField getNameClient() {
        return nameClient;
    }

    public JTextField getProduct() {
        return product;
    }

    public JTextField getQuantity() {
        return quantity;
    }
}
