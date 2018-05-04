##############################################################
# Homework #4
# name: Ivan Lin
# sbuid: 111020797
##############################################################

##############################################################
# DO NOT DECLARE A .DATA SECTION IN YOUR HW. IT IS NOT NEEDED
##############################################################

.text

##############################
# Part I FUNCTIONS
##############################

#(a0: slot[][] board, a1: int num_rows, a2: int num_cols, a3: int row, int col, char c, int turn_num)
set_slot:
    # Define your code here
    addi $sp $sp -12
    sw $s0 0($sp)
    sw $s1 4($sp)
    sw $s2 8($sp)
    
    lw $s0 12($sp)# col in s0
    lw $s1 16($sp)# char c in s1
    lw $s2 20($sp)#turn_num in s2
    
    #err checking
    li $v0 -1
    bltz $a1 return_set_slot#num rows < 0
    bltz $a2 return_set_slot#num cols < 0
    bltz $a3 return_set_slot#row < 0
    bge $a3 $a1 return_set_slot#row>=num_rows
    bltz $s0 return_set_slot#col < 0
    bge $s0 $a2 return_set_slot#col>=num_cols
    beq $s1 'R' set_slot_valid_char
    beq $s1 'Y' set_slot_valid_char
    beq $s1 '.' set_slot_valid_char
    j return_set_slot
    set_slot_valid_char:
    bltz $s2 return_set_slot #turn<0
    bgt $s2 255 return_set_slot #$#turn>255:
    
    #func
    li $v0 0
    mult $a2 $a3
    mflo $t1#t1=rows*num_cols
    add $t1 $t1 $s0#t1+=cols
    add $t1 $t1 $t1#t1*=2, t1 is displacement from 0,0
    add $a0 $a0 $t1#a0+=t1, a0 is now address of ind
    sb $s1 0($a0)#zeroth byte: char
    sb $s2 1($a0)#first byte: turn num
    
    return_set_slot:
    lw $s0 0($sp)
    lw $s1 4($sp)
    lw $s2 8($sp)
    addi $sp $sp 12
    jr $ra

get_slot:
    # Define your code here
    addi $sp $sp -4
    sw $s0 0($sp)
    lw $s0 4($sp)# col in s0
    
    #err checking
    li $v0 -1
    li $v1 -1
    bltz $a1 return_get_slot#num rows < 0
    bltz $a2 return_get_slot#num cols < 0
    bltz $a3 return_get_slot#row < 0
    bge $a3 $a1 return_get_slot#row>=num_rows
    bltz $s0 return_get_slot#col < 0
    bge $s0 $a2 return_get_slot#col>=num_cols
    
    #func
    mult $a2 $a3
    mflo $t1#t1=rows*num_cols
    add $t1 $t1 $s0#t1+=cols
    add $t1 $t1 $t1#t1*=2, t1 is displacement from 0,0
    add $a0 $a0 $t1#a0+=t1, a0 is now address of ind
    
    ##############BIIIIIIIIIIIIIIG CHANGE -> works for me but test flipped on hw results? why?
    lb $v1 0($a0)#zeroth byte: char
    lb $v0 1($a0)#first byte: turn num
    
    return_get_slot:
    lw $s0 0($sp)
    addi $sp $sp 4
    jr $ra

clear_board:
    # Define your code here
    addi $sp $sp -20
    sw $s0 0($sp)
    sw $s1 4($sp)
    sw $s2 8($sp)
    sw $s3 12($sp)
    sw $ra 16($sp)
    #err check
    li $v0 -1
    bltz $a1 end_clear
    bltz $a2 end_clear
    #func
    li $v0 0
   
   move $s0 $a0
   move $s1 $a1
   move $s2 $a2
   li $s3 0#row
   li $s4 0#col
     
    addi $sp $sp -12
    li $t0 '.'
    sw $t0 4($sp) #load char to '.'
    sw $0 8($sp)  #load turn to 0
    
    clear_loop: 
   	bge $s3 $s1 end_clear_loop#row >=numrows
    move $a0 $s0
    move $a1 $s1
    move $a2 $s2
    move $a3 $s3
    sw $s4 0($sp) #load col
 	jal set_slot
 	addi $s4 $s4 1#add 1 to col
 	blt $s4 $s2 not_next_row#col == numcols, next row, reset col
 		li $s4 0
 		addi $s3 $s3 1
 	not_next_row:
    j clear_loop
    end_clear_loop:
    addi $sp $sp 12
    end_clear:    
    lw $s0 0($sp)
    lw $s1 4($sp)
    lw $s2 8($sp)
    lw $s3 12($sp)
    lw $ra 16($sp)
    addi $sp $sp 20
    jr $ra

 
