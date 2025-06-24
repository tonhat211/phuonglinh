function handleChange(event) {
    event.stopPropagation();
    let selectedValue = event.target.value;
    setDefaultOption("action");
    event.target.value = selectedValue;
    console.log("selected value: " + selectedValue);
    const group = event.currentTarget.closest(".group");
    selectedValue=selectedValue.toUpperCase();
    switch (selectedValue) {
        case "UPDATE":
            // setupUpdate(group,"#update-info-modal");
            // setDefaultOption("action");
            setInfosStatus(group,true);
            group.querySelector(".update-tool").classList.add('active');
            break;
        case "INFO":
            var id = group.querySelector(".id").innerText;
            showInfo(id,'info');
            setDefaultOption("action");
            break;
        case "LOCK": {
            setInfosStatus(group,false);
            var id = group.querySelector(".id").innerText;
            setupConfirm(id,'#lock-confirm-modal',id);
            break;
        }
        case "UNLOCK": {
            setInfosStatus(group,false);
            var id = group.querySelector(".id").innerText;
            setupConfirm(id,'#unlock-confirm-modal',id);
            break;
        }
        case "DELETE": {
            setInfosStatus(group,false);
            var id = group.querySelector(".id").innerText;
            setupConfirm(id,'#delete-confirm-modal',id);
            break;
        }
        case "DESCRIPTION": {
            var id = group.querySelector(".id").innerText;
            showDescription(id);
            break;
        }
        case "UPDATEDETAIL" : {
            setInfosStatus(document.querySelector('#infoTable'),false);
            if(document.querySelector('#infoTable .btn-update-detail.active'))
                document.querySelector('#infoTable .btn-update-detail.active').classList.remove('active');
            setInfosStatus(group,true);
            group.querySelector(".btn-update-detail").classList.add('active');
            break;
        }
        case "LOCKDETAIL": {
            setInfosStatus(group,false);
            var classify = group.querySelector("input[name='classify']").value;
            var id = group.querySelector('.id').innerText;
            var object = "Phân loại: ID: "+ id + " - " + classify;
            setupConfirm(id,'#lock-detail-confirm-modal',object);
            break;
        }
        case "ACTIVEDETAIL": {
            setInfosStatus(group,false);
            var classify = group.querySelector("input[name='classify']").value;
            var id = group.querySelector('.id').innerText;
            var object = "Phân loại: ID: "+ id + " - " + classify;
            setupConfirm(id,'#unlock-detail-confirm-modal',object);
            break;
        }
        case "DELETEDETAIL": {
            setInfosStatus(group,false);
            var classify = group.querySelector("input[name='classify']").value;
            var id = group.querySelector('.id').innerText;
            var object = "Phân loại: ID: "+ id + " - " + classify;
            setupConfirm(id,'#delete-detail-confirm-modal',object);
            break;
        }
        case "OTHER-CATEGORY": {
            console.log(document.querySelector('#addProductForm .other-category-input'));
            addActive('#addProductForm .other-category-input');
            addActive('#updateInfoForm .other-category-input');
            group.querySelector("input[name='other-category']").value="Khác";
            break;
        }
        case "DONGTRUNG":
        case "NAM":
        case "RUOU": {
            removeActive('#addProductForm .other-category-input');
            removeActive('#updateInfoForm .other-category-input');
            group.querySelector("input[name='other-category']").value="";
            break;
        }

        default:
            setInfosStatus(group,false);

    }
}

function setupConfirm(id,modalID,object) {
    // var name = group.querySelector('input[name="name"]').value;
    openModal(modalID)
    document.querySelector(modalID).querySelector('.object').innerText = object;
    document.querySelector(modalID).querySelector('input[name="id"]').value = id;
}

function lock(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "avoucher?a=lock",
        data: {id: id},
        success: function(data) {
            $('#avoucher-response').html(data);
            hideModal('#lock-confirm-modal');
            setDefaultOption('action');

        },
        error: function(error) {
            $('#avoucher-response').html(error.responseText);
        }
    });
}
function active(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "avoucher?a=active",
        data: {id: id},
        success: function(data) {
            $('#avoucher-response').html(data);
            hideModal('#unlock-confirm-modal');
            setDefaultOption('action');
        },
        error: function(error) {
            $('#avoucher-response').html(error.responseText);
        }
    });
}

