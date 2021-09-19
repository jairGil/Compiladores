ECHO off
cls
del %1.LA1

java DiagLA.java %1
IF ERRORLEVEL 1 GOTO fallo

echo "Analisis lexicográfico terminado"

java RecDesc3.java %1
IF ERRORLEVEL 1 GOTO fallo

echo "Analisis sintáctico terminado"
echo "No se hallaron errores en la compilación"
goto :salir

:fallo
    echo "ERRORES EN LA COMPILACION!"

:salir
    echo "COMPILACION TERMINADA"