##############################
# Part II FUNCTIONS
##############################

load_board:
    # Define your code here
    addi $sp $sp -28
    sw $s0 0($sp)#board
    sw $s1 4($sp)#fd
    sw $s2 8($sp)#numrow
    sw $s3 12($sp)#numcol
    sw $s4 16($sp)#row
    sw $s5 20($sp)#col
    sw $ra 24($sp)    
    move $s0 $a0
    #open file
    move $a0 $a1
    li $a1 0
    li $v0 13  
    syscall
    move $s1 $v0 #s1 hold fd
    li $v0 -1
    li $v1 -1
    beqz $v0 return_load_board#file err
    addi $sp $sp -4
    li $t0 '0'
    initalize:#read line1
    	move $a0 $s1
    	addi $a1 $sp 0
    	li $a2 5
    	li $v0 14
    	syscall
    	li $t2 10
    	#rr
    	lb $t1 0($sp)
    	sub $t3 $t1 $t0
    	mul $t3 $t3 $t2
    	lb $t1 1($sp)
    	sub $t1 $t1 $t0
    	add $t3 $t3 $t1
    	move $s2 $t3
    	#cc
    	lb $t1 2($sp)
    	sub $t4 $t1 $t0
    	mul $t4 $t4 $t2
    	lb $t1 3($sp)
    	sub $t1 $t1 $t0
    	add $t4 $t4 $t1
    	move $s3 $t4
    	addi $sp $sp 4
    	li $v0 -1
    	li $v1 -1
    	 beqz $s2 return_load_board#numrows=0
    	 beqz $s3 return_load_board#numcols=0
    	 addi $sp $sp -4
    addi $sp $sp -12
     #t0 hold '0', t2 holds 10
    read_loop:
    	move $a0 $s1
    	addi $a1 $sp 12#set to pointer after the 12 set aside for parameter
    	li $a2 9#rrccPttt
    	li $v0 14
    	syscall
    beqz $v0 end_read_loop_no_err #no input to read
    	#rr
    	lb $t1 12($sp)
    	sub $t3 $t1 $t0
    	mul $t3 $t3 $t2
    	lb $t1 13($sp)
    	sub $t1 $t1 $t0
    	add $t3 $t3 $t1
    	#cc
    	lb $t1 14($sp)
    	sub $t4 $t1 $t0
    	mul $t4 $t4 $t2
    	lb $t1 15($sp)
    	sub $t1 $t1 $t0
    	add $t4 $t4 $t1
    	move $s4 $t3#move row, col to s regs
    	move $s5 $t4
    	li $v0 -1
	li $v1 -1
    	bltz $s4 end_read_loop
    	bge $s4 $s2 end_read_loop
    	bltz $s5 end_read_loop
    	bge $s5 $s3 end_read_loop
    	#C
    	lb $t5 16($sp)#load byte
    	#ttt
    	lb $t1 17($sp)
    	sub $t6 $t1 $t0
    	mul $t6 $t6 $t2
    	lb $t1 18($sp)
    	sub $t1 $t1 $t0
    	mul $t6 $t6 $t2
    	add $t6 $t6 $t1
    	lb $t1 19($sp)
    	sub $t1 $t1 $t0
    	add $t6 $t6 $t1
    	#t6 holds turn
    	blez $t6 end_read_loop
    	bgt $t6 255 end_read_loop
    	#
      	move $a0 $s0
    	move $a1 $s2
    	move $a2 $s3
    	move $a3 $s4
    	sw $s5 0($sp)
    	sw $t5 4($sp)
    	sw $t6 8($sp)
    	jal set_slot
    j read_loop
    end_read_loop_no_err:
    move $v0 $s2 #return vals = row, cols
    move $v1 $s3
    end_read_loop:
    addi $sp $sp 12
    addi $sp $sp 4
    return_load_board:
    move $s0 $v0
    move $a0 $s1
    li $v0 16
    syscall
    move $v0 $s0
    #close fd
    lw $s0 0($sp)
    lw $s1 4($sp)
    lw $s2 8($sp)
    lw $s3 12($sp)
    lw $s4 16($sp)#row
    lw $s5 20($sp)#col
    lw $ra 24($sp)
    addi $sp $sp 28
    jr $ra

