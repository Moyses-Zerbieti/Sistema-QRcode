package com.codigoQR.sistemQrCode.Exception;

import java.util.List;

public record ErroResposta(int status, String mensagem, List<ErroCampo> erros) {
}
