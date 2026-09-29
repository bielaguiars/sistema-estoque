import java.util.ArrayList;
import java.util.List;

/**
 * Composição: o Estoque TEM UMA lista de produtos (não herda de Product).
 */
public class Estoque {

    private final List<Product> produtos = new ArrayList<>();

    public void adicionarProduto(Product p) {
        produtos.add(p);
    }

    public void venderProduto(int indice, int quantidade) throws ProdutoIndisponivelException {
        if (indice < 0 || indice >= produtos.size()) {
            throw new ProdutoIndisponivelException("Não existe produto no índice " + indice + ".");
        }
        produtos.get(indice).vender(quantidade); // propaga a exceção, se ocorrer
    }

    public double calcularValorTotalEstoque() {
        double total = 0;
        for (Product p : produtos) {
            total += p.calcularValorTotal(); // polimorfismo dinâmico: cada subtipo calcula do seu jeito
        }
        return total;
    }

    public List<Product> getProdutos() {
        return List.copyOf(produtos);
    }
}
