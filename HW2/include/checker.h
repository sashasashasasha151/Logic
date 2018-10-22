#pragma once

#include <iostream>
#include <string>
#include "result.h"

using namespace std;

class Checker {
private:
    Result *A = nullptr;
    Result *B = nullptr;
    Result *C = nullptr;

    bool isEquals(Result *left, Result *right) {
        if (left == nullptr && right == nullptr) {
            return true;
        }
        if ((left == nullptr && right != nullptr) || (left != nullptr && right == nullptr)) {
            return false;
        }
        if (left->expression == right->expression) {
            return isEquals(left->left, right->left) && isEquals(left->right, right->right);
        }
        return false;
    }

public:
    bool checker(Result *axiom, Result *expression) {
        if (expression == nullptr) {
            return false;
        }
        if (axiom->expression == "A") {
            if (A == nullptr) {
                A = expression;
                return true;
            } else {
                return isEquals(A, expression);
            }
        }
        if (axiom->expression == "B") {
            if (B == nullptr) {
                B = expression;
                return true;
            } else {
                return isEquals(B, expression);
            }
        }
        if (axiom->expression == "C") {
            if (C == nullptr) {
                C = expression;
                return true;
            } else {
                return isEquals(C, expression);
            }
        }
        if (axiom->expression == "!") {
            if (expression->expression == "!") {
                return checker(axiom->left, expression->left);
            } else {
                return false;
            }
        }
        if (axiom->expression == expression->expression) {
            return checker(axiom->left, expression->left) && checker(axiom->right, expression->right);
        }
        return false;
    }
};
