package ase.rsse.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ase.rsse.apirec.transactions.ChangeContext;
import ase.rsse.apirec.transactions.Operation;

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
			ChangeContext cctx = new ChangeContext()
					.withIndex(0)
					.withLabel("label")
					.withOperation(Operation.ADD)
					.withDistance(2.5f)
					.withWeightOfScope(0.5f)
					.withWeightOfDependency(1f);
			
			assertNotNull(cctx);
			assertEquals(0, cctx.getIndex());
			assertEquals("label", cctx.getLabel());
			assertEquals(Operation.ADD, cctx.getOperation());
			assertEquals(2.5f, cctx.getDistance(), 0.01);
			assertEquals(0.5f, cctx.getWeightOfScope(), 0.01);
			assertEquals(1f, cctx.getWeightOfDependency(), 0.01);
			
			// set
			cctx = null;
			assertEquals(null, cctx);
			cctx = new ChangeContext();
			assertNotNull(cctx);
			cctx.setIndex(0);
			cctx.setLabel("label");
			cctx.setOperation(Operation.ADD);
			cctx.setDistance(2.5f);
			cctx.setWeightOfScope(0.5f);
			cctx.setWeightOfDependency(1f);
			
			assertEquals(0, cctx.getIndex());
			assertEquals("label", cctx.getLabel());
			assertEquals(Operation.ADD, cctx.getOperation());
			assertEquals(2.5f, cctx.getDistance(), 0.01);
			assertEquals(0.5f, cctx.getWeightOfScope(), 0.01);
			assertEquals(1f, cctx.getWeightOfDependency(), 0.01);
		}
}
