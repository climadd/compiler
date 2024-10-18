package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;

class TestToken {

	@Test
	public void testToken() {
		
		//testing correct constructors
		Token token0 = new Token(TokenType.TYINT, 1);
		Token token1 = new Token(TokenType.ID, 1, "number");
		Token token2 = new Token(TokenType.INT, 1, "44");
		
		assertEquals(token0.getType(), TokenType.TYINT);
		assertEquals(token0.getLine(), 1);
		assertEquals(token0.getValue(), null);
		
		assertEquals(token1.getType(), TokenType.ID);
		assertEquals(token1.getLine(), token0.getLine());
		assertEquals(token1.getValue(), "number");
		
		assertEquals(token2.getType(), TokenType.INT);
		assertEquals(token2.getValue(), "44");
	}
	
	@Test
	public void testToString(){
		
		//testing toStringMethod
		Token token0 = new Token(TokenType.TYFLOAT, 0);
		Token token1 = new Token(TokenType.ID, 12, "number");
		Token token2 = new Token(TokenType.PLUS, 1);
		
		assertEquals(token0.toString(), "<TYFLOAT, r:0>");
		assertEquals(token1.toString(), "<ID, r:12, number>");
		assertEquals(token2.toString(), "<PLUS, r:1>");
	}

}
