<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List" %>
<%@ include file = "header.jsp" %>

<div class="right_col">
        <div class="x_panel">
          <div class="x_title">
            <h2>LAPORAN</h2>
          </div>
          <div class="x_content">
          
          
          <div class="table-responsive">
			    <table id="reportTable" class="table table-bordered table-striped table-hover" style="color:black;">
			        <thead>
			       		 
			        	<tr style="border=0">
			        		<td colspan=2>
			        		KEMENTERIAN JABATAN : 
			        		</td>
			        		<td>
			        		:
			        		</td>
			        		<td colspan=7>
			        			MAJLIS DAERAH TANGKAK
			        		</td>
			        	
			        	</tr>
			        	<tr style="border=0">
			        		<td colspan=2>
			        		KATEGORI STOK : 
			        		</td>
			        		<td>
			        		:
			        		</td>
			        		<td colspan=7>
			        			STOR ALAT TULIS
			        		</td>
			        	
			        	</tr>
			            <tr>
			                <th>Sukuan</th>
			                <th>Jumlah Permintaan</th>
			                <th>Baru</th>
			                <th>Diluluskan</th>
			                <th>Ditolak</th>
			                <th>Selesai</th>
			                <th>Jumlah Barang Dipinda</th>
			                <th>Jumlah Barang Lulus</th>
			                <th>Jumlah Barang Tolak</th>
			                <th>Jumlah Nilai Stok (RM)</th>
			            </tr>
			        </thead>
			
			        <tbody>
			            <c:choose>
			                <c:when test="${not empty report}">
			                    <c:forEach var="r" items="${report}">
			                        <tr>
			                            <td>${r.quarter}</td>
			                            <td>${r.total_requests}</td>
			                            <td>${r.baru}</td>
			                            <td>${r.diluluskan}</td>
			                            <td>${r.ditolak}</td>
			                            <td>${r.selesai}</td>
			                            <td>${r.total_items_requested}</td>
			                            <td>${r.total_items_lulus}</td>
			                            <td>${r.total_items_tolak}</td>
			                            <td>RM ${r.current_stock_value}</td>
			                        </tr>
			                    </c:forEach>
			                    
			                    
			                </c:when>
			
			                <c:otherwise>
			                    <tr>
			                        <td colspan="10" class="text-center">
			                            No report data available.
			                        </td>
			                    </tr>
			                </c:otherwise>
			            </c:choose>
			            
			            <tr style="border=0; background:white !important;">
			            	<td colspan=6>
			            		Disemak Oleh
			            	</td>
			            	<td colspan=4>
			            		Disahkan Oleh
			            	</td>
			            </tr>
						 <tr style="border=0; background:white !important;">
			            	<td colspan=6>
			            		&nbsp;
			            	</td>
			            	<td colspan=4>
			            		&nbsp;
			            	</td>
			            </tr>			
			             <tr style="border=0; background:white !important;">
			            	<td colspan=6>
			            		..................................................
			            	</td>
			            	<td colspan=4>
			            		..................................................
			            	</td>
			            </tr>
			            <tr style="border=0; background:white !important;">
			            	<td colspan=6>
			            		(Tandatangan Ketua Bahagian)
			            	</td>
			            	<td colspan=4>
			            		(Tandatangan Pegawai Pengawal)
			            	</td>
			            </tr>     
			            <tr style="border=0; background:white !important;">
			            	<td colspan=6>
			            		Nama:
			            	</td>
			            	<td colspan=4>
			            		Nama:
			            	</td>
			            </tr>       
			            <tr style="border=0; background:white !important;">
			            	<td colspan=6>
			            		Tarikh:
			            	</td>
			            	<td colspan=4>
			            		Tarikh:
			            	</td>
			            </tr>              
			        </tbody>
			    </table>
			</div>
          
    <div class="text-right" style="margin-bottom:15px;">
    <button class="btn btn-danger" onclick="downloadPDF()">
        <i class="fa fa-file-pdf-o"></i> Muat Turun
    </button>
</div>      
        
          
          
          
          
            </div>
          </div>
        </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.8.2/jspdf.plugin.autotable.min.js"></script>
      
      
      <script>
function downloadPDF() {

    const { jsPDF } = window.jspdf;

    const doc = new jsPDF("l", "mm", "a4");
    const pageWidth = doc.internal.pageSize.getWidth();

    doc.setFontSize(16);
    doc.text("LAPORAN TAHUN", pageWidth /2 , 15, {align:"center"});

    doc.autoTable({
        html: "#reportTable",
        startY: 22,
        styles: {
            fontSize: 8
        },
        headStyles: {
            fillColor: [255, 255, 255],
            textColor: [0, 0, 0],
            lineColor: [0, 0, 0]
        }
       
    });

    doc.save("Quarterly_Request_Report.pdf");
}
</script>
      
      <script>
        document
          .getElementById("type-select")
          .addEventListener("change", function () {
            const month = document.getElementById("monthly-select");
            const quarter = document.getElementById("quarterly-select");
            const year = document.getElementById("yearly-select");
            month.disabled = true;
            quarter.disabled = true;

            if (this.value == "monthly") {
              month.disabled = false;
            } else if (this.value == "quarterly") {
              quarter.disabled = false;
            }
          });
        
       
      </script>