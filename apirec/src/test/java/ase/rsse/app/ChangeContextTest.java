package ase.rsse.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ase.rsse.apirec.transactions.ChangeContext;

public class ChangeContextTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
			// with
			ChangeContext cctx = new ChangeContext();			
			assertNotNull(cctx);
			
			// set
			cctx = null;
			assertEquals(null, cctx);
			cctx = new ChangeContext();
			assertNotNull(cctx);
		}
}
