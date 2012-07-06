#!/bin/sh
SCRIPTPATH=$(dirname $(realpath $0)); cd $SCRIPTPATH || exit 1;

python2.5 prot_2_cp/src/Main.py
