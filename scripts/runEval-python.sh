#!/bin/bash

for filename in python/nltk/*.py; do
   CMD="python3.5 $filename"
   echo "$0: running command '$CMD'..."
   /usr/bin/time -v $CMD  >& cmd-time-report-"$1".txt
   # scripts/extractTimeAndMemory.pl cmd-time-report-processed -"$1".txt
done
