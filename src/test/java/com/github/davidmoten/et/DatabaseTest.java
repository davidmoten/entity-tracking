package com.github.davidmoten.et;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.PropertyContainer;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DatabaseTest {

	@Test
	public void test() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("time", new Date());
		map.put("lat", -15.7);
		map.put("lon", 135.5);
		PropertyContainer pc = EasyMock.createMock(PropertyContainer.class);
		EasyMock.expect(pc.getProperties()).andReturn(map).atLeastOnce();
		EasyMock.replay(pc);
		Database.writeReportsAsJson(Lists.newArrayList(pc, pc),
				new PrintWriter(System.out));
		EasyMock.verify(pc);
	}

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testGetReports() {
		Database db = new Database();
		db.systemIntegrationTest(new PrintWriter(System.out));
	}
}
