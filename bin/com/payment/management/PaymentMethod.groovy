package com.payment.management

enum PaymentMethod  {
    BILLET ("Boleto Bancário"),
    CREDIT_CARD ("Cartão de crédito")

    final String value

    PaymentMethod(String value) { this.value = value }

    String toString() { value }
    String getKey() { name() }
}
