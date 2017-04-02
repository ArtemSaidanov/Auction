package by.saidanov.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description: This class closes statements and resultSets.
 *
 * @author Artiom Saidanov.
 */
public class ClosingUtil {

    private ClosingUtil(){}

    public static void close(PreparedStatement statement){
        if(statement != null){
            try{
                statement.close();
            }
            catch(SQLException e){
                AuctionLogger.getInstance().log(ClosingUtil.class, e.getMessage());
            }
        }
    }

    public static void close(ResultSet resultSet){
        if(resultSet != null){
            try{
                resultSet.close();
            }
            catch(SQLException e){
                AuctionLogger.getInstance().log(ClosingUtil.class, e.getMessage());
            }
        }
    }
}
