package model;

public class Bill {
    private String clientName;
    private String productType;
    private float total;


    public Bill( String clientName, String productType, float total) {
        this.clientName = clientName;
        this.productType = productType;
        this.total = total;
    }


    /**
     * 	Returneaza un tablou bidimensional care contine campurile clasei si numele tabelei
     * din care face parte in baza de date;
     */
    public String[] toArrayString() {
        String s[] = new String[5];
        s[0] = "bill";
        s[1] = clientName;
        s[2] = productType;
        s[3] = Float.toString(total);

        return s;
    }

    /**
     * Returneaza numele clientului pentru care s-a intocmit chitanta.
     */
    public String getClientName() {
        return clientName;
    }


    /**
     * Returneaza tipul produsului care s-a dorit a fi cumparat.
     */
    public String getProductType() {
        return productType;
    }


    /**
     * Returneaza suma totala de platit pentru produse.
     */
    public float getTotal() {
        return total;
    }
}
