package org.cogcomp;

import edu.illinois.cs.cogcomp.nlp.corpusreaders.XmlFragmentWhitespacingDocumentReader;

/**
 * Created by mssammon on 1/14/18.
 */
public class SimpleLRECDocReader extends XmlFragmentWhitespacingDocumentReader {

    private final String sourceFileExtension;

    public SimpleLRECDocReader(String corpusName, String sourceDirectory, String sourceFileExtension, String annotationFileExtension) throws Exception {
        super(corpusName, sourceDirectory, sourceFileExtension, annotationFileExtension);
        this.sourceFileExtension = sourceFileExtension;
    }

    @Override
    protected String getRequiredFileExtension() {
        return ".xml";// sourceFileExtension;
    }
}
