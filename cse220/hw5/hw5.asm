########################
# Homework #5
# name: Ivan Lin
# sbuid: 111020797
########################

.text

###CHEATING BY USING THE FACT THAT INPUT WILL BE VALID
	
match_glob:
	addi $sp $sp -20
	sw $a0 0($sp)
	sw $a1 4($sp)
	sw $ra 8($sp)
	sw $s0 12($sp)
	sw $s1 16($sp)
	
	#case pattern equals *, return (true, sequence length)
	jal strlen
	move $v1 $v0
	li $v0 1
	lb $t0 1($a0)
	bnez $t0 len_one
	lb $t0 0($a0)
	li $t1 '*'
	beq $t0 $t1 return_match_glob
	len_one:
	#case they are equalignorecase, return (true, 0)
	lw $a0 0($sp)
	jal strlen
	move $a2 $v0
	lw $a0 0($sp)
	lw $a1 4($sp)
	jal eqIgnoreCase #returns 1 if they are the same, 0 otherwise
	move $t0 $v0
	li $v0 1
	li $v1 0
	beq $t0 1 return_match_glob
	
	#if seq or pattern is empty and other isnt, return (false, 0)
	lw $a0 0($sp)#check seq
	jal strlen
	move $s0 $v0
	lw $a0 4($sp)#check pattern
	jal strlen
	move $s1 $v0
	beq $s0 $s1 skip_zero_testing
	li $v0 0
	li $v1 0
	beqz $s0 return_match_glob
	beqz $s1 return_match_glob
	skip_zero_testing:

	#recursive calls
	#if characters are equal
	lw $a0 0($sp)
	lw $a1 4($sp)
	li $a2 1
	jal eqIgnoreCase
		beqz $v0 match_fail
		lw $a0 0($sp)
		lw $a1 4($sp)
		addi $a0 $a0 1
		addi $a1 $a1 1
		jal match_glob
		j return_match_glob
	match_fail:
	#current char is a glob match
	lw $t0 4($sp)
	lb $t0 0($t0)
	bne $t0 '*' not_wild
		lw $a0 0($sp)
		lw $a1 4($sp)
		addi $a1 $a1 1
		jal match_glob
		beq $v0 1 return_match_glob#match found
		#no match found,
		lw $a0 0($sp)
		lw $a1 4($sp)
		addi $a0 $a0 1
		jal match_glob
		addi $v1 $v1 1
		j return_match_glob
	not_wild:
	li $v0 0
	li $v1 0
	return_match_glob:
	lw $ra 8($sp)	
	lw $s0 12($sp)
	lw $s1 16($sp)
	addi $sp $sp 20
	jr $ra
	
###############################

save_perm: #a0 dst, a1 sequence
	move $t0 $a0#write
	li $t1 0#read
	save_loop:
		add $t2 $a1 $t1
		lb $t3 0($t2)
		beqz $t3 end_save_loop 
		
		li $t4 2
		div $t1 $t4
		mfhi $t5 #remainder
		
		beqz $t1 dont_hyphenate
		bnez $t5 dont_hyphenate
			li $t6 '-'
			sb $t6 0($t0)
			addi $t0 $t0 1
		dont_hyphenate:
		
		sb $t3 0($t0)
		
		addi $t1 $t1 1
		addi $t0 $t0 1
	j save_loop
	end_save_loop:
	
	li $t3 '\n'
	sb $t3 0($t0)
	
	addi $t0 $t0 1

	move $v0 $t0
	#move $v0 $a0
	jr $ra

construct_candidates: #a0 space for candidates a1 sequence a2 n
	li $t0 2
	div $a2 $t0
	mfhi $t1
	beqz $t1 new_pair
		addi $a2 $a2 -1
		add $a1 $a1 $a2
		lb $t2 0($a1)
		case_A:
		bne $t2 'A' case_T
			li $t3 'T'
			j added_matching
		case_T:
		bne $t2 'T' case_C
			li $t3 'A'
			j added_matching
		case_C:
		bne $t2 'C' case_G
			li $t3 'G'
			j added_matching
		case_G:
			li $t3 'C'
			j added_matching
		added_matching:
		li $v0 1
		sb $t3 0($a0)
		j return_construct
	new_pair:
		li $v0 4
		li $t0 'A'
		sb $t0 0($a0)
		li $t0 'C'
		sb $t0 1($a0)
		li $t0 'G'
		sb $t0 2($a0)
		li $t0 'T'
		sb $t0 3($a0)
	return_construct:
	jr $ra
	
