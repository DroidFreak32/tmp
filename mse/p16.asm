; Multiply and display
PA EQU 40B0H 
PB EQU PA+1 
PC EQU PB+1 
PCW EQU PC+1 
CW EQU 82H 
DATA SEGMENT 
X DB ? 
Y DB ? 
PROD DB ?
DATA ENDS 

CODE SEGMENT 
ASSUME CS:CODE,DS:DATA 
START: 
MOV AX, DATA 
MOV DS, AX 
MOV AL,CW 
MOV DX,PCW 
OUT DX, AL 
MOV DX, PB 
IN AL,DX 
MOV X,AL 
MOV AH,01H 
INT 21H 
MOV DX,PB 
IN AL,DX 
MOV Y,AL 
MOV AL,X 
MUL Y 
MOV DX,PA 
OUT DX, AL 
MOV AH, 4CH 
INT 21H 
CODE ENDS 
END START