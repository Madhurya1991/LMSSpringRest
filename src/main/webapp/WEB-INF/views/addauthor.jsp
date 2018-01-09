<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html"%>
<%-- <%AdminService service = new AdminService();
List<Book> books = service.readBooks();
%> --%>
<div class="container">
	<form action="saveAuthor" method="post">
		<h3>Enter Author Name to Save</h3>
		<input type="text" name="authorName"> <br />
		<button type="submit">Save</button>
	</form>
</div>