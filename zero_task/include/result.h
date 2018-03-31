#pragma once

#include <iostream>
#include <string>
#include <vector>

using namespace std;

struct Result {
    string expression;
    Result * left = nullptr;
    Result * right = nullptr;
    int number = 0;

    explicit Result(string expression) : expression(std::move(expression)) {
    };

    Result(string expression, Result * a) : expression(std::move(expression)) {
        left = a;
        number = 1;
    }

    Result(string expression, Result * a, Result * b) : expression(std::move(expression)) {
        left = a;
        right = b;
        number = 2;
    }
};