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
              <h2>
                BORANG PERMINTAAN STOK
                <br />
              </h2>
            </div>
            
	            <h5 align="center" style="color: black">(INDIVIDU KEPADA STOR)</h5>
	            <div class="x_content">
	              <div class="col-md-12 col-sm-12">
	              	<form method="POST" action="RequestController">
		                <div id="dynamic-container">
		                  <div class="row">
		                    <div class="col-md-6">
		                      <h5>No Kod/Perihal Stok</h5>
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
		                  </div>
		                  <div class="row">
		                    <div class="col-md-6">
		                      <select name="item" id="item-1" class="form-control item-select" required>
		                        <option value="" selected disabled>
		                          --Pilih Barang--
		                        </option>
		                        <% int counter = 1; %>
		                        <c:forEach var = "item" items="${items}">
			                        <option value="${item.itemid}">${item.itemname }</option>
		                        </c:forEach>
		                        
		                      </select>
		                    </div>
		                    <div class="col-md-1">
		                      <input
		                        type="number"
		                        name="quant"
		                        id="quant-1"
		                        class="form-control quant-input"
		                      	required
		                      />
		                    </div>
		                    <div class="col-md-1">
		                      <b>
		                        <h4 class="stock-text"></h4>
		                      </b>
		                    </div>
		                    <div class="col-md-2">
		                      <input
		                        type="text"
		                        name="note"
		                        id="note-1"
		                        class="form-control"
		                      />
		                    </div>
		                  </div>
		                </div>
		                <button type="button" class="btn btn-primary" id="btn-add-row">
		                  Tambah Item
		                </button>
		                <br>
		                <button
		                type="submit"
		                  class="btn btn-success pull-right"
		                  style="margin-right:200px;"
		                >
		                  Hantar Permohonan
		                </button>
	              	</form>
	              </div>
	              
	            </div>
	          </div>
          <!-- x_content -->
          
        </div>
        <!-- x_panel -->
        
        
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
            let rowCount = 1;
            
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
        </script>