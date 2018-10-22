CXXFLAGS+=-std=c++14 -Wall -O2 
CXX=g++ 

all: out out/main.o 

run: out out/main.o 
	./out/main.o

out/main.o: main.cpp
	$(CXX) $^ $(CXXFLAGS) -o out/main.o

pack:
	zip hw1_cpp.zip -r Makefile main.cpp include

out: 
	mkdir -p out

clean: 
	rm -rf out
	rm -f include/*.h
