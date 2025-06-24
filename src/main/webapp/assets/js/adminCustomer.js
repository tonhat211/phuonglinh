function showDetail(event) {
    event.stopPropagation();
    const detailContainer = document.getElementById("detail-container");
    detailContainer.classList.toggle("active");
    var id = event.currentTarget.querySelector('.user-id .id').innerText;
    getDetail(id).then(function(user) {
        renderDetail(user)// Gọi render khi dữ liệu đã được lấy về
    }).catch(function(error) {
        console.error("Có lỗi khi lấy chi tiết khách hàng:", error);
        $('#detail-container tbody').html('<p>Không thể tải thông tin khách hàng.</p>'); // Hiển thị thông báo lỗi nếu có
    });

    const currentRow = event.currentTarget;
    currentRow.parentNode.insertBefore(detailContainer, currentRow.nextElementSibling);
}

function getDetail(id) {
    console.log("get detail");
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'acustomer?a=info&id='+id, // URL của Servlet hoặc API
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

function renderDetail(user) {
    const detailContainer = document.querySelector("#detail-container");
    // var genderName="Nam";
    // if(user.gender=="1") genderName = "Nam"
    // else if(user.gender=="2") genderName = "Nữ"
    // else genderName = "Không xác định"
    const detailE = `
        <tr class="group">
            <th class="grid-col-1" >
                <div class="flex-coll" style="align-items: start">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="gender" id="exampleRadios1" value="1" checked readonly>
                        <label class="form-check-label" for="exampleRadios1">
                            Nam
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="gender" id="exampleRadios2" value="2" readonly>
                        <label class="form-check-label" for="exampleRadios2">
                            Nữ
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="gender" id="exampleRadios3" value="0" readonly>
                        <label class="form-check-label" for="exampleRadios3">
                            Ẩn
                        </label>
                    </div>
                </div>
            </th>
            <td class="grid-col-1">
                <input class="info-input" type="date" name="dob" value="${user.dob}" readonly>
            </td>
            <td class="grid-col-1">
                <input class="info-input" type="text" name="phone" value="${user.phone}" readonly>
            </td>
            <td class="grid-col-1">
                <span class="dateIn">${user.dateIn}</span>
            </td>
        </tr>
        <tr class="edit-tool" style="display: none">
             <td colspan="4">
                <div class="flex-roww " style="justify-content: space-around;width: 100%;">
                    <button class="btn btn-outline-primary" onclick="cancelUpdate()">Hủy</button>
                    <button class="btn btn-primary" onclick="update(${user.id})">Lưu</button>
                </div>
            </td>
        </tr>
    `;

    detailContainer.querySelector('#detail-table tbody').innerHTML = detailE;

    const genders = detailContainer.querySelectorAll('input[name="gender"]');
    genders.forEach(gender => {
        if(gender.value==user.gender) gender.checked = true;
        else {
            gender.checked = false;
            gender.closest('.form-check').style.display = "none";
        }
        // console.log(gender.closest('.form-check'));
    });

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
        case "LOCK": {
            setInfosStatus(group,false);
            var name = group.querySelector('input[name="name"]').value;
            var id = group.querySelector(".id").innerText;
            setupConfirm(id,'#lock-confirm-modal',name);
            break;
        }
        case "UNLOCK": {
            setInfosStatus(group,false);
            var name = group.querySelector('input[name="name"]').value;
            var id = group.querySelector(".id").innerText;
            setupConfirm(id,'#unlock-confirm-modal',name);
            break;
        }
        case "DELETE": {
            setInfosStatus(group,false);
            var name = group.querySelector('input[name="name"]').value;
            var id = group.querySelector(".id").innerText;
            setupConfirm(id,'#delete-confirm-modal',name);
            break;
        }
        case "PWD": {
            setInfosStatus(group,false);
            var name = group.querySelector('input[name="name"]').value;
            var id = group.querySelector(".id").innerText;
            setupConfirm(id,'#pwd-confirm-modal',name);
            break;
        }
        case "UPDATE": {
            setInfosStatus(group,false);
            // var name = group.querySelector(".name").innerText;
            var id = group.querySelector(".id").innerText;
            setupUpdate(id,group);
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
        url: "acustomer?a=lock",
        data: {id: id},
        success: function(data) {
            $('#acustomer-response').html(data);
            hideModal('#lock-confirm-modal');
            setDefaultOption('action');
        },
        error: function(error) {
            $('#acustomer-response').html(error.responseText);
        }
    });
}

function active(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "acustomer?a=active",
        data: {id: id},
        success: function(data) {
            $('#acustomer-response').html(data);
            hideModal('#unlock-confirm-modal');
            setDefaultOption('action');
        },
        error: function(error) {
            $('#acustomer-response').html(error.responseText);
        }
    });
}

function deleteCustomer(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    console.log("xóa: " + id);
    $.ajax({
        type: "POST",
        url: "acustomer?a=delete",
        data: {id: id},
        success: function(data) {
            $('#acustomer-response').html(data);
            hideModal('#delete-confirm-modal');

        },
        error: function(error) {
            $('#acustomer-response').html(error.responseText);
        }
    });
}

