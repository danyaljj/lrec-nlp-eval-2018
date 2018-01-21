package org.cogcomp;

import edu.illinois.cs.cogcomp.annotation.AnnotatorException;
import edu.illinois.cs.cogcomp.annotation.AnnotatorService;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.core.utilities.XmlDocumentProcessor;
import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;
import edu.illinois.cs.cogcomp.nlp.corpusreaders.AnnotationReader;
import edu.illinois.cs.cogcomp.nlp.corpusreaders.CorpusReaderConfigurator;
import edu.illinois.cs.cogcomp.nlp.corpusreaders.XmlFragmentWhitespacingDocumentReader;
import edu.illinois.cs.cogcomp.pipeline.common.PipelineConfigurator;
import edu.illinois.cs.cogcomp.pipeline.main.PipelineFactory;
import edu.stanford.nlp.parser.metrics.Eval;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Run a specified NLP component (and prerequisites) on a given set of documents. Report memory use and time taken.
 * TODO: compare against output from linux 'time' command.
 *
 * @author mssammon
 */
public class Evaluator {

    private static final String NAME = Evaluator.class.getCanonicalName();

    /** number of files to process */
    private final int numFiles;
    private final PrintWriter outFileWriter;
    boolean includeModelLoadTime;
    private long timeEnd;
    private long timeStart;
    List<String> failedDocs;
    private int numDocs;
    private int numToks;

    enum NlpComponent {POS, CHUNK};

    AnnotatorService pipeline;
    XmlFragmentWhitespacingDocumentReader reader;
    ResourceManager rm;

    /**
     * nonDefault config should specify which annotator to use (and its prerequisite annotators).
     * Use the PipelineConfigurator keys.
     *
     * This constructor does NOT instantiate the models: this is computed as part of runtime.
     *
     * @param nonDefaultConfig
     * @throws Exception
     */
    public Evaluator(ResourceManager nonDefaultConfig) throws Exception {

        rm = new PipelineConfigurator().getConfig(nonDefaultConfig);
        rm = new LRECEvalConfigurator().getConfig(rm);

        numFiles = rm.getInt(LRECEvalConfigurator.NUM_FILES.key);

        String sourceDir = rm.getString(CorpusReaderConfigurator.CORPUS_DIRECTORY.key);
        String sourceExtn = rm.getString(CorpusReaderConfigurator.SOURCE_EXTENSION.key);
        String annoExtn = rm.getString(CorpusReaderConfigurator.ANNOTATION_EXTENSION.key);

        reader = new SimpleLRECDocReader("gigaword_nyt_subset", sourceDir, sourceExtn, annoExtn);
        String outFile = rm.getString(LRECEvalConfigurator.OUTPUT_FILE.key);
        outFileWriter = new PrintWriter(new File(outFile));

    }


    /**
     * process a specified number of documents. report number of docs, number of tokens, and time taken.
     * Presently, time taken includes everything -- loading models, reading input, and processing.
     *
     * @throws IOException
     * @throws AnnotatorException
     */
    public void runEval() throws IOException, AnnotatorException {
        numDocs = 0;
        numToks = 0;

        timeStart = System.currentTimeMillis();
        failedDocs = new LinkedList<>();
        pipeline = PipelineFactory.buildPipeline(new LRECEvalConfigurator().getConfig(rm));

        while (reader.hasNext() && numDocs < numFiles) {
            TextAnnotation ta = reader.next();
            numDocs++;
            boolean isFailed = false;

            try {
                pipeline.annotateTextAnnotation(ta, true);
            } catch (AnnotatorException e) {
                e.printStackTrace();
                failedDocs.add(ta.getId());
                isFailed = true;
            }

            if (!isFailed)
                numToks += ta.size();
        }
        timeEnd = System.currentTimeMillis();

        generateReport();
    }

    private void generateReport() {
        outFileWriter.println("Number of Documents: " + (numDocs - failedDocs.size()));
        outFileWriter.println("Number of Tokens: " + numToks);
        outFileWriter.println("Time taken: " + ((timeEnd - timeStart)/1000.0));
//        outFileWriter.println("Peak memory: " + peakMemory);
        outFileWriter.close();
    }


    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: " + NAME + " config");
            System.exit(-1);
        }

        Evaluator ev = null;
        try {
            ev = new Evaluator(new ResourceManager(args[0]));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            ev.runEval();
        } catch (IOException | AnnotatorException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
