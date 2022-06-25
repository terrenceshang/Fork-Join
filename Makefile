JAVAC=/usr/bin/javac
DOCDIR= ./javadocs

.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES= Sequential.class MedianCalculation.class ForkJoin.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class

runseq: $(CLASS_FILES)
	java -cp $(BINDIR) Sequential
	
runpar: $(CLASS_FILES)
	java -cp $(BINDIR) ForkJoin 
       
docs:
	javadoc -classpath ${BINDIR} -d ${DOCDIR} ${SRCDIR}/*.java
        
cleandocs:
	rm -rf ${DOCDIR}/*

