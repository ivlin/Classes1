# Homework #1
# name: IVAN_LIN
# sbuid: 111020797

.data
.align 2
numargs: .word 0
integer: .word 0
fromBase: .word 0
toBase: .word 0
Err_string: .asciiz "ERROR\n"
output: .word 0

# buffer is 32 space characters
buffer: .ascii "                                "
newline: .asciiz "\n"

# Helper macro for grabbing command line arguments
.macro load_args
	sw $a0, numargs
	
	lw $t0, 0($a1)
	sw $t0, integer
	
	lw $t0, 4($a1)
	sw $t0, fromBase

	lw $t0, 8($a1)
	sw $t0, toBase

.end_macro

.text
.globl main

main:
load_args()

lw $t0 numargs
li $t1 3
bne $t0 $t1 errmsg #check for three args

#check if fromBase is more than 1 char
lw $t0 integer #since machine is little endian the msb (beginning of string) is stored in higher memory addresses
lw $t1 fromBase #little endian so input is stored with first char (msb) at a higher memory address
sub $t2 $t0 $t1 #checks if there are only two bytes between addresses of the first 
li $t3 2
bne $t2 $t3 errmsg

#check ascii for fromBase
lb $t4 0($t1)
check_ascii_1:
	#geq_0:
		li $t5 '2'
		blt $t4 $t5 errmsg
	#leq_F:
		li $t5 'F'
		bgt $t4 $t5 errmsg
	#leq_9:
		li $t5 '9'
		ble $t4 $t5 continue1
	#geq_A:
		li $t5 'A'
		bge $t4 $t5 continue1
	j errmsg
	
continue1:

#check if toBase is more than 1 char
lw $t0 fromBase 
lw $t1 toBase
sub $t2 $t0 $t1 
li $t3 2 
bne $t2 $t3 errmsg

#check ascii for toBase
lb $t4 0($t1)
check_ascii_2:
	#geq_0:
		li $t5 '2'
		blt $t4 $t5 errmsg
	#leq_F:
		li $t5 'F'
		bgt $t4 $t5 errmsg
	#leq_9:
		li $t5 '9'
		ble $t4 $t5 continue2
	#geq_A:
		li $t5 'A'
		bge $t4 $t5 continue2
	j errmsg	
continue2:


#check integer
lw $t0 integer
lw $t1 fromBase
lb $s1 0($t1)#byte at fromBase
lb $t1 0($t0)#byte at integer

add $s0 $0 $0 #s=0
while:
	beqz $t1 close_loop
	lb $t1 0($t0)#loads byte from integer
	#geq_0:
		li $t5 '0'
		blt $t4 $t5 errmsg
	#leq_F:
		li $t5 'F'
		bgt $t4 $t5 errmsg
	#leq_9:
		li $t5 '9'
		ble $t4 $t5 continue3
	#geq_A:
		li $t5 'A'
		bge $t4 $t5 continue3
		j errmsg	
	continue3:
	bge $t1 $s1 errmsg#make sure its less than the base value
	addi $t0 $t0 1#next byte in int
	j while
close_loop:

#conversion to base 10

lw $t1 fromBase
lb $s2 0($t1)#byte at fromBase

#converts from ascii to int
add $s2 $s2 $0
li $t3 '9'
ble $s2 $t3 skip_conversion #converts hex to sequentially follow number chars
li $t3 'A'
sub $s2 $s2 $t3 
li $t3 '9'
add $s2 $s2 $t3
addi $s2 $s2 1
skip_conversion:
li $t3 '0'
sub $s2 $s2 $t3 
#s2 now stores int representation of the base

#converts from ascii to int
li $t5 '9'
li $t6 'A'
sub $t7 $t6 $t5
li $t5 1
sub $t7 $t7 $t5
# this is the code difference between ascii 'A' and '0'

lw $t0 integer
lb $t1 0($t0)#byte at integer
#now mult
add $s0 $0 $0 #will be running sum
while_2:
	beqz $t1 close_loop_2
	li $t3 '9'
	ble $t1 $t3 skip_int_conversion #converts hex to sequentially follow number chars
	sub $t1 $t1 $t7
	skip_int_conversion:
	li $t3 '0'
	sub $t1 $t1 $t3
	#multiplication method
	mult $s0 $s2
	mflo $s0
	add $s0 $s0 $t1
	#nupdate loop
	addi $t0 $t0 1#next byte in int
	lb $t1 0($t0)#byte at integer #for some reason breaks at the beginning of loop
	j while_2
close_loop_2:

lw $t1 toBase
lb $s2 0($t1)
#converts from ascii to int
add $s2 $s2 $0
li $t3 '9'
ble $s2 $t3 skip_conversion_2 #converts hex to sequentially follow number chars
sub $s2 $s2 $t7
skip_conversion_2:
li $t3 '0'
sub $s2 $s2 $t3 
#s2 now stores int representation of the base



add $s3 $0 $0#s3 will hold new answer
la $t4 buffer#get memory address of buffer
addi $t4 $t4 31#offset from beginning of buffer

#s0 now holds num in base 10 form
#S2 hold the toBase
li $t6 '0'#prepend 0
sb  $t6 0($t4)

while_3:
	beqz $s0 close_loop_3
	div $s0 $s2
	mflo $s0#quotient
	add $t2 $0 $0
	mfhi $t2#remainder
	
	li $t3 9
	ble $t2 $t3 skip_hex_conversion#remainder greater than 9
	add $t2 $t2 $t7
	skip_hex_conversion:
	
	addi $t2 $t2 '0'
	sb $t2 0($t4)
	li $t5 1#decrement offset since its right justified
	sub $t4 $t4 $t5
	j while_3
close_loop_3:

end:
li $v0 4
la $a0 buffer
syscall		
li $v0, 10 #end program
syscall
		
			
errmsg: #print error, kill program
	li $v0 4
	la $a0 Err_string
	syscall
	li $v0, 10
	syscall
