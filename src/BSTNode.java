public class BSTNode<E> implements Comparable<E>{
	protected E element;
	protected BSTNode<E> right;
	protected BSTNode<E> left;
	protected BSTNode<E> father;
	protected int balance;
	protected String tabs;
	
	public String getTabs() {
		return tabs;
	}

	public void setTabs(String tabs) {
		this.tabs = tabs;
	}

	public BSTNode(E e) {
		element = e;
		right = left = father = null;
	}
	
	public BSTNode(E e, BSTNode<E> father) {
		element = e;
		right = left = null;
		this.father = father;
	}

	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public BSTNode<E> getRight() {
		return right;
	}

	public void setRight(BSTNode<E> right) {
		this.right = right;
	}

	public BSTNode<E> getLeft() {
		return left;
	}

	public void setLeft(BSTNode<E> left) {
		this.left = left;
	}

	public BSTNode<E> getFather() {
		return father;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "BSTNode [element=" + element + ", right=" + right + ", left=" + left + ", father=" + father + "]";
	}

	public void setFather(BSTNode<E> father) {
		this.father = father;
	}
	
	public int getLevel() {
		BSTNode<E> father = this.getFather();
		int count = 0;
		while (father != null) {
			count++;
			father = father.getFather();
		}
		return count;
	}

	@Override
	public int compareTo(Object o) {
		return ((Comparable<E>)element).compareTo((E) o);
	}
}