save_board:
# Define your code here
	addi $sp $sp -36
	sw $s0 0($sp)
	sw $s1 4($sp)
	sw $s2 8($sp)
	sw $s3 12($sp)
	sw $s4 16($sp)
	sw $s5 20($sp)
	sw $ra 24($sp)
	sw $s6 28($sp)
	sw $s7 32($sp)
#func
    move $s0 $a0
    move $s1 $a1
    move $s2 $a2
    li $s3 0
    li $s4 0
    move $a0 $a3
    li $a1 1
    li $v0 13  
    syscall
    move $s5 $v0#save fd
    #err checking
    beq $v0 -1 return_save_board
    li $v0 -1
    bltz $s1 return_save_board
    bltz $s2 return_save_board
    
    li $t0 10
    addi $sp $sp -4
    
    div $s1 $t0#row
    mflo $t1#quotient
    mfhi $t2#remainder
    addi $t1 $t1 '0'
    sb $t1 0($sp)
    addi $t2 $t2 '0'
    sb $t2 1($sp)
    
    div $s2 $t0#col
    mflo $t1#quotient
    mfhi $t2#remainder
    addi $t1 $t1 '0'
    sb $t1 2($sp)
    addi $t2 $t2 '0'
    sb $t2 3($sp)
    li $t3 '\n'
    sb $t3 4($sp)
    
    move $a0 $s5#fd to write
    move $a1 $sp#output buffer
    li $a2 5#write 5 bytes
    li $v0 15
    syscall
    
    addi $sp $sp 4
    
    #t0 holds 10 for div purposes
    addi $sp $sp -4
    li $s6 0
    li $s7 0
    write_loop:
    move $t1 $s2
    addi $t1 $t1 -1
    	bge $s3 $t1 valid_write_loop#row>=numrows 
        	
    	move $a0 $s0
    	move $a1 $s1
    	move $a2 $s2
    	move $a3 $s3
    	sw $s4 0($sp)
    	jal get_slot
	 
    beq $v0 '.' empty_slot
    	#write
     addi $sp $sp -4
    addi $s7 $s7 1
     div $s3 $t0#row
     mflo $t1#quotient
     mfhi $t2#remainder
     addi $t1 $t1 '0'
     addi $t2 $t2 '0'
     sb $t1 0($sp)
     sb $t2 1($sp)
     div $s4 $t0#col
     mflo $t1#quotient
     mfhi $t2#remainder
     addi $t1 $t1 '0'
     addi $t2 $t2 '0'
     sb $t1 2($sp)
     sb $t2 3($sp)
     sb $v0 4($sp)
     
     div $v1 $t0#turn
     mflo $t2#quotient
     mfhi $t3#remainder
     div $t2 $t0#divide by 10 again
     mflo $t1 #quotient
     mfhi $t2 #remainder
     
     addi $t1 $t1 '0'
     sb $t1 5($sp) #hund place
     addi $t2 $t2 '0'
     sb $t2 6($sp) #ten
     addi $t3 $t3 '0'
     sb $t3 7($sp) #one
     li $t4 '\n'
     sb $t4 8($sp) #newline
     
     move $a0 $s5#fd to write
     move $a1 $sp#output b iffer
     li $a2 5#write 5 bytes
     
     move $a0 $s5
     move $a1 $sp
     li $a2 9
     li $v0 15
     syscall
    
     beq $v0 -1 end_write_loop
    	addi $s6 $s6 1
    addi $sp $sp 4
    empty_slot:
    
    addi $s4 $s4 1
    blt $s4 $s2 end_of_row#col<numcols
    	li $s4 0
    	addi $s3 $s3 1
    end_of_row:
    
    j write_loop
    valid_write_loop:
    move $v0 $s6
    end_write_loop:
    addi $sp $sp 4
    return_save_board:
    move $a0 $s5
    li $v0 16
    syscall
    move $v0 $s7
	lw $s0 0($sp)
	lw $s1 4($sp)
	lw $s2 8($sp)
	lw $s3 12($sp)
	lw $s4 16($sp)
	lw $s5 20($sp)
	lw $ra 24($sp)
	lw $s6 28($sp)
	lw $s7 32($sp)
	addi $sp $sp 36
    jr $ra

