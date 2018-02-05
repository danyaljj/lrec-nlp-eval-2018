import os

from get_folder import getFolder
from textblob import TextBlob

root_dir = getFolder()
print(root_dir)
files = [item for item in os.listdir(root_dir) if os.path.isfile(os.path.join(root_dir, item))]  # Filter the items and only keep files (strip out directories)

for ff in files:
    f = open(root_dir + ff, 'r')
    paragraph = f.read()
    blob = TextBlob(paragraph)
    blob.sentences
    f.close()
