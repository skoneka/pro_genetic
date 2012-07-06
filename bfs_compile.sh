#!/bin/sh
SCRIPTPATH=$(dirname $(realpath $0)); cd $SCRIPTPATH || exit 1;
javac prot_1_bfs/src/algorytmygenetyczne/*.java
