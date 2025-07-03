package com.codigoQR.sistemQrCode.exception;

import java.util.List;

public record ErroResposta(int status, String mensagem, List<ErroCampo> erros) {
}
