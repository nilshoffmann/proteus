#!/bin/sh
#######################################################
# Wrapper script to run Proteus on a cluster host
# Requires working installation of OpenGrid engine or 
# drmaa compatible grid submission systems.
#
# This script requires that X11 binaries are on the 
# executing user's path!
#######################################################
# set proteus bin location

PROTEUS_DEFAULT_HOME="/vol/maltcms/proteus"

if [ -z "$PROTEUS_HOME" ]; then
	echo "PROTEUS_HOME variable not set, using default location: $PROTEUS_DEFAULT_HOME"
	PROTEUS_HOME="$PROTEUS_DEFAULT_HOME"
	#echo "Proteus installation not found, aborting. Please set PROTEUS_HOME to point to the base directory of your Proteus installation!"
	#xmessage "Proteus installation not found, aborting. Please set PROTEUS_HOME to point to the base directory of your Proteus installation!"
	#exit 1;
fi

PROTEUS="$PROTEUS_HOME/bin/proteus"

if [ -z "$PROTEUS" ]; then
	echo "Proteus executable $PROTEUS not found, aborting."
    xmessage "Proteus executable $PROTEUS not found, aborting."
    exit 1;
fi

if [ -z "$SGE_ARCH" ]; then
        # start netbeans on a cluster host
        #
        #  if $DISPLAY is of the form host:[0-9]* or unix:0.0
        #  take it as is, otherwise (SunRay displays!) we need to
        #  build the correct one...
        #
        NWORDS=`echo $DISPLAY | tr : ' ' | wc -w` 
        if [ "$NWORDS" -ne 2 -o "$DISPLAY" = "unix:0.0" ]; then
                HOSTNAME=`uname -n` 
                DISP=`display | cut -d: -f2`
                DISPLAY=${HOSTNAME}:${DISP}
        fi
        export DISPLAY
        exec qrsh -nostdin -l arch=sol-amd64 -cwd -now y -V $PROTEUS "$@"
else
        # we already run on a cluster host, execute maui locally
        exec $PROTEUS
fi