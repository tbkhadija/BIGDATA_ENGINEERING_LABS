package edu.ensias.bigdata.tp1;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

public class HDFSInfo {

    public static void main(String[] args) {

        // Vérification du paramètre d’entrée
        if (args.length != 1) {
            System.err.println("❌ Utilisation : HDFSInfo <chemin_complet_du_fichier>");
            System.exit(1);
        }

        // Récupération du chemin du fichier passé en argument
        String cheminFichier = args[0];

        // Création de la configuration Hadoop
        Configuration conf = new Configuration();

        try {
            // Connexion au système de fichiers HDFS
            FileSystem fs = FileSystem.get(conf);

            // Création d’un objet Path vers le fichier
            Path path = new Path(cheminFichier);

            // Vérification de l’existence du fichier
            if (!fs.exists(path)) {
                System.err.println("❌ Le fichier " + cheminFichier + " n’existe pas sur HDFS.");
                fs.close();
                System.exit(1);
            }

            // Récupération des métadonnées du fichier
            FileStatus status = fs.getFileStatus(path);

            System.out.println("📄 Informations générales sur le fichier :");
            System.out.println(" - Nom : " + status.getPath().getName());
            System.out.println(" - Taille totale : " + status.getLen() + " octets");
            System.out.println(" - Facteur de réplication : " + status.getReplication());
            System.out.println(" - Taille d’un bloc : " + status.getBlockSize() + " octets");
            System.out.println("---------------------------------------------");

            // Récupération des informations sur les blocs
            BlockLocation[] blocs = fs.getFileBlockLocations(status, 0, status.getLen());

            System.out.println("📦 Détails des blocs :");
            for (int i = 0; i < blocs.length; i++) {
                BlockLocation bloc = blocs[i];
                System.out.println("🧱 Bloc #" + (i + 1));
                System.out.println("   - Début : " + bloc.getOffset());
                System.out.println("   - Taille : " + bloc.getLength());
                System.out.println("   - Datanodes : " + String.join(", ", bloc.getHosts()));
                System.out.println("---------------------------------------------");
            }

            fs.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