validate_board:
	addi $sp $sp -36
    # Define your code here
    sw $s0 0($sp)
    sw $s1 4($sp)
    sw $s2 8($sp)
    sw $s3 12($sp)
    sw $s4 16($sp)
    sw $s5 20($sp)
    sw $s6 24($sp)
    sw $s7 28($sp)
    sw $ra 32($sp)
    li $s0 0#final outpiut
    move $s1 $a0
    move $s2 $a1
    move $s3 $a2
    bit_0:#numrows<4
    bge $s2 4 bit_1
    	ori $s0 $s0 1
    bit_1:#numcols<4
    bge $s3 4 bit_2
    	ori $s0 $s0 2
    bit_2:#numrows*numcols>255
    mul $t0 $s2 $s3
    ble $t0 255 bit_3_4
    	ori $s0 $s0 4
    bit_3_4:
    #bit 3: difference of -1<=r-y<=1
    #bit 4: do not alternate turns --> if we implement this check, it imples bit_3 check
    #bit_5: no empty slots below pieces
    #bit_6: no piece with lower turns below
    #bit_7L duplicate turns, turns do not begin at 1
    #s6 will hold the previous turn number(for travelling up columns)
    #s7 will hold the previous character
    li $s4 0
    li $s5 0
    addi $sp $sp -256
    #zero out stack mem
    li $t0 0
    zero_stack:
    	bge $t0 256 zeroed_out
    	add $t1 $t0 $sp
    	sw $0 0($t1)
    	addi $t0 $t0 4
    j zero_stack
    zeroed_out:
    validate_col:
    	validate_row:
    		bge $s5 $s3 done_validating 
    		move $a0 $s1
    		move $a1 $s2
    		move $a2 $s3
    		move $a3 $s4
    		addi $sp $sp -4
    		sw $s5 0($sp)
    		jal get_slot
    		addi $sp $sp 4
    		#check previous
    		beq $v0 '.' not_filled
    			bne $s7 '.' above_filled
    				ori $s0 $s0 32#fifth bite =1
    				bgt $v1 $s6 invalid_tower
    					ori $s0 $s0 64#bit 6
    		invalid_tower:
    		not_filled:
    		above_filled:
    		move $s7 $v0
    		move $s6 $v1
    		#save to stack
    		#calculate index:
    		move $t0 $v1
    		
    		#find the next multiple of 4 (since we can only store bytes)
    		move $t1 $t0#beginning of word
    		li $t2 4
		check_mult_4:
	    		div $t1 $t2
			mfhi $t3#remainder
			beq $t3 0 is_mult_4
			addi $t1 $t1 -1
		j check_mult_4
		is_mult_4:
		add $t3 $t0 $sp

		lb $t4 0($t3)
		
		beq $t4 0 not_duplicates
		beq $t4 '.' not_duplicates
			ori $s0 $s0 128#bit 7
		not_duplicates:
		
		sub $t2 $t1 $t0
		li $t3 4
		sub $t2 $t3 $t2#subtract distance from 4 (lsb on right0
		li $t3 8
		mul $t2 $t2 $t3
    		sllv $v0 $v0 $t2#shift bytes by distance from word beginning
    		add $t1 $t1 $sp
    		lw $t4 0($t1)
    		or $t5 $v0 $t4#combines everything
    		
    		
    		sw $t5 0($t1)
    		
    		addi $s4 $s4 1
    		blt $s4 $s2 validate_row
    			addi $s5 $s5 1
    			li $s4 0#row
    			li $s6 0#turn
    			li $s7 0#char
    		j validate_col
    	validated_col:
    done_validating:
    
    move $t0 $sp 
    #j printed_out
    li $t1 0#holds previous character
    li $t3 0#numer of 'R'
    li $t4 0#Number of 'Y' 
    addi $t5 $sp 256
    print_stack:
    	bge $t0 $t5 printed_out
    	
    	lb $t2 0($t0)
    	
		
	beq $t2 0 skip_similarity_check
	beq $t2 '.' skip_similarity_check
			
		lb $t6 1($sp)
		bne $t6 0 starts_at_turn_1
			ori $s0 $s0 128#byte 7, checks if starts at turn 1
		starts_at_turn_1:	
	
		beq $t2 'Y' isY
		addi $t3 $t3 1
		j tally
		isY:
		addi $t4 $t4 1
		tally:
		
		bne $t2 $t1 alternates
			ori $s0 $s0 16#bite 4
		alternates:
		
		
       		move $t1 $t2#updates previous
	skip_similarity_check:
       	addi $t0 $t0 1
    j print_stack
    printed_out:
    sub $t5 $t3 $t4
    blt $t5 -1 same_turns
    bgt $t5 1 same_turns
    j not_same
    same_turns:
    	ori $s0 $s0 8 #byte 3
    	not_same:
    move $v0 $s0
    addi $sp $sp 256
    lw $s0 0($sp)
    lw $s1 4($sp)
    lw $s2 8($sp)
    lw $s3 12($sp)
    lw $s4 16($sp)
    lw $s5 20($sp)
    lw $s6 24($sp)
    lw $s7 28($sp)
    lw $ra 32($sp)
    addi $sp $sp 36
    jr $ra

