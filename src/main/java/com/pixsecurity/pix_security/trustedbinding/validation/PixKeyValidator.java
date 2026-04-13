package com.pixsecurity.pix_security.trustedbinding.validation;

// Classe para validação dos dados WIFI e chave PIX
public class PixKeyValidator {

    // Validação do WIFI no formato MAC Address
    public static boolean isValidMacAddress(String value) { // Valida se o value recebido está no formato correto
        return value != null // Value não pode ser vazio
                && value.matches("^([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$"); // E tem que seguir este formato (ex MAC Adress: AA:BB:CC:DD:EE:FF)
    }

    // Validação chave PIX formatos CPF, CNPJ, Email, Telefone, chave aleatória
    public static boolean isValidPixContact(String value) {
        return isValidCpf(value)
                || isValidCnpj(value)
                || isValidEmail(value)
                || isValidPhone(value)
                || isValidUuid(value);
    }

    public static boolean isValidCpf(String value) { // Validação do CPF
        return value != null && value.matches("^\\d{11}$"); // value não pode ser vazio e deverá possuir 11 digitos
    }

    public static boolean isValidCnpj(String value) { // Validação do CNPJ
        return value != null && value.matches("^\\d{14}$"); // value não pode ser vazio e deverá possuir 14 digitos
    }

    public static boolean isValidEmail(String value) { // Validação de email
        return value != null && value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"); // value não pode ser vazio e deverá seguir este formato
    }

    public static boolean isValidPhone(String value) { // Validação de telefone
        return value != null && value.matches("^\\+55\\d{10,11}$"); // value não pode ser vazio e deverá seguir este formato (+55 e depois 10 ou 11 digitos)
    }

    public static boolean isValidUuid(String value) { // Validação de chave aleatória
        return value != null && value.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        // value não pode ser vazio e deverá seguir este formato
    }
}
