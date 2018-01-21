package org.cogcomp;

import edu.illinois.cs.cogcomp.nlp.corpusreaders.XmlFragmentWhitespacingDocumentReader;

/**
 * A simple extension of a document reader that expects one document per file, and filters
 *    out xml markup.
 *
 * @author mssammon
 */
public class SimpleLRECDocReader extends XmlFragmentWhitespacingDocumentReader {

    private static final String EXTENSION = ".txt"; //".xml";

    private final String sourceFileExtension;

    public SimpleLRECDocReader(String corpusName, String sourceDirectory, String sourceFileExtension, String annotationFileExtension) throws Exception {
        super(corpusName, sourceDirectory, sourceFileExtension, annotationFileExtension);
        this.sourceFileExtension = sourceFileExtension;
    }

    @Override
    protected String getRequiredFileExtension() {
        return EXTENSION;// sourceFileExtension;
    }
}
