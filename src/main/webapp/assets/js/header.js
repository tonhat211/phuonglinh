function initUpdateAddress(event,parent, formElement) {
    event.preventDefault();
    const parentEs= document.querySelectorAll(parent);
    parentEs.forEach((element) => {
        if(element.classList.contains('active')) {
            element.classList.remove('active');
        }
    });
    const parentElement = event.currentTarget.closest(parent);
    parentElement.classList.add('active');
    let id = parentElement.querySelector('.id').innerText;
    let receiver = parentElement.querySelector('.receiver').innerText;
    let phone = parentElement.querySelector('.phone').innerText;
    let street = parentElement.querySelector('.street').innerText;
    let village = parentElement.querySelector('.village').innerText;
    let district = parentElement.querySelector('.district').innerText;
    let province = parentElement.querySelector('.province').innerText;
    const form = document.querySelector(formElement);
    const inputs = form.querySelectorAll('input');
    inputs.forEach(input => {
        if(input.name == "id") input.value = id;
        else if(input.name == "receiver") input.value = receiver;
        else if(input.name == "phone") input.value = phone;
        else if(input.name == "street") input.value = street;
        else if(input.name == "village") input.value = village;
        else if(input.name == "district") input.value = district;
        else if(input.name == "province") input.value = province;
        else if(input.name == "action") input.value = "updateAddress";

    });
    form.querySelector('.edit-title').innerText = "Cập nhật địa chỉ";
    document.querySelector('#address-form-container').classList.add('active');
}

function initAddAddress(formElement) {
    const form = document.querySelector(formElement);
    const inputs = form.querySelectorAll('input');
    inputs.forEach(input => {
        input.value = '';
    });
    form.querySelectorAll('input[name="action"]').value="addAddress";
    form.querySelector('.edit-title').innerText = "Thêm địa chỉ";
    document.querySelector('#address-form-container').classList.add('active');
}

function updateAddressAjax(action,id,receiver,phone,detail,village,district,province) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'profile', // URL của Servlet hoặc API
            type: 'GET',
            dataType: 'json',
            data: {a: action, id:id ,receiver: receiver,phone: phone,detail:detail,village:village,district:district,province:province},
            success: function(data) {
                resolve(data);// Gọi resolve với dữ liệu trả về
                cancelEdit('#editAddressForm');
                showSuccessToast('Cập nhật địa chỉ thành công','#toast-header');
            },
            error: function(error) {
                reject(error); // Nếu có lỗi, gọi reject
            }
        });
    });
    // $.ajax({
    //     type: "GET",
    //     url: "profile",
    //     data: {action: action, id:id ,receiver: receiver,phone: phone,street:street,village:village,district:district,province:province},
    //     success: function(data) {
    //         var addresses = $('#address-container .address-item');
    //         addresses.each(function(index, element) {
    //             if ($(element).hasClass('active')) {
    //                 $(element).html(data);
    //             }
    //         });
    //         cancelEdit('#editAddressForm');
    //         showSuccessToast('Cập nhật địa chỉ thành công','#toast-header');
    //     },
    //     error: function(error) {
    //         console.error("Error during querying address: ", error);
    //     }
    // });
}

function addAddressAjax(action,id,receiver,phone,detail,village,district,province) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'profile', // URL của Servlet hoặc API
            type: 'GET',
            dataType: 'json',
            data: {a: action, id:id ,receiver: receiver,phone: phone,detail:detail,village:village,district:district,province:province},
            success: function(data) {
                resolve(data);// Gọi resolve với dữ liệu trả về
                cancelEdit('#editAddressForm');
                showSuccessToast('Thêm địa chỉ thành công','#toast-header');
            },
            error: function(error) {
                reject(error); // Nếu có lỗi, gọi reject
            }
        });
    });
    // $.ajax({
    //     type: "GET",
    //     url: "profile",
    //     data: {action: action, id:id ,receiver: receiver,phone: phone,street:street,village:village,district:district,province:province},
    //     success: function(data) {
    //         $('#address-container').html(data);
    //         cancelEdit('#editAddressForm');
    //         showSuccessToast('Thêm địa chỉ thành công','#toast-header');
    //
    //     },
    //     error: function(error) {
    //         console.error("Error during querying address: ", error);
    //     }
    // });
}

function cancelEdit(element) {
    const form = document.querySelector(element);
    const inputs = form.querySelectorAll('input');
    inputs.forEach(input => {
        input.value = '';
        if(input.name=="action") input.value = 'addAddress';
    });
    form.querySelector('.edit-title').innerText = "Thêm địa chỉ";
    if(document.querySelector('#address-form-container.active')) {
        document.querySelector('#address-form-container').classList.remove('active');
    }

}

