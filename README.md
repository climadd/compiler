# AcDc Compiler
The compilation consists of two phases: 
    **Lexical Analysis** and **Syntactic Analysis**, which builds an **Abstract Syntax Tree** (AST);
    followed by **Semantic Analysis** (type checking) and **Code Generation** for Dc, both based on the AST.

## Source Language: Ac
* 2 Data types: int and float.
* Integer literals: Sequence of digits. If starting with 0, no other digits can follow.
* Floating-point literals: Sequence of digits followed by a "." and up to 5 digits.
* Variables: Only lowercase English letters (a-z), must be declared before use.
* Declaration: "int" or "float" followed by a variable, with optional initialization.
* Expressions:Can be literals (int or float), variables, or binary expressions (+, -, *, /). Binary operands must be of the same type. int can automatically convert to float, no other conversions allowed.
* Instructions: Assignment and Print

## Target Language: Dc
Stack-based, uses reverse Polish notation. One of the first cross-platform applications for Unix.

* Usage: Start by typing "dc" in the terminal and pressing Enter. Input expressions to evaluate. Use "p" to print results and "q" to quit.
* Operators: Basic operators include +, -, *, /, and more.
* Precision: You can set the number of decimal digits to consider (default is 0). (Example: 5 4 / evaluates to 0 by default.)
* more on [Dc Manual](https://www.gnu.org/software/bc/manual/dc-1.05/html_mono/dc.html)
