package structures;

import java.util.Iterator;

public class RecursiveList<T> implements ListInterface<T> {
	private Node<T> head = null;

	private Node<T> curr = head;
	private int size;
	private int searchSize=size;
	private int searchCounter;
	public RecursiveList(){
		this.head=null;
		this.size=0;
		this.searchSize=size;
		this.searchCounter=0;
	}
	public Node<T> runner(int i,RecursiveList<T> a){
		
	if(i==0){
		return curr;
	}
	
	 else{
		 a.curr=a.curr.next;
		 return runner(i-1,a);
	 }
	}

	/*public void resetCurr(){
	curr=head;
	}*/
	public ListInterface<T> insertAt(int index, T elem) throws IndexOutOfBoundsException{
		// TODO Auto-generated method stub
		if(index<0 || index>size){
			throw new IndexOutOfBoundsException();
		}
		else{
		Node<T> insert= new Node<T>(elem);
		
		if(size==0){
			head=insert;
			size=1;
		}
		
		else{
			Node<T> first = runner(index-1,this);
			Node<T> second = null;
		if(index==0){
			insert.next=this.head;
			head=insert;
			size++;
			
		}
		else if(index==size){
			first.next=insert;
			size++;
			}
		else if(index!=size){
		second = runner(index,this);
		first.next=insert;
		insert.next=second;
		size++;
		}
		}
		}
		curr=head;
		
		return this;
		
	}
	/**
	 * Removes the ith element in this {@link ListInterface} and returns it.
	 * This method runs in O(i) time.
	 * 
	 * @param i
	 *            the index of the element to remove
	 * @throws IndexOutOfBoundsException
	 *             if {@code i} is less than 0 or {@code i} is greater than or
	 *             equal to {@link ListInterface#size()}
	 * @return The removed element
	 */
	public Node<T> search(T elem,int searchSize){
		if(searchSize==0){
			return null;
		}
		
		 else{
			 if(curr.data==elem)
			 {
				 return curr;
			 }
			 else{
			searchCounter++;
			curr=curr.next;
			return search(elem,searchSize-1);
			 }
	}
	}
	/**
	 * Removes the ith element in this {@link ListInterface} and returns it.
	 * This method runs in O(i) time.
	 * 
	 * @param i
	 *            the index of the element to remove
	 * @throws IndexOutOfBoundsException
	 *             if {@code i} is less than 0 or {@code i} is greater than or
	 *             equal to {@link ListInterface#size()}
	 * @return The removed element
	 */
	public T removeAt(int i) throws IndexOutOfBoundsException{
		if(i<0||i>size-1){
			throw new IndexOutOfBoundsException();
		}
		else{
			T out= runner(i,this).data;
			remove(runner(i,this).data);
			size--;
			return out;
		}
	
	}
	@Override
	/**
	 * Returns the ith element in this {@link ListInterface}. This method runs
	 * in O(i) time.
	 * 
	 * @param i
	 *            the index to lookup
	 * @throws IndexOutOfBoundsException
	 *             if {@code i} is less than 0 or {@code i} is greater than or
	 *             equal to {@link ListInterface#size()}
	 * @return the ith element in this {@link ListInterface}.
	 */
	public T get(int i) throws IndexOutOfBoundsException{
		if(i<0||i>=size){
			throw new IndexOutOfBoundsException();
		}
		return runner(i,this).data;
		
		
	}
	/**
	 * Removes {@code elem} from this {@link ListInterface} if it exists. If
	 * multiple instances of {@code elem} exist in this {@link ListInterface}
	 * the one associated with the smallest index is removed. This method runs
	 * in O(size) time.
	 * 
	 * @param elem
	 *            the element to remove
	 * @throws NullPointerException
	 *             if {@code elem} is {@code null}
	 * @return {@code true} if this {@link ListInterface} was altered and
	 *         {@code false} otherwise.
	 */

	@Override
	public boolean remove(T elem) throws NullPointerException {
		if(elem==null){
			throw new NullPointerException();
		}
		if(this.indexOf(elem)<0){
			return false;
		}
		if(this.indexOf(elem)==0){
			head=head.next;
			size--;
			return true;
		}
		else{
			Node<T> first =runner(this.indexOf(elem)-1,this);
			Node<T> next = runner(this.indexOf(elem)+1,this);
			first.next=next;
			size--;
			return true;
		}
	
	}
	/**
	 * Returns the smallest index which contains {@code elem}. If there is no
	 * instance of {@code elem} in this {@link ListInterface} then -1 is
	 * returned. This method runs in O(size) time.
	 * 
	 * @param elem
	 *            the element to search for
	 * @throws NullPointerException
	 *             if {@code elem} is {@code null}
	 * @return the smallest index which contains {@code elem} or -1 if
	 *         {@code elem} is not in this {@link ListInterface}
	 */
	@Override
	public int indexOf(T elem) {
		if(this.search(elem, searchSize)==null){
			return -1;
		}
		else{
			return searchCounter;
		}
		
	}
	
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public ListInterface<T> insertFirst(T elem) {
		// TODO Auto-generated method stub
		size++;
		return insertAt(0,elem);
	}

	@Override
	public ListInterface<T> insertLast(T elem) {
		// TODO Auto-generated method stub
		size++;
		return insertAt(size,elem);
	}

	
	public T removeFirst() {
		// TODO Auto-generated method stub
		size--;
		return removeAt(0);
	}

	@Override
	public T removeLast() {
		// TODO Auto-generated method stub
		size--;
		return removeAt(size-1);
	}

	@Override
	public T getFirst() {
		// TODO Auto-generated method stub
		return get(0);
	}

	@Override
	public T getLast() {
		// TODO Auto-generated method stub
		return get(size-1);
	}

	


	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (size==0);
	}

	class Node<T> {
			public T data;
			public Node<T> next;
			
			public Node(T data) { this.data=data;}
			public Node(T data, Node<T> next) {
				this.data = data; this.next=next;
			}
	}
}
