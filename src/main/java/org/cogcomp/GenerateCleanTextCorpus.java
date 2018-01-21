package org.cogcomp;

import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.core.io.IOUtils;
import edu.illinois.cs.cogcomp.core.io.LineIO;
import edu.illinois.cs.cogcomp.lbjava.util.FileUtils;

import java.util.Collections;

/**
 * Crude tool to generate plain-text version of xml-format newswire corpus
 *
 * @author mssammon
 */
public class GenerateCleanTextCorpus {

    public static void main(String args[]) throws Exception {
        /**
         * the base class of this reader is buggy -- doesn't handle file extension arguments properly. The
         * reader itself has a workaround. Good enough for now.
         */
        SimpleLRECDocReader reader = new SimpleLRECDocReader("Gigaword_excerpt_plain", "/shared/experiments/mssammon/lrec2018/corpusFiles", ".xml", ".xml");

        String outDir = "/shared/experiments/mssammon/lrec2018/corpusFilesPlain";

        while (reader.hasNext()) {
            TextAnnotation doc = reader.next();
            String outFile = outDir + "/" + IOUtils.getFileStem(doc.getId()) + ".txt";
            LineIO.write(outFile, Collections.singletonList(doc.getText()));
        }
    }


}
