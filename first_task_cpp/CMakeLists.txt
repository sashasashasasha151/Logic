cmake_minimum_required(VERSION 3.10)
project(first_task_cpp)

set(CMAKE_CXX_STANDARD 14)

add_executable(first_task_cpp main.cpp include/checker.h include/parser.h include/result.h include/global.h)