permutations:#a0 sequence, a1 n existing len, a2 - res, a3, desired length
	#argument saving and init
	addi $sp $sp -32
	sw $s0 0($sp)
	sw $s1 4($sp)
	sw $s2 8($sp)
	sw $s3 12($sp)
	sw $s4 16($sp)
	sw $s5 20($sp)
	sw $s6 24($sp)
	sw $ra 28($sp)
	#sw $fp 32($sp)
	
	move $s0 $a0
	move $s1 $a1
	move $s2 $a2
	move $s3 $a3
	#base case - desired len is 0 or odd
	li $v0 -1
	li $v1 0
	beqz $s3 return_permutations
	li $t0 2
	div $s3 $t0
	mfhi $t0	
	bnez $t0 return_permutations
	
	# n==len, already fulfilled
	addi $t0 $s3 0
	add $t0 $t0 $s0
	sb $0 0($t0)
	move $a0 $s2
	move $a1 $s0
	jal save_perm
	move $v1 $v0
	li $v0 0
	beq $s1 $s3 return_permutations
	
	#otherwise
	addi $sp $sp -4
	move $a0 $sp
	move $a1 $s0
	move $a2 $s1
	jal construct_candidates
	
	#iterate through chars construting perms
	move $s4 $v0#num candidates
	#move $a0 $s4
	#li $v0 1
	#syscall
	
	li $s5 0#i=0
	loop_candidates:
	bge $s5 $s4 loaded_all_perms
	
	add $t0 $s0 $s1 #seq[n]
	add $t1 $s5 $sp #cand[i]
	lb $t2 0($t1)
	sb $t2 0($t0)
	
	move $a0 $s0
	move $a1 $s1
	addi $a1 $a1 1
	move $a2 $s2
	move $a3 $s3	
	jal permutations
	
	move $s2 $v1
	
	addi $s5 $s5 1
	j loop_candidates
	loaded_all_perms:
	
	addi $sp $sp 4
	li $v0 0
	move $v1 $s2
	
	return_permutations:
	lw $s0 0($sp)
	lw $s1 4($sp)
	lw $s2 8($sp)
	lw $s3 12($sp)
	lw $s4 16($sp)
	lw $s5 20($sp)
	lw $s6 24($sp)
	lw $ra 28($sp)
	addi $sp $sp 32
	jr $ra


####  helpers
strlen:#a0 is string
	li $t0 0
	countNext:
		lb $t1 0($a0)
		beqz $t1 doneCounting
		addi $t0 $t0 1
		addi $a0 $a0 1
		j countNext
	doneCounting:
	move $v0 $t0
	jr $ra

eqIgnoreCase: #a0 string1 start, a1 string2 start, a2 bytes to read (also terminates on line ending
	li $v0 0
	li $t0 0
	li $t1 'a'
	li $t2 'A'
	sub $t3 $t1 $t2
	read_through_cases:	
		bge $t0 $a2 done_reading
		add $t1 $t0 $a0
		add $t2 $t0 $a1
		lb $t1 0($t1)
		lb $t2 0($t2)
		beq $t1 $t2 continue_read_case
		add $t4 $t1 $t3
		beq $t4 $t2 continue_read_case
		add $t5 $t2 $t3
		beq $t1 $t5 continue_read_case
		j ret_eqIgnoreCase
		continue_read_case:
		beqz $t1 done_reading
		beqz $t2 done_reading
		addi $t0 $t0 1
	j read_through_cases
	done_reading:
	li $v0 1
	ret_eqIgnoreCase:	
	jr $ra
	
.data
