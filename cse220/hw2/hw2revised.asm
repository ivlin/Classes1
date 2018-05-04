##############################################################
# Homework #2
# name: IVAN_LIN
# sbuid: 111020797
##############################################################
.text

##############################
# PART 1 FUNCTIONS
##############################

char2digit:
    #Converts ASCII chars '0' to '9' to int values
    	li $t0 '0'
    	li $t1 '9'
	addi $v0 $0 -1
    	blt $a0 $t0 not_a_digit
    	bgt $a0 $t1 not_a_digit
    	sub $v0 $a0 $t0
    	not_a_digit:
	jr $ra

memchar2digit:
    #Converts ASCII at specified address to int values
	addi $sp $sp -4
	sw $ra 0($sp)
	lb $a0 0($a0)
	jal char2digit
	lw $ra 0($sp)
	addi $sp $sp 4
        jr $ra

fromExcessk:
    #Define your code here 
	li $v0 -1
	move $v1 $a0#change 1
	bltz $a0 leq_0#change 2
	blez $a1 leq_0
	sub $v1 $a0 $a1
	li $v0 0
	leq_0:  
	jr $ra

printNbitBinary:
    #Define your code here
    	li $v0 -1#change 3
    	blez $a1 invalid_m#change 4
    	bgt $a1 32 invalid_m
	addi $sp $sp -8
	sw $s0 0($sp)
	sw $s1 4($sp)
	li $v0 1 #print int
	li $t0 32
	sub $t0 $t0 $a1
	sllv $a0 $a0 $t0
	move $s0 $a0
	move $s1 $a1
	bitshift:
		beqz $s1 mzero
		li $a0 1 #print 1 if less than 0
		bltz $s0 isNegative
		li $a0 0
		isNegative:
		syscall
		sll $s0 $s0 1
		addi $s1 $s1 -1
		j bitshift
	mzero:	
    	li $v0 0
    	lw $s0 0($sp)
    	lw $s1 4($sp)
    	addi $sp $sp 8
    	invalid_m:
    	jr $ra

##############################
# PART 2 FUNCTIONS
##############################

