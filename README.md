# AcDc Compiler
The compilation process is divided into four phases:<br>
    **Lexical Analysis**, to tokenize the input;<br>
    **Syntactic Analysis**, to parse the tokens and construct an **Abstract Syntax Tree** (AST);<br>
    **Semantic Analysis**,which includes type checking to ensure correctness;<br>
    **Code Generation**, where the AST is transformed into Dc code.<br>

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
