#pragma once

#include <iostream>
#include <string>
#include "result.h"

using namespace std;

class Parser {
private:
    string expression;
    int index = 0;

    void make_result(Result * current_expression) {
        switch (current_expression->number) {
            case 2: {
                cout << "(" << current_expression->expression << ",";
                make_result(current_expression->left);
                cout << ",";
                make_result(current_expression->right);
                cout << ")";
                break;
            }
            case 1: {
                cout << "(!";
                make_result(current_expression->left);
                cout << ")";
                break;
            }
            case 0: {
                cout << current_expression->expression;
                break;
            }
            default:
                break;
        }
    }

    Result * parsing() {
        return binary_parsing(0);
    }

    Result * binary_parsing(int current_priority) {
        Result * left = expressions_parsing();

        while (true) {
            string op = char_parsing();
            int priority = get_priority(op);
            if (priority == 1) {
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
            left = new Result(op, left, right);
        }
    }

    Result * expressions_parsing() {
        string currentChar = char_parsing();

        if (currentChar == "(") {
            Result * result = parsing();
            index++;
            return result;
        }

        if (currentChar[0] >= 65 && currentChar[0] <= 90) {
            return new Result(currentChar);
        }

        return new Result(currentChar, expressions_parsing());;
    }

    string char_parsing() {

        if (index == expression.length()) {
            return "";
        }

        if (expression[index] >= 65 && expression[index] <= 90) {
            string number;
            while (index < expression.length() && ((expression[index] >= 65 && expression[index] <= 90) ||
                                                   (expression[index] >= 48 && expression[index] <= 57))) {
                number += expression[index++];
            }
            return number;
        }

        string operations[] = {"(", ")", "->", "&", "|", "!"};
        for (auto &operation : operations) {
            if (operation[0] == expression[index]) {
                index += operation.length();
                return operation;
            }
        }
        return "";
    }

    int get_priority(const string &operation) {
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
    explicit Parser(string expression) : expression(std::move(expression)) {};

    void parse() {
        index = 0;
        make_result(parsing());
    }
};