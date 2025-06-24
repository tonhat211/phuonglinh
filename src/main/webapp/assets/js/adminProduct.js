function previewImage(event) {
    const parent = event.currentTarget.closest('.group');
    var file = parent.querySelector('.thumbnailInput').files[0];
    var reader = new FileReader();

    reader.onload = function(e) {
        var imagePreview = parent.querySelector('.thumbnailPreview');
        imagePreview.src = e.target.result;
        parent.querySelector('.thumbnail-img-container').style.display = 'block';
        imagePreview.setAttribute("data-file",file.name);
        updateThumbnailUrl(parent);

    }
    if (file) {
        reader.readAsDataURL(file);
    }
}
function updateThumbnailUrl(container) {
    const imageElement = container.querySelector('.thumbnail-img-container .thumbnailPreview');
    const url = imageElement.getAttribute('data-file');
    container.querySelector('input[name="thumbnailInput"]').value = url;
}
function previewImages(event) {
    const parent = event.currentTarget.closest('.group');
    const imgListContainer = $(parent).find('.upload__img-wrap');
    const files = Array.from(event.currentTarget.files);
    files.forEach(function (f) {
        var reader = new FileReader();
        reader.onload = function (e) {
            const imgContainerE = `
                <div class="img-container grid-col-2 group" style="margin-top: 15px;position: relative;">
                    <img src="${e.target.result}" alt="" class="imgPreview" data-file="${f.name}" style="width:100%">
                    <div class="upload__img-close" onclick="removeImg(event)"></div>
                </div>
            `;
            imgListContainer.append(imgContainerE);

            updateImgUrls(parent);
        };
        reader.readAsDataURL(f);
    });

}
function removeThumbnail(event) {
    const group = event.currentTarget.closest('.group');
    const thumbnailImgContainer = group.querySelector('.thumbnail-img-container');
    thumbnailImgContainer.querySelector('.thumbnailPreview').setAttribute('data-file', "");
    thumbnailImgContainer.style.display='none';
    group.querySelector('.thumbnailInput').value="";
    updateThumbnailUrl(group);
}
function removeImg(event) {
    const imgContainer = event.currentTarget.closest('.group');
    const imgListContainer = imgContainer.parentNode.closest('.group');
    console.log(imgListContainer);
    imgContainer.remove();
    updateImgUrls(imgListContainer);
    if(imgListContainer.querySelectorAll('.img-container').length ===0){
        imgListContainer.querySelector('input[name="imgs"]').value = '';
    }


}
function updateImgUrls(container) {
    const elements = container.querySelectorAll('.imgPreview');
    console.log("so luong hinh: "+ elements.length);
    var urls="";
    elements.forEach(function (element) {
        urls+=element.getAttribute('data-file')+'==';
    });
    container.querySelector('input[name="imgUrls"]').value = urls;

}
function showDetail(event) {
    event.stopPropagation();
    const currentRow = event.currentTarget;
    const detailContainer = document.getElementById("detail-container");
    if(detailContainer.classList.contains("active")) {
        closeDetail();
        setInfosStatus(currentRow,false);
        setDefaultOption("action");
    } else {
        detailContainer.classList.toggle("active");
    }
    var id = event.currentTarget.querySelector('.product-id .id').innerText;
    getDetail(id).then(function(details) {
        renderDetail(id,details)// Gọi render khi dữ liệu đã được lấy về
    }).catch(function(error) {
        showErrorToast2("Không thể tải phân loại sản phẩm.",'none');
        if(detailContainer.classList.contains("active")) {
            closeDetail();
            setInfosStatus(currentRow,false);
            setDefaultOption("action");
        }
    });
    currentRow.parentNode.insertBefore(detailContainer, currentRow.nextElementSibling);
}
function getDetail(id) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'aproduct?a=detail&id='+id, // URL của Servlet hoặc API
            type: 'POST',
            dataType: 'json',
            success: function(data) {
                console.log(data);
                resolve(data); // Gọi resolve với dữ liệu trả về
            },
            error: function(error) {
                reject(error); // Nếu có lỗi, gọi reject
            }
        });
    });
}
function renderDetail(productID,details) {
    const detailContainer = $("#detail-container tbody");
    detailContainer.empty();
    var stt=1;
    details.forEach(function(detail) {
        var isLocked = 'locked';
        let lockOption = '<option class="active-option" value="activedetail">Mở khóa</option>';
        if(detail.avai==1) {
            isLocked="";
            lockOption = '<option class="lock-option" value="lockdetail">Khóa</option>';
        }
        const detailE = `
       <tr class="group ${isLocked}" id="detail-${detail.id}">
            <th scope="row" class="grid-col-0_5 text-center" >
                <span class="stt">${stt++}</span>
            </th>
            <td class="grid-col-0_5 text-center">
                <span class="id">${detail.id}</span>
                <span class="productID" style="display: none">${productID}</span>

            </td>
            <td class="grid-col-1">
                <input class="info-input" type="text" name="classify" value="${detail.classify}" readonly>
            </td>
            <td class="grid-col-1 text-center">
                <input class="info-input text-center" type="number" name="prePrice" value="${detail.prePrice}" readonly>
            </td>
            <td class="grid-col-1 text-center">
                <input class="info-input text-center" type="number" name="price" value="${detail.price}" readonly>
            </td>
            <td class="grid-col-1 text-center">
                <input class="info-input text-center" type="number" name="qty" value="${detail.qty}" readonly>
            </td>
            <td class="grid-col-1">
                <div class="flex-roww">
                    <select name="action" class="exception" onchange="handleChange(event);" data-default="none">
                        <option value="none">...</option>
                        <option value="updatedetail">Cập nhật</option>
                        ${lockOption}
                        <option value="deletedetail">Xóa</option>
                    </select>
                    <button class="btn-update-detail" style="display: none" onclick="updateDetail(event);"><i class="bi bi-floppy"></i></button>
            
                </div>
            </td>

        </tr>
    `;
    detailContainer.append(detailE);

    });
    const addToolE= `
       <tr class="group" id="add-detail-form">
            <th scope="row" class="grid-col-0_5 text-center" >
            </th>
            <td class="grid-col-0_5 text-center">
                <span class="productID" style="display: none">${productID}</span>
            </td>
            <td class="grid-col-1">
                <input class="info-input" type="text" name="classify" placeholder="Nhập phân loại" hidden>
            </td>
            <td class="grid-col-1 text-center">
                <input class="info-input text-center" type="number" name="prePrice" placeholder="Nhập giá trước giảm" hidden>
            </td>
            <td class="grid-col-1 text-center">
                <input class="info-input text-center" type="number" name="price" placeholder="Nhập giá sau giảm" hidden>
            </td>
            <td class="grid-col-1 text-center">
                <input class="info-input text-center" type="number" name="qty" placeholder="Nhập số lượng" hidden>
            </td>
            <td class="grid-col-1">
                <div class="flex-roww">
                     <button class="btn btn-primary btn-setup" type="button" onclick="setupAddDetail(event);">Thêm</button>
                     <button class="btn btn-primary btn-save" type="button" onclick="saveAddDetail(event)" style="display: none">Lưu</button>
                     <button class="btn btn-outline-primary btn-cancel" style="margin-left: 20px;display: none;" type="button" onclick="cancelAddDetail(event);">Hủy</button>
                </div>
            </td>

        </tr>
    `;
    detailContainer.append(addToolE);


}
function setupAddDetail(event) {
    const group = event.currentTarget.closest('.group');
    const inputs = group.querySelectorAll('input');
    inputs.forEach(input => {
        input.hidden = false;
    });
    group.querySelector('.btn-save').style.display = 'block';
    group.querySelector('.btn-cancel').style.display = 'block';
    group.querySelector('.btn-setup').style.display = 'none';
}

