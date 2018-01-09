<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@include file="include.html"%>
<%AdminService adminService = new AdminService();
Author author = adminService.readAuthorByPk(Integer.parseInt(request.getParameter("authorId")));
%>
<div class="container">
	<form action="editAuthor" method="post">
		<h3>Enter Author Name to Edit</h3>
		<input type="text" name="authorName" value="<%=author.getAuthorName()%>"> <br />
		<input type="hidden" name="authorId" value="<%=author.getAuthorId()%>"> <br />
		<button type="submit">Save</button>
	</form>
</div>