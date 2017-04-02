package by.saidanov.dao;

import by.saidanov.auction.entities.Lot;
import by.saidanov.exceptions.DaoException;

import java.sql.Connection;
import java.util.List;

/**
 * Description: interface contains methods which only "lot" table needs.
 *
 * @author Artiom Saidanov.
 */
public interface ILotDAO extends DAO<Lot> {

    /** This method gets all user lots from database */
    List<Lot> getUserLots(int id, Connection connection) throws DaoException;
}
