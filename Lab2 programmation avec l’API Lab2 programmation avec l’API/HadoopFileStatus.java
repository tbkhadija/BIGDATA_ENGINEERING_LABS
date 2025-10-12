package edu.ensias.bigdata.tp1;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

public class HadoopFileStatus {

    public static void main(String[] args) {
    	
    	
    	 if (args.length != 3) {
    		 System.err.println("❌ Utilisation : HadoopFileStatus <chemin_fichier> <nom_fichier> <nouveau_nom_fichier>");
             System.exit(1);
         }
    	 
    	   // Lecture des paramètres
         String cheminFichier = args[0];       
         String nomFichier = args[1];            
         String nouveauNomFichier = args[2];     
         
         
         
        // Création d'une configuration Hadoop
        Configuration conf = new Configuration();

        // Charger les fichiers de configuration Hadoop
        //conf.set("fs.defaultFS", "hdfs://localhost:9000");

        try {
            // Connexion au système de fichiers HDFS
            FileSystem fs = FileSystem.get(conf);

            // Chemin complet du fichier
            Path nomcomplet = new Path(cheminFichier, nomFichier);

            // Obtenir les informations du fichier
            FileStatus status = fs.getFileStatus(nomcomplet);

            System.out.println("Taille : " + status.getLen() + " octets");
            System.out.println("Nom du fichier : " + status.getPath().getName());
            System.out.println("Réplication : " + status.getReplication());
            System.out.println("Taille du bloc : " + status.getBlockSize());

            // Renommer le fichier
            fs.rename(nomcomplet, new Path(cheminFichier, nouveauNomFichier));
            System.out.println("✅ Fichier renommé avec succès ");

            fs.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
