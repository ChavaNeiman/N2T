// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
(INIT)
    @SCREEN
    D=A
    @i
    M=D
    
    @KBD
    D=M
    @NOT_PRESSED
    D;JEQ

    D=-1

(NOT_PRESSED)
    @fill
    M=D
    
(FILL_SCREEN)
    @i
    D=M
    @KBD
    D=D-A   //if i<screen_size
    @INIT   // goto STOP
    D;JGE 

    @fill
    D=M
    @i
    A=M
    M=D
    
    D=A+1
    @i
    M=D

    @FILL_SCREEN
    0;JMP