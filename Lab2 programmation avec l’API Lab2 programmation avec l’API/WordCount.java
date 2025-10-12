package mapreducelab;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {

    public static void main(String[] args) throws Exception {

        // Création de la configuration Hadoop
        Configuration conf = new Configuration();

        // Définition du job et de son nom
        Job job = Job.getInstance(conf, "word count");

        // Classe principale
        job.setJarByClass(WordCount.class);

        // Classe qui fait le map
        job.setMapperClass(TokenizerMapper.class);

        // Classe qui fait le shuffling et le reduce
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);

        // Définition des types de sortie
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // Spécifier le fichier d'entrée
        FileInputFormat.addInputPath(job, new Path(args[0]));

        // Spécifier le fichier contenant le résultat
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Exécuter le job et terminer le programme
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
