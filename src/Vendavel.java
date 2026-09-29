/**
 * Contrato para tudo que pode ser vendido.
 */
public interface Vendavel {
    void vender(int quantidadeDesejada)
        throws ProdutoIndisponivelException;
}
