<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="item.model.Item" %>  
<%@ page import="java.time.LocalDate" %>
<%@ include file = "header.jsp" %>

<div class="right_col">
  <div class="x_panel" style="color: black">
    <h4 align="center">BORANG PERMOHONAN STOK</h4>
    <h5 align="center">(INDIVIDU KEPADA STOR)</h5>
    <form method="POST" action="InheritanceController?action=complete">
    <input type="hidden" name="requestid" value="${req.requestid }">
    <table class="table table-bordered">
  
      <tbody>
        <tr align="center">
          <td><b>No. Kod</b></td>
          <td><b>Perihal Stok</b></td>
          <td><b>Kuantiti Dimohon</b></td>
          <td><b>Catatan</b></td>
          <td><b>Kuantiti Diluluskan</b></td>
        </tr>
		<c:forEach var="ri" items="${req.request_Item}">
			<tr>
          <td>${ri.itemid}</td>
          <td>${ri.getItem().itemname}</td>
          <td>${ri.requestedamount}</td>
          <td>${ri.note}</td>
          <td>
	          <c:choose>
        		<c:when test="${ri.status == 'LULUS' }">
        			${ri.requestedamount }
        		</c:when>  
        		<c:otherwise>
        			0
        		</c:otherwise>
    	      </c:choose>
          </td>
        </tr>
		</c:forEach>
        
      </tbody>
    </table>
    
	<c:choose>
		<c:when test="${req.requeststatus == 'DILULUSKAN' }">
			<div class="row">
		      <div class="col-md-4 col-sm-4">
		        <label for="col-date">Tarikh Pengambilan Barang</label>
		        <input
		          type="date"
		          name=""
		          id="col-date"
		          class="form-control admin-enable"
		          value="${req.collectiondate }"
		          disabled
		        />
		      </div>
		    </div>
		    <br>
		    <div class="row">
			      <div class="col-md-4">
			        <label for="r-col-date">Tarikh Barang Diambil</label>
			        <input
			          type="date"
			          name="collected-date"
			          id="collected-date"
			          class="form-control"
			          required
			          value="<%= LocalDate.now() %>"
			        />
			      </div>
			    </div>
		</c:when>
		<c:when test="${req.requeststatus == 'DITOLAK' }">
				<div class="row">
			      <div class="col-md-4">
				      <label for="rej-reason">Sebab ditolak:</label>
				      <textarea
				        class="form-control"
				        name="rej-reason"
				        id="rej-reason"
				        readonly
				      >${req.rejectedreason} </textarea>
				    </div>
			    </div>
			</c:when>
			<c:when test="${req.requeststatus == 'SELESAI' }">
				<div class="row">
			      <div class="col-md-4">
			        <label for="r-col-date">Tarikh Barang Diambil</label>
			        <input
			          type="date"
			          
			          class="form-control"
			          value="${req.collecteddate}"
			          readonly
			        />
			      </div>
			    </div>
			</c:when>
	</c:choose>
    
    <br />
    <button class="btn btn-primary" onclick="history.back();" type="button">Kembali</button>
    <c:if test="${req.requeststatus == 'DILULUSKAN' }">
    	<button class="btn btn-success">
	      Selesai
	    </button>
    </c:if>
    
    
    <br />
    </form>
  </div>
</div>
