 $(document).ready(function() {
        initializeSelect2();
    });

function initializeSelect2() {
$('.select').each(function() {
    if (!$(this).hasClass("select2-hidden-accessible")) {
        $(this).select2({
            theme: 'bootstrap4',
            placeholder: "-- Chọn --",
            allowClear: true,
            width: '100%'
        });
    }
});
}
let rowIndex = 0;
let tongTien = 0;

$('#tempHangHoa').on('select2:select', function (e) {
    let selectedData = e.params.data;
    // Lấy thuộc tính data-unit thông qua element gốc của option được chọn
    let optionElement = selectedData.element;
    let unit = optionElement ? optionElement.getAttribute('data-unit') || '' : '';

    document.getElementById('tempDvt').innerText = unit;
});

document.getElementById('btnAddRow').addEventListener('click', function(e) {
    e.preventDefault();
    let hangHoaSelect = $('#tempHangHoa');
    let hangHoaId = hangHoaSelect.val();

    let hangHoaName = hangHoaSelect.find('option:selected').text();

    let soLuong = parseInt(document.getElementById('tempSoLuong').value) || 0;
    let donGia = parseFloat(document.getElementById('tempDonGia').value) || 0;
    let dvt = document.getElementById('tempDvt').innerText;

    if (!hangHoaId || soLuong <= 0 || donGia<=0) {
        alert("Vui lòng chọn hàng hóa và nhập đầy đủ thông tin!");
        return;
    }

    let thanhTien = soLuong * donGia;
    tongTien +=thanhTien;
    let tbody = document.querySelector('#detailTable tbody');

    let tr = document.createElement('tr');
    tr.innerHTML = `
        <td>${rowIndex + 1}</td>
        <td>
            ${hangHoaName}
            <input type="hidden" name="goodsReceiptDetailDTOS[${rowIndex}].hangHoa.id" value="${hangHoaId}">
            <input type="hidden" name="goodsReceiptDetailDTOS[${rowIndex}].soLuong" value="${soLuong}">
            <input type="hidden" name="goodsReceiptDetailDTOS[${rowIndex}].donGia" value="${donGia}">
        </td>
        <td>${soLuong}</td>
        <td>${dvt}</td>
        <td>${donGia.toLocaleString()}</td>
        <td>${thanhTien.toLocaleString()}</td>
        <td><button type="button" class="btn btn-danger btn-sm remove-row">Xóa</button></td>
    `;

    tbody.appendChild(tr);
    rowIndex++;

    // Reset lại form nhập tạm cho Select2
    $('#tempHangHoa').val(null).trigger('change');
    document.getElementById('tempSoLuong').value = "";
    document.getElementById('tempDvt').innerText = "";
    document.getElementById('tempDonGia').value = "";
    document.getElementById('tongTienDisplay').innerText = tongTien.toLocaleString();
});

// Xử lý nút xóa dòng trong bảng
document.addEventListener('click', function(e) {
    if (e.target && e.target.classList.contains('remove-row')) {
        e.target.closest('tr').remove();
        reindexRows();
    }
});

function reindexRows() {
    let rows = document.querySelectorAll('#detailTable tbody tr');
    rowIndex = 0;
    rows.forEach((row, index) => {
        row.cells[0].innerText = index + 1;
        let inputs = row.querySelectorAll('input[type="hidden"]');
        inputs.forEach(input => {
            let name = input.getAttribute('name');
            let newName = name.replace(/\[\d+\]/, `[${index}]`);
            input.setAttribute('name', newName);
        });
        rowIndex = index + 1;
    });
}
