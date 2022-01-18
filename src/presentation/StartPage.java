package presentation;

import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPage {
    private JFrame framePP;
    private JPanel panelPP;
    private JButton client;
    private JButton product;
    private JButton order;
    private JLabel title;
    private Control Control = new Control(this);
    ClientPage clientPage;
    OrderPage orderPage;
    ProductPage productPage;

    public StartPage() {
        initialize();
    }

    /**
     * O metoda in care imi adaug obiectele necesare paginii de start a interfetei grafice
     */
    public void setFields(){
        title = new JLabel(" Warehouse ");
        title.setBounds(225,20,200,100);
        title.setFont(new Font("Times New Roman",Font.PLAIN,28));
        title.setForeground(Color.PINK);
        panelPP.add(title);

        client = new JButton("Clients");
        client.setBounds(200,150,200,50);
        client.setFont(new Font("Times New Roman",Font.PLAIN,20));
        client.setBackground(Color.PINK);
        client.addActionListener(Control);
        panelPP.add(client);

        product = new JButton("Products");
        product.setBounds(200,250,200,50);
        product.setFont(new Font("Times New Roman",Font.PLAIN,20));
        product.setBackground(Color.PINK);
        product.addActionListener(Control);
        panelPP.add(product);

        order = new JButton("Orders");
        order.setBounds(200,350,200,50);
        order.setFont(new Font("Times New Roman",Font.PLAIN,20));
        order.setBackground(Color.PINK);
        order.addActionListener(Control);
        panelPP.add(order);
    }

    /**
     * O metoda in care imi initializez frame-ul  si panel-ul initial (de start) al interfetei grafice
     */
    public void initialize(){
        framePP = new JFrame();
        framePP.setSize(700, 600);
        framePP.setBounds(450, 550, 650, 550);
        framePP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePP.getContentPane().setLayout(null);
        panelPP = new JPanel();
        panelPP.setBounds(0, 0, 700, 600);
        panelPP.setBackground(Color.darkGray);
        framePP.getContentPane().add(panelPP);
        panelPP.setLayout(null);
        setFields();
        framePP.add(panelPP);
        framePP.setVisible(true);
        clientPage = new ClientPage(this.panelPP);
        orderPage = new OrderPage(this.panelPP);
        productPage = new ProductPage(this.panelPP);
    }

    public class Control implements ActionListener{
       presentation.StartPage page;

        public Control(StartPage startPage) {
            this.page = startPage;
        }

        @Override
        /**
         * O metoda suprascrisa in care pentru fiecare buton din pagina de start imi reinitializez (curat) panel-ul si
         * imi apelez metoda corespunzatoare butonului apelat astfel incat pe panel sa imi apara obiectele corect.
         * De ex. Daca se apasa butonul de client se va elibera panel-ul si se va apela metoda de initializeClient care
         * va reliza interfata grafica corespunzatoare clientului
         */
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == page.getClient()){
                page.getPanelPP().removeAll();
                page.getFramePP().repaint();
                page.getFramePP().revalidate();
                clientPage.initializeClient();
            }
             else if(e.getSource() == page.getOrder()){
                page.getPanelPP().removeAll();
                page.getFramePP().repaint();
                page.getFramePP().revalidate();
                orderPage.initializeOrder();
            }
            else if(e.getSource() == page.getProduct()){
                System.out.println("Products");
                page.getPanelPP().removeAll();
                page.getFramePP().repaint();
                page.getFramePP().revalidate();
                productPage.initializeProduct();
            }
        }
    }


    public JButton getClient() {
        return client;
    }

    public void setClient(JButton client) {
        this.client = client;
    }

    public JButton getProduct() {
        return product;
    }

    public void setProduct(JButton product) {
        this.product = product;
    }

    public JButton getOrder() {
        return order;
    }

    public void setOrder(JButton order) {
        this.order = order;
    }

    public JFrame getFramePP() {
        return framePP;
    }

    public JPanel getPanelPP() {
        return panelPP;
    }
}


