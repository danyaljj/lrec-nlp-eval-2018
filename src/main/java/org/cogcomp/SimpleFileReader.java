package org.cogcomp;

import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.nlp.corpusreaders.XmlFragmentWhitespacingDocumentReader;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by mssammon on 1/14/18.
 */
public class SimpleFileReader extends XmlFragmentWhitespacingDocumentReader {
    public SimpleFileReader(String corpusName, String sourceDirectory, String sourceFileExtension, String annotationFileExtension) throws Exception {
        super(corpusName, sourceDirectory, sourceFileExtension, annotationFileExtension);
    }

//    @Override
//    public String getRequiredFileExtension() {
//        return ".xml";
//    }
}
