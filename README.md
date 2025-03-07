AcDc Compiler
-------------
### Project Overview
This project is a compiler that translates Ac, a simple language with integer and floating-point support, into Dc, a stack-based calculator language using reverse Polish notation. The compiler processes Ac code through lexical, syntactic, and semantic analysis before generating Dc-compatible output.
Ac features basic arithmetic operations, variable declarations, and assignments, while Dc executes expressions using a stack-based approach. This compiler bridges the two, allowing Ac programs to be evaluated in Dc. More on [Dc Manual](https://www.gnu.org/software/bc/manual/dc-1.05/html_mono/dc.html).

Below is the installation guide to set up and run the project.

### Installation
This compiler was developed using **Java 17**, but it should run on any modern Java version. Before running the project, ensure that Java is installed on your system. You can follow this [installation guide](https://www.java.com/en/download/help/download_options.html) to set it up.

#### Setup Instructions:
1. **Verify Java Installation**: Run `java -version` in the terminal to confirm Java is installed.
2. **Clone the Repository** (if applicable): Use `git clone <repo-link>` to download the project.
3. **Compile the Source Code**: Navigate to the project directory and use `javac <MainClass>.java`.
4. **Run the Compiler**: Execute the program with `java <MainClass> <input-file.ac>`.

## About the Compiler
- **Lexical Analysis** – Performed by the `Scanner` class, this phase tokenizes the input stream, converting raw text into a sequence of meaningful tokens.  
- **Syntactic Analysis** – Handled by the `Parser` class, this stage processes tokens to construct an Abstract Syntax Tree (AST), representing the program’s structure.  
- **Semantic Analysis** – Executed by the `TypeCheckingVisitor` and its related classes, this phase ensures type correctness and enforces semantic rules.  
- **Code Generation** – Conducted by the `CodeGenerationVisitor`, where the AST is translated into Dc code, producing the final output.  
