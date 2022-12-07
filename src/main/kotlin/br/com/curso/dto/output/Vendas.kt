package br.com.curso.dto.output

import br.com.curso.dto.input.VendaInput

data class Vendas(
    val cliente:String,
    val veiculo:Veiculo,
    val venda: VendaInput,
    val parcelas:List<Parcelas>
)
