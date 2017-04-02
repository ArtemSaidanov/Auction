package by.saidanov.auction.constants;

/**
 * Description: contains constants to Message manager
 *
 * @author Artiom Saidanov.
 */
public class MessageConstants {

    public static final String WRONG_LOGIN_OR_PASSWORD = "message.loginerror";
    public static final String USER_EXISTS = "message.userexsistserror";
    public static final String SUCCESS_REGISTRATION = "message.successregistration";
    public static final String INVALID_NUMBER_FORMAT = "message.invalidnumberformat";
    public static final String EMPTY_FIELDS = "message.emptyfields";
    public static final String DATABASE_ERROR = "message.databaseerror";

    /* Lot messages */
    public static final String LOT_CREATED = "message.newlotcreated";
    public static final String CREATION_FAILED = "message.lotcreationfailed";
    public static final String LOTS_NOT_AVAILABLE = "message.lotnotavailable";
    public static final String LOT_WAS_DELETED = "message.lotdeleted";
    public static final String LOT_DELETE_WAS_FAILED = "message.lotdeletefailed";
    public static final String ID_NOT_FOUND = "message.idnotfound";
    public static final String LOT_BOUGHT = "message.lotboughtsuccessfully";
    public static final String NOT_ENOUGH_MONEY = "message.notenoughmoney";

    /* Account messages*/
    public static final String YOU_ENTERED_BLACK_FIELD = "message.blankmoneyfield";
    public static final String MONEY_WITHDRAWN_SUCCESSFULLY = "message.moneywithdrawnsuccessfully";
    public static final String MONEY_PUTTED_SUCCESSFULLY = "message.moneyputtedsuccessfully";
    public static final String BLANK_QUANTITY = "message.blanckquantity";

    public MessageConstants() {}
}
