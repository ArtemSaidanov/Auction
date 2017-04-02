package by.saidanov.auction.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description: Describes all lots in auction
 *
 * @author Artiom Saidanov.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Lot extends EntityMarker {
    private int id;
    private int userId;
    private String name;
    private String description;

    /* number of items */
    private int quantity;

    /* max price of one item */
    private int startPrice;

    /* cut-off price of one item */
    private int minPrice;

    /* current price of one item */
    private int currentPrice;

    /* TODO comment */
    private int priceCutStep;

    /* time to next price cut */
    private int timeToNextCut;

    /* whether the lot is sold or not */
    private boolean isOpen;

    private boolean isNew;

}
