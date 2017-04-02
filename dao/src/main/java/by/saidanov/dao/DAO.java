package by.saidanov.dao;

import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.exceptions.DaoException;

import java.sql.Connection;
import java.util.List;

/**
 * Description: interface contains all basic CRUD operations, this interface is the foundation for all DAO classes.
 *
 * @author Artiom Saidanov.
 */
public interface DAO<T extends EntityMarker> {

    /**
     * This method inserts entity in table. Also this method used to update row in table.
     *
     * @param entity     - one of the entities
     * @param connection transmitted from the service to provide transactions
     */
    void add(T entity, Connection connection) throws DaoException;

    /**
     * This method selects all rows from table.
     *
     * @param connection transmitted from the service to provide transactions
     * @return list of entities
     */
    List<T> getAll(Connection connection) throws DaoException;

    /**
     * This method selects entity by id.
     *
     * @param id         of entity that you want to get
     * @param connection transmitted from the service to provide transactions
     * @return one entity
     */
    T getById(int id, Connection connection) throws DaoException;

    /**
     * This method selects all rows from table.
     *
     * @param id         of entity that you want to delete
     * @param connection transmitted from the service to provide transactions
     */
    void delete(int id, Connection connection) throws DaoException;

    /**
     * This method returns you max id.
     * It is necessary when you insert object in table, then you want to know which id this object has.
     *
     * @param connection transmitted from the service to provide transactions
     */
    int getMaxId(Connection connection) throws DaoException;
}
