package org.cogcomp;

import edu.illinois.cs.cogcomp.annotation.AnnotatorException;
import edu.illinois.cs.cogcomp.annotation.AnnotatorService;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.nlp.tokenizer.StatefulTokenizer;
import edu.illinois.cs.cogcomp.nlp.tokenizer.Tokenizer;
import edu.illinois.cs.cogcomp.pipeline.main.PipelineFactory;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

// org.cogcomp.CogComp2ndTry

/**
 * Created by daniel on 1/24/18.
 */
public class CogComp2ndTry {
//    public static String f = "/Users/daniel/Desktop/corpusFilesPlain";
    public static String f = "/scratch/lrec-eval/corpusFilesPlain/";

    public static void main(String[] args) throws IOException, AnnotatorException {
        System.out.println("Starting to execute thins ... ");
        if(args[0].contains("tokenizer1")) {
            tokenizer1();
        }
        else if(args[0].contains("tokenizer2")) {
            tokenizer2();
        }
    }

    public static void tokenizer1() throws IOException, AnnotatorException {
        File folder = new File(f);
        File[] listOfFiles = folder.listFiles();
        AnnotatorService pipeline = PipelineFactory.buildPipeline();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(listOfFiles[i].getPath())));
                pipeline.createAnnotatedTextAnnotation( "", "", content);
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    public static void tokenizer2() throws IOException, AnnotatorException {
        File folder = new File(f);
        File[] listOfFiles = folder.listFiles();
        Tokenizer t = new StatefulTokenizer(true);
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(listOfFiles[i].getPath())));
                t.tokenizeSentence(content);
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }
}
