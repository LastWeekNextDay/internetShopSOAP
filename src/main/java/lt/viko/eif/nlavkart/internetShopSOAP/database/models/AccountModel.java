package lt.viko.eif.nlavkart.internetShopSOAP.database.models;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

/**
 * AccountModel class.
 */
@XmlType(name = "accounts", propOrder = {"id", "username", "password", "cart"})
@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "account")
public class AccountModel {
    /**
     * ID of the account.
     */
    @XmlAttribute(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    /**
     * Username of the account.
     */
    private String username;
    /**
     * Password of the account.
     */
    private String password;
    /**
     * CartModel of the account.
     */
    @OneToOne(targetEntity = CartModel.class, cascade = {CascadeType.ALL, CascadeType.MERGE})
    private CartModel cart;

    /**
     * Check if account is deleted.
     */
    private boolean deleted;

    /**
     * No-Args constructor.
     */
    public AccountModel() {
    }

    /**
     * Constructor with parameters.
     *
     * @param id       id
     * @param username username
     * @param password password
     * @param cart     cartModel
     * @param deleted  deleted
     */

    public AccountModel(int id, String username, String password, CartModel cart, boolean deleted) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cart = cart;
        this.deleted = deleted;
    }

    /**
     * Get id.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username.
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password.
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get cart.
     *
     * @return cart
     */
    public CartModel getCart() {
        return cart;
    }

    /**
     * Set cartModel.
     *
     * @param cart cartModel
     */
    public void setCart(CartModel cart) {
        this.cart = cart;
    }

    /**
     * Get deleted.
     *
     * @return deleted
     */
    public boolean getDeleted() {
        return deleted;
    }

    /**
     * Set deleted.
     *
     * @param deleted deleted
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
