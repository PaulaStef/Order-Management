package bll;

import bll.validators.PriceValidators;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private List<Validator<Product>> validators = new ArrayList<>();
    private ProductDAO productDAO;

    public ProductBLL() {
        validators.add(new PriceValidators());
        productDAO = new ProductDAO();
    }

    /**
     * Imi va cauta toate produsele existente in baza de date
     * @return - returneaza o lista de produse cu aceste
     */
    public List<Product> findAll(){
        return productDAO.findAll();
    }

    /**
     * Cauta produsul cu id-ul specificat in baza de date
     * @param id - id-ul produsului cautat
     * @return - produsul daca aceste a fost gasit, altfel arunca o eroare
     */
    public Product findProductById(int id){
        if(productDAO.findById(id) != null)
            return productDAO.findById(id);
        else
            throw new NoSuchElementException("Product with id = " + id + "not found");
    }

    /**
     * Insereaza un nou produs in baza de date
     * @param product
     * @return
     */

    public Product insert(Product product){
        return productDAO.insert(product);
    }

    /**
     * Face un uodate pentru campurile produsului
     * @param product
     * @return
     */

    public Product update(Product product){
        return productDAO.update(product);
    }

    /**
     * Sterge din baza de date produsul specificat
     * @param product
     * @return
     */
    public boolean delete(Product product){
        return productDAO.delete(product);
    }

}