package by.saidanov.exceptions;

import java.sql.SQLException;

/**
 * Description: this class send exception to service module
 *
 * @author Artiom Saidanov.
 */
public class DaoException extends Exception{

    public DaoException(String message) {}

    public DaoException(String message, SQLException e) {}
}
