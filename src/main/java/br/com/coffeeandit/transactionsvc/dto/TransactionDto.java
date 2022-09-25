package br.com.coffeeandit.transactionsvc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "uuid")
@ToString(of = {"uuid", "situacao"})
public class TransactionDto {

    @Id
    private UUID uuid;
    @NotNull(message = "Informar o valor da transação")
    @Min(value = 10)
    private BigDecimal valor;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime data;
    @NotNull(message = "Informar a conta de origem da transação")
    @Valid
    private Conta conta;
    @Valid
    private BeneficiatioDto beneficiario;
    @NotNull(message = "Informar o tipo da transação")
    private TipoTransacao tipoTransacao;
    private SituacaoEnum situacao;

    public void suspeitaFraude() {
        situacao = SituacaoEnum.EM_SUSPEITA_FRAUDE;
    }

    public void analisada() {
        situacao = SituacaoEnum.ANALISADA;
    }

    public void analiseHumana() {
        situacao = SituacaoEnum.EM_ANALISE_HUMANA;
    }

    @JsonIgnore
    public boolean isAnalisada() {
        return getSituacao().equals(SituacaoEnum.ANALISADA);
    }


    public void aprovar() {
        situacao = SituacaoEnum.APROVADA;
    }
}

