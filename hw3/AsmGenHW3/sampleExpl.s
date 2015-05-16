	.file	"out.s"

	.data

	.text
	.globl	Xmain
Xmain:
	pushq	%rbp               # prologue
	movq	%rsp, %rbp
	movl	$1, %eax           # 1st Local variable = 1
	pushq	%rax
	movl	$2, %eax           # 2nd Local variable = 2
	pushq	%rax
	movl	$2, %eax           # 3rd Local variable = 2
	pushq	%rax
	jmp	l1
l0:
	movl	$0, %eax           # 4th Local variable = 0
	pushq	%rax
	jmp	l3
l2:
	movl	-16(%rbp), %edi   	
    movl	-32(%rbp), %esi
	call	Xf                 # 2nd = f(2nd, 4th)
	movl	%eax, -16(%rbp)
	movl	-32(%rbp), %edi    # return value of nested function calls h(g(4th))
	call	Xg                 # |
	movq	%rax, %rdi         # |
	call	Xh                 # V
	movq	%rax, %rdi         # becomes arg for print
	call	Xprint
	movl	$1, %eax
	movl	-32(%rbp), %edi    
	addl	%edi, %eax         # Increment 4th by one
	movl	%eax, -32(%rbp)    # Store sum back in 4th 
l3:
	movl	-8(%rbp), %eax
	movl	-32(%rbp), %edi
	cmpl	%eax, %edi         # While conditional 4th < 1st
	jl	l2                     
	movl	-16(%rbp), %edi    # 2nd local var becomes arg for print
	call	Xprint
	movl	-16(%rbp), %eax
	movl	-24(%rbp), %edi
	imull	%edi, %eax         # Multiply 3rd and 2nd
	movl	%eax, -16(%rbp)    # Store product back in 2nd
	movl	-8(%rbp), %eax     
	movl	$1, %edi
	addl	%edi, %eax         # Increment 1st by one
	movl	%eax, -8(%rbp)     # Store sum back in 1st
	addq	$8, %rsp           # 4th local no longer in scope
l1:
	movl	$10, %eax
	movl	-8(%rbp), %edi
	cmpl	%eax, %edi         # While conditional 1st < $10
	jl	l0                       
	movl	-8(%rbp), %edi     # 1st local becomes arg for print     
	call	Xprint     
	movq	%rbp, %rsp         # epilogue
	popq	%rbp
	ret

	.globl	Xf                 # Function
Xf:
	pushq	%rbp               # Prologue
	movq	%rsp, %rbp
	movl	%edi, %eax
	movl	%esi, %ecx
	subl	%ecx, %eax
	pushq	%rax
	movl	-8(%rbp), %eax     # localF initialized to difference of first and second arg
	pushq	%rax
	pushq	%rsi
	pushq	%rdi
	movl	-8(%rbp), %edi     # return of g(localF)
	call	Xg                 # |
	movq	%rax, %rcx         # |
	popq	%rdi               # |
	popq	%rsi               # |
	popq	%rax               # V
	imull	%ecx, %eax         # Multiplied with localF
	movl	%eax, -8(%rbp)     # Stored back in localF
	movl	-8(%rbp), %eax     # Return localF
	movq	%rbp, %rsp
	popq	%rbp               # Epilogue
	ret

	.globl	Xg                 # Function
Xg:
	pushq	%rbp               # Prologue
	movq	%rsp, %rbp      
	movl	$2, %eax           # Load constant into register ***** Dont need to do this! *****
	movl	%edi, %esi
	imull	%esi, %eax         # Multiply argument by two
	movl	%edi, %esi
	subl	%esi, %eax         # subract product by value of argument
	movq	%rbp, %rsp
	popq	%rbp               # Epilogue
	ret

	.globl	Xh                 # Function
Xh:
	pushq	%rbp               # Prologue
	movq	%rsp, %rbp         
	movl	$2, %eax           # Load constant into register ***** Dont need to do this! *****
	movl	%edi, %esi 
	cmpl	%eax, %esi         # if arg > 2 jump
	jnl	l4                    
	movl	$1, %eax           # otw, return 1
	movq	%rbp, %rsp
	popq	%rbp               # Epilogue
	ret
l4:
	pushq	%rdi
	movl	-8(%rbp), %edi     # h(arg - 1)
	movl	$1, %esi           # |
	subl	%esi, %edi         # |
	call	Xh                 # | Recursion
	popq	%rdi               # |
	pushq	%rax               # |
	pushq	%rdi               # V
	movl	-16(%rbp), %edi    # h(arg - 2)
	movl	$2, %esi           # |
	subl	%esi, %edi         # |
	call	Xh                 # | Recursion
	movq	%rax, %rsi         # |
	popq	%rdi               # |
	popq	%rax               # V
	addl	%esi, %eax         # Return the sum of the return values of the recursive calls
	movq	%rbp, %rsp
	popq	%rbp               # Epilogue
	ret

