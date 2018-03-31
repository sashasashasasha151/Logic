cmake_minimum_required(VERSION 3.10)
project(zero_task)

set(CMAKE_CXX_STANDARD 11)

add_executable(zero_task main.cpp include/parser.h include/result.h)