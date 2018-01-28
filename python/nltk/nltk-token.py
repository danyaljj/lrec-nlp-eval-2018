import os

import nltk

from nltk.tokenize import sent_tokenize
from python.get_folder import getFolder

root_dir = getFolder()
print(root_dir)
files = [item for item in os.listdir(root_dir) if os.path.isfile(os.path.join(root_dir, item))]

for ff in files:
    f = open(root_dir + ff, 'r')
    paragraph = f.read()
    sent_tokenize_list = sent_tokenize(paragraph)
    f.close()
