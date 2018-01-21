package org.cogcomp;

import edu.illinois.cs.cogcomp.core.utilities.configuration.Property;
import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;
import edu.illinois.cs.cogcomp.nlp.corpusreaders.CorpusReaderConfigurator;
import edu.illinois.cs.cogcomp.pipeline.common.PipelineConfigurator;

/**
 * Configuration options for cogcomp-nlp LREC evaluation code.
 * Override pipeline config options to 'false'.
 */
public class LRECEvalConfigurator extends PipelineConfigurator {

    public static final Property NUM_FILES = new Property("numFiles", "1000");
    public static final Property OUTPUT_FILE = new Property("outputFile", "report.txt");

    @Override
    public ResourceManager getDefaultConfig() {

        Property[] properties = new Property[] {
            NUM_FILES, OUTPUT_FILE,
                new Property(CorpusReaderConfigurator.SOURCE_EXTENSION.key, ".xml"),
                new Property(CorpusReaderConfigurator.ANNOTATION_EXTENSION.key, ".xml"),
                new Property(CorpusReaderConfigurator.CORPUS_DIRECTORY.key, "/shared/experiments/mssammon/lrec2018/corpusFiles")
        };

        return new ResourceManager(generateProperties(properties));
    }
}
