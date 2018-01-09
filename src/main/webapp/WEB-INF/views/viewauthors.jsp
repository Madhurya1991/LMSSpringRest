<%@ taglib prefix="gcit" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="include.html"%>

<script>
	function searchAuthors(){
		$.ajax({
		  url: "searchAuthors",
		  data: { 
			  searchString: $('#searchString').val()
		  }
		}).done(function(data) {
		    $('#authorsTable').html(data);
		});
	}
</script>
<div class="container">
	<h3>Find the List of Authors in LMS.</h3>
	${statusMessage}

		<input class="form-control mr-sm-2" type="text" placeholder="Search by Author Name" aria-label="Search" id="searchString" oninput="searchAuthors()">
	</form>
	<nav aria-label="Page navigation example">
		<ul class="pagination">
			<li class="page-item"><a class="page-link" href="#"
				aria-label="Previous"> <span aria-hidden="true">&laquo;</span> <span
					class="sr-only">Previous</span>
			</a></li>
			<gcit:forEach begin="1" end="${totalPages}" var="i">
				<li class="page-item"><a class="page-link"
					href="pageAuthors?pageNo=">${i}</a></li>
				</gcit:forEach>
			<li class="page-item"><a class="page-link" href="#"
				aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
					class="sr-only">Next</span>
			</a></li>
		</ul>
	</nav>
	<table class="table table-striped" id="authorsTable">
		<tr>
			<th>#</th>
			<th>Author Name</th>
			<th>Books Written</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<gcit:forEach items="${authors}" var="a" varStatus="authorIndex">
			<tr>
				<td>${authorIndex.index}</td>
				<td>${a.authorName}</td>
				<td>
					
				</td>
				<td><button type="button" class="btn btn-primary"
						onclick="javascript:location.href='editauthor.jsp?authorId='">Edit</button></td>
				<td><button type="button" class="btn btn-danger"
						onclick="javascript:location.href='deleteAuthor?authorId='">Delete</button></td>
			</tr>
		</gcit:forEach>
	</table>
</div>