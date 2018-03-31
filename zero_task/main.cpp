#include <iostream>
#include "include/parser.h"

using namespace std;


int main() {
    string str;
    cin >> str;
    Parser parser = Parser(str);
    parser.parse();
    return 0;
}
//P->!QQ->!R10&S|!T&U&V
//(->,P,(->,(!QQ),(|,(&,(!R10),S),(&,(&,(!T),U),V))))
//(->,P,(->,(!QQ),(|,(&,(!R10),S),(&,(&,(!T),U),V))))
//(->,(&,(!A),(!B)),(!(|,A,B)))