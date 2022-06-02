package TDAArbolBinario;

import java.util.Iterator;

import Excepciones.BoundaryViolationException;
import Excepciones.EmptyListException;
import Excepciones.EmptyTreeException;
import Excepciones.InvalidOperationException;
import Excepciones.InvalidPositionException;
import TDALista.ElementIterator;
import TDALista.ListaDoblementeEnlazada;
import TDALista.Position;
import TDALista.PositionList;
//Funciona
public class ArbolBinarioEnlazado<E> implements BinaryTree<E> {
	protected BTPosition<E> raiz;
	protected int size;
	
	/**
	 * Crea un objeto de tipo arbol binario vacio.
	 */
	public ArbolBinarioEnlazado() {
		this.raiz = null;
		this.size = 0;
	}
	
	/**
	 * Consulta la cantidad de nodos en el árbol.
	 * @return Cantidad de nodos en el árbol.
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Consulta si el árbol está vacío.
	 * @return Verdadero si el árbol está vacío, falso en caso contrario.
	 */
	public boolean isEmpty() {
		return this.size==0;
	}
	
	/**
	 * Devuelve un iterador de los elementos almacenados en el árbol en preorden.
	 * @return Iterador de los elementos almacenados en el árbol.
	 */
	public Iterator<E> iterator() {
		ElementIterator<E> it = null;
		PositionList<E> list = new ListaDoblementeEnlazada<E>();
		if(!this.isEmpty() ) {
			this.preOrden(list, raiz);
		}
		try {
			it = new ElementIterator<E>(list);
		} catch (EmptyListException e) {System.out.println("Entro en una exception");}
		
		return list.iterator();
	}
	
