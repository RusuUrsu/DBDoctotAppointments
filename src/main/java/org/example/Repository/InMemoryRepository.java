package org.example.Repository;

import org.example.model.Identifiable;
import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<T extends Identifiable> implements IRepository<T> {
    private final Map<Integer, T> data = new HashMap<>();

    @Override
    public void create(T obj) {
        int id = obj.getId();
        if (!data.containsKey(id)) {
            data.put(id, obj);
        } else {
            System.out.println("Item with ID " + id + " already exists.");
        }
    }

    @Override
    public T read(int id) {
        return data.get(id);
    }

    @Override
    public void update(T obj) {
        int id = obj.getId();
        if (data.containsKey(id)) {
            data.put(id, obj);
        } else {
            System.out.println("Item with ID " + id + " does not exist.");
        }
    }

    @Override
    public void delete(int id) {
        if (data.remove(id) == null) {
            System.out.println("Item with ID " + id + " does not exist.");
        }
    }

    @Override
    public Map<Integer, T> getAll() {
        return new HashMap<>(data);
    }

    @Override
    public T getById(int id) {
        return data.get(id);
    }
}
