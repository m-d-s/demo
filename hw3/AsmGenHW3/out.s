	.file	"out.s"

	.data
Xi:
	.long	0

	.text

	.globl	XinitGlobals
XinitGlobals:
	pushq	%rbp
	movq	%rsp, %rbp
	movl	$1, %eax
	movl	%eax, Xi
	movq	%rbp, %rsp
	popq	%rbp
	ret
	.globl	Xmain
Xmain:
	pushq	%rbp
	movq	%rsp, %rbp
	movl	Xi, %edi
	call	Xprint
	movl	$42, %edi
	call	Xprint
	movq	%rbp, %rsp
	popq	%rbp
	ret

