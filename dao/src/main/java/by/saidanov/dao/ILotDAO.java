package by.saidanov.dao;

import by.saidanov.auction.entities.Lot;
import by.saidanov.auction.entities.User;
import by.saidanov.exceptions.DaoException;
import org.hibernate.Session;

import java.util.List;

/**
 * Description: interface contains methods which only "lot" table needs
 *
 * @author Artiom Saidanov.
 */
public interface ILotDAO {

    /**
     * This method get all lots except lot that belong to user called this method
     *
     * @param user        - user of lot
     * @param firstResult - this is row from witch starts showing
     * @return list of lots
     * @throws DaoException - throws an exception on the service layer
     */
    List<Lot> getAll(User user, int firstResult) throws DaoException;

    /**
     * This method get all lots
     *
     * @return list of lots
     * @throws DaoException - throws an exception on the service layer
     */
    List<Lot> getAll() throws DaoException;


    /**
     * This method changes lot's boolean isOpen to false
     *
     * @param id - unique lotId
     * @throws DaoException - throws an exception on the service layer
     */
    void delete(int id) throws DaoException;

    /**
     * This method gets lot by lotId
     *
     * @param id - unique lotId
     * @return lot
     * @throws DaoException - throws an exception on the service layer
     */
    Lot getById(Integer id) throws DaoException;


    /**
     * This method gets all user lots
     *
     * @param user - user of lot
     * @return list of lots
     * @throws DaoException - throws an exception on the service layer
     */
    List<Lot> getUserLots(User user) throws DaoException;

    Integer getRowCount(User user) throws DaoException;
}
