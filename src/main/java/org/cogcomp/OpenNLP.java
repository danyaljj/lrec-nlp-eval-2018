package org.cogcomp;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import opennlp.tools.chunker.ChunkerModel;

import opennlp.tools.util.Span;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static edu.illinois.cs.cogcomp.lbjava.nlp.POS.tokens;

/**
 * Created by daniel on 1/24/18.
 */
public class OpenNLP {
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
        else if(args[0].contains("pos_maxent")) {
            pos_maxent();
        }
        else if(args[0].contains("pos_perceptron")) {
            pos_perceptron();
        }
        else if(args[0].contains("chunk")) {
            chunker();
        }
    }

    public static void sentenceSplitter() throws IOException {
        File folder = new File(f);
        File[] listOfFiles = folder.listFiles();

        InputStream inputStream = new FileInputStream("OpenNLPModel/en-sent.bin");
        SentenceModel model = new SentenceModel(inputStream);
        SentenceDetectorME detector = new SentenceDetectorME(model);

        for (int i = 0; i < listOfFiles.length; i++) {
            //System.out.println("file: " + listOfFiles[i]);
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(listOfFiles[i].getPath())));
                detector.sentDetect(content);
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    public static void tokenize() throws IOException {
        File folder = new File(f);
        File[] listOfFiles = folder.listFiles();

        InputStream inputStream = new FileInputStream("OpenNLPModel/en-sent.bin");
        SentenceModel model = new SentenceModel(inputStream);
        SentenceDetectorME detector = new SentenceDetectorME(model);

        InputStream modelIn = new FileInputStream("OpenNLPModel/en-token.bin");
        TokenizerModel model2 = new TokenizerModel(modelIn);
        Tokenizer tokenizer = new TokenizerME(model2);

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(listOfFiles[i].getPath())));
                String[] sentences = detector.sentDetect(content);
                for(String s : sentences) {
                    tokenizer.tokenize(s);
                }
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    public static void NER() throws IOException {
        File folder = new File(f);
        File[] listOfFiles = folder.listFiles();

        InputStream inputStream = new FileInputStream("OpenNLPModel/en-sent.bin");
        SentenceModel model = new SentenceModel(inputStream);
        SentenceDetectorME detector = new SentenceDetectorME(model);

        InputStream modelIn = new FileInputStream("OpenNLPModel/en-token.bin");
        TokenizerModel model2 = new TokenizerModel(modelIn);
        Tokenizer tokenizer = new TokenizerME(model2);

        InputStream modelInNERPER = new FileInputStream("OpenNLPModel/en-ner-person.bin");
        TokenNameFinderModel modelNERPER = new TokenNameFinderModel(modelInNERPER);
        NameFinderME nameFinderPER = new NameFinderME(modelNERPER);

        InputStream modelInNERLOC = new FileInputStream("OpenNLPModel/en-ner-location.bin");
        TokenNameFinderModel modelNERLOC = new TokenNameFinderModel(modelInNERLOC);
        NameFinderME nameFinderLOC = new NameFinderME(modelNERLOC);

        InputStream modelInNERORG = new FileInputStream("OpenNLPModel/en-ner-organization.bin");
        TokenNameFinderModel modelNERORG = new TokenNameFinderModel(modelInNERORG);
        NameFinderME nameFinderORG = new NameFinderME(modelNERORG);

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(listOfFiles[i].getPath())));
                String[] sentences = detector.sentDetect(content);
                for(String s : sentences) {
                    String[] tokens = tokenizer.tokenize(s);
                    nameFinderPER.find(tokens);
                    nameFinderLOC.find(tokens);
                    nameFinderORG.find(tokens);
                }
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    public static void pos_maxent() throws IOException {
        File folder = new File(f);
        File[] listOfFiles = folder.listFiles();

        InputStream inputStream = new FileInputStream("OpenNLPModel/en-sent.bin");
        SentenceModel model = new SentenceModel(inputStream);
        SentenceDetectorME detector = new SentenceDetectorME(model);

        InputStream modelIn = new FileInputStream("OpenNLPModel/en-token.bin");
        TokenizerModel model2 = new TokenizerModel(modelIn);
        Tokenizer tokenizer = new TokenizerME(model2);

        // Parts-Of-Speech Tagging
        // reading parts-of-speech model to a stream
        InputStream posModelIn = new FileInputStream("OpenNLPModel/en-pos-maxent.bin");
        // loading the parts-of-speech model from stream
        POSModel posModel = new POSModel(posModelIn);
        // initializing the parts-of-speech tagger with model
        POSTaggerME posTagger = new POSTaggerME(posModel);

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(listOfFiles[i].getPath())));
                String[] sentences = detector.sentDetect(content);
                for(String s : sentences) {
                    String[] tokens = tokenizer.tokenize(s);
                    posTagger.tag(tokens);
                }
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    public static void pos_perceptron() throws IOException {
        File folder = new File(f);
        File[] listOfFiles = folder.listFiles();

        InputStream inputStream = new FileInputStream("OpenNLPModel/en-sent.bin");
        SentenceModel model = new SentenceModel(inputStream);
        SentenceDetectorME detector = new SentenceDetectorME(model);

        InputStream modelIn = new FileInputStream("OpenNLPModel/en-token.bin");
        TokenizerModel model2 = new TokenizerModel(modelIn);
        Tokenizer tokenizer = new TokenizerME(model2);

        // Parts-Of-Speech Tagging
        // reading parts-of-speech model to a stream
        InputStream posModelIn = new FileInputStream("OpenNLPModel/en-pos-perceptron.bin");
        // loading the parts-of-speech model from stream
        POSModel posModel = new POSModel(posModelIn);
        // initializing the parts-of-speech tagger with model
        POSTaggerME posTagger = new POSTaggerME(posModel);

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(listOfFiles[i].getPath())));
                String[] sentences = detector.sentDetect(content);
                for(String s : sentences) {
                    String[] tokens = tokenizer.tokenize(s);
                    posTagger.tag(tokens);
                }
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    public static void chunker() throws IOException {
        File folder = new File(f);
        File[] listOfFiles = folder.listFiles();

        InputStream inputStream = new FileInputStream("OpenNLPModel/en-sent.bin");
        SentenceModel model = new SentenceModel(inputStream);
        SentenceDetectorME detector = new SentenceDetectorME(model);

        InputStream modelIn = new FileInputStream("OpenNLPModel/en-token.bin");
        TokenizerModel model2 = new TokenizerModel(modelIn);
        Tokenizer tokenizer = new TokenizerME(model2);

        // Parts-Of-Speech Tagging
        // reading parts-of-speech model to a stream
        InputStream posModelIn = new FileInputStream("OpenNLPModel/en-pos-maxent.bin");
        // loading the parts-of-speech model from stream
        POSModel posModel = new POSModel(posModelIn);
        // initializing the parts-of-speech tagger with model
        POSTaggerME posTagger = new POSTaggerME(posModel);


        // reading the chunker model
        InputStream ins = new FileInputStream("OpenNLPModel/en-chunker.bin");
        // loading the chunker model
        ChunkerModel chunkerModel = new ChunkerModel(ins);
        // initializing chunker(maximum entropy) with chunker model
        ChunkerME chunker = new ChunkerME(chunkerModel);

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".txt")) {
                String content = new String(Files.readAllBytes(Paths.get(listOfFiles[i].getPath())));
                String[] sentences = detector.sentDetect(content);
                for(String s : sentences) {
                    String[] tokens = tokenizer.tokenize(s);
                    String tags[] = posTagger.tag(tokens);
                    System.out.println(Arrays.toString(chunker.chunk(tokens, tags)));
                }
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

}
