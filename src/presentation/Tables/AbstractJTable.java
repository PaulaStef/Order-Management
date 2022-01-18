package presentation.Tables;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractJTable<T> {
    private final Class<T> type;

    public AbstractJTable() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Metoda ce utilizeaza reflexia pentru a crea un JTable cu toate field-urile unui obiect de tip T
     * si cu toate obiectele de tip T existente in baza de date. Obiectele din baza de date sunt oferite prin
     * intermediul unei liste de obiecte date ca parametru.
     * @param lists - lista cu obiectele de tip T
     * @return - JTable-ul creat
     */
    public JTable JTable(List<T> lists) {
        Object[] columns = new Object[type.getDeclaredFields().length];
        Object[][] values = new Object[lists.size()+1][type.getDeclaredFields().length];
        int i = 0;
        System.out.println(lists.size());
        for(Field field: type.getDeclaredFields()){
            field.setAccessible(true);
            columns[i] = field.getName();
            values[0][i] = field.getName();
            System.out.println(field.getName());
            i++;
        }
        int k = 1;
        int j = 0;
        try {
            for (T tObject : lists) {
                j=0;
                for (Field field : type.getDeclaredFields()) {
                    field.setAccessible(true);
                    values[k][j] = field.get(tObject);
                    System.out.println(field.get(tObject));
                    j++;
                }
                k++;
            }
        }catch (Exception e1){
            System.out.println(e1.getMessage());
        }
        JTable table = new JTable(values,columns);

        return table;
    }
}
