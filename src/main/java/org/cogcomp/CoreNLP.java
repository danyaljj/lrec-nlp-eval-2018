package org.cogcomp;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by daniel on 1/24/18.
 */
public class CoreNLP {
//    public static String f = "/Users/daniel/Desktop/corpusFilesPlain";
    public static String f = "/scratch/lrec-eval/corpusFilesPlain/";

    public static void main(String[] args) throws IOException {
        if(args[0].contains("sentence")) {
            sentenceSplitter();
        }
        else if(args[0].contains("token")) {
            tokenize();
        }
        else if(args[0].contains("ner")) {
            NER();
        }
        else if(args[0].contains("pos")) {
            pos();
        }
    }

    public static void sentenceSplitter() throws IOException {
        File folder = new File(f);
        File[] listOfFiles = folder.listFiles();

        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        for (int i = 0; i < listOfFiles.length; i++) {
            //System.out.println("file: " + listOfFiles[i]);
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(listOfFiles[i].getPath())));
                Annotation document = new Annotation(content);
                pipeline.annotate(document);
                //System.out.println(document.get(CoreAnnotations.SentencesAnnotation.class).size());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    public static void tokenize() throws IOException {
        File folder = new File(f);
        File[] listOfFiles = folder.listFiles();

        Properties props = new Properties();
        props.put("annotators", "tokenize");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(listOfFiles[i].getPath())));
                Annotation document = new Annotation(content);
                pipeline.annotate(document);
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    public static void NER() throws IOException {
        File folder = new File(f);
        File[] listOfFiles = folder.listFiles();

        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        for (int i = 0; i < listOfFiles.length; i++) {
            //System.out.println("file: " + listOfFiles[i]);
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(listOfFiles[i].getPath())));
                Annotation document = new Annotation(content);
                pipeline.annotate(document);
                //System.out.println(document.get(CoreAnnotations.SentencesAnnotation.class).size());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    public static void pos() throws IOException {
        File folder = new File(f);
        File[] listOfFiles = folder.listFiles();

        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        for (int i = 0; i < listOfFiles.length; i++) {
            //System.out.println("file: " + listOfFiles[i]);
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(listOfFiles[i].getPath())));
                Annotation document = new Annotation(content);
                pipeline.annotate(document);
                //System.out.println(document.get(CoreAnnotations.SentencesAnnotation.class).size());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }
}
