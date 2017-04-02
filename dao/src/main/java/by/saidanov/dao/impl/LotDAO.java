package by.saidanov.dao.impl;

import by.saidanov.auction.entities.Lot;
import by.saidanov.auction.entities.User;
import by.saidanov.constants.SQLQuery;
import by.saidanov.dao.ILotDAO;
import by.saidanov.exceptions.DaoException;
import by.saidanov.managers.PoolManager;
import by.saidanov.utils.AuctionLogger;
import by.saidanov.utils.ClosingUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: this class works with "lot" table
 *
 * @author Artiom Saidanov.
 */
public class LotDAO implements ILotDAO {

    private volatile static LotDAO instance;

    private static String message;

    private LotDAO() {
    }

    public static LotDAO getInstance() {
        if (instance == null) {
            synchronized (LotDAO.class) {
                if (instance == null) {
                    instance = new LotDAO();
                }
            }
        }
        return instance;
    }

    @Override
    public void add(Lot lot, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        String message;
        if (lot == null) {
            message = "NullPointerException in add() method in " + getClass().toString();
            throw new DaoException(message);
        }
        try {
            statement = connection.prepareStatement(SQLQuery.ADD_LOT);
            statement.setInt(1, lot.getUserId());
            statement.setString(2, lot.getName());
            statement.setString(3, lot.getDescription());
            statement.setInt(4, lot.getQuantity());
            statement.setInt(5, lot.getStartPrice());
            statement.setInt(6, lot.getMinPrice());
            statement.setInt(7, lot.getCurrentPrice());
            statement.setInt(8, lot.getPriceCutStep());
            statement.setInt(9, lot.getTimeToNextCut());
            statement.executeUpdate();
        } catch (SQLException e) {
            message = "Unable to add lot";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
        }
    }

    @Override
    public Lot getById(int id, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Lot lot = null;
        try {
            statement = connection.prepareStatement(SQLQuery.GET_LOT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lot = getLot(resultSet);
            }
        } catch (SQLException e) {
            String message = "Unable to get lot";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
            ClosingUtil.close(resultSet);
        }
        return lot;
    }

    @Override
    public void delete(int id, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLQuery.DELETE_LOT);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            String message = "Unable to delete lot";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
        }
    }

    /**
     * This method delete all user's lots from database
     */
    public void delete(User user, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLQuery.DELETE_LOT_FROM_DB);
            statement.setInt(1, user.getId());
            statement.execute();
        } catch (SQLException e) {
            String message = "Unable to delete all user's lots";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
        }
    }

    /**
     * TODO fix it!
     */
    @Override
    public int getMaxId(Connection connection) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int maxId = -1;
        try {
            Connection connection1 = PoolManager.getDataSource().getConnection();
            statement = connection1.prepareStatement(SQLQuery.GET_MAX_ID);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                maxId = rs.getInt(1);
            }
        } catch (SQLException e) {
            String message = "Unable to get max lot id";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
            //ClosingUtil.close(resultSet);
        }
        return maxId;
    }

    @Override
    public List<Lot> getAll(Connection connection) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Lot> lotList;
        try {
            statement = connection.prepareStatement(SQLQuery.GET_ALL_LOTS);
            resultSet = statement.executeQuery();
            lotList = parseResultSet(resultSet);
        } catch (SQLException e) {
            String message = "Unable to get all lots";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
            ClosingUtil.close(resultSet);
        }
        return lotList;
    }

    /**
     * This method gets all lots except lots that belong to user that want to see all lots
     */
    public List<Lot> getAll(int userId, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Lot> lotList;
        try {
            statement = connection.prepareStatement(SQLQuery.GET_ALL_LOTS_EXCEPT_USER);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            lotList = parseResultSet(resultSet);
        } catch (SQLException e) {
            String message = "Unable to get all lots";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
            ClosingUtil.close(resultSet);
        }
        return lotList;
    }

    @Override
    public List<Lot> getUserLots(int userId, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Lot> lotList;
        try {
            statement = connection.prepareStatement(SQLQuery.GET_ALL_USER_LOTS);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            lotList = parseResultSet(resultSet);
        } catch (SQLException e) {
            String message = "Unable to get all lots";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
            ClosingUtil.close(resultSet);
        }
        return lotList;
    }


    public void update(Lot lot, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLQuery.UPDATE_LOT);
            statement.setInt(1, lot.getQuantity());
            statement.setInt(2, lot.getCurrentPrice());
            statement.setBoolean(3, lot.isNew());
            statement.setInt(4, lot.getId());
            statement.execute();
        } catch (SQLException e) {
            String message = "Unable to update lot";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
        }
    }

    private List<Lot> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Lot> lotList = new ArrayList<>();

        while (resultSet.next()) {
            Lot lot = getLot(resultSet);
            if (lot.isOpen()) {
                lotList.add(lot);
            }
        }
        return lotList;
    }

    private Lot getLot(ResultSet resultSet) throws SQLException {
        Lot lot = Lot.builder().build();
        lot.setId(resultSet.getInt("id"));
        lot.setUserId(resultSet.getInt("user_id"));
        lot.setName(resultSet.getString("name"));
        lot.setDescription(resultSet.getString("description"));
        lot.setQuantity(resultSet.getInt("quantity"));
        lot.setStartPrice(resultSet.getInt("start_price"));
        lot.setMinPrice(resultSet.getInt("min_price"));
        lot.setCurrentPrice(resultSet.getInt("current_price"));
        lot.setPriceCutStep(resultSet.getInt("price_cut_step"));
        lot.setTimeToNextCut(resultSet.getInt("time_to_next_cut"));
        lot.setOpen(resultSet.getBoolean("is_open"));
        lot.setNew(resultSet.getBoolean("is_new"));
        return lot;
    }

}
