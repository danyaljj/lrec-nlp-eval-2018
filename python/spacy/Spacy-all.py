import os

from get_folder import getFolder

root_dir = getFolder()
files = [item for item in os.listdir(root_dir) if os.path.isfile(os.path.join(root_dir, item))]  # Filter the items and only keep files (strip out directories)

# Install: pip install spacy && python -m spacy download en
import spacy

# Load English tokenizer, tagger, parser, NER and word vectors
nlp = spacy.load('en',  disable=['parser'])



for ff in files:
    f = open(root_dir + ff, 'r')
    paragraph = f.read()
    doc = nlp(paragraph)
    sentences = list(doc.sents)
#    [tok for tok in doc]
#    [chunk  for chunk in doc.noun_chunks]
#    [tok.lemma_ for tok in doc]
#    [entity.text for entity in doc.ents]
#    [tok.pos_ for tok in doc]
    f.close()
