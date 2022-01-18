package bll.validators;

import model.Product;

public class PriceValidators implements Validator<Product> {
    private static final float MIN_PRICE = 1;
    private static final float MAX_PRICE = 1000;

    /**
     * O metoda prin care ne asiguram ca pretul unor produse adaugate se afla intr-un anumit interval
     * @param product - produsul pentru care validam pretul
     * @return - true in caz afirmativ, altfel false
     */
    @Override
    public boolean validate(Product product) {
       if(product.getPrice() < MIN_PRICE || product.getPrice() > MAX_PRICE){
           return false;
       }
       return true;
    }
}
