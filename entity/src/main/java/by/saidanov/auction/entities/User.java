package by.saidanov.auction.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Description: this entity describes all application users
 *
 * @author Artiom Saidanov.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class User extends EntityMarker implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private int accessLevel;
}
