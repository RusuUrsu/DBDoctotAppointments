package org.example.Repository;

import org.example.model.Identifiable;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FileRepository<T extends Identifiable> implements IRepository<T> {
    private final String filePath;
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public FileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void create(T obj) {
        Map<Integer, T> data = readDataFromFile();
        if (obj.getId() == null) {
            obj.setId(idGenerator.getAndIncrement());
        }
        if (!data.containsKey(obj.getId()))
        {
            data.put(obj.getId(), obj);
            writeDataToFile(data);
        }
        else
            System.out.println("Item with ID " + obj.getId() + " already exists.");
    }

    @Override
    public T read(int id) {
        Map<Integer, T> data = readDataFromFile();
        return data.get(id);
    }

    @Override
    public void update(T obj) {
        Map<Integer, T> data = readDataFromFile();
        int id = obj.getId();
        if (data.containsKey(id)) {
            data.put(id, obj);
            writeDataToFile(data);
        }
        else
            System.out.println("Item with ID " + id + " does not exist.");
    }

    @Override
    public void delete(int id) {
        Map<Integer, T> data = readDataFromFile();
        if (data.remove(id) != null)
            writeDataToFile(data);
        else
            System.out.println("Item with ID " + id + " does not exist.");
    }

    @Override
    public Map<Integer, T> getAll() {
        return readDataFromFile();
    }

    @Override
    public T getById(int id) {
        return readDataFromFile().get(id);
    }

    private Map<Integer, T> readDataFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Map<Integer, T>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    private void writeDataToFile(Map<Integer, T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
