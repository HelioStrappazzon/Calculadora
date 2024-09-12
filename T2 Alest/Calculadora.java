import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Calculadora {

    private int maxPilhaSize;

    public Calculadora() {
        maxPilhaSize = 0;
    }

    public double avaliarExpressao(String expressao) throws Exception {
        Pilha operandos = new Pilha();
        Pilha operadores = new Pilha();
        Stack<Character> delimitadores = new Stack<>();

        for (int i = 0; i < expressao.length(); i++) {
            char c = expressao.charAt(i);

            if (Character.isDigit(c)) {
                int num = 0;
                while (i < expressao.length() && Character.isDigit(expressao.charAt(i))) {
                    num = num * 10 + (expressao.charAt(i) - '0');
                    i++;
                }
                i--;
                operandos.push(num);
                updateMaxPilhaSize(operandos.size());
            } else if (c == '(' || c == '[' || c == '{') {
                delimitadores.push(c);
                operadores.push((int) c);
            } else if (c == ')' || c == ']' || c == '}') {
                while (!operadores.isEmpty() && !isMatchingDelimiter((char) operadores.top().intValue(), c)) {
                    processarOperador(operandos, (char) operadores.pop().intValue());
                }
                if (!operadores.isEmpty() && isMatchingDelimiter((char) operadores.top().intValue(), c)) {
                    operadores.pop();
                    delimitadores.pop();
                } else {
                    throw new Exception("Erro de sintaxe: delimitador " + c + " sem correspondência.");
                }
            } else if (isOperador(c)) {
                while (!operadores.isEmpty() && precedencia((char) operadores.top().intValue()) >= precedencia(c)) {
                    processarOperador(operandos, (char) operadores.pop().intValue());
                }
                operadores.push((int) c);
            }
        }

        while (!operadores.isEmpty()) {
            processarOperador(operandos, (char) operadores.pop().intValue());
        }

        if (operandos.size() != 1) {
            throw new Exception("Erro de sintaxe: expressão inválida.");
        }

        return operandos.pop();
    }

    private void processarOperador(Pilha operandos, char operador) throws Exception {
        if (operandos.size() < 2) {
            throw new Exception("Erro de sintaxe: operandos insuficientes.");
        }
        double b = operandos.pop();
        double a = operandos.pop();
        double resultado = 0;

        switch (operador) {
            case '+':
                resultado = a + b;
                break;
            case '-':
                resultado = a - b;
                break;
            case '*':
                resultado = a * b;
                break;
            case '/':
                if (b == 0) {
                    throw new Exception("Erro de sintaxe: divisão por zero.");
                }
                resultado = a / b;
                break;
            case '^':
                resultado = Math.pow(a, b);
                break;
            default:
                throw new Exception("Erro de sintaxe: operador inválido.");
        }

        operandos.push((int) resultado);
        updateMaxPilhaSize(operandos.size());
    }

    private boolean isOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    private int precedencia(char operador) {
        switch (operador) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    private boolean isMatchingDelimiter(char open, char close) {
        return (open == '(' && close == ')') || (open == '[' && close == ']') || (open == '{' && close == '}');
    }

    private void updateMaxPilhaSize(int size) {
        if (size > maxPilhaSize) {
            maxPilhaSize = size;
        }
    }

    public int getMaxPilhaSize() {
        return maxPilhaSize;
    }

    public void resetMaxPilhaSize() {
        maxPilhaSize = 0;
    }

    public void processarArquivo(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                try {
                    double resultado = avaliarExpressao(linha);
                    System.out.println("Expressão: " + linha);
                    System.out.println("Resultado: " + resultado);
                } catch (Exception e) {
                    System.out.println("Expressão: " + linha);
                    System.out.println("Erro: " + e.getMessage());
                }
                System.out.println("Tamanho máximo da pilha: " + getMaxPilhaSize());
                resetMaxPilhaSize();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}