function cancelAddDetail(event) {
    const group = event.currentTarget.closest('.group');
    const inputs = group.querySelectorAll('input');
    inputs.forEach(input => {
        input.hidden = true;
        input.value="";
    });
    group.querySelector('.btn-save').style.display = 'none';
    group.querySelector('.btn-cancel').style.display = 'none';
    group.querySelector('.btn-setup').style.display = 'block';
}

function saveAddDetail(event) {
    const group = event.currentTarget.closest('.group');
    var productID = group.querySelector('.productID').innerText;
    var classify = group.querySelector('input[name="classify"]').value;
    var prePrice = group.querySelector('input[name="prePrice"]').value;
    var price = group.querySelector('input[name="price"]').value;
    var qty = group.querySelector('input[name="qty"]').value;
    console.log(productID);
    console.log(classify);
    console.log(prePrice);
    console.log(price);
    console.log(qty);
    $.ajax({
        type: "POST",
        url: "aproduct?a=addDetail",
        data: {productID:productID, classify: classify,prePrice:prePrice, price: price, qty: qty},
        success: function(data) {
            $('#aproduct-response').html(data);
        },
        error: function(error) {
            showErrorToast2('Thêm phân loại thất bại: '+error.responseText+'!','none');
        }
    });

}

function closeDetail() {
    document.querySelector('#detail-container').classList.remove('active');
}

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
            setupUpdate(group,"#update-info-modal");
            setDefaultOption("action");
            break;
        case "INFO":
            var id = group.querySelector(".id").innerText;
            showInfo(id,'info');
            setDefaultOption("action");
            break;
        case "LOCK": {
            setInfosStatus(group,false);
            var name = group.querySelector(".name").innerText;
            var id = group.querySelector(".id").innerText;
            setupConfirm(id,'#lock-confirm-modal',name);
            break;
        }
        case "UNLOCK": {
            setInfosStatus(group,false);
            var name = group.querySelector(".name").innerText;
            var id = group.querySelector(".id").innerText;
            setupConfirm(id,'#unlock-confirm-modal',name);
            break;
        }
        case "DELETE": {
            setInfosStatus(group,false);
            var name = group.querySelector(".name").innerText;
            var id = group.querySelector(".id").innerText;
            setupConfirm(id,'#delete-confirm-modal',name);
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

function setInfosStatus(group,status) {
    const inputs = group.querySelectorAll("input");
    var status =!status;
    inputs.forEach((input) => {
        input.readOnly = status;
    });
    const selects = group.querySelectorAll("select");
    selects.forEach((select) => {
        select.disabled = status;
        if(select.classList.contains('exception')) {
            select.disabled = false;
        }
    });
    group.querySelector("select[name='action']").disabled =false;
}

function updateDetail(event) {
    const group = event.currentTarget.closest('.group');
    var id = group.querySelector('.id').innerText;
    var productID = group.querySelector('.productID').innerText;
    var classify = group.querySelector('input[name="classify"]').value;
    var prePrice = group.querySelector('input[name="prePrice"]').value;
    var price = group.querySelector('input[name="price"]').value;
    var qty = group.querySelector('input[name="qty"]').value;
    $.ajax({
        type: "POST",
        url: "aproduct?a=updateDetail",
        data: {id: id, productID:productID, classify: classify,prePrice:prePrice, price: price, qty: qty},
        success: function(data) {
            $('#aproduct-response').html(data);
        },
        error: function(error) {
            showErrorToast2('Cập nhật thất bại: '+error.responseText+'!','none');
        }
    });
}

function refreshDetail(productID) {
    getDetail(productID).then(function(details) {
        renderDetail(productID,details)// Gọi render khi dữ liệu đã được lấy về
    }).catch(function(error) {
        showErrorToast2("Không thể tải phân loại sản phẩm.",'none');
        if(detailContainer.classList.contains("active")) {
            closeDetail();
            // setInfosStatus(currentRow,false);
            setDefaultOption("action");
        }
    });
}

function setupUpdate(group) {
    var id = group.querySelector(".id").innerText;
    showInfo(id,'update');

}

function lockDetail(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "aproduct?a=lockdetail",
        data: {id: id},
        success: function(data) {
            $('#aproduct-response').html(data);
            hideModal('#lock-detail-confirm-modal');
        },
        error: function(error) {
            showErrorToast2('Khóa thất bại: '+error.responseText+'!','none');
        }
    });
}

