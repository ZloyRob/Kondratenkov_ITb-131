#!/bin/bash
javac -cp ".\libs\commons-cli-1.3.1.jar;.\libs\commons-codec-1.10.jar;.\libs\commons-lang3-3.5.jar;." -d "./out/production/Kondratenkov_Vadim" src/*.java #
echo "Hello World"
read -p "Press any key..."