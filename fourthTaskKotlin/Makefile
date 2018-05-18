.PHONY: all compile run pack clean out
  
MAINCLASS = Main
KOTLIN_PATH=src/

all: prepare-kotlin out compile

run:
	java -jar hello.jar
        
pack:
	zip hw4.zip -r Makefile src kotlin.mk

clean:
	rm -rf out
	rm hello.jar

compile:
	${KOTLINC} -d hello.jar -include-runtime ${KOTLIN_PATH}/*.kt -jvm-target 1.8

out:
	mkdir -p out

include kotlin.mk
