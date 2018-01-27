package org.cogcomp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by daniel on 1/24/18.
 */
public class OpenNLP {
    public static void main(String[] args) throws IOException {
//        String sentence = "Hi. How are you? Welcome to Tutorialspoint. "
//                + "We provide free tutorials on various technologies";
//
//        //Loading sentence detector model
//        InputStream inputStream = new FileInputStream("OpenNLPModel/en-sent.bin");
//        SentenceModel model = new SentenceModel(inputStream);
//
//        //Instantiating the SentenceDetectorME class
//        SentenceDetectorME detector = new SentenceDetectorME(model);
//
//        //Detecting the sentence
//        String sentences[] = detector.sentDetect(sentence);
//
//        //Printing the sentences
//        for(String sent : sentences)
//            System.out.println(sent);



//        try (InputStream modelIn = new FileInputStream("OpenNLPModel/en-token.bin")) {
//            TokenizerModel model2 = new TokenizerModel(modelIn);
//            Tokenizer tokenizer = new TokenizerME(model2);
//            String tokens[] = tokenizer.tokenize("An input sample sentence.");
//            System.out.println(Arrays.toString(tokens));
//        }


        String sentence[] = new String[]{
                "Pierre",
                "Vinken",
                "is",
                "61",
                "years",
                "old",
                "."
        };
        try (InputStream modelIn = new FileInputStream("OpenNLPModel/en-ner-person.bin")){
            TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
            NameFinderME nameFinder = new NameFinderME(model);
            Span nameSpans[] = nameFinder.find(sentence);
            System.out.println(Arrays.toString(nameSpans));
        }


    }
}
