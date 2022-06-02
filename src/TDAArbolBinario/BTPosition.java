package TDAArbolBinario;

import TDALista.Position;

public interface BTPosition<E> extends Position<E> {
	public E element();
	
	public BTPosition<E> getLeft();
	
	public BTPosition<E> getRight();
	
	public BTPosition<E> getParent();
	
	public void setElement(E element);
	
	public void setLeft(BTPosition<E> left);
	
	public void setRight(BTPosition<E> right);
	
	public void setParent(BTPosition<E> parent);
}
