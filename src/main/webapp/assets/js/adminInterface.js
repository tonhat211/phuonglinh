function previewImages(event) {
    const parent = event.currentTarget.closest('.group');
    const imgListContainer = $(parent).find('.upload__img-wrap');
    const files = Array.from(event.currentTarget.files);
    files.forEach(function (f) {
        var reader = new FileReader();
        reader.onload = function (e) {
            const imgContainerE = `
                <div class="img-container grid-col-12 group" style="margin-top: 15px;position: relative;">
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

function toggleActionToolQuery(event) {
    const group = event.currentTarget.closest('.group');
    group.querySelector('.action-tool-main').classList.toggle('active');
    group.querySelector('.action-tool-con').classList.toggle('active');
}

function toggleActionToolElement(group) {
    group.querySelector('.action-tool-main').classList.toggle('active');
    group.querySelector('.action-tool-con').classList.toggle('active');
}

function updateBanner(event) {
    const group = event.currentTarget.closest('.group');
    const form = event.currentTarget.closest('form');
    const deleteImgBtns = form.querySelectorAll('.upload__img-close');
    deleteImgBtns.forEach(function (f) {
        f.classList.add('active');
    })
    toggleActionToolElement(group);
    form.querySelector('input[name="imgs"]').disabled = false;

}

function cancelUpdateBanner(event) {
    const group = event.currentTarget.closest('.group');
    const form = event.currentTarget.closest('form');
    const deleteImgBtns = form.querySelectorAll('.upload__img-close.active');
    deleteImgBtns.forEach(function (f) {
        f.classList.remove('active');
    })
    toggleActionToolElement(group);
    form.querySelector('input[name="imgs"]').disabled = true;
}

document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('#updateBannerForm').addEventListener('submit', function(e) {
        e.preventDefault();
        var formData = new FormData(this);
        $.ajax({
            url: 'ainterface?a=updateBanner', // URL servlet của bạn
            type: 'POST',
            data: formData,
            processData: false, // can khi gui form
            contentType: false, // can khi gui form
            success: function (data) {
                if(data==='success') {
                    showSuccessToast2("Cập nhật thành công!",'none');
                }
            },
            error: function (error) {
                showErrorToast2("Có lỗi: "+error.responseText,'none');
            }
        });
    });

    document.querySelector('#updateLogoForm').addEventListener('submit', function(e) {
        e.preventDefault();
        var formData = new FormData(this);
        $.ajax({
            url: 'ainterface?a=updateLogo', // URL servlet của bạn
            type: 'POST',
            data: formData,
            processData: false, // can khi gui form
            contentType: false, // can khi gui form
            success: function (data) {
                if(data==='success') {
                    let newUrl = formData.get("thumbnailInput");

                    reRenderLogo(newUrl);
                    showSuccessToast2("Cập nhật thành công!",'none');
                }
            },
            error: function (error) {
                showErrorToast2("Có lỗi: "+error.responseText,'none');
            }
        });
    })
});

function reRenderLogo(newUrl) {
    document.querySelector('#header .logo img').src = `./assets/img/logo/${newUrl}`;

}

function tabTo(event) {
    const tabMenu = event.currentTarget.closest('.tab-menu');
    const tabLis = tabMenu.querySelectorAll('li.active');
    tabLis.forEach(function (f) {
        f.classList.remove('active');
    });
    event.currentTarget.classList.add('active');
    const mainContent = document.querySelector('.main-content');
    const tabContents = mainContent.querySelectorAll('.tab-content.active');
    tabContents.forEach(function (f) {
        f.classList.remove('active');
    });
    let nextTab = event.currentTarget.getAttribute("data-value");
    mainContent.querySelector(`#${nextTab}-content`).classList.add('active');


}

function previewImage(event) {
    const parent = event.currentTarget.closest('.group');
    var file = parent.querySelector('.thumbnailInput').files[0];
    var reader = new FileReader();

    reader.onload = function (e) {
        var imagePreview = parent.querySelector('.thumbnailPreview');
        imagePreview.src = e.target.result;
        parent.querySelector('.thumbnail-img-container').style.display = 'block';
        imagePreview.setAttribute("data-file", file.name);
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

function removeThumbnail(event) {
    const group = event.currentTarget.closest('.group');
    const thumbnailImgContainer = group.querySelector('.thumbnail-img-container');
    thumbnailImgContainer.querySelector('.thumbnailPreview').setAttribute('data-file', "");
    thumbnailImgContainer.style.display = 'none';
    group.querySelector('.thumbnailInput').value = "";
    updateThumbnailUrl(group);
}

function updateLogo(event) {
    const group = event.currentTarget.closest('.group');
    const form = event.currentTarget.closest('form');
    toggleActionToolElement(group);
    form.querySelector('input[name="thumbnail"]').disabled = false;

}

function cancelUpdateLogo(event) {
    const group = event.currentTarget.closest('.group');
    const form = event.currentTarget.closest('form');
    toggleActionToolElement(group);
    form.querySelector('input[name="thumbnail"]').disabled = true;
}

function clearImgStorage() {
    $.ajax({
        url: 'ainterface?a=clearImgStorage', // URL servlet của bạn
        type: 'POST',
        success: function (data) {
            if(data==='success') {
                showSuccessToast2("Dọn dẹp thành công!",'none');
            }
        },
        error: function (error) {
            showErrorToast2("Có lỗi: "+error.responseText,'none');
        }
    });
}

