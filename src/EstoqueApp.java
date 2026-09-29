import java.util.List;
import java.util.Locale;

/**
 * Programa principal: demonstra cadastro, vendas, exceções e o
 * cálculo polimórfico do valor total do estoque.
 */
public class EstoqueApp {

    private static final Locale PT_BR = Locale.of("pt", "BR");

    public static void main(String[] args) {
        Estoque estoque = new Estoque();

        // 1) Cadastro de produtos válidos
        titulo("1. Cadastro de produtos");
        try {
            estoque.adicionarProduto(new ProdutoComum("Caderno", 15.90, 40));
            estoque.adicionarProduto(new ProdutoComum("Caneta azul", 2.50, 100));
            estoque.adicionarProduto(new ProdutoPerecivel("Iogurte natural", 4.80, 30, 2)); // <= 3 dias: 20% OFF
            estoque.adicionarProduto(new ProdutoPerecivel("Queijo minas", 32.00, 10, 12));
            System.out.println("4 produtos cadastrados com sucesso.");
        } catch (QuantidadeInvalidaException e) {
            System.out.println("Erro inesperado no cadastro: " + e.getMessage());
        }
        listar(estoque);
        System.out.println("Valor total do estoque: " + moeda(estoque.calcularValorTotalEstoque()));

        // 2) Tentativa de cadastro com quantidade negativa
        titulo("2. Cadastro inválido (quantidade negativa)");
        try {
            estoque.adicionarProduto(new ProdutoComum("Borracha", 1.20, -5));
            System.out.println("Produto cadastrado (não deveria acontecer).");
        } catch (QuantidadeInvalidaException e) {
            System.out.println("[QuantidadeInvalidaException capturada] " + e.getMessage());
        }

        // 3) Venda válida
        titulo("3. Venda válida");
        try {
            estoque.venderProduto(0, 10);
            System.out.println("Venda realizada: 10 x Caderno. Estoque restante: "
                + estoque.getProdutos().get(0).getQuantidade());
        } catch (ProdutoIndisponivelException e) {
            System.out.println("[ProdutoIndisponivelException capturada] " + e.getMessage());
        }

        // 4) Venda acima do disponível
        titulo("4. Venda acima do estoque disponível");
        try {
            estoque.venderProduto(3, 50);
            System.out.println("Venda realizada (não deveria acontecer).");
        } catch (ProdutoIndisponivelException e) {
            System.out.println("[ProdutoIndisponivelException capturada] " + e.getMessage());
        }

        // 5) Sobrecarga de aplicarDesconto (polimorfismo estático)
        titulo("5. Sobrecarga: aplicarDesconto()");
        List<Product> produtos = estoque.getProdutos();
        Product caneta = produtos.get(1);
        Product queijo = produtos.get(3);
        caneta.aplicarDesconto(10);        // versão (double)
        queijo.aplicarDesconto(30, 5.00);  // versão (double, double): 30% limitado a R$ 5,00
        System.out.println("Caneta azul com 10% de desconto -> " + moeda(caneta.getPreco()));
        System.out.println("Queijo minas com 30% (máx. R$ 5,00) -> " + moeda(queijo.getPreco()));

        // 6) Todas as exceções no mesmo try: da mais específica para a mais genérica
        titulo("6. Captura com hierarquia de exceções");
        try {
            estoque.adicionarProduto(new ProdutoPerecivel("Leite", -3.99, 20, 5));
            estoque.venderProduto(2, 5);
            registrarOperacao(estoque);
        } catch (QuantidadeInvalidaException e) {
            System.out.println("[QuantidadeInvalidaException capturada] " + e.getMessage());
        } catch (ProdutoIndisponivelException e) {
            System.out.println("[ProdutoIndisponivelException capturada] " + e.getMessage());
        } catch (EstoqueException e) {
            // O catch mais genérico vem por último; antes dos outros, eles ficariam inalcançáveis
            System.out.println("[EstoqueException capturada] " + e.getMessage());
        }

        // 7) Estado final e valor total (polimorfismo dinâmico)
        titulo("7. Estoque final");
        listar(estoque);
        System.out.println();
        System.out.println("VALOR TOTAL DO ESTOQUE: " + moeda(estoque.calcularValorTotalEstoque()));
    }

    // Declara a exceção genérica: qualquer erro do domínio pode sair daqui
    private static void registrarOperacao(Estoque estoque) throws EstoqueException {
        if (estoque.getProdutos().isEmpty()) {
            throw new EstoqueException("Nenhum produto cadastrado para registrar.");
        }
        System.out.println("Operação registrada.");
    }

    private static void listar(Estoque estoque) {
        List<Product> produtos = estoque.getProdutos();
        for (int i = 0; i < produtos.size(); i++) {
            Product p = produtos.get(i);
            System.out.printf("[%d] %s | Total: %s%n", i, p.getDescricao(), moeda(p.calcularValorTotal()));
        }
    }

    private static String moeda(double valor) {
        return String.format(PT_BR, "R$ %.2f", valor);
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }
}
