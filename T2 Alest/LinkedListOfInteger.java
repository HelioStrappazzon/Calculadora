public class LinkedListOfInteger {

    // Classe interna Node
    private class Node {
        public Integer element;
        public Node next;

        public Node(Integer element) {
            this.element = element;
            next = null;
        }

        public Node(Integer element, Node next) {
            this.element = element;
            this.next = next;
        }
    }

    // Referência para o primeiro elemento da lista encadeada.
    private Node head;
    // Referência para o último elemento da lista encadeada.
    private Node tail;
    // Contador para a quantidade de elementos que a lista contém.
    private int count;

    /**
     * Construtor da lista.
     */
    public LinkedListOfInteger() {
        head = null;
        tail = null;
        count = 0;
    }

    /**
     * Retorna true se a lista não contém elementos.
     * @return true se a lista não contém elementos
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Retorna o número de elementos da lista.
     * @return o número de elementos da lista
     */
    public int size() {
        return count;
    }

    /**
     * Esvazia a lista
     */
    public void clear() {
        head = null;
        tail = null;
        count = 0;
    }

    /**
     * Adiciona um elemento ao final da lista.
     * @param element elemento a ser adicionado ao final da lista
     */
    public void add(Integer element) { // O(1)
        Node n = new Node(element);
        if (head == null) {
            head = n;
        } else {
            tail.next = n;
        }
        tail = n;
        count++;
    }

    /**
     * Retorna o elemento de uma determinada posição da lista.
     * @param index a posição da lista
     * @return o elemento da posição especificada
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    public Integer get(int index) { // O(n)
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException();
        }
        if (index == count - 1)
            return tail.element;

        Node aux = head;
        int c = 0;
        while (c < index) {
            aux = aux.next;
            c++;
        }
        return aux.element;
    }

    /**
     * Retorna true se a lista contém o elemento especificado.
     * @param element o elemento a ser testado
     * @return true se a lista contém o elemento especificado
     */
    public boolean contains(Integer element) { // O(n)
        Node aux = head;
        while (aux != null) {
            if (aux.element.equals(element)) {
                return true;
            }
            aux = aux.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        Node aux = head;

        while (aux != null) {
            s.append(aux.element.toString());
            s.append("\n");
            aux = aux.next;
        }

        return s.toString();
    }

    ////////////////////////////////////////////////////////////////

    /**
     * Substitui o elemento armazenado em uma determinada posição da lista pelo
     * elemento indicado.
     * @param index a posição da lista
     * @param element o elemento a ser armazenado na lista
     * @return o elemento armazenado anteriormente na posição da lista
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    public Integer set(int index, Integer element) {
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException();
        }
        Integer elem = null;

        if (index == count - 1) { // se for o último, não precisa percorrer a lista
            elem = tail.element;
            tail.element = element;
            return elem;
        }

        Node aux = head;
        for (int i = 0; i < index; i++) { // vai até a posição index
            aux = aux.next;
        }
        elem = aux.element;
        aux.element = element;
        return (elem);
    }

    /**
     * Insere um elemento em uma determinada posição da lista.
     * @param index a posição da lista onde o elemento será inserido
     * @param element elemento a ser inserido
     * @throws IndexOutOfBoundsException se (index < 0 || index > size())
     */
    public void add(int index, Integer element) {
        // Primeiro verifica se index é válido
        if (index < 0 || index > count) {
            throw new IndexOutOfBoundsException();
        }

        // Cria o nodo
        Node n = new Node(element);

        // "Encadear" o nodo criado na lista
        if (index == 0) { // se inserção no início
            if (count == 0) { // se a lista estava vazia
                tail = n;
            } else {
                n.next = head;
            }
            head = n;
        } else if (index == count) { // se inserção no fim
            tail.next = n;
            tail = n;
        } else { // se inserção no meio
            Node ant = head;
            for (int i = 0; i < index - 1; i++) { // "caminha" até a posição index-1
                ant = ant.next;
            }
            n.next = ant.next;
            ant.next = n;
        }

        // Atualiza o count
        count++;
    }

    /**
     * Remove a primeira ocorrência do elemento na lista, se estiver presente.
     * @param element o elemento a ser removido
     * @return true se a lista contém o elemento especificado
     */
    public boolean remove(Integer element) {
        if (count == 0)
            return false;

        // Se remoção do primeiro
        if (element.equals(head.element)) {
            if (count == 1) { // se tem apenas 1 elemento na lista
                tail = null;
            }
            head = head.next;
            count--;
            return true;
        }

        Node ant = head; // referência para o anterior
        Node aux = head.next; // referência para o elemento que está sendo verificado

        for (int i = 1; i < count; i++) {
            if (element.equals(aux.element)) { // se achou o elemento a ser removido
                if (aux == tail) { // se remoção do último
                    tail = ant;
                    tail.next = null;
                } else { // se remoção do meio
                    ant.next = aux.next;
                }
                count--;
                return true;
            }
            aux = aux.next; // passa para o nodo seguinte
            ant = ant.next; // passa para o nodo seguinte
        }

        return false;
    }

    /**
     * Remove o elemento de uma determinada posição da lista.
     * @param index a posição da lista
     * @return o elemento que foi removido da lista
     * @throws IndexOutOfBoundsException se (index < 0 || index >= size())
     */
    public Integer removeByIndex(int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();

        // Se remoção do primeiro
        if (index == 0) {
            Integer elemRemovido = head.element;
            head = head.next;
            if (count == 1) {// se tinha apenas um elemento na lista
                tail = null;
            }
            count--;
            return elemRemovido;
        }

        Node ant = head;
        for (int i = 0; i < index - 1; i++) {
            ant = ant.next;
        }
        Integer elemRemovido = ant.next.element;
        if (index == count - 1) { // se remoção do último
            tail = ant;
            tail.next = null;
        } else { // se remoção do meio
            ant.next = ant.next.next;
            // Alternativa para o comando acima
            // Node aux = ant.next;
            // ant.next = aux.next;
        }
        count--;
        return elemRemovido;
    }

    /**
     * Retorna o índice da primeira ocorrência do elemento na lista, ou -1 se a
     * lista não contém o elemento.
     * @param element o elemento a ser buscado
     * @return o índice da primeira ocorrência do elemento na lista, ou -1 se a
     * lista não contém o elemento
     */
    public int indexOf(Integer element) {
        Node aux = head;
        for (int i = 0; i < count; i++) {
            if (aux.element.equals(element)) {
                return i;
            }
            aux = aux.next;
        }
        return -1;
    }
}

