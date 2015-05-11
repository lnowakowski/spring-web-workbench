package org.ln.spring.web.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class SimpleTests {
	@Test
	public void testSwapNormalArithm() {
		final int _a = 10;
		final int _b = 20;
		
		int a = _a;
		int b = _b;
		
		a = a + b;
		b = a - b;
		a = a - b;
		
		assertEquals(_b, a);
		assertEquals(_a, b);
	}

	@Test
	public void testSwapNormalDivMul() {
		final int _a = 10;
		final int _b = 20;
		
		int a = _a;
		int b = _b;
		
		a = a * b;
		b = a / b;
		a = a / b;
		
		assertEquals(_b, a);
		assertEquals(_a, b);
	}

	@Test
	public void testSwapXor() {
		final int _a = 10;
		final int _b = 20;
		
		int a = _a;
		int b = _b;
		
		a = a ^ b;
		b = a ^ b;
		a = a ^ b;
		
		assertEquals(_b, a);
		assertEquals(_a, b);
	}
	
	@Test
	public void testReverseStringWithoutApi() {
		String in = "abcdef";
		String out = "fedcba";
		
		assertEquals(out, reverse(in));
	}
	
	private String reverse(String str) {
		if (str.length() < 2) {
			return str;
		}
		
		return reverse(str.substring(1)) + str.charAt(0);
	}
}
