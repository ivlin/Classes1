.data

newline: .asciiz "\n"
array: .space 42
loadfile: .asciiz "hw4/a,txt"
savefile: .asciiz "save_board_example"
.text
.global main

main:

la $a0 array
li $a1 4
li $a2 4
jal clear_board

la $a0 array
la $a1 loadfile
jal load_board
move $s0 $v0
move $s1 $v1

la $a0 array
li $a1 4
li $a2 4
li $a3 2
addi $sp $sp -4
li $t0 2
sw $t0 0($sp)
jal get_slot
move $s5 $v0
move $s6 $v1
addi $sp $sp 4

la $a0 array
move $a1 $s0
move $a2 $s1
jal display_board
move $s4 $v0

la $a0 array
move $a1 $s0
move $a2 $s1
la $a3 savefile
jal save_board
move $s6 $v0

li $v0 10
syscall

.include "hw4.asm"
