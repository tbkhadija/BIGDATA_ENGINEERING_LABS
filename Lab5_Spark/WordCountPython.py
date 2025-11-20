# Databricks notebook source
from pyspark.sql.functions import explode, split, col, lower

# Lire ta table "alice" (celle créée depuis l'import de alice.txt)
df = spark.table("alice")

# Afficher le contenu
df.show(5)

# Découper les lignes en mots
words = (
    df.select(explode(split(col("value"), r"\W+")).alias("word"))
      .withColumn("word", lower(col("word")))
      .filter(col("word") != "")
)

# Compter le nombre d'occurrences de chaque mot
wordcount = words.groupBy("word").count().orderBy(col("count").desc())

wordcount.write.mode("overwrite").saveAsTable("alice_wordcount")


# Afficher les 20 mots les plus fréquents
wordcount.show(20)
