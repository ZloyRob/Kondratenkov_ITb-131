#!/bin/bash
#./BUILD.sh #
finalerror=1 #
#
echo "Start Test" #
./RUN.sh ""
error=$?
echo "Test 1"
answer=0
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-h "
error=$?
echo "Test 2"
answer=0
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login XXX -pass XXX "
error=$?
echo "Test 3"
answer=1
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login jdoe -pass XXX "
error=$?
echo "Test 4"
answer=2
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login jdoe -pass sup3rpaZZ "
error=$?
echo "Test 5"
answer=0
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login jdoe -pass sup3rpaZZ -role READ -res a "
error=$?
echo "Test 6"
answer=0
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login jdoe -pass sup3rpaZZ -role READ -res a.b "
error=$?
echo "Test 7"
answer=0
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login jdoe -pass sup3rpaZZ -role XXX -res a.b "
error=$?
echo "Test 8"
answer=3
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login jdoe -pass sup3rpaZZ -role READ -res XXX "
error=$?
echo "Test 9"
answer=4
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login jdoe -pass sup3rpaZZ -role WRITE -res a "
error=$?
echo "Test 10"
answer=4
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login jdoe -pass sup3rpaZZ -role WRITE -res a.bc "
error=$?
echo "Test 11"
answer=4
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol 100 "
error=$?
echo "Test 12"
answer=0
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 01-01-2015 -de 2015-12-31 -vol 100 "
error=$?
echo "Test 13"
answer=5
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol XXX "
error=$?
echo "Test 14"
answer=5
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login X -pass X -role READ -res X -ds 2015-01-01 -de 2015-12-31 -vol XXX "
error=$?
echo "Test 15"
answer=1
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
./RUN.sh "-login X -pass X -role READ -res X "
error=$?
echo "Test 16"
answer=1
if [[ $answer != $error ]] #
then #
echo "0" #
finalerror=0 #
else #
echo "1" #
fi #
#
echo "Exit code $finalerror" #
exit $finalerror #
