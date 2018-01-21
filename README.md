# LREC 2018: Evaluating CogComp-nlp

This project contains simple code for evaluating time and memory
requirements of cogcomp-nlp components, to be used in the publication
"" at LREC 2018.

It is set up to use files from the English Gigaword 5 corpus, but 
should be easily adaptable to other corpora.  The purpose is NOT
to evaluate accuracy/F1 as this corpus does not have the appropriate
gold standard annotations. Rather, it should report processing time
per 10,000 tokens and maximum memory use. 
