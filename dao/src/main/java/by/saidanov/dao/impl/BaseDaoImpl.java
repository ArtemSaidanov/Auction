package by.saidanov.dao.impl;

import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.dao.BaseDao;
import by.saidanov.exceptions.DaoException;
import by.saidanov.utils.AuctionLogger;
import by.saidanov.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.TransientObjectException;

import java.io.Serializable;

/**
 * Description: this is implementation of {@link BaseDao} interface. This class works with all entities in application.
 *
 * @author Artiom Saidanov.
 */
public class BaseDaoImpl implements BaseDao<EntityMarker> {

    private volatile static BaseDaoImpl instance;

    protected BaseDaoImpl() {
    }

    public static BaseDaoImpl getInstance() {
        if (instance == null) {
            synchronized (BaseDaoImpl.class) {
                if (instance == null) {
                    instance = new BaseDaoImpl();
                }
            }
        }
        return instance;
    }

    public Session getSession() {
        return HibernateUtil.getHibernateUtil().getSession();
    }

    @Override
    public Serializable save(EntityMarker entity) throws DaoException {
        Serializable id = null;
        try {
            id = getSession().save(entity);
        } catch (TransientObjectException e){
            String message = e.toString() + e.getMessage();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message);
        }
        return id;
    }

    @Override
    public void update(EntityMarker entity) throws DaoException {
        try {
            getSession().update(entity);
        } catch (TransientObjectException e){
            String message = e.toString() + e.getMessage();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message);
        }
    }

    @Override
    public EntityMarker get(Class clazz, int id) throws DaoException {
        EntityMarker entity = (EntityMarker) getSession().get(clazz, id);
        if (entity == null) {
            String message = "No entity" + clazz.getName() + " with id = " + id;
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message);
        }
        return entity;
    }

    @Override
    public void delete(EntityMarker entity) throws DaoException {
        try {
            getSession().delete(entity);
        } catch (HibernateException e){
            String message = e.toString() + e.getMessage();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message);
        }
    }
}
