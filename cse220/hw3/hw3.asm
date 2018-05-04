##############################################################
# Homework #3
# name: Ivan_Lin
# sbuid: 111020797
##############################################################
.text

##############################
# FUNCTIONS
##############################

indexOf:
    # Define your code here
    add $a0 $a0 $a2 #increment to start index
    move $v0 $a2
    nextChar1:
    	lb $t0 ($a0)#the next character
    	beq $t0 $0 hasNoIndex
    	beq $t0 $a1 hasIndex
    		addi $v0 $v0 1
    		addi $a0 $a0 1
    j nextChar1
    hasNoIndex:
    	li $v0 -1
    hasIndex:
    jr $ra

replaceAllChar:
    # Define your code here
    move $v0 $a0
    li $v1 -1
    lb $t0 0($a0)
    beq $t0 $0 endReplace
    lb $t0 0($a1)
    beq $t0 $0 endReplace
    li $v1 0
    nextCharReplace:
    	lb $t0 ($a0)#the next character
    	beq $t0 $0 endReplace
    	move $t1 $a1 #i=0;i<pattern.len;i++
    	checkPattern:
    		lb $t2 ($t1)
    		beq $t2 $0 doneCheckingPattern#next char 
    			beq $t0 $t2 inPattern#if equal to string char
    			addi $t1 $t1 1
    		j checkPattern
    		inPattern:#sets the pattern
    			sb $a2 ($a0)
    			addi $v1 $v1 1
    			j doneCheckingPattern
	doneCheckingPattern:
		addi $a0 $a0 1
	j nextCharReplace
    endReplace:
    jr $ra

countOccurrences:
    # Define your code here
    li $v0 0
    nextCharCount:
    	lb $t0 ($a0)#the next character
    	beq $t0 $0 endCount
    	move $t1 $a1 #i=0;i<pattern.len;i++
    	checkPatternCount:
    		lb $t2 ($t1)
    		beq $t2 $0 doneCheckingPatternCount#next char 
    			beq $t0 $t2 inPatternCount#if equal to string char
    			addi $t1 $t1 1
    		j checkPatternCount
    		inPatternCount:#sets the pattern
    			addi $v0 $v0 1
    			j doneCheckingPatternCount
	doneCheckingPatternCount:
		addi $a0 $a0 1
	j nextCharCount
    endCount:
    jr $ra

replaceAllSubstr:
	addi $sp $sp -20
	sw $ra 0($sp)
	sw $s0 4($sp)
	sw $s1 8($sp)
	sw $s2 12($sp)
	sw $s3 16($sp)
	
	move $s0 $a0 # dst 
	move $s1 $a1 # dstLen
	lw $s2 20($sp)	#get replaceStr from stack
	move $s3 $a0 #pointer to beginning of dst (for return)
	
	#move $s4 $a1
	
	isLessThanDstLen:
	move $a0 $a2
	move $a1 $a3
	jal countOccurrences
	#v0 holds the number of occurences
	
	move $t0 $s2
	li $t1 0
	countReplaceStrLen:
		lb $t2 ($t0)
		beqz $t2 doneCountingStrLen
		addi $t0 $t0 1	
		addi $t1 $t1 1
	j countReplaceStrLen
	doneCountingStrLen:
	move $t3 $t1#t3 holds the length of replacement string
	
	move $t0 $a2
	li $t1 0
	countOldStrLen:
		lb $t2 ($t0)
		beqz $t2 doneCountingOldStrLen
		addi $t0 $t0 1	
		addi $t1 $t1 1
	j countOldStrLen
	doneCountingOldStrLen:
	move $t4 $t1#t4 holds the length of old string
	
	mult $v0 $t3
	mflo $t0
	sub $t4 $t4 $v0
	add $t4 $t4 $t0 #t4 holds the modified string length
	
	#check if new string is too big or if the first byte is null
	lb $t5 ($a3)
	
	beqz $t5 repSubstrErr#check if searchchars is empty
	blez $t4 repSubstrErr#check if string is empty
	bge $t4 $s1 repSubstrErr#check if modified string is too large
	j validReplacements
	repSubstrErr:
		li $v1 -1
	j replaceAllExit
	
	validReplacements:
	bgt $t4 $s1 endReplaceAll
		#s0:beginning of dst, #s3:beginning of dst - don't modify
		#s2 the replacement string
	li $v1 0
	buildNewString:
	    	lb $t0 ($a2)#the next character
	    	beq $t0 $0 endReplaceAll
    		move $t1 $a3 #set t1 to beginning of the pattern string #i=0;i<pattern.len;i++
    		checkPatternDst:#
    			lb $t2 ($t1)
    			beq $t2 $0 notInPattern#next char 
    			beq $t0 $t2 inPatternForDst#if equal to string char
    			addi $t1 $t1 1
    		j checkPatternDst
    		inPatternForDst:#adds replacement string to dst
    			addi $v1 $v1 1
    			move $t3 $s2#set t3 to address of first char of replacement string 
    			addReplacementStr:
    				lb $t4 ($t3)
    				beqz $t4 doneCheckingPatternDst
    				sb $t4 ($s0) 
    				addi $t3 $t3 1#replacement
    				addi $s0 $s0 1#new string
    			j addReplacementStr
    		notInPattern:# adds usual char to dst		
    			sb $t0 ($s0)
    			addi $s0 $s0 1
		doneCheckingPatternDst:
			addi $a2 $a2 1#a2 is the index string
	j buildNewString

	endReplaceAll:
	sb $0 ($s0)
	
	replaceAllExit:
	
	move $v0 $s3
	lw $ra 0($sp)
	lw $s0 4($sp)
	lw $s1 8($sp)
	lw $s2 12($sp)
	lw $s3 16($sp)
	addi $sp $sp 20
    jr $ra

split:
    # Define your code here
    addi $sp $sp -20
    sw $ra 0($sp)
    sw $s0 4($sp)
    sw $s1 8($sp)
    sw $s2 12($sp)
    sw $s3 16($sp)
    
    move $s0 $a0#dst
    move $s1 $a1#dstlen
    move $s2 $a2#string
    li $s3 1#tokencount   
    
    #error checking
    
    
    #s4 holds the partition for the beginning of the remaining str
    #$a0 holds string address, a1 the character, a2 the startindex
    sw $s2 ($a0)#first token
    li $a2 0#first sets search at beginning of string
    delimiterSearch:
    	move $a0 $s2
    	move $a1 $a3#a3 is the char
    	jal indexOf #returns (index)
    	
    	li $t0 -1
    	beq $v0 $t0 noMoreDelimiters#if -1 no more
	blez $s1 noMoreSpace
	
    	add $t1 $s2 $v0#$v1#address of delimiter
    	
    	sb $0 ($t1)#insert nulls
    		    	
    	addi $s0 $s0 4#new token place
    	addi $t1 $t1 1#change address to beginning of token
    	#move $s0 $t1
    	sw $t1 ($s0)#add to dst
    	move $a2 $v0#updates start index
    	addi $a2 $a2 1#to token
    	addi $s3 $s3 1
    	addi $s1 $s1 -1
    	j delimiterSearch
    noMoreSpace:
    	li $v1 -1
    noMoreDelimiters:
    move $v0 $s3
    lw $ra 0($sp)
    lw $s0 4($sp)
    lw $s1 8($sp)
    lw $s2 12($sp)
    lw $s3 16($sp)
    addi $sp $sp 20
    jr $ra