function addActive(element) {
    document.querySelector(element).classList.add('active');
}

function removeActive(element) {
    if(document.querySelector(element).classList.contains('active'))
        document.querySelector(element).classList.remove('active');
}

function activeDetail(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "aproduct?a=activeDetail",
        data: {id: id},
        success: function(data) {
            $('#aproduct-response').html(data);
            hideModal('#unlock-detail-confirm-modal');
        },
        error: function(error) {
            showErrorToast2('Kích hoạt thất bại: '+error.responseText+'!','none');
        }
    });
}

function deleteDetail(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "aproduct?a=deleteDetail",
        data: {id: id},
        success: function(data) {
            $('#aproduct-response').html(data);
            hideModal('#delete-detail-confirm-modal');
        },
        error: function(error) {
            showErrorToast2('Kích hoạt thất bại: '+error.responseText+'!','none');
        }
    });
}
function showInfo(id,action) {
    document.querySelector("#update-info-modal").classList.add('active');
    getInfo(id).then(function(info) {
        renderInfo(info,action)// Gọi render khi dữ liệu đã được lấy về
    }).catch(function(error) {
        showErrorToast2("Không thể tải thông tin sản phẩm: " + error.responseText,'none');
    });
}
function getInfo(id) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'aproduct?a=info&id='+id, // URL của Servlet hoặc API
            type: 'POST',
            dataType: 'json',
            success: function(data) {
                resolve(data); // Gọi resolve với dữ liệu trả về
            },
            error: function(error) {
                reject(error); // Nếu có lỗi, gọi reject
            }
        });
    });
}
function renderInfo(productInfo,action) {
    var product = productInfo.product;
    const detailContainer = document.querySelector("#update-info-modal");
    detailContainer.querySelector('.title').innerText = 'Thông tin sản phẩm';
    detailContainer.querySelector('input[name="name"]').value = product.name;
    detailContainer.querySelector('input[name="id"]').value = product.id;
    // detailContainer.querySelector('.productID').innerText = product.id;
    const selectE = detailContainer.querySelector('select[name="category"]');
    var categorySelected = product.category;
    var isSelected = false;
    const options = selectE.options; // Lấy tất cả các <option> trong <select>
    for (let i = 0; i < options.length; i++) {
        if (options[i].value === categorySelected) {
            selectE.selectedIndex = i;
            isSelected=true;
            break;
        }
    }
    if(isSelected===false) {
        detailContainer.querySelector('input[name="other-category"]').value = categorySelected;
        detailContainer.querySelector('input[name="other-category"]').classList.add('active');
        categorySelected = 'other-category';
        for (let i = 0; i < options.length; i++) {
            if (options[i].value === categorySelected) {
                selectE.selectedIndex = i;
                break;
            }
        }
    }
    detailContainer.querySelector('textarea[name="ingredient"]').value = product.ingredient;
    detailContainer.querySelector('.thumbnailPreview').src = "./assets/img/thumbnail/"+ product.thumbnail;
    detailContainer.querySelector('input[name="thumbnailInput"]').value = product.thumbnail;
    detailContainer.querySelector('input[name="prominence"]').value = product.prominence;
    var imgsE ="";
    var imgs = productInfo.images;
    var imgNames = "";
    imgs.forEach((img) => {
        var imgE =`
            <div class="img-container grid-col-2 group" style="margin-top: 15px;position: relative;">
                <img src="./assets/img/detail/${img.url}" alt="" class="imgPreview" data-file="${img.url}" style="width:100%"/>
                <div class="upload__img-close" onClick="removeImg(event)"></div>
            </div>
        `;
        imgNames+=img.url +"==";
        imgsE+=imgE;
    });
    detailContainer.querySelector('input[name="imgUrls"]').value = imgNames;
    detailContainer.querySelector('.upload__img-wrap').innerHTML = imgsE;
    detailContainer.querySelector('textarea[name="description"]').value = product.description;
    detailContainer.querySelector('textarea[name="direction"]').value = product.direction;
    var readonlyStatus = false;
    var disableStatus = false;
    var displayStatus = "flex";
    var title = 'Cập nhật thông tin sản phẩm';
    if(action==='info') {
        readonlyStatus = true;
        disableStatus = true;
        displayStatus = "none";
        title = 'Thông tin sản phẩm';
        if(detailContainer.classList.contains('parent-primary')) {
            detailContainer.classList.remove('parent-primary');
        }
        if(detailContainer.classList.contains('parent-secondary')) {
            detailContainer.classList.remove('parent-secondary');
        }
        detailContainer.classList.add('parent-secondary');
        detailContainer.querySelector('.btn-setup').addEventListener('click', function(event) {
            showInfo(product.id,'update');
        });


    } else {
        if(detailContainer.classList.contains('parent-primary')) {
            detailContainer.classList.remove('parent-primary');
        }
        if(detailContainer.classList.contains('parent-secondary')) {
            detailContainer.classList.remove('parent-secondary');
        }
        detailContainer.classList.add('parent-primary');
    }
    detailContainer.querySelector('.title').innerText = title;
    const inputs = detailContainer.querySelectorAll('input');
    inputs.forEach((input)=> {
        input.readOnly = readonlyStatus;
        if(input.type==='file') input.disabled = disableStatus;
    });
    const textareas = detailContainer.querySelectorAll('textarea');
    textareas.forEach((textarea)=> {
        textarea.readOnly = readonlyStatus;
        textarea.disabled=disableStatus;
    });
    const selects = detailContainer.querySelectorAll('select');
    selects.forEach((select)=> {
        select.disabled=disableStatus;
    });
    const btnCloseImgs = detailContainer.querySelectorAll('.upload__img-close');
    btnCloseImgs.forEach((btn) => {
        btn.style.display = displayStatus;
    });
    detailContainer.querySelector('input[name="id"]').readOnly = true;
}

