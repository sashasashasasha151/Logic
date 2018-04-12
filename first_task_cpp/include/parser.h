#include <iostream>
#include <string>
#include "result.h"

using namespace std;

class Parser {
private:
    string expression;
    int index = 0;

    Result *parsing() {
        return binary_parsing(0);
    }

    Result *binary_parsing(int current_priority) {
        Result * left = expression_parsing();

        while (true) {
            string op = char_parsing();
            int priority = get_priority(op);
            if(priority == 1) {
                if (priority < current_priority) {
                    index -= op.length();
                    return left;
                }
            } else {
                if (priority <= current_priority) {
                    index -= op.length();
                    return left;
                }
            }

            Result * right = binary_parsing(priority);
            left = new Result(op,left,right);
        }
    }

    Result *expression_parsing() {
        string current_char = char_parsing();

        if (current_char == "(") {
            Result *result = parsing();
            index++;
            return result;
        }

        return (current_char[0] >= 65 && current_char[0] <= 90) ? new Result(current_char) : new Result(current_char,expression_parsing());
    }

    string char_parsing() {

        if (index == expression.length()) {
            return "";
        }

        if (expression[index] >= 65 && expression[index] <= 90) {
            string number = "";
            while (index < expression.length() && ((expression[index] >= 65 && expression[index] <= 90) ||
                                                   (expression[index] >= 48 && expression[index] <= 57))) {
                number += expression[index++];
            }
            return number;
        }

        string operations[] = {"(", ")", "->", "&", "|", "!"};
        for (string operation : operations) {
            if (operation[0] == expression[index]) {
                index += operation.length();
                return operation;
            }
        }
        return "";
    }

    int get_priority(string operation) {
        if (operation == "->") {
            return 1;
        }
        if (operation == "|") {
            return 2;
        }
        if (operation == "&") {
            return 3;
        }
        if (operation == "!") {
            return 4;
        }
        return 0;
    }

public:
    Parser(string expression) : expression(expression) {}

    Result *parse() {
        index = 0;
        return parsing();
    }
};