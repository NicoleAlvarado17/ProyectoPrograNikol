package fidecompro.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAO<T> {

    private String fileName;

    protected GenericDAO(String fileName) {
        this.fileName = fileName;
    }

    @SuppressWarnings("unchecked")
    public List<T> loadAll() {
        File f = new File(fileName);
        if (!f.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            return (List<T>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveAll(List<T> data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
