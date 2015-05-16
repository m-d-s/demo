	.file	"out.s"

	.data
Xi:
	.long	0
Xa:
	.long	0
Xc:
	.long	0

	.text

	.globl	XinitGlobals
XinitGlobals:
	pushq	%rbp
	movq	%rsp, %rbp
	movl	$4, %eax
	movl	%eax, Xi
	movl	$4, %eax
	movl	$3, %edi
	imull	%edi, %eax
	movl	%eax, Xa
	movl	$0, %eax
	movl	%eax, Xc
	movq	%rbp, %rsp
	popq	%rbp
	ret
	.globl	Xmain
Xmain:
	pushq	%rbp
	movq	%rsp, %rbp
	movl	Xi, %edi
	call	Xprint
	movl	Xa, %edi
	call	Xprint
	movl	Xa, %eax
	movl	$1, %edi
	addl	%edi, %eax
	movl	%eax, Xa
	movl	Xa, %edi
	call	Xprint
	movl	Xc, %edi
	call	Xprint
	movq	%rbp, %rsp
	popq	%rbp
	ret

