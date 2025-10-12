package edu.ensias.bigdata.tp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

public class ReadHDFS {

    public static void main(String[] args) {

        // Vérification du paramètre
        if (args.length != 1) {
            System.err.println("❌ Utilisation : ReadHDFS <chemin_complet_du_fichier>");
            System.exit(1);
        }

        // Récupération du chemin passé en argument
        String cheminFichier = args[0];

        // Création de la configuration Hadoop
        Configuration conf = new Configuration();

        try {
            // Connexion au système de fichiers HDFS
            FileSystem fs = FileSystem.get(conf);

            // Création d’un objet Path pour le fichier à lire
            Path path = new Path(cheminFichier);

            // Vérification que le fichier existe
            if (!fs.exists(path)) {
                System.err.println("❌ Le fichier " + cheminFichier + " n’existe pas sur HDFS.");
                fs.close();
                System.exit(1);
            }

            // Ouverture d’un flux de lecture HDFS
            try (FSDataInputStream inStream = fs.open(path);
                 InputStreamReader isr = new InputStreamReader(inStream, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(isr)) {

                System.out.println("📖 Contenu du fichier : " + cheminFichier);
                System.out.println("---------------------------------------------");

                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }

                System.out.println("---------------------------------------------");
                System.out.println("✅ Lecture terminée avec succès !");
            }

            fs.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
