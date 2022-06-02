package TDAArbolBinario;

public class BTNode<E>  implements BTPosition<E> {
	private E element;
	private BTPosition<E> left, right, parent;
	
	public BTNode( E element, BTPosition<E> left, BTPosition<E> right, BTPosition<E> parent) {
		this.element = element;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}
	
	public E element () {
		return this.element;
	}
	
	public BTPosition<E> getLeft() {
		return this.left;
	}
	
	public BTPosition<E> getRight(){
		return this.right;
	}
	
	public BTPosition<E> getParent(){
		return this.parent;
	}
	
	public void setElement(E element) {
		this.element = element;
	}
	
	public void setLeft(BTPosition<E> left) {
		this.left = left;
	}
	
	public void setRight(BTPosition<E> right) {
		this.right = right;
	}
	
	public void setParent(BTPosition<E> parent) {
		this.parent = parent;
	}
}
