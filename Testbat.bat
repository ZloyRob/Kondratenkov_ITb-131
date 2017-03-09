jar Kondratenkov_Vadim.jar -login 111 -pass pas1
echo %ERRORLEVEL%
jar Kondratenkov_Vadim.jar -login log1 -pass pas22
echo %ERRORLEVEL%
jar Kondratenkov_Vadim.jar -login log1 -pass pas1 
echo %ERRORLEVEL%
jar Kondratenkov_Vadim.jar -login log1 -pass pas1 -res A.B.C
echo %ERRORLEVEL%
jar Kondratenkov_Vadim.jar -login log1 -pass pas1 -res A.B.C -role WRITE
echo %ERRORLEVEL%
jar Kondratenkov_Vadim.jar -login log1 -pass pas1 -res A.B.Ce -role WRITE
echo %ERRORLEVEL%
jar Kondratenkov_Vadim.jar -login log1 -pass pas1 -res A.B.C -role READ
echo %ERRORLEVEL%
jar Kondratenkov_Vadim.jar -login log1 -pass pas1 -res A.B.C -role WROTE
echo %ERRORLEVEL%
jar Kondratenkov_Vadim.jar -login log1 -pass pas1 -res A.B.C -role WRITE -ds 2016-11-30 -de 20016-11-30 -vol 256
echo %ERRORLEVEL%
jar Kondratenkov_Vadim.jar -login log1 -pass pas1 -res A.B.C -role WRITE -ds вчера -de завтра -vol 256
echo %ERRORLEVEL%
jar Kondratenkov_Vadim.jar -login log1 -pass pas1 -res A.B.C -role WRITE -ds 2016-11-30 -de 20016-11-30 -vol сколько получится
echo %ERRORLEVEL%
jar Kondratenkov_Vadim.jar -yatojelogin log -login log1 -pass pas1
echo %ERRORLEVEL%