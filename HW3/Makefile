.PHONY: all compile run pack clean out
  
MAINCLASS = Main
KOTLIN_PATH=src/

all: prepare-kotlin out compile

run:
	java -jar hello.jar
        
pack:
	zip hw6.zip -r Makefile src kotlin.mk

clean:
	rm -rf out
	rm hello.jar

compile:
	${KOTLINC} -d hello.jar -include-runtime ${KOTLIN_PATH}/*.kt ${KOTLIN_PATH}/include/*.kt -jvm-target 1.8

out:
	mkdir -p out

include kotlin.mk
