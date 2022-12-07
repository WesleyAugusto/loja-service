package br.com.curso.service

import br.com.curso.dto.input.VendaInput
import br.com.curso.dto.output.Parcelas
import br.com.curso.dto.output.Vendas
import br.com.curso.producer.VendaProducer
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.*


@Singleton
class VendaService(
    private val veiculoService: VeiculoService,
    private val vendaProducer: VendaProducer,
) {

    fun realizarVenda(vendaInput: VendaInput): Vendas {
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

        vendaProducer.publicarVenda(UUID.randomUUID().toString(), venda)

        return venda;
    }

}