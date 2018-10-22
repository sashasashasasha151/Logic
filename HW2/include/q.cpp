#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <unordered_map>
#include "include/result.h"
#include "include/parser.h"
#include "include/checker.h"


using namespace std;

vector<Result *> axioms;

string ax[] = {"A->B->A",
               "(A->B)->(A->B->C)->(A->C)",
               "A->B->A&B",
               "A&B->A",
               "A&B->B",
               "A->A|B",
               "B->A|B",
               "(A->C)->(B->C)->(A|B->C)",
               "(A->B)->(A->!B)->!A",
               "!!A->A"};

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

string make_result(Result *current_expression) {
    string sb = "";
    if(current_expression->number == 2) {
        sb+="(";
        sb+=make_result(current_expression->left);
        sb+=current_expression->expression;
        sb+=make_result(current_expression->right);
        sb+=")";
    }
    if(current_expression->number == 1) {
        sb+="!(";
        sb+=make_result(current_expression->left);
        sb+=")";
    }
    if(current_expression->number == 0) {
        return current_expression->expression;
    }
    return sb;
}

pair<string, string> split1(string in) {
    for (int i = 0; i < in.length() - 1; ++i) {
        if (in[i] == '|' && in[i + 1] == '-') {
            return {in.substr(0, i), in.substr(i + 2, in.length() - i - 2)};
        }
    }
}

vector<string> split2(string in) {
    vector<string> ans;
    int start = 0;
    for (int i = 0; i < in.length(); ++i) {
        if (in[i] == ',') {
            ans.push_back(in.substr(start, i - start));
            start = i + 1;
        }
    }
    ans.push_back(in.substr(start, in.length() - start));
    return ans;
}

int main() {

    for (int i = 0; i < 10; ++i) {
        axioms.push_back(Parser(ax[i]).parse());
    }

//    ifstream cin("in.txt");
//    ofstream cout("wfwf.txt");

    string str;
    cin >> str;

    pair<string, string> input_string = split1(str);
    vector<string> as = split2(input_string.first);
    vector<Result *> assumptions;
    for (int i = 0; i < as.size(); ++i) {
        if (as[i] != "") {
            assumptions.push_back(Parser(as[i]).parse());
        }
    }

    int i = 1;

    string s;
    vector<Result *> printed;
    unordered_map<string, int> hashed, hashedH;
    while (cin >> s) {
        Result *new_assumprion = Parser(s).parse();
        bool is_assum = false;

        for (int j = 0; j < assumptions.size(); ++j) {
            if (isEquals(new_assumprion, assumptions[j])) {
                cout << "(" << i++ << ") " + s << " (Предп. " << j + 1 << ")\n";
                is_assum = true;
                break;
            }
        }

        if (is_assum) {
            printed.push_back(new_assumprion);
            hashed[make_result(new_assumprion)] = i - 1;
            if (new_assumprion->expression == "->") {
                hashedH[make_result(new_assumprion->right)] = i - 1;
            }
            continue;
        }

        for (int j = 0; j < 10; ++j) {
            Checker checker = Checker();
            if (checker.checker(axioms[j], new_assumprion)) {
                cout << "(" << i++ << ") " + s << " (Сх. акс. " << j + 1 << ")\n";
                is_assum = true;
                break;
            }
        }

        if (is_assum) {
            printed.push_back(new_assumprion);
            hashed[make_result(new_assumprion)] = i - 1;
            if (new_assumprion->expression == "->") {
                hashedH[make_result(new_assumprion->right)] = i - 1;
            }
            continue;
        }

        string del = make_result(new_assumprion);
        if (hashedH.find(del) != hashedH.end()) {
            string dell = make_result(printed[hashedH[del] - 1]->left);
            if (hashed.find(dell) != hashed.end()) {
                cout << "(" << i++ << ") " + s << " (M.P. " << hashedH[del] << ", " << hashed[dell] << ")\n";
                is_assum = true;
            }
        }

        if (is_assum) {
            printed.push_back(new_assumprion);
            hashed[make_result(new_assumprion)] = i - 1;
            if (new_assumprion->expression == "->") {
                hashedH[make_result(new_assumprion->right)] = i - 1;
            }
            continue;
        }
        cout << "(" << i++ << ") " + s << " (Не доказано)\n";
        printed.push_back(new_assumprion);
        hashed[make_result(new_assumprion)] = i - 1;
        if (new_assumprion->expression == "->") {
            hashedH[make_result(new_assumprion->right)] = i - 1;
        }
    }

    return 0;
}