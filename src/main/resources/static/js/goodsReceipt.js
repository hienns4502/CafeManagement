
(function(){
    $(document).ready(function() {
        initializeSelect2();
    });

    // Lấy đơn vị tính khi chọn Select2
    $('#tempHangHoa').on('select2:select', function (e) {
        let optionElement = e.params.data.element;
        let unit = optionElement ? optionElement.getAttribute('data-unit') || '' : '';
        document.getElementById('tempDvt').innerText = unit;
    });

    // Thêm dòng mới
    document.getElementById('btnAddRow').addEventListener('click', function(e) {
        e.preventDefault();
        let hangHoaSelect = $('#tempHangHoa');
        let hangHoaId = hangHoaSelect.val();
        let hangHoaName = hangHoaSelect.find('option:selected').text();

        let soLuong = parseInt(document.getElementById('tempSoLuong').value) || 0;
        let donGia = parseFloat(document.getElementById('tempDonGia').value) || 0;
        let dvt = document.getElementById('tempDvt').innerText;

        if (!hangHoaId || soLuong <= 0 || donGia <= 0) {
            alert("Vui lòng chọn hàng hóa và nhập đầy đủ thông tin!");
            return;
        }

        let thanhTien = soLuong * donGia;
        let rowCount = document.querySelectorAll('#detailTable tbody tr').length;

        let tbody = document.querySelector('#detailTable tbody');
        let tr = document.createElement('tr');
        tr.innerHTML = `
            <td class="text-center">${rowCount + 1}</td>
            <td>
                ${hangHoaName}
                <input type="hidden" name="goodsReceiptDetailDTOS[${rowCount}].hangHoa.id" value="${hangHoaId}">
                <input type="hidden" name="goodsReceiptDetailDTOS[${rowCount}].soLuong" value="${soLuong}">
                <input type="hidden" name="goodsReceiptDetailDTOS[${rowCount}].donGia" value="${donGia}">
            </td>
            <td class="text-center">${soLuong}</td>
            <td class="text-center">${dvt}</td>
            <td class="text-right">${donGia.toLocaleString()}</td>
            <td class="text-right font-weight-bold">${thanhTien.toLocaleString()}</td>
            <td class="text-center"><button type="button" class="btn btn-danger btn-sm remove-row">Xóa</button></td>
        `;

        tbody.appendChild(tr);

        // Reset form
        hangHoaSelect.val(null).trigger('change');
        document.getElementById('tempSoLuong').value = "";
        document.getElementById('tempDvt').innerText = "";
        document.getElementById('tempDonGia').value = "";

        calculateTotal('#detailTable', '#tongTienDisplay');
    });

    // Xử lý sự kiện xóa dòng chung
    document.addEventListener('click', function(e) {
        let removeBtn = e.target.closest('.remove-row');
        if (removeBtn) {
            removeBtn.closest('tr').remove();
            reindexTableRows('#detailTable', 'goodsReceiptDetailDTOS', '#tongTienDisplay');
        }
    });
})();