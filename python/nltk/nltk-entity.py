import os

import nltk

from get_folder import getFolder

root_dir = getFolder()
print(root_dir)
files = [item for item in os.listdir(root_dir) if os.path.isfile(os.path.join(root_dir, item))]  # Filter the items and only keep files (strip out directories)

for ff in files:
    f = open(root_dir + ff, 'r')
    paragraph = f.read()
    tokens = nltk.word_tokenize(paragraph)
    tagged = nltk.pos_tag(tokens)
    entities = nltk.chunk.ne_chunk(tagged)
    f.close()