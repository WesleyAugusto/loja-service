package br.com.curso.service

import br.com.curso.dto.input.VendaInput
import br.com.curso.dto.output.Parcelas
import br.com.curso.dto.output.Vendas
import br.com.curso.http.VeiculoHttp
import br.com.curso.producer.VendaProducer
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.UUID


@Singleton
class VendaService(
    private val veiculoService: VeiculoService,
    private  val vendaProducer: VendaProducer,
    private val objectMapper: ObjectMapper
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
            confirmaVenda(venda)
        return venda;
    }

    fun confirmaVenda(venda:Vendas){
        var vendaJSON = objectMapper.writeValueAsString(venda)
        vendaProducer.publicarVenda(UUID.randomUUID().toString(), vendaJSON)

    }
}