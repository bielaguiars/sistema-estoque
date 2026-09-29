/**
 * Produto com validade: recebe 20% de desconto automático
 * no valor total quando faltam 3 dias ou menos para vencer.
 */
public class ProdutoPerecivel extends Product {

    private static final int LIMITE_DIAS_DESCONTO = 3;
    private static final double DESCONTO_VENCIMENTO = 0.20;

    private int diasParaVencer;

    public ProdutoPerecivel(String nome, double preco, int quantidade, int diasParaVencer)
            throws QuantidadeInvalidaException {
        super(nome, preco, quantidade);
        this.diasParaVencer = diasParaVencer;
    }

    public boolean isProximoDoVencimento() {
        return diasParaVencer <= LIMITE_DIAS_DESCONTO;
    }

    @Override
    public double calcularValorTotal() {
        double total = getPreco() * getQuantidade();
        if (isProximoDoVencimento()) {
            total -= total * DESCONTO_VENCIMENTO;
        }
        return total;
    }

    @Override
    public String getDescricao() {
        String validade = "Vence em " + diasParaVencer + " dia(s)";
        if (isProximoDoVencimento()) {
            validade += " (20% OFF)";
        }
        return super.getDescricao() + " | " + validade;
    }

    public int getDiasParaVencer() {
        return diasParaVencer;
    }
}
