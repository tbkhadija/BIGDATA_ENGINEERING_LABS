package edu.ensias.bigdata.tp1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

public class WriteHDFS {

    public static void main(String[] args) {

        // Vérification des arguments
        if (args.length != 2) {
            System.err.println("❌ Utilisation : WriteHDFS <chemin_complet_du_fichier> <texte_a_ecrire>");
            System.exit(1);
        }

        String cheminFichier = args[0];
        String texte = args[1];

        Configuration conf = new Configuration();

        try {
            FileSystem fs = FileSystem.get(conf);
            Path path = new Path(cheminFichier);

            if (fs.exists(path)) {
                System.out.println("⚠️ Le fichier existe déjà sur HDFS : " + cheminFichier);
                System.out.println("Le fichier ne sera pas écrasé.");
            } else {
                try (FSDataOutputStream outStream = fs.create(path);
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream, StandardCharsets.UTF_8))) {

                    writer.write("Bonjour tout le monde !");
                    writer.newLine();
                    writer.write(texte);
                    writer.newLine();

                    System.out.println("✅ Fichier créé et écrit avec succès : " + cheminFichier);
                }
            }

            fs.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
