#!/bin/sh
SCRIPTPATH=$(dirname $(realpath $0)); cd $SCRIPTPATH || exit 1;

OSRC="dist.sh cp_run.sh bfs_compile.sh bfs_run.sh"
DSRC="pro_genetic_intro.tex pro_genetic_intro.pdf pro_genetic_prot2.tex pro_genetic_prot2.pdf pro_genetic_prot1.pdf pro_genetic_summary.tex pro_genetic_summary.pdf"

OSRC_PROT2=""
FSRC_PROT2="eztext.py Genetic.py Overlay.py Main.py Objects.py Config.py Util.py"

FSRC_PROT1="AlgorytmyGenetyczne.java Autobus.java Figura.java FrameDemo.java ListySasiedztwa.java Panel.java Populacja.java"

TDIR="pro_genetic_dist"

rm -rf $TDIR

mkdir -p $TDIR
mkdir -p $TDIR/doc
mkdir -p $TDIR/prot_1_bfs/src/algorytmygenetyczne
mkdir -p $TDIR/prot_2_cp/src


doit ()
{
    for f in $3
    do
        echo $1 " <-- " $2/$f 
        cp $2/$f $1
    done
}

doit $TDIR/ . "$OSRC"
doit $TDIR/doc doc "$DSRC"
doit $TDIR/prot_1_bfs/src/algorytmygenetyczne prot_1_bfs/src/algorytmygenetyczne "$FSRC_PROT1"

doit $TDIR/prot_2_cp prot_2_cp "$OSRC_PROT2"
doit $TDIR/prot_2_cp/src prot_2_cp/src "$FSRC_PROT2"

tar cf $TDIR.tar $TDIR
gzip -f $TDIR.tar
