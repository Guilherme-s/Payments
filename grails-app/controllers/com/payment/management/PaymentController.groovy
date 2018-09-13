package com.payment.management

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class PaymentController {

    PaymentService paymentService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
        List<Payment> paymentList = paymentService.list(params)
        respond paymentList, model: [paymentCount: paymentList.totalCount]
    }

    def show(Long id) {
        respond Payment.get(id)
    }

    def create() {
        respond new Payment(params)
    }

    def save(Payment payment) {
        if (payment == null) {
            notFound()
            return
        }
        payment.validate()
        if (payment.hasErrors()) {
            respond payment.errors, view: 'create'
            return
        }

        try {
            paymentService.save(payment)
        } catch (ValidationException e) {
            respond payment.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'payment.label', default: 'Payment'), payment.id])
                redirect payment
            }
            '*' { respond payment, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond Payment.get(id)
    }

    def update(Payment payment) {
        if (payment == null) {
            notFound()
            return
        }
        payment.validate()
        if (payment.hasErrors()) {
            respond payment.errors, view: 'edit'
            return
        }

        try {
            paymentService.save(payment)
        } catch (ValidationException e) {
            respond payment.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'payment.label', default: 'Payment'), payment.id])
                redirect payment
            }
            '*' { respond payment, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }
        paymentService.delete(id)


        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'payment.label', default: 'Payment'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'payment.label', default: 'Payment'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
