#!/usr/bin/perl

use strict;

######
# given a report file from the gnu 'time' command, parse out the
#   time and memory information, compute the time per 1k words 
#   (assuming a given number of words specified as a constant in this
#   script) and report it.

die "Usage: $0 reportFile" unless @ARGV == 1;

my $NUM_TOKS = 665233.0;

my $in = shift;

my $sysTime;
my $maxMemK;

open (IN, "<$in") or die "ERROR: $0: can't open input file '$in': $!\n";

while(<IN>) {
  if (/Elapsed \(wall clock\) time \(h:mm:ss or m:ss\):\s(\S+)\s*$/) {
    $sysTime = $1;
  }
  elsif (/Maximum resident set size.*:\s(\S+)\s*$/) {
    $maxMemK = $1;
  }
}

close IN;

my $memUseG = sprintf("%.3f", $maxMemK / 1048576.0);
my $numToksPerSec = sprintf("%.3f", $NUM_TOKS / $sysTime);

print STDOUT "Max memory use: $memUseG Gb.\n";
print STDOUT "Processing speed: " . sprintf("%.3f", $numToksPerSec / 1000.0) . " thousand toks/second.\n";

