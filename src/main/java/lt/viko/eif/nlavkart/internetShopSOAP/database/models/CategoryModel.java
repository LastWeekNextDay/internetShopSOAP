package lt.viko.eif.nlavkart.internetShopSOAP.database.models;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * CategoryModel class.
 */

@XmlType(name = "categories", propOrder = {"id", "name", "description"})
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "category")
public class CategoryModel {
    /**
     * ID of the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    /**
     * Name of the category.
     */
    private String name;
    /**
     * Description of the category.
     */
    private String description;

    /**
     * No-Args constructor.
     */
    public CategoryModel() {
    }

    /**
     * Constructor with parameters.
     *
     * @param id          id
     * @param name        name
     * @param description description
     */
    public CategoryModel(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
}
