package ase.rsse.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ase.rsse.apirec.transactions.CodeContext;
import ase.rsse.apirec.transactions.Operation;

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
		CodeContext coctx = new CodeContext()
				.withIndex(0)
				.withLabel("label")
				.withOperation(Operation.ADD)
				.withDistance(2.5f)
				.withWeightOfScope(0.5f)
				.withWeightOfDependency(1f);
		
		assertNotNull(coctx);
		assertEquals(0, coctx.getIndex());
		assertEquals("label", coctx.getLabel());
		assertEquals(Operation.ADD, coctx.getOperation());
		assertEquals(2.5f, coctx.getDistance(), 0.01);
		assertEquals(0.5f, coctx.getWeightOfScope(), 0.01);
		assertEquals(1f, coctx.getWeightOfDependency(), 0.01);
		
		// set
		coctx = null;
		assertEquals(null, coctx);
		coctx = new CodeContext();
		assertNotNull(coctx);
		coctx.setIndex(0);
		coctx.setLabel("label");
		coctx.setOperation(Operation.ADD);
		coctx.setDistance(2.5f);
		coctx.setWeightOfScope(0.5f);
		coctx.setWeightOfDependency(1f);
		
		assertEquals(0, coctx.getIndex());
		assertEquals("label", coctx.getLabel());
		assertEquals(Operation.ADD, coctx.getOperation());
		assertEquals(2.5f, coctx.getDistance(), 0.01);
		assertEquals(0.5f, coctx.getWeightOfScope(), 0.01);
		assertEquals(1f, coctx.getWeightOfDependency(), 0.01);
	}
}
