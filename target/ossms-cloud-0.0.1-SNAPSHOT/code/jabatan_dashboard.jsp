<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ include file = "header.jsp" %>    

<div class="right_col">
        <div class="x_panel">
          <div class="x_title">
            <h2>Senarai Jabatan</h2>
            <div class="clearfix"></div>
          </div>
          <br />
          <button class="btn btn-primary" data-toggle="modal" data-target="#tambah-jabatan">
            Tambah Jabatan
          </button>

          <div class="x_content">
            <table
              class="table table-striped table-bordered"
              id="datatable-fixed-header"
            >
              <thead>
                <tr>
                  <th style="width: 10%">No.</th>
                  <th>Nama Jabatan</th>
                  
                  <th style="width: 10%">Tindakan</th>
                </tr>
              </thead>
              <tbody>
              <% int counter = 1; %>
              <c:forEach var = "dept" items="${depts }"> 
                <tr>
                  <td data-id="${dept.departmentid}"><%= counter++ %></td>
                  <td>${dept.departmentname } </td>
                   <td>
                    <button class="d-inline-block btn btn-primary btn-sm btn-edit" data-id="${dept.departmentid}" title="Sunting Jabatan">
                      <i class="bi bi-pencil"></i>
                    </button>
                  </td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
        
       <!-- modal tambah jabatan --> 
        <div class="modal fade" id="tambah-jabatan">
        <div class="modal-dialog">
          <div class="modal-content">
            <form action="DepartmentController" method="POST">
              <div class="modal-header">
                <button
                  class="close modal-close"
                  data-dismiss="modal"
                  type="button"
                >
                  &times;
                </button>
                <h4 class="modal-title">Tambah Jabatan</h4>
              </div>
              <div class="modal-body">
                <div class="x_panel">
                  
                  <div class="row">
                    <div class="col-md-12 col-sm-12">
                      <label for="t-name">Nama Jabatan</label>
                      <input
                        type="text"
                        class="form-control"
                        required="required"
                        name="t-name"
                        id="t-name"
                      />
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
      
      <!-- modal sunting jabatan --> 
        <div class="modal fade" id="sunting-jabatan">
        <div class="modal-dialog">
          <div class="modal-content">
            <form action="DepartmentController" method="POST">
              <div class="modal-header">
                <button
                  class="close modal-close"
                  data-dismiss="modal"
                  type="button"
                >
                  &times;
                </button>
                <h4 class="modal-title">Sunting Jabatan</h4>
              </div>
              <div class="modal-body">
                <div class="x_panel">
                  <input type="hidden" name="deptid" id="deptid">
                  <div class="row">
                    <div class="col-md-12 col-sm-12">
                      <label for="s-name">Nama Jabatan</label>
                      <input
                        type="text"
                        class="form-control"
                        required="required"
                        name="s-name"
                        id="s-name"
                      />
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
                  Simpan
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
        
        
        
      </div>
     <script>
     $(document).ready(function(){
   		
   		$('.btn-edit').click(function(){
   			var id = $(this).data('id');
       		
       		$.ajax({
       			url: 'DepartmentController',
       			type: 'GET',
       			data: {
       				action: 'getDepartmentJSON',
       				deptid: id
       			},
       			dataType: 'json',
       			
       			success: function(data){
       				$('#deptid').val(data.departmentid);
       				 $('#s-name').val(data.departmentname);
                      
       				$('#sunting-jabatan').modal('show');
       			},
       			
       			error: function(){
       				alert("Failed to retrieve data");
       			}
   			})
   		
   		});
   		     		
   	});
        
      </script>