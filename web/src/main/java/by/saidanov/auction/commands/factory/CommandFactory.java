package by.saidanov.auction.commands.factory;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.commands.impl.DefaultCommand;
import by.saidanov.auction.commands.impl.user.LoginCommand;
import by.saidanov.auction.utils.RequestParamParser;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: This class creates commands.
 *
 * @author Artiom Saidanov.
 */
public class CommandFactory {

    private volatile static CommandFactory instance;

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        if (instance == null) {
            synchronized (CommandFactory.class) {
                if (instance == null) {
                    instance = new CommandFactory();
                }
            }
        }
        return instance;
    }

    /**
     * This method defines command that will be executed by "command" parameter from request
     */
    public BaseCommand defineCommand(HttpServletRequest request) {
        BaseCommand current = null;
        try {
            CommandType type = RequestParamParser.getCommandType(request);
            current = type.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            current = new DefaultCommand();
        }
        return current;
    }
}
