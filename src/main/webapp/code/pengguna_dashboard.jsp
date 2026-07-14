<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="item.model.Item" %>  
<%@ include file = "header.jsp" %>    

<div class="right_col">
        <div class="x_panel">
          <div class="x_title">
            <h2>SENARAI PENGGUNA</h2>
          </div>
          <div class="x_content">
            <button
              class="btn btn-primary"
              style="margin-bottom: 20px"
              data-toggle="modal"
              data-target="#borang-pengguna"
            >
              Tambah Pengguna
            </button>
            <table
              class="table table-striped table-bordered"
              style="width: 100%"
              id="datatable-fixed-header"
            >
              <thead>
                <tr>
                  <td>No.</td>
                  <td>Nama</td>
                  <td>No. KP</td>
                  <td>No. Telefon</td>
                  <td>Jabatan</td>
                  <td>Tahap</td>
                  <td>Status</td>
                  <td>Tindakan</td>
                </tr>
              </thead>
              <tbody>
              	 <% int counter = 1; %>
                <c:forEach var = "staff" items="${staffs}">
                	<tr>
                		<td><%= counter++ %> </td>
                		<td>${staff.staffname}</td>
                		<td>${staff.staffic}</td>
                		<td>${staff.staffphonenumber}</td>
                		<td>${staff.dept.departmentname}</td>
                		<td>${staff.staffrole}</td>
                		<td>
                		
                		
                		
                		<c:choose>
						        <c:when test="${staff.staffstatus == 'AKTIF'}">
						            <span class="badge badge-success">${staff.staffstatus}</span>
						        </c:when>
						
						        <c:when test="${staff.staffstatus == 'TIDAK AKTIF'}">
						            <span class="badge badge-danger">${staff.staffstatus}</span>
						        </c:when>
						
						    </c:choose>
                		</td>
                		<td>
		                    <a
		                      class="btn btn-info btn-sm"
		                      href="${pageContext.request.contextPath}/StaffController?action=sadmineditform&staffeditid=${staff.staffid}"
		                      title="Kemaskini"
		                      ><i class="bi bi-pencil"></i
		                    ></a>
		                    <a
		                      onclick="return confirm('Set semula kata laluan?');"
		                      class="btn btn-danger btn-sm"
		                      title="Reset Kata Laluan"
		                      href="${pageContext.request.contextPath}/StaffController?action=resetPassword&staffeditid=${staff.staffid}"
		                      ><i class="bi bi-key"></i
		                    ></a>
		                  </td>
                	</tr>
                </c:forEach>
              
              
              
              </tbody>
              
             
            </table>
          </div>
        </div>
      </div>
      <!-- borang tambah pengguna -->
      <div class="modal fade" id="borang-pengguna">
        <div class="modal-dialog">
          <div class="modal-content">
            <form action="" method="POST">
              <div class="modal-header">
                <button
                  type="button"
                  data-dismiss="modal"
                  class="close modal-close"
                >
                  &times;
                </button>
                <h4 class="modal-title">Borang Pengguna</h4>
              </div>
              <div class="modal-body">
                <div class="x_panel">
                  <div class="col-md-12 col-sm-12 col-sx-12">
                    <label for="nama">Nama</label>
                    <input
                      type="text"
                      id="nama"
                      name="nama"
                      required="required"
                      class="form-control"
                    />
                    <label for="no-kp">Nombor Kad Pengenalan</label>
                    <input
                      type="text"
                      class="form-control"
                      id="no-kp"
                      name="no-kp"
                      required="required"
                      oninput="validateIC(this)"
                      maxlength="12"
                    />
                    <small id="ic-warning" class="text-danger" style="display:none;">
					    No. Kad Pengenalan tersebut telah digunakan.
					</small>
                    <label for="no-tel">No Telefon</label>
                    <input
                      type="text"
                      class="form-control"
                      id="no-tel"
                      name="no-tel"
                      required="required"
                      oninput="this.value = this.value.replace(/[^0-9]/g, '')"
                    />
                    
                    <label for="jabatan">Jabatan</label>
                    <select name="jabatan" id="jabatan" class="form-control">
                       <option disabled selected>--Pilih Jabatan--</option>
                       <c:forEach var = "dept" items="${depts}">
                       		<option value="${dept.departmentid}">${dept.departmentname}</option>
                       </c:forEach>
                    </select>

					<label for="pengurus">Pengurus</label>
                    <select name="pengurus" id="pengurus" class="form-control">
                       <option disabled selected>--Pilih Pengurus--</option>
                       <c:forEach var = "staff" items="${staffs}">
                       		<option value="${staff.staffid}">${staff.staffname}</option>
                       </c:forEach>
                    </select>

                    <label for="tahap">Tahap</label>
                    <select name="tahap" id="tahap" class="form-control">
                      <option value="BIASA">BIASA</option>
                      <option value="ADMIN">ADMIN</option>
                      <option value="SUPERADMIN">SUPERADMIN</option>
                    </select>
                    <label for="status">Status</label>
                    <select
                      name="status"
                      id="status"
                      class="form-control"
                      required="required"
                    >
                      <option value="AKTIF">AKTIF</option>
                      <option value="TIDAK AKTIF">TIDAK AKTIF</option>
                    </select>
                    <label for="kata-laluan">Kata Laluan</label>
                    <input
                      type="text"
                      class="form-control"
                      id="kata-laluan"
                      name="kata-laluan"
                      disabled="disabled"
                      value="P@ssword.123"
                    />
                  </div>
                </div>
              </div>
              <div class="modal-footer">
                <button
                  class="btn btn-success"
                  type="submit"
                  id="pengguna-baru"
                  name="pengguna-baru"
                  
                >
                  Hantar
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
      
      
      <script>

		const staffICs = [
		<c:forEach items="${staffs}" var="staff" varStatus="loop">
		    "${staff.staffic}"<c:if test="${!loop.last}">,</c:if>
		</c:forEach>
		];
		
		function validateIC(input){
		
		    // Allow numbers only
		    input.value = input.value.replace(/[^0-9]/g,'');
		
		    const warning = document.getElementById("ic-warning");
			const submitBtn = document.getElementById("pengguna-baru");
			
			submitBtn.disabled = true;
			
			if(input.value.length < 12){
		        return;
		    }
		    if(input.value.length === 12){
		
		        if(staffICs.includes(input.value)){
		            warning.style.display = "block";
		            submitBtn.disabled = true;
		        }else{
		            warning.style.display = "none";
		            submitBtn.disabled = false;
		        }
		
		    }else{
		        warning.style.display = "none";
		        submitBtn.disabled = false;
		    }
		
		}

	</script>