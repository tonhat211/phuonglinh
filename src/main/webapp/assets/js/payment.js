function openAddressModal(event) {
    event.preventDefault();
    document.querySelector(".change-address-modal").classList.add('active');
    getAddressList().then(function(addresses) {
        renderAddressModal(addresses); // Gọi render khi dữ liệu đã được lấy về
    }).catch(function(error) {
        console.error("Có lỗi khi lấy danh sách địa chỉ:", error);
        $('#address-container2').html('<p>Không thể tải danh sách địa chỉ.</p>'); // Hiển thị thông báo lỗi nếu có
    });
}

function renderAddressModal(addresses) {
    // Xóa các địa chỉ cũ (nếu có) để tránh trùng lặp
    const addressListContainer = $('#address-container2'); // Giả sử bạn có một div với id là address-list
    addressListContainer.empty();

    // Duyệt qua danh sách các địa chỉ và tạo phần tử HTML cho mỗi địa chỉ
    addresses.forEach(function(address) {
        const addressItem = `
             <div class="address-item" onclick="selectAddressItem(event);">
                <div class="flex-roww" style="justify-content: space-between; align-items: start">
                    <div class="address-info">
                        <p><span class="receiver">${address.receiver}</span> |   <span class="phone">${address.phone}</span></p>
                        <p class="id" hidden><%=a.id%></p>
                        <p style="margin-top: 5px">Địa chỉ: <span class="address">${address.province} - ${address.district} - ${address.village} - ${address.detail}</span></p>
                    </div>
                </div>
                <div class="seperate-horizontal-90"></div>
            </div>
        `;

        // Thêm địa chỉ vào container
        addressListContainer.append(addressItem);
    });
}


// dam bao dom da tai xong
document.addEventListener("DOMContentLoaded", function() {
    const paymentForm = document.querySelector('#paymentForm');
    paymentForm.addEventListener('submit', async function (event) {
        event.preventDefault();
        const addressInput = this.querySelector('input[name="address-input"]');
        if(!document.querySelector('.chosen-address .receiver')) showInfoToast2('Hãy chọn địa chỉ giao hàng!','none');
        var receiver = document.querySelector('.chosen-address .receiver').innerText;
        var phone = document.querySelector('.chosen-address .phone').innerText;
        var address = document.querySelector('.chosen-address .address').innerText;

        var fullAddress = receiver +" (" + phone + ")   |" + address;
        addressInput.value = fullAddress;
        try {
            // const canSubmit = await checkVoucher('order'); // Gọi hàm checkVoucher
            let canSubmit = true; // Gọi hàm checkVoucher
            checkQty(this).then(function(id) {
                console.log("check qty -> 0");
                renderExhaustedRow(id);
                if(id!==0) canSubmit=false;
                if (canSubmit) {
                    console.log("Submitting form...");
                    paymentForm.submit();
                } else {
                    console.log("Form cannot be submitted.");
                }
            }).catch(function(error) {
                showErrorToast2("Không thể tải thông tin sản phẩm: " + error.responseText,'none');
            });

        } catch (err) {
            console.error("Error occurred during voucher check:", err);
        }

    });
});


function checkVoucher(moreAction) {
    console.log("check voucher");

    return new Promise((resolve, reject) => {
        var voucherID = document.querySelector('input[name="voucher"]').value;

        // Nếu voucherID trống, trả về true ngay lập tức
        if (voucherID === "") {
            document.querySelector('.message-error').innerText = "";
            resolve(true); // Trả về true
            return;
        }

        // Lấy danh sách cart-id
        var cartIds = [];
        const cartidEs = document.querySelectorAll('input[name="cart-id"]');
        cartidEs.forEach(function(ele) {
            cartIds.push(ele.value);
        });

        // Gọi Ajax để kiểm tra voucher
        $.ajax({
            type: "POST",
            url: "payment",
            data: {
                a: "voucher",
                voucher: voucherID,
                cartIds: JSON.stringify(cartIds),
                morea: moreAction
            },
            success: function(data) {
                console.log("data:" + data);
                if (moreAction != null) {
                    if (data === "true") {
                        resolve(true); // Điều kiện hợp lệ
                    } else {
                        $('#payment-response').html(data); // Cập nhật giao diện
                        resolve(false); // Không cần submit form
                    }
                } else {
                    $('#payment-response').html(data); // Cập nhật giao diện
                    resolve(false); // Không cần submit form
                }
            },
            error: function(error) {
                $('#payment-response').html(error.responseText);
                reject(error); // Trả về lỗi
            }
        });
    });
}

function checkQty(formE) {
    var formData = new FormData(formE);
    return new Promise((resolve, reject) => {
        // Gọi Ajax để kiểm tra qty
        $.ajax({
            type: "POST",
            url: "payment?a=qty",
            data: {"cart-ids": JSON.stringify(formData.getAll("cart-id"))},
            dataType: 'json',
            success: function (data) {
                console.log("success: " + data);
                resolve(data);
            },
            error: function (error) {
                console.log("error: ");

                reject(error);
            }
        });
    });
}

function applyVoucher(newTotalMoney,discountMoney) {
    document.querySelector('#total-money').innerText = newTotalMoney;
    document.querySelector('#discount-money').innerText = discountMoney;
    document.querySelector('.message-error').innerText = "";
}

function cancelVoucher(message) {
    document.querySelector('#total-money').innerText = document.querySelector('input[name="total-money-input"]').value.toLocaleString('vi-VN') + ' ₫';
    document.querySelector('#discount-money').innerText = '0';
    document.querySelector('.message-error').innerText = message;
}


function selectAddressItem(event) {
    let receiver = event.currentTarget.querySelector('.receiver').innerText;
    let phone = event.currentTarget.querySelector('.phone').innerText;
    let address = event.currentTarget.querySelector('.address').innerText;

    document.querySelector('.chosen-address .receiver').innerText = receiver;
    document.querySelector('.chosen-address .phone').innerText = phone;
    document.querySelector('.chosen-address .address').innerText = address;

    document.querySelector(".change-address-modal").classList.remove('active');

}

function renderExhaustedRow(id) {
    console.log("renderExhaustedRow:" + id);
    if(id!==0) {
        const row = document.querySelector(`#de-${id}`);
        row.classList.add('exhausted');
        showErrorToast2('Số lượng không đủ','none');
    }

}