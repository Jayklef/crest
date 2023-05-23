package com.jayklef.crest.servlet;

import com.jayklef.crest.model.OrderDetail;
import com.jayklef.crest.service.PaymentServices;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AuthorizePaymentServlet", value = "/authorize_payment")
public class AuthorizePaymentServlet extends HttpServlet {

    public AuthorizePaymentServlet(){

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String product = request.getParameter("productName");
        String shipping = request.getParameter("shipping");
        String subtotal = request.getParameter("subtotal");
        String tax = request.getParameter("tax");
        String total = request.getParameter("total");

        OrderDetail orderDetail = new OrderDetail(product, shipping, subtotal, tax, total);

        try {
            PaymentServices paymentServices = new PaymentServices();
            String approvalLink = paymentServices.authorizePayment(orderDetail);

            response.sendRedirect(approvalLink);
        }catch (PayPalRESTException exception){
            exception.printStackTrace();
        }
    }
}
