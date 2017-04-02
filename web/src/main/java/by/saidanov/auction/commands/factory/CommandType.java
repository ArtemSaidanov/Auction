package by.saidanov.auction.commands.factory;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.commands.impl.DefaultCommand;
import by.saidanov.auction.commands.impl.user.GoBackCommand;
import by.saidanov.auction.commands.impl.account.MoneyPageCommand;
import by.saidanov.auction.commands.impl.account.PutMoneyCommand;
import by.saidanov.auction.commands.impl.account.TakeMoneyCommand;
import by.saidanov.auction.commands.impl.lot.*;
import by.saidanov.auction.commands.impl.user.GoToRegistrationCommand;
import by.saidanov.auction.commands.impl.user.LoginCommand;
import by.saidanov.auction.commands.impl.user.LogoutCommand;
import by.saidanov.auction.commands.impl.user.RegistrationCommand;

/**
 * Description: contains all commands
 *
 * @author Artiom Saidanov.
 */
public enum CommandType {

    //user commands
    LOGIN, LOGOUT, REGISTRATION, GOTOREGISTRATION, BACK, DEFAULT,

    //lot commands
    CREATELOT, LOTLIST, DELETELOT, BUYLOT, SHOWLOT, GOTOCREATELOT, GOTOCLIENTLOTS,

    MONEYPAGE, TAKEMONEY, PUTMONEY;

    public BaseCommand getCurrentCommand() throws EnumConstantNotPresentException {
        switch (this) {
            case LOGIN:
                return new LoginCommand();

            case LOGOUT:
                return new LogoutCommand();

            case REGISTRATION:
                return new RegistrationCommand();

            case GOTOREGISTRATION:
                return new GoToRegistrationCommand();

            case BACK:
                return new GoBackCommand();

            case DELETELOT:
                return new DeleteLotCommand();

            case CREATELOT:
                return new CreateLotCommand();

            case LOTLIST:
                return new LotListCommand();

            case BUYLOT:
                return new BuyLotCommand();

            case SHOWLOT:
                return new ShowLotCommand();

            case GOTOCREATELOT:
                return new GoToCreateLot();

            case GOTOCLIENTLOTS:
                return new GoToClientLots();

            case TAKEMONEY:
                return new TakeMoneyCommand();

            case MONEYPAGE:
                return new MoneyPageCommand();

            case PUTMONEY:
                return new PutMoneyCommand();

            default:
                return new DefaultCommand();
        }
    }
}
