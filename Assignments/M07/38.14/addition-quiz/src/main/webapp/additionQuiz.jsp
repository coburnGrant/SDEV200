<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.grant.AdditionQuestion" %>
<!DOCTYPE html>
<html>
<head>
    <title>Addition Quiz</title>
</head>
<body>
    <h2>Addition Quiz</h2>
    <form action="quizResult.jsp" method="post">
        <%
            int numQuestions = 5;
            for(int i = 1; i <= numQuestions; i++) {
                AdditionQuestion question = new AdditionQuestion();
                session.setAttribute("question_" + i, question);
        %>
            <p><%= i %>: <%= question.getQuestion() %>
            <input type="text" name="answer_<%= i %>" required>
            </p>
        <%
            }
        %>

        <button type="submit">Submit Answers</button>
        <button type="reset">Reset</button>
        <a href="additionQuiz.jsp" class="button">New questions</a>
    </form>
</body>
</html>