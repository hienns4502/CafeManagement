
    function addNewIngredient() {
        let selectElem = document.getElementById('inputMerchandise');
        let quantityElem = document.getElementById('inputQuantity');
        let unitElem = document.getElementById('inputUnit');

        let merchandiseId = selectElem.value;
        let merchandiseName = selectElem.options[selectElem.selectedIndex].text;

        let quantity = quantityElem.value;

        let unitId = unitElem.value;
        let unitName = unitElem.options[unitElem.selectedIndex].text;

        if (!merchandiseId) {
            alert("Vui lòng chọn thành phần!");
            return;
        }
        if (!quantity || quantity <= 0) {
            alert("Vui lòng nhập khối lượng hợp lệ!");
            quantityElem.focus();
            return;
        }

        if(!unitId){
            alert("Vui lòng chọn đơn vị tính!")
        }

        let tbody = document.getElementById('recipeTableBody');
        let rowCount = tbody.getElementsByClassName('recipe-row').length;

        let newRow = document.createElement('tr');
        newRow.className = 'recipe-row';

        newRow.innerHTML = `
            <td>
                ${merchandiseName}
                <input type="hidden" name="recipeDetails[${rowCount}].merchandiseId" value="${merchandiseId}">
            </td>
            <td>
                ${quantity}
                <input type="hidden" name="recipeDetails[${rowCount}].quantity" value="${quantity}">
            </td>
            <td>
                ${unitName}
                <input type="hidden" name="recipeDetails[${rowCount}].unitId" value="${unitId}">
            </td>
            <td class="text-center">
                <button type="button" class="btn btn-danger btn-sm" onclick="removeIngredientRow(this)">Xóa</button>
            </td>
        `;

        tbody.appendChild(newRow);

        // Reset lại form nhập liệu bên dưới
        selectElem.selectedIndex = 0;
        quantityElem.value = '';
        unitElem.selectedIndex = 0;

        updateIndexes();
    }

    function removeIngredientRow(button) {
        let row = button.closest('tr');
        row.remove();
        updateIndexes();
    }

    function updateIndexes() {
        let rows = document.querySelectorAll('#recipeTableBody tr.recipe-row');
        rows.forEach((index_row, index) => {
            let hiddenMerchandise = index_row.querySelector('input[name*="merchandiseId"]');
            let hiddenQuantity = index_row.querySelector('input[name*="quantity"]');
            let hiddenUnit = index_row.querySelector('input[name*="unitId"]');

            if (hiddenMerchandise) hiddenMerchandise.name = `recipeDetails[${index}].merchandiseId`;
            if (hiddenQuantity) hiddenQuantity.name = `recipeDetails[${index}].quantity`;
            if (hiddenUnit) hiddenUnit.name = `recipeDetails[${index}].unitId`;
        });
    }
