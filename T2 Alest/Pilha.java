import java.util.EmptyStackException;

public class Pilha {

    private LinkedListOfInteger pilha;

    public Pilha() {
        pilha = new LinkedListOfInteger();
    }

    // Insere no topo da pilha
    public void push(Integer e) {
        pilha.add(0, e); // O topo da pilha é o início da lista
    }

    // Remove e retorna o elemento do topo da pilha
    public Integer pop() {
        // Primeiro verifica se a pilha não está vazia
        if (pilha.isEmpty())
            throw new EmptyStackException();
        return pilha.removeByIndex(0);
    }

    public Integer top() {
        // Primeiro verifica se a pilha não está vazia
        if (pilha.isEmpty())
            throw new EmptyStackException();
        return pilha.get(0);
    }

    public int size() {
        return pilha.size();
    }

    public boolean isEmpty() {
        return pilha.isEmpty();
    }

    public void clear() {
        pilha.clear();
    }
}