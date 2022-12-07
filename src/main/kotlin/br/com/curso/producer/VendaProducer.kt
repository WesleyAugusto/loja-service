package br.com.curso.producer

import br.com.curso.dto.output.Vendas
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient
interface VendaProducer {

    @Topic("ms-vendas")
    fun publicarVenda(@KafkaKey id:String, vendas:Vendas){

    }
}