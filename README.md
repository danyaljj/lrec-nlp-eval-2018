# LREC 2018: Evaluating CogComp-nlp

This project contains simple code for evaluating time and memory
requirements of cogcomp-nlp components, to be used in a CCG publication
at LREC 2018.

It is set up to use a set of xml files extracted from a news
corpus to provide an estimate of time and memory required to process 
text measured in e.g. thousands of tokens. (Later versions may work 
directly with specific corpora in their original formats).

This project provides a general application for CCG NLP components
from cogcomp-nlp, but the evaluation script can be used as a template
for arbitrary tools run from the command line -- it uses Gnu's "time"
command to report the CPU time and memory use of the process it starts.
It logs the output to a file, then extracts the memory and time values,
and reports the total CPU time taken and the maximum memory footprint.


NOTE: The purpose is NOT to evaluate accuracy/F1 as this corpus does not 
have the appropriate gold standard annotations: the point is to compare
the time and memory requirements of different NLP tools in a fair, 
reproducible way. 



USAGE:
./scripts/runEval.sh

-- reads files from /shared/experiments/mssammon/lrec2018/corpusFilesPlain/

-- generates output file 'cmd-time-report.txt' that contains the full
   output of the 'time' command. 

-- plain text corpus has 999 files, 665233 words (using linux 'wc -w'). It
   was extracted from a few of the NYT files from English Gigaword v5.




CAVEATS:
* make sure you run the evaluated process on a test machine with zero or
low activity from other users. I'm not sure how sophisticated the 'time'
command is, but suspect it does not account for effects of swapping
memory between processes on a heavily-loaded machine.

* run your target application single-threaded. For a direct comparison 
we want to use single-threaded processes; in the publication, we will 
note which applications can be run multi-threaded.

* this is not intended to be a good example of an NLP project. It was 
completed in something of a rush. In particular, you should not extract
plain text files from a corpus and then process that text, unless you
have a nice, simple way to map the resulting annotations back to the
character offsets in the source documents. 
INSTEAD, you should use a corpus reader from cogcomp-nlp/corpusreaders,
or generate a new one.  There are beginnings in this project for a 
Gigaword corpus reader, but it doesn't work yet.

* the current evaluation approach includes the time to load models/resources
in the runtime total. This allows us to follow a simple and hopefully
foolproof approach to measure time taken, i.e. using the system time
value reported by the Gnu "time" command. 


TODO:
* compute time/10k tokens
* update evaluator to read plain text