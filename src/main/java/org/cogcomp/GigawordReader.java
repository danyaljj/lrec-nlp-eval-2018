package org.cogcomp;

import edu.illinois.cs.cogcomp.annotation.TextAnnotationBuilder;
import edu.illinois.cs.cogcomp.annotation.XmlTextAnnotationMaker;
import edu.illinois.cs.cogcomp.core.utilities.XmlDocumentProcessor;
import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;
import edu.illinois.cs.cogcomp.nlp.corpusreaders.XmlDocumentReader;
import edu.illinois.cs.cogcomp.nlp.tokenizer.StatefulTokenizer;
import edu.illinois.cs.cogcomp.nlp.utility.TokenizerTextAnnotationBuilder;

/**
 * Read files from Gigaword corpus: each individual file contains multiple documents, is compressed using gzip.
 * NOT YET IMPLEMENTED: to do properly, need to identify document-level chunks and create TextAnnotations with
 *    appropriate offsets into original file.
 * @author mssammon
 */
public class GigawordReader extends XmlDocumentReader {

    public GigawordReader(ResourceManager rm, XmlTextAnnotationMaker xmlTextAnnotationMaker) throws Exception {
        super(rm, xmlTextAnnotationMaker);
    }

    public GigawordReader(ResourceManager config) throws Exception {
        super(config, buildXmlTextAnnotationMaker(config));
    }

    private static XmlTextAnnotationMaker buildXmlTextAnnotationMaker(ResourceManager config) {
        return new XmlTextAnnotationMaker(buildTextAnnotationBuilder(config), buildXmlProcessor(config));
    }

    private static XmlDocumentProcessor buildXmlProcessor(ResourceManager config) {
        return null; //new XmlDocumentProcessor(deletableTags);
    }

    /**
     * builds a TextAnnotationBuilder. Presently, English only; config option indicates whether to split on hyphen.
     * @param config
     */
    private static TextAnnotationBuilder buildTextAnnotationBuilder(ResourceManager config) {
        return new TokenizerTextAnnotationBuilder(new StatefulTokenizer(config.getBoolean(LRECEvalConfigurator.SPLIT_ON_DASH.key)));
    }
}
