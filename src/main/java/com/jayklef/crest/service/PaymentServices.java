package com.jayklef.crest.service;

import com.jayklef.crest.model.OrderDetail;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.RedirectUrls;

public class PaymentServices {

    private static final String CLIENT_ID = "AbBvXQvD-6GKiOP91M1GksU2z0YGCpLpXCWIJyphwfx9iO95l2TghTVzKy3cTmJbuQ4cm2JQp1Ooa6Ov";
    private static final String CLIENT_SECRET = "EE1f2qkQ82yz6p8JGw3quMkBFozU4pWZqrX0uj5ukDogHddPtBEtO5xgnTZ-axwcNClT-Lv2ARr3G7rJ";
    private static final String MODE = "sandbox";

    public String authorizePayment(OrderDetail orderDetail){
        Payer payer = getPayerInformation();

        RedirectUrls redirectUrls = getRedirectUrls();
        return null;
    }

    private RedirectUrls getRedirectUrls(){
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:63342/crest/templates/cancel.html");
        redirectUrls.setReturnUrl("http://localhost:63342/crest/templates/review_payment");

        return redirectUrls;
    }

    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("Jude")
                 .setLastName("Okoye")
                .setEmail("jude.okoye@gmail.com");

        payer.setPayerInfo(payerInfo);
        return payer;
    }
}
