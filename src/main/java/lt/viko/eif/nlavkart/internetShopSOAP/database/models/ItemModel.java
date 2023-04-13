package lt.viko.eif.nlavkart.internetShopSOAP.database.models;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * ItemModel class.
 */
@XmlType(name = "items", propOrder = {"id", "name", "description", "category", "price", "quantity"})
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "item")
public class ItemModel {
    /**
     * ID of the item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    /**
     * Name of the item.
     */
    private String name;
    /**
     * Description of the item.
     */
    private String description;
    /**
     * CategoryModel of the item.
     */
    @ManyToOne(targetEntity = CategoryModel.class, cascade = CascadeType.ALL)
    private CategoryModel category;
    /**
     * Price of the item.
     */
    private double price;
    /**
     * Quantity of the item.
     */
    private int quantity;

    /**
     * No-Args constructor.
     */
    public ItemModel() {
    }

    /**
     * Constructor with parameters.
     *
     * @param id          id
     * @param name        name
     * @param description description
     * @param category    categoryModel
     * @param price       price
     * @param quantity    quantity
     */
    public ItemModel(int id, String name, String description, CategoryModel category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
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
     * Get name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description.
     *
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get categoryModel.
     *
     * @return categoryModel
     */
    public CategoryModel getCategory() {
        return category;
    }

    /**
     * Set categoryModel.
     *
     * @param categoryModel categoryModel
     */
    public void setCategory(CategoryModel categoryModel) {
        this.category = categoryModel;
    }

    /**
     * Get price.
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set price.
     *
     * @param price price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get quantity.
     *
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set quantity.
     *
     * @param quantity quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
