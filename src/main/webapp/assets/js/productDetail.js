function updatePrice(price) {
    document.querySelector('.cur-price').innerText = price;
}

function checkVersion(event,otherParent,productID,kind) {
    let version = event.currentTarget.innerText;
    let other;
    const otherParentE = document.querySelector(otherParent);
    const otherEs = otherParentE.querySelectorAll('.option-item');
    for(let i=0;i<otherEs.length;i++) {
        if(otherEs[i].classList.contains('active')) {
            other= otherEs[i].innerText;
        }
    }
    checkVersionAjax(productID,kind, version, other);

}

function checkVersionAjax(productID,kind, version, other) {
    $.ajax({
        type: "GET",
        url: "product?action=checkVersion",
        data: {"productID" : productID,"kind": kind, "version" : version, "other" : other},
        success: function(data) {
            $('#productDetail-response').html(data);
        },
        error: function(error) {
            console.error("Error during querying: ", error);
        }
    });
}
function addToCart() {
    var id = document.querySelector('input[name="chosen-detailID"]').value;
    if(id=="") {
        showInfoToast2("Hãy chọn phân loại!",'none');
        return;
    }

    $.ajax({
        type: "POST",
        url: "cart?a=add",
        data: {id:id},
        success: function(data) {
            $('#productDetail-response').html(data);
        },
        error: function(error) {
            console.error("Error during querying: ", error);
        }
    });
}

function buy(event) {
    event.preventDefault();
    var id = document.querySelector('input[name="chosen-detailID"]').value;
    if(id=="") {
        showInfoToast2("Hãy chọn phân loại!",'none');
        return;
    }
    var url =event.currentTarget.href +"&id="+id;
    window.location.href = url;

}

function checkClassify(id) {
    document.querySelector('input[name="chosen-detailID"]').value = id;
    $.ajax({
        type: "POST",
        url: "product?a=classify",
        data: {id:id},
        success: function(data) {
            // console.log(data);
            $('#productDetail-response').html(data);
        },
        error: function(error) {
            console.error("Error during querying: ", error);
        }
    });
}

function updatePrice(price,prePrice) {
    const productPrices = document.querySelectorAll('.cur-price');
    productPrices.forEach(function(productPrice) {
        productPrice.innerText = price;
    });
    const prePrices = document.querySelectorAll('.pre-price');
    prePrices.forEach(function(productPrice) {
        productPrice.innerText = prePrice;
    });
}