package br.com.curso.service

import br.com.curso.dto.input.VendaInput
import br.com.curso.dto.output.Parcelas
import br.com.curso.dto.output.Vendas
import br.com.curso.http.VeiculoHttp
import jakarta.inject.Singleton
import java.time.LocalDate


@Singleton
class VendaService(private val veiculoHttp: VeiculoHttp) {

    fun realizarVenda(vendaInput: VendaInput){
        val veiculo = veiculoHttp.findById(vendaInput.veiculo)
        var parcelas:List<Parcelas> = ArrayList<Parcelas>()
        var valorParcelas = vendaInput.valor.divide(vendaInput.quantidadedeParcelas.toBigDecimal())
        var dataVenciemto = LocalDate.now().plusMonths(1)

        for (i in 1..vendaInput.quantidadedeParcelas){
            var parcela = Parcelas(valorParcelas, dataVenciemto.toString())
            parcelas.plus(parcela)
            dataVenciemto = dataVenciemto.plusMonths(1)
        }
        var venda = Vendas(vendaInput.cliente, veiculo, vendaInput, parcelas)
        println(venda)


    }
}