function updateProduct(formE) {
    var formData = new FormData(formE);
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'aproduct?a=update', // URL servlet của bạn
            type: 'POST',
            data: formData,
            processData: false, // can khi gui form
            contentType: false, // can khi gui form
            dataType: 'json',
            success: function (data) {
                resolve(data); // Gọi resolve với dữ liệu trả về
            },
            error: function (error) {
                reject(error);
            }
        });
    });
}
function clearInput(group) {
    const inputs = group.querySelectorAll("input");
    inputs.forEach(input => { input.value = "";});
}
function renderProductRow(product,action) {
    const tableBody = document.querySelector("#infoTable tbody");
    const productItems = tableBody.querySelectorAll('tr');
    for(let i = 0; i < productItems.length; i++) {
        if(productItems[i].id === "product"+product.id) {
            const currentProduct = productItems[i];
            currentProduct.querySelector('.img-thumbnail-container img').src = "./assets/img/thumbnail/"+product.thumbnail;
            currentProduct.querySelector('.name').innerText = product.name;
            currentProduct.querySelector('.category').innerText = product.category;
            currentProduct.querySelector('.prominence').innerText = product.prominence;
            break;
        }
    }
    if(action==='update') {
        showInfo(product.id,'info');
    }

}

function saveUpdate(event,action) {
    const formE = event.currentTarget.closest('form');
    updateProduct(formE).then(function(info) {
        renderProductRow(info,action)// Gọi render khi dữ liệu đã được lấy về
        showSuccessToast2("Cập nhật thành công.",'none');
    }).catch(function(error) {
        showErrorToast2("Không thể tải thông tin sản phẩm: " + error.responseText,'none');
    });
}

