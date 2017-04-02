package by.saidanov.constants;

/**
 * Description: class contains all sql queries.
 *
 * @author Artiom Saidanov.
 */
public class SQLQuery {

    /* Queries to "user" table */
    public static final String CHECK_AUTHORIZATION = "SELECT login, password FROM user WHERE login = ? AND password = ?";
    public static final String GET_USER_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    public static final String CHECK_LOGIN = "SELECT login FROM user WHERE login = ?";
    public static final String ADD_USER = "INSERT INTO auction.user(login, password, first_name, last_name, access_level) VALUES (?, ?, ?, ?, ?)";
    public static final String ADD_ACCOUNT = "INSERT INTO auction.account(user_id, amount_of_money) VALUES (?, ?)";
    public static final String GET_LAST_USER_ID = "SELECT MAX(id) FROM user";
    public static final String DELETE_USER = "DELETE FROM user WHERE id = ?";


    /* Queries to "lot" table */
    public static final String ADD_LOT =
                            "INSERT INTO lot(user_id, name, description, quantity, start_price, min_price, " +
                                                    "current_price, price_cut_step, time_to_next_cut) " +
                                                    "VALUES (?,?,?,?,?,?,?,?,?)";
    public static final String GET_ALL_LOTS = "SELECT * FROM lot";
    public static final String GET_ALL_LOTS_EXCEPT_USER = "SELECT * FROM lot WHERE user_id != ?";
    public static final String GET_ALL_USER_LOTS = "SELECT * FROM lot WHERE user_id = ?";
    public static final String GET_LOT_BY_ID = "SELECT * FROM lot WHERE id = ?";
    public static final String DELETE_LOT = "UPDATE lot SET is_open = 0 WHERE id = ?";
    public static final String GET_MAX_ID = "SELECT MAX(id) FROM lot";
    public static final String UPDATE_LOT = "UPDATE lot SET quantity = ?, current_price = ?, is_new = ? WHERE id = ?";


    public static final String GET_ACCOUNT_BY_ID = "SELECT * FROM account WHERE id = ?";
    public static final String GET_ACCOUNT_BY_USER_ID = "SELECT * FROM account WHERE user_id = ?";
    public static final String UPDATE_ACCOUNT_AMOUNT_OF_MONEY = "UPDATE account SET amount_of_money = ? WHERE user_id = ?";

    public static final String DELETE_ACCOUNT = "DELETE FROM account WHERE user_id = ?";

    public static final java.lang.String DELETE_LOT_FROM_DB = "DELETE FROM lot WHERE user_id = ?";
}