##############################
# Part III FUNCTIONS
##############################

display_board:
    # Define your code here
    addi $sp $sp -28
    sw $s0 0($sp)
    sw $s1 4($sp)
    sw $s2 8($sp)
    sw $s3 12($sp)
    sw $s4 16($sp)
    sw $ra 20($sp)
    sw $s5 24($sp)
    #err check
    li $v0 -1
    bltz $a1 end_draw
    bltz $a2 end_draw
    #func
    li $v0 0
   move $s0 $a0
   move $s1 $a1
   move $s2 $a2
   li $s3 0#row
   li $s4 0#col
   li $s5 0#piece counter
    addi $sp $sp -4
    draw_loop: 
   	bge $s3 $s1 end_draw_loop#row >=numrows
    move $a0 $s0
    move $a1 $s1
    move $a2 $s2
    move $a3 $s3#row
    sub $a3 $s1 $s3#num_row-row
    addi $a3 $a3 -1#-1
    sw $s4 0($sp) #load col
 	jal get_slot
 	bne $v0 'R' invalid_display_Y
 		addi $s5 $s5 1
 	invalid_display_Y:
 	bne $v0 'Y' invalid_display
 		addi $s5 $s5 1
 	invalid_display:
 	addi $s4 $s4 1#add 1 to col
 	move $a0 $v0 
 	li $v0 11
 	syscall
 	blt $s4 $s2 not_next_row_draw#col == numcols, next row, reset col
 		li $s4 0
 		addi $s3 $s3 1
 		li $a0 '\n'
 		li $v0 11
 		syscall
 	not_next_row_draw:
    j draw_loop
    end_draw_loop:
    move $v0 $s5
    addi $sp $sp 4
    end_draw:    
    lw $s0 0($sp)
    lw $s1 4($sp)
    lw $s2 8($sp)
    lw $s3 12($sp)
    lw $s4 16($sp)
    lw $ra 20($sp)
    lw $s5 24($sp)
    addi $sp $sp 28
    jr $ra

drop_piece:
    # Define your code here
    addi $sp $sp -32
    sw $s0 0($sp)
    sw $s1 4($sp)
    sw $s2 8($sp)
    sw $s3 12($sp)
    sw $s4 16($sp)
    sw $s5 20($sp)
    sw $s6 24($sp)
    sw $ra 28($sp)
    
    move $s0 $a0
    move $s1 $a1
    move $s2 $a2
    li $s3 0
    move $s4 $a3
    lw $s5 32($sp)
    lw $s6 36($sp)
    
    bltz $s1 fail_drop
    bltz $s2 fail_drop
    bltz $s4 fail_drop
    bge $s4 $s2 fail_drop
    bgt $s6 255 fail_drop
    beq $s5 'R' valid_piece_char
    beq $s5 'Y' valid_piece_char
    	j fail_drop
    valid_piece_char:
    
    addi $sp $sp -12
    drop_loop:
    	move $a0 $s0
    	move $a1 $s1
    	move $a2 $s2
    	move $a3 $s3
    	sw $s4 0($sp)
    	jal get_slot
    	beq $v0 '.' bottom#valid spot
    	bge $s3 $s1 full_drop#full
    	addi $s3 $s3 1	
    j drop_loop
    bottom:
 	move $a0 $s0
    	move $a1 $s1
    	move $a2 $s2
    	move $a3 $s3
    	sw $s4 0($sp)
    	sw $s5 4($sp)
    	sw $s6 8($sp)
    	jal set_slot
    	addi $sp $sp 12
    	
    	j return_drop_piece
    	full_drop:
    	addi $sp $sp 12
    fail_drop:
    	li $v0 -1
    return_drop_piece:
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

