package org.cogcomp;

import edu.illinois.cs.cogcomp.annotation.AnnotatorException;
import edu.illinois.cs.cogcomp.annotation.AnnotatorService;
import edu.illinois.cs.cogcomp.core.datastructures.Pair;
import edu.illinois.cs.cogcomp.core.datastructures.ViewNames;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.Constituent;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.View;
import edu.illinois.cs.cogcomp.pipeline.main.PipelineFactory;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import org.apache.commons.io.FileUtils;

import javax.xml.bind.SchemaOutputResolver;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class NERPredictions {
    public static void main(String[] args) throws IOException, AnnotatorException {
//        String f = "/Users/daniel/ideaProjects/lrec-nlp-eval-2018/conll2003/eng/eng.testa.conll";
//        String f = "/Users/daniel/ideaProjects/lrec-nlp-eval-2018/MUC7ColumnsFolders/Test/MUC7.NE.formalrun.sentences.columns.gold";
//        String f = "/Users/daniel/ideaProjects/lrec-nlp-eval-2018/Ontonotes/ColumnFormat/Test/ontonotes_test_combined.txt";
        String f = "/Users/daniel/ideaProjects/lrec-nlp-eval-2018/enron/EnronRandom/enron-test-merged.txt";

        List<String> tokens = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        AnnotatorService pipeline = PipelineFactory.buildPipeline(ViewNames.POS, ViewNames.LEMMA, ViewNames.SHALLOW_PARSE, ViewNames.NER_ONTONOTES);

        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
        StanfordCoreNLP stanfordCoreNLP = new StanfordCoreNLP(props);


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

        int numberOfLinesNotAligned = 0;
        try {
            File file = new File(f);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split("\t");
                //System.out.println(split.length);
                //System.out.println(Arrays.toString(split));
                if (split.length > 1) {
                    String token = split[5];
                    String label = split[0];
                    tokens.add(token);
                    labels.add(label);
                } else if(tokens.size() > 0) {
                    // process NER
                    String sentence = String.join(" ", tokens);
                    System.out.println("Str: " + sentence);
                    if(true) {
                        BufferedWriter cogcompWriter = new BufferedWriter(new FileWriter("cogcomp-nlp-ontonotes-ner-output-enron.txt", true));
                        TextAnnotation ta = pipeline.createAnnotatedTextAnnotation("", "", sentence);
                        List predLabels = SingleTaToConll(ta);
                        assert (labels.size() == labels.size()) : "label size:  " + labels.size() + "  - tokens: " + tokens.size();
                        if (predLabels.size() == labels.size()) {
                            for (int i = 0; i < tokens.size(); i++) {
                                cogcompWriter.write(tokens.get(i) + " " + "-" + " " + labels.get(i) + " " + predLabels.get(i) + "\n");
                            }
                        } else {
                            System.out.println("not aligned correctly: " + numberOfLinesNotAligned);
                            numberOfLinesNotAligned++;
                        }
                        cogcompWriter.close();
                    }
                    else if(false) {
                        BufferedWriter corenlpWriter = new BufferedWriter(new FileWriter("corennlp-ner-output-enron.txt", true));
                        System.out.println("Sentence: " + sentence);
                        Annotation document = new Annotation(sentence);
                        stanfordCoreNLP.annotate(document);
                        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
                        List<CoreLabel> coreLabels = sentences.get(0).get(CoreAnnotations.TokensAnnotation.class);
                        if (/*sentence.length() == 1 && */coreLabels.size() == labels.size()) {
                            for (int i = 0; i < coreLabels.size(); i++) {
                                CoreLabel token = coreLabels.get(i);
                                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                                if(ne.equalsIgnoreCase("O")) {
                                    corenlpWriter.write(tokens.get(i) + " " + "-" + " " + labels.get(i) + " O\n");
                                }
                                else {
                                    String neBioLabel = "";
                                    if (i > 0) {
                                        CoreLabel tokenBefore = coreLabels.get(i - 1);
                                         String neBefore = tokenBefore.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                                         if(neBefore.equals(ne)) {
                                             neBioLabel = "I-" + ne;
                                         }
                                         else {
                                             neBioLabel = "B-" + ne;
                                         }

                                    } else {
                                        neBioLabel = "B-" + ne;
                                    }
                                    corenlpWriter.write(tokens.get(i) + " " + "-" + " " + labels.get(i) + " " + neBioLabel + "\n");
                                }
                            }
                        } else {
                            System.out.println("not aligned correctly: " + numberOfLinesNotAligned);
                            System.out.println("number of sentences: " + sentence.length());
                            System.out.println("number of labels: " + labels.size());
                            System.out.println("number of predicted labels: " + coreLabels.size());
                            numberOfLinesNotAligned++;
                        }
                        corenlpWriter.close();
                    }
                    else if (false) {
                        BufferedWriter opennlpWriter = new BufferedWriter(new FileWriter("opennlp-ner-output-enron.txt", true));
                        // opennlp ner extraction
                        String[] sentences = detector.sentDetect(sentence);
                        String[] predictedTokens = tokenizer.tokenize(sentences[0]);
                        if (tokens.size() == predictedTokens.length) {
                            String[] predictedLabels = new String[predictedTokens.length];
                            for (int i = 0; i < predictedLabels.length; i++) {
                                predictedLabels[i] = "O";
                            }

                            Span[] spansPER = nameFinderPER.find(predictedTokens);
                            Span[] spansLOC = nameFinderLOC.find(predictedTokens);
                            Span[] spansORG = nameFinderORG.find(predictedTokens);

                            for (Span sp : spansLOC) {
                                predictedLabels[sp.getStart()] = "B-LOC";
                                for (int i = sp.getStart() + 1; i < sp.getEnd(); i++) {
                                    predictedLabels[i] = "I-LOC";
                                }
                            }

                            for (Span sp : spansORG) {
                                predictedLabels[sp.getStart()] = "B-ORG";
                                for (int i = sp.getStart() + 1; i < sp.getEnd(); i++) {
                                    predictedLabels[i] = "I-ORG";
                                }
                            }

                            for (Span sp : spansPER) {
                                predictedLabels[sp.getStart()] = "B-PER";
                                for (int i = sp.getStart() + 1; i < sp.getEnd(); i++) {
                                    predictedLabels[i] = "I-PER";
                                }
                            }

                            for (int i = 0; i < predictedLabels.length; i++) {
                                opennlpWriter.write(tokens.get(i) + " " + "-" + " " + labels.get(i) + " " + predictedLabels[i] + "\n");
                            }

                        }else {
                            System.out.println("not aligned correctly: " + numberOfLinesNotAligned);
                            System.out.println("number of sentences: " + sentence.length());
                            System.out.println("number of labels: " + labels.size());
                            //System.out.println("number of predicted labels: " + coreLabels.size());
                            numberOfLinesNotAligned++;
                        }
                        opennlpWriter.close();
                    }
                    labels.clear();
                    tokens.clear();
                }

            }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<String> SingleTaToConll(TextAnnotation ta) throws IOException {

        List<String> labels = new ArrayList<>();

        View sentview = ta.getView(ViewNames.SENTENCE);
        View nerview = ta.getView(ViewNames.NER_ONTONOTES);
        for (int i = 0; i < ta.getTokens().length; i++) {

            // Default "outside" label in NER_CONLL
            String label = "O";

            List<Constituent> constituents = nerview.getConstituentsCoveringToken(i);

            // should be just one constituent
            if (constituents.size() > 0) {
                Constituent c = constituents.get(0);
                if (c.getStartSpan() == i) {
                    label = "B-" + c.getLabel();
                } else {
                    label = "I-" + c.getLabel();
                }

                if (constituents.size() > 1) {
                    System.out.println("More than one label -- selecting the first.");
                    System.out.println("Constituents: " + constituents);
                }
            }
            labels.add(label);
        }

        return labels;
    }

    public static String conllline(String tag, int num, String word) {
        return String.format("%s\t0\t%s\tO\tO\t%s\tx\tx\t0", tag, num, word);
    }

}
