package com.payment.management

import javax.persistence.EnumType
import javax.persistence.Enumerated

class Payment {

 	String customerName

	String email

	String cpf

    String description

	Date dueDate

	BigDecimal value

	Boolean deleted = false

	@Enumerated(EnumType.STRING)
	PaymentMethod paymentMethod = PaymentMethod.BILLET

	PaymentStatus status = PaymentStatus.PENDING

	PaymentService paymentService

	static transients = ['paymentService']

    static constraints = {
    	customerName nullable:false, blank: false
        cpf nullable: true, blank: true,validator: { val, obj, errors -> if(val && !obj.paymentService.validateCpf(val)) errors.rejectValue('cpf', 'payment.invalid.cpf.message')}
        email email: true, nullable: true, blank: true
    	value nullable: false, blank: false, min: 5.00
		paymentMethod nullable: false, blank: false
		status nullable: false, blank: false
		dueDate nullable: false, blank: false, min: new Date().clearTime()
        description nullable: false, blank: false
	}

	static mapping = {
		autowire true
	}

	static namedQueries = {
        query { Map search ->

			eq("deleted", false)
        	if (search.containsKey("name")) {
				or {
					like 'customerName', "%" + search.name + "%"
					like 'email', "%" + search.name + "%"
				}
			}

			if(search.containsKey("paymentMethod") && search.paymentMethod){
				eq("paymentMethod", search.paymentMethod as PaymentMethod)
			}

			if(search.containsKey("status") && search.status){
				eq("status", search.status as PaymentStatus)
			}

            if(search.containsKey("startDueDate")){
               between("dueDate", search.startDueDate, search.endDueDate)
            }

		}

		paid { Map search ->
			eq("status", PaymentStatus.PAID)

			query(search)
		}

		getOverduePayments{
			Date dateNow = new Date()
			lt("dueDate", dateNow )
			eq("status", PaymentStatus.PENDING)
			eq("deleted", false)
		}
    }
}
