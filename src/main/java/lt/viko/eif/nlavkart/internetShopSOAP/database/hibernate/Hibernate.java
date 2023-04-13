package lt.viko.eif.nlavkart.internetShopSOAP.database.hibernate;

import lt.viko.eif.nlavkart.internetShopSOAP.database.models.AccountModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.CategoryModel;
import lt.viko.eif.nlavkart.internetShopSOAP.database.models.ItemModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.lang.module.Configuration;
import java.util.List;

/**
 * Hibernate class.
 */
public class Hibernate {
    /**
     * Configuration.
     */
    private Configuration configuration;
    /**
     * Registry.
     */
    private StandardServiceRegistry registry;
    /**
     * Session.
     */
    private Session session;
    /**
     * Session factory.
     */
    private SessionFactory sessionFactory;
    /**
     * Transaction.
     */
    private Transaction transaction;
    /**
     * Is transaction active.
     */
    private boolean isTransactionActive = false;
    /**
     * No-argument constructor.
     */
    public Hibernate() {
    }
    /**
     * Get configuration.
     */
    public Configuration getConfiguration() {
        return configuration;
    }
    /**
     * Get session.
     */
    public Session getSession() {
        return session;
    }
    /**
     * Set configuration.
     *
     * @param configuration configuration
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
    /**
     * Set session.
     *
     * @param session session
     */
    public void setSession(Session session) {
        this.session = session;
    }
    /**
     * Get session factory.
     */
    public org.hibernate.SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    /**
     * Set session factory.
     *
     * @param sessionFactory session factory
     */
    public void setSessionFactory(org.hibernate.SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    /**
     * Get registry.
     */
    public StandardServiceRegistry getRegistry() {
        return registry;
    }
    /**
     * Set registry.
     *
     * @param registry registry
     */
    public void setRegistry(StandardServiceRegistry registry) {
        this.registry = registry;
    }
    /**
     * Get is transaction active.
     */
    public boolean getIsTransactionActive() {
        return isTransactionActive;
    }
    /**
     * Set is transaction active.
     *
     * @param isTransactionActive is transaction active
     */
    public void setIsTransactionActive(boolean isTransactionActive) {
        this.isTransactionActive = isTransactionActive;
    }
    /**
     * Create session factory.
     */
    public void createSessionFactory(){
        if (sessionFactory == null) {
            try {
                registry = new StandardServiceRegistryBuilder().configure().build();
                MetadataSources sources = new MetadataSources(registry);
                Metadata setData = sources.getMetadataBuilder().build();
                sessionFactory = setData.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
    }
    /**
     * Open transaction.
     */
    public void openTransaction(){
        if (!isTransactionActive) {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            isTransactionActive = true;

        }
    }
    /**
     * Close transaction.
     */
    public void closeTransaction(){
        if (isTransactionActive) {
            transaction.commit();
            session.close();
            isTransactionActive = false;
        }
    }
    /**
     * Save object to session.
     *
     * @param object object
     */
    public void save(Object object){
        session.save(object);
    }

    /**
     * Query session.
     *
     * @param query query
     * @param resultType result type
     */
    public List<AccountModel> queryAccountModel(String query, boolean resultType) {
        if (resultType) {
            return session.createQuery(query, AccountModel.class).list();
        } else {
            session.createQuery(query).executeUpdate();
            return null;
        }
    }

    public List<CategoryModel> queryCategoryModel(String query, boolean resultType) {
        if (resultType) {
            return session.createQuery(query, CategoryModel.class).list();
        } else {
            session.createQuery(query).executeUpdate();
            return null;
        }
    }

    public List<ItemModel> queryItemModel(String query, boolean resultType) {
        if (resultType) {
            return session.createQuery(query, ItemModel.class).list();
        } else {
            session.createQuery(query).executeUpdate();
            return null;
        }
    }
}
