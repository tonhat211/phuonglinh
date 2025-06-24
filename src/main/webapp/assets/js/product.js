function moreProduct(event) {
    event.preventDefault();
    isLoading(true);
    const menu =  document.querySelector('#header-main-menu');
    const activeMenu = menu.querySelector('a.active');
    var category = 'nam';
    if(activeMenu) {
        category = activeMenu.getAttribute('data-value');
    }

    var url = event.currentTarget.href + "&c=" + category + "&page="+event.currentTarget.getAttribute('data-value');
    getProductList(url).then(function(productUnits) {
        renderProductList(productUnits);
        isLoading(false);// Gọi render khi dữ liệu đã được lấy về
    }).catch(function(error) {
        showErrorToast2("Có lỗi khi lấy sản phẩm",'none');
        isLoading(false);
    });
}

function updateMorePage(newPage) {
    const btnMoreProduct = document.querySelector('#btn-more-product');
    var currentPage = btnMoreProduct.getAttribute('data-value');
    if(currentPage==newPage) {
        showInfoToast2("Đã tới cuối trang",'none');
    } else {
        btnMoreProduct.setAttribute('data-value', newPage);
    }
}

function renderProductList(json) {
    var productUnits = json.productUnits;
    var script = json.script;
    const productListContainer = $('#product-list');
    console.log("vo vong lap")
    productUnits.forEach(function(productUnit) {
        var product = productUnit.product;
        var saleE = "";
        var saleProgram = productUnit.saleProgram;
        if(saleProgram.title!==undefined) {
            saleE=`
                <div class="info promotion">
                    <p class="promotion-item">${saleProgram.title}</p>
                </div>
            `;
        }

        const productItem = `
             <div class="grid-col-2_4 product-item-container grid-col-4_tab grid-col-6_mob">
                <div class="product-item product-item_mob">
                    <a href="product?a=detail&id=${product.id}">
                        <div class="img-container flex-roww" style="justify-content: center;">
                            <img class="img-product" src="./assets/img/thumbnail/${product.thumbnail}" alt="${product.name}">
                        </div>
                        ${saleE}
                        <div class="info name info-name_mob">
                            <p>${product.name}</p>
                        </div>
                        <div class="price" style="margin-top: 0;">
                            <div><p class="cur-price">${productUnit.formatPrice}</p> </div>
                            <div class="flex-roww flex-coll_mob" style="justify-content: space-between;">
                                <p class="pre-price">${productUnit.formatPrePrice}</p>
                                <div class="info rate">
                                    <p><i class="bi bi-star-fill"></i><span class="rate-figure">${productUnit.star}</span>(<span class="total-estimate">${productUnit.totalComment}</span>)</p>
                                </div>
                            </div>
                        </div>
                    </a>


                </div>

            </div>
        `;
        productListContainer.append(productItem);
    });
    $('#product-response').html(script);

}

function getProductList(url) {
    console.log("getProductList");
    return new Promise((resolve, reject) => {
        $.ajax({
            url: url, // URL của Servlet hoặc API
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