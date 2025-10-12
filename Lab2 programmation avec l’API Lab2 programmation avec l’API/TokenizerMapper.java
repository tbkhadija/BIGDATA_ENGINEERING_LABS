package mapreducelab;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

    // Constante : chaque mot vaut 1
    private static final IntWritable one = new IntWritable(1);

    // Objet Text réutilisé pour stocker les mots
    private final Text word = new Text();

    @Override
    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        // Affiche la position (offset) pour le débogage
        System.out.println(key.toString());

        // Découpe la ligne en mots
        StringTokenizer itr = new StringTokenizer(value.toString());

        // Parcourt chaque mot et écrit (mot, 1)
        while (itr.hasMoreTokens()) {
            word.set(itr.nextToken());
            context.write(word, one);
        }
    }
}
