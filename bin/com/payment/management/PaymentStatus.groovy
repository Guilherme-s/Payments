package com.payment.management
//import grails.plugins.i18nEnums.traits.I18nEnumTrait
//implements I18nEnumTrait
enum PaymentStatus  {
	PENDING("Pendente"),
	PAID("Paga"),
	OVERDUE("Vencida")

	final String value

	PaymentStatus(String value) { this.value = value }

	String toString() { value }
	String getKey() { name() }
}
