<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ include file = "header.jsp" %>    

 <div class="right_col">
        <div class="x_panel">
          <div class="x_title">
            <h2>SENARAI PERMINTAAN</h2>
          </div>
          <div class="x_content">
            <br />
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th style="width: 20%">
                    <span class="badge badge-baru">BARU</span>
                  </th>
                  <th>
                    <b class="text-primary" style="font-size: 14px">${baruCount }</b>
                  </th>
                  <th style="width: 20%">
                    <span class="badge badge-success">DILULUSKAN</span>
                  </th>
                  <th>
                    <b class="text-success" style="font-size: 14px">${lulusCount }</b>
                  </th>
                  <th style="width: 20%">
                    <span class="badge badge-info">SELESAI</span>
                  </th>
                  <th>
                    <b class="text-info" style="font-size: 14px">${selesaiCount }</b>
                  </th>
                  <th style="width: 20%">
                    <span class="badge badge-danger">DITOLAK</span>
                  </th>
                  <th>
                    <b class="text-danger" style="font-size: 14px">${tolakCount }</b>
                  </th>
                </tr>
              </thead>
            </table>
            <a href="${pageContext.request.contextPath}/RequestController?action=addForm" class="btn btn-primary"
              >Permintaan Baru</a
            >
            <br />
            <div class="col-md-12 col-sm-12 col-xs-12">
              <table
                class="table table-striped table-bordered"
                id="datatable-fixed-header"
              >
                <thead>
                	<tr>
	                  <th>No.</th>
	                  <th>Tarikh Diterima</th>
	                  <th>Diminta Oleh</th>
	                  <th>Jabatan</th>
	                  <th>Tarikh Pengambilan</th>
	                  <th>Status</th>
	                  <th style="width: 10%">Tindakan</th>
                	</tr>
                </thead>
                
                <tbody>
                <% int counter = 1; %>
                <c:forEach var = "req" items="${requests}">
	                <tr class="text-info">
	                    <td><%= counter++ %></td>
	                    <td>${req.submitteddate }</td>
	                    <td>${req.getRequester().staffname }</td>
	                    <td>${req.getDepartment().departmentname }</td>
	                    <td>${req.getApproved().collectiondate }</td>
	              
	                      <td>
						    <c:choose>
						        <c:when test="${req.requeststatus == 'BARU'}">
						            <span class="badge badge-baru">${req.requeststatus}</span>
						        </c:when>
						
						        <c:when test="${req.requeststatus == 'DILULUSKAN'}">
						            <span class="badge badge-diluluskan">${req.requeststatus}</span>
						        </c:when>
						
						        <c:when test="${req.requeststatus == 'SELESAI'}">
						            <span class="badge badge-selesai">${req.requeststatus}</span>
						        </c:when>
						
						        <c:otherwise>
						            <span class="badge badge-ditolak">${req.requeststatus}</span>
						        </c:otherwise>
						    </c:choose>
						</td>
	                    
	                    
	                    <td>
						    <c:choose>
						
						        <c:when test="${req.requeststatus == 'BARU' && (req.requester.staffid == sessionScope.staff.staffid || sessionScope.staff.staffrole != 'BIASA') }">
						            <a href="${pageContext.request.contextPath}/RequestController?action=editForm&requestid=${req.requestid}"
						               class="btn btn-primary btn-sm"
						               title="Sunting">
						                <i class="bi bi-pencil"></i>
						            </a>
						        </c:when>
						
						        <c:when test="${req.requeststatus != 'BARU'}">
						            <a href="${pageContext.request.contextPath}/InheritanceController?action=viewForm&requestid=${req.requestid}"
						               class="btn btn-info btn-sm"
						               title="Lihat">
						                <i class="bi bi-eye"></i>
						            </a>
						        </c:when>
						
						    </c:choose>
						</td>
	                    
	                   
	                  </tr>
                </c:forEach>
                  
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <div class="modal fade" id="permintaan-baru">
        <div class="modal-dialog">
          <div class="modal-content">
            <form action="" method="POST">
              <div class="modal-header">
                <button
                  class="close modal-close"
                  data-dismiss="modal"
                  type="button"
                >
                  &times;
                </button>
                <h4 class="modal-title">Permintaan Baru</h4>
              </div>
              <div class="modal-body">
                <div class="x_panel">
                  <div class="row">
                    <div class="col-md-12 col-sm-12">
                      <label for="t-item">Barang</label>
                      <select name="t-item" id="t-item" class="form-control">
                        <option value="" disabled selected>
                          --Pilih Barang--
                        </option>

                        
                        <option value=""></option>
                      </select>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-12 col-sm-12">
                      <label for="t-quant">Jumlah</label>
                      <input
                        type="number"
                        name="t-quant"
                        id="t-quant"
                        required="required"
                        min="1"
                        class="form-control"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="modal-footer">
                <button
                  type="submit"
                  id="btn-p-baru"
                  name="btn-p-baru"
                  class="btn btn-success"
                >
                  Hantar
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