function initDeleteAddress(event,parent,modal,confirmModal) {
    const parentEs= document.querySelectorAll(parent);
    parentEs.forEach((element) => {
        if(element.classList.contains('active')) {
            element.classList.remove('active');
        }
    });
    const parentElement = event.currentTarget.closest(parent);
    parentElement.classList.add('active');
    let id = parentElement.querySelector('.id').innerText;
    let receiver = parentElement.querySelector('.receiver').innerText;
    let phone = parentElement.querySelector('.phone').innerText;
    let address = parentElement.querySelector('.address').innerText;

    const modalElement = parentElement.closest(modal);
    const confirmElement = modalElement.querySelector(confirmModal);
    confirmElement.querySelector('.id').innerText = id;
    confirmElement.querySelector('.receiver').innerText = receiver;
    confirmElement.querySelector('.phone').innerText = phone;
    confirmElement.querySelector('.address').innerText = address;
    confirmElement.classList.add('active');
}

function deleteAddressAjax(id) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'profile', // URL của Servlet hoặc API
            type: 'GET',
            dataType: 'json',
            data: {a: "deleteAddress",id: id},
            success: function(data) {
                resolve(data);// Gọi resolve với dữ liệu trả về
                $('.address-confirm').removeClass('active');
                showSuccessToast('Xóa địa chỉ thành công','#toast-header');
            },
            error: function(error) {
                reject(error); // Nếu có lỗi, gọi reject
            }
        });
    });
}

function cancelDelete(event,element) {
    event.currentTarget.closest(element).classList.remove('active');
}

function initUpdateInfo(event,parent) {
    const form = event.currentTarget.closest(parent);
    const inputs = form.querySelectorAll('input');
    inputs.forEach(input => {
        input.removeAttribute('readonly');
        if(input.name=="dateIn") input.readOnly = true;
        if(input.name=="email") input.readOnly = true;
    });
    const radios = form.querySelectorAll('input[type="radio"]');
    radios.forEach(radio => {
        radio.disabled = false;
    });
    form.querySelector('.update-action').style.display = 'none';
    form.querySelector('.edit-title').style.display = 'block';
    form.querySelector('.btn-edit-container').style.display = 'flex';
}

function cancelEditInfo(formQuery) {
    const form = document.querySelector(formQuery);
    const inputs = form.querySelectorAll('input');
    inputs.forEach(input => {
        input.setAttribute('readonly',true);
    });
    const radios = form.querySelectorAll('input[type="radio"]');
    radios.forEach(radio => {
        radio.disabled = true;
    });
    form.querySelector('.update-action').style.display = 'flex';
    form.querySelector('.edit-title').style.display = 'none';
    form.querySelector('.btn-edit-container').style.display = 'none';

//     them ham goi ajax de lay lai gia tri ban dau
}

function initChangePwd(event,pwdForm) {
    event.preventDefault();
    document.querySelector(pwdForm).classList.add('active');
}

function cancelChangePwd(pwdForm) {
    document.querySelector(pwdForm).classList.remove('active');
    // if(document.querySelector('#edit-pwd-form-container.active')) {
    //     document.querySelector('#edit-pwd-form-container').classList.remove('active');
    // }
}

function manageAddress(event) {
    event.preventDefault();
    document.querySelector('.address-modal').classList.add('active');
    // Gọi hàm getAddressList và truyền kết quả vào renderAddressList
    getAddressList().then(function(addresses) {
        renderAddressList(addresses); // Gọi render khi dữ liệu đã được lấy về
    }).catch(function(error) {
        console.error("Có lỗi khi lấy danh sách địa chỉ:", error);
        $('#address-container').html('<p>Không thể tải danh sách địa chỉ.</p>'); // Hiển thị thông báo lỗi nếu có
    });
}

function manageProfile(event) {
    event.preventDefault();
    document.querySelector('.info-modal').classList.add('active');
    if(document.querySelector('#edit-pwd-form-container').classList.contains('active')) {
        document.querySelector('#edit-pwd-form-container').classList.remove('active');
    }
    getUserInfo().then(function(info) {
        renderUserInfo(info);
    }).catch(function(error) {
    });
    cancelEditInfo('#editInfoForm');

}

function loadUserInfo() {
    $.ajax({
        type: "post",
        url: "profile",
        data: {a: "info"},
        success: function(data) {
            $('#editInfoForm input[name="name"]').val(data.name);
            $('#editInfoForm input[name="email"]').val(data.email);
            $('#editInfoForm input[name="phone"]').val(data.phone);
            // $('#editInfoForm input[name="gender"][value="'+data.gender+'"]').checked = true;
            $('#editInfoForm input[name="birthday"]').val(data.dob);
            const genders = document.querySelectorAll('#editInfoForm input[name="gender"]');
            genders.forEach(gender => {
                if(gender.value==data.gender) gender.checked = true;
                else gender.checked = false;
            });

        },
        error: function(error) {
            console.error("Error during querying address: ", error);
        }
    });
}

function openHeaderModal(modal) {
    const modalElement = document.querySelector(modal);
    modalElement.classList.add('active');

}

