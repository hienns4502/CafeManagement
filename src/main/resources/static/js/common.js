// 1. Khởi tạo Select2 chung
function initializeSelect2() {
    $('.select').each(function() {
        if (!$(this).hasClass("select2-hidden-accessible")) {
            $(this).select2({
                theme: 'bootstrap4',
                placeholder: $(this).attr('data-placeholder') || "-- Chọn --",
                allowClear: true,
                width: '100%'
            });
        }
    });
}

// 2. Tính lại tổng tiền toàn bảng (Dựa vào cột Thành tiền cuối cùng)
function calculateTotal(tableSelector, totalDisplaySelector, currencySymbol = "") {
    let rows = document.querySelectorAll(`${tableSelector} tbody tr`);
    let total = 0;
    rows.forEach(row => {
        // Lấy giá trị tiền từ cột thành tiền
        let cells = row.querySelectorAll('td');
        let cellText = cells[cells.length - 2].innerText.replace(/[^\d]/g, '');
        total += parseFloat(cellText) || 0;
    });

    let totalEl = document.querySelector(totalDisplaySelector);
    if (totalEl) {
        totalEl.innerText = total.toLocaleString() + (currencySymbol ? " " + currencySymbol : "");
    }
    return total;
}

// 3. Xử lý đánh lại STT và Index name input
function reindexTableRows(tableSelector, propertyPrefix, totalDisplaySelector, currencySymbol = "") {
    let rows = document.querySelectorAll(`${tableSelector} tbody tr`);
    rows.forEach((row, index) => {
        // Cập nhật STT cột 1
        row.cells[0].innerText = index + 1;

        // Cập nhật lại index cho các input ẩn (name="prefix[index].property")
        let inputs = row.querySelectorAll('input[type="hidden"]');
        inputs.forEach(input => {
            let name = input.getAttribute('name');
            let newName = name.replace(/\[\d+\]/, `[${index}]`);
            input.setAttribute('name', newName);
        });
    });

    // Tính lại tổng tiền sau khi xóa/thêm
    if (totalDisplaySelector) {
        calculateTotal(tableSelector, totalDisplaySelector, currencySymbol);
    }

}

