#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <unordered_map>
#include <ctime>
#include <algorithm>
#include "include/parser.h"
#include "include/checker.h"


using namespace std;

vector<Result *> axioms;

typedef std::unordered_multimap<string, int> simap;

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
    if (current_expression->number == 2) {
        sb += "(" + make_result(current_expression->left) + current_expression->expression +
              make_result(current_expression->right) + ")";
    }
    if (current_expression->number == 1) {
        sb += "!(" + make_result(current_expression->left) + ")";
    }
    if (current_expression->number == 0) {
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
    std::ios::sync_with_stdio(false);

    for (const auto &i : ax) {
        axioms.push_back(Parser(i).parse());
    }

    ifstream cin("in.txt");
    ofstream cout("wfwf.txt");
//    ifstream cin("in.txt");
//    ofstream cout("wfwf.txt");

    string str;
    cin >> str;

    pair<string, string> input_string = split1(str);
    vector<string> as = split2(input_string.first);

    unordered_map<string, int> assumptions;
    for (int i = 0; i < as.size(); ++i) {
        if (!as[i].empty()) {
            assumptions[make_result(Parser(as[i]).parse())] = i;
        }
    }

    int i = 1;

    string s;
    vector<Result *> printed;
    unordered_map<string, int> hashed;
    unordered_multimap<string, int> hashedH;

    while (cin >> s) {
        Result *new_assumprion = Parser(s).parse();
        string aN = make_result(new_assumprion);
        bool is_assum = false;


        if (assumptions.find(aN) != assumptions.end()) {
            cout << "(" << i++ << ") " + s << " (Предп. " << assumptions[aN] + 1 << ")\n";
            is_assum = true;
        }

        if (is_assum) {
            printed.push_back(new_assumprion);
            hashed[aN] = i - 1;
            if (new_assumprion->expression == "->") {
                hashedH.insert({make_result(new_assumprion->right), i - 1});
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
            hashed[aN] = i - 1;
            if (new_assumprion->expression == "->") {
                hashedH.insert({make_result(new_assumprion->right), i - 1});
            }
            continue;
        }

        if (hashedH.find(aN) != hashedH.end()) {
            auto range = hashedH.equal_range(aN);
            auto x = range.first;
            while (x != range.second) {
                string dell = make_result(printed[x->second - 1]->left);
                if (hashed.find(dell) != hashed.end()) {
                    cout << "(" << i++ << ") " + s << " (M.P. " << x->second << ", " << hashed[dell] << ")\n";
                    is_assum = true;
                    break;
                }
                x++;
            }
        }

        if (is_assum) {
            printed.push_back(new_assumprion);
            hashed[aN] = i - 1;
            if (new_assumprion->expression == "->") {
                hashedH.insert({make_result(new_assumprion->right), i - 1});
            }
            continue;
        }
        cout << "(" << i++ << ") " + s << " (Не доказано)\n";

        printed.push_back(new_assumprion);
        hashed[aN] = i - 1;
        if (new_assumprion->expression == "->") {
            hashedH.insert({make_result(new_assumprion->right), i - 1});
        }

    }

    return 0;
}