package com.ryan.servicepayment.services;

import com.ryan.servicepayment.dto.ContaRequest;
import com.ryan.servicepayment.model.Conta;
import com.ryan.servicepayment.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private ContaService contaService;

    @Test
    void deveCriarConta() {

        ContaRequest request = new ContaRequest(
                "Ryan Silva",
                "12345678900",
                new BigDecimal("1000.00"),
                new BigDecimal("500.00")
        );

        Conta contaMock = new Conta();
        contaMock.setId("123");
        contaMock.setNomeCompleto("Ryan Silva");
        contaMock.setCpf("12345678900");
        contaMock.setSaldoAtual(new BigDecimal("1000.00"));
        contaMock.setLimiteAtual(new BigDecimal("500.00"));

        when(contaRepository.save(any(Conta.class))).thenReturn(contaMock);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);


        Conta resultado = contaService.criarConta(request);


        assertNotNull(resultado);
        assertEquals("Ryan Silva", resultado.getNomeCompleto());
        assertEquals("12345678900", resultado.getCpf());
        verify(contaRepository).save(any(Conta.class));
        verify(valueOperations).set(anyString(), any());
    }

    @Test
    void deveListarTodasContas() {

        Conta conta1 = new Conta();
        conta1.setId("1");
        Conta conta2 = new Conta();
        conta2.setId("2");

        when(contaRepository.findAll()).thenReturn(Arrays.asList(conta1, conta2));


        List<Conta> resultado = contaService.listarTodasContas();


        assertEquals(2, resultado.size());
        verify(contaRepository).findAll();
    }

    @Test
    void deveBuscarPorCpf() {

        Conta conta = new Conta();
        conta.setCpf("12345678900");
        conta.setNomeCompleto("Ryan Silva");


        when(contaRepository.findByCpf("12345678900"))
                .thenReturn(Arrays.asList(conta));


        Conta resultado = contaService.buscarPorCpf("12345678900");


        assertNotNull(resultado);
        assertEquals("12345678900", resultado.getCpf());
        verify(contaRepository).findByCpf("12345678900");
    }

    @Test
    void deveDeletarConta() {
        // Arrange
        String id = "123";
        doNothing().when(contaRepository).deleteById(id);
        when(redisTemplate.delete(anyString())).thenReturn(true);

        // Act
        contaService.deletarConta(id);

        // Assert
        verify(contaRepository).deleteById(id);
        verify(redisTemplate).delete("balanceamento:123");
    }
}