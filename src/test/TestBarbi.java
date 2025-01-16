package test;

import org.junit.jupiter.api.Test;

import ast.*;
import scanner.*;
import parser.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestBarbi {


    private static final String INPUT_DIR = "src/test/data/testParser";

    @Test
    void testCorrectProgram() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testParser1.txt");
        Parser parser = new Parser(scanner);
        assertDoesNotThrow(parser::parse, "Unexpected Exception");
    }

    @Test
    void testUncorrectProgram() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testParserUncorrect.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, parser::parse);
    }


    @Test
    void testCorrectProgram2() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testParser2.txt");
        Parser parser = new Parser(scanner);
        assertDoesNotThrow(parser::parse, "Unexpected Exception");
    }

    @Test
    void testGenerale() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testGenerale.txt");
        Parser parser = new Parser(scanner);
        assertDoesNotThrow(parser::parse, "Unexpected Exception");
    }

    @Test
    void testGeneraleUncorrect() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testGeneraleUncorrect.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, parser::parse);
    }

    @Test
    void testEmpty () throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testEmpty.txt");
        Parser parser = new Parser(scanner);
        assertDoesNotThrow(parser::parse, "Unexpected Exception");
    }

    @Test
    void testParserCorretto1() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testParserCorretto1.txt");
        Parser parser = new Parser(scanner);
        assertDoesNotThrow(parser::parse, "Unexpected Exception");
    }

    @Test
    void testParserCorretto2() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testParserCorretto2.txt");
        Parser parser = new Parser(scanner);
        assertDoesNotThrow(parser::parse, "Unexpected Exception");
    }

    @Test
    void testParserEcc_0() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testParserEcc_0.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, parser::parse);
    }

    @Test
    void testParserEcc_1() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testParserEcc_1.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, parser::parse);
    }

    @Test
    void testParserEcc_2() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testParserEcc_2.txt");
        Parser parser = new Parser(scanner);
        SyntacticException exc = assertThrows(SyntacticException.class, parser::parse);
        assertEquals("Unexpected token INT at row 3", exc.getMessage());
    }

    @Test
    void testParserEcc_3() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testParserEcc_3.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, parser::parse);
    }

    @Test
    void testParserEcc_4() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testParserEcc_4.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, parser::parse);
    }

    @Test
    void testParserEcc_5() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testParserEcc_5.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, parser::parse);
    }

    @Test
    void testParserEcc_6() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testParserEcc_6.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, parser::parse);
    }

    @Test
    void testParserEcc_7() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testParserEcc_7.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, parser::parse);
    }

    @Test
    void testParserSoloDich() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testSoloDich.txt");
        Parser parser = new Parser(scanner);
        assertDoesNotThrow(parser::parse, "Unexpected Exception");
    }

    @Test
    void testParserEcc_9() throws IOException {
        Scanner scanner = new Scanner(INPUT_DIR + "/testSoloDichPrint.txt");
        Parser parser = new Parser(scanner);
        assertDoesNotThrow(parser::parse, "Unexpected Exception");
    }

    @Test
    void testParserComplexAST() throws IOException, SyntacticException, LexicalException {
        // Configurazione dello scanner con un input simulato
        Scanner scanner = new Scanner(INPUT_DIR + "/testAST.txt");
        Parser parser = new Parser(scanner);
        NodeProgram nodeProgram = parser.parse();

        // Costruzione manuale dell'AST atteso
        NodeId tempId = new NodeId("temp");

        NodeExpr value3 = new NodeConst("3", LangType.TYINT);
        NodeExpr value5 = new NodeConst("5", LangType.TYINT);
        NodeExpr value7 = new NodeConst("7", LangType.TYINT);
        NodeExpr value8_3 = new NodeConst("8.3", LangType.TYFLOAT);

        NodeExpr timesExpr = new NodeBinOp(LangOper.TIMES, value5, value7);
        NodeExpr divideExpr = new NodeBinOp(LangOper.DIVIDE, timesExpr, value8_3);
        NodeExpr plusExpr = new NodeBinOp(LangOper.PLUS, value3, divideExpr);

        NodeExpr derefTemp = new NodeDeref(tempId);
        NodeExpr minusExpr = new NodeBinOp(LangOper.MINUS, plusExpr, derefTemp);

        NodeDecl nodeDecl = new NodeDecl(tempId, LangType.TYINT, minusExpr);
        ArrayList<NodeDecSt> declarations = new ArrayList<>();
        declarations.add(nodeDecl);

        NodeProgram expectedAST = new NodeProgram(declarations);

        assertEquals(expectedAST.toString(), nodeProgram.toString());
    }

    @Test
    void testParserASTPrintAndAssign() throws IOException, SyntacticException, LexicalException {
        // Configura lo scanner con input simulato
        Scanner scanner = new Scanner(INPUT_DIR + "/testAST2.txt");
        Parser parser = new Parser(scanner);
        NodeProgram nodeProgram = parser.parse();
    
        // Costruzione manuale dell'AST atteso
        NodeId aId = new NodeId("a");
    
        // Nodo per il comando "print a;"
        NodeStm printStmt = new NodePrint(aId);
    
        // Nodo per l'assegnazione "a = 5 + 7 * 3;"
        NodeExpr value5 = new NodeConst("5", LangType.TYINT);
        NodeExpr value7 = new NodeConst("7", LangType.TYINT);
        NodeExpr value3 = new NodeConst("3", LangType.TYINT);
    
        NodeExpr timesExpr = new NodeBinOp(LangOper.TIMES, value7, value3);
        NodeExpr plusExpr = new NodeBinOp(LangOper.PLUS, value5, timesExpr);
    
        NodeStm assignStmt = new NodeAssign(aId, plusExpr);
    
        // Costruzione del programma
        ArrayList<NodeDecSt> statements = new ArrayList<>();
        statements.add(printStmt);
        statements.add(assignStmt);
    
        NodeProgram expectedAST = new NodeProgram(statements);
    
        assertEquals(expectedAST.toString(), nodeProgram.toString());
    }


    @Test
    void testParserASTWithMultipleDeclarationsAndAssignments() throws IOException, SyntacticException, LexicalException {
        Scanner scanner = new Scanner("./src/test/data/testParser/testo.txt");
        Parser parser = new Parser(scanner);
        NodeProgram nodeProgram = parser.parse();


        NodeId tempId = new NodeId("temp");
        NodeDecl tempDecl = new NodeDecl(tempId, LangType.TYINT, null);

        NodeBinOp tempAssignExpr = new NodeBinOp(
            LangOper.PLUS,
            new NodeDeref(tempId),
            new NodeConst("7", LangType.TYINT)
        );
        NodeStm tempAssign = new NodeAssign(tempId, tempAssignExpr);

        NodeId temp1Id = new NodeId("temp");
        NodeDecl temp1Decl = new NodeDecl(temp1Id, LangType.TYFLOAT, null);

        NodeBinOp mulExpr = new NodeBinOp(
            LangOper.TIMES,
            new NodeConst("7", LangType.TYINT),
            new NodeConst("5", LangType.TYINT)
        );
        NodeBinOp plusExpr = new NodeBinOp(
            LangOper.PLUS,
            new NodeConst("3", LangType.TYINT),
            mulExpr
        );
        NodeBinOp divExpr = new NodeBinOp(
            LangOper.DIVIDE,
            new NodeConst("6", LangType.TYINT),
            new NodeConst("4", LangType.TYINT)
        );
        NodeBinOp finalExpr = new NodeBinOp(
            LangOper.MINUS,
            plusExpr,
            divExpr
        );
        NodeStm temp1Assign = new NodeAssign(temp1Id, finalExpr);

        ArrayList<NodeDecSt> statements = new ArrayList<>();
        statements.add(tempDecl);
        statements.add(tempAssign);
        statements.add(temp1Decl);
        statements.add(temp1Assign);

        NodeProgram expectedAST = new NodeProgram(statements);

        assertEquals(expectedAST.toString(), nodeProgram.toString());
        
    }
}
