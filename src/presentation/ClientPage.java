package presentation;

import bll.ClientBLL;
import dao.ClientDAO;
import model.Client;
import presentation.Tables.ClientTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

public class ClientPage {
    private  JPanel panelC;
    private JTextField Id;
    private JTextField name;
    private JTextField address;
    private JTextField email;
    private JButton add;
    private JButton edit;
    private JButton delete;
    private JButton show;
    private JTable table;
    private ClientControl control = new ClientControl(this);


    public ClientPage(JPanel panel) {
        this.panelC = panel;
        //controller.setClientPage(this);
    }

    public JPanel getPanelC() {
        return panelC;
    }

    /**
     * O metoda in care imi adaug in panel obiectele necesare pentru interfata grafica a unui client
     */
    public void initializeClient(){

        Id = new JTextField();
        Id.setBounds(90, 10,200,50);
        Id.setBackground(Color.PINK);
        JLabel label_1 = new JLabel("Id: ");
        label_1.setBounds(15,10,100,30);
        label_1.setFont(new Font("Times New Roman",Font.PLAIN,20));
        label_1.setForeground(Color.PINK);
        panelC.add(label_1);
        panelC.add(Id);

        name = new JTextField();
        name.setBounds(90, 80,200,50);
        name.setBackground(Color.PINK);
        JLabel label_2 = new JLabel("Name:  ");
        label_2.setBounds(15,70,100,50);
        label_2.setFont(new Font("Times New Roman",Font.PLAIN,20));
        label_2.setForeground(Color.PINK);
        panelC.add(label_2);
        panelC.add(name);

        address = new JTextField();
        address.setBounds( 400, 10,200,50);
        address.setBackground(Color.PINK);
        JLabel label_3 = new JLabel("Address:  ");
        label_3.setBounds(310,10,100,50);
        label_3.setFont(new Font("Times New Roman",Font.PLAIN,20));
        label_3.setForeground(Color.PINK);
        panelC.add(label_3);
        panelC.add(address);

        email = new JTextField();
        email.setBounds( 400, 80,200,50);
        email.setBackground(Color.PINK);
        JLabel label_4 = new JLabel("Email:  ");
        label_4.setBounds(310,70,100,50);
        label_4.setFont(new Font("Times New Roman",Font.PLAIN,20));
        label_4.setForeground(Color.PINK);
        panelC.add(label_4);
        panelC.add(email);

        add = new JButton("Add");
        add.setBounds(90,170,150,50);
        add.setBackground(Color.WHITE);
        add.setFont(new Font("Times New Roman",Font.PLAIN,20));
        add.setFocusable(false);
        add.addActionListener(control);
        panelC.add(add);

        edit = new JButton("Edit");
        edit.setBounds(90,250,150,50);
        edit.setBackground(Color.WHITE);
        edit.setFont(new Font("Times New Roman",Font.PLAIN,20));
        edit.setFocusable(false);
        edit.addActionListener(control);
        panelC.add(edit);

        delete = new JButton("Delete");
        delete.setBounds(90,330,150,50);
        delete.setBackground(Color.WHITE);
        delete.setFont(new Font("Times New Roman",Font.PLAIN,20));
        delete.setFocusable(false);
        delete.addActionListener(control);
        panelC.add(delete);

        show = new JButton("Show");
        show.setBounds(90,410,150,50);
        show.setBackground(Color.WHITE);
        show.setFont(new Font("Times New Roman",Font.PLAIN,20));
        show.setFocusable(false);
        show.addActionListener(control);
        panelC.add(show);
    }

    public class ClientControl implements ActionListener {
        presentation.ClientPage page;

        public ClientControl(ClientPage clientPage) {
            this.page = clientPage;
        }

        @Override
        /**
         * O metoda suprascrisa in care pentru fiecare buton apasat se relizeaza setarile specifice si se apeleaza
         * metodele corespunzatoare pentru inserare/update/stergere/vizualizare tabel
         */
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == page.getAdd()){
                int id = Integer.parseInt(page.getId().getText());
                String name = page.getName().getText();
                String address = page.getAddress().getText();
                String email = page.getEmail().getText();
                Client client = new Client(id,name,address,email);
                ClientBLL clientBLL = new ClientBLL();
                if(clientBLL.insert(client) != null)
                    System.out.println("Client inserted");
            }
            else if(e.getSource() == page.getEdit()){
                System.out.println("Edit");
                int id = Integer.parseInt(page.getId().getText());
                String name = page.getName().getText();
                String address = page.getAddress().getText();
                String email = page.getEmail().getText();
                Client client = new Client(id,name,address,email);
                System.out.println(client.getId() + client.getName() + client.getAddress() + client.getEmail());
                ClientBLL clientBLL = new ClientBLL();
                if(clientBLL.update(client) != null)
                    System.out.println("Client update");
            }
            else if(e.getSource() == page.getDelete()){
                int id = Integer.parseInt(page.getId().getText());
                String name = page.getName().getText();
                String address = page.getAddress().getText();
                String email = page.getEmail().getText();
                Client client = new Client(id,name,address,email);
                ClientBLL clientBLL = new ClientBLL();
                if(clientBLL.delete(client) == true)
                    System.out.println("Client delete");
            }
            else if(e.getSource() == page.getShow()) {
                ClientTable j = new ClientTable();
                ClientBLL client = new ClientBLL();
                List<Client> result = client.findAll();
                System.out.println(result.size());
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


    public JTextField getId() {
        return Id;
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

    public JTextField getName() {
        return name;
    }

    public JTextField getAddress() {
        return address;
    }

    public JTextField getEmail() {
        return email;
    }
}
