package presentation;

import bll.ClientBLL;
import bll.ProductBLL;
import model.Client;
import model.Product;
import presentation.Tables.ClientTable;
import presentation.Tables.ProductTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductPage {
    private  JPanel panelC;
    private JTextField Id;
    private JTextField price;
    private JTextField type;
    private JTextField quantity;
    private JButton add;
    private JButton edit;
    private JButton delete;
    private JButton show;
    private JTable table;
    private ProductControl control = new ProductControl(this);

    public ProductPage(JPanel panelC) {
        this.panelC = panelC;
    }

    public void constructorClientTable(Object[] columns, Object[][] data){
        table = new JTable(data,columns);
    }

    /**
     * O metoda unde adaug la panel obiectele necesare interfetei grafice pentru produs
     */
    public void initializeProduct(){
        Id = new JTextField();
        Id.setBounds(90, 10,200,50);
        Id.setBackground(Color.PINK);
        JLabel label_1 = new JLabel("Id: ");
        label_1.setBounds(15,10,100,30);
        label_1.setFont(new Font("Times New Roman",Font.PLAIN,20));
        label_1.setForeground(Color.PINK);
        panelC.add(label_1);
        panelC.add(Id);

        price = new JTextField();
        price.setBounds(90, 80,200,50);
        price.setBackground(Color.PINK);
        JLabel label_2 = new JLabel("Price:  ");
        label_2.setBounds(15,70,100,50);
        label_2.setFont(new Font("Times New Roman",Font.PLAIN,20));
        label_2.setForeground(Color.PINK);
        panelC.add(label_2);
        panelC.add(price);

        type = new JTextField();
        type.setBounds( 400, 10,200,50);
        type.setBackground(Color.PINK);
        JLabel label_3 = new JLabel("Type:  ");
        label_3.setBounds(310,10,100,50);
        label_3.setFont(new Font("Times New Roman",Font.PLAIN,20));
        label_3.setForeground(Color.PINK);
        panelC.add(label_3);
        panelC.add(type);

        quantity = new JTextField();
        quantity.setBounds( 400, 80,200,50);
        quantity.setBackground(Color.PINK);
        JLabel label_4 = new JLabel("Quantity:  ");
        label_4.setBounds(310,70,100,50);
        label_4.setFont(new Font("Times New Roman",Font.PLAIN,20));
        label_4.setForeground(Color.PINK);
        panelC.add(label_4);
        panelC.add(quantity);

        add = new JButton("Add");
        add.setBounds(90,170,150,50);
        add.setBackground(Color.WHITE);
        add.setFont(new Font("Times New Roman",Font.PLAIN,20));
        add.addActionListener(control);
        panelC.add(add);

        edit = new JButton("Edit");
        edit.setBounds(90,250,150,50);
        edit.setBackground(Color.WHITE);
        edit.setFont(new Font("Times New Roman",Font.PLAIN,20));
        edit.addActionListener(control);
        panelC.add(edit);

        delete = new JButton("Delete");
        delete.setBounds(90,330,150,50);
        delete.setBackground(Color.WHITE);
        delete.setFont(new Font("Times New Roman",Font.PLAIN,20));
        delete.addActionListener(control);
        panelC.add(delete);

        show = new JButton("Show");
        show.setBounds(90,410,150,50);
        show.setBackground(Color.WHITE);
        show.setFont(new Font("Times New Roman",Font.PLAIN,20));
        show.addActionListener(control);
        panelC.add(show);

    }

    public class ProductControl implements ActionListener {
        presentation.ProductPage page;

        public ProductControl(ProductPage productPage) {
            this.page = productPage;
        }

        @Override
        /**
         * O metoda unde pentru fiecare buton corespunzator actiunilor realizate asupra produsului
         * se vor realiza setarile corespunzatoare si se vor apela metodele respective
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == page.getAdd()) {
                int id = Integer.parseInt(page.getId().getText());
                String type = page.getType().getText();
                Float price = Float.valueOf(page.getPrice().getText());
                int quantity = Integer.parseInt(page.getQuantity().getText());
                Product product = new Product(id, type, quantity, price);
                ProductBLL productBLL = new ProductBLL();
                if (productBLL.insert(product) != null)
                    System.out.println("product inserted");
            } else if (e.getSource() == page.getEdit()) {
                int id = Integer.parseInt(page.getId().getText());
                String type = page.getType().getText();
                Float price = Float.valueOf(page.getPrice().getText());
                int quantity = Integer.parseInt(page.getQuantity().getText());
                Product product = new Product(id, type, quantity, price);
                ProductBLL productBLL = new ProductBLL();
                if (productBLL.update(product) != null)
                    System.out.println("product update");
            } else if (e.getSource() == page.getDelete()) {
                int id = Integer.parseInt(page.getId().getText());
                String type = page.getType().getText();
                Float price = Float.valueOf(page.getPrice().getText());
                int quantity = Integer.parseInt(page.getQuantity().getText());
                Product product = new Product(id, type, quantity, price);
                ProductBLL productBLL = new ProductBLL();
                if (productBLL.delete(product) == true)
                    System.out.println("product delete");
            } else if (e.getSource() == page.getShow()) {
                ProductTable j = new ProductTable();
                ProductBLL product = new ProductBLL();
                List<Product> result = product.findAll();
                //System.out.println(result.size());
                page.setTable(j.JTable(result));
                JFrame frame = new JFrame();
                frame.setSize(1000, 700);
                frame.setBounds(0, 0, 1000, 700);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBackground(Color.darkGray);
                frame.add(page.getTable());
                frame.setVisible(true);

            }
        }
    }

    public JButton getAdd() {
        return add;
    }

    public JButton getEdit() {
        return edit;
    }

    public JButton getDelete() {
        return delete;
    }


    public JButton getShow() {
        return show;
    }


    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JTextField getId() {
        return Id;
    }

    public JTextField getPrice() {
        return price;
    }

    public JTextField getType() {
        return type;
    }

    public JTextField getQuantity() {
        return quantity;
    }
}
