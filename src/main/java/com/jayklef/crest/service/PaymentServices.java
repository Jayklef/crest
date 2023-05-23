package com.jayklef.crest.service;

import com.jayklef.crest.model.OrderDetail;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import java.util.ArrayList;
import java.util.List;

public class PaymentServices {

    private static final String CLIENT_ID = "AbBvXQvD-6GKiOP91M1GksU2z0YGCpLpXCWIJyphwfx9iO95l2TghTVzKy3cTmJbuQ4cm2JQp1Ooa6Ov";
    private static final String CLIENT_SECRET = "EE1f2qkQ82yz6p8JGw3quMkBFozU4pWZqrX0uj5ukDogHddPtBEtO5xgnTZ-axwcNClT-Lv2ARr3G7rJ";
    private static final String MODE = "sandbox";

    public String authorizePayment(OrderDetail orderDetail) throws PayPalRESTException {
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectUrls();
        List<Transaction> transactions = getTransactionInformation(orderDetail);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(transactions)
                      .setRedirectUrls(redirectUrls)
                      .setPayer(payer)
                      .setIntent("authorize");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        Payment approvedPayment = requestPayment.create(apiContext);

        System.out.println(approvedPayment);

        return getApprovalLinks(approvedPayment);
    }

    private String getApprovalLinks(Payment approvedPayment){
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links){
            if (link.getRel().equalsIgnoreCase("approval_Url")){
                approvalLink = link.getHref();
            }
        }

        return approvalLink;
    }

    private RedirectUrls getRedirectUrls(){
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:63342/crest/templates/cancel.html");
        redirectUrls.setReturnUrl("http://localhost:63342/crest/templates/review_payment");

        return redirectUrls;
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }

    private List<Transaction> getTransactionInformation(OrderDetail orderDetail){
        Details details = new Details();
        details.setShipping(orderDetail.getShipping());
        details.setSubtotal(orderDetail.getSubtotal());
        details.setTax(orderDetail.getTax());

        Amount amount = new Amount();
        amount.setCurrency("NGN");
        amount.setTotal(orderDetail.getTotal());
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(orderDetail.getProductName());

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setCurrency("NGN");
        item.setName(orderDetail.getProductName());
        item.setPrice(orderDetail.getTotal());
        item.setTax(orderDetail.getTax());
        item.setQuantity("");

        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        return transactions;
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
