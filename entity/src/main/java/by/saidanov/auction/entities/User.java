package by.saidanov.auction.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.criterion.LogicalExpression;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Description: this entity describes all application users
 *
 * @author Artiom Saidanov.
 */
@Entity
@Table
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends EntityMarker {

    public static final long serialVersionUID = 100L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String login;

    @Column
    private String password;

    @Column(name = "access_level")
    private Integer accessLevel;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user",
                            cascade = CascadeType.ALL)
    @Access(AccessType.PROPERTY)
    private Account account;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Lot> lots = new HashSet<>();

    public User() {}

    public User(Integer id, String firstName, String lastName, String login, String password, Integer accessLevel, Account account, Set<Lot> lots) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.accessLevel = accessLevel;
        this.account = account;
        this.lots = lots;
    }


    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public Integer getAccessLevel() {
        return this.accessLevel;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Lot> getLots() {
        return lots;
    }

    public void setLots(Set<Lot> lots) {
        this.lots = lots;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        final User other = (User) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$login = this.getLogin();
        final Object other$login = other.getLogin();
        if (this$login == null ? other$login != null : !this$login.equals(other$login)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        final Object this$accessLevel = this.getAccessLevel();
        final Object other$accessLevel = other.getAccessLevel();
        if (this$accessLevel == null ? other$accessLevel != null : !this$accessLevel.equals(other$accessLevel))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $login = this.getLogin();
        result = result * PRIME + ($login == null ? 43 : $login.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $accessLevel = this.getAccessLevel();
        result = result * PRIME + ($accessLevel == null ? 43 : $accessLevel.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof User;
    }

    public String toString() {
        return "by.saidanov.auction.entities.User(id=" + this.getId() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", login=" + this.getLogin() + ", password=" + this.getPassword() + ", accessLevel=" + this.getAccessLevel() + ")";
    }


    public static class UserBuilder {
        private Integer id;
        private String firstName;
        private String lastName;
        private String login;
        private String password;
        private Integer accessLevel;
        private Account account;
        private Set<Lot> lots = new HashSet<>();

        UserBuilder() {
        }

        public User.UserBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public User.UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public User.UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public User.UserBuilder login(String login) {
            this.login = login;
            return this;
        }

        public User.UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public User.UserBuilder accessLevel(Integer accessLevel) {
            this.accessLevel = accessLevel;
            return this;
        }

        public User.UserBuilder account(Account account) {
            this.account = account;
            return this;
        }

        public User.UserBuilder lots(Set<Lot> lots){
            if (lots != null){
                this.lots = lots;
            }else {
             this.lots = new HashSet<>();
            }
            return this;
        }

        public User build() {
            return new User(id, firstName, lastName, login, password, accessLevel, account, lots);
        }

        public String toString() {
            return "by.saidanov.auction.entities.User.UserBuilder(id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", login=" + this.login + ", password=" + this.password + ", accessLevel=" + this.accessLevel + ", account=" + this.account + ")";
        }
    }
}
