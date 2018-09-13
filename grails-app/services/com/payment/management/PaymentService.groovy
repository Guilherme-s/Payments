package com.payment.management

import grails.gorm.transactions.Transactional


@Transactional
class PaymentService {

    public List<Payment> list(params) {
        return Payment.query(params).list(max: params.max, offset: params.offset, sort: params.sort, order: params.order)
    }

    public Payment save(Payment payment) {
        payment.save(flush: true)
        return payment
    }

    public void delete(Long id) {
        Payment payment = Payment.get(id)
        payment.deleted = true
        payment.save(validate: false)
    }

    public void processOverduePayments() {
        List<Payment> payments = Payment.getOverduePayments().list()
        payments.each {
            p ->
                p.status = PaymentStatus.OVERDUE
                p.save(validate: false)
        }
    }

    public Boolean validateCpf(String cpf) {
        try {
            int d1, d2
            int dg1, dg2, remain
            int dgcpf

            d1 = d2 = 0
            dg1 = dg2 = remain = 0

            cpf = cpf.replace("-", "").replace(".", "")
            for (int i = 1; i < cpf.length() - 1; i++) {
                dgcpf = cpf.substring(i - 1, i) as Integer
                d1 = d1 + (11 - i) * dgcpf
                d2 = d2 + (12 - i) * dgcpf
            }

            remain = (d1 % 11)
            dg1 = (remain < 2) ? 0 : 11 - remain

            d2 += 2 * dg1
            remain = (d2 % 11)
            dg2 = (remain < 2) ? 0 : 11 - remain

            String dgverif = cpf.substring(cpf.length() - 2)
            String dgresult = (dg1 as String) + (dg2 as String)

            return dgverif == dgresult
        } catch (Exception e) {
            return false
        }
    }

}
