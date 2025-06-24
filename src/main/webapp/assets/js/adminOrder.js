function updateStatus(id,status) {
    updateStatusHelper(id,status).then(function(orderUnit) {
        refreshOrderRow(orderUnit)// Gọi render khi dữ liệu đã được lấy về
        setDefaultOption("action");
    }).catch(function(error) {
        showErrorToast2("Không thể tải thông tin đơn hàng: " + error.responseText,'none');
        // if(detailContainer.classList.contains("active")) {
        //     closeDetail();
        //     setInfosStatus(currentRow,false);
        //     setDefaultOption("action");
        // }
    });
}
function refreshOrderRow(orderUnit) {
    const tableBody = document.querySelector("#infoTable tbody");
    const orderItems = tableBody.querySelectorAll('tr');
    var script = orderUnit.script;
    var order = orderUnit.order;
    $('#aorder-response').html(script);
    if(order.id===0) return;
    for(let i = 0; i < orderItems.length; i++) {
        if(orderItems[i].id === "order"+order.id) {
            const currentOrder = orderItems[i];
            const statusE = currentOrder.querySelector('.current-status');
            statusE.innerText = orderUnit.statusName;
            if(statusE.classList.contains(`status-${order.status-1}`)) {
                statusE.classList.remove(`status-${order.status-1}`)
            }
            statusE.classList.add(`status-${order.status}`)
            currentOrder.querySelector('.updateTime').innerText = orderUnit.updateAt;
            const btnNextStatusE = currentOrder.querySelector('.btn-next-status');
            btnNextStatusE.innerText = orderUnit.nextStatusName;
            if(btnNextStatusE.classList.contains(`btn-status-${order.status}`)) {
                btnNextStatusE.classList.remove(`btn-status-${order.status}`);
            }
            btnNextStatusE.classList.add(`btn-status-${orderUnit.nextStatus}`);
            btnNextStatusE.setAttribute('onclick', `updateStatus('${order.id}','${orderUnit.nextStatus}')`);

            tableBody.insertBefore(currentOrder, orderItems[0]);
            setDefaultOption("action");
            break;
        }
    }
}

function updateStatusHelper(id,status) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'aorder?a=update',
            type: 'POST',
            dataType: 'json',
            data: {id:id,status:status},
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

function handleChange(event) {
    event.stopPropagation();
    let selectedValue = event.target.value;
    console.log("selected value: " + selectedValue);
    const group = event.currentTarget.closest(".group");
    selectedValue=selectedValue.toUpperCase();
    var id = parseInt(group.querySelector('.id').innerText);
    switch (selectedValue) {
        case "3": {
            console.log("const")
            updateStatus(id, selectedValue);
            break;
        }
        default:
            updateStatus(id, selectedValue);

    }
}

function setDefaultOption(name) {
    document.querySelectorAll("select[name="+name+"]").forEach(select => {
        const defaultValue = select.getAttribute('data-default');
        select.value = defaultValue;
    });
}

function query(event) {
    event.preventDefault();
    const statusCondition = document.querySelector('select#byStatus').value;
    const a = event.currentTarget;
    a.href = a.href +"&status=" + statusCondition;
    window.location.href = a.href;
}

function queryBy(event) {
    var value = event.currentTarget.value;
    window.location.href = "aorder?a=query&page=1&status=" + value;
    console.log(value);
}

function showDetail(event,id) {
    event.preventDefault();
    event.stopPropagation();
    getDetail(id).then(function(orderUnit) {
        renderDetail(orderUnit)// Gọi render khi dữ liệu đã được lấy về
    }).catch(function(error) {
        showErrorToast2("Không thể tải chi tiết đơn hàng.",'none');
    });
}

function getDetail(id) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'aorder?a=detail&id='+id, // URL của Servlet hoặc API
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