undo_piece:
    # Define your code here
    addi $sp $sp -36
    sw $s0 0($sp)
    sw $s1 4($sp)
    sw $s2 8($sp)
    sw $s3 12($sp)
    sw $s4 16($sp)
    sw $s5 20($sp)
    sw $s6 24($sp)
    sw $s7 28($sp)
    sw $ra 32($sp)
    #err check
    li $v0 '.'
    li $v1 -1
    bltz $a1 end_find
    bltz $a2 end_find
    #func
   
   move $s0 $a0
   move $s1 $a1
   move $s2 $a2
   li $s3 0#row
   li $s4 0#col
   
   addi $sp $sp -12
   li $s5 0
   find_latest_loop: 
   bge $s3 $s1 undone#row >=numrows
    move $a0 $s0
    move $a1 $s1
    move $a2 $s2
    move $a3 $s3
    sw $s4 0($sp) #col
    
    jal get_slot 
    	
    	ble $v1 $s5 greatest #s5 holds the most recent turn
 	 move $s6 $s3 #s6 row of recent
 	 move $s7 $s4 #s7 col of recent
 	 move $s5 $v1 #sets new k of
 	greatest:
 	
 	addi $s4 $s4 1#add 1 to col
 	blt $s4 $s2 row_end#col == numcols, next row, reset col
 		li $s4 0
 		addi $s3 $s3 1
 	row_end:
    j find_latest_loop
    undone:
    bnez $s5 valid_removal
    li $v0 '.' #no piece was found with a greater turn than 0
    li $v1 -1
    j finished_resetting
    valid_removal:
    move $a0 $s0
    move $a1 $s1
    move $a2 $s2
    move $s3 $s6
    move $a3 $s3
    move $s4 $s7
    sw $s4 0($sp)
    jal get_slot
    move $s6 $v0#temp store old vales
    move $s7 $v1#temp store old vales
    
    move $a0 $s0
    move $a1 $s1
    move $a2 $s2
    move $a3 $s3
    sw $s4 0($sp)
    li $t0 '.'
    sw $t0 4($sp)
    li $t1 0
    sw $t1 8($sp)
    jal set_slot
    move $v0 $s6
    move $v1 $s7
    finished_resetting:
    addi $sp $sp 12
    end_find:    
    lw $s0 0($sp)
    lw $s1 4($sp)
    lw $s2 8($sp)
    lw $s3 12($sp)
    lw $s4 16($sp)
    lw $s5 20($sp)
    lw $s6 24($sp)
    lw $s7 28($sp)
    lw $ra 32($sp)
    addi $sp $sp 36
    jr $ra

check_winner:
    # Define your code here
    addi $sp $sp -32
    sw $s0 0($sp)
    sw $s1 4($sp)
    sw $s2 8($sp)
    sw $s3 12($sp)
    sw $s4 16($sp)
    sw $s5 20($sp)#current row/col (used during check)
    sw $s6 24($sp)#stores previous piece (used during check)
    sw $ra 28($sp)
    move $s0 $a0
    move $s1 $a1
    move $s2 $a2
    li $s3 0
    li $s4 0
    addi $sp $sp -4
        start_row:
    	li $s6 '.'
    	bge $s3 $s1 declare_winner
    	start_col:
    		bge $s4 $s2 end_col
    		move $s5 $s4#load s5 with current col
    		check_horizontal:
    			sub $t0 $s5 $s4#dist from beginning of check
    			bge $t0 4 declare_winner
    			bge $s5 $s2 fail_check_horizontal#end of row
    			move $a0 $s0
    			move $a1 $s1
    			move $a2 $s2
    			move $a3 $s3
    			sw $s5 0($sp)
    			jal get_slot 
    			beq $v0 '.' fail_check_horizontal
    			beq $s4 $s5 is_first_piece_horizontal#only check if equals previous after the first piece
    				bne $v0 $s6 fail_check_horizontal
    			is_first_piece_horizontal:
    			addi $s5 $s5 1
    			move $s6 $v0
    			j check_horizontal
    		fail_check_horizontal:
    		move $s5 $s3#load s5 with current row
    		check_vertical:
    			sub $t0 $s5 $s3#dist from beginning of check
    			bge $t0 4 declare_winner
    			bge $s5 $s1 fail_check_vertical#end of col
    			move $a0 $s0
    			move $a1 $s1
    			move $a2 $s2
    			move $a3 $s5
    			sw $s4 0($sp)
    			jal get_slot 
    			beq $v0 '.' fail_check_vertical
    			beq $s3 $s5 is_first_piece_vertical#only check if equals previous after the first piece
    				bne $v0 $s6 fail_check_vertical
    			is_first_piece_vertical:
    			addi $s5 $s5 1
    			move $s6 $v0
    			j check_vertical
    		fail_check_vertical:
    		#update begin col
    		li $s6 '.'
    		addi $s4 $s4 1
    		j start_col
    	end_col:
    	#update begin row, reset begin col
    	li $s4 0
    	addi $s3 $s3 1
    	j start_row
    declare_winner:
        addi $sp $sp 4
    	move $v0 $s6
    
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

