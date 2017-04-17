package by.saidanov.auction.entities;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Description: this entity is users amount of money that user can spend on lots.
 *
 * @author Artiom Saidanov.
 */

@Entity
@Table
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Account extends EntityMarker {

    public static final long serialVersionUID = 100L;

    @Id
    @GenericGenerator(
                            name = "foreignKeyGenerator",
                            strategy = "foreign",
                            parameters = @Parameter(name = "property", value = "user"))
    @GeneratedValue(generator = "foreignKeyGenerator")
    private Integer userId;

    @Column(name = "amount_of_money")
    private Integer amountOfMoney;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @Access(AccessType.PROPERTY)
    private User user;

    public Account() {
    }

    public Account(Integer userId, Integer amountOfMoney, User user) {
        this.amountOfMoney = amountOfMoney;
        this.user = user;
        this.userId = userId;
    }


    public static AccountBuilder builder() {
        return new AccountBuilder();
    }

    public Integer getUserId() {
        return this.userId;
    }

    public Integer getAmountOfMoney() {
        return this.amountOfMoney;
    }

    public User getUser() {
        return this.user;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setAmountOfMoney(Integer amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Integer getId() {
        return this.getUserId();
    }

    public String toString() {
        return "by.saidanov.auction.entities.Account(userId=" + this.getUserId() + ", amountOfMoney=" + this.getAmountOfMoney() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (userId != null ? !userId.equals(account.userId) : account.userId != null) return false;
        return amountOfMoney != null ? amountOfMoney.equals(account.amountOfMoney) : account.amountOfMoney == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (amountOfMoney != null ? amountOfMoney.hashCode() : 0);
        return result;
    }

    public static class AccountBuilder {
        private Integer userId;
        private Integer amountOfMoney;
        private User user;

        AccountBuilder() {
        }

        public Account.AccountBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Account.AccountBuilder amountOfMoney(Integer amountOfMoney) {
            this.amountOfMoney = amountOfMoney;
            return this;
        }

        public Account.AccountBuilder user(User user) {
            this.user = user;
            return this;
        }

        public Account build() {
            return new Account(userId, amountOfMoney, user);
        }

        public String toString() {
            return "by.saidanov.auction.entities.Account.AccountBuilder(userId=" + this.userId + ", amountOfMoney=" + this.amountOfMoney + ")";
        }
    }
}
