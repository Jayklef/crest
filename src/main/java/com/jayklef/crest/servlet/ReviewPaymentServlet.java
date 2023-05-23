package com.jayklef.crest.servlet;

import com.jayklef.crest.service.PaymentServices;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ReviewPaymentServlet", value = "/ReviewPaymentServlet")
public class ReviewPaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("payerId");

        try {
            PaymentServices paymentServices = new PaymentServices();
            Payment payment = paymentServices.getPaymentDetails(paymentId);
        }catch (PayPalRESTException exception){
            exception.printStackTrace();
            request.setAttribute("errorMessage", "Could not get payment details ");
        }
    }
}