function addMoreClassify(event) {
    const group = event.currentTarget.closest('.group');
    const newDetail = group.cloneNode(true);
    clearInput(newDetail);
    group.parentNode.appendChild(newDetail);
    const btnRemove = group.querySelector('.btn-remove-classify');
    plusToMinus(event.currentTarget,btnRemove);
}

function removeClassify(event) {
    const group = event.currentTarget.closest('.group');
    group.parentNode.removeChild(group);
}

function plusToMinus(plus,minus) {
    plus.classList.remove('active');
    minus.classList.add('active');
}

function minusToPlus(minus,plus) {
    minus.classList.remove('active');
    plus.classList.add('active');
}

function saveAdd(event) {
    const formE = event.currentTarget.closest('form');
    var classifyArr=[];
    const classifyItemEs = formE.querySelectorAll('.classify-item');
    classifyItemEs.forEach(classifyItemE => {
        var classifyItemJson={};
        var classify = classifyItemE.querySelector('input[name="classify"]').value;
        var prePrice = classifyItemE.querySelector('input[name="prePrice"]').value;
        var price = classifyItemE.querySelector('input[name="price"]').value;
        var qty = classifyItemE.querySelector('input[name="qty"]').value;
        classifyItemJson['classify'] = classify;
        classifyItemJson['prePrice'] = prePrice;
        classifyItemJson['price'] = price;
        classifyItemJson['qty'] = qty;

        classifyArr.push(classifyItemJson);
    });
    const classifiesString = JSON.stringify(classifyArr, null, 2);
    formE.querySelector('input[name="classifies"]').value = classifiesString;
    // addProduct(formE).then(function(info) {
    //     renderProductRow(info,action)// Gọi render khi dữ liệu đã được lấy về
    //     showSuccessToast2("Thêm thành công.",'none');
    // }).catch(function(error) {
    //     showErrorToast2("Không thể tải thông tin sản phẩm: " + error.responseText,'none');
    // });
    var formData = new FormData(formE);
    $.ajax({
        url: 'aproduct?a=add', // URL servlet của bạn
        type: 'POST',
        data: formData,
        processData: false, // can khi gui form
        contentType: false, // can khi gui form
        success: function (data) {
            $('#aproduct-response').html(data); // Gọi resolve với dữ liệu trả về
        },
        error: function (error) {
            showErrorToast2('Lỗi: ' +error.responseText,'none');
        }
    });
}

