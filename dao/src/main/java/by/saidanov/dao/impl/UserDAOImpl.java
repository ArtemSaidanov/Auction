package by.saidanov.dao.impl;

import by.saidanov.auction.entities.User;
import by.saidanov.dao.IUserDAO;
import by.saidanov.exceptions.DaoException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Description: this class contains implementation of specific methods which only "user" table needs
 *
 * @author Artiom Saidanov.
 */
public class UserDAOImpl extends BaseDaoImpl implements IUserDAO {

    private UserDAOImpl() {
    }

    private volatile static UserDAOImpl instance;

    public static UserDAOImpl getInstance() {
        if (instance == null) {
            synchronized (UserDAOImpl.class) {
                if (instance == null) {
                    instance = new UserDAOImpl();
                }
            }
        }
        return instance;
    }


    @Override
    public boolean isAuthorized(String login, String password) throws DaoException {
        String selectByLoginAndPass = "select u.firstName from User u where u.login = :login and u.password = :password";
        Query query = getSession().createQuery(selectByLoginAndPass);
        query.setParameter("login", login);
        query.setParameter("password", password);
        List users = query.list();
        return !users.isEmpty();
    }

    @Override
    public User getByLogin(String login) throws DaoException {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("login", login));
        List users = criteria.list();
        User user = null;
        for (Object o : users) {
            user = (User) o;
        }
        return user;
    }

    @Override
    public boolean isNewUser(String login) throws DaoException {
        Criteria criteria = getSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("login", login));
        List users = criteria.list();
        User user = null;
        for (Object o : users) {
            user = (User) o;
        }
        return user == null;
    }
}
