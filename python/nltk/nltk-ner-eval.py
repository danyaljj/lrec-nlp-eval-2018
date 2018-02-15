import os

import nltk

from nltk.chunk import conlltags2tree, tree2conlltags

import spacy

from spacy.gold import biluo_tags_from_offsets

nlp = spacy.load('en')

# root_dir = "/Users/daniel/ideaProjects/lrec-nlp-eval-2018/conll2003/eng/eng.testa.conll"
# root_dir = "/Users/daniel/ideaProjects/lrec-nlp-eval-2018/MUC7ColumnsFolders/Test/MUC7.NE.formalrun.sentences.columns.gold"
# root_dir = "/Users/daniel/ideaProjects/lrec-nlp-eval-2018/Ontonotes/ColumnFormat/Test/ontonotes_test_combined.txt"
root_dir = "/Users/daniel/ideaProjects/lrec-nlp-eval-2018/enron/EnronRandom/enron-test-merged.txt"

lst = list(open(root_dir))

print(len(lst))

nltk_output = open('nltk_enron_output.txt', 'rb+')
spacy_output = open('spacy_enron_output.txt', 'rb+')

nltk_number_of_sentences_not_aligned = 0
total_number_of_sentence = 0
token_cache = []
for l in lst:
    split = l.split("\t")
    if len(split) == 1:
        # i.e. this is end of the line
        # run it through the annnotator
        gold_tokens = [a[0] for a in token_cache]
        ne_tags_only = [a[1] for a in token_cache]
        string_sentence = " ".join(gold_tokens)
        total_number_of_sentence = total_number_of_sentence + 1
        # print("String sentence: " + string_sentence)

        if(True):
            # tokens = nltk.word_tokenize(string_sentence)
            tagged = nltk.pos_tag(gold_tokens)
            entities = nltk.chunk.ne_chunk(tagged)
            iob_tagged = tree2conlltags(entities)

            for i, e in enumerate(iob_tagged):
                token = e[0]
                pos = e[1]
                ner_pred = e[2]
                ner_gold = ne_tags_only[i]
                output_line = token + " " + pos + " " + ner_gold + " " + ner_pred + "\n"
                nltk_output.write(bytes(output_line, 'utf-8'))

            assert (len(tagged) == len(gold_tokens))

        if(False):
            doc = nlp(string_sentence)
            sentences = list(doc.sents)
            predicted_tokens = [tok for tok in doc]

            if(len(predicted_tokens) != len(gold_tokens)):
                nltk_number_of_sentences_not_aligned = nltk_number_of_sentences_not_aligned + 1
            #     print (nltk_number_of_sentences_not_aligned)
                # print("predicted tokens: " + str(predicted_tokens))
                # print("gold tokens: " + str(gold_tokens))
                # print("--------")
            else:
                predicted_entities = [(ent.start_char, ent.end_char, ent.label_) for ent in doc.ents]
                tags = biluo_tags_from_offsets(doc, predicted_entities)

                for i, e in enumerate(predicted_tokens):
                    token = predicted_tokens[i].text
                    entity_label = tags[i]
                    entity_gold_label = ne_tags_only[i]
                    output_line = token + " " + "-" + " " + entity_gold_label + " " + entity_label + "\n"
                    spacy_output.write(bytes(output_line, 'utf-8'))


        token_cache = []
    else:
        label = split[0]
        token = split[5]
        token_cache.append((token, label))



print("Total number of sentences: " + str(total_number_of_sentence))