function queryBy(event) {
    var value = event.currentTarget.value;
    window.location.href = "aproduct?a=query&page=1&category=" + value;
    console.log(value);
}

function query(event) {
    event.preventDefault();
    console.log("query");
    const categoryCondition = document.querySelector('select#byCategory').value;
    console.log(document.querySelector('select#byCategory'));
    console.log("Category condiditon: "+ categoryCondition);
    const a = event.currentTarget;
    a.href = a.href +"&category=" + categoryCondition;
    window.location.href = a.href;
}

function lock(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "aproduct?a=lock",
        data: {id: id},
        success: function(data) {
            $('#aproduct-response').html(data);
            hideModal('#lock-confirm-modal');


        },
        error: function(error) {
            $('#aproduct-response').html(error.responseText);
        }
    });
}

function active(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "aproduct?a=active",
        data: {id: id},
        success: function(data) {
            $('#aproduct-response').html(data);
            hideModal('#unlock-confirm-modal');
        },
        error: function(error) {
            $('#aproduct-response').html(error.responseText);
        }
    });
}

function deleteProduct(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "aproduct?a=delete",
        data: {id: id},
        success: function(data) {
            $('#aproduct-response').html(data);
            hideModal('#delete-confirm-modal');
        },
        error: function(error) {
            $('#aproduct-response').html(error.responseText);
        }
    });
}

function refreshProduct(productID) {
    getInfo(productID).then(function(productInfo) {
        refreshProductRow(productInfo);// Gọi render khi dữ liệu đã được lấy về
        setDefaultOption("action");
    }).catch(function(error) {
        showErrorToast2("Lỗi: " +error.responseText ,'none');
        setDefaultOption("action");

    });
}
function refreshProductRow(productInfo) {
    const tableBody = document.querySelector("#infoTable tbody");
    const productItems = tableBody.querySelectorAll('tr');
    var product = productInfo.product;
    for(let i = 0; i < productItems.length; i++) {
        if(productItems[i].id === "product"+product.id) {
            const currentProduct = productItems[i];
            if(product.avai!==1) {
                currentProduct.classList.add('locked');
            } else{
                if(currentProduct.classList.contains('locked')) {
                    currentProduct.classList.remove('locked');
                }
            }
            currentProduct.querySelector('.img-thumbnail-container img').src = "./assets/img/thumbnail/"+product.thumbnail;
            currentProduct.querySelector('.name').innerText = product.name;
            currentProduct.querySelector('.category').innerText = product.category;
            currentProduct.querySelector('.prominence').innerText = product.prominence;
            break;
        }
    }
}
function closeModal(event) {
    event.currentTarget.closest(".modall").classList.remove("active");
}
function setupConfirm(id,modalID,object) {
    // var name = group.querySelector('input[name="name"]').value;
    openModal(modalID)
    document.querySelector(modalID).querySelector('.object').innerText = object;
    document.querySelector(modalID).querySelector('input[name="id"]').value = id;
}

function openModal(modalID) {
    document.querySelector(modalID).classList.add('active');
}
function setDefaultOption(name) {
    document.querySelectorAll('select[name="'+name+'"]').forEach(select => {
        const defaultValue = select.getAttribute('data-default');
        select.value = defaultValue;
    });
}
document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('#add-product-btn').addEventListener('click', function(e) {
        e.preventDefault();
        openModal("#add-modal");
    });
});

function setFilterSearch(event) {
    document.querySelector('.search-bar input[name="filterSearch"]').value = event.currentTarget.value;
}