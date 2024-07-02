import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class DataUtils {

    // Méthode pour sauvegarder une liste de ressources dans un fichier
    public static void saveResources(List<Resource> resources, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(resources);
            System.out.println("Les ressources ont été sauvegardées avec succès dans " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
