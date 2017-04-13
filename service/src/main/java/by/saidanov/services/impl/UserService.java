package by.saidanov.services.impl;

import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.auction.entities.User;
import by.saidanov.auction.util.UserType;
import by.saidanov.dao.BaseDao;
import by.saidanov.dao.IUserDAO;
import by.saidanov.dao.impl.BaseDaoImpl;
import by.saidanov.dao.impl.UserDAOImpl;
import by.saidanov.exceptions.DaoException;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.Service;
import by.saidanov.utils.AuctionLogger;

/**
 * Description: contains business-logic and provides transactional work for user entity
 *
 * @author Artiom Saidanov.
 */
public class UserService implements Service{

    private volatile static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }

    /**
     * This method checks user login and password
     */
    public boolean checkUserAuthorization(String login, String password) throws ServiceException {
        boolean isAuthorized;
        IUserDAO userDao = UserDAOImpl.getInstance();
        try {
            getSession().beginTransaction();
            isAuthorized = userDao.isAuthorized(login, password);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
        return isAuthorized;
    }

    /**
     * This method gets user by his login. All logins in application are unique
     */
    public User getUserByLogin(String login) throws ServiceException {
        User user;
        IUserDAO userDao = UserDAOImpl.getInstance();
        try {
            getSession().beginTransaction();
            user = userDao.getByLogin(login);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
        return user;
    }

    /**
     * This method checks uniqueness of user login
     */
    public boolean checkIsUserNew(User user) throws ServiceException {
        boolean isNew;
        IUserDAO userDao = UserDAOImpl.getInstance();
        try {
            getSession().beginTransaction();
            isNew = userDao.isNewUser(user.getLogin());
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
        return isNew;
    }

    /**
     * This method adds user and user's account to database when user passes the registration
     */
    public void add(User user, Account account) throws ServiceException {
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        user.setAccount(account);
        account.setUser(user);
        try {
            getSession().beginTransaction();
            baseDao.save(user);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
    }

    /**
     * This method separates client and admins
     */
    public UserType checkAccessLevel(User user) {
        UserType userType;
        if (UserType.CLIENT.ordinal() == user.getAccessLevel()) {
            userType = UserType.CLIENT;
        } else {
            userType = UserType.ADMIN;
        }
        return userType;
    }

    public void delete(User user) throws ServiceException {
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        try {
            getSession().beginTransaction();
            baseDao.delete(user);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
    }
}
