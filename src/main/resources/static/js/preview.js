
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