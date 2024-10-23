package ecom.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecom.demo.models.Payment;
import ecom.demo.repository.PaymentRepo;

@Service
public class PaymentService {

    private final PaymentRepo paymentRepo;

    @Autowired
    public PaymentService(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public int addPayment(Payment payment) {
        return paymentRepo.addPayment(payment);
    }

    public Payment getPaymentById(String paymentID) {
        return paymentRepo.getPaymentById(paymentID);
    }

    public List<Payment> getAllPayments() {
        return paymentRepo.getAllPayments();
    }

    public int updatePayment(Payment payment) {
        return paymentRepo.updatePayment(payment);
    }

    public int deletePayment(String paymentID) {
        return paymentRepo.deletePayment(paymentID);
    }
}
