package org.example.Repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.example.model.Identifiable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The {@code DBRepo} class provides a concrete implementation of the {@code IRepository} interface
 * for repositories interacting with a database using Hibernate, without custom exception handling.
 *
 * @param <T> The type of entity managed by the repository.
 */
public class DBRepo<T extends Identifiable> implements IRepository<T> {
    private final SessionFactory sessionFactory;
    private final Class<T> entityType;

    public DBRepo(SessionFactory sessionFactory, Class<T> entityType) {
        this.sessionFactory = sessionFactory;
        this.entityType = entityType;
    }

    @Override
    public void create(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(entity); // Save new entity
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // Print stack trace for debugging
        } finally {
            session.close();
        }
    }

    @Override
    public T read(int id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(entityType, id); // Fetch the entity by ID
        } catch(Exception e){
            e.printStackTrace(); // Print stack trace for debugging
            return null; // Return null if entity is not found or there's an error
        } finally {
            session.close();
        }
    }

    @Override
    public void update(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(entity); // Update the entity with new data
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // Print stack trace for debugging
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            T entity = session.get(entityType, id); // Get the entity by ID
            if (entity != null) {
                session.delete(entity); // Delete the entity
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace(); // Print stack trace for debugging
        } finally {
            session.close();
        }
    }

    @Override
    public Map<Integer, T> getAll() {
        Session session = sessionFactory.openSession();
        try {
            List<T> resultList = session.createQuery("FROM " + entityType.getSimpleName(), entityType).getResultList();
            // Convert the list of entities to a map by their ID
            return resultList.stream().collect(Collectors.toMap(Identifiable::getId, entity -> entity));
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return null; // Return null in case of an error
        } finally {
            session.close();
        }
    }

    @Override
    public T getById(int id) {
        return read(id); // Directly reuse the `read` method
    }
}