	/**
	 * Devuelve una colección iterable de las posiciones de los nodos del árbol.
	 *@return Colección iterable de las posiciones de los nodos del árbol.
	 */
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> list = new ListaDoblementeEnlazada<Position<E>>();
		if( !isEmpty()) {
			pre(list, raiz);
		}
		return list;
	}
	
	/**
	 * Modifica la lista añadiendo las posiciones de un subárbol de manera pre-Orden.
	 * @param list Lista de posiciones de un elemento.
	 * @param p Posicion de la raiz del subárbol.
	 */
	private void pre(PositionList<Position<E>> list, BTPosition<E> p) {
		list.addLast(p);
		if (p.getLeft() != null)
			pre(list, p.getLeft());
		if (p.getRight() != null)
			pre(list, p.getRight());
	}
	
	/**
	 * Modifica la lista añadiendo los elementos de un subárbol de manera pre-Orden.
	 * @param list Lista de elementos.
	 * @param p Posición de la raiz del subárbol.
	 */
	private void preOrden(PositionList<E> list, BTPosition<E> p) {
		list.addLast(p.element());
		if(p.getLeft() != null) {
			this.preOrden(list, p.getLeft());
		} 
		if(p.getRight() != null) {
			this.preOrden(list, p.getRight());
		}
	}
	
	/**
	 * Reemplaza el elemento almacenado en la posición dada por el elemento pasado por parámetro. Devuelve el elemento reemplazado.
	 * @param v Posición de un nodo.
	 * @param e Elemento a reemplazar en la posición pasada por parámetro.
	 * @return Elemento reemplazado.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 */
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> nodo = this.checkPosition(v);
		E elementOfNodo = nodo.element();
		nodo.setElement(e);
		return elementOfNodo;
	}
	
	/**
	 * Devuelve la posición de la raíz del árbol.
	 * @return Posición de la raíz del árbol.
	 * @throws EmptyTreeException si el árbol está vacío.
	 */
	public Position<E> root() throws EmptyTreeException {
		if(this.isEmpty()) {
			throw new EmptyTreeException("Arbol vacio");
		} else {
			return this.raiz;
		}
	}
	
	/**
	 * Devuelve la posición del nodo padre del nodo correspondiente a una posición dada.
	 * @param v Posición de un nodo.
	 * @return Posición del nodo padre del nodo correspondiente a la posición dada.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 * @throws BoundaryViolationException si la posición pasada por parámetro corresponde a la raíz del árbol.
	 */
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> nodo = this.checkPosition(v);
		if( nodo.equals(raiz)) {
			throw new BoundaryViolationException("El nodo raiz no tiene padre");
		} else {
			return nodo.getParent();
		}
	}
	
	/**
	 * Devuelve una colección iterable de los hijos del nodo correspondiente a una posición dada.
	 * @param v Posición de un nodo.
	 * @return Colección iterable de los hijos del nodo correspondiente a la posición pasada por parámetro.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 */
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		BTPosition<E> nodo = this.checkPosition(v);
		PositionList<Position<E>> list = new ListaDoblementeEnlazada<Position<E>>();
		
		if(nodo.getLeft() != null) {
			list.addLast(nodo.getLeft());
		}
		
		if(nodo.getRight() != null) {
			list.addLast(nodo.getRight());
		}
		
		return list;
	}
	
	/**
	 * Consulta si una posición corresponde a un nodo interno.
	 * @param v Posición de un nodo.
	 * @return Verdadero si la posición pasada por parámetro corresponde a un nodo interno, falso en caso contrario.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 */
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		BTPosition<E> nodo = this.checkPosition(v);
		return (nodo.getLeft() != null || nodo.getRight() != null);
	}
	
	/**
	 * Consulta si una posición dada corresponde a un nodo externo.
	 * @param v Posición de un nodo.
	 * @return Verdadero si la posición pasada por parámetro corresponde a un nodo externo, falso en caso contrario.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 */
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		BTPosition<E> nodo = this.checkPosition(v);
		return (nodo.getLeft() == null && nodo.getRight() == null);
	}
	
	/**
	 * Consulta si una posición dada corresponde a la raíz del árbol.
	 * @param v Posición de un nodo.
	 * @return Verdadero, si la posición pasada por parámetro corresponde a la raíz del árbol,falso en caso contrario.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 */
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTPosition<E> nodo = this.checkPosition(v);
		return (nodo.equals(raiz));
	}
	
	/**
	 * Crea un nodo con rótulo e como raíz del árbol.
	 * @param E Rótulo que se asignará a la raíz del árbol.
	 * @throws InvalidOperationException si el árbol ya tiene un nodo raíz.
	 */
	public Position<E> createRoot(E r) throws InvalidOperationException {
		if( this.raiz != null) {
			throw new InvalidOperationException("Operacion invalida");
		} else {
			this.raiz = new BTNode<E>( r, null, null, null);
			this.size++;
			return raiz;
		}
	}
	
	/**
	 * Devuelve la posición del hijo izquierdo de v.
	 * @param v Posición de un nodo.
	 * @return Posición del hijo izquierdo de v.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 * @throws BoundaryViolationException si v no tiene hijo izquierdo.
	 */
	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> nodo = this.checkPosition(v);
		if( nodo.getLeft() == null) {
			throw new BoundaryViolationException("No tiene hijo izquierdo");
		}
		return nodo.getLeft();
	}
	
	/**
	 * Devuelve la posición del hijo derecho de v.
	 * @param v Posición de un nodo.
	 * @return Posición del hijo derecho de v.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.
	 * @throws BoundaryViolationException si v no tiene hijo derecho.
	 */
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> nodo = this.checkPosition(v);
		if( nodo.getRight() == null) {
			throw new BoundaryViolationException("No tiene hijo derecho");
		}
		return nodo.getRight();
	}
	
	/**
	 * Testea si v tiene un hijo izquierdo.
	 * @param v Posición de un nodo.
	 * @return Verdadero si v tiene un hijo izquierdo y falso en caso contrario.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.	
	 */
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTPosition<E> nodo = this.checkPosition(v);
		return nodo.getLeft() != null;
	}
	
	/**
	 * Testea si v tiene un hijo derecho.
	 * @param v Posición de un nodo.
	 * @return Verdadero si v tiene un hijo derecho y falso en caso contrario.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida.	
	 */
	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTPosition<E> nodo = this.checkPosition(v);
		return nodo.getRight() != null;
	}
	
	/**
	 * Agrega un nodo con rótulo r como hijo izquierdo de un nodo dado.
	 * @param r Rótulo del nuevo nodo.
	 * @param v Posición del nodo padre.
	 * @return La posición del nuevo nodo creado.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida o el árbol está vacío.
	 * @throws InvalidOperationException si v ya tiene un hijo izquierdo.
	 */
	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> ancester = this.checkPosition(v);
		if( ancester.getLeft() != null ) {
			throw new InvalidOperationException ("Operacion invalida");
		} else {
			Position<E> nuevo = new BTNode<E>( r, null, null, ancester); 
			BTNode<E> nuevoNodo = this.checkPosition(nuevo);
			ancester.setLeft(nuevoNodo);
			this.size++;
			return nuevo;
		}
	}
	
	/**
	 * Agrega un nodo con rótulo r como hijo derecho de un nodo dado.
	 * @param r Rótulo del nuevo nodo.
	 * @param v Posición del nodo padre.
	 * @return La posición del nuevo nodo creado.
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida o el árbol está vacío.
	 * @throws InvalidOperationException si v ya tiene un hijo derecho.
	 */
	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> ancester = this.checkPosition(v);
		if( ancester.getRight() != null ) {
			throw new InvalidOperationException ("Operacion invalida");
		} else {
			Position<E> nuevo = new BTNode<E>( r, null, null, ancester); 
			BTNode<E> nuevoNodo = this.checkPosition(nuevo);
			ancester.setRight(nuevoNodo);
			this.size++;
			return nuevo;
		}
	}
	
	/**
	 * Elimina el nodo referenciado por una posición dada. Si el nodo tiene un único hijo, el nodo eliminado será reemplazado por su único hijo.
	 * @param v Posición del nodo a eliminar.
	 * @return el rótulo del nodo eliminado.
     * @throws InvalidPositionException si la posición pasada por parámetro es inválida o el árbol está vacío.
	 * @throws InvalidOperationException si el nodo a eliminar tiene mas de un hijo.
     */
	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> nodo = this.checkPosition(v);
		E elementOfNodo = nodo.element();
		//Verifico si tiene mas de un descendiente
		if(this.hasLeft(v) && this.hasRight(v)) {
			throw new InvalidOperationException("Operacion invalida");
		} else {
			boolean esDescendienteIzquierdo = false;
			boolean esDescendienteDerecho = false;
			BTPosition<E> ancester = nodo.getParent();
			BTPosition<E> nodoDescendiente = this.hasLeft(v) ? nodo.getLeft() : nodo.getRight();
			//Si tiene un descendiente
			if(this.hasLeft(v) || this.hasRight(v)) {
				if(!nodo.equals(raiz)) {
					//Si el ancestro tiene un hijo izquierdo, verifico si es nodo
					if(ancester.getLeft() != null) {
						esDescendienteIzquierdo = ancester.getLeft().equals(nodo);
					}
					//Si el ancestro tiene un hijo derecho, verifico si es nodo
					if(ancester.getRight() != null) {
						esDescendienteDerecho = ancester.getRight().equals(nodo);
					}
					//Si nodo es el descendiente izquierdo, le modifico al ancestro el descendiente izquierdo.
					if(esDescendienteIzquierdo) {
						ancester.setLeft(nodoDescendiente);
					} else if(esDescendienteDerecho){ //Si nodo es el descendiente derecho, le modifico al ancestro el descendiente derecho.
						ancester.setRight(nodoDescendiente);
					}
				} else {
					//Si nodo es la raiz del árbol, lo reemplazo.
					this.raiz = nodoDescendiente;
				}
				
				//Establezco el nuevo ancestro del nodo descendiente
				nodoDescendiente.setParent(ancester);
				
			} else {
				//Si no tiene descendiente
				if(!nodo.equals(raiz)) {
					//Si el ancestro tiene un hijo izquierdo, verifico si es nodo
					if(ancester.getLeft() != null) {
						esDescendienteIzquierdo = ancester.getLeft().equals(nodo);
					}
					//Si el ancestro tiene un hijo derecho, verifico si es nodo
					if(ancester.getRight() != null) {
						esDescendienteDerecho = ancester.getRight().equals(nodo);
					}
					//Si nodo es el descendiente izquierdo, le modifico al ancestro el descendiente izquierdo.
					if(esDescendienteIzquierdo) {
						ancester.setLeft(null);
					} else if(esDescendienteDerecho) { //Si nodo es el descendiente derecho, le modifico al ancestro el descendiente derecho.
						ancester.setRight(null);
					}
				}
			}
			//Ya actualizados los valores del entorno del nodo eliminado
			nodo.setParent(null);
			nodo.setLeft(null);
			nodo.setRight(null);
			nodo.setElement(null);
			this.size--;
			return elementOfNodo;
		}
	}
	
	/**
	 * Inserta a los árboles T1 y T2 como subárboles hijos de la hoja v (izquierdo y derecho respectivamente).
	 * @param v Posición de una hoja del árbol.
	 * @param T1 árbol binario a insertar como hijo izquierdo de v.
	 * @param T2 árbol binario a insertar como hijo derecho de v. 
	 * @throws InvalidPositionException si la posición pasada por parámetro es inválida o el árbol está vacío, o v no corresponde a una hoja.
	 */
	public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
		if(!this.isExternal(r)) {
			throw new InvalidPositionException("Posicion invalida");
		} else if(this.isEmpty()) {
			throw new InvalidPositionException("El arbol esta vacio");
		} else {
			BTPosition<E> nodo = this.checkPosition(r);
			BTPosition<E> raizIzquierda = null;
			BTPosition<E> raizDerecha = null;
			
			try {
				raizIzquierda = this.checkPosition(T1.root());
				raizDerecha = this.checkPosition(T2.root());
			} catch (EmptyTreeException e) {e.printStackTrace();}
			
			nodo.setLeft(raizIzquierda);
			nodo.setRight(raizDerecha);
			
			this.size = this.size + T1.size() + T2.size();
		}
	}
	
	/**
	 * Consulta si una posicion es valida y devuelve un nodo utilizable.
	 * @param p Posicion a verificar.
	 * @return el nodo del casteo de la posición.
	 * @throws InvalidPositionException si la posición pasada por parámetro es invalida.
	 */
	private BTNode<E> checkPosition(Position<E> p) throws InvalidPositionException {
		if( p == null ) {
			throw new InvalidPositionException("Posicion invalida");
		} else {
			BTNode<E> btPos;
			try {
				btPos = (BTNode<E>) p;
			} catch (ClassCastException e) {
				throw new InvalidPositionException ("Posicion invalida");
			}
			return btPos;
		}
	}
}
