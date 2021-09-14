ECHO off
cls
del %1.LA1
java LenguajeA.java %1
IF ERRORLEVEL 1 GOTO fallo
echo Analisis lexicografico terminado
java RecDesc.java %1
IF ERRORLEVEL 1 GOTO fallo
echo Analisis lexicografico terminado
echo No se hallaron errores en la compilacion
goto :salir


:fallo
echo ERRORES EN LA COMPILACION!
:salir
echo COMPILACION TERMINADA
