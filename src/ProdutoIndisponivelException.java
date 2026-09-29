/**
 * Lançada quando se tenta vender mais unidades do que há em estoque.
 */
public class ProdutoIndisponivelException extends EstoqueException {
    public ProdutoIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
