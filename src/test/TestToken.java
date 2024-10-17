package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;

class TestToken {

	@Test
	public void test() {
		
		
		//testing correct constructors
		Token token0 = new Token(TokenType.TYINT, 1);
		Token token1 = new Token(TokenType.ID, 1, "number");
		Token token2 = new Token(TokenType.INT, 1, "44");
		
		assertEquals(token0.getTipo(), TokenType.TYINT);
		assertEquals(token0.getLine(), 1);
		assertEquals(token0.getVal(), null);
		
		assertEquals(token1.getTipo(), TokenType.ID);
		assertEquals(token1.getLine(), 1);
		assertEquals(token1.getVal(), "number");
		
	
		
	}

}
