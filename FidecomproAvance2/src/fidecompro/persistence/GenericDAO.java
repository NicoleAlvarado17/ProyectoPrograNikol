package fidecompro.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAO<T extends Serializable> {

    private final String archivo;

    protected GenericDAO(String archivo) {
        this.archivo = archivo;
    }

    /**
     * Lee y devuelve la lista de objetos T desde el fichero. Si no existe aún,
     * devuelve lista vacía.
     */
    @SuppressWarnings("unchecked")
    public List<T> cargar() {
        File f = new File(archivo);
        if (!f.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois
                = new ObjectInputStream(new FileInputStream(f))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Sobrescribe el fichero con la lista completa de objetos T.
     */
    public void guardar(List<T> lista) {
        try (ObjectOutputStream oos
                = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