function deleteVoucher(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "avoucher?a=delete",
        data: {id: id},
        success: function(data) {
            $('#avoucher-response').html(data);
            hideModal('#delete-confirm-modal');
        },
        error: function(error) {
            $('#avoucher-response').html(error.responseText);
        }
    });
}
function setDefaultOption(name) {
    document.querySelectorAll('select[name="'+name+'"]').forEach(select => {
        const defaultValue = select.getAttribute('data-default');
        select.value = defaultValue;
    });
}
function saveUpdate(event) {
    const groupE = event.currentTarget.closest('.group');
    const id = groupE.querySelector('.id').innerText;
    const discount = groupE.querySelector('input[name="discount"]').value;
    const maxMoney = groupE.querySelector('input[name="maxMoney"]').value;
    const qty = groupE.querySelector('input[name="qty"]').value;
    const startTime = groupE.querySelector('input[name="startTime"]').value;
    const endTime = groupE.querySelector('input[name="endTime"]').value;

    update(id,discount,maxMoney,qty,startTime,endTime).then(function(info) {
        renderInfoRow(info)// Gọi render khi dữ liệu đã được lấy về
        showSuccessToast2("Cập nhật thành công.",'none');
        cancelUpdate(event,groupE);
    }).catch(function(error) {
        showErrorToast2("Không thể tải thông tin: " + error.responseText,'none');
    });
}
function update(id,discount,maxMoney,qty,startTime,endTime) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'avoucher?a=update', // URL servlet của bạn
            type: 'POST',
            data: {id: id,discount: discount,maxMoney: maxMoney,qty: qty,startTime: startTime,endTime: endTime},
            dataType: 'json',
            success: function (data) {
                resolve(data);
            },
            error: function (error) {
                reject(error);
            }
        });
    });
}
function renderInfoRow(voucher) {
    const tableBody = document.querySelector("#infoTable tbody");
    const productItems = tableBody.querySelectorAll('tr');
    for(let i = 0; i < productItems.length; i++) {
        if(productItems[i].id === "voucher"+voucher.id) {
            const currentRow = productItems[i];
            currentRow.querySelector(".id").innerText = voucher.id;
            currentRow.querySelector('input[name="discount"]').value = voucher.discount;
            currentRow.querySelector('input[name="maxMoney"]').value = voucher.maxMoney;
            currentRow.querySelector('input[name="qty"]').value = voucher.qty;
            currentRow.querySelector('input[name="startTime"]').value = voucher.startTime;
            currentRow.querySelector('input[name="endTime"]').value = voucher.endTime;
            break;
        }
    }

}
function cancelUpdate(event,group) {
    if(group==null)
        group = event.currentTarget.closest('.group');
    setInfosStatus(group,false);
    if(group.querySelector(".update-tool").classList.contains('active'))
        group.querySelector(".update-tool").classList.remove('active');
    setDefaultOption("action");

}
function saveUpdateDescription(event) {
    const groupE = event.currentTarget.closest('form');
    const id = groupE.querySelector('input[name="id"]').value;
    const description = groupE.querySelector('textarea[name="description"]').value;
    $.ajax({
        url: 'avoucher?a=updateDescription', // URL servlet của bạn
        type: 'POST',
        data: {id: id,description: description},
        success: function (data) {
            if(data==='true') {
                showSuccessToast2("Cập nhật thành công.",'none');
            } else {
                showErrorToast2("Cập nhật thất bại.",'none');
            }
            hideModal('#description-modal');
            setDefaultOption("action");
        },
        error: function (error) {
            showErrorToast2("Cập nhật thất bại.",'none');
        }
    });
}
function showDescription(id) {
    getDescription(id).then(function(data) {
        renderDescription(data)// Gọi render khi dữ liệu đã được lấy về
    }).catch(function(error) {
        showErrorToast2("Không thể tải thông tin: " + error.responseText,'none');
    });
}
function getDescription(id) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'avoucher?a=description', // URL servlet của bạn
            type: 'POST',
            dataType: 'json',
            data:{id:id},
            success: function (data) {
                resolve(data);
            },
            error: function (error) {
                reject(error);
            }
        });
    });
}
function renderDescription(voucher) {
    const modal = document.querySelector('#description-modal');
    modal.classList.add('active');
    modal.querySelector('input[name="id"]').value = voucher.id;
    modal.querySelector('textarea[name="description"]').value = voucher.description;

}
function renderAppliedProductList(products) {
    const appliedProductListTable = $('#appliedProductListTable tbody'); // Giả sử bạn có một div với id là address-list
    appliedProductListTable.empty();
    let stt=0;
    products.forEach(function(productAdmin) {
        var product = productAdmin.product;
        const item = `
            <tr class="group" id="appliedProduct${product.id}">
                <th scope="row" class="grid-col-0_5 text-center" >
                    <span class="stt">${stt++}</span>
                </th>
                <td class="grid-col-0_5 text-center product-id">
                    <span class="id">${product.id}</span>
                </td>
                <td class="grid-col-0_5">
                    <div class="img-thumbnail-container">
                        <img src="./assets/img/thumbnail/${product.thumbnail}" alt="">
                    </div>
                </td>
                <td class="grid-col-1_5">
                    <span class="name">${product.name}</span>
                </td>
                <td class="grid-col-1">
                    <span class="category">${product.category}</span>
                </td>
                <td class="grid-col-1" onclick="event.stopPropagation();">
                    <button class="btn btn-primary" onclick="removeProduct(event)">Xóa</button>
                </td>
            </tr>
        `;
        appliedProductListTable.append(item);
    });
}
function getAppliedProductList(id) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'avoucher?a=appliedProducts', // URL servlet của bạn
            type: 'POST',
            dataType: 'json',
            data:{id:id},
            success: function (data) {
                resolve(data);
            },
            error: function (error) {
                reject(error);
            }
        });
    });
}
function showAppliedProductList(id) {
    document.querySelector('#applied-product-list-modal').classList.add('active');
    getAppliedProductList(id).then(function(data) {
        renderAppliedProductList(data)// Gọi render khi dữ liệu đã được lấy về
    }).catch(function(error) {
        showErrorToast2("Không thể tải thông tin: " + error.responseText,'none');
    });
}
