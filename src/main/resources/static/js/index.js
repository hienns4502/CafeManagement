
document.getElementById('form-file').addEventListener('change', function(event) {
    const file = event.target.files[0];

    if (file) {
        if (!file.type.startsWith('image/')) {
            alert('Vui lòng chọn một file ảnh hợp lệ.');
            return;
        }
           const preview = document.getElementById('preview-avatar');
           preview.src = URL.createObjectURL(file)
    }
});
$(document).ready(function() {
    // 1. Tự động active khi tải trang nếu đang ở trang con (khối collapse có sẵn class 'show')
    $('.collapse.show').each(function() {
        var targetId = '#' + this.id;
        $('[data-target="' + targetId + '"]').addClass('active');
    });

    // 2. Bắt sự kiện click vào các nút toggle menu
    $('[data-toggle="collapse"]').on('click', function() {
        // Đổi trạng thái active ngay trên nút được bấm
        $(this).toggleClass('active');
    });
});
 function selectUnit(event, element) {
        event.preventDefault();
        document.getElementById('unitIdInput').value = element.getAttribute('data-id');
        document.getElementById('selectedUnitText').innerText = element.innerText;
    }
