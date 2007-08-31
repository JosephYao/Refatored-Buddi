/*
 * Created on Aug 24, 2007 by wyatt
 */
package org.homeunix.thecave.buddi.model;

import static org.junit.Assert.*;

import org.homeunix.thecave.buddi.model.impl.ModelFactory;
import org.junit.Before;
import org.junit.Test;



public class DataModelTest {

	private Document d;
	
	@Before
	public void setup() throws Exception{
		d = ModelFactory.createDocument();
	}
	
	@Test
	public void testDocument(){
		try {
			assertTrue(d.getBudgetCategories().size() > 1);
		
		}
		catch (Exception e){
			fail("Exception: " + e);
		}
	}
	
}
