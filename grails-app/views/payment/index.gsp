<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'payment.label', default: 'Payment')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-payment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="list" href="${createLink(uri: '/')}"><g:message code="default.listAll.label" args="[entityName]"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-payment" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:form name="frmPayment" model="Payment" action="index" controller="Payment">
               <div class="fieldcontain">
                   <label for="name">Nome/e-mail</label>
                    <g:textField name="name" value="${params.name}" />
               </div>
                <div class="fieldcontain">
               <label for="paymentMethod">Forma de pagamento</label>
                    <g:select name="paymentMethod" from="${com.payment.management.PaymentMethod.values()}" noSelection="['':'']" optionKey="key" value="${params.paymentMethod}"/>
                </div>
                <div class="fieldcontain">
                    <label for="status">Status</label>
                    <g:select name="status" from="${com.payment.management.PaymentStatus.values()}" noSelection="['':'']" optionKey="key" value="${params.status}"/>
                </div>
                <div class="fieldcontain">
                   <label for="startDueDate">Data de vencimento inicial</label>
                    <g:datePicker name="startDueDate" value="${params.startDueDate}" precision="day"/>
                </div>
                <div class="fieldcontain">
                    <label for="endDueDate">Data de vencimento final</label>
                    <g:datePicker name="endDueDate" value="${params.endDueDate}" precision="day"/>
                </div>
                <div class="btnCenter">
                    <input type="submit" value="Buscar"/>
                </div>
            </g:form>


            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${paymentList}" />
            <div class="paginate">
                <g:paginate total="${paymentCount ?: 0}"
                            controller="payment" action="index"
                            max="${max ?: 10}" offset="${offset ?: 0}" />
            </div>
        </div>
    </body>
</html>