#!/bin/bash


CONFIG=""

if [ $# -eq 1 ]; then
    CONFIG=$1
else
    echo "Usage: $0 config"
    exit -1
fi

TARGET="target"
DEP="target/dependency"

if [[ !(-e $TARGET) || !(-e $DEP) ]]
then
    mvn install && mvn dependency:copy-dependencies
fi

MAIN="org.cogcomp.Evaluator"
FLAGS="-Xmx4g"  # set memory appropriate to annotator requirements

CP="."

for JAR in `ls $TARGET/*.jar`; do
    CP="${CP}:$JAR"
done

for JAR in `ls $DEP/*jar`; do
    CP="${CP}:$JAR"
done

CMD="java $FLAGS -cp $CP $MAIN $CONFIG"

echo "$0: running command '$CMD'..."

time $CMD

echo "$0: done."

