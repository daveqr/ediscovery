package com.davedaniels.ediscovery.dao;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration( locations = { "classpath:daoContext.xml", 
"classpath:test-datasource-context.xml" } )
@TransactionConfiguration( transactionManager = "transactionManager", defaultRollback = false )
public class TestMyDao extends AbstractTransactionalJUnit4SpringContextTests {

   /**
    * @throws java.lang.Exception
    */
   @Before
   public void setUp() throws Exception {
      deleteFromTables( "myTable" );
   }
   
   @Test
   public void myTest() {
      fail("Test something");
   }
}
