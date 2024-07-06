#!/bin/bash

# Ensure correct number of arguments
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <ClassName>"
    exit 1
fi

# Set class name from argument
className="$1"

# Clean bin directory
rm -rf bin/*

# Compile source files
javac -d bin src/*.java

# Compile test files (include bin in classpath)
javac -d bin -cp bin test/testClasses/*.java

# Run test for specified class
java -cp bin FinalProject.test.testClasses.$className