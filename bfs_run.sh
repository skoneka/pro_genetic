#!/bin/sh
SCRIPTPATH=$(dirname $(realpath $0)); cd $SCRIPTPATH || exit 1;
cd prot_1_bfs/src
java algorytmygenetyczne.AlgorytmyGenetyczne 2> /dev/null

if [ $? -eq 1 ]; then
    cd $SCRIPTPATH
    echo "Compiling"
    ./bfs_compile.sh
    exec ./bfs_run.sh

fi
