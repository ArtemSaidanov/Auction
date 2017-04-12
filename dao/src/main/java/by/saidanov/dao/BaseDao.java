package by.saidanov.dao;

import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.exceptions.DaoException;
import org.hibernate.Session;

import java.io.Serializable;

/**
 * Description: this interface contains base CRUD methods.
 *
 * @author Artiom Saidanov.
 */
public interface BaseDao<E extends EntityMarker> {

    /**
     * This method saves entity in table and assign entity a generated identifier.
     *
     * @param entity  - one of the entities
     * @return the generated identifier
     * @throws DaoException - throws an exception on the service layer
     */
    Serializable save(E entity) throws DaoException;

    /**
     * This method updates entity in table.
     *
     * @param entity  - one of the entities
     * @throws DaoException - throws an exception on the service layer if entity does not exist
     */
    void update(E entity) throws DaoException;

    /**
     * This method gets entity from database.
     *
     * @param id - of entity that you want to get
     * @return one entity
     * @throws DaoException - throws an exception on the service layer if entity does not exist
     */
    E get(Class clazz, int id) throws DaoException;

    /**
     * This method deletes entity from table.
     *
     * @param entity  - that you want to delete
     * @throws DaoException - throws an exception on the service layer if entity does not exist
     */
    void delete(E entity) throws DaoException;

}
