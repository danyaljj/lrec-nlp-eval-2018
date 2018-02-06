import os

from get_folder import getFolder
from textblob import TextBlob
from textblob import Word

root_dir = getFolder()
files = [item for item in os.listdir(root_dir) if os.path.isfile(os.path.join(root_dir, item))]  # Filter the items and only keep files (strip out directories)

for ff in files:
    f = open(root_dir + ff, 'r')
    paragraph = f.read()
    blob = TextBlob(paragraph)
    for w1 in blob.words:
        w = Word(w1)
        w.lemmatize()
    f.close()


