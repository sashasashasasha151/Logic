# CMAKE generated file: DO NOT EDIT!
# Generated by "MinGW Makefiles" Generator, CMake Version 3.10

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

SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = C:\Users\sasha\AppData\Local\JetBrains\Toolbox\apps\CLion\ch-0\181.4203.549\bin\cmake\bin\cmake.exe

# The command to remove a file.
RM = C:\Users\sasha\AppData\Local\JetBrains\Toolbox\apps\CLion\ch-0\181.4203.549\bin\cmake\bin\cmake.exe -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = C:\Code\Logic\first_task

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = C:\Code\Logic\first_task\cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/first_task.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/first_task.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/first_task.dir/flags.make

CMakeFiles/first_task.dir/main.cpp.obj: CMakeFiles/first_task.dir/flags.make
CMakeFiles/first_task.dir/main.cpp.obj: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=C:\Code\Logic\first_task\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/first_task.dir/main.cpp.obj"
	C:\MinGW\MinGW\bin\g++.exe  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles\first_task.dir\main.cpp.obj -c C:\Code\Logic\first_task\main.cpp

CMakeFiles/first_task.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/first_task.dir/main.cpp.i"
	C:\MinGW\MinGW\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E C:\Code\Logic\first_task\main.cpp > CMakeFiles\first_task.dir\main.cpp.i

CMakeFiles/first_task.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/first_task.dir/main.cpp.s"
	C:\MinGW\MinGW\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S C:\Code\Logic\first_task\main.cpp -o CMakeFiles\first_task.dir\main.cpp.s

CMakeFiles/first_task.dir/main.cpp.obj.requires:

.PHONY : CMakeFiles/first_task.dir/main.cpp.obj.requires

CMakeFiles/first_task.dir/main.cpp.obj.provides: CMakeFiles/first_task.dir/main.cpp.obj.requires
	$(MAKE) -f CMakeFiles\first_task.dir\build.make CMakeFiles/first_task.dir/main.cpp.obj.provides.build
.PHONY : CMakeFiles/first_task.dir/main.cpp.obj.provides

CMakeFiles/first_task.dir/main.cpp.obj.provides.build: CMakeFiles/first_task.dir/main.cpp.obj


# Object files for target first_task
first_task_OBJECTS = \
"CMakeFiles/first_task.dir/main.cpp.obj"

# External object files for target first_task
first_task_EXTERNAL_OBJECTS =

first_task.exe: CMakeFiles/first_task.dir/main.cpp.obj
first_task.exe: CMakeFiles/first_task.dir/build.make
first_task.exe: CMakeFiles/first_task.dir/linklibs.rsp
first_task.exe: CMakeFiles/first_task.dir/objects1.rsp
first_task.exe: CMakeFiles/first_task.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=C:\Code\Logic\first_task\cmake-build-debug\CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable first_task.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\first_task.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/first_task.dir/build: first_task.exe

.PHONY : CMakeFiles/first_task.dir/build

CMakeFiles/first_task.dir/requires: CMakeFiles/first_task.dir/main.cpp.obj.requires

.PHONY : CMakeFiles/first_task.dir/requires

CMakeFiles/first_task.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\first_task.dir\cmake_clean.cmake
.PHONY : CMakeFiles/first_task.dir/clean

CMakeFiles/first_task.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" C:\Code\Logic\first_task C:\Code\Logic\first_task C:\Code\Logic\first_task\cmake-build-debug C:\Code\Logic\first_task\cmake-build-debug C:\Code\Logic\first_task\cmake-build-debug\CMakeFiles\first_task.dir\DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/first_task.dir/depend

