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
            <h2>KEMASKINI PENGGUNA</h2>
            <div class="clearfix"></div>
          </div>
          <div class="x_content">
          <form method="POST" action="StaffController?action=sadminedit&staffeditid=${staff.staffid}">
            <div class="row">
              <div class="col-md-6">
                <label for="u-ic">NOMBOR KAD PENGENALAN</label>
                <input
                  type="number"
                  name="u-ic"
                  id="u-id"
                  class="form-control"
                  value="${staff.staffic}"
                />
              </div>
              <div class="col-md-6">
                <label for="u-name">NAMA</label>
                <input
                  type="text"
                  class="form-control"
                  id="u-name"
                  name="u-name"
                  value="${staff.staffname }"
                />
              </div>
            </div>
            <div class="row">
              <div class="col-md-6">
                <label for="u-pn"> NOMBOR TELEFON </label>
                <input
                  type="text"
                  class="form-control"
                  id="u-pn"
                  name="u-pn"
                  value="${staff.staffphonenumber }"
                />
              </div>
              <div class="col-md-6">
                <label for="u-status">STATUS</label>
                <select name="u-status" id="u-status" class="form-control">
                  <option value="AKTIF"
					    <c:if test="${staff.staffstatus == 'AKTIF'}">
					        selected="selected"
					    </c:if>>
					    AKTIF
					</option>
                  <option value="TIDAK AKTIF"
                  <c:if test="${staff.staffstatus == 'TIDAK AKTIF'}">
					selected="selected"
					</c:if>
                  >TIDAK AKTIF</option>
                </select>
              </div>
            </div>
            <div class="row">
              <div class="col-md-6">
                <label for="u-dep">JABATAN</label>
                <select name="u-dep" id="u-dep" class="form-control">
                  <option value="" disabled>--Pilih Jabatan--</option>
                  <c:forEach var="dept" items="${depts}">
                  		<option value="${dept.departmentid }" 
                  		<c:if test="${staff.departmentid == dept.departmentid }">
                  			selected="selected"
                  		</c:if>
                  		
                  		>${dept.departmentname} </option>
                  </c:forEach>
                  
                </select>
              </div>
              <div class="col-md-6">
                <label for="u-role">TAHAP</label>
                <select name="u-role" id="u-role" class="form-control">
                  <option value="" disabled>--Pilih Tahap--</option>
                  <option value="BIASA"
                  <c:if test="${staff.staffrole == 'USER'}">
					selected="selected"
					</c:if>
                  >BIASA</option>
                  <option value="ADMIN"
                  <c:if test="${staff.staffrole == 'ADMIN'}">
					selected="selected"
					</c:if>
                  >ADMIN</option>
                  <option value="SUPERADMIN"
                  <c:if test="${staff.staffrole == 'SUPERADMIN'}">
					selected="selected"
					</c:if>
                  >SUPERADMIN</option>
                </select>
              </div>
            </div>
            <div class="row">
              <div class="col-md-6">
                <label for="u-manager">PENGURUS (Tidak Wajib)</label>
                <select name="u-manager" id="u-manager" class="form-control">
                  <option value="" disabled selected>--Pilih Pengurus--</option>
                  <c:forEach var="man" items="${managers}">
                  		<option value="${man.staffid }" 
                  		<c:if test="${staff.managerid == man.staffid}">
                  			selected="selected"
                  		</c:if>
                  		
                  		>${man.staffname} </option>
                  </c:forEach>
                  
                </select>
              </div>
              </div>
            
            <br />
            <button
              class="btn btn-success"
            >
              Kemaskini
            </button>
            <button class="btn btn-secondary" onclick="history.back()">
              Kembali
            </button>
          </form>
          </div>
          
        </div>
      </div>
    