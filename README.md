# Getting Started

## Requirements

-[JAVA SE Development Kit 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
-[Apache ANT](http://ant.apache.org)
-[Git](http://git-scm.com/)

An IDE, preferrably [NetBeans IDE 8](http://www.netbeans.org).

Please note: Proteus requires libraries and other projects that are not directly contained in its
git repository. You need to clone the maltcmsui repository, before you can use Proteus as a git 
submodule.

## Checking out (Read-Only)

    git clone git://git.code.sf.net/p/maltcmsui/code maltcmsui-code

## Checking out (Read/Write)

    git clone ssh://YOUR_SF_USERNAME@git.code.sf.net/p/maltcmsui/code maltcmsui-code
or
    git clone https://YOUR_SF_USERNAME@git.code.sf.net/p/maltcmsui/code maltcmsui-code

## Initialize the Proteus submodule

    git submodule init
    git submodule update

## Commiting and Pushing your work

    git add YOURCHANGEDFILES
    git commit -m "YOURCHANGEMESSAGE"
    git push origin master

## Performing a release

Make sure that implementation changes in modules are reflected in 
updated micro version numbers! E.g. 1.0.0 -> 1.0.1

Make sure that api changes in modules are reflected by updated 
minor version numbers! E.g. 1.0.0 -> 1.1.0

Make sure that incompatible api changes in modules are reflected by
updated major version numbers! E.g. 1.0.0 -> 2.0.0

Incompatible API changes usually involve restructuring of the module's
public APIs, so that legacy code would no longer compile against/work at runtime.

Increase the version within 'nbproject/project.properties'
to indicate a new application version:

    app.version.major=1
    app.version.minor=2
    app.version.micro=1

Increase the micro version for bugfixes or minor functionality improvements.
Increase the minor version for new functionality.
Increase the major number for major new functionality and/or incompatible API changes.

Make sure that the 'site/' is updated to reflect the new release version (check paths).

Commit all pending changes. 
Create a release tag.

Open an ssh connection to sourceforge:

    >ssh -t SFUSERNAME,maltcms@shell.sourceforge.net create

From the IDE, run 

"Package As->Installers"

to create the platform specific installers. This is currently not easily possible from ANT

Run 

    >ant release-and-deploy

to build, test, and deploy the application.

