package by.saidanov.auction.entities;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Description: Describes all lots in auction
 *
 * @author Artiom Saidanov.
 */
@Entity
@Table
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lot extends EntityMarker{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    /* number of items */
    @Column
    private Integer quantity;

    /* max price of one item */
    @Column(name = "start_price")
    private Integer startPrice;

    /* cut-off price of one item */
    @Column(name = "min_price")
    private Integer minPrice;

    /* current price of one item */
    @Column(name = "current_price")
    private Integer currentPrice;

    /* the amount of money by which the price is reduced in one step */
    @Column(name = "price_cut_step")
    private Integer priceCutStep;

    /* time to next price cut */
    @Column(name = "time_to_next_cut")
    private Integer timeToNextCut;

    /* whether the lot is sold or not */
    @Column(name = "is_open")
    private boolean isOpen;

    @Column(name = "is_new")
    private boolean isNew;

    public Lot(String name, String description, User user, Integer quantity, Integer startPrice, Integer minPrice,
               Integer currentPrice, Integer priceCutStep, Integer timeToNextCut, boolean isOpen, boolean isNew) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.quantity = quantity;
        this.startPrice = startPrice;
        this.minPrice = minPrice;
        this.currentPrice = currentPrice;
        this.priceCutStep = priceCutStep;
        this.timeToNextCut = timeToNextCut;
        this.isOpen = isOpen;
        this.isNew = isNew;
    }

    public Lot() {
    }

    public static LotBuilder builder() {
        return new LotBuilder();
    }


    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public User getUser() {
        return this.user;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Integer getStartPrice() {
        return this.startPrice;
    }

    public Integer getMinPrice() {
        return this.minPrice;
    }

    public Integer getCurrentPrice() {
        return this.currentPrice;
    }

    public Integer getPriceCutStep() {
        return this.priceCutStep;
    }

    public Integer getTimeToNextCut() {
        return this.timeToNextCut;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean isNew() {
        return this.isNew;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setStartPrice(Integer startPrice) {
        this.startPrice = startPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public void setCurrentPrice(Integer currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setPriceCutStep(Integer priceCutStep) {
        this.priceCutStep = priceCutStep;
    }

    public void setTimeToNextCut(Integer timeToNextCut) {
        this.timeToNextCut = timeToNextCut;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Lot)) return false;
        final Lot other = (Lot) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$quantity = this.getQuantity();
        final Object other$quantity = other.getQuantity();
        if (this$quantity == null ? other$quantity != null : !this$quantity.equals(other$quantity)) return false;
        final Object this$startPrice = this.getStartPrice();
        final Object other$startPrice = other.getStartPrice();
        if (this$startPrice == null ? other$startPrice != null : !this$startPrice.equals(other$startPrice))
            return false;
        final Object this$minPrice = this.getMinPrice();
        final Object other$minPrice = other.getMinPrice();
        if (this$minPrice == null ? other$minPrice != null : !this$minPrice.equals(other$minPrice)) return false;
        final Object this$currentPrice = this.getCurrentPrice();
        final Object other$currentPrice = other.getCurrentPrice();
        if (this$currentPrice == null ? other$currentPrice != null : !this$currentPrice.equals(other$currentPrice))
            return false;
        final Object this$priceCutStep = this.getPriceCutStep();
        final Object other$priceCutStep = other.getPriceCutStep();
        if (this$priceCutStep == null ? other$priceCutStep != null : !this$priceCutStep.equals(other$priceCutStep))
            return false;
        final Object this$timeToNextCut = this.getTimeToNextCut();
        final Object other$timeToNextCut = other.getTimeToNextCut();
        if (this$timeToNextCut == null ? other$timeToNextCut != null : !this$timeToNextCut.equals(other$timeToNextCut))
            return false;
        if (this.isOpen() != other.isOpen()) return false;
        if (this.isNew() != other.isNew()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $quantity = this.getQuantity();
        result = result * PRIME + ($quantity == null ? 43 : $quantity.hashCode());
        final Object $startPrice = this.getStartPrice();
        result = result * PRIME + ($startPrice == null ? 43 : $startPrice.hashCode());
        final Object $minPrice = this.getMinPrice();
        result = result * PRIME + ($minPrice == null ? 43 : $minPrice.hashCode());
        final Object $currentPrice = this.getCurrentPrice();
        result = result * PRIME + ($currentPrice == null ? 43 : $currentPrice.hashCode());
        final Object $priceCutStep = this.getPriceCutStep();
        result = result * PRIME + ($priceCutStep == null ? 43 : $priceCutStep.hashCode());
        final Object $timeToNextCut = this.getTimeToNextCut();
        result = result * PRIME + ($timeToNextCut == null ? 43 : $timeToNextCut.hashCode());
        result = result * PRIME + (this.isOpen() ? 79 : 97);
        result = result * PRIME + (this.isNew() ? 79 : 97);
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Lot;
    }

    public String toString() {
        return "by.saidanov.auction.entities.Lot(id=" + this.getId() + ", name=" + this.getName()
                                + ", description=" + this.getDescription() + ", quantity=" + this.getQuantity()
                                + ", startPrice=" + this.getStartPrice() + ", minPrice=" + this.getMinPrice()
                                + ", currentPrice=" + this.getCurrentPrice() + ", priceCutStep=" + this.getPriceCutStep()
                                + ", timeToNextCut=" + this.getTimeToNextCut() + ", isOpen=" + this.isOpen()
                                + ", isNew=" + this.isNew() + ")";
    }

    public static class LotBuilder {
        private Integer id;
        private String name;
        private String description;
        private User user;
        private Integer quantity;
        private Integer startPrice;
        private Integer minPrice;
        private Integer currentPrice;
        private Integer priceCutStep;
        private Integer timeToNextCut;
        private boolean isOpen;
        private boolean isNew;

        LotBuilder() {
        }

        public Lot.LotBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public Lot.LotBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Lot.LotBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Lot.LotBuilder user(User user) {
            this.user = user;
            return this;
        }

        public Lot.LotBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Lot.LotBuilder startPrice(Integer startPrice) {
            this.startPrice = startPrice;
            return this;
        }

        public Lot.LotBuilder minPrice(Integer minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public Lot.LotBuilder currentPrice(Integer currentPrice) {
            this.currentPrice = currentPrice;
            return this;
        }

        public Lot.LotBuilder priceCutStep(Integer priceCutStep) {
            this.priceCutStep = priceCutStep;
            return this;
        }

        public Lot.LotBuilder timeToNextCut(Integer timeToNextCut) {
            this.timeToNextCut = timeToNextCut;
            return this;
        }

        public Lot.LotBuilder isOpen(boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }

        public Lot.LotBuilder isNew(boolean isNew) {
            this.isNew = isNew;
            return this;
        }

        public Lot build() {
            return new Lot(name, description, user, quantity, startPrice, minPrice,
                                    currentPrice, priceCutStep, timeToNextCut, isOpen, isNew);
        }

        public String toString() {
            return "by.saidanov.auction.entities.Lot.LotBuilder(id=" + this.id + ", name=" + this.name
                                    + ", description=" + this.description + ", quantity=" + this.quantity
                                    + ", startPrice=" + this.startPrice + ", minPrice=" + this.minPrice
                                    + ", currentPrice=" + this.currentPrice + ", priceCutStep=" + this.priceCutStep
                                    + ", timeToNextCut=" + this.timeToNextCut + ", isOpen=" + this.isOpen
                                    + ", isNew=" + this.isNew + ")";
        }
    }
}
