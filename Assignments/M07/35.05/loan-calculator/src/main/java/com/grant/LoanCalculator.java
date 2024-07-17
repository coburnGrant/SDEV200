package com.grant;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet
public class LoanCalculator extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoanCalculator() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double loanAmount = Double.parseDouble(request.getParameter("loanAmount"));
        double annualInterestRate =
        Double.parseDouble(request.getParameter("annualInterestRate"));
        int numberOfYears = Integer.parseInt(request.getParameter("numberOfYears"));

        Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);
        double monthlyPayment = loan.getMonthlyPayment();
        double totalPayment = loan.getTotalPayment();

        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h2>Loan Details</h2>");
        response.getWriter().println("<p>Loan Amount: " + loanAmount + "</p>");
        response.getWriter().println("<p>Annual Interest Rate: " + annualInterestRate
        + "</p>");
        response.getWriter().println("<p>Number of Years: " + numberOfYears +
        "</p>");
        response.getWriter().println("<p>Monthly Payment: " + monthlyPayment +
        "</p>");
        response.getWriter().println("<p>Total Payment: " + totalPayment + "</p>");
        response.getWriter().println("</body></html>");
    }
}
