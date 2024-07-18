<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.grant.AdditionQuestion" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz Results</title>
</head>
<body>
    <h2>Quiz Results</h2>
    <%
        int numQuestions = 5;
        int correctAnswers = 0;

        for (int i = 1; i <= numQuestions; i++) {
            String answerStr = request.getParameter("answer_" + i);
            int userAnswer = Integer.parseInt(answerStr);

            AdditionQuestion question = (AdditionQuestion) session.getAttribute("question_" + i);
            
            if (question != null) {
                int correctAnswer = question.getAnswer();

                out.println("<p>" + i + ": " + question.getQuestion() + userAnswer + " " + question.getResultString(userAnswer) + "</p>");
            } else {
                out.println("<p>Question " + i + " could not be retrieved.</p>");
            }
        }

        out.println("<p>You answered " + correctAnswers + " out of " + numQuestions + " questions correctly.</p>");
    %>

    <a href="additionQuiz.jsp" class="button">Quiz again</a>
</body>
</html>