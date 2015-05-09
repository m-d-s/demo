	.file	"out.s"

	.data

	.text
	.globl	Xmain
Xmain:
	pushq	%rbp
	movq	%rsp, %rbp
	pushq	$0
	movl	$0, %eax
	movl	%eax, -8(%rbp)
	jmp	l1
l0:
	movl	-8(%rbp), %edi
	call	Xprint
	movl	-8(%rbp), %eax
	movl	$1, %edi
	addl	%edi, %eax
	movl	%eax, -8(%rbp)
l1:
	movl	$11, %eax
	movl	-8(%rbp), %edi
	cmpl	%eax, %edi
	jl	l0
	movq	%rbp, %rsp
	popq	%rbp
	ret