btof:
    #Define your code here
    	addi $sp $sp -24
    	sw $ra 0($sp)
    	sw $s0 4($sp)
    	sw $s1 8($sp)
    	sw $s2 12($sp)
    	sw $s3 16($sp)
    	sw $s4 20($sp)
    	add $s0 $a0 $0 #s0 points to the current position in the string
	lb $s1 0($a0) #s1 holds the code for the next byte
	add $s2 $a0 $0 #s2 points to the beginning of the string
	add $s3 $0 $0 #s3 hold the decimal value 
	add $s4 $0 $0
	#add $t4 $0 $0
	fraction:
		caseNaN:#check if is NanN if is, skip to end
			lb $t0 0($s0)
			bne $t0 'N' checkSigns
			lb $t0 1($s0)
			bne $t0 'a' checkSigns
			lb $t0 2($s0)
			bne $t0 'N' checkSigns
			#is NaN 
			li $v0 0
			add $v1 $0 $0
			not $v1 $v1
			srl $v1 $v1 1
			j return_str_to_bin
		checkSigns: #check for plus/minus sign. save sign to s4. increment start of string
		bne $s1 '-' notNegative
			addi $s0 $s0 1
			addi $s2 $s2 1
			addi $s4 $0 1
			sll $s4 $s4 8
			j noSign
		notNegative:
		bne $s1 '+' noSign
			addi $s2 $s2 1
			addi $s0 $s0 1
		noSign:
		caseInf:#check if Inf. Then skip to end
			lb $t0 0($s0)
			bne $t0 'I' checkForZeroes
			lb $t0 1($s0)
			bne $t0 'n' checkForZeroes
			lb $t0 2($s0)
			bne $t0 'f' checkForZeroes
			#is Inf 
			li $v0 0
			li $t1 255
			or $v1 $s4 $t1
			sll $v1 $v1 23
			j return_str_to_bin
		checkForZeroes:#cuts out prefixing 0s
		lb $s1 0($s0)
		bne $s1 '0' noLeadZeros
			addi $s0 $s0 1
			addi $s2 $s2 1
			j checkForZeroes
		noLeadZeros:
		nextDigit:#converts string to binary
			beqz $s1 finishedReading
			caseDecimal: #saves position of decimal to s3
				bne $s1 '.' isNotDecimal
				add $s3 $s0 $0
				j valid_char
			isNotDecimal:
			beq $s1 '0' valid_char
			beq $s1 '1' valid_char
			beq $s1 10 valid_char#newline
			addi $v0 $0 -1
			j return_str_to_bin
			valid_char:
			jal memchar2digit
			beq $v0 -1 not_digit
				sll $v1 $v1 1
				add $v1 $v1 $v0	
			not_digit:
		done_checking:
		addi $t7 $v1 0
		addi $a0 $s0 1
		add $s0 $a0 $0
		lb $s1 0($a0)
		j nextDigit
		finishedReading:
   	#end main body
   	
   	checkIfZero:#check fo 0.0 and -0.0
   		beqz $v1 doneShifting
   		#if zero, just return zerod out register
   	notZero:
   	
	sub $t0 $s0 $s2#distance from the current position to the beginning of string
   	addi $t0 $t0 -1
   	li $t1 24#changed from 24
	sub $t1 $t1 $t0
   	sub $t0 $s3 $s2#distance from decimal to beginnning of the string
   	addi $s4 $s4 127
	lb $t4 0($s2)
   	beq $t4 '1' greaterThan1
   	distToDecimal:#calculate distance between end of number and the decimal point
		#calculate for cases where number is less than 0
		beq $t2 '1' doneWithDecimal
   		addi $t1 $t1 1
   		addi $s4 $s4 -1
   		addi $s2 $s2 1
		lb $t2 0($s2)
		bnez $t2 notEOL
			j doneWithDecimal
		notEOL:
		j distToDecimal
		#calculate for cases where number is greater than 0
	greaterThan1:
		add $s4 $s4 $t0
		addi $t1 $t1 1
		addi $s4 $s4 -1
   	doneWithDecimal:
   	add $t6 $v1 $0
   	
   	#the number is currently right aligned. shift it to the 10th place in the register 
   	bgez $t1 isPositive
   		not $t1 $t1
   		addi $t1 $t1 1
   		add $t7 $t1 $0
   		srlv $v1 $v1 $t1
   		j doneShifting
   	isPositive:
   		sllv $v1 $v1 $t1
   	
   	add $t5 $v1 $0
   	sll $v1 $v1 9
   	srl $v1 $v1 9
   		
   	doneShifting:
   	sll $s4 $s4 23 #distance from 8th bit from right to the 31st bit --> fraction 	
   	or $v1 $v1 $s4#combines sign in s4
	li $v0 0
   	return_str_to_bin:
   	lw $ra 0($sp)
   	lw $s0 4($sp)
   	lw $s1 8($sp)
   	lw $s2 12($sp)
   	lw $s3 16($sp)
   	lw $s4 20($sp)
   	addi $sp $sp 24
    	jr $ra

print_parts:
	addi $sp $sp -8
	sw $s0 0($sp)
	sw $s1 4($sp)
    add $v0 $0 $0#zero out return register
    move $s0 $a0#store arg in s0
    #s1 holds the sign
    
    
   	move $t0 $a0
   	srl $a0 $a0 31
   	li $v0 1
   	syscall
 
   	   
    li $v0 4#print sign of the number
 
     	la $a0 space
   	syscall
   	move $a0 $t0
   	
    check_sign:
    	bltz $a0 negativeSign
   	positiveSign:
   		addi $s1 $0 1
		la $a0 printPositive
   		j doneReadingSigns
   	negativeSign:
   		addi $s1 $0 -1
		la $a0 printNegative
    doneReadingSigns:
   	syscall
   	la $a0 newline
   	li $v0 4
   	syscall
    	set_returns:
    special_cases: #check for special cases
   	add $t0 $0 $0 #0.0
   	
   	addi $t1 $0 1
   	sll $t1 $t1 31 #-0.0
   	
   	addi $t2 $0 255
   	sll $t2 $t2 30 #+Inf
   	
   	addi $t3 $0 1
   	sll $t3 $t3 8
   	addi $t3 $t3 255 #-Inf
   	
   	add $t4 $0 $0
   	not $t4 $t4
   	srl $t4 $t4 1 #NaN
   	
   	beq $s0 $t0 special 
   	beq $s0 $t1 special
   	beq $s0 $t2 special
   	beq $s0 $t3 special
   	beq $s0 $t4 special
   	j notSpecial
   	special:   		
   		add $s0 $0 $0
   	notSpecial:
   	move $a0 $s0
   	decimal:
   		sll $a0 $a0 1
   		srl $a0 $a0 24
   	
  	addi $sp $sp -8
  	sw $a0 0($sp)
   	sw $ra 4($sp)
   	li $a1 8 
   	jal printNbitBinary
   	la $a0 space
   	li $v0 4
   	syscall#print space
   	lw $ra 4($sp)
   	lw $a0 0($sp)
  	addi $sp $sp 8
   	li $v0 1
   	syscall
   	
   	li $v0 4
   	la $a0 newline
   	syscall
   	mantissa:#print mantissa
   	move $a0 $s0
   	sll $a0 $a0 9
   	srl $a0 $a0 9
   	
   	addi $sp $sp -8
  	sw $a0 0($sp)
   	sw $ra 4($sp)
   	li $a1 23
   	jal printNbitBinary
   	
   	la $a0 space
   	li $v0 4
   	syscall#print space
   	lw $ra 4($sp)
   	lw $a0 0($sp)
  	addi $sp $sp 8
   	li $v0 1
   	syscall
	lw $s0 0($sp)
	lw $s1 4($sp)
	addi $sp $sp 8
    jr $ra

