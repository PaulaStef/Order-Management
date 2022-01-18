package presentation;

import bll.ClientBLL;
import model.Client;
import presentation.Tables.AbstractJTable;
import presentation.Tables.ClientTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controller implements ActionListener {
    OrderPage orderPage;
    ClientPage clientPage;
    ProductPage productPage;
    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource() == clientPage.getAdd()){
          //insert client
      }
      else if(e.getSource() == clientPage.getEdit()){
          //update client
      }
      else if(e.getSource() == clientPage.getDelete()){
          //delete client
      }
      else if(e.getSource() == clientPage.getShow()) {
          System.out.println("Show");
          ClientTable j = new ClientTable();
          ClientBLL client = new ClientBLL();
          List<Client> result = client.findAll();
          JTable table = j.JTable(result);
          clientPage.setTable(table);
          //clientPage.getPanelC().add(clientPage.getTable());
      }
      else if(e.getSource() == orderPage.getAdd()){
          //add Order
      }
      else if(e.getSource() == orderPage.getBill()){
          //print bill
      }
    }

    public void setOrderPage(OrderPage orderPage) {
        this.orderPage = orderPage;
    }

    public void setClientPage(ClientPage clientPage) {
        this.clientPage = clientPage;
    }

    public void setProductPage(ProductPage productPage) {
        this.productPage = productPage;
    }
}
