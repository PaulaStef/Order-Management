package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * O metoda in care se creeaza un string ce va reprezenta un statement de cautare a unui obiect(type)
     * in baza de date. Cautarea se va realiza dupa parametrul field care va reprezenta un camp al obiecutlui/tabelului
     * @param field - camp al obiectului/ tabeluilui cautat
     * @return - returneaza string-ul creat
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * O metoda prin care vom prelua toate obiectele de tip type din baza de date si vom returna o lista de obiecte de
     * acelasi tip.
     * @return - o lista cu toate obiectele gasite in baza de date
     */
    public List<T> findAll() {
        // TODO:
        StringBuilder findAll = new StringBuilder();
        findAll.append("SELECT * FROM ");
        findAll.append(type.getSimpleName() + ";");
        ResultSet result = null;
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(String.valueOf(findAll))){
            result = preparedStatement.executeQuery();
            return createObjects(result);
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(result != null){
                ConnectionFactory.close(result);
            }
        }
        return Collections.emptyList();
    }

    /**
     * O metoda prin care vom cauta un obiect de tip type, in baza de date, care va avea ca id parametrul dat
     * @param id -id ul obiectului pe care il cautam
     * @return - obiectul cautat in cazul in care exista in baza de date, sau null altfel
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<T> result =  createObjects(resultSet);
                if(!result.isEmpty()) {
                return result.get(0);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * O metoda care preia rezultatul unui query (o interogatie in Mysql) si genereaza o lista de obiecte
     *  de tip type cu aceste rezultate
     * @param resultSet - rezultatul de le executeQuery
     * @return - lista generata
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * O metoda prin care se realizeaza un statement, dupa care se executa cu scopul de a insera intr-un tabel (type),
     * un obiect de tip T. Am folosit reflexia astfel incat putem utiliza aceasta metoda pentru a insera orice tip
     * de obiect (existent in baza de date) in orice tabel(atata timp cat corespund)
     * @param t - Obiectul de tip T pe care dorim sa il adaugam
     * @return - obiectul inserat
     */
    public T insert(T t) {
        // TODO:
        StringBuilder insert = new StringBuilder();
        insert.append("INSERT into management.");
        insert.append(t.getClass().getSimpleName());
        insert.append(" (");
        for(Field field : t.getClass().getDeclaredFields()){
            insert.append(field.getName());
            insert.append(",");
        }
        insert.deleteCharAt(insert.length()-1);
        insert.append(") values (");
        for(Field field : t.getClass().getDeclaredFields()){
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(t);
                insert.append("'" + value + "',");
            }catch (IllegalAccessException e1){
                System.out.println(e1.getMessage());
            }catch(IllegalArgumentException e2){
                System.out.println(e2.getMessage());
            }
        }
        insert.deleteCharAt(insert.length()-1);
        insert.append(");");
        System.out.println(insert);

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(String.valueOf(insert))){
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return t;
    }

    /**
     * In aceasta metoda realizam si executam un statement prin care vom face update unui obiect de tip T
     * existent in baza de date. Am folosit reflexia pentru a putea folosi aceasta metoda pentru a face update
     * tuturor obiectelor de un anumit tip dat( atata tip cat exista in baza de date)
     * @param t - Obiectul de tip T la care dorim sa ii facem update
     * @return - obiectul daca s-a relaizat update-ul
     */
    public T update(T t)  {
        // TODO:
        StringBuilder update = new StringBuilder();
        update.append("Update ");
        update.append(t.getClass().getSimpleName());
        update.append(" SET ");
        int id = -1;
        try{
        for(Field field : t.getClass().getDeclaredFields()){
            field.setAccessible(true);
            Object value;
            if(!field.getName().equals("id")){
                value = field.get(t);
                update.append(field.getName() + " = '" + value + "',");
            }
            else{
                id = (int) field.get(t);
            }
        }
        }catch (IllegalArgumentException e1){
            System.out.println(e1.getMessage());
        }catch (IllegalAccessException e2){
            System.out.println(e2.getMessage());
        }
        update.deleteCharAt(update.length()-1);
        update.append(" WHERE id = " + id + ";");
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(String.valueOf(update))){
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return t;
    }

    /**
     * In aceasta metoda realizam si executam un statement prin care vom sterge un obiect de tip T
     * existent in baza de date. Am folosit reflexia pentru a putea folosi aceasta metoda pentru a realiza stergerea
     * tuturor obiectelor de un anumit tip dat( atata tip cat exista in baza de date)
     * @param t - Obiectul de tip T la care dorim sa il sterge
     * @return - true daca obiectul a fost sters, false altfel
     */
    public boolean delete(T t){
        StringBuilder delete = new StringBuilder();
        delete.append("DELETE FROM ");
        delete.append(t.getClass().getSimpleName());
        delete.append(" WHERE id = ");
        int id = -1;
        try{
        for(Field field : t.getClass().getDeclaredFields()){
            field.setAccessible(true);
            if(field.getName().equals("id")){
                id = (int) field.get(t);
                break;
            }
        }
        }catch (IllegalAccessException e1){
            System.out.println(e1.getMessage());
        }catch (IllegalArgumentException e2){
            System.out.println(e2.getMessage());
        }
        delete.append( id + ";");
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(String.valueOf(delete))){
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}

