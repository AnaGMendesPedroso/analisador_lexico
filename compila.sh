#!/bin/bash
PWD=$(pwd)
cd "$PWD"/src
javac *.java
mv *.class ../class
