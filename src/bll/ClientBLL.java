package bll;

import bll.validators.EmailValidators;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

import javax.lang.model.type.ErrorType;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.ErrorManager;

public class ClientBLL {
    private List<Validator<Client>> validators = new ArrayList<>();
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators.add(new EmailValidators());
        clientDAO = new ClientDAO();
    }

    /**
     * Imi va cauta toti clientii existenti in baza de date
     * @return - returneaza o lista de clienti cu acestia
     */

    public List<Client> findAll(){
        return clientDAO.findAll();
    }

    /**
     * Metoda prin care cautam un client dupa un id
     * @param id -id-ul clientului pe care il cautam
     * @return - va returna Clientul cautat, sau null daca acesta nu exista in baza de date
     */
    public Client findClientById(int id){
        if(clientDAO.findById(id) != null )
            return clientDAO.findById(id);
        else
            throw  new NoSuchElementException("Client with id = " + id + " not found !");
    }

    /**
     * Realizeaza inserarea clientului in baza de date, dar mai intai, valideaza email-urile clientului
     * care urmeaza sa fie adaugat
     * @param client - clientul ce dorim sa il inseram
     * @return - returneaza clientul inserat in cz afirmativ, iar altfel arunca o exceptie si returneaza null
     */
    public Client insert(Client client){
        if(validators.get(validators.size()-1).validate(client))
             return clientDAO.insert(client);
        else
            System.out.println("File does not exist or it cannot be read.");
        return null;
    }

    /**
     * Realizeaza update in baza de date pentru Clientul dorit
     * @param client - clientul pentru care dorim sa facem update
     * @return - clientul modificat
     */
    public Client update(Client client){
        return clientDAO.update(client);
    }

    /**
     * Va sterge din baza de date clientul dorit
     * @param client - clientul pe care dorim sa il stergem
     * @return - true in cazul in care s-a realizat stergerea, altfel false
     */
    public boolean delete(Client client){
        return clientDAO.delete(client);
    }

}
