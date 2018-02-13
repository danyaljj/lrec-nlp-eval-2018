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
my $clockTime;

open (IN, "<$in") or die "ERROR: $0: can't open input file '$in': $!\n";

while(<IN>) {
  if (/System time.*:\s(\S+)\s*$/) {
    $sysTime = $1;
  }
  if (/Maximum resident set size.*:\s(\S+)\s*$/) {
    print "$1\n";
    $maxMemK = $1;
  }
  if (/Elapsed.*:\s(.*):(.*)\s*/) {
    print "$1\n";
    print "$2\n";
    $clockTime = $1 * 60 + $2;
    print "Clock time: $clockTime\n"
  }
}

close IN;

if ($sysTime > 0.0) {
    my $numToksPerSec = sprintf("%.3f", $NUM_TOKS / $sysTime);
    print STDOUT "Processing speed (system time): " . sprintf("%.3f", $numToksPerSec / 1000.0) . " thousand toks/second.\n";
}

if ($clockTime > 0.0) {
    my $numToksPerSec1 = sprintf("%.3f", $NUM_TOKS / $clockTime);
    print STDOUT "Processing speed  (clock time): " . sprintf("%.3f", $numToksPerSec1 / 1000.0) . " thousand toks/second.\n";
}


my $memUseG = sprintf("%.3f", $maxMemK / 1048576.0);
print STDOUT "Max memory use: $memUseG Gb.\n";




