.PHONY: all compile run pack clean out
  
MAINCLASS = hw0.MainKt
KOTLIN_PATH=src/main/kotlin/

all: prepare-kotlin out compile

run:
	java -jar hello.jar
        
pack:
	zip hw1.zip -r Makefile src kotlin.mk

clean:	
	rm -rf out	
	rm hello.jar

compile:
	${KOTLINC} -d hello.jar -include-runtime ${KOTLIN_PATH}/hw1/*.kt 

out:
	mkdir -p out

include kotlin.mk