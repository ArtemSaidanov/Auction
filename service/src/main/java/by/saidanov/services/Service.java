package by.saidanov.services;

import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.utils.HibernateUtil;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

/**
 * Description: base service interface, contains all necessary methods
 *
 * @author Artiom Saidanov.
 */
public interface Service<T extends EntityMarker>{

    default Session getSession(){
        return HibernateUtil.getHibernateUtil().getSession();
    }

//    /**
//     * Calls Dao add() method
//     * @param entity - object of derived class Entity
//     * @throws SQLException
//     * */
//    void add(T entity) throws SQLException, ServiceException;
//
//    /**
//     *  Calls Dao getAll() method
//     * @return list of objects of derived class Entity
//     * @throws SQLException
//     * */
//    List<T> getAll() throws SQLException, ServiceException;
//
//    /**
//     * Calls Dao getById() method
//     * @param id - id of entity
//     * @return object of derived class Entity
//     * @throws SQLException
//     * */
//    T getById(int id) throws SQLException, ServiceException;
//
//    /**
//     * Calls Dao update() method
//     * @param entity - object of derived class Entity
//     * @throws SQLException
//     * */
//    void update(T entity) throws SQLException, ServiceException;
//
//    /**
//     * Calls Dao delete() method
//     * @param id - id of entity
//     * @throws SQLException
//     * */
//    void delete(int id) throws SQLException, ServiceException;
}
