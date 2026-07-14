<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="item.model.Item" %>  
<%@ include file = "header.jsp" %>

<style>
        .x_content {
          display: flex;
          flex-direction: row;
          position: relative;
          overflow: hidden;
          width: 100%;
          max-width: 100%;
          min-height: 480px;
          gap: 0;
        }

        .form-container {
          position: absolute;
          top: 0px;
          height: 100%;
          -webkit-transition: all 0.6s ease-in-out;
          transition: all 0.6s ease-in-out;
          padding-top: 50px;
        }

        .form-edit-profile {
          min-width: 50%;
          z-index: 2;
          opacity: 1;
        }

        .form-change-password {
          min-width: 50%;
          z-index: 1;
          opacity: 0;
        }

        .overlay-container {
          position: absolute;
          top: 0;
          left: 50%;
          width: 50%;
          height: 100%;
          overflow: hidden;
          transition: -webkit-transform 0.6s ease-in-out;
          transition: transform 0.6s ease-in-out;
          transition:
            transform 0.6s ease-in-out,
            -webkit-transform 0.6s ease-in-out;
          z-index: 100;
        }

        .overlay {
          background: #ff416c;
          background: -webkit-gradient(
              linear,
              left top,
              right top,
              from(#ff4b2b),
              to(#ff416c)
            )
            no-repeat 0 0 / cover;
          background: linear-gradient(to right, #42E307, #ff416c) no-repeat 0
            0 / cover;
          color: #fff;
          position: relative;
          left: -100%;
          height: 100%;
          width: 200%;
          -webkit-transform: translateX(0);
          transform: translateX(0);
          -webkit-transition: -webkit-transform 0.6s ease-in-out;
          transition: -webkit-transform 0.6s ease-in-out;
          transition: transform 0.6s ease-in-out;
          transition:
            transform 0.6s ease-in-out,
            -webkit-transform 0.6s ease-in-out;
        }

        .overlay-panel {
          position: absolute;
          top: 0;
          display: -webkit-box;
          display: -ms-flexbox;
          display: flex;
          -webkit-box-orient: vertical;
          -webkit-box-direction: normal;
          -ms-flex-direction: column;
          flex-direction: column;
          -webkit-box-pack: center;
          -ms-flex-pack: center;
          justify-content: center;
          -webkit-box-align: center;
          -ms-flex-align: center;
          align-items: center;
          padding: 0 40px;
          height: 100%;
          width: 50%;
          text-align: center;
          -webkit-transform: translateX(0);
          transform: translateX(0);
          -webkit-transition: -webkit-transform 0.6s ease-in-out;
          transition: -webkit-transform 0.6s ease-in-out;
          transition: transform 0.6s ease-in-out;
          transition:
            transform 0.6s ease-in-out,
            -webkit-transform 0.6s ease-in-out;
        }

        .overlay-right {
          right: 0;
          -webkit-transform: translateX(0%);
          transform: translateX(0%);
        }

        .overlay-left {
          -webkit-transform: translateX(-20%);
          transform: translateX(-20%);
        }

        .x_content.right-panel-active .overlay-container {
          -webkit-transform: translateX(-100%);
          transform: translateX(-100%);
        }

        .x_content.right-panel-active .form-edit-profile {
          -webkit-transform: translateX(100%);
          transform: translateX(100%);
          opacity: 0;
        }

        .x_content.right-panel-active .form-change-password {
          -webkit-transform: translateX(100%);
          transform: translateX(100%);
          opacity: 1;
          z-index: 5;
        }

        .x_content.right-panel-active .overlay {
          -webkit-transform: translateX(50%);
          transform: translateX(50%);
        }

        .x_content.right-panel-active .overlay-left {
          -webkit-transform: translateX(0);
          transform: translateX(0);
        }

        .x_content.right-panel-active .overlay-right {
          -webkit-transform: translateX(20%);
          transform: translateX(20%);
        }

        button.ghost {
          border-radius: 20px;
          border: 1px solid #ff4b2b;
          background: #ff4b2b;
          color: #fff;
          font-size: 12px;
          font-weight: 700px;
          padding: 12px 45px;
          letter-spacing: 1px;
          text-transform: uppercase;
          -webkit-transition: -webkit-transform 80ms ease-in;
          transition: -webkit-transform 80ms ease-in;
          transition: transform 80ms ease-in;
          transition:
            transform 80ms ease-in,
            -webkit-transform 80ms ease-in;
          background: transparent;
          border-color: #fff;
        }

        button.ghost:active {
          -webkit-transform: scale(0.95);
          transform: scale(0.95);
        }

        button.ghost:focus {
          outline: none;
        }

        .pw-rules .check {
          display: none;
          color: green;
        }

        .pw-rules .cross {
          color: red;
        }

        .pw-rules h6.valid .check {
          display: inline;
        }

        .pw-rules h6.valid .cross {
          display: none;
        }
      </style>
      <div class="right_col">
        <div class="x_panel">
          <div class="x_title">
            <h2>Profil Pengguna</h2>
          </div>
          <div class="x_content" id="x_content">
            <div class="form-container form-edit-profile">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <form action="StaffController?action=editOwnInfo&staffeditid=${staff.staffid}" method="POST">
                  <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                    <input type="hidden"  name="staffid" value="${staff.staffid}">
                      <label for="nama">Nama</label>
                      <input
                        type="text"
                        class="form-control"
                        id="nama"
                        name="nama"
                        required="required"
                        value="${staff.staffname}"
                      />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                      <label for="no-kp">Nombor Kad Pengenalan</label>
                      <input
                        type="text"
                        class="form-control"
                        id="no-kp"
                        name="no-kp"
                        required="required"
                        value="${staff.staffic}"
                        oninput="this.value = this.value.replace(/[^0-9]/g, '')"
                      />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                      <label for="no-tel">Nombor Telefon</label>
                      <input
                        type="text"
                        class="form-control"
                        id="no-tel"
                        name="no-tel"
                        required="required"
                        value="${staff.staffphonenumber }"
                        oninput="this.value = this.value.replace(/[^0-9]/g, '')"
                      />
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                      <label for="jabatan">Jabatan</label>
                      <select
                        name="jabatan"
                        id="jabatan"
                        class="form-control"
                        required="required"
                      >
                      <c:forEach var="dept" items="${depts }">
                      	<option value="${dept.departmentid}"
                      	<c:if test="${dept.departmentid == staff.departmentid }">
                      		selected="selected"
                      	</c:if>
                      	
                      	>${dept.departmentname }</option>
                      </c:forEach>
                      
                      </select>
                     
                    </div>
                  </div>

                  

                  <div style="margin-top: 20px">
                    <button
                      class="btn btn-success"
                      name="kemaskini"
                      id="kemaskini"
                      type="submit"
                     
                    >
                      Kemaskini
                    </button>
                  </div>
                </form>
              </div>
            </div>
            <div class="form-container form-change-password">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <form action="StaffController?action=changePassword&staffeditid=${staff.staffid}" method="POST">
                  <br /><br /><br />
                  <div class="row">
                    <div class="col-md-12 col-sm-12 col-xs-12">
                      <label for="kata-laluan-baru">Kata Laluan Baru</label>
                      <input
                        type="text"
                        class="form-control"
                        id="kata-laluan-baru"
                        name="kata-laluan-baru"
                        required
                        autocomplete="off"
                      />
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12">
                      <label for="sah-kata-laluan-baru">Pengesahan Kata Laluan Baru</label>
                      <input
                        type="text"
                        class="form-control"
                        id="sah-kata-laluan-baru"
                        required
                        autocomplete="off"
                      />

                      <div class="pw-rules">
                        <h6 id="rule-lower">
                          <span class="icon cross">&Chi;</span>
                          <span class="icon check">&radic;</span>
                          Huruf kecil
                        </h6>
                        <h6 id="rule-upper">
                          <span class="icon cross">&Chi;</span>
                          <span class="icon check">&radic;</span>
                          Huruf Besar
                        </h6>
                        <h6 id="rule-number">
                          <span class="icon cross">&Chi;</span>
                          <span class="icon check">&radic;</span>
                          Nombor
                        </h6>
                        <h6 id="rule-special">
                          <span class="icon cross">&Chi;</span>
                          <span class="icon check">&radic;</span>
                          Aksara khas
                        </h6>
                        <h6 id="rule-min">
                          <span class="icon cross">&Chi;</span>
                          <span class="icon check">&radic;</span>
                          Minimum lapan (8) huruf
                        </h6>
                        <h6 id="rule-same">
                          <span class="icon cross">&Chi;</span>
                          <span class="icon check">&radic;</span>
                          Kedua-dua kata laluan sepadan
                        </h6>
                      </div>
                    </div>
                  </div>
                  <div style="margin-top: 20px">
                    <button
                      class="btn btn-danger"
                      name="tukar-password"
                      id="tukar-password"
                      type="submit"
                      disabled
                      
                    >
                      Tukar Kata Laluan
                    </button>
                  </div>
                </form>
              </div>
            </div>
            <!-- form change password -->
            <div class="overlay-container">
              <div class="overlay">
                <div class="overlay-panel overlay-left">
                  
                  <button class="ghost" id="btn-edit-profile">
                    Edit Profil
                  </button>
                </div>
                <div class="overlay-panel overlay-right">
                  <button class="ghost" id="btn-password">
                    Tukar Kata Laluan
                  </button>
                </div>
              </div>
            </div>
          </div>
          <!-- x_content -->
        </div>
      </div>

      <script>
        const btnChangePass = document.getElementById("tukar-password");

        function validatePassword(pw) {
          const hasUpper = /[A-Z]/.test(pw);
          const hasLower = /[a-z]/.test(pw);
          const hasNumber = /[0-9]/.test(pw);
          const hasSpecial = /[^A-Za-z0-0]/.test(pw);
          const pwLen = pw.length > 8;
          return hasUpper && hasLower && hasNumber && hasSpecial && pwLen;
        }

        function checkBothPassword() {
          const oldValid = validatePassword(old_pass.value);
          const newValid = validatePassword(new_pass.value);
          if (old_pass.value == new_pass.value) {
            btnChangePass.disabled = !(oldValid && newValid);
          } else {
            btnChangePass.disabled = true;
          }
          toggleRule(
            "rule-same",
            old_pass.value == new_pass.value &&
              old_pass.value.trim() !== "" &&
              new_pass.value.trim() !== "",
          );
        }

        function toggleRule(id, isValid) {
          const rule = document.getElementById(id);
          rule.classList.toggle("valid", isValid);
        }
        $(document).ready(function () {
          $("select").select2({
            width: "100%",
          });

          old_pass = document.getElementById("kata-laluan-baru");
          new_pass = document.getElementById("sah-kata-laluan-baru");
          old_pass.addEventListener("keypress", function (e) {
            if (e.key === " ") {
              e.preventDefault();
            }
          });

          new_pass.addEventListener("keypress", function (e) {
            if (e.key === " ") {
              e.preventDefault();
            }
          });
          old_pass.addEventListener("input", checkBothPassword);
          new_pass.addEventListener("input", checkBothPassword);

          old_pass.addEventListener("input", function () {
            const pw = this.value;

            toggleRule("rule-lower", /[a-z]/.test(pw));
            toggleRule("rule-upper", /[A-Z]/.test(pw));
            toggleRule("rule-number", /[0-9]/.test(pw));
            toggleRule("rule-special", /[^A-Za-z0-9]/.test(pw));
            toggleRule("rule-min", pw.length >= 8);
          });

          const btnpass = document.getElementById("btn-password");
          const btnprof = document.getElementById("btn-edit-profile");
          const xcontent = document.getElementById("x_content");

          btnpass.addEventListener("click", () => {
            xcontent.classList.add("right-panel-active");
          });

          btnprof.addEventListener("click", () => {
            xcontent.classList.remove("right-panel-active");
          });
        });
      </script>
    </div>
  </body>
</html>
