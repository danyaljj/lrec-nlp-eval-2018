import os

from python.nltk.get_folder import getFolder

root_dir = getFolder()
files = [item for item in os.listdir(root_dir) if os.path.isfile(os.path.join(root_dir, item))]  # Filter the items and only keep files (strip out directories)


# Install: pip install spacy && python -m spacy download en
import spacy

# Load English tokenizer, tagger, parser, NER and word vectors
nlp = spacy.load('en')


for ff in files:
    f = open(root_dir + ff, 'r')
    paragraph = f.read()
    doc = nlp(paragraph)
    for tok in doc:
        tok.pos_
    f.close()