function renderDetail(orderUnit) {
    const modal = document.querySelector('#detail-modal');
    modal.classList.add('active');
    var order = orderUnit.order;
    modal.querySelector('.id').innerText = order.id;
    modal.querySelector('.createAt').innerText = orderUnit.createAt;
    modal.querySelector('.receiverInfo').innerText = order.receiverInfo;
    modal.querySelector('.product-list').innerText = orderUnit.productList;
    modal.querySelector('.paymentInfo').innerText = orderUnit.paymentInfo;
    modal.querySelector('.shippingFee').innerText = orderUnit.shippingFee;
    modal.querySelector('.discountVoucher').innerText = orderUnit.discountVoucher;
    modal.querySelector('.totalMoney').innerText = orderUnit.totalMoney;
    if(order.isPayed===1) modal.querySelector('.total-money-container').classList.add('no-print');

}

function printDetail() {
    // Lấy nội dung của modal
    const modalE = document.querySelector('#detail-modal');
    const noPrintEs = modalE.querySelectorAll('.no-print-sure');
    noPrintEs.forEach(e => {
        e.style.display = 'none';
    });
    var modalContent = modalE.innerHTML;
// Lấy các thẻ <style> hoặc <link> từ trang hiện tại để giữ nguyên CSS
    var styles = '';
    var styleSheets = document.styleSheets;
    for (var i = 0; i < styleSheets.length; i++) {
        try {
            if (styleSheets[i].cssRules) {
                for (var j = 0; j < styleSheets[i].cssRules.length; j++) {
                    styles += styleSheets[i].cssRules[j].cssText;
                }
            }
        } catch (e) {
            console.log(e);
        }
    }

    // Mở cửa sổ in mới
    var printWindow = window.open('', '', 'height=600,width=800');

    // Tạo nội dung HTML cho cửa sổ in
    printWindow.document.write('<html><head><title>Print Modal</title>');
    printWindow.document.write('<style>' + styles + '</style>'); // Thêm CSS vào cửa sổ in
    printWindow.document.write('</head><body>');
    printWindow.document.write(modalContent); // Thêm nội dung modal vào cửa sổ in
    printWindow.document.write('</body></html>');

    // Đảm bảo tài liệu đã được tải xong, sau đó gọi phương thức in
    printWindow.document.close();
    printWindow.print();  // Gọi cửa sổ in từ cửa sổ mới
    printWindow.close();  // Đóng cửa sổ sau khi in xong (tuỳ chọn)
    noPrintEs.forEach(e => {
        e.style.display = 'flex';
    });
}

function printBase() {
    // Lấy nội dung của modal
    const modalE = document.querySelector('#detail-modal');
    const noPrintEs = modalE.querySelectorAll('.no-print');
    noPrintEs.forEach(e => {
        e.style.display = 'none';
    });

    var modalContent = document.querySelector('#detail-modal').innerHTML;
// Lấy các thẻ <style> hoặc <link> từ trang hiện tại để giữ nguyên CSS
    var styles = '';
    var styleSheets = document.styleSheets;
    for (var i = 0; i < styleSheets.length; i++) {
        try {
            if (styleSheets[i].cssRules) {
                for (var j = 0; j < styleSheets[i].cssRules.length; j++) {
                    styles += styleSheets[i].cssRules[j].cssText;
                }
            }
        } catch (e) {
            console.log(e);
        }
    }

    // Mở cửa sổ in mới
    var printWindow = window.open('', '', 'height=600,width=800');

    // Tạo nội dung HTML cho cửa sổ in
    printWindow.document.write('<html><head><title>Print Modal</title>');
    printWindow.document.write('<style>' + styles + '</style>'); // Thêm CSS vào cửa sổ in
    printWindow.document.write('</head><body>');
    printWindow.document.write(modalContent); // Thêm nội dung modal vào cửa sổ in
    printWindow.document.write('</body></html>');

    // Đảm bảo tài liệu đã được tải xong, sau đó gọi phương thức in
    printWindow.document.close();
    printWindow.print();  // Gọi cửa sổ in từ cửa sổ mới
    printWindow.close();  // Đóng cửa sổ sau khi in xong (tuỳ chọn)
    noPrintEs.forEach(e => {
        e.style.display = 'flex';
    });
}

function setTitle(title) {
    document.querySelector('.pagetitle h1').innerText = title;
}