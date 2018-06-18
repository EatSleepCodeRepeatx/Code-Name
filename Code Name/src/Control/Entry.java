package Control;

public class Entry<E> {

  /** Data stored within this node. */
  private E element;

  /** Reference to the node following this one in the linked list. */
  private Entry<E> next;
  private Entry<E> previous;
  
  //Variable that holds whether this team has revealed an assassin.
  private boolean hasRevealedAssassin;

  /**
   * Creates an empty node.
   */
  public Entry() {
    this(null);
    hasRevealedAssassin = false;
  }

  /**
   * Creates a node storing the specified element.
   *
   * @param elem Element to which the new node should refer
   */
  public Entry(E elem) {
    next = null;
    previous = null;
    element = elem;
    hasRevealedAssassin = false;
  }

  /**
   * Get the next node in the linked list.
   *
   * @return Reference to the node following this one
   */
  public Entry<E> getNext() {
    return next;
  }
  public Entry<E> getPrev() {
	return previous;
  }

  /**
   * Specify that the given node should follow the current one in the linked list.
   *
   * @param node node which should follow the current one
   */
  public void setNext(Entry<E> node) {
    next = node;
  }
  public void setPrev(Entry<E> node) {
	previous = node;
  }

  /**
   * Get the element stored within this node.
   *
   * @return Element referred to by the node
   */
  public E getElement() {
    return element;
  }

  /**
   * Direct the node to store a new element.
   *
   * @param elem New element which this node should store
   */
  public void setElement(E elem) {
    element = elem;
  }
  public boolean equals(Entry<E> other) {
	  return element.equals(other.getElement());
  }
  
  public void setRevealedAssassin() {
	  hasRevealedAssassin = true;
  }
  
  public boolean hasRevealedAssassin() {
	  return hasRevealedAssassin;
  }
}