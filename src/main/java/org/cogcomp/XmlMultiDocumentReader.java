package org.cogcomp;

import edu.illinois.cs.cogcomp.annotation.XmlTextAnnotationMaker;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.XmlTextAnnotation;
import edu.illinois.cs.cogcomp.core.io.LineIO;
import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;
import edu.illinois.cs.cogcomp.nlp.corpusreaders.XmlDocumentReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * NOT YET IMPLEMENTED:
 * override XmlDocumentReader functionality to read more than one document (TextAnnotation) from a single file, while
 *    maintaining offsets into original file.
 */
public class XmlMultiDocumentReader extends XmlDocumentReader {
    private static final Logger logger = LoggerFactory.getLogger(XmlMultiDocumentReader.class);

    public XmlMultiDocumentReader(ResourceManager rm, XmlTextAnnotationMaker xmlTextAnnotationMaker) throws Exception {
        super(rm, xmlTextAnnotationMaker);
    }

    @Override
    public List<XmlTextAnnotation> getAnnotationsFromFile(List<Path> corpusFileListEntry) throws Exception {
        Path sourceTextAndAnnotationFile = corpusFileListEntry.get(0);
        fileId =
                sourceTextAndAnnotationFile.getName(sourceTextAndAnnotationFile.getNameCount() - 1)
                        .toString();
        logger.debug("read source file {}", fileId);
//        numFiles++;
//        String fileText = LineIO.slurp(sourceTextAndAnnotationFile.toString());
        List<XmlTextAnnotation> xmlTaList = new ArrayList<>(1);
//        XmlTextAnnotation xmlTa = super.xmlTextAnnotationMaker.createTextAnnotation(fileText, this.corpusName, fileId);
//        if (null != xmlTa) {
//            xmlTaList.add(xmlTa);
//            numTextAnnotations++;
//        }

        return xmlTaList;
    }
}
