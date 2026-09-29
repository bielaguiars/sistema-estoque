import java.util.Locale;

/**
 * Classe abstrata base para todos os produtos do estoque.
 * Não pode ser instanciada diretamente: cada subclasse define
 * como o seu valor total é calculado.
 */
public abstract class Product implements Vendavel {

    private String nome;
    private double preco;
    private int quantidade;

    public Product(String nome, double preco, int quantidade) throws QuantidadeInvalidaException {
        if (preco < 0) {
            throw new QuantidadeInvalidaException(
                "Preço inválido para \"" + nome + "\": " + preco + " (não pode ser negativo).");
        }
        if (quantidade < 0) {
            throw new QuantidadeInvalidaException(
                "Quantidade inválida para \"" + nome + "\": " + quantidade + " (não pode ser negativa).");
        }
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // Método abstrato: cada subclasse decide como calcular
    public abstract double calcularValorTotal();

    // Método concreto: pode ser sobrescrito pelas subclasses
    public String getDescricao() {
        return String.format(Locale.of("pt", "BR"),
            "%-16s | Preço: R$ %6.2f | Qtd: %3d", nome, preco, quantidade);
    }

    // Implementação da interface Vendavel
    @Override
    public void vender(int quantidadeDesejada) throws ProdutoIndisponivelException {
        if (quantidadeDesejada <= 0) {
            throw new IllegalArgumentException("A quantidade vendida deve ser maior que zero.");
        }
        if (quantidadeDesejada > quantidade) {
            throw new ProdutoIndisponivelException(
                "Estoque insuficiente de \"" + nome + "\": pedido de " + quantidadeDesejada
                + " unidade(s), mas só há " + quantidade + " disponível(is).");
        }
        quantidade -= quantidadeDesejada;
    }

    // Sobrecarga (polimorfismo estático), versão 1: desconto percentual simples
    public void aplicarDesconto(double percentual) {
        validarPercentual(percentual);
        preco -= preco * percentual / 100.0;
    }

    // Sobrecarga (polimorfismo estático), versão 2: desconto percentual limitado a um valor máximo em R$
    public void aplicarDesconto(double percentual, double descontoMaximo) {
        validarPercentual(percentual);
        if (descontoMaximo < 0) {
            throw new IllegalArgumentException("O desconto máximo não pode ser negativo.");
        }
        double desconto = Math.min(preco * percentual / 100.0, descontoMaximo);
        preco -= desconto;
    }

    private void validarPercentual(double percentual) {
        if (percentual < 0 || percentual > 100) {
            throw new IllegalArgumentException("Percentual de desconto deve estar entre 0 e 100.");
        }
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
