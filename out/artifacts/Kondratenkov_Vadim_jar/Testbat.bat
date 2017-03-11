java -jar Kondratenkov_Vadim.jar 
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -h
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login XXX -pass XXX
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login jdoe -pass XXX
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login jdoe -pass sup3rpaZZ
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login jdoe -pass sup3rpaZZ -role READ -res a
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login jdoe -pass sup3rpaZZ -role READ -res a.b
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login jdoe -pass sup3rpaZZ -role XXX -res a.b
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login jdoe -pass sup3rpaZZ -role READ -res XXX
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login jdoe -pass sup3rpaZZ -role WRITE -res a
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login jdoe -pass sup3rpaZZ -role WRITE -res a.bc
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol 100
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 01-01-2015 -de 2015-12-31 -vol 100
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol XXX
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login X -pass X -role READ -res X -ds 2015-01-01 -de 2015-12-31 -vol XXX
echo %ERRORLEVEL%

java -jar Kondratenkov_Vadim.jar -login X -pass X -role READ -res X
echo %ERRORLEVEL%

Pause