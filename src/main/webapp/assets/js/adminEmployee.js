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
    var id = event.currentTarget.querySelector('.employee-id .id').innerText;
    getDetail(id).then(function(user) {
        renderDetail(user)// Gọi render khi dữ liệu đã được lấy về
    }).catch(function(error) {
        console.error("Có lỗi khi lấy chi tiết nhân viên:", error);
        $('#detail-container tbody').html('<p>Không thể tải thông tin nhân viên.</p>'); // Hiển thị thông báo lỗi nếu có
    });


    currentRow.parentNode.insertBefore(detailContainer, currentRow.nextElementSibling);
}

function getDetail(id) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'aemployee?a=info&id='+id, // URL của Servlet hoặc API
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

function renderDetail(employee) {
    const detailContainer = document.querySelector("#detail-container");
    // var genderName="Nam";
    // if(user.gender=="1") genderName = "Nam"
    // else if(user.gender=="2") genderName = "Nữ"
    // else genderName = "Không xác định"
    var user = employee.user;
    const detailE = `
           <tr class="group">
                <th scope="row" class="grid-col-1" >
                    <input type="text" name="eid" value="${employee.id}" hidden>
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
                    <div class="flex-coll" style="align-items: start;">
                        <input class="info-input" type="text" name="area" value="${employee.area}" readonly>
                        <input class="info-input" type="text" name="position" value="${employee.position}" readonly>

                    </div>
                </td>
                <td class="grid-col-1">
                    <span class="updateAt">${employee.updateAt}</span>
                </td>
                <td class="grid-col-1">
                    <span class="dateIn">${user.dateIn}</span>
                </td>
<!--                <td class="grid-col-1_5">-->
<!--                    <div class="flex-coll" style="align-items: end;">-->
<!--                        <button class="btn btn-primary btn-setupupdate" type="button" onclick="setupUpdate(event);" style="background-color: blue; color:white">Cập nhật</button>-->
<!--                        <div class="flex-roww secondary-btns-container" style="display: none">-->
<!--                            <button class="btn btn-fourth btn-save" style="margin-right: 20px;background-color: blue; color:white" type="button" onclick="saveUpdateDetails(event);">Lưu</button>-->
<!--                            <button class="btn btn-fourth btn-cancel" style="" type="button" onclick="closeDetail();">Hủy</button>-->

<!--                        </div>-->
<!--                    </div>-->
<!--                </td>-->
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
            var email = group.querySelector(".email").innerText;
            var object = name+ "\n" + email;
            var id = group.querySelector(".id").innerText;
            setupConfirm(id,'#lock-confirm-modal',object);
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

function closeModal(event) {
    event.currentTarget.closest(".modall").classList.remove("active");

}

function lock(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "aemployee?a=lock",
        data: {id: id},
        success: function(data) {
            console.log(data);
            $('#aemployee-response').html(data);
            hideModal('#lock-confirm-modal');
            setDefaultOption('action');

        },
        error: function(error) {
            $('#aemployee-response').html(error.responseText);
        }
    });
}

function active(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "aemployee?a=active",
        data: {id: id},
        success: function(data) {
            $('#aemployee-response').html(data);
            hideModal('#unlock-confirm-modal');
            setDefaultOption('action');
        },
        error: function(error) {
            $('#aemployee-response').html(error.responseText);
        }
    });
}

function setupConfirm(id,modalID,object) {
    // var name = group.querySelector('input[name="name"]').value;
    openModal(modalID)
    document.querySelector(modalID).querySelector('.object').innerText = object;
    document.querySelector(modalID).querySelector('input[name="id"]').value = id;
}

function pwd(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    $.ajax({
        type: "POST",
        url: "aemployee?a=pwd",
        data: {id: id},
        success: function(data) {
            $('#aemployee-response').html(data);
            hideModal('#pwd-confirm-modal');
            setDefaultOption('action');
        },
        error: function(error) {
            $('#aemployee-response').html(error.responseText);
        }
    });
}

function deleteEmployee(event) {
    var id = event.currentTarget.closest('.group').querySelector('input[name="id"]').value;
    console.log("xóa: " + id);
    $.ajax({
        type: "POST",
        url: "aemployee?a=delete",
        data: {id: id},
        success: function(data) {
            $('#aemployee-response').html(data);
            hideModal('#delete-confirm-modal');

        },
        error: function(error) {
            $('#aemployee-response').html(error.responseText);
        }
    });
}

function setupUpdate(id,group) {
    const detailContainer = document.getElementById("detail-container");
    detailContainer.classList.add("active");
    getDetail(id).then(function(employee) {
        renderDetail(employee)// Gọi render khi dữ liệu đã được lấy về
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

function update(id) {
    console.log(id);
    const row = document.querySelector(`#employee${id}`);
    const detail = document.querySelector("#detail-container");
    console.log(row);
    const name = row.querySelector('input[name="name"]').value;
    const gender = detail.querySelector('input[name="gender"]:checked').value;
    const dob = detail.querySelector('input[name="dob"]').value;
    const phone = detail.querySelector('input[name="phone"]').value;
    const position = detail.querySelector('input[name="position"]').value;
    const area = detail.querySelector('input[name="area"]').value;

    $.ajax({
        type: "POST",
        url: "aemployee?a=update",
        data: {id: id,name:name,gender:gender,dob:dob,phone:phone,position:position, area:area},
        success: function(data) {
            $('#aemployee-response').html(data);
            setInfosStatus(row,false);
            setInfosStatus(detail,false);
            setDefaultOption('action');
            closeDetail();
            // detail.querySelector('.edit-tool').style.display = "none";
            hideModal('#delete-confirm-modal');


        },
        error: function(error) {
            $('#aemployee-response').html(error.responseText);
        }
    });

}

