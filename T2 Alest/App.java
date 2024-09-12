public class App {
    public static void main(String[] args) {
        Calculadora calculadora = new Calculadora();
        String caminhoArquivo = "expressoes.txt";
        calculadora.processarArquivo(caminhoArquivo);
    }
}