
public interface AVLInterface<T> {
	public boolean isEmpty();
	public int numElements();
	public void insert(T element) throws DuplicatedKeyException;
	public void remove(T element);
	public boolean binarySearch (Comparable<T> element);
}
