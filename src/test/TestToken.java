package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;

class TestToken {

	@Test
	void test() {
		Token t = new Token (TokenType.FLOAT, 1, "0.4444");
		String s = t.toString();
		s.equals("<FLOAT, r:1, 0.4444");

	}

}
