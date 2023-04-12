package lt.viko.eif.nlavkart.internetShopSOAP.database.models;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * CartModel class.
 */
@XmlType(name = "carts", propOrder = {"id", "items"})
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "cart")
public class CartModel {
    /**
     * ID of the cart.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    /**
     * Items in the cart.
     */
    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    @OneToMany(targetEntity = ItemModel.class, cascade = CascadeType.ALL)
    private List<ItemModel> items = new ArrayList<>();

    /**
     * No-Args constructor.
     */
    public CartModel() {
    }

    /**
     * Constructor with parameters.
     *
     * @param items itemModels
     * @param id    id
     */
    public CartModel(List<ItemModel> items, int id) {
        this.items = items;
        this.id = id;
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
     * Get itemModels.
     *
     * @return itemModels
     */
    public List<ItemModel> getItems() {
        return items;
    }

    /**
     * Set itemModels.
     *
     * @param items itemModels
     */
    public void setItems(List<ItemModel> items) {
        this.items = items;
    }
}
