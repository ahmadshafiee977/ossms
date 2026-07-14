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
            <h2>BORANG PERMINTAAN STOK</h2>
          </div>
          <h5 align="center" style="color: black">(INDIVIDU KEPADA STOR)</h5>
          <div class="x_content">
            <div class="col-md-12 col-sm-12">
              <div>
              <div class="row">
                  <div class="col-md-6">
                    <h5>Perihal Stok</h5>
                  </div>
                  <div class="col-md-1">
                    <h5>Kuantiti Dimohon</h5>
                  </div>
                  <div class="col-md-1">
                    <h5>Stok</h5>
                  </div>
                  <div class="col-md-2">
                    <h5>Catatan</h5>
                  </div>
					
				<c:if test="${req.requesterid != sessionScope.staff.staffid && req.requeststatus == 'BARU' && sessionScope.staff.staffrole != 'BIASA'}">
				    <div class="col-md-1">
				        <h5>Status</h5>
				    </div>
				</c:if>
                  
               </div>
               <c:choose>
				    <c:when test="${sessionScope.staff.staffid == req.requesterid && req.requeststatus == 'BARU'}">
				        <c:set var="formAction" value="RequestController"/>
				    </c:when>
				
				    <c:when test="${(sessionScope.staff.staffrole == 'ADMIN' || sessionScope.staff.staffrole == 'SUPERADMIN') && sessionScope.staff.staffid != req.requesterid}">
				        <c:set var="formAction" value="InheritanceController?action=approve"/>
				    </c:when>
				
				    <c:otherwise>
				        <c:set var="formAction" value=""/>
				    </c:otherwise>
				</c:choose>
               
			
               
               <form method="POST" action="${formAction}">
               <% int counter=1; %>
               <input type="hidden" name="requestid" value="${req.requestid}">
               <div id="dynamic-container">
               <c:forEach var="ri" items="${req.request_Item}">
              
				    <div class="row" id="row-<%= counter %>">
		                    <div class="col-md-6">
		                      <c:choose>

							    <c:when test="${(sessionScope.staff.staffrole == 'ADMIN' || sessionScope.staff.staffrole == 'SUPERADMIN') && sessionScope.staff.staffid != req.requesterid}">
							        <input type="text"
							               class="form-control"
							               value="${ri.item.itemname}"
							               readonly />
							
							        <!-- Keep the item ID so it's submitted with the form -->
							        <input type="hidden"
							               name="item"
							               value="${ri.item.itemid}" />
							    </c:when>
							
							    <c:otherwise>
							        <select name="item" id="item-1" class="form-control item-select" required>
							            <option value="" selected disabled>
							                --Pilih Barang--
							            </option>
							
							            <c:forEach var="item" items="${items}">
							                <option value="${item.itemid}"
							                    <c:if test="${item.itemid == ri.item.itemid}">
							                        selected="selected"
							                    </c:if>>
							                    ${item.itemname}
							                </option>
							            </c:forEach>
							        </select>
							    </c:otherwise>
							
							</c:choose>
		                    </div>
		                    <div class="col-md-1">
		                      <input
		                        type="number"
		                        name="quant"
		                        id="quant-1"
		                        class="form-control quant-input"
		                      	required
		                      	value="${ri.requestedamount}"
		                      />
		                    </div>
		                    <div class="col-md-1">
		                      <b>
		                        <h4 class="stock-text">
		                         <c:forEach var = "item" items="${items}">
			                       <c:if test="${item.itemid == ri.item.itemid}">
								        ${item.currentstock}
								    </c:if>
		                        </c:forEach>
		                        	
		                        
		                        
		                        </h4>
		                      </b>
		                    </div>
		                    <div class="col-md-2">
		                      <input
		                        type="text"
		                        name="note"
		                        id="note-1"
		                        class="form-control"
		                        value="${ri.note}"
		                      />
		                    </div>
		                    <c:choose>
							    <c:when test="${req.requesterid != sessionScope.staff.staffid && req.requeststatus == 'BARU' && sessionScope.staff.staffrole != 'BIASA'}">
							        <div class="col-md-2">
							            <select name="status" id="status-<%= counter %>" required class="admin-enable">
							                <option disabled selected></option>
							                <option value="LULUS"
							                <c:if test="${ri.item.currentstock < ri.requestedamount}">
						                        disabled="disabled"
						                    </c:if>>
							                &#10004;</option>
							                <option value="TOLAK"
							                <c:if test="${ri.item.currentstock < ri.requestedamount}">
						                        selected="selected"
						                    </c:if>>
							                &#10006;</option>
							            </select>
							        </div>
							    </c:when>
							   
							</c:choose>
							
		                     <% if (counter!= 1){ %>
		                     	<c:if test="${req.requesterid == sessionScope.staff.staffid && req.requeststatus == 'BARU'}">
							        <div class="col-md-1">
							            <button class="btn btn-danger btn-delete"
							            		type="button"
							                    data-row="<%= counter %>"
							                    title="Buang">-</button>
							        </div>
							        </c:if>
							    <% } %>
							     <% counter++; %>
		                 
		                   
		                   
		                  </div>
		                  
		                
				</c:forEach>
				</div>
					<c:if test="${sessionScope.staff.staffid == req.requesterid && req.requeststatus == 'BARU'}">
			            <button class="btn btn-dark" id="btn-add-row" type="button">Tambah Item</button>
			            <br><br>
			            <button class="btn btn-danger" onclick="return confirm('Padam permintaan ini?')" value="delete" name="action">
			                Padam
			            </button>
			            <button class="btn btn-success pull-right" style="margin-right:200px;" type="submit">Simpan</button>
			        </c:if>
                
             

              <br />
              <c:if test="${sessionScope.staff.staffid != req.requesterid && req.requeststatus == 'BARU' && sessionScope.staff.staffrole != 'BIASA'}">
				<div class="row">
	                <div class="col-md-4">
	                  <label for="col-date">Tarikh Pengambilan Barang</label>
	                  <input
	                    type="date"
	                    name="col-date"
	                    id="col-date"
	                    class="form-control admin-enable"
	                    min=""
	                    required
	                  />
	                </div>
	            </div>
			  </c:if>
              
                
                
                
             

              <br /><br />
            
              <div>
			    <c:choose>
			
			        
			        
			
			        
			        <c:when test="${(sessionScope.staff.staffrole == 'ADMIN' || sessionScope.staff.staffrole == 'SUPERADMIN') && req.requeststatus == 'BARU' && sessionScope.staff.staffid != req.requesterid}">
			
			           
			            <button class="btn btn-danger" type="button" data-toggle="modal" name="btn-tolak" data-target="#form-tolak-permintaan">
			                Tolak
			            </button>
			            
			             <button class="btn btn-success" name="btn-lulus">
			                Lulus
			            </button>
			
			        </c:when>
			
			        
			        <c:when test="${(sessionScope.staff.staffrole == 'ADMIN' || sessionScope.staff.staffrole == 'SUPERADMIN') && req.requeststatus == 'DILULUSKAN'}">
			            <button class="btn btn-success" name = "btn-selesai">
			                Selesai
			            </button>
			        </c:when>
			
			    </c:choose>
			</div>
              </form>
            </div>
          </div>
        </div>
      </div>
      <form method="POST" action="InheritanceController?action=reject">
	      <div class="modal fade" id="form-tolak-permintaan">
	        <div class="modal-dialog">
	          <div class="modal-content">
	            <div class="modal-header">
	              <button
	                type="button"
	                class="close modal-close"
	                data-dismiss="modal"
	              >
	                &times;
	              </button>
	              <h4 class="modal-title">Pengesahan Penolakan</h4>
	            </div>
	            <div class="modal-body">
	              <div class="x_panel">
	                <div class="row">
	                  <div class="col-md-12 col-sm-12">
	                  
		                  <input type="hidden" name="requestid" value="${req.requestid}">
		                    <label for="rej-reason" >Sebab ditolak</label>
		                    <input
		                      type="text"
		                      class="form-control admin-enable"
		                      id="rej-reason"
		                      name="rej-reason"
		                      required
		                    />
	                  
	                  </div>
	                </div>
	              </div>
	            </div>
	            <div class="modal-footer">
	              <button
	                type="submit"
	                class="btn btn-danger"
	                
	              >
	                Tolak
	              </button>
	            </div>
	          </div>
	        </div>
          </form>
      </div>        
        
        <script>
          document.addEventListener("DOMContentLoaded", function () {
            const items = [
            	<% 
            	List<Item> items  = (List<Item>) request.getAttribute("items");
            	
            	if(items != null){
            		
	            	for (int i = 0; i < items.size(); i++) {
	            	    Item item = items.get(i);
	            	%>
	            	{
	            	    itemid: <%= item.getItemid() %>,
	            	    itemname: "<%= item.getItemname() %>"
	            	}<%= (i < items.size() - 1) ? "," : "" %>
	            	<%
	            	}
            	}
            	%>	
            ];

            $("select").select2();

            function generateOptions() {
              return items
                .map(
                  (item) =>
                    `<option value="\${item.itemid}">
                     \${item.itemname}
                    </option>
                `,
                )
                .join("");
            }
            let rowCount = <%= counter %>;
            
            document
            .getElementById("btn-add-row")
            .addEventListener("click", function () {
            	rowCount++;
            	const row = document.createElement("div");
                row.className = "row";
                row.id = `row-\${rowCount}`;
                
                row.innerHTML = `
                    <div class="col-md-6">
                        <select name="item" id="ps-\${rowCount}" class="form-control item-select" required>
                            <option value="" selected disabled>--Pilih Barang--</option>
                            \${generateOptions()}
                        </select>
                    </div>
                    <div class="col-md-1">
                        <input type="number" name="quant" id="quant-\${rowCount}" class="form-control quant-input" required>
                    </div>
                    <div class="col-md-1">
                        <b><h4 class="stock-text"></h4></b>
                    </div>
                    <div class="col-md-2">
                        <input type="text" name="note" id="note-\${rowCount}" class="form-control">
                    </div>
                    <div class="col-md-1">
                        <button type="button" class="btn btn-danger btn-delete" data-row="\${rowCount}" title="Buang">-</button>
                    </div>
                        `;
                document.getElementById("dynamic-container").appendChild(row);
                $("select").select2();
                updateSelectOptions();
                
              });

            document
              .getElementById("dynamic-container")
              .addEventListener("click", function (e) {
                if (e.target.classList.contains("btn-delete")) {
                  const rowId = e.target.getAttribute("data-row");
                  document.getElementById(`row-\${rowId}`).remove();
                  updateSelectOptions();
                }
              });
            })
           
            function updateSelectOptions() {
			
			    let selectedValues = [];
			
			    // Get all selected values
			    $(".item-select").each(function () {
			        let value = $(this).val();
			
			        if (value) {
			            selectedValues.push(value);
			        }
			    });
			
			
			    // Disable selected options in other selects
			    $(".item-select").each(function () {
			
			        let currentValue = $(this).val();
			
			        $(this).find("option").each(function () {
			
			            let optionValue = $(this).val();
			
			            // Ignore placeholder
			            if (optionValue === "") {
			                return;
			            }
			
			            if (selectedValues.includes(optionValue) && optionValue !== currentValue) {
			                $(this).prop("disabled", true);
			            } else {
			                $(this).prop("disabled", false);
			            }
			
			        });
			
			
			        // Refresh Select2
			        $(this).select2();
			    });
			}
			
			
			// For dynamic selects
			$("#dynamic-container").on("change", ".item-select", function () {
			    updateSelectOptions();
			});
			
			
			// When adding a new row
			function initializeSelect2(element) {
			
			    $(element).select2({
			        width: "100%"
			    });
			
			    updateSelectOptions();
			}
			
			
			$('#dynamic-container').on('select2:select', '.item-select', function () {

			    const select = this;
			    const row = $(select).closest('.row');
			    const quant = row.find('.quant-input');
			    const stock = row.find('.stock-text');

			    fetch('${pageContext.request.contextPath}/ItemController?action=getStock&itemId=' + $(select).val())
			        .then(response => response.json())
			        .then(data => {

			            stock.text(data.currentStock);

			            quant.prop('max', data.currentStock);

			            quant.val('');

			        })
			        .catch(error => {

			            console.log(error);

			            stock.text("Error");

			        });

			});

			$('#dynamic-container').on('input', '.quant-input', function () {

			    const max = parseInt($(this).prop('max')) || 0;
			    let value = parseInt($(this).val()) || 0;

			    if (value > max) {
			        $(this).val(max);
			    }

			});
			
			document.addEventListener("DOMContentLoaded", function () {

			    const isRequester =
			        ${sessionScope.staff.staffid == req.requesterid};

			    const isAdmin =
			        ${sessionScope.staff.staffrole == 'ADMIN' ||
			          sessionScope.staff.staffrole == 'SUPERADMIN'};

			    if (!isRequester && isAdmin) {

			        document.querySelectorAll("input, select").forEach(function (el) {

			            if (!el.classList.contains("admin-enable")) {
			                el.readOnly = true;
			            }

			        });

			    }

			});
        </script>