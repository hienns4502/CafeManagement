//(function(){
//    $(document).ready(function() {
//        initializeSelect2();
//    });
//
//    function initializeSelect2() {
//        $('.select').each(function() {
//            if (!$(this).hasClass("select2-hidden-accessible")) {
//                $(this).select2({
//                    theme: 'bootstrap4',
//                    placeholder: "-- Chọn --",
//                    allowClear: true,
//                    width: '100%'
//                });
//            }
//        });
//    }
//
//    let rowIndex = 0;
//    let tongTien = 0;
//    let unit = '';
//    let stock = 0;
//    let price = 0;
//
//    // Lấy thông tin phụ từ option khi chọn Select2
//    $('#tempMerchandise').on('select2:select', function (e) {
//        let selectedData = e.params.data;
//        let optionElement = selectedData.element;
//        unit = optionElement ? optionElement.getAttribute('data-unit') || '' : '';
//        stock = optionElement ? optionElement.getAttribute('data-stock') || '' : '';
//        price = parseFloat(optionElement ? optionElement.getAttribute('data-price') || 0 : 0);
//    });
//
//    // Sự kiện nút Thêm hàng hóa vào bảng
//    document.getElementById("btnAddRow").addEventListener("click", function(e) {
//        e.preventDefault();
//
//        let merchandiseSelect = $('#tempMerchandise');
//        let merchandiseId = merchandiseSelect.val();
//        let merchandiseName = merchandiseSelect.find('option:selected').text();
//        let soLuong = parseInt(document.getElementById("tempSoLuong").value) || 0;
//
//        if (!merchandiseId || soLuong <= 0) {
//            alert("Vui lòng chọn hàng hóa và điền số lượng lớn hơn 0!");
//            return;
//        }
//
//        let thanhTien = soLuong * price;
//        tongTien += thanhTien;
//
//        let tbody = document.querySelector('#detailTable tbody');
//        let tr = document.createElement('tr');
//
//        tr.innerHTML = `
//            <td class="text-center">${rowIndex + 1}</td>
//            <td class="font-weight-bold">
//                ${merchandiseName}
//                <input type="hidden" name="deliveryNoteDetails[${rowIndex}].hangHoa.id" value="${merchandiseId}">
//                <input type="hidden" name="deliveryNoteDetails[${rowIndex}].soLuong" value="${soLuong}">
//            </td>
//            <td class="text-center">${unit}</td>
//            <td class="text-center"><span class="badge badge-success">${stock}</span></td>
//            <td class="text-center">${soLuong}</td>
//            <td class="text-right">${price.toLocaleString()} ₫</td>
//            <td class="text-right font-weight-bold">${thanhTien.toLocaleString()} ₫</td>
//            <td class="text-center">
//                <button type="button" class="btn btn-sm btn-outline-danger remove-row">
//                    <i class="fas fa-trash"></i>
//                </button>
//            </td>
//        `;
//
//        tbody.appendChild(tr);
//        rowIndex++;
//
//        // Reset lại form nhập tạm
//        $('#tempMerchandise').val(null).trigger('change');
//        document.getElementById("tempSoLuong").value = "1";
//        unit = '';
//        stock = 0;
//        price = 0;
//
//        // Cập nhật tổng tiền hiển thị ở footer
//        document.getElementById("showTotal").innerText = tongTien.toLocaleString() + " ₫";
//    });
//
//    // Xử lý nút xóa dòng
//    document.addEventListener('click', function(e) {
//        let removeBtn = e.target.closest('.remove-row');
//        if (removeBtn) {
//            let tr = removeBtn.closest('tr');
//            tr.remove();
//            reindexRows();
//        }
//    });
//
//    function reindexRows() {
//        let rows = document.querySelectorAll('#detailTable tbody tr');
//        rowIndex = 0;
//        tongTien = 0;
//
//        rows.forEach((row, index) => {
//            row.cells[0].innerText = index + 1;
//            let inputs = row.querySelectorAll('input[type="hidden"]');
//            inputs.forEach(input => {
//                let name = input.getAttribute('name');
//                let newName = name.replace(/\[\d+\]/, `[${index}]`);
//                input.setAttribute('name', newName);
//            });
//            rowIndex = index + 1;
//        });
//    }
//})();

(function(){
    $(document).ready(function() {
        initializeSelect2();
    });

    let unit = '', stock = 0, price = 0;

    $('#tempMerchandise').on('select2:select', function (e) {
        let optionElement = e.params.data.element;
        unit = optionElement ? optionElement.getAttribute('data-unit') : '';
        stock = optionElement ? optionElement.getAttribute('data-stock')  : '';
        price = parseFloat(optionElement ? optionElement.getAttribute('data-price')  : 0);
    });

    document.getElementById("btnAddRow").addEventListener("click", function(e) {
        e.preventDefault();

        let merchandiseSelect = $('#tempMerchandise');
        console.log(merchandiseSelect);
        let merchandiseId = merchandiseSelect.val();
        console.log(merchandiseId)
        let merchandiseName = merchandiseSelect.find('option:selected').text();
        let soLuong = parseInt(document.getElementById("tempSoLuong").value) || 0;

        if (!merchandiseId || soLuong <= 0) {
            alert("Vui lòng chọn hàng hóa và điền số lượng lớn hơn 0!");
            return;
        }

        if(stock<soLuong){
            alert("Số lượng hàng hóa trong kho không đủ để xuất, vui lòng chọn lại số lượng cần xuất");
            return;
        }

        let thanhTien = soLuong * price;
        let rowCount = document.querySelectorAll('#detailTable tbody tr').length;

        let tbody = document.querySelector('#detailTable tbody');
        let tr = document.createElement('tr');

        tr.innerHTML = `
            <td class="text-center">${rowCount + 1}</td>
            <td class="font-weight-bold">
                ${merchandiseName}
                <input type="hidden" name="deliveryNoteDetails[${rowCount}].hangHoa.id" value="${merchandiseId}">
                <input type="hidden" name="deliveryNoteDetails[${rowCount}].soLuong" value="${soLuong}">
            </td>
            <td class="text-center">${unit}</td>
            <td class="text-center"><span class="badge badge-success">${stock}</span></td>
            <td class="text-center">${soLuong}</td>
            <td class="text-right">${price.toLocaleString()} ₫</td>
            <td class="text-right font-weight-bold">${thanhTien.toLocaleString()} ₫</td>
            <td class="text-center">
                <button type="button" class="btn btn-sm btn-outline-danger remove-row">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        `;

        tbody.appendChild(tr);

        // Reset form
        merchandiseSelect.val(null).trigger('change');
        document.getElementById("tempSoLuong").value = "1";
        unit = ''; stock = 0; price = 0;

        calculateTotal('#detailTable', '#showTotal', '₫');
    });

    document.addEventListener('click', function(e) {
        let removeBtn = e.target.closest('.remove-row');
        if (removeBtn) {
            removeBtn.closest('tr').remove();
            reindexTableRows('#detailTable', 'deliveryNoteDetails', '#showTotal', '₫');
        }
    });
})();