package org.cogcomp;

import edu.illinois.cs.cogcomp.core.utilities.configuration.Property;
import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;
import edu.illinois.cs.cogcomp.nlp.corpusreaders.CorpusReaderConfigurator;

/**
 * Config options for a Gigaword reader.
 *
 * @author mssammon
 */
public class GigawordReaderConfigurator extends CorpusReaderConfigurator {

    @Override
    public ResourceManager getDefaultConfig() {
        Property[] properties = new Property[]{
                new Property(CorpusReaderConfigurator.ANNOTATION_EXTENSION.key, ".gz"),
                new Property(CorpusReaderConfigurator.SOURCE_EXTENSION.key, ".gz")
        };

        return new ResourceManager(generateProperties(properties));
    }
}