function closeDetail() {
    document.querySelector('#detail-container').classList.remove('active');
}

function add() {
    isLoading(true);
    const formE = document.querySelector('#addEmployeeForm');
    var formData = new FormData(formE);
    var name = formData.get('name');
    var email = formData.get('email');
    var position = formData.get('position');
    var area = formData.get('area');
    var pwd = formData.get('pwd');
    $.ajax({
        type: "POST",
        url: "aemployee?a=add",
        data: {name:name,position:position, area:area,email:email,pwd:pwd},
        success: function(data) {
            isLoading(false);
            $('#aemployee-response').html(data);
        },
        error: function(error) {
            isLoading(false);
            $('#aemployee-response').html(error.responseText);
        }
    });

}

function errorEmail(message) {
    const formE= document.querySelector('#addEmployeeForm');
    formE.querySelector(".email-error").innerText = message;
}


document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('#add-btn').addEventListener('click', function(e) {
        e.preventDefault();
        openModal("#add-modal");
    });
});

function getRoles(url) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: url,
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

function showRoles(event) {
    console.log("roles");
    event.preventDefault();
    openModal("#roles-modal");
    var url = event.currentTarget.href;
    getRoles(url).then(function(user) {
        renderRoles(user)
    }).catch(function(error) {
        console.error("Có lỗi khi lấy tải quyền truy cập:", error);
        showErrorToast2("Không thể tải quyền truy cập của tài khoản",'none')
    });

}

function renderRoles(user) {
    const container = document.querySelector("#updateRolesForm");
    var name = user.name;
    var email = user.email;
    var id= user.id;
    var roles = user.roles;
    container.querySelector('.name').innerText = name;
    container.querySelector('.email').innerText = email;
    container.querySelector('input[name="id"]').value = id;
    const roleInputs = container.querySelectorAll('input.role');
    roleInputs.forEach(function(input) {
        if(roles.includes(input.name)){
            input.checked = true;

        }
        input.disabled=true;
    });
    container.querySelector('.btn-cancel').style.display = 'none';
    container.querySelector('.btn-save').style.display = 'none';
    container.querySelector('.btn-setup').style.display = 'block';

}

function setupUpdateRoles(event) {
    const group = event.currentTarget.closest('.group');
    const inputs = group.querySelectorAll('input');
    inputs.forEach(function (input) {
        input.disabled = false;
    });
    group.querySelector('.btn-save').style.display = 'block';
    group.querySelector('.btn-cancel').style.display = 'block';
    group.querySelector('.btn-setup').style.display = 'none';

}

function saveRoles() {
    const form = document.querySelector('#updateRolesForm');
    var id = form.querySelector("input[name='id']").value;
    var employee = form.querySelector("input[name='employee']").checked ? "employee" : null;
    var customer = form.querySelector("input[name='customer']").checked ? "customer" : null;
    var product = form.querySelector("input[name='product']").checked ? "product" : null;
    var order = form.querySelector("input[name='order']").checked ? "order" : null;
    var interface = form.querySelector("input[name='interface']").checked ? "interface" : null;
    $.ajax({
        type: "POST",
        url: "aemployee?a=updateRoles",
        data: {id: id, employee: employee, customer: customer, product: product, order: order,interface:interface},
        success: function (data) {
            $('#aemployee-response').html(data);
            hideModal('#roles-modal');
        },
        error: function (error) {
            $('#aemployee-response').html(error.responseText);
        }
    });
}