function pwd(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "acustomer?a=pwd",
        data: {id: id},
        success: function(data) {
            $('#acustomer-response').html(data);
            hideModal('#pwd-confirm-modal');
            setDefaultOption('action');
        },
        error: function(error) {
            $('#acustomer-response').html(error.responseText);
        }
    });
}

function setupUpdate(id,group) {
    const detailContainer = document.getElementById("detail-container");
    detailContainer.classList.add("active");
    getDetail(id).then(function(user) {
        renderDetail(user)// Gọi render khi dữ liệu đã được lấy về
        const detailInputs = detailContainer.querySelectorAll('input.info-input');
        detailInputs.forEach(function(input) {
            input.readOnly=false;
        });
        detailContainer.querySelector('.edit-tool').style.display = "table-row";
        const genders = detailContainer.querySelectorAll('input[name="gender"]');
        genders.forEach(gender => {
            gender.closest('.form-check').style.display="block";
            gender.readOnly=false;
        });
    }).catch(function(error) {
        console.error("Có lỗi khi lấy chi tiết khách hàng:", error);
        $('#detail-container tbody').html('<p>Không thể tải thông tin khách hàng.</p>'); // Hiển thị thông báo lỗi nếu có
    });

    group.parentNode.insertBefore(detailContainer, group.nextElementSibling);

    const rowInputs = group.querySelectorAll('input.info-input');
    rowInputs.forEach(function(input) {
       input.readOnly=false;
    });


}

function cancelUpdate() {
    const detailContainer = document.querySelector("#detail-container");
    detailContainer.classList.remove("active");
    const group = document.querySelector('.info-container');
    setInfosStatus(group,false);
    setDefaultOption("action");

}

function update(id) {
    console.log(id);
    const row = document.querySelector(`#customer${id}`);
    const detail = document.querySelector("#detail-container");
    console.log(row);
    const name = row.querySelector('input[name="name"]').value;
    const gender = detail.querySelector('input[name="gender"]:checked').value;
    const dob = detail.querySelector('input[name="dob"]').value;
    const phone = detail.querySelector('input[name="phone"]').value;

    $.ajax({
        type: "POST",
        url: "acustomer?a=update",
        data: {id: id,name:name,gender:gender,dob:dob,phone:phone},
        success: function(data) {
            $('#acustomer-response').html(data);
            setInfosStatus(row,false);
            setDefaultOption("action");
            closeDetail();s
        },
        error: function(error) {
            $('#acustomer-response').html(error.responseText);
        }
    });

}

function setInfosStatus(group,status) {
    const inputs = group.querySelectorAll("input");
    var status =!status;
    inputs.forEach((input) => {
        input.readOnly = status;
    });
    group.querySelector("select").disabled = status;
    group.querySelector("select[name='action']").disabled =false;
}

function queryCustomer(page) {
    console.log("query");
    return new Promise((resolve, reject) => {
        $.ajax({
            url: "acustomer?a=query&page="+page,
            type: 'GET',
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

// function query(page) {
//     queryCustomer(page).then(function(users) {
//         renderCustomers(page,users)// Gọi render khi dữ liệu đã được lấy về
//         updatePagination('#customer-pagination', (page-1));
//     }).catch(function(error) {
//         console.error("Có lỗi khi lấy chi tiết khách hàng:", error);
//         $('#detail-container tbody').html('<p>Không thể tải thông tin khách hàng.</p>'); // Hiển thị thông báo lỗi nếu có
//     });
// }

function renderCustomers(page,customers) {
    const customersContainer = $('#infoTable tbody');
    customersContainer.empty();
    var stt = 0;
    customers.forEach(function(user) {
        var isLocked ="";
        if(user.avai=='0') isLocked = "locked";
        stt+=1;
        const customerItem = `
            <tr class="group ${isLocked}" id="customer${user.id}" onclick="showDetail(event);">
                <th scope="row" class="grid-col-0_5 text-center" >
                    <span class="stt">${stt}</span>
                </th>
                <td class="grid-col-0_5 text-center user-id">
                    <span class="id">${user.id}</span>
                </td>
                <td class="grid-col-2">
                    <input class="info-input" type="text" name="name" value="${user.name}" readonly>
                </td>
                <td class="grid-col-2">
                    <span class="email">${user.email}</span>
                </td>
                <td class="grid-col-1 text-center">
                    <a href="aorder?action=orderof&id=${user.id}">Đơn hàng</a>
                </td>
                <td class="grid-col-1" onclick="event.stopPropagation();">
                    <select name="action" onchange="handleChange(event);" data-default="none">
                        <option value="none">...</option>
                            <option class="unlock-option" value="unlock">Mở khóa</option>
                            <option class="lock-option" value="lock">Khóa</option>
                        <option value="pwd">Cấp mật khẩu</option>
                        <option value="delete">Xóa</option>
                        <option value="update">Cập nhật</option>
                    </select>
                </td>
            </tr>
    `;
        customersContainer.append(customerItem);
    });
}

function closeDetail() {
    document.querySelector('#detail-container').classList.remove('active');
}

document.addEventListener("DOMContentLoaded", function() {
    document.querySelector('#addCustomerForm').addEventListener('submit', (e) => {
        e.preventDefault();
        isLoading(true);
        this.submit();
    })
});



