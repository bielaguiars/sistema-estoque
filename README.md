# Sistema de Estoque de Produtos (Java)

Exercício da 1ª avaliação: classes abstratas, herança, interfaces, polimorfismo, composição e exceções.

## Como compilar e executar

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out EstoqueApp
```

## Estrutura

| Arquivo | Papel | Conceito |
|---|---|---|
| `EstoqueException` | Exceção base (checked) | Hierarquia de exceções |
| `QuantidadeInvalidaException` | Preço ou quantidade negativos | Herança de exceção |
| `ProdutoIndisponivelException` | Venda acima do estoque | Herança de exceção |
| `Vendavel` | Contrato `vender(int)` | Interface |
| `Product` | Classe abstrata com atributos `private`, `calcularValorTotal()` abstrato, `getDescricao()` concreto, `aplicarDesconto` sobrecarregado | Abstração, encapsulamento, polimorfismo estático |
| `ProdutoComum` | `preco × quantidade` | Herança, `@Override` |
| `ProdutoPerecivel` | 20% de desconto se `diasParaVencer <= 3`; descrição com validade | Herança, polimorfismo dinâmico |
| `Estoque` | **Tem uma** `List<Product>` | Composição |
| `EstoqueApp` | `main()` com todos os cenários | Tratamento de exceções |

### Decisões
- `aplicarDesconto(percentual)` reduz o preço unitário pelo percentual; `aplicarDesconto(percentual, descontoMaximo)` faz o mesmo, mas limita o desconto a um valor máximo em R$.
- `venderProduto` com índice inexistente também lança `ProdutoIndisponivelException`.
- No passo 6 os três `catch` aparecem no mesmo `try`, com `EstoqueException` por último (o mais genérico).

## Saída do programa

```
=== 1. Cadastro de produtos ===
4 produtos cadastrados com sucesso.
[0] Caderno          | Preço: R$  15,90 | Qtd:  40 | Total: R$ 636,00
[1] Caneta azul      | Preço: R$   2,50 | Qtd: 100 | Total: R$ 250,00
[2] Iogurte natural  | Preço: R$   4,80 | Qtd:  30 | Vence em 2 dia(s) (20% OFF) | Total: R$ 115,20
[3] Queijo minas     | Preço: R$  32,00 | Qtd:  10 | Vence em 12 dia(s) | Total: R$ 320,00
Valor total do estoque: R$ 1321,20

=== 2. Cadastro inválido (quantidade negativa) ===
[QuantidadeInvalidaException capturada] Quantidade inválida para "Borracha": -5 (não pode ser negativa).

=== 3. Venda válida ===
Venda realizada: 10 x Caderno. Estoque restante: 30

=== 4. Venda acima do estoque disponível ===
[ProdutoIndisponivelException capturada] Estoque insuficiente de "Queijo minas": pedido de 50 unidade(s), mas só há 10 disponível(is).

=== 5. Sobrecarga: aplicarDesconto() ===
Caneta azul com 10% de desconto -> R$ 2,25
Queijo minas com 30% (máx. R$ 5,00) -> R$ 27,00

=== 6. Captura com hierarquia de exceções ===
[QuantidadeInvalidaException capturada] Preço inválido para "Leite": -3.99 (não pode ser negativo).

=== 7. Estoque final ===
[0] Caderno          | Preço: R$  15,90 | Qtd:  30 | Total: R$ 477,00
[1] Caneta azul      | Preço: R$   2,25 | Qtd: 100 | Total: R$ 225,00
[2] Iogurte natural  | Preço: R$   4,80 | Qtd:  30 | Vence em 2 dia(s) (20% OFF) | Total: R$ 115,20
[3] Queijo minas     | Preço: R$  27,00 | Qtd:  10 | Vence em 12 dia(s) | Total: R$ 270,00

VALOR TOTAL DO ESTOQUE: R$ 1087,20
```
