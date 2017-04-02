package by.saidanov.auction.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Description: this entity is users amount of money that user can spend on lots.
 *
 * @author Artiom Saidanov.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Account extends EntityMarker implements Serializable {

    private int id;
    private int userId;
    private int amountOfMoney;

}
