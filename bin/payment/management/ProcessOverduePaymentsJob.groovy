package payment.management

import com.payment.management.PaymentService

class ProcessOverduePaymentsJob {

    PaymentService paymentService;

    static triggers = {
        cron name: 'cronTrigger', cronExpression: '0 0 8 ? * MON-FRI'
    }

    def execute() {
        paymentService.processOverduePayments()
    }
}
