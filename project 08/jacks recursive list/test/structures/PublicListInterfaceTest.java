package structures;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PublicListInterfaceTest {

	private ListInterface<String> list;
	private ListInterface<Integer> nums;
	private ListInterface<Integer> little;

	@Before
	public void setup(){
          list = new RecursiveList<String>();
	}

	@Test (timeout = 500)
	public void testInsertFirstIsEmptySizeAndGetFirst1() {
		assertTrue("Newly constructed list should be empty.", list.isEmpty());
		assertEquals("Newly constructed list should be size 0.", 0, list.size());
		assertEquals("Insert First should return instance of self", list, list.insertFirst("hello"));
		assertFalse("List should now have elements.", list.isEmpty());
		assertEquals("List should now have 1 element.", 1, list.size());
		assertEquals("First element should .equals \"hello\".", "hello", list.getFirst());
		list.insertFirst("world");
		assertEquals(2, list.size());
		list.insertFirst("foo");
		assertEquals(3, list.size());
		assertEquals("First element should .equals \"foo\".", "foo", list.getFirst());
	}
	public void testEverything(){
		nums.insertFirst(2);
		assertTrue(nums.size()==1);
		nums.insertAt(0, 1);
		assertTrue(nums.size()==2);
		assertTrue(nums.get(0)==1);
		assertFalse(nums.isEmpty());
		nums.insertAt(2, 4);
		nums.insertAt(2, 3);
		nums.insertLast(5);
		assertTrue(nums.get(2)==3);
		assertTrue(nums.getFirst()==1);
		assertTrue(nums.getLast()==5);
		nums.remove(3);
		for(int x = 0;x<nums.size();x++){
			//System.out.print(nums.get(x));
		}
		
	}
	@Test
	public void testLittleStuff(){
		/*assertTrue(little.size()==0);
		little.insertFirst(7);
		assertTrue(little.size()==1);
		little.remove(little.get(0));
		assertTrue(little.size()==0);*/
		/*little.insertFirst(8);
		little.insertFirst(2);
		little.insertFirst(6);
		little.insertFirst(4);
		little.insertLast(5);
		little.remove(2);
		assertTrue(little.size()==4);
		assertTrue(little.size()==4);
		assertTrue(little.get(0)==4);
		assertTrue(little.get(1)==6);
		assertTrue(little.get(2)==8);
		assertTrue(little.get(3)==5);
		little.removeAt(2);
		assertTrue(little.size()==3);
		assertTrue(little.get(0)==4);
		assertTrue(little.get(1)==6);
		assertTrue(little.get(2)==5);
		little.removeFirst();
		assertTrue(little.size()==2);
		System.out.println(little.get(0));
		System.out.println(little.get(1));*/
		assertTrue(little.size()==0);
		
		little.insertAt(0, 7);
		assertTrue(little.get(0)==7);
		assertTrue(little.indexOf(7)==0);
		little.remove(7);
		assertTrue(little.size()==0);
		assertTrue(little.isEmpty());
		assertTrue(little.indexOf(7)==-1);
		little.insertFirst(4);
		little.insertFirst(6);
		little.insertAt(1, 8);
		assertTrue(little.indexOf(6)==0);
		assertTrue(little.indexOf(8)==1);
		assertTrue(little.removeAt(2)==4);
		assertTrue(little.removeAt(0)==6);
		assertTrue(little.removeAt(0)==8);
		assertTrue(little.isEmpty());
		little.insertLast(8);
		little.insertAt(1,3);
		little.insertFirst(9);
		//assertTrue()
	}
	@Test (timeout = 500)
	public void testInsertFirstIsEmptySizeAndGetFirst7() {
		assertTrue(list.isEmpty());
		assertEquals(0, list.size());
		assertEquals(list, list.insertLast("hello"));
		assertFalse(list.isEmpty());
		assertEquals(1, list.size());
		assertEquals("hello", list.getFirst());
		list.insertLast("world");
		assertEquals(2, list.size());
		list.insertLast("foo");
		assertEquals(3, list.size());
		assertEquals("foo", list.getLast());
	}
	@Test (timeout = 500)
	public void testLastremovelast() {
		list.insertLast("hey");
		list.insertLast("help");
		list.insertLast("me");
		list.insertLast("get");
		list.insertLast("through");
		list.insertLast("these");
		list.insertLast("fucking");
		list.insertLast("tests");
		assertFalse(list.isEmpty());
		assertEquals(8, list.size());
		assertEquals("tests", list.getLast());
		list.removeLast();
		assertEquals(7, list.size());
		assertEquals("fucking", list.getLast());
		list.removeLast();
		assertEquals(6, list.size());
		assertEquals("these", list.getLast());
		list.removeLast();
		assertEquals(5, list.size());
		assertEquals("through", list.getLast());
		list.removeLast();
		assertEquals(4, list.size());
		assertEquals("get", list.getLast());
		list.removeLast();
		assertEquals(3, list.size());
		assertEquals("me", list.getLast());
		list.removeLast();
		assertEquals(2, list.size());
		assertEquals("help", list.getLast());	
		list.removeLast();
		assertEquals(1, list.size());
		assertEquals("hey", list.getLast());
		list.removeLast();
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
	}
	@Test (timeout = 500)
	public void testtestInsertAtIsEmptySizeAndGetAt() {
		assertTrue(list.isEmpty());
		assertEquals(0, list.size());
		list.insertAt(0,"world");
	
		assertFalse(list.isEmpty());
		assertEquals(1, list.size());
		list.insertAt(1,"help");
		list.insertAt(2,"me");
		list.insertAt(0,"ah");
		list.insertAt(4,"save");
		list.insertAt(0,"asdf");
		assertEquals("asdf", list.get(0));
		assertFalse(list.isEmpty());
		assertEquals("world", list.get(2));
		assertEquals("save", list.getLast());
	}
	@Test 
	public void testInsertsGetsRemovesSize(){
		list.insertAt(0,"a");
		assertEquals(1, list.size());
		list.insertFirst("b");
		list.insertLast("c");
		assertEquals(3, list.size());
		list.insertAt(1,"d");
		list.insertAt(3,"e");
		assertEquals(5, list.size());
		list.insertLast("f");
		list.insertAt(0,"g");
		assertEquals(7, list.size());
		list.insertAt(2,"h");
		list.insertFirst("i");


		assertEquals(9, list.size());
		assertEquals("i", list.get(0));
		assertEquals("g", list.get(1));
		assertEquals("b", list.get(2));
		assertEquals("h", list.get(3));
		assertEquals("d", list.get(4));
		assertEquals("a", list.get(5));
		assertEquals("e", list.get(6));
		assertEquals("c", list.get(7));
		assertEquals("f", list.get(8));
	}
}
