#pragma once

#include <iostream>
#include <string>

using namespace std;

struct Result {
    string expression;
    Result *left = nullptr;
    Result *right = nullptr;
    int number = 0;

    Result(string expression) : expression(expression) {}

    Result(string expression, Result *a, Result *b) : expression(expression), left(a), right(b), number(2) {}

    Result(string expression, Result *a) : expression(expression), left(a), number(1) {}
};