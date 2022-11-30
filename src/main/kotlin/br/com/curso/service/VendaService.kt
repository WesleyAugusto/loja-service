package br.com.curso.service

import br.com.curso.dto.input.VendaInput
import br.com.curso.dto.output.Parcelas
import br.com.curso.dto.output.Vendas
import br.com.curso.http.VeiculoHttp
import jakarta.inject.Singleton
import java.time.LocalDate


@Singleton
class VendaService(private val veiculoService: VeiculoService) {

    fun realizarVenda(vendaInput: VendaInput) {
        val veiculo = veiculoService.getVeiculo(vendaInput.veiculo)
        val parcelas: ArrayList<Parcelas> = arrayListOf()
        val valorParcelas = vendaInput.valor.divide(vendaInput.quantidadedeParcelas.toBigDecimal())
        var dataVenciemto = LocalDate.now().plusMonths(1)

        for (i in 1..vendaInput.quantidadedeParcelas) {
            val parcela = Parcelas(valorParcelas, dataVenciemto.toString())
            parcelas.add(parcela)
            dataVenciemto = dataVenciemto.plusMonths(1)
        }
        val venda = Vendas(vendaInput.cliente, veiculo, vendaInput, parcelas)
            println(venda)
    }
}