# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.10

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /home/sasha/.local/share/JetBrains/Toolbox/apps/CLion/ch-0/181.4203.549/bin/cmake/bin/cmake

# The command to remove a file.
RM = /home/sasha/.local/share/JetBrains/Toolbox/apps/CLion/ch-0/181.4203.549/bin/cmake/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/sasha/Code/Logic/first_task_cpp

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/sasha/Code/Logic/first_task_cpp/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/first_task_cpp.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/first_task_cpp.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/first_task_cpp.dir/flags.make

CMakeFiles/first_task_cpp.dir/main.cpp.o: CMakeFiles/first_task_cpp.dir/flags.make
CMakeFiles/first_task_cpp.dir/main.cpp.o: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/sasha/Code/Logic/first_task_cpp/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/first_task_cpp.dir/main.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/first_task_cpp.dir/main.cpp.o -c /home/sasha/Code/Logic/first_task_cpp/main.cpp

CMakeFiles/first_task_cpp.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/first_task_cpp.dir/main.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/sasha/Code/Logic/first_task_cpp/main.cpp > CMakeFiles/first_task_cpp.dir/main.cpp.i

CMakeFiles/first_task_cpp.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/first_task_cpp.dir/main.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/sasha/Code/Logic/first_task_cpp/main.cpp -o CMakeFiles/first_task_cpp.dir/main.cpp.s

CMakeFiles/first_task_cpp.dir/main.cpp.o.requires:

.PHONY : CMakeFiles/first_task_cpp.dir/main.cpp.o.requires

CMakeFiles/first_task_cpp.dir/main.cpp.o.provides: CMakeFiles/first_task_cpp.dir/main.cpp.o.requires
	$(MAKE) -f CMakeFiles/first_task_cpp.dir/build.make CMakeFiles/first_task_cpp.dir/main.cpp.o.provides.build
.PHONY : CMakeFiles/first_task_cpp.dir/main.cpp.o.provides

CMakeFiles/first_task_cpp.dir/main.cpp.o.provides.build: CMakeFiles/first_task_cpp.dir/main.cpp.o


# Object files for target first_task_cpp
first_task_cpp_OBJECTS = \
"CMakeFiles/first_task_cpp.dir/main.cpp.o"

# External object files for target first_task_cpp
first_task_cpp_EXTERNAL_OBJECTS =

first_task_cpp: CMakeFiles/first_task_cpp.dir/main.cpp.o
first_task_cpp: CMakeFiles/first_task_cpp.dir/build.make
first_task_cpp: CMakeFiles/first_task_cpp.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/sasha/Code/Logic/first_task_cpp/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable first_task_cpp"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/first_task_cpp.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/first_task_cpp.dir/build: first_task_cpp

.PHONY : CMakeFiles/first_task_cpp.dir/build

CMakeFiles/first_task_cpp.dir/requires: CMakeFiles/first_task_cpp.dir/main.cpp.o.requires

.PHONY : CMakeFiles/first_task_cpp.dir/requires

CMakeFiles/first_task_cpp.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/first_task_cpp.dir/cmake_clean.cmake
.PHONY : CMakeFiles/first_task_cpp.dir/clean

CMakeFiles/first_task_cpp.dir/depend:
	cd /home/sasha/Code/Logic/first_task_cpp/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/sasha/Code/Logic/first_task_cpp /home/sasha/Code/Logic/first_task_cpp /home/sasha/Code/Logic/first_task_cpp/cmake-build-debug /home/sasha/Code/Logic/first_task_cpp/cmake-build-debug /home/sasha/Code/Logic/first_task_cpp/cmake-build-debug/CMakeFiles/first_task_cpp.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/first_task_cpp.dir/depend

