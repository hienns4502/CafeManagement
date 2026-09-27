$(document).ready(function() {
        // Xử lý form search nếu có
        const startDate = document.getElementById('startDate');
        const endDate = document.getElementById('endDate');
        const formSearch = document.getElementById('formSearch');
        if (startDate && endDate && formSearch) {
            [startDate, endDate].forEach(input => {
                input.addEventListener('change', function() {
                    if (startDate.value !== '' || endDate.value !== '') {
                        formSearch.submit();
                    }
                });
            });
        }
    });
