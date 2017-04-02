package by.saidanov.auction.utils;

import by.saidanov.auction.commands.factory.CommandType;
import by.saidanov.auction.constants.Parameters;
import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.Lot;
import by.saidanov.auction.util.UserType;
import by.saidanov.auction.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.saidanov.auction.constants.Parameters.*;

/**
 * Description: this class works with all possible request parameters
 *
 * @author Artiom Saidanov.
 */
public class RequestParamParser {


    /**
     * @return "command" parameter from request
     */
    public static CommandType getCommandType(HttpServletRequest request) {
        String commandName = request.getParameter(COMMAND);
        CommandType commandType = CommandType.DEFAULT;
        if (commandName != null) {
            commandType = CommandType.valueOf(commandName.toUpperCase());
        }
        return commandType;
    }

    /**
     * @return "userType" parameter from request
     */
    public static UserType getUserType(HttpServletRequest request) {
        return (UserType) request.getSession().getAttribute(USER_TYPE);
    }

    /**
     * This method builds user from request params.
     * For LoginCommand User object has only login and password
     * For RegistrationCommand User object has all fields
     *
     * @return @{@link User} object
     */
    public static User getUser(HttpServletRequest request) {
        User user = User.builder().build();
        int id = 0;
        if (request.getParameter(USER_ID) != null) {
            id = Integer.parseInt(request.getParameter(USER_ID));
        }
        int accessLevel = 0;
        if (request.getParameter(USER_ACCESS_LEVEL) != null) {
            accessLevel = Integer.parseInt(request.getParameter(USER_ACCESS_LEVEL));
        }
        String firstName = request.getParameter(USER_FIRST_NAME);
        String lastName = request.getParameter(USER_LAST_NAME);
        String login = request.getParameter(USER_LOGIN);
        String password = request.getParameter(USER_PASSWORD);
        if (firstName != null & lastName != null & login != null & password != null) {
            user = User.builder()
                                    .login(login)
                                    .password(password)
                                    .firstName(firstName)
                                    .lastName(lastName)
                                    .accessLevel(accessLevel)
                                    .id(id)
                                    .build();
            return user;
        } else if (login != null & password != null) {
            user = User.builder()
                                    .login(login)
                                    .password(password)
                                    .build();
            return user;
        }
        return user;
    }

    /**
     * This method builds lot from request params.
     * Also method provides defense from null parameters(if user entered blank field)
     *
     * @return @{@link Lot} object
     */
    public static Lot getLot(HttpServletRequest request) {
        Lot lot = null;
        String name = request.getParameter(LOT_NAME);
        String description = request.getParameter(LOT_DESC);
        String quantity = request.getParameter(LOT_QUANTITY);
        String startPrice = request.getParameter(LOT_START_PRICE);
        String minPrice = request.getParameter(LOT_MIN_PRICE);
        String priceCutStep = request.getParameter(PRICE_CUT_STEP);
        String timeToNextCut = request.getParameter(TIME_TO_NEXT_CUT);
        if (!name.equals("") & !description.equals("")
                                & !quantity.equals("") & !startPrice.equals("")
                                & !minPrice.equals("") & !priceCutStep.equals("")
                                & !timeToNextCut.equals("")) {
            lot = Lot.builder().name(name)
                                    .description(description)
                                    .quantity(Integer.parseInt(quantity))
                                    .startPrice(Integer.parseInt(startPrice))
                                    .currentPrice(Integer.parseInt(startPrice))
                                    .minPrice(Integer.parseInt(minPrice))
                                    .priceCutStep(Integer.parseInt(priceCutStep))
                                    .timeToNextCut(Integer.parseInt(timeToNextCut))
                                    .build();
        }
        return lot;
    }

    /**
     * This method gets lot id from request and provides that id will be not null
     *
     * @return lot id
     */
    public static int getLotId(HttpServletRequest request) {
        String lotId = request.getParameter(LOT_ID);
        int id;
        if (lotId != null) {
            id = Integer.parseInt(lotId);
            return id;
        }
        return -1;
    }

    /**
     * This method gets account from request
     */
    public static Account getAccount(HttpServletRequest request) {
        Account account = Account.builder().build();
        String money = request.getParameter(Parameters.AMOUNT_OF_MONEY);
        int amountOfMoney;
        if (money != null && !money.equals("")) {
            amountOfMoney = Integer.parseInt(money);
            account.setAmountOfMoney(amountOfMoney);
            return account;
        }
        return null;
    }

    /**
     * This method gets defines which action client chose to work with account - take money or put money
     */
    public static String getAction(HttpServletRequest request) {
        String action = request.getParameter("action");
        if (action != null) {
            return action;
        }
        return null;
    }

    /**
     * This method gets amount of money from request
     */
    public static int getMoney(HttpServletRequest request) {
        String money = request.getParameter("amount");
        if (money != null && !money.equals("")) {
            int amountOfMoney = Integer.parseInt(money);
            return amountOfMoney;
        }
        return -1;
    }

    public static User getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        return user;
    }

    public static Integer getLotQuantity(HttpServletRequest request) {
        String quantityStr = request.getParameter("quantity");
        if (quantityStr != null && !quantityStr.equals("")) {
            return Integer.parseInt(quantityStr);
        }
        return -1;
    }

    public static String getPage(HttpServletRequest request) {
        String page = request.getParameter("page");
        if (page != null && !page.equals("")) {
            return page;
        } else return null;
    }
}
