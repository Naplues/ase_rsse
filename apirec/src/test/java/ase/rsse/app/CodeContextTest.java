package ase.rsse.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ase.rsse.apirec.transactions.CodeContext;

public class CodeContextTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		// with
		CodeContext coctx = new CodeContext();
		
		assertNotNull(coctx);
		
		// set
		coctx = null;
		assertEquals(null, coctx);
		coctx = new CodeContext();
		assertNotNull(coctx);
	}
}