##############################
# EXTRA CREDIT FUNCTION
##############################	

check_diagonal_winner:
        # Define your code here
    addi $sp $sp -32
    sw $s0 0($sp)
    sw $s1 4($sp)
    sw $s2 8($sp)
    sw $s3 12($sp)
    sw $s4 16($sp)
    sw $s5 20($sp)#current displacement (used during check)
    sw $s6 24($sp)#stores previous piece (used during check)
    sw $ra 28($sp)
    move $s0 $a0
    move $s1 $a1
    move $s2 $a2
    li $s3 0
    li $s4 0
    addi $sp $sp -4
        start_row_diagonal:
    	li $s6 '.'
    	bge $s3 $s1 declare_winner_diagonal
    	start_col_diagonal:
    		bge $s4 $s2 end_col_diagonal
    		li $s5 0#load s5 with 0
    		check_upward_diagonal:
    			bge $s5 4 declare_winner_diagonal#success    	
    			add $t1 $s3 $s5		
    			add $t2 $s4 $s5		
    			bge $t1 $s1 fail_check_upward_diagonal#end of row
    			bge $t2 $s2 fail_check_upward_diagonal#end of col
    			move $a0 $s0
    			move $a1 $s1
    			move $a2 $s2
    			add $a3 $s3 $s5
    			add $t3 $s4 $s5
    			sw $t3 0($sp)
    			jal get_slot 
    			beq $v0 '.' fail_check_upward_diagonal
    			beq $s5 $0 is_first_piece_diagonal_u#only check if equals previous after the first piece
    				bne $v0 $s6 fail_check_upward_diagonal
    			is_first_piece_diagonal_u:
    			addi $s5 $s5 1
    			move $s6 $v0
    			j check_upward_diagonal
    		fail_check_upward_diagonal:
    				
    		li $s5 0#load s5 with 0
    		check_downward_diagonal:
    			bge $s5 4 declare_winner_diagonal#success    	
    			add $t1 $s3 $s5		
    			sub $t2 $s4 $s5		
    			bge $t1 $s1 fail_check_downward_diagonal#end of row
    			blt $t2 $0 fail_check_downward_diagonal#end of col
    			move $a0 $s0
    			move $a1 $s1
    			move $a2 $s2
    			add $a3 $s3 $s5
    			sub $t3 $s4 $s5
    			sw $t3 0($sp)
    			jal get_slot 
    			beq $v0 '.' fail_check_downward_diagonal
    			beq $s5 $0 is_first_piece_diagonal_d#only check if equals previous after the first piece
    				bne $v0 $s6 fail_check_downward_diagonal
    			is_first_piece_diagonal_d:
    			addi $s5 $s5 1
    			move $s6 $v0
    			j check_downward_diagonal
    		fail_check_downward_diagonal:
    		
    		#update begin col
    		li $s6 '.'
    		addi $s4 $s4 1
    		j start_col_diagonal
    	end_col_diagonal:
    	#update begin row, reset begin col
    	li $s4 0
    	addi $s3 $s3 1
    	j start_row_diagonal
    declare_winner_diagonal:
        addi $sp $sp 4
    	move $v0 $s6
    
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



##############################################################
# DO NOT DECLARE A .DATA SECTION IN YOUR HW. IT IS NOT NEEDED
##############################################################