print_binary_product:
	addi $sp $sp -4
	sw $s0 0($sp)
    add $v0 $0 $0#zero out return register
    move $s0 $a0#store arg in s0
    #s1 holds the sign
    	special_cases_2:
   	add $t0 $0 $0 #0.0
   	
   	addi $t1 $0 1
   	sll $t1 $t1 31 #-0.0
   	
   	addi $t2 $0 255
   	sll $t2 $t2 23 #+Inf
   	
   	addi $t3 $0 1
   	sll $t3 $t3 8
   	addi $t3 $t3 255 
   	sll $t3 $t3 23 #-Inf
   	
   	add $t4 $0 $0
   	not $t4 $t4
   	srl $t4 $t4 1 #NaN
   	
   	beq $s0 $t0 special_2 
   	beq $s0 $t1 special_2
   	beq $s0 $t2 special_2
   	beq $s0 $t3 special_2
   	beq $s0 $t4 special_2
   	j not_special_2
   	special_2:
   		li $v0 0
   		j return_print_binary_product
   	not_special_2:
 
   		
  li $v0 4#printstirng
    check_sign_2:
    	bltz $a0 negativeSign_2
   	positiveSign_2:
   		addi $s1 $0 1
		la $a0 printPositive
   		j doneReadingSigns_2
   	negativeSign_2:
   		addi $s1 $0 -1
		la $a0 printNegative
   	doneReadingSigns_2:
   	syscall
   	li $v0 1
	li $a0 1
	syscall
	li $v0 4
	la $a0 dot
	syscall
   		  		  		  		
  	li $v0 1
   	
   	move $a0 $s0
   	sll $a0 $a0 9
   	srl $a0 $a0 9
   	
   	addi $sp $sp -8
  	sw $a0 0($sp)
   	sw $ra 4($sp)
   	li $a1 23
   	jal printNbitBinary
   	
   	la $a0 space
   	li $v0 4
   	syscall#print space
   	lw $a0 0($sp)
   	lw $ra 4($sp)
  	
   	addi $sp $sp -8
   	exponent:
   	#print exponent
   	sw $ra 0($sp)
   	move $a0 $s0
   	sll $a0 $a0 1
   	srl $a0 $a0 24
   	li $a1 127
   	jal fromExcessk
   	lw $ra 0($sp)
   	
   	addi $sp $sp 4
   	
   	move $t0 $v1
   	 li $v0 4
   	 la $a0 times
   	 syscall
   	 li $v0 1
   	 li $a0 2
   	 syscall
   	 li $v0 4
   	 la $a0 exp
   	 syscall
   	 move $a0 $v1
   	 
   	 move $t0 $v1
   	 li $v0 4
   	 la $a0 printPositive
   	 bgez $a0 isPositive_2
   	 	la $a0 printNegative
   	 isPositive_2:
   	 syscall
   	 
   	 move $a0 $t0
   	 li $v0 1
   	 syscall
    	return_print_binary_product:
	lw $s0 0($sp)
	addi $sp $sp 4
    jr $ra



#################################################################
# Student defined data section
#################################################################
.data
.align 2  # Align next items to word boundary

#place all data declarations here


printPositive: .asciiz "+"
printNegative: .asciiz "-"
space: .asciiz " " 
newline: .asciiz "\n"
dot: .asciiz "."
exp: .asciiz "^"
times: .ascii " x "
