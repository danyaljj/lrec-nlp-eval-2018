import os

import nltk

from get_folder import getFolder

root_dir = getFolder()
print(root_dir)
files = [item for item in os.listdir(root_dir) if os.path.isfile(os.path.join(root_dir, item))]  # Filter the items and only keep files (strip out directories)
from nltk.stem import SnowballStemmer

for ff in files:
    f = open(root_dir + ff, 'r')
    paragraph = f.read()
    tokens = nltk.word_tokenize(paragraph)
    [SnowballStemmer(t) for t in tokens]


    f.close()