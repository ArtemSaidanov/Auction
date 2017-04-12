package by.saidanov.dao.impl;

import by.saidanov.auction.entities.Lot;
import by.saidanov.auction.entities.User;
import by.saidanov.dao.ILotDAO;
import by.saidanov.exceptions.DaoException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: this class contains implementation of specific methods which only "lot" table needs
 *
 * @author Artiom Saidanov.
 */
public class LotDAOImpl extends BaseDaoImpl implements ILotDAO {

    private volatile static LotDAOImpl instance;

    private LotDAOImpl() {
    }

    public static LotDAOImpl getInstance() {
        if (instance == null) {
            synchronized (LotDAOImpl.class) {
                if (instance == null) {
                    instance = new LotDAOImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Lot> getAll(User user) throws DaoException {
        Criteria criteria = getSession().createCriteria(Lot.class);
        criteria.add(Restrictions.ne("user", user));
        criteria.add(Restrictions.eq("isOpen", true));
        List result = criteria.list();
        List<Lot> allLots = new ArrayList<>();
        allLots.addAll(result);
        return allLots;
    }

    @Override
    public List<Lot> getAll() throws DaoException {
        Criteria criteria = getSession().createCriteria(Lot.class);
        List result = criteria.list();
        List<Lot> allLots = new ArrayList<>();
        allLots.addAll(result);
        return allLots;
    }

    @Override
    public void delete(int id) throws DaoException {
        Lot lot = (Lot) getSession().get(Lot.class, id);
        lot.setOpen(false);
        getSession().saveOrUpdate(lot);
//        String deleteQuery = "UPDATE Lot l set l.isOpen = :b where l.id =:id";
//        Query query = session.createQuery(deleteQuery);
//        query.setParameter("id", id);
//        query.setParameter("b", false);
//        query.executeUpdate();
    }

    @Override
    public Lot getById(Integer id) throws DaoException {
        Criteria criteria = getSession().createCriteria(Lot.class);
        criteria.add(Restrictions.eq("id", id));
        List users = criteria.list();
        Lot lot = null;
        for (Object o : users) {
            lot = (Lot) o;
        }
        return lot;
    }

    @Override
    public List<Lot> getUserLots(User user) throws DaoException {
        Criteria criteria = getSession().createCriteria(Lot.class);
        criteria.add(Restrictions.eq("user", user));
        criteria.add(Restrictions.eq("isOpen", true));
        List result = criteria.list();
        List<Lot> allLots = new ArrayList<>();
        allLots.addAll(result);
        return allLots;
    }
}