function getUserInfo() {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'profile?a=info', // URL của Servlet hoặc API
            type: 'GET',
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

function renderUserInfo(info) {
    // const editInfoForm = $('#editInfoForm');
    $('#editInfoForm input[name="name"]').val(info.name);
    $('#editInfoForm input[name="email"]').val(info.email);
    $('#editInfoForm input[name="phone"]').val(info.phone);
    $('#editInfoForm input[name="birthday"]').val(info.dob);
    const genders = document.querySelectorAll('#editInfoForm input[name="gender"]');
    genders.forEach(gender => {
        if(gender.value==info.gender) gender.checked = true;
        else gender.checked = false;
        gender.disabled = true;
    });
    var script = info.script;
    if (script !== undefined) {
        $('#header-response').html(script);
    }


}

function getAddressList() {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'profile?a=address', // URL của Servlet hoặc API
            type: 'GET',
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

function renderAddressList(addresses) {
    // Xóa các địa chỉ cũ (nếu có) để tránh trùng lặp
    const addressListContainer = $('#address-container'); // Giả sử bạn có một div với id là address-list
    addressListContainer.empty();

    // Duyệt qua danh sách các địa chỉ và tạo phần tử HTML cho mỗi địa chỉ
    addresses.forEach(function(address) {
        const addressItem = `
            <div class="address-item">
                <div class="flex-roww" style="justify-content: space-between; align-items: start">
                    <div class="address-info">
                        <p><span class="receiver">${address.receiver}</span> | <span class="phone">${address.phone}</span></p>
                        <p class="id" hidden="">${address.id}</p>
                        <p style="margin-top: 5px">Địa chỉ:</p>
                        <div>
                            <p class="address" style="margin-left: 10px;">
                                <span class="street">${address.detail}</span> - 
                                <span class="village">${address.village}</span> - 
                                <span class="district">${address.district}</span> - 
                                <span class="province">${address.province}</span>
                            </p>
                        </div>
                    </div>
                    <div class="flex-coll update-action">
                        <i class="bi bi-pencil-square btn-edit" onclick="initUpdateAddress(event, '.address-item', '#editAddressForm')"></i>
                        <i class="bi bi-trash btn-delete" onclick="initDeleteAddress(event, '.address-item', '.modall', '.address-confirm')"></i>
                    </div>
                </div>
                <div class="seperate-horizontal-90"></div>
            </div>
        `;

        // Thêm địa chỉ vào container
        addressListContainer.append(addressItem);
    });
}

function openElement(element) {
    document.querySelector(element).classList.add('active');
    document.querySelector(element).style.display = 'block';

}

function closeElement(element) {
    document.querySelector(element).classList.remove('active');

}

// dam bao dom da tai xong
document.addEventListener("DOMContentLoaded", function() {
    const updateInfoForm = document.querySelector('#editInfoForm');
    updateInfoForm.addEventListener('submit', function(e) {
        e.preventDefault();
        var formData = new FormData(this);
        let name = formData.get('name');
        let email = formData.get('email');
        let gender = formData.get("gender");
        let phone = formData.get("phone");
        let birthday = formData.get("birthday");
        let dateIn = formData.get("dateIn");
        let action = formData.get("a");
        saveUpdateInfo(action,name,email,gender,phone,birthday,dateIn).then(function(info) {
            renderUserInfo(info);
        }).catch(function(error) {
        });

    });

    document.querySelector("#editPasswordForm").addEventListener('submit', function(event) {
        event.preventDefault();
        var formdata = new FormData(document.querySelector("#editPasswordForm"));
        var action = formdata.get("action");
        var currentPassword = formdata.get("currentPassword");
        var password = formdata.get("password");
        var repassword = formdata.get("repassword");
        changePwd(action,currentPassword,password,repassword);
    });
});

function saveUpdateInfo(action,name,email,gender,phone,birthday,dateIn) {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "post",
            url: "profile",
            dataType: 'json',
            data: {a: action ,name: name,email:email,gender:gender, phone: phone,birthday:birthday,dateIn:dateIn},
            success: function(data) {
                resolve(data);
            },
            error: function(error) {
                reject(error);
            }
        });
    });



}
function changePwd(action,currentPassword,password,repassword) {
    //  nhung loi: mat khau yeu, mat khau khong khop, email da duoc dang ky
    const regex = /^(?=.*[a-z]).{8,}$/; //test mat khau: it nhat 8 kí tu

    if(!regex.test(password)){
        wrongNewPwd('#editPasswordForm',"* Mật khẩu phải ít nhất 8 ký tự, gồm chữ và số");
        // tellWeakPassword("#editPasswordForm .pwd-error","#editPasswordForm .repwd-error",'#editPasswordForm input[name="password"]','#editPasswordForm input[name="repassword"]');
    } else if(password != repassword) {
        wrongNewPwd('#editPasswordForm',"Mật khẩu không trùng khớp");
        // tellNotSamePassword("#editPasswordForm .pwd-error");
        // tellNotSamePassword("#editPasswordForm .repwd-error");
    } else {
        clearError('#editPasswordForm');
        $.ajax({
            url: "profile",
            method: "POST",
            data: { a: action, currentPassword: currentPassword, password : password,repassword: repassword},
            success: function(data) {
                $("#header-response").html(data);
            }
        });
    }
}
function showMenu() {
    document.querySelector('.header_bottom').classList.add('active');
    document.querySelector('.overlay_mob').classList.add('active');
}

function hideMenu() {
    document.querySelector('.header_bottom').classList.remove('active');
    document.querySelector('.overlay_mob').classList.remove('active');
}
