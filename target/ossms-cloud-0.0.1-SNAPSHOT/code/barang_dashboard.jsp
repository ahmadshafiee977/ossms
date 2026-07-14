<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ include file = "header.jsp" %>    

<div class="right_col">
        <div class="col-md-12 col-sm-12">
          <div class="x_panel">
            <div class="x_title">
              <h2>Senarai Barang</h2>
              <div class="clearfix"></div>
            </div>
            <br />
            <c:if test="${sessionScope.staff.staffrole != 'BIASA'}">
			<button class="btn btn-primary" data-toggle="modal" data-target="#tambah-barang">Tambah Barang</button>
            </c:if>
            <div class="x_content">
              <table
                class="table table-striped table-bordered"
                id="datatable-fixed-header"
              >
                <thead>
                  <tr>
                    <th style="width: 5%">No.</th>
                    <th>Nama Barang</th>
                    <th>Harga(RM)</th>
                    <th>Unit</th>
                    <th>Stok Semasa</th>
                    <th>Status</th>
                    <th style="width: 15%">Aksi</th>
                  </tr>
                </thead>
                <tbody>
                <% int counter = 1; %>
                <c:forEach var = "item" items="${items}">
	                <tr class="${item.currentstock < item.minimumstock ? 'text-danger' : ''}">
	                	<td data-id="${item.itemid }"><%= counter++ %></td>
	              		<td>${item.itemname } </td>
	              		<td><fmt:formatNumber value="${item.itemprice}" minFractionDigits="2" maxFractionDigits="2" /> </td>
	              		<td>${item.itemunit} </td>
	              		<td>${item.currentstock}
	              		${item.currentstock < item.minimumstock ? '(Kritikal)' : ''}
	              		 </td>
	              		<td>
	              			<c:choose>
						        <c:when test="${item.itemstatus == 'AKTIF'}">
						            <span class="badge badge-success">${item.itemstatus}</span>
						        </c:when>
						
						        <c:when test="${item.itemstatus == 'TIDAK AKTIF'}">
						            <span class="badge badge-danger">${item.itemstatus}</span>
						        </c:when>
						
						    </c:choose>
	              		
	              		</td>
	              		<td>
	              			<button
		                        class="btn btn-primary btn-sm btn-edit"
		                        data-id="${item.itemid }"
		                        title="Kemaskini"
		                        onclick="$('#sunting-barang').modal('show')"
		                      >
		                         <c:choose>
							        <c:when test="${sessionScope.staff.staffrole == 'BIASA'}">
							            <i class="bi bi-eye"></i>
							        </c:when>
							        <c:otherwise>
							            <i class="bi bi-pencil-square"></i>
							        </c:otherwise>
							    </c:choose>
		                        
		                      </button>
		
	              		</td>
	                </tr>
	                
                </c:forEach>
                
                </tbody>
              </table>
            </div>
          </div>
          <!-- x_panel -->
        </div>
        <!-- col-md-12 -->
      <div class="modal fade" id="tambah-barang">
        <div class="modal-dialog">
          <div class="modal-content">
            <form action="ItemController" method="POST">
              <div class="modal-header">
                <button
                  class="close modal-close"
                  data-dismiss="modal"
                  type="button"
                >
                  &times;
                </button>
                <h4 class="modal-title">Tambah Barang</h4>
              </div>
              <div class="modal-body">
                <div class="x_panel">
                  
                  <div class="row">
                    <div class="col-md-12 col-sm-12">
                      <label for="t-name">Nama Barang</label>
                      <input
                        type="text"
                        class="form-control"
                        required="required"
                        name="t-name"
                        id="t-name"
                        required
                      />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-12 col-sm-12">
                      <label for="t-desc">Deskripsi</label>
                      <textarea
                        class="form-control"
                        required="required"
                        name="t-desc"
                        id="t-desc"
                      ></textarea>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-6">
                      <label for="t-price">Harga(Per Unit)</label>
                      <input
                        type="number"
                        step="0.01"
                        class="form-control"
                        required="required"
                        name="t-price"
                        id="t-price"
                        required
                      />
                    </div>
                    <div class="col-md-6">
                      <label for="t-unti">Unit</label>
                      <input
                        type="text"
                        class="form-control"
                        required="required"
                        name="t-unit"
                        id="t-unit"
                        required
                      />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-6">
                      <label for="t-max">Stok Maksimum</label>
                      <input
                        type="number"
                        class="form-control"
                        required="required"
                        name="t-max"
                        id="t-max"
                        required
                      />
                    </div>
                    
                    <div class="col-md-6">
                      <label for="t-min">Stok Minimum</label>
                      <input
                        type="number"
                        class="form-control"
                        required="required"
                        name="t-min"
                        id="t-min"
                        required
                      />
                    </div>
                  </div>
                  <div class="row">
                  	<div class="col-md-6">
                      <label for="t-cur">Stok Semasa</label>
                      <input
                        type="number"
                        class="form-control"
                        required="required"
                        name="t-cur"
                        id="t-cur"
                        value="0"
                        required
                      />
                    </div>
                    <div class="col-md-6">
                      <label for="t-status">Status</label>
                      <select name="t-status" id="t-status" class="form-control">
                      	<option value="AKTIF" selected>AKTIF</option>
                      	<option value="TIDAK AKTIF" >TIDAK AKTIF</option>
                      </select>
                    </div>
                  </div>
                  <br />
                  
                </div>
              </div>
              <div class="modal-footer">
                <button
                  type="submit"
                  id="btn-tambah-barang"
                  name="btn-tambah-barang"
                  class="btn btn-success"
                >
                  Tambah
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
      <!-- modal edit barang -->
      <div class="modal fade user-disabled" id="sunting-barang">
        <div class="modal-dialog">
          <div class="modal-content">
            <form action="ItemController" method="POST">
              <div class="modal-header">
                <button
                  type="button"
                  data-dismiss="modal"
                  class="close modal-close"
                >
                  &times;
                </button>
                <h4 class="modal-title">
                	<c:choose>
						<c:when test="${sessionScope.staff.staffrole == 'BIASA'}">
							Maklumat Barang
						</c:when>
						<c:otherwise>
							 Sunting Barang
						</c:otherwise>
					</c:choose>
                
               
                </h4>
              </div>
              <div class="modal-body">
                <div class="x_panel">
                  <input type="hidden" name="itemid" id="s-itemid" />
                  
                  <div class="row">
                    <div class="col-md-12 col-sm-12">
                      <label for="s-name">Nama Barang</label>
                      <input
                        type="text"
                        class="form-control"
                        id="s-name"
                        name="s-name"
                        required="required"
                      />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-12 col-md-12">
                      <label for="s-desc">Deskripsi</label>
                      <textarea
                        name="s-desc"
                        id="s-desc"
                        class="form-control"
                        required="required"
                      ></textarea
                      >
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-6 col-sm-6">
                      <label for="s-price">Harga (Per Unit)</label>
                      <input
                        type="number"
                        step="0.01"
                        class="form-control"
                        required="required"
                        name="s-price"
                        id="s-price"
                       
                      />
                    </div>
                    <div class="col-md-6 col-sm-6">
                      <label for="s-loc">Unit</label>
                      <input
                        type="text"
                        class="form-control"
                        id="s-unit"
                        name="s-unit"
                        required="required"
                       
                      />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-6">
                      <label for="s-max">Stok Maksimum</label>
                      <input
                        type="number"
                        name="s-max"
                        id="s-max"
                        class="form-control"
                        required="required"
                        
                      />
                    </div>

                    <div class="col-md-6">
                      <label for="s-min">Stok Minimum</label>
                      <input
                        type="number"
                        name="s-min"
                        id="s-min"
                        class="form-control"
                        required="required"
                       
                      />
                    </div>
                  </div>
                  
                  <div class="row">
                  	<div class="col-md-3">
                      <label for="s-cur">Stok Semasa</label>
                      <input
                        type="number"
                        name="s-cur"
                        id="s-cur"
                        class="form-control"
                        required="required"
                        readonly
                        
                      />
                    </div>
                    <div class="col-md-3">
                      <label for="s-cur">Tambah Stok</label>
                      <input
                        type="number"
                        name="s-restock"
                        id="s-restock"
                        class="form-control"
                        min="0"
                        value="0"
                        required
                        
                        
                      />
                    </div>
                    <div class="col-md-6">
                       <label for="s-status">Status</label>
                      <select name="s-status" id="s-status" class="form-control">
                      	<option value="AKTIF">AKTIF</option>
                      	<option value="TIDAK AKTIF" >TIDAK AKTIF</option>
                      </select>
                    </div>
                  </div>
                  <br />
                </div>
              </div>
              <div class="modal-footer">
              	<c:if test="${sessionScope.staff.staffrole != 'BIASA'}">
                <button
                  type="submit"
                  class="btn btn-success"
                  id="btn-sunting-barang"
                  name="btn-sunting-barang"
                >
                  Kemaskini
                </button>
                </c:if>
              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- modal restock -->
      <div class="modal fade" id="restock-barang">
        <div class="modal-dialog">
          <div class="modal-content">
            <form action="ItemController" method="POST">
              <div class="modal-header">
                <button
                  type="button"
                  data-dismiss="modal"
                  class="close modal-close"
                >
                  &times;
                </button>
                <h4 class="modal-title">Penambahan Stok</h4>
              </div>
              <div class="modal-body">
                <div class="x_panel">
                  <div class="row">
                    <div class="col-md-12 col-sm-12">
                      <label for="itemid">Kod Item</label>
                      <input
                        type="text"
                        class="form-control"
                        id="rs-itemid"
                        name="itemid"
                        readonly                        
                      />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-12 col-sm-12">
                      <label for="rs-name">Perihal Barang</label>
                      <input
                        type="text"
                        class="form-control"
                        id="rs-name"
                        name="rs-name"
                        disabled="disabled"
                        
                      />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-12 col-sm-12">
                      <label for="rs-quant">Jumlah Barang Diterima</label>
                      <input
                        type="number"
                        name="rs-quant"
                        id="rs-quant"
                        class="form-control"
                        required="required"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="modal-footer">
                <button
                  type="submit"
                  class="btn btn-success"
                  id="btn-tambah-stock"
                  name="btn-tambah-stock"
                  
                >
                  Tambah
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>

            <script>
      	$(document).ready(function(){
      		
      		$('.btn-restock').click(function(){
      			var id = $(this).data('id');
          		
          		$.ajax({
          			url: 'ItemController',
          			type: 'GET',
          			data: {
          				action: 'getItemJSON',
          				itemid: id
          			},
          			dataType: 'json',
          			
          			success: function(data){
          				$('#rs-itemid').val(data.itemid);
          				$('#rs-name').val(data.itemname);
          				
          				$('#restock-barang').modal('show');
          			},
          			
          			error: function(){
          				alert("Failed to retrieve data");
          			}
      			})
      		
      		});
      		     		
      	});
      </script>
      
      <script>
      	$(document).ready(function(){
      		
      		$('.btn-edit').click(function(){
      			var id = $(this).data('id');
          		
          		$.ajax({
          			url: 'ItemController',
          			type: 'GET',
          			data: {
          				action: 'getItemJSON',
          				itemid: id
          			},
          			dataType: 'json',
          			
          			success: function(data){
          				$('#s-itemid').val(data.itemid);
          				 $('#s-id').val(data.itemId);
                         $('#s-name').val(data.itemname);
                         $('#s-desc').val(data.itemdesc);
                         $('#s-price').val(data.itemprice);
                         $('#s-unit').val(data.itemunit);
                         $('#s-max').val(data.maximumstock);
                         $('#s-cur').val(data.currentstock);
                         $('#s-min').val(data.minimumstock);
                         $('#s-status').val(data.itemstatus);
          				
          				$('#sunting-barang').modal('show');
          			},
          			
          			error: function(){
          				alert("Failed to retrieve data");
          			}
      			})
      		
      		});
      		     		
      	});
      	
      	$(function () {
      	    if ("${sessionScope.staff.staffrole}" === "BIASA") {
      	        $(".user-disabled")
      	            .find("input, select, textarea")
      	            .prop("disabled", true);
      	    }
      	});
      </script>
      </div>
      <!-- right_col -->
      
