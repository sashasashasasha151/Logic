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
    string sb;
    if (current_expression->number == 2) {
        sb += "(";
        sb += make_result(current_expression->left);
        sb += current_expression->expression;
        sb += make_result(current_expression->right);
        sb += ")";
    }
    if (current_expression->number == 1) {
        sb += "!(";
        sb += make_result(current_expression->left);
        sb += ")";
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

    for (const auto &i : ax) {
        axioms.push_back(Parser(i).parse());
    }

    ifstream cin("in.txt");
    ofstream cout("wfwf.txt");

    string str;
    cin >> str;

    pair<string, string> input_string = split1(str);
    vector<string> as = split2(input_string.first);

    vector<Result *> assumptions;

    for (int i = 0; i < as.size() - 1; ++i) {
        if (!as[i].empty()) {
            assumptions.push_back(Parser(as[i]).parse());
            cout << as[i];
            if (i != as.size() - 2) {
                cout << ",";
            }
        }
    }
    Result *alpha = Parser(as[as.size() - 1]).parse();
    string _alpha = make_result(alpha);

    string something_strange = _alpha + "->(" + _alpha + "->" + _alpha + ")\n" +
                               "(" + _alpha + "->(" + _alpha + "->" + _alpha + "))->(" + _alpha + "->((" + _alpha +
                               "->" + _alpha + ")->" + _alpha + "))->(" + _alpha + "->" + _alpha + ")\n" +
                               "(" + _alpha + "->((" + _alpha + "->" + _alpha + ")->" + _alpha + "))->(" + _alpha +
                               "->" + _alpha + ")\n" +
                               "(" + _alpha + "->((" + _alpha + "->" + _alpha + ")->" + _alpha + "))\n";
    Result *beta = Parser(input_string.second).parse();
    Result *terminate = new Result("->", alpha, beta);
    cout << "|-";
    cout << make_result(new Result("->", Parser(as[as.size() - 1]).parse(), Parser(input_string.second).parse()))
         << "\n";

    string s;

    int i = 0;

    vector<Result *> input;

    while (cin >> s) {
        Result *r = Parser(s).parse();
        input.push_back(r);
    }

    vector<Result *> printed;

    for (int k = 0; k < input.size(); ++k) {
        Result *r = input[k];
        string result = make_result(r);

        bool is_assum = false;

        for (auto &assumption : assumptions) {
            if (isEquals(r, assumption)) {
                is_assum = true;
                break;
            }
        }

        if (is_assum) {
            cout << result << "\n";
            cout << result << "->(" << _alpha << "->" << result << ")\n";
            string out = "(" + _alpha + "->" + result + ")";
            cout << out << "\n";
            printed.push_back(r);
            continue;
        }

        for (int j = 0; j < 10; ++j) {
            Checker checker = Checker();
            if (checker.checker(axioms[j], r)) {
                is_assum = true;
                break;
            }
        }

        if (is_assum) {
            cout << result << "\n";
            cout << result << "->(" << _alpha << "->" << result << ")\n";
            cout << _alpha << "->" << result << "\n";
            printed.push_back(r);
            continue;
        }

        if (isEquals(r, alpha)) {
            cout << something_strange;
            cout << _alpha << "->" << result << "\n";
            printed.push_back(r);
            continue;
        }

        int lft;

        for (int j = printed.size() - 1; j >= 0; --j) {
            if (printed[j]->expression == "->" && isEquals(r, printed[j]->right)) {
                for (int k = printed.size() - 1; k >= 0; --k) {
                    if (isEquals(printed[k], printed[j]->left)) {
                        lft = k;
                        is_assum = true;
                        break;
                    }
                }
                if (is_assum) {
                    break;
                }
            }
        }


        if (is_assum) {
            string _lft = make_result(printed[lft]);

            string s1 =
                    "(" + _alpha + "->" + _lft + ")->((" + _alpha + "->(" + _lft + "->" + result + "))->(" + _alpha + "->" + result + "))\n";
            string s2 = "((" + _alpha + "->(" + _lft + "->" + result + "))->(" + _alpha + "->" + result + "))\n";
            cout << s1 << s2 << _alpha << "->" << result << "\n";
            printed.push_back(r);
            continue;
        } else {
            while (true) {
                i++;
            }
        }
    }

